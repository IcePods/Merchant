package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.shan.merchant.Adapter.BarberAdapter;
import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.MyTools.PrepareBarberData;
import com.example.shan.merchant.R;

import java.util.ArrayList;
import java.util.List;

public class MerchantEmployessActivity extends AppCompatActivity {
    private ImageButton backbutton;//返回按钮
    private Button addbutton;//添加店员按钮
    private MyOnClickListener myOnClickListener;//监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_employess);

        ListView lv = findViewById(R.id.merchant_employees_list);
        backbutton = findViewById(R.id.merchant_employess_back);//返回按钮
        addbutton = findViewById(R.id.merchant_employess_add);//添加店员按钮

        myOnClickListener = new MyOnClickListener();

        final BarberAdapter adapter = new BarberAdapter(this,R.layout.item_activity_merchant_employess, PrepareBarberData.prepareBarberInfo());
        lv.setAdapter(adapter);
        lv.setDivider(null);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });
        backbutton.setOnClickListener(myOnClickListener);
        addbutton.setOnClickListener(myOnClickListener);
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
}
