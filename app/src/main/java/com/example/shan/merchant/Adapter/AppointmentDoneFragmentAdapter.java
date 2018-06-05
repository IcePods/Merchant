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

public class AppointmentDoneFragmentAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //声明数据源
    private List<Map<String, Object>> doneAppointmentList;
    //声明列表项的布局itemID
    private int item_layout_id;

    public AppointmentDoneFragmentAdapter(Context mContext, List<Map<String, Object>> doneAppointmentList, int item_layout_id) {
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
        TextView done_content = convertView.findViewById(R.id.appointment_done_content);
        TextView done_time = convertView.findViewById(R.id.appointment_done_time);

        //利用传递的数据源给相应的控件对象赋值
        Map<String,Object> doneAppointments = doneAppointmentList.get(i);
        done_username.setText((String)doneAppointments.get("done_username"));
        done_tel.setText((String)doneAppointments.get("done_tel"));
        done_content.setText((String)doneAppointments.get("done_content"));
        done_time.setText((String)doneAppointments.get("done_time"));



        return convertView;
    }
}
