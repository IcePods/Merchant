package com.example.shan.merchant.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shan.merchant.R;

public class MainActivity extends AppCompatActivity {
    private Button informationButton;//店铺管理按钮
    private Button employeesButton;//店员管理按钮
    private Button conventionButton;//预约管理按钮
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
        informationButton.setOnClickListener(myListener);
        employeesButton.setOnClickListener(myListener);
        conventionButton.setOnClickListener(myListener);
        activitysButton.setOnClickListener(myListener);
        worksButton.setOnClickListener(myListener);
        exitButton.setOnClickListener(myListener);
    }
    //给控件赋值
    private void initParams() {
        informationButton = findViewById(R.id.merchant_home_information);
        employeesButton = findViewById(R.id.merchant_home_employees);
        conventionButton = findViewById(R.id.merchant_home_convention);
        activitysButton = findViewById(R.id.merchant_home_activitys);
        worksButton = findViewById(R.id.merchant_home_works);
        exitButton = findViewById(R.id.merchant_home_exit);
    }

    //监听器类
    private class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //店铺管理
                case R.id.merchant_home_information:
                    break;
                //店员管理
                case R.id.merchant_home_employees:
                    break;
                //预约管理
                case R.id.merchant_home_convention:
                    break;
                //活动管理
                case R.id.merchant_home_activitys:
                    break;
                //发布作品
                case R.id.merchant_home_works:
                    break;
                //退出登录
                case R.id.merchant_home_exit:
                    break;
            }
        }
    }
}
