package com.example.shan.merchant.Activity;

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
    private List<Works> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_production_list);
        //获取控件
        findview();
        list = creakDatasource();
        ListView lv = findViewById(R.id.merchant_production_list);
        myClickListener = new MyClickListener();
        final MerchantProductionListAdopter adopter1 = new MerchantProductionListAdopter(this,R.layout.item_activity_merchant_production,list);
        lv.setAdapter(adopter1);
        lv.setDivider(null);
        backBtn.setOnClickListener(myClickListener);
        addBtn.setOnClickListener(myClickListener);
    }

    private void findview() {
        backBtn = findViewById(R.id.merchant_production_list_back);
        addBtn = findViewById(R.id.create_new_production);
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.merchant_production_list_back:
                    finish();
                    break;
                case R.id.create_new_production:
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
