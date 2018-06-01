package com.example.shan.merchant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.shan.merchant.R;

public class MerchantProductionDetailActivity extends AppCompatActivity {
    private ImageButton Back;
    private Button Change;
    private MyClickListener myClickListener;
    public static MerchantProductionDetailActivity instance = null;//用于finish页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_production_detail);
        instance = this;
        findview();
        Back.setOnClickListener(myClickListener);
        Change.setOnClickListener(myClickListener);
    }

    private void findview() {
        Back = findViewById(R.id.merchant_production_detail_back);
        Change = findViewById(R.id.production_detail_edit);
        myClickListener = new MyClickListener();
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_production_detail_back:
                    finish();
                    break;
                case R.id.production_detail_edit:
                    intent.setClass(getApplicationContext(),MerchantProductionChangeActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
