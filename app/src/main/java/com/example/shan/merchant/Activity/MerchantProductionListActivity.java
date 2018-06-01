package com.example.shan.merchant.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.shan.merchant.Adapter.MerchantProductionListAdopter;
import com.example.shan.merchant.Entity.Works;
import com.example.shan.merchant.R;

import java.util.ArrayList;
import java.util.List;

public class MerchantProductionListActivity extends AppCompatActivity {
    private ImageButton backBtn;
    private Button addBtn;
    private MyClickListener myClickListener;
    private ListView lv;
    private List<Works> list = new ArrayList<>();
    public static MerchantProductionListActivity prolist = null;//用于finish页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_production_list);
        prolist = this;
        //获取控件
        findview();
        //获取数据源
        list = creakDatasource();
        //获取监听器
        myClickListener = new MyClickListener();
        final MerchantProductionListAdopter adopter = new MerchantProductionListAdopter(this,R.layout.item_activity_merchant_production,list);
        lv.setAdapter(adopter);
        lv.setDivider(null);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //携带数据跳转到另一个Activity，进行数据的更新操作
                Intent intent = new Intent();
                //指定跳转路线
                intent.setClass(getApplicationContext(),MerchantProductionDetailActivity.class);

                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(myClickListener);
        addBtn.setOnClickListener(myClickListener);
    }

    private void findview() {
        backBtn = findViewById(R.id.merchant_production_list_back);
        addBtn = findViewById(R.id.create_new_production);
        lv = findViewById(R.id.merchant_production_list);
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_production_list_back:
                    finish();
                    break;
                case R.id.create_new_production:
                    intent.setClass(getApplicationContext(),MerchantNewProductionActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
    private List<Works> creakDatasource(){
        List<Works> works = new ArrayList<>();
        Works works1 = new Works(0,R.mipmap.marcel,"经典烫发","128","经典烫发，可美了，可漂亮了，可好看了");
        works.add(works1);
        Works works2 = new Works(1,R.mipmap.marcel,"经典染发","88","经典染发，可美了，可漂亮了，可好看了");
        works.add(works2);
        Works works3 = new Works(2,R.mipmap.marcel,"经典理发","28","经典理发，可美了，可漂亮了，可好看了");
        works.add(works3);
        return works;
    }
}
