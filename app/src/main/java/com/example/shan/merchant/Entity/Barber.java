package com.example.shan.merchant.Entity;

import java.io.Serializable;

/**
 * Created by shan on 2018/5/30.
 * 理发师
 */

public class Barber implements Serializable{
    private int barberId;
    private int barberImg; //理发师头像
    private String barberName;//理发师姓名
    //用户与理发师主键关联映射
    private Users user;

    public Barber() {
    }

    public Barber(int barberId, int barberImg, String barberName) {
        this.barberId = barberId;
        this.barberImg = barberImg;
        this.barberName = barberName;
    }

    public int getBarberId() {
        return barberId;
    }

    public void setBarberId(int barberId) {
        this.barberId = barberId;
    }

    public int getBarberImg() {
        return barberImg;
    }

    public void setBarberImg(int barberImg) {
        this.barberImg = barberImg;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
