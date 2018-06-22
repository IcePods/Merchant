package com.example.shan.merchant.Entity;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by shan on 2018/5/18.
 * 发型实体类
 */

public class HairStyle implements Serializable{
    private int hairstyleId;//发型id
    private String hairstyleName;//发型名称
    private String hairstylePicture;//发型图片地址
    private String hairstyleIntroduce;//发型简介
    private String hairstyleType; //发型类型
    private Set<HairStyleDetail> hairStyleDetailSet;//发型详情
    private Shop shop; //店铺

    public int getHairstyleId() {
        return hairstyleId;
    }

    public void setHairstyleId(int hairstyleId) {
        this.hairstyleId = hairstyleId;
    }

    public String getHairstyleName() {
        return hairstyleName;
    }

    public void setHairstyleName(String hairstyleName) {
        this.hairstyleName = hairstyleName;
    }

    public String getHairstylePicture() {
        return hairstylePicture;
    }

    public void setHairstylePicture(String hairstylePicture) {
        this.hairstylePicture = hairstylePicture;
    }

    public String getHairstyleIntroduce() {
        return hairstyleIntroduce;
    }

    public void setHairstyleIntroduce(String hairstyleIntroduce) {
        this.hairstyleIntroduce = hairstyleIntroduce;
    }

    public String getHairstyleType() {
        return hairstyleType;
    }

    public void setHairstyleType(String hairstyleType) {
        this.hairstyleType = hairstyleType;
    }

    public Set<HairStyleDetail> getHairStyleDetailSet() {
        return hairStyleDetailSet;
    }

    public void setHairStyleDetailSet(Set<HairStyleDetail> hairStyleDetailSet) {
        this.hairStyleDetailSet = hairStyleDetailSet;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
