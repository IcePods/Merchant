package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shan.merchant.R;

public class MainActivity extends AppCompatActivity {
    private Button informationButton;//店铺管理按钮
    private Button employeesButton;//店员管理按钮
    private Button appointmentButton;//预约管理按钮
    private Button activitysButton;//活动管理按钮
    private Button worksButton;//发布作品按钮
    private Button exitButton;//退出登录按钮
    private MyOnClickListener myListener;//监听器

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
        informationButton.setOnClickListener(myListener);
        employeesButton.setOnClickListener(myListener);
        appointmentButton.setOnClickListener(myListener);
        activitysButton.setOnClickListener(myListener);
        worksButton.setOnClickListener(myListener);
        exitButton.setOnClickListener(myListener);
    }

    //给控件赋值
    private void initParams() {
        informationButton = findViewById(R.id.merchant_home_information);
        employeesButton = findViewById(R.id.merchant_home_employees);
        appointmentButton = findViewById(R.id.merchant_home_appointment);
        activitysButton = findViewById(R.id.merchant_home_activitys);
        worksButton = findViewById(R.id.merchant_home_works);
        exitButton = findViewById(R.id.merchant_home_exit);
    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //1. 创建Intent对象
            Intent intent = new Intent();
            switch (v.getId()){
                //店铺管理
                case R.id.merchant_home_information:
                    //只实现跳转，跳转到店铺管理页面
                    //2. 指定跳转路线
                    intent.setClass(getApplicationContext(),MerchantInformationActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
                //店员管理
                case R.id.merchant_home_employees:
                    //只实现跳转，跳转到店员管理页面
                    //2. 指定跳转路线
                    intent.setClass(getApplicationContext(),MerchantEmployessActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
                //预约管理
                case R.id.merchant_home_appointment:
                    intent.setClass(getApplicationContext(),MerchantAppointmentActivity.class);
                    startActivity(intent);
                    break;
                //活动管理
                case R.id.merchant_home_activitys:
                    intent.setClass(getApplicationContext(),MerchantActivitysActivity.class);
                    startActivity(intent);
                    break;
                //发布作品
                case R.id.merchant_home_works:
                    intent.setClass(getApplicationContext(),MerchantProductionListActivity.class);
                    startActivity(intent);
                    break;
                //退出登录
                case R.id.merchant_home_exit:
                    intent.setClass(getApplicationContext(),MerchantLoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
}
