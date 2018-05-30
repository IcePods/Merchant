package com.example.shan.merchant.Entity;

import java.io.Serializable;

/**
 * Created by shan on 2018/5/30.
 * 理发师
 */

public class Barber implements Serializable{
    private int id;
    private int BarberImg; //理发师头像
    private String BarberName;//理发师姓名
    private String BarberIntro;//理发师简介

    public Barber() {
    }

    public Barber(int id, int barberImg, String barberName, String barberIntro) {
        this.id = id;
        BarberImg = barberImg;
        BarberName = barberName;
        BarberIntro = barberIntro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBarberIntro() {
        return BarberIntro;
    }

    public void setBarberIntro(String barberIntro) {
        BarberIntro = barberIntro;
    }
}
