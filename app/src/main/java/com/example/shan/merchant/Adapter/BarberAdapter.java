package com.example.shan.merchant.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by shan on 2018/5/30.
 */

public class BarberAdapter extends BaseAdapter{
    private Context mContext;
    private int mLayout;
    private List<Barber> barbers;

    public BarberAdapter(Context mContext, int mLayout, List<Barber> barbers) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.barbers = barbers;
    }

    //数据源的数量
    @Override
    public int getCount() {
        return barbers.size();
    }

    //返回选择的item项的数据
    @Override
    public Object getItem(int position) {
        return barbers.get(position);
    }

    //返回当前选择了第几个item项
    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回item的布局视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView && null != mContext) {
            //获取子项目item的布局文件
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
        }
        //获取布局文件中的控件对象
        CircleImageView img = convertView.findViewById(R.id.merchant_employess_item_picture);
        TextView name = convertView.findViewById(R.id.merchant_employess_item_name);
        TextView introduce = convertView.findViewById(R.id.merchant_employess_item_introduce);

        //利用传递的数据源给相应的控件对象赋值
        Barber barber = barbers.get(position);
        Log.i("barberaaaaaaa",barber.getBarberName());
        img.setImageResource(R.mipmap.ic_launcher);
        String str = "理发师理发师，擅长理光头";
        if(str.length() > 10){
            str = str.substring(0,10)+"...";
        }
        name.setText(barber.getBarberName());
        introduce.setText(str);
        //返回子项目布局视图
        return convertView;
    }
}
