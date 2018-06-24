package com.example.shan.merchant.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shan.merchant.Adapter.BarberAdapter;
import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.Entity.HairStyle;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.MerchantTokenSql;
import com.example.shan.merchant.MyTools.UploadPictureUtil;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 店员管理页面
 */
public class MerchantEmployessActivity extends AppCompatActivity {
    private ImageButton backbutton;//返回按钮
    private LinearLayout addEmployeeLL;//添加店员
    private Button addEmployeeButton;//添加店员
    private MyOnClickListener myOnClickListener;//监听器
    private ListView lv;
    private RefreshLayout refreshLayout;

    private List<Barber> data; //店员列表
    private int showDataNum=0;
    UploadPictureUtil util = new UploadPictureUtil();
    private String token;
    private BarberAdapter adapter;
    private String getBarberUrl = UrlAddress.url + "getBarberByMerchant.action";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_employess);
        //初始化控件
        initContrl();

        //获取服务器数据
        initData();
        //设置刷新加载
        setPullRefresher();

        myOnClickListener = new MyOnClickListener();
        backbutton.setOnClickListener(myOnClickListener);
        addEmployeeLL.setOnClickListener(myOnClickListener);
        addEmployeeButton.setOnClickListener(myOnClickListener);
    }
    //初始化控件
    private void initContrl() {
        lv = findViewById(R.id.merchant_employees_list);
        backbutton = findViewById(R.id.merchant_employess_back);//返回按钮
        addEmployeeLL = findViewById(R.id.merchant_employess_add);//添加店员
        addEmployeeButton = findViewById(R.id.merchant_employess_add_btn);//添加店员按钮
        refreshLayout = findViewById(R.id.employess_refresh_ayout);
        token = util.getToken(getApplicationContext());
        data = new ArrayList<>();
    }

    private void initData() {
        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String m = bundle.getString("string");
                Gson gson = new Gson();
                Log.i("李垚：：barber", m);
                List<Barber> list = gson.fromJson(m,new TypeToken<List<Barber>>(){}.getType());
                if(list.size()<=0){
                    Toast.makeText(getApplicationContext(),
                            "您还没有发布任何作品呦！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    data.addAll(list);
                    if(list.size()>=7) {
                        list.removeAll(list);
                        for (int i = 0; i < 7; i++) {
                            list.add(data.get(i));
                        }
                    }
                }
                showDataNum = list.size();
                adapter = new BarberAdapter(getApplicationContext(), R.layout.item_activity_merchant_employess, list);
                lv.setAdapter(adapter);
                lv.setDivider(null);
            }
        };
        util.requestServer(getBarberUrl,null,token,handler);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()){
                //返回按钮
                case R.id.merchant_employess_back:
                    //只销毁当前页面
                    finish();
                    break;
                //添加店员
                case R.id.merchant_employess_add:
                    //只实现跳转，跳转到添加店员页面
                    //2. 指定跳转路线
                    intent.setClass(getApplicationContext(),MerchantEmployessAddActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
                //添加店员
                case R.id.merchant_employess_add_btn:
                    //只实现跳转，跳转到添加店员页面
                    //2. 指定跳转路线
                    intent.setClass(getApplicationContext(),MerchantEmployessAddActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
            }
        }
    }
    private void setPullRefresher(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);
                //不传时间则立即停止刷新    传入false表示刷新失败
                //服务器请求动态数据
                UploadPictureUtil util = new UploadPictureUtil();
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle bundle = msg.getData();
                        String dynamicStrList = bundle.getString("string");
                        Gson gson = new Gson();
                        List<Barber> list = gson.fromJson(dynamicStrList, new TypeToken<List<Barber>>(){}.getType());
                        if(list.size() == 0){
                            Toast.makeText(getApplicationContext(),
                                    "还没有人发布动态呦！",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            data.removeAll(data);
                            data.addAll(list);
                            //设置显示的数据
                            if(list.size()>7){
                                list.removeAll(list);
                                for (int i=0;i<7;i++){
                                    list.add(data.get(i));
                                }
                            }
                        }

                        showDataNum = list.size();
                        adapter.refresh(list);
                    }
                };
                util.requestServer(getBarberUrl,null,token,handler);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
                //在这里执行上滑加载时的具体操作
                List<Barber> add = new ArrayList<>();
                if(showDataNum < data.size()){
                    if(showDataNum+7 <= data.size()){
                        for(int i=0; i<7; i++){
                            add.add(data.get(showDataNum + i));
                        }
                        showDataNum+=7;
                    }else {
                        for(;showDataNum<data.size(); showDataNum++){
                            add.add(data.get(showDataNum));
                            showDataNum = data.size();
                        }
                    }
                }else {
                    Toast.makeText(getApplicationContext(),
                            "没有更多啦",
                            Toast.LENGTH_SHORT).show();
                }
                adapter.add(add);
            }
        });
    }

}
