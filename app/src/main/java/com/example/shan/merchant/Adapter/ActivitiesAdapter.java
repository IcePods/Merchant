package com.example.shan.merchant.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shan.merchant.Entity.Activity;
import com.example.shan.merchant.R;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class ActivitiesAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //声明数据源
    private List<Activity> activitiesList;
    //声明列表项的布局itemID
    private int item_layout_id;
    private Activity activity;

    public ActivitiesAdapter(Context mContext, List<Activity> activitiesList, int item_layout_id) {
        this.mContext = mContext;
        this.activitiesList = activitiesList;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        return activitiesList.size();
    }

    @Override
    public Object getItem(int i) {
        return activitiesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (null == convertView && null != mContext) {
            //获取子项目item的布局文件
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //布局文件赋值给convertView参数
            convertView = mInflater.inflate(item_layout_id, null);
        }
        //获取布局文件中的控件对象
        TextView activity_name = convertView.findViewById(R.id.item_merchant_activity_name);
        TextView activity_content = convertView.findViewById(R.id.item_merchant_activity_content);
        TextView activity_start_time = convertView.findViewById(R.id.item_merchant_activity_start_time);
        TextView activity_end_time = convertView.findViewById(R.id.item_merchant_activity_end_time);

        //利用传递的数据源给相应的控件对象赋值
        activity = activitiesList.get(i);
        activity_name.setText(activity.getActivityName());
        //Log.i("aaaaaaaaaaaaa",activity.getActivity_name());
        activity_content.setText(activity.getActivityContent());
        activity_start_time.setText((CharSequence) activity.getActivityStartTime());
        activity_end_time.setText((CharSequence) activity.getActivityEndTime());

        return convertView;
    }
}
