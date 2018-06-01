package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shan.merchant.R;

public class MerchantActivitysAddActivity extends AppCompatActivity {
    private ImageView btn_addActivity_back;//返回按钮
    private Button btn_addActivity_submit;//提交按钮

    private EditText activityName;//活动名称
    private EditText activityIntro;//活动介绍
    private EditText activityTime;//活动时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_activitys_add);

        getView();

        MyOnClickListener listener = new MyOnClickListener();
        btn_addActivity_submit.setOnClickListener(listener);
        btn_addActivity_back.setOnClickListener(listener);
    }

    //获取控件
    private void getView(){
        btn_addActivity_back = findViewById(R.id.merchant_activity_add_back);
        btn_addActivity_submit = findViewById(R.id.merchant_activity_add_submit);
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

            }
        }
    }

}
