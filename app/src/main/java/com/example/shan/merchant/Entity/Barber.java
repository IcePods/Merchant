package com.example.shan.merchant.Entity;

import java.io.Serializable;

/**
 * Created by shan on 2018/5/30.
 * 理发师
 */

public class Barber implements Serializable{
    private int BarberId;
    private int BarberImg; //理发师头像
    private String BarberName;//理发师姓名
    //用户与理发师主键关联映射
    private Users user;

    public Barber() {
    }

    public Barber(int barberId, int barberImg, String barberName) {
        this.BarberId = barberId;
        BarberImg = barberImg;
        BarberName = barberName;
    }

    public int getBarberId() {
        return BarberId;
    }

    public void setBarberId(int barberId) {
        BarberId = barberId;
    }

    public int getBarberImg() {
        return BarberImg;
    }

    public void setBarberImg(int barberImg) {
        BarberImg = barberImg;
    }

    public String getBarberName() {
        return BarberName;
    }

    public void setBarberName(String barberName) {
        BarberName = barberName;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
