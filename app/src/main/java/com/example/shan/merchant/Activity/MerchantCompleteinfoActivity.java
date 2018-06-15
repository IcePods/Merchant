package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shan.merchant.R;

public class MerchantCompleteinfoActivity extends AppCompatActivity {
    private EditText editShopName;
    private EditText editBossName;
    private EditText editPhone;
    private TextView tvAddress;
    private ImageView ivShopimages;
    private Button btnSubmit;
    private MyOnClickListener myOnClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_completeinfo);

        //获取控件
        findview();
        myOnClickListener = new MyOnClickListener();
        btnSubmit.setOnClickListener(myOnClickListener);
    }

    private void findview() {
        editShopName = findViewById(R.id.merchant_info_shopname);
        editBossName = findViewById(R.id.merchant_info_shopmanagername);
        editPhone = findViewById(R.id.merchant_info_shoptelephone);
        tvAddress = findViewById(R.id.but_completeinfo_address);
        ivShopimages = findViewById(R.id.merchant_info_shopimages);
        btnSubmit = findViewById(R.id.merchant_completeinfo_btn);
    }
    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //申请开店按钮
                case R.id.merchant_completeinfo_btn:
                    //只实现跳转
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
            }
        }
    }
}
