package com.example.shan.merchant.Entity;

import java.io.Serializable;

/**
 * Created by 天龙 on 2018/5/30.
 */

public class Works implements Serializable {
    private int id;
    private int WorkImg;
    private String WorkTitle;
    private String WorkPrice;
    private String WorkDescription;
    public Works(){}
    public Works(int id,int workImg,String workTitle,String workPrice,String workDescription){
        this.id = id;
        this.WorkImg = workImg;
        this.WorkTitle = workTitle;
        this.WorkPrice = workPrice;
        this.WorkDescription = workDescription;
    }

    public int getId() {
        return id;
    }

    public int getWorkImg() {
        return WorkImg;
    }

    public void setWorkImg(int workImg) {
        WorkImg = workImg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkTitle() {
        return WorkTitle;
    }

    public void setWorkTitle(String workTitle) {
        WorkTitle = workTitle;
    }

    public String getWorkPrice() {
        return WorkPrice;
    }

    public void setWorkPrice(String workPrice) {
        WorkPrice = workPrice;
    }

    public String getWorkDescription() {
        return WorkDescription;
    }

    public void setWorkDescription(String workDescription) {
        WorkDescription = workDescription;
    }
}
