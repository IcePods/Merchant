package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shan.merchant.Entity.Shop;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.MyTools.UploadPictureUtil;
import com.example.shan.merchant.R;
import com.google.gson.Gson;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {
    private LinearLayout informationLL;//店铺管理
    private Button informationBtn;
    private LinearLayout employeesLL;//店员管理
    private Button employeesBtn;
    private LinearLayout appointmentLL;//预约管理
    private Button appointmentBtn;
    private LinearLayout activitysLL;//活动管理
    private Button activitysBtn;
    private LinearLayout worksLL;//发布作品
    private Button worksBtn;
    private LinearLayout exitLL;//退出登录
    private Button exitBtn;
    private MyOnClickListener myListener;//监听器
    private long fistKeyDownTime;//记录第一次按下返回的时间（毫秒数）
    //获取shop信息地址
    private String getShopUrl = UrlAddress.url + "getShop.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initParams();
        //绑定监听器
        myListener = new MyOnClickListener();
        setMyOnClickListener(myListener);
    }
    //绑定监听器
    private void setMyOnClickListener(MyOnClickListener myListener) {
        informationLL.setOnClickListener(myListener);
        informationBtn.setOnClickListener(myListener);
        employeesLL.setOnClickListener(myListener);
        employeesBtn.setOnClickListener(myListener);
        appointmentLL.setOnClickListener(myListener);
        appointmentBtn.setOnClickListener(myListener);
        activitysLL.setOnClickListener(myListener);
        activitysBtn.setOnClickListener(myListener);
        worksLL.setOnClickListener(myListener);
        worksBtn.setOnClickListener(myListener);
        exitLL.setOnClickListener(myListener);
        exitBtn.setOnClickListener(myListener);
    }

    //给控件赋值
    private void initParams() {
        informationLL = findViewById(R.id.merchant_home_information);
        informationBtn = findViewById(R.id.merchant_home_information_btn);
        employeesLL = findViewById(R.id.merchant_home_employees);
        employeesBtn = findViewById(R.id.merchant_home_employees_btn);
        appointmentLL = findViewById(R.id.merchant_home_appointment);
        appointmentBtn = findViewById(R.id.merchant_home_appointment_btn);
        activitysLL = findViewById(R.id.merchant_home_activitys);
        activitysBtn = findViewById(R.id.merchant_home_activitys_btn);
        worksLL = findViewById(R.id.merchant_home_works);
        worksBtn = findViewById(R.id.merchant_home_works_btn);
        exitLL = findViewById(R.id.merchant_home_exit);
        exitBtn = findViewById(R.id.merchant_home_exit_btn);
    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //1. 创建Intent对象
            Intent intent = new Intent();
            switch (v.getId()){
                //店铺管理
                case R.id.merchant_home_information_btn:
                case R.id.merchant_home_information:
                    //只实现跳转，跳转到店铺管理页面
                    //2. 指定跳转路线
                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            Bundle bundle = msg.getData();
                            String shopStr = bundle.getString("string");
                            Gson gson = new Gson();
                            Shop shop = gson.fromJson(shopStr, Shop.class);
                            Intent inten = new Intent();
                            inten.putExtra("shop",shop);
                            inten.setClass(getApplicationContext(),MerchantInformationActivity.class);
                            //3. 进行跳转
                            startActivity(inten);
                        }
                    };
                    UploadPictureUtil util = new UploadPictureUtil();
                    String token = util.getToken(getApplicationContext());
                    util.requestServer(getShopUrl,null,token,handler);
                    break;
                //店员管理
                case R.id.merchant_home_employees:
                    //只实现跳转，跳转到店员管理页面
                    //2. 指定跳转路线
                    intent.setClass(getApplicationContext(),MerchantEmployessActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
                case R.id.merchant_home_employees_btn:
                    intent.setClass(getApplicationContext(),MerchantEmployessActivity.class);
                    startActivity(intent);
                    break;
                //预约管理
                case R.id.merchant_home_appointment:
                    intent.setClass(getApplicationContext(),MerchantAppointmentActivity.class);
                    startActivity(intent);
                    break;
                case R.id.merchant_home_appointment_btn:
                    intent.setClass(getApplicationContext(),MerchantAppointmentActivity.class);
                    startActivity(intent);
                    break;
                //活动管理
                case R.id.merchant_home_activitys:
                    intent.setClass(getApplicationContext(),MerchantActivitiesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.merchant_home_activitys_btn:
                    intent.setClass(getApplicationContext(),MerchantActivitiesActivity.class);
                    startActivity(intent);
                    break;
                //发布作品
                case R.id.merchant_home_works:
                    intent.setClass(getApplicationContext(),MerchantProductionListActivity.class);
                    startActivity(intent);
                    break;
                case R.id.merchant_home_works_btn:
                    intent.setClass(getApplicationContext(),MerchantProductionListActivity.class);
                    startActivity(intent);
                    break;
                //退出登录
                case R.id.merchant_home_exit:
                    File file = new File(getApplication().getFilesDir().getParent()+"/shared_prefs/merchanttoken.xml");
                    file.delete();
                    Log.i("gugu","成功删除");
                    JPushInterface.setAlias(getApplicationContext(),0,"exitLogin");
                    intent.setClass(getApplicationContext(),MerchantLoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.merchant_home_exit_btn:
                    File file1 = new File(getApplication().getFilesDir().getParent()+"/shared_prefs/merchanttoken.xml");
                    file1.delete();
                    Log.i("gugu","成功删除");
                    JPushInterface.setAlias(getApplicationContext(),0,"exitLogin");
                    intent.setClass(getApplicationContext(),MerchantLoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
    /**
     * 摁下两次返回键退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - fistKeyDownTime>2000) {//第一次按键
                //提示再按一次退出系统
                Toast.makeText(MainActivity.this,
                        "再按一次，退出系统",
                        Toast.LENGTH_SHORT).show();
                //记录下当前按键的时间
                fistKeyDownTime = System.currentTimeMillis();
            }else {//第二次按键
                //退出系统
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
