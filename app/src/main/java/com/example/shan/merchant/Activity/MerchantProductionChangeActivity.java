package com.example.shan.merchant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.shan.merchant.R;

public class MerchantProductionChangeActivity extends AppCompatActivity {
    private ImageButton Backc;
    private EditText WorkNamec;
    private EditText WorkDescriptionc;
    private EditText WorkPricec;
    private ImageView WorkPicturec;
    private Button Commitc;
    private MyClickListener myClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_production_change);
        //获取控件
        findview();
        Backc.setOnClickListener(myClickListener);
        Commitc.setOnClickListener(myClickListener);
    }

    private void findview() {
        Backc = findViewById(R.id.merchant_production_change_back);
        WorkNamec = findViewById(R.id.production_change_title);
        WorkDescriptionc = findViewById(R.id.production_change_description);
        WorkPricec = findViewById(R.id.production_change_price);
        WorkPicturec = findViewById(R.id.production_change_picture);
        Commitc = findViewById(R.id.commit_production_change);
        myClickListener = new MyClickListener();
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_production_change_back:
                    Log.i("ztl","点击了返回");
                    finish();
                    break;
                case R.id.commit_production_change:
                    intent.setClass(getApplicationContext(),MerchantProductionListActivity.class);
                    startActivity(intent);
                    //结束当前页面
                    finish();
                    //结束详情页面
                    MerchantProductionDetailActivity.instance.finish();
                    //结束发型列表页面
                    MerchantProductionListActivity.prolist.finish();
                    break;
            }
        }
    }
}
