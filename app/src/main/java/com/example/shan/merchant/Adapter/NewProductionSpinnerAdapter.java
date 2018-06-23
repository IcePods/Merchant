package com.example.shan.merchant.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by shan on 2018/6/23.
 */

public class NewProductionSpinnerAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private int mLayout;
    private String [] mStringArray;
    public NewProductionSpinnerAdapter(Context context,int mLayout,String[] stringArray) {
        super(context, mLayout, stringArray);
        mContext = context; mStringArray=stringArray;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent,false);
        }
        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray[position]);
        tv.setTextSize(13);
        tv.setTextColor(0xff292929);
        return convertView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        //此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray[position]);
        tv.setTextSize(13);
        tv.setTextColor(0xff7F7F7F);
        tv.setGravity(Gravity.RIGHT);
        return convertView;
    }
}
