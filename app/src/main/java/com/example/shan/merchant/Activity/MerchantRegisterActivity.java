package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shan.merchant.R;

public class MerchantRegisterActivity extends AppCompatActivity {
    private Button btn_register;//注册按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_register);
        btn_register = findViewById(R.id.merchant_register_btn);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MainActivity.class);
                Toast.makeText(MerchantRegisterActivity.this, "您已成功注册", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
