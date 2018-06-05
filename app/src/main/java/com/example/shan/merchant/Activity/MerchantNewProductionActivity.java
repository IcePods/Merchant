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

import org.w3c.dom.Text;

public class MerchantNewProductionActivity extends AppCompatActivity {
    private ImageButton Back;
    private EditText WorkName;
    private EditText WorkDescription;
    private EditText WorkPrice;
    private ImageView WorkPicture;
    private TextView WorkHead;
    private Button Commit;
    private MyClickListener myClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_new_production);
        //获取控件
        findview();
        Back.setOnClickListener(myClickListener);
        Commit.setOnClickListener(myClickListener);
    }

    private void findview() {
        Back = findViewById(R.id.merchant_new_production_back);
        WorkName = findViewById(R.id.new_production_title);
        WorkDescription = findViewById(R.id.new_production_description);
        WorkPrice = findViewById(R.id.new_production_price);
        WorkPicture = findViewById(R.id.new_production_shopimages);
        WorkHead = findViewById(R.id.new_production_head);
        Commit = findViewById(R.id.commit_new_production);
        myClickListener = new MyClickListener();
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_new_production_back:
                    finish();
                    break;
                case R.id.commit_new_production:
                    finish();
            }
        }
    }
}
