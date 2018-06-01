package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shan.merchant.Adapter.ActivitiesAdapter;
import com.example.shan.merchant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchantActivitysActivity extends AppCompatActivity {
    private ImageView btn_activity_back;//顶部返回按钮
    private ImageView img_deleteActivity;//删除活动按钮
    private ImageView img_addActivity;//添加图片
    private TextView tv_addActivity;//添加文本框
    private ListView lv_activities;//活动列表


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_activitys);

        getView();

        //绑定监听器
        MyOnClickListener listener = new MyOnClickListener();
        btn_activity_back.setOnClickListener(listener);
        /*img_deleteActivity.setOnClickListener(listener);*/
        img_addActivity.setOnClickListener(listener);
        tv_addActivity.setOnClickListener(listener);
        ActivitiesAdapter adapter = new ActivitiesAdapter(getApplicationContext(),prepareDatas(),R.layout.item_merchant_activity);
        lv_activities.setAdapter(adapter);
        lv_activities.setDivider(null);


    }

    //获取控件
    private void getView(){
        btn_activity_back = findViewById(R.id.activity_back);
        lv_activities = findViewById(R.id.lv_activities);
        img_addActivity = findViewById(R.id.img_addActivity);
        tv_addActivity = findViewById(R.id.tv_addActivity);

    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.activity_back://返回按钮
                    finish();
                    break;
                case R.id.img_addActivity://添加活动
                    intent.setClass(getApplicationContext(),MerchantActivitysAddActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tv_addActivity://添加活动
                    intent.setClass(getApplicationContext(),MerchantActivitysAddActivity.class);
                    startActivity(intent);
                    break;


            }
        }
    }
    private List<Map<String, Object>> prepareDatas() {
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
    }
}
