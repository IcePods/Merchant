package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shan.merchant.Entity.Activity;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.UploadPictureUtil;
import com.example.shan.merchant.R;
import com.google.gson.Gson;

public class MerchantAddActivityActivity extends AppCompatActivity {
    private Button btn_addActivity_back;//返回按钮
    private Button btn_addActivity_submit;//提交按钮

    private EditText activityName;//活动名称
    private EditText activityIntro;//活动介绍
    private EditText startTime;//活动开始时间
    private EditText endTime; //活动结束时间

    private Activity activity;  //Activity对象
    private String token;
    UploadPictureUtil util = new UploadPictureUtil();
    private String addActivityUrl = UrlAddress.url + "addActivity.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_activitys_add);
        //初始化控件
        initControl();

        MyOnClickListener listener = new MyOnClickListener();
        btn_addActivity_submit.setOnClickListener(listener);
        btn_addActivity_back.setOnClickListener(listener);
    }

    //获取控件
    private void initControl(){
        btn_addActivity_back = findViewById(R.id.merchant_activity_add_back);
        btn_addActivity_submit = findViewById(R.id.activity_submit);
        activityName = findViewById(R.id.merchant_activity_add_name);
        activityIntro = findViewById(R.id.merchant_activity_add_introduce);
        startTime = findViewById(R.id.activity_start_time);
        endTime = findViewById(R.id.activity_end_time);
        activity = new Activity();
        token = util.getToken(getApplicationContext());
    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.merchant_activity_add_back://返回按钮
                    finish();
                    break;
                case R.id.activity_submit:
                    activity.setActivityName(activityName.getText().toString());
                    activity.setActivityContent(activityIntro.getText().toString());
                    activity.setActivityStartTime(startTime.getText().toString());
                    activity.setActivityEndTime(endTime.getText().toString());
                    Gson gson = new Gson();
                    String activityStr = gson.toJson(activity);
                    util.requestServer(addActivityUrl, activityStr, token, null);
                    finish();
                    break;
            }
        }
    }
}
