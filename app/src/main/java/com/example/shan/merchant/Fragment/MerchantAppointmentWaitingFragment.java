package com.example.shan.merchant.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shan.merchant.Adapter.AppointmentWaitingFragmentAdapter;
import com.example.shan.merchant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantAppointmentWaitingFragment extends Fragment {
    private ListView waitingList;//待处理列表
    private Context context;


    public MerchantAppointmentWaitingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_appointment_waiting, container, false);
        waitingList = view.findViewById(R.id.lv_appointment_waiting);
        final AppointmentWaitingFragmentAdapter adapter = new AppointmentWaitingFragmentAdapter(getActivity().getApplicationContext(),prepareDatas(),R.layout.item_appointment_waiting_layout);
        waitingList.setAdapter(adapter);
        waitingList.setDivider(null);
        //待处理item点击方法
        waitingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        return view;
    }

    private List<Map<String, Object>> prepareDatas() {
        List<Map<String, Object>> waitingAppointments = new ArrayList<>();
        Map<String,Object> appointment1 = new HashMap<>();
        appointment1.put("waiting_username","李梅梅");
        appointment1.put("waiting_tel","13443225322");
        appointment1.put("waiting_content","剪发");
        appointment1.put("waiting_time","2018-5-30 9:00");
        waitingAppointments.add(appointment1);

        Map<String,Object> appointment2 = new HashMap<>();
        appointment2.put("waiting_username","李磊");
        appointment2.put("waiting_tel","15732118890");
        appointment2.put("waiting_content","剪发");
        appointment2.put("waiting_time","2018-5-30 9:00");
        waitingAppointments.add(appointment2);

        Map<String,Object> appointment3 = new HashMap<>();
        appointment3.put("waiting_username","John");
        appointment3.put("waiting_tel","15233446642");
        appointment3.put("waiting_content","剪发");
        appointment3.put("waiting_time","2018-5-30 9:00");
        waitingAppointments.add(appointment3);

        Map<String,Object> appointment4 = new HashMap<>();
        appointment4.put("waiting_username","John");
        appointment4.put("waiting_tel","15233446642");
        appointment4.put("waiting_content","剪发");
        appointment4.put("waiting_time","2018-5-30 9:00");
        waitingAppointments.add(appointment4);

        Map<String,Object> appointment5 = new HashMap<>();
        appointment5.put("waiting_username","John");
        appointment5.put("waiting_tel","15233446642");
        appointment5.put("waiting_content","剪发");
        appointment5.put("waiting_time","2018-5-30 9:00");
        waitingAppointments.add(appointment5);



        return  waitingAppointments;

    }
}

