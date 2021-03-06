package com.example.shan.merchant.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.shan.merchant.Adapter.AppointmentNotDoneFragmentAdapter;
import com.example.shan.merchant.Entity.Appointment;
import com.example.shan.merchant.Entity.UrlAddress;
import com.example.shan.merchant.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantAppointmentNotDoneFragment extends Fragment {
    private ListView notDoneList;//未完成列表
    OkHttpClient okHttpClient = new OkHttpClient();
    private List<Appointment> appointList;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    String appointlist = bundle.getString("appoint");
                    Log.i("appoint",appointlist);
                    Gson gson = new Gson();
                    appointList = gson.fromJson(appointlist,new TypeToken<List<Appointment>>(){}.getType());
                    Log.i("appoint",appointList.toString());
                    final AppointmentNotDoneFragmentAdapter adapter = new AppointmentNotDoneFragmentAdapter(getActivity().getApplicationContext(),appointList,R.layout.item_appointment_not_done_layout);
                    notDoneList.setAdapter(adapter);
                    notDoneList.setDivider(null);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public MerchantAppointmentNotDoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_merchant_appointment_not_done, container, false);
        notDoneList = view.findViewById(R.id.lv_appointment_notDone);
        getAppointment();
        return view;
    }
    public void getAppointment(){
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("merchanttoken", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("token","");
        Log.i("hzl",token);
        new Thread(){
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                FormBody.Builder builder1 = new FormBody.Builder();
                builder1.add("Appointment_state","进行中");
                FormBody body = builder1.build();
                final Request request = builder.header("MerchantTokenSql",token).post(body).url(UrlAddress.url+"getAppointmentByMerchant.action").build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    String a = response.body().string();
                    Log.i("appoint",a);
                    Message message = Message.obtain();
                    message.what = 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("appoint",a);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
