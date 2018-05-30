package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shan.merchant.R;

public class MerchantInformationActivity extends AppCompatActivity {
    private ImageButton btnback;
    private EditText editShopName;//店铺名字
    private EditText editBoss;//店长名字
    private TextView tvPicture;//店铺头像
    private TextView tvAddress;//店铺地址
    private EditText editPhone;//电话
    private ImageView ivAddPicture;//添加图片
    private Button btnConfirm;
    private myClickListener myListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_information);

        //获取控件
        findview();
        btnback.setOnClickListener(myListener);
        tvPicture.setOnClickListener(myListener);
        tvAddress.setOnClickListener(myListener);
        ivAddPicture.setOnClickListener(myListener);
        btnConfirm.setOnClickListener(myListener);
    }

    private void findview() {
        btnback = findViewById(R.id.merchant_information_back);
        editShopName = findViewById(R.id.merchant_information_name);
        editBoss = findViewById(R.id.merchant_information_boss);
        tvPicture = findViewById(R.id.tv_information_picture);
        tvAddress = findViewById(R.id.tv_information_address);
        editPhone = findViewById(R.id.merchant_information_phone);
        ivAddPicture = findViewById(R.id.merchant_information_shopimages);
        btnConfirm = findViewById(R.id.merchant_information_confirm);
        myListener = new myClickListener();
    }
    class myClickListener implements ImageView.OnClickListener{
        Intent intent = new Intent();
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                //点击返回按钮
                case R.id.merchant_information_back:
                    finish();
                    break;
                //点击选择店铺头像TextView
                case R.id.tv_information_picture:
                    break;
                //点击选择店铺地址TextView
                case R.id.tv_information_address:
                    break;
                //点击选择店铺图片ImageView
                case R.id.merchant_information_shopimages:
                    break;
                //点击保存按钮
                case R.id.merchant_information_confirm:
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
}
