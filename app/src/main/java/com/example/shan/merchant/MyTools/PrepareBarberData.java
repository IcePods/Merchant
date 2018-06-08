package com.example.shan.merchant.MyTools;

import com.example.shan.merchant.Entity.Barber;
import com.example.shan.merchant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shan on 2018/5/30.
 * 理发师信息静态数据
 */

public class PrepareBarberData {
    public static List<Barber> prepareBarberInfo(){
        List<Barber> barberList = new ArrayList<>();
        //第一个理发师
        Barber barber1 = new Barber(0, R.mipmap.user_person_headimg,"理发师一");//,"资深理发师一，擅长理光头，擅长理光头，擅长理光头"
        barberList.add(barber1);
        //第二个理发师
        Barber barber2 = new Barber(1, R.mipmap.user_person_headimg,"理发师二");
        barberList.add(barber2);
        //第三个理发师
        Barber barber3 = new Barber(0, R.mipmap.user_person_headimg,"理发师三");
        barberList.add(barber3);
        //第四个理发师
        Barber barber4 = new Barber(0, R.mipmap.user_person_headimg,"理发师四");
        barberList.add(barber4);
        //第五个理发师
        Barber barber5 = new Barber(0, R.mipmap.user_person_headimg,"理发师五");
        barberList.add(barber5);
        //第六个理发师
        Barber barber6 = new Barber(0, R.mipmap.user_person_headimg,"理发师六");
        barberList.add(barber6);
        return barberList;
    }
}
