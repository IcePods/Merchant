package com.example.shan.merchant.Adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.shan.merchant.Entity.Appointment;
import com.example.shan.merchant.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public class AppointmentDoneFragmentAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //声明数据源
    private List<Appointment> doneAppointmentList;
    //声明列表项的布局itemID
    private int item_layout_id;
    private Appointment doneAppointments;

    public AppointmentDoneFragmentAdapter(Context mContext, List<Appointment> doneAppointmentList, int item_layout_id) {
        this.mContext = mContext;
        this.doneAppointmentList = doneAppointmentList;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        return doneAppointmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return doneAppointmentList.get(i);
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
        TextView done_username = convertView.findViewById(R.id.appointment_done_username);
        TextView done_tel = convertView.findViewById(R.id.appointment_done_tel);
        TextView done_hairstyle = convertView.findViewById(R.id.appointment_done_hairstyle);
        TextView done_barber = convertView.findViewById(R.id.appointment_done_barber);
        TextView done_time = convertView.findViewById(R.id.appointment_done_time);
        TextView done_state = convertView.findViewById(R.id.appointment_done_state);

        //利用传递的数据源给相应的控件对象赋值
        doneAppointments = doneAppointmentList.get(i);
        done_username.setText(doneAppointments.getAppoint_username());
        done_tel.setText(doneAppointments.getAppoint_phone());
        done_hairstyle.setText(doneAppointments.getAppoint_hairStyle().getHairstyleName());
        done_barber.setText(doneAppointments.getAppoint_barber());
        done_time.setText(doneAppointments.getAppoint_time());
        done_state.setText(doneAppointments.getAppoint_state());

        return convertView;
    }
}
