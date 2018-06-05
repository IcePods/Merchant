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
 * Created by Administrator on 2018/5/29 0029.
 */

public class AppointmentWaitingFragmentAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //声明数据源
    private List<Map<String, Object>> waitingAppointmentList;
    //声明列表项的布局itemID
    private int item_layout_id;

    public AppointmentWaitingFragmentAdapter(Context mContext, List<Map<String, Object>> appointments, int item_layout_id) {
        this.mContext = mContext;
        this.waitingAppointmentList = appointments;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        return waitingAppointmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return waitingAppointmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView && null != mContext) {
            //获取子项目item的布局文件
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //布局文件赋值给convertView参数
            convertView = mInflater.inflate(item_layout_id, null);
        }

        //获取布局文件中的控件对象
        TextView waiting_username = convertView.findViewById(R.id.appointment_waiting_username);
        TextView waiting_tel = convertView.findViewById(R.id.appointment_waiting_tel);
        TextView waiting_content = convertView.findViewById(R.id.appointment_waiting_content);
        TextView waiting_time = convertView.findViewById(R.id.appointment_waiting_time);

        //利用传递的数据源给相应的控件对象赋值
        Map<String,Object> waitingAppointments = waitingAppointmentList.get(position);
        waiting_username.setText((String)waitingAppointments.get("waiting_username"));
        waiting_tel.setText((String)waitingAppointments.get("waiting_tel"));
        waiting_content.setText((String)waitingAppointments.get("waiting_content"));
        waiting_time.setText((String)waitingAppointments.get("waiting_time"));



        return convertView;
    }
}
