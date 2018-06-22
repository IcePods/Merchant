package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shan.merchant.Entity.Merchant;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MerchantRegisterActivity extends AppCompatActivity {
    private Button btn_toLogin;//跳转到登录界面按钮
    private Button btn_register;//注册按钮
    private EditText edt_account;//店铺用户名  id=merchant_register_username
    private EditText edt_pwd;//店铺密码  id=merchant_register_pwd

    OkHttpClient okHttpClient;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/plain;charset=UTF-8");

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    String u = bundle.getString("merchant");
                    Log.i("uuuuuuuuuuuuuu",u);
                    if(u.equals("true")){
                        //注册成功，跳转到登录页面
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),MerchantLoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        //注册失败，不跳转 提示错误信息（提示用户名已注册？提示注册失败？）
                        Toast.makeText(getApplicationContext(),"用户名已存在",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_register);
        btn_toLogin = findViewById(R.id.merchant_RegisterTologin_btn);
        btn_register = findViewById(R.id.merchant_register_btn);
        edt_account = findViewById(R.id.merchant_register_username);
        edt_pwd = findViewById(R.id.merchant_register_pwd);

        btn_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MerchantLoginActivity.class);
                startActivity(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = edt_account.getText().toString().trim();
                String pwd = edt_pwd.getText().toString().trim();
                if (account.isEmpty() || account.equals("")){
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                }else if (account.length()>20){
                    Toast.makeText(MerchantRegisterActivity.this, "用户名过长，请输入长度20以内的字符", Toast.LENGTH_SHORT).show();
                }else if (pwd.isEmpty() || pwd.equals("")){
                    Toast.makeText(MerchantRegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }else if (pwd.length()>20){
                    Toast.makeText(MerchantRegisterActivity.this, "密码过长，请输入长度20以内的字符", Toast.LENGTH_SHORT).show();
                }else {
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Merchant merchant = new Merchant();
                    merchant.setMerchantAccount(account);
                    merchant.setMerchantPassword(pwd);
                    merchant.setShop(null);
                    merchant.setOpenSuccess(0);
                    String RegisterJson = gson.toJson(merchant);
                    Log.i("aaaaaaaaaaaaaaaaaaaa",RegisterJson);
                    postRegisterShop(RegisterJson);
                }
            }
        });
        edt_account.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                }else {
                    String str =edt_account.getText().toString().trim();
                    if (str.isEmpty() || str.equals("") || str == null){
                        Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                    }else if (str.length()>20){
                        Toast.makeText(MerchantRegisterActivity.this, "用户名过长，请输入长度20以内的字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        edt_pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                }else {
                    String str =edt_pwd.getText().toString().trim();
                    if (str.isEmpty() || str.equals("") || str == null){
                        Toast.makeText(MerchantRegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }else if (str.length()>20){
                        Toast.makeText(MerchantRegisterActivity.this, "密码过长，请输入长度20以内的字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        /*System.setProperty("sun.net.client.defaultConnectTimeout", String
                .valueOf(10000));// （单位：毫秒）
        System.setProperty("sun.net.client.defaultReadTimeout", String
                .valueOf(10000)); // （单位：毫秒）*/
    }
    //POST请求，带有封装请求参数的请求方式
    private void postRegisterShop(final String a){
        new Thread(){
            @Override
            public void run() {

                RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN,a);
                Request.Builder builder = new Request.Builder();
                builder.url(UrlAddress.url+"merchantRegister.action");
                builder.post(body);
                Request request = builder.build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    String a = response.body().string();
                    Message message =Message.obtain();
                    message.what =1;
                    Bundle bundle =new Bundle();
                    bundle.putString("merchant",a);
                    message.setData(bundle);
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }
}
