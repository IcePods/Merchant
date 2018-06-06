package com.example.shan.merchant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shan.merchant.R;

public class MerchantProductionChangeActivity extends AppCompatActivity {
    private ImageButton Backc;
    private EditText WorkNamec;
    private EditText WorkDescriptionc;
    private EditText WorkPricec;
    private ImageView WorkPicturec;
    private TextView WorkHeadc;
    private Button Commitc;
    private MyClickListener myClickListener;
    private ArrayAdapter adapter;
    private Spinner spinnerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_production_change);
        //获取控件
        findview();
        adapter =ArrayAdapter.createFromResource(this,R.array.hairtype,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerc.setAdapter(adapter);
        spinnerc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ztl","你选择了"+adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Backc.setOnClickListener(myClickListener);
        Commitc.setOnClickListener(myClickListener);
    }

    private void findview() {
        Backc = findViewById(R.id.merchant_production_change_back);
        WorkNamec = findViewById(R.id.production_change_title);
        WorkDescriptionc = findViewById(R.id.production_change_description);
        WorkPricec = findViewById(R.id.production_change_price);
        WorkPicturec = findViewById(R.id.production_change_picture);
        WorkHeadc = findViewById(R.id.change_production_head);
        Commitc = findViewById(R.id.commit_production_change);
        spinnerc = findViewById(R.id.production_change_type);
        myClickListener = new MyClickListener();
    }
    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            switch (view.getId()){
                case R.id.merchant_production_change_back:
                    finish();
                    break;
                case R.id.commit_production_change:
                    intent.setClass(getApplicationContext(),MerchantProductionListActivity.class);
                    startActivity(intent);
                    //结束当前页面
                    finish();
                    //结束详情页面
                    MerchantProductionDetailActivity.instance.finish();
                    //结束发型列表页面
                    MerchantProductionListActivity.prolist.finish();
                    break;
            }
        }
    }
}
