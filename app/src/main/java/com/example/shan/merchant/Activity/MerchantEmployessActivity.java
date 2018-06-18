package com.example.shan.merchant.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.shan.merchant.Adapter.BarberAdapter;
import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.MerchantTokenSql;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 店员管理页面
 */
public class MerchantEmployessActivity extends AppCompatActivity {
    private ImageButton backbutton;//返回按钮
    private Button addbutton;//添加店员按钮
    private MyOnClickListener myOnClickListener;//监听器
    private ListView lv;

    private List<Barber> barberList = new ArrayList<>(); //店员列表

    private String token="";
    private String merchantAccount;//账号
    private String merchantPassword;//密码

    private SQLiteDatabase database;//查询数据库
    OkHttpClient okHttpClient;
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/plain;charset=UTF-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_employess);

        selectMerchant();
        lv = findViewById(R.id.merchant_employees_list);
        backbutton = findViewById(R.id.merchant_employess_back);//返回按钮
        addbutton = findViewById(R.id.merchant_employess_add);//添加店员按钮

        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();

        //获取服务器数据
        initData();
        myOnClickListener = new MyOnClickListener();
        backbutton.setOnClickListener(myOnClickListener);
        addbutton.setOnClickListener(myOnClickListener);
    }

    private void initData() {

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    //店员列表
                    case 1:
                        Bundle bundle = msg.getData();
                        String m = bundle.getString("merchant");
                        Log.i("merchantaaaaaaa",m);
                        Gson gson = new Gson();
                        List<Barber> list = gson.fromJson(m,new TypeToken<List<Barber>>(){}.getType());
                        Log.i("listlistlist",""+list.size());
                        final BarberAdapter adapter = new BarberAdapter(getApplicationContext(), R.layout.item_activity_merchant_employess, list);
                        lv.setAdapter(adapter);
                        lv.setDivider(null);
                        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //携带数据跳转到另一个Activity，进行数据的更新操作
                                Intent intent = new Intent();
                                //指定跳转路线
                                intent.setClass(getApplicationContext(),MerchantEmployessChangeActivity.class);
                                //把点击的理发师对象添加到intent对象中去
                                Bundle bundle = new Bundle();
                                Barber barber = (Barber) adapter.getItem(position);
                                bundle.putSerializable("barberInfo",barber);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });*/
                        break;
                }
                super.handleMessage(msg);
            }
        };
        postGetMerchantBarber(merchantAccount,merchantPassword,handler);
    }

    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //返回按钮
                case R.id.merchant_employess_back:
                    //只销毁当前页面
                    finish();
                    break;
                //添加店员
                case R.id.merchant_employess_add:
                    Intent intent = new Intent();
                    //只实现跳转，跳转到添加店员页面
                    //2. 指定跳转路线
                    intent.setClass(getApplicationContext(),MerchantEmployessAddActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
            }
        }
    }
    //POST请求，带有封装请求参数的请求方式
    //获取店员列表
    private void postGetMerchantBarber(final String merchantAccount, final String merchantPassword, final Handler handler){
        new Thread(){
            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("merchantAccount",merchantAccount);
                builder.add("merchantPassword",merchantPassword);
                FormBody body = builder.build();
                Request request = new Request.Builder().post(body).url(UrlAddress.url+"getBarberByMerchant.action").build();
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

    //从本地数据库中获取登录店铺的账号密码
    private void selectMerchant(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("merchanttoken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        database =new MerchantTokenSql(this).getReadableDatabase();
        /*Cursor cursor = database.rawQuery("select * from user where usertoken = "+token,null);*/
        Cursor cursor = database.query("merchant",null,"merchanttoken"+"=?",new String[]{token},null,null,null);
        if(cursor.moveToLast()){
            merchantAccount = cursor.getString(cursor.getColumnIndex("merchantaccount"));
            merchantPassword = cursor.getString(cursor.getColumnIndex("merchantpassword"));
        }
    }

}
