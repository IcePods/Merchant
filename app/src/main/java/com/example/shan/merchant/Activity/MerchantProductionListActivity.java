package com.example.shan.merchant.Activity;

import android.content.Intent;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shan.merchant.Adapter.GridImageAdapter;
import com.example.shan.merchant.Adapter.MerchantProductionListAdopter;
import com.example.shan.merchant.Entity.HairStyle;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.Entity.Works;
import com.example.shan.merchant.MyTools.UploadPictureUtil;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MerchantProductionListActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private LinearLayout addProductionLL;//上传新作品
    private Button addProductionButton;//上传新作品
    private MyClickListener myClickListener;
    private RefreshLayout refreshLayout;
    private ListView lv;
    private List<HairStyle> data; //数据源
    private int showDataNum=0;
    public static MerchantProductionListActivity prolist = null;//用于finish页面
    private MerchantProductionListAdopter adopter;
    UploadPictureUtil util = new UploadPictureUtil();
    private String token;

    private String showHairStyleUrl = UrlAddress.url + "getShopHairStyle.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_production_list);
        prolist = this;
        //初始化控件
        initControl();
        //初始化页面数据
        initData();
        //绑定刷新加载
        setPullRefresher();

        //获取监听器
        myClickListener = new MyClickListener();


        backBtn.setOnClickListener(myClickListener);
        addProductionLL.setOnClickListener(myClickListener);
        addProductionButton.setOnClickListener(myClickListener);
    }

    /**
     * 初始化控件
     */
    private void initControl() {
        backBtn = findViewById(R.id.merchant_production_list_back);
        addProductionLL = findViewById(R.id.create_new_production);
        addProductionButton = findViewById(R.id.create_new_production_button);
        lv = findViewById(R.id.merchant_production_list);
        refreshLayout = findViewById(R.id.refreshLayout);
        data = new ArrayList<>();
        token = util.getToken(getApplicationContext());

    }

    /**
     * 初始化页面数据
     */
    private void initData(){
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String hairStyleListStr = bundle.getString("string");
                Log.i("李垚：：：返回的内容", hairStyleListStr);
                Gson gson = new Gson();
                List<HairStyle> list = gson.fromJson(hairStyleListStr, new TypeToken<List<HairStyle>>() {}.getType());
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
                adopter = new MerchantProductionListAdopter(getApplicationContext(),R.layout.item_activity_merchant_production,list);
                lv.setAdapter(adopter);
                lv.setDivider(null);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //携带数据跳转到另一个Activity，进行数据的更新操作
                        Intent intent = new Intent();
                        HairStyle hairStyle = data.get(position);
                        Log.i("李垚：：：：set数量", hairStyle.getHairStyleDetailSet().size()+"");
                        intent.putExtra("hairstyle",hairStyle);
                        //指定跳转路线
                        intent.setClass(getApplicationContext(),MerchantProductionDetailActivity.class);

                        startActivity(intent);
                    }
                });
            }
        };
        Log.i("李垚：：：：", token);
        Log.i("李垚：：：showHairStyleUrl",showHairStyleUrl);
        util.requestServer(showHairStyleUrl,null,token,handler);

    }

    //点击事件监听器
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_production_list_back:
                    finish();
                    break;
                case R.id.create_new_production:
                    intent.setClass(getApplicationContext(),MerchantNewProductionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.create_new_production_button:
                    intent.setClass(getApplicationContext(),MerchantNewProductionActivity.class);
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
                        List<HairStyle> list = gson.fromJson(dynamicStrList, new TypeToken<List<HairStyle>>(){}.getType());
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
                        adopter.refresh(list);
                    }
                };

                util.requestServer(showHairStyleUrl,null,token,handler);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000);
                //在这里执行上滑加载时的具体操作
                List<HairStyle> add = new ArrayList<>();
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
                adopter.add(add);
            }
        });
    }



}
