package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.MyTools.PrepareBarberData;
import com.example.shan.merchant.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MerchantEmployessChangeActivity extends AppCompatActivity {
    private LinearLayout ll_pic;// 照片所在LinearLayout  id=merchant_employess_change_ll_pic
    private LinearLayout ll_name;// 昵称所在LinearLayout  id=merchant_employess_change_ll_name
    private LinearLayout ll_intro;// 介绍所在LinearLayout  id=merchant_employess_change_ll_intro

    private CircleImageView img;// 店员照片 id=merchant_employesschange_picture
    private EditText name;// 店员昵称  id=merchant_employesschange_name
    private EditText intro;// 店员介绍  id=merchant_employesschange_introduce

    private ImageButton backbutton;//返回按钮  id=merchant_employesschange_back
    private Button submitbutton;//提交按钮  id=merchant_employesschange_submit

    private MyOnClickListener myOnClickListener;//点击事件监听器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_employess_change);

        initParams();
        //获取跳转携带的数据 并赋给店员相应的信息中
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Barber barber = (Barber) bundle.getSerializable("barberInfo");
        img.setImageResource(barber.getBarberImg());
        name.setText(barber.getBarberName());
        intro.setText(barber.getBarberIntro());
        //绑定监听器
        myOnClickListener = new MyOnClickListener();
        backbutton.setOnClickListener(myOnClickListener);
        submitbutton.setOnClickListener(myOnClickListener);
    }

    private void initParams() {
        ll_pic = findViewById(R.id.merchant_employess_change_ll_pic);
        ll_name = findViewById(R.id.merchant_employess_change_ll_name);
        ll_intro = findViewById(R.id.merchant_employess_change_ll_intro);
        img = findViewById(R.id.merchant_employesschange_picture);
        name = findViewById(R.id.merchant_employesschange_name);
        intro = findViewById(R.id.merchant_employesschange_introduce);
        backbutton = findViewById(R.id.merchant_employesschange_back);
        submitbutton = findViewById(R.id.merchant_employesschange_submit);
    }
    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //返回按钮
                case R.id.merchant_employesschange_back:
                    finish();
                    break;
                //提交按钮
                case R.id.merchant_employesschange_submit:
                    Toast.makeText(getApplicationContext(),"submit",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
