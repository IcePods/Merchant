package com.example.shan.merchant.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shan.merchant.Adapter.ActivitiesAdapter;
import com.example.shan.merchant.Entity.Activity;
import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.Entity.Merchant;
import com.example.shan.merchant.Entity.Shop;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.MerchantTokenSql;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private ImageView btn_activity_back;//顶部返回按钮
    private ImageView img_deleteActivity;//删除活动按钮
    private ImageView img_addActivity;//添加活动图片
    private TextView tv_addActivity;//添加活动文本框
    private ListView lv_activities;//活动列表
    private TextView tv_activity_name;//活动名称
    private TextView tv_activity_content;//活动内容
    private TextView tv_activity_startTime;//活动开始时间
    private TextView tv_activity_endTime;//活动结束时间

//    private List<Activity> activityList = new ArrayList<>();//活动列表

    private String token="";
    private String merchantAccount;//账号
    private String merchantPassword;//密码
    private SQLiteDatabase database;//查询数据库
    private List<Activity> list = new ArrayList<>();

    OkHttpClient okHttpClient;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/plain;charset=UTF-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_activitys);

        selectMerchant();
        //获取控件
        getView();
        okHttpClient = new OkHttpClient();
        initData();

        Log.i("gugu", "ceshi");
        //绑定监听器
        MyOnClickListener listener = new MyOnClickListener();
        btn_activity_back.setOnClickListener(listener);
        /*img_deleteActivity.setOnClickListener(listener);*/
        img_addActivity.setOnClickListener(listener);
        tv_addActivity.setOnClickListener(listener);


    }

    private void initData() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Bundle bundle = msg.getData();
                        String m = bundle.getString("activity");
                        Gson gson = new Gson();
                        list = gson.fromJson(m,new TypeToken<List<Activity>>(){}.getType());
                        Log.i("guguaaaaaa", "" + list.get(1).getActivityName());
                        ActivitiesAdapter adapter = new ActivitiesAdapter(getApplicationContext(), list, R.layout.item_merchant_activity);
                        lv_activities.setAdapter(adapter);
                        lv_activities.setDivider(null);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        postGetActivityByMerchant(merchantAccount, merchantPassword, handler);
    }

    //获取控件
    private void getView() {
        btn_activity_back = findViewById(R.id.activity_back);
        lv_activities = findViewById(R.id.lv_activities);
        img_addActivity = findViewById(R.id.img_addActivity);
        tv_addActivity = findViewById(R.id.tv_addActivity);
        tv_activity_name = findViewById(R.id.item_merchant_activity_name);
        tv_activity_content = findViewById(R.id.item_merchant_activity_content);
        tv_activity_startTime = findViewById(R.id.item_merchant_activity_start_time);
        tv_activity_endTime = findViewById(R.id.item_merchant_activity_end_time);
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
                case R.id.img_addActivity://添加活动
                    intent.setClass(getApplicationContext(), MerchantAddActivityActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_addActivity://添加活动
                    intent.setClass(getApplicationContext(), MerchantAddActivityActivity.class);
                    startActivity(intent);
                    break;


            }
        }
    }

    /* private List<Map<String, Object>> prepareDatas() {
         List<Map<String, Object>> activities = new ArrayList<>();
         Map<String,Object> activity1 = new HashMap<>();
         activity1.put("activity_name","520理发活动");
         activity1.put("activity_content","13443225322");
         activity1.put("activity_time","5月20日-25日");
         activities.add(activity1);

         Map<String,Object> activity2 = new HashMap<>();
         activity2.put("activity_name","520理发活动");
         activity2.put("activity_content","13443225322");
         activity2.put("activity_time","5月20日-25日");
         activities.add(activity2);

         Map<String,Object> activity3 = new HashMap<>();
         activity3.put("activity_name","520理发活动");
         activity3.put("activity_content","13443225322");
         activity3.put("activity_time","5月20日-25日");
         activities.add(activity3);
         return activities;
     }*/
    //POST请求，带有封装请求参数的请求方式
    private void postGetActivityByMerchant(final String merchantAccount, final String merchantPassword, final Handler handler) {
        new Thread() {
            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("merchantAccount", merchantAccount);
                builder.add("merchantPassword", merchantPassword);
                FormBody body = builder.build();
                Request request = new Request.Builder().post(body).url(UrlAddress.url + "merchantGetMerchantActivity.action").build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    String a = response.body().string();
                    Message message = Message.obtain();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("activity", a);
                    message.setData(bundle);
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //从本地数据库中获取登录店铺的账号密码
    private void selectMerchant() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("merchanttoken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        database = new MerchantTokenSql(this).getReadableDatabase();
        /*Cursor cursor = database.rawQuery("select * from user where usertoken = "+token,null);*/
        Cursor cursor = database.query("merchant", null, "merchanttoken" + "=?", new String[]{token}, null, null, null);
        if (cursor.moveToLast()) {
            merchantAccount = cursor.getString(cursor.getColumnIndex("merchantaccount"));
            merchantPassword = cursor.getString(cursor.getColumnIndex("merchantpassword"));
        }
    }
}
