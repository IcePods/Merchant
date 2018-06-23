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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shan.merchant.Adapter.ActivitiesAdapter;
import com.example.shan.merchant.Entity.Activity;
import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.Entity.Merchant;
import com.example.shan.merchant.Entity.Shop;
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
import java.util.Set;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MerchantActivitiesActivity extends AppCompatActivity {
    private ImageButton btn_activity_back;//顶部返回按钮
    private Button addActivity; //添加活动
    private ListView lv_activity;//活动列表
    private RefreshLayout refreshLayout;//刷新加载控件
    private List<Activity> data;//数据源
    private int showDataNum=0;//当前显示的数量
    private ActivitiesAdapter adapter;

    private String token;
    //请求工具类
    private UploadPictureUtil util = new UploadPictureUtil();
    //请求地址
    private String getActivityUrl = UrlAddress.url + "getActivityList.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_activitys);
        //获取控件
        getView();
        //初始化页面数据
        initData();
        //设置页面刷新加载
        setPullRefresher();

        //绑定监听器
        MyOnClickListener listener = new MyOnClickListener();
        btn_activity_back.setOnClickListener(listener);
        addActivity.setOnClickListener(listener);
    }

    //获取控件
    private void getView() {
        btn_activity_back = findViewById(R.id.activity_back);
        lv_activity = findViewById(R.id.activity_list);
        addActivity = findViewById(R.id.activity_add);
        refreshLayout = findViewById(R.id.activity_refresh_layout);
        token = util.getToken(getApplicationContext());
        data = new ArrayList<>();
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String m = bundle.getString("string");
                Gson gson = new Gson();
                List<Activity> list = gson.fromJson(m,new TypeToken<List<Activity>>(){}.getType());
                if(list.size()<=0){
                    Toast.makeText(getApplicationContext(),
                            "您还没有发布任何作品呦！",
                            Toast.LENGTH_SHORT).show();
                }else {
                    data.addAll(list);
                    if(list.size()>=5) {
                        list.removeAll(list);
                        for (int i = 0; i < 5; i++) {
                            list.add(data.get(i));
                        }
                    }
                }
                showDataNum = list.size();
                adapter = new ActivitiesAdapter(getApplicationContext(), list, R.layout.item_merchant_activity);
                lv_activity.setAdapter(adapter);
                lv_activity.setDivider(null);
            }
        };

        util.requestServer(getActivityUrl,null,token,handler);
    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.activity_back://返回按钮
                    finish();
                    break;
                case R.id.activity_add://添加活动
                    intent.setClass(getApplicationContext(), MerchantAddActivityActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 刷新加载功能
     */
    private void setPullRefresher(){
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(2000/*,false*/);
                //不传时间则立即停止刷新    传入false表示刷新失败
                //服务器请求动态数据
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle bundle = msg.getData();
                        String dynamicStrList = bundle.getString("string");
                        Gson gson = new Gson();
                        List<Activity> list = gson.fromJson(dynamicStrList, new TypeToken<List<Activity>>(){}.getType());
                        if(list.size() == 0){
                            Toast.makeText(getApplicationContext(),
                                    "还没有人发布动态呦！",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            data.removeAll(data);
                            data.addAll(list);
                            //设置显示的数据
                            if(list.size()>5){
                                list.removeAll(list);
                                for (int i=0;i<5;i++){
                                    list.add(data.get(i));
                                }
                            }
                        }

                        showDataNum = list.size();
                        adapter.refresh(list);
                    }
                };

                util.requestServer(getActivityUrl,null,token,handler);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
                //在这里执行上滑加载时的具体操作
                List<Activity> add = new ArrayList<>();
                if(showDataNum < data.size()){
                    if(showDataNum+5 <= data.size()){
                        for(int i=0; i<5; i++){
                            add.add(data.get(showDataNum + i));
                        }
                        showDataNum+=5;
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