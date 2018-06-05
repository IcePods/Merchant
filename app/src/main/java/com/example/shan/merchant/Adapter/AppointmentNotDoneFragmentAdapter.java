package com.example.shan.merchant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shan.merchant.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class AppointmentNotDoneFragmentAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //声明数据源
    private List<Map<String, Object>> notDoneAppointmentList;
    //声明列表项的布局itemID
    private int item_layout_id;

    public AppointmentNotDoneFragmentAdapter(Context mContext, List<Map<String, Object>> notDoneAppointmentList, int item_layout_id) {
        this.mContext = mContext;
        this.notDoneAppointmentList = notDoneAppointmentList;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        return notDoneAppointmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return notDoneAppointmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (null == convertView && null != mContext) {
            //获取子项目item的布局文件
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //布局文件赋值给convertView参数
            convertView = mInflater.inflate(item_layout_id, null);
        }

        //获取布局文件中的控件对象
        TextView notdone_username = convertView.findViewById(R.id.appointment_notdone_username);
        TextView notdone_tel = convertView.findViewById(R.id.appointment_notdone_tel);
        TextView notdone_content =convertView.findViewById(R.id.appointment_notdone_content);
        TextView notdone_time = convertView.findViewById(R.id.appointment_notdone_time);

        //利用传递的数据源给相应的控件对象赋值
        Map<String,Object> notDoneAppointments = notDoneAppointmentList.get(position);
        notdone_username.setText((String)notDoneAppointments.get("notdone_username"));
        notdone_tel.setText((String)notDoneAppointments.get("notdone_tel"));
        notdone_content.setText((String)notDoneAppointments.get("notdone_content"));
        notdone_time.setText((String)notDoneAppointments.get("notdone_time"));



        return convertView;
    }
}
