package com.example.shan.merchant.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.shan.merchant.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 问题
 * 在id为merchant_employess_add_ll_pic的LinearLayout中，如未上传照片 将CircleImageView设为空 显示“请选择”的TextView
 * 如上传照片 则将TextView的内容设为空  考虑点击事件绑定在imageButton还是同时绑定在TextView
 * 点击提交 提交到数据库并返回到店员管理页面  考虑提示保存成功
 */
public class MerchantEmployessAddActivity extends AppCompatActivity {
    private ImageButton backbutton;//返回按钮
    private Button submitbutton;//提交按钮

    private CircleImageView img;//店员照片
    private EditText name;//店员昵称
    private EditText intro;//店员介绍

    private MyOnClickListener myOnClickListener;//监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_employess_add);

        initParams();
        myOnClickListener = new MyOnClickListener();
        backbutton.setOnClickListener(myOnClickListener);
        submitbutton.setOnClickListener(myOnClickListener);
    }
    //给控件赋值
    private void initParams() {
        backbutton = findViewById(R.id.merchant_employessadd_back);
        submitbutton = findViewById(R.id.merchant_employessadd_submit);
        img = findViewById(R.id.merchant_employessadd_picture);
        name = findViewById(R.id.merchant_employessadd_name);
        intro = findViewById(R.id.merchant_employessadd_introduce);
    }

    //监听器
    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //返回按钮
                case R.id.merchant_employessadd_back:
                    finish();
                    break;
                //提交按钮
                case R.id.merchant_employessadd_submit:
//                    finish();
                    break;
            }
        }
    }
}
