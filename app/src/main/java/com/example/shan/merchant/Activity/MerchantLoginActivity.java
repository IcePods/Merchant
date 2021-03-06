package com.example.shan.merchant.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shan.merchant.Entity.Merchant;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.MerchantTokenSql;
import com.example.shan.merchant.R;
import com.google.gson.Gson;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MerchantLoginActivity extends AppCompatActivity {
    private Button btn_toRegister;//跳转到注册界面按钮
    private Button btn_login;//登录按钮
    private EditText merchant_userName;//登录用户名
    private EditText merchant_userPwd;//登录密码
    private TextView errorMessage;
    private MerchantTokenSql merchantTokenSql = new MerchantTokenSql(this);//数据库连接
    private SQLiteDatabase sqLiteDatabase;
    private OkHttpClient okHttpClient;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/plain;charset=UTF-8");


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //店员管理
                case 1:
                    Bundle bundle = msg.getData();
                    String m = bundle.getString("merchant");
                    Log.i("loginbackaaaaaaa",m);
                    Gson gson = new Gson();
                    Merchant merchant = gson.fromJson(m,Merchant.class);

                    if(merchant.getMerchantCondition()== true){
                        SharedPreferences sharedPreferences= getSharedPreferences("merchanttoken", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.putString("token",merchant.getMerchantToken());
                        editor.putString("merchantAccount",merchant.getMerchantAccount());
                        editor.putString("merchantPassword",merchant.getMerchantPassword());
                        editor.commit();
                        //登录成功插入数据库
                        insertMerchantToSql(merchant);
                        JPushInterface.setAlias(getApplicationContext(),0,merchant.getMerchantToken());
                        Intent intent = new Intent();
                        //若已经开店成功 直接跳转到主页
                        if(1 == merchant.getOpenSuccess()){
                            intent.setClass(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }else {
                            //若未开店成功 则跳转到完善店铺信息页面   未成功==0
                            intent.setClass(getApplicationContext(),MerchantCompleteinfoActivity.class);
                            startActivity(intent);
                        }


                    }else {
//                        errorMessage.setText("用户明密码错误");
                        Toast.makeText(getApplicationContext(),"用户名密码错误",Toast.LENGTH_SHORT).show();
                        merchant_userName.setText("");
                        merchant_userPwd.setText("");
                    }
                    break;
                //活动管理
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_login);

        //获取控件
        getView();

        //绑定监听器
        okHttpClient = new OkHttpClient();
        MyOnClickListener listener = new MyOnClickListener();
        btn_login.setOnClickListener(listener);
        btn_toRegister.setOnClickListener(listener);
        merchant_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                }else {
                    String str =merchant_userName.getText().toString().trim();
                    if (str.isEmpty()){
                        Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                    }else if (str.length()>20){
                        Toast.makeText(MerchantLoginActivity.this, "用户名过长，请输入长度20以内的字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        merchant_userPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                }else {
                    String str =merchant_userPwd.getText().toString().trim();
                    if (str.isEmpty()){
                        Toast.makeText(MerchantLoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }else if (str.length()>20){
                        Toast.makeText(MerchantLoginActivity.this, "密码过长，请输入长度20以内的字符", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //获取控件
    private void getView(){
        btn_toRegister = findViewById(R.id.merchant_loginToRegister_btn);
        btn_login = findViewById(R.id.merchant_login_btn);
        merchant_userName = findViewById(R.id.merchant_login_username);
        merchant_userPwd = findViewById(R.id.merchant_login_pwd);
    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_login_btn://登录
                    postLoginMerchant(merchant_userName.getText().toString().trim(),merchant_userPwd.getText().toString().trim());
                    break;
                case R.id.merchant_loginToRegister_btn://注册
                    intent.setClass(getApplicationContext(),MerchantRegisterActivity.class);
                    startActivity(intent);
                    break;

            }
        }
    }

    //POST请求，带有封装请求参数的请求方式
    private void postLoginMerchant(final String merchantAccount, final String merchantPassword){
        new Thread(){
            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("merchantAccount",merchantAccount);
                builder.add("merchantPassword",merchantPassword);
                FormBody body = builder.build();
                Request request = new Request.Builder().post(body).url(UrlAddress.url+"merchantLoginCheck.action").build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {//2）异步请求

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("李垚：：：：","请求成功");
                        String a = response.body().string();
                        Log.i("李垚：：检测返回的数据：", a);
                        Message message = Message.obtain();
                        message.what =1;
                        Bundle bundle =new Bundle();
                        bundle.putString("merchant",a);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("李垚：：：：：", "请求失败");
                    }
                });
            }
        }.start();
    }

    private void insertMerchantToSql(Merchant merchant){
        sqLiteDatabase = merchantTokenSql.getReadableDatabase();
        Log.i("hzl","logininsert"+merchant.toString());

        ContentValues cv = new ContentValues();
        cv.put("merchantaccount",merchant.getMerchantAccount());
        cv.put("merchantpassword",merchant.getMerchantPassword());
        cv.put("merchanttoken",merchant.getMerchantToken());
        long insert =  sqLiteDatabase.insert("merchant",null,cv);
        Log.i("insert111",insert+"");
        Log.i("hzl","merchant插入成功");
    }









}
