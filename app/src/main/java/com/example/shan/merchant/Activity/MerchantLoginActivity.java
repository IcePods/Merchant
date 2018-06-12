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
import com.example.shan.merchant.Entity.Users;
import com.example.shan.merchant.MyTools.MerchantTokenSql;
import com.example.shan.merchant.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
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
    private MerchantTokenSql userTokenSql = new MerchantTokenSql(this);//数据库连接
    private SQLiteDatabase sqLiteDatabase;
    private OkHttpClient okHttpClient;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/plain;charset=UTF-8");


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
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
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }else {
//                        errorMessage.setText("用户明密码错误");
                        Toast.makeText(getApplicationContext(),"用户名密码错误",Toast.LENGTH_SHORT).show();
                        merchant_userName.setText("");
                        merchant_userPwd.setText("");
                    }
                    break;
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
                try {
                    Response response = call.execute();
                    String a = response.body().string();
                    Message message = Message.obtain();
                    message.what =1;
                    Bundle bundle =new Bundle();
                    bundle.putString("merchant",a);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void insertMerchantToSql(Merchant merchant){
        sqLiteDatabase = userTokenSql.getReadableDatabase();
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
