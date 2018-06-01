package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shan.merchant.R;

public class MerchantLoginActivity extends AppCompatActivity {
    private Button btn_toRegister;//跳转到注册界面按钮
    private Button btn_login;//登录按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_login);

        //获取控件
        getView();

        //绑定监听器
        MyOnClickListener listener = new MyOnClickListener();
        btn_login.setOnClickListener(listener);
        btn_toRegister.setOnClickListener(listener);
    }

    //获取控件
    private void getView(){
        btn_toRegister = findViewById(R.id.merchant_loginToRegister_btn);
        btn_login = findViewById(R.id.merchant_login_btn);
    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_login_btn://登录
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    Toast.makeText(MerchantLoginActivity.this, "您已成功登录", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    break;
                case R.id.merchant_loginToRegister_btn://注册
                    intent.setClass(getApplicationContext(),MerchantRegisterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
