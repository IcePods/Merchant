package com.example.shan.merchant.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 问题
 * 在id为merchant_employess_add_ll_pic的LinearLayout中，如未上传照片 将CircleImageView设为空 显示“请选择”的TextView
 * 如上传照片 则将TextView的内容设为空  考虑点击事件绑定在imageButton还是同时绑定在TextView
 */
public class MerchantEmployessAddActivity extends AppCompatActivity {
    private Button backbutton;//返回按钮
    private Button submitbutton;//提交按钮

    private EditText edit_account;//要添加店员的用户账号
    private EditText edit_password;//要添加店员的用户密码

    private MyOnClickListener myOnClickListener;//监听器
    OkHttpClient okHttpClient = new OkHttpClient();
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/plain;charset=UTF-8");

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    String flag = bundle.getString("addbarberResponse");
                    Log.i("uuuuuuuuuuuuuu",flag);
                    if(flag.equals("success")){
                        Toast.makeText(getApplicationContext(),"理发师添加成功，请刷新页面",Toast.LENGTH_SHORT).show();
                        finish();
                    }else if(flag.equals("AddFalse")){
                        Toast.makeText(getApplicationContext(),"理发师添加失败",Toast.LENGTH_SHORT).show();
                    }else if(flag.equals("AlreadyExistsInBarber")){
                        Toast.makeText(getApplicationContext(),"添加失败，用户不可添加，可能存在在其他店铺中",Toast.LENGTH_SHORT).show();
                    }else if(flag.equals("AccountOrPassWordFalse")){
                        Toast.makeText(getApplicationContext(),"添加失败，请检查您的用户名或者密码是否正确",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"添加失败，请删除用户所有收藏店铺后重试",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

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
        edit_account = findViewById(R.id.merchant_employess_add_useraccount);
        edit_password = findViewById(R.id.merchant_employess_add_userpassword);
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
                    addBarber(edit_account.getText().toString().trim(),edit_password.getText().toString().trim());
                    break;
            }
        }
    }
    /**
     * 添加店员
     * */
    public void addBarber(String account,String password){
        SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences("merchanttoken", Context.MODE_PRIVATE);
        String merchantToken = sharedPreferences.getString("token","");
        String merchantAccount = sharedPreferences.getString("merchantAccount","");
        String merchantPassword = sharedPreferences.getString("merchantPassword","");
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("UserAccount",account);//要添加的店员的用户账号
        builder.add("UserPassword",password);
        builder.add("merchantAccount",merchantAccount);//店铺账号
        builder.add("merchantPassword",merchantPassword);
        // 先查出该用户 在给该用户新建一个barber（数据库中），建立关联 再把barber添加到shop中
        final FormBody body = builder.build();
        Request request = new Request.Builder().header("MerchantTokenSQL",merchantToken).post(body).url(UrlAddress.url+"addBarber.action").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String flag = response.body().string();
                Log.i("addbarberResponse",flag);
                Message message = Message.obtain();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("addbarberResponse",flag);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });
    }
}
