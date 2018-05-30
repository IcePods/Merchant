package com.example.shan.merchant.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.shan.merchant.Adapter.AppointmentNotDoneFragmentAdapter;
import com.example.shan.merchant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantAppointmentNotDoneFragment extends Fragment {
    private ListView notDoneList;//未完成列表
    private Context context;


    public MerchantAppointmentNotDoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_appointment_not_done, container, false);
        notDoneList = view.findViewById(R.id.lv_appointment_notDone);
        final AppointmentNotDoneFragmentAdapter adapter = new AppointmentNotDoneFragmentAdapter(getActivity().getApplicationContext(),prepareDatas(),R.layout.item_appointment_not_done_layout);
        notDoneList.setAdapter(adapter);
        notDoneList.setDivider(null);


        return view;
    }

    private List<Map<String, Object>> prepareDatas() {
        List<Map<String, Object>> notdoneAppointments = new ArrayList<>();
        Map<String,Object> appointment1 = new HashMap<>();
        appointment1.put("notdone_username","李梅梅");
        appointment1.put("notdone_tel","13443225322");
        appointment1.put("notdone_time","2018-5-30 9:00");
        notdoneAppointments.add(appointment1);

        Map<String,Object> appointment2 = new HashMap<>();
        appointment2.put("notdone_username","李磊");
        appointment2.put("notdone_tel","15732118890");
        appointment2.put("notdone_time","2018-5-30 9:00");
        notdoneAppointments.add(appointment2);

        Map<String,Object> appointment3 = new HashMap<>();
        appointment3.put("notdone_username","John");
        appointment3.put("notdone_tel","15233446642");
        appointment3.put("notdone_time","2018-5-30 9:00");
        notdoneAppointments.add(appointment3);

        return  notdoneAppointments;

    }

}
