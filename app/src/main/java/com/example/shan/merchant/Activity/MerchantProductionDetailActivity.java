package com.example.shan.merchant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shan.merchant.Entity.HairStyle;
import com.example.shan.merchant.Entity.HairStyleDetail;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.UploadPictureUtil;
import com.example.shan.merchant.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MerchantProductionDetailActivity extends AppCompatActivity {
    private ImageButton Back;//返回按钮
    private Button delete;//删除按钮
    private MyClickListener myClickListener;
    public static MerchantProductionDetailActivity instance = null;//用于finish页面
    private HairStyle hairStyle;  //页面跳转时携带的发型对象
    private ImageView detailImage;//图片详情
    private TextView hairstyleTitle;//发型名称
    private TextView hairstyleType;//发型类型
    private TextView hairstyleDesc;//发型简介
    UploadPictureUtil util = new UploadPictureUtil();
    private String deleteUrl = UrlAddress.url + "deleteHairStyle.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_production_detail);
        Intent intent = getIntent();
        hairStyle = (HairStyle)intent.getSerializableExtra("hairstyle");

        instance = this;
        findview();
        initControlData();
        Back.setOnClickListener(myClickListener);
        delete.setOnClickListener(myClickListener);
    }

    private void findview() {
        Back = findViewById(R.id.merchant_production_detail_back);
        delete = findViewById(R.id.production_detail_delete);
        myClickListener = new MyClickListener();
        detailImage = findViewById(R.id.production_detail_image);
        hairstyleTitle = findViewById(R.id.production_detail_title);
        hairstyleType = findViewById(R.id.production_detail_type);
        hairstyleDesc = findViewById(R.id.production_detail_description);
    }

    /**
     * 给控件对象赋值
     */
    private void initControlData(){
        Glide.with(this).load(UrlAddress.url +hairStyle.getHairstylePicture()).into(detailImage);
        hairstyleTitle.setText(hairStyle.getHairstyleName());
        hairstyleType.setText(hairStyle.getHairstyleType());
        hairstyleDesc.setText(hairStyle.getHairstyleIntroduce());
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_production_detail_back:
                    finish();
                    break;
                case R.id.production_detail_delete:
                    Gson gson = new Gson();
                    String hairStyleStr = gson.toJson(hairStyle);
                    util.requestServer(deleteUrl,hairStyleStr,null,null);
                    finish();
                    break;
            }
        }
    }
}
