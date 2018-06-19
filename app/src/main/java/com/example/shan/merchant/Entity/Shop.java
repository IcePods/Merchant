package com.example.shan.merchant.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sweet on 2018/5/15.
 */

public class Shop implements Serializable {
    private int shopId;
    private String shopPicture;
    private String shopName;
    private String shopAddress;
    private String shopPhone;
    private String shopIntroduce;//店铺简介
    private Set<ShopPicture> ShopPictureSet = new HashSet<>();
    private List<HairStyleDetail> hairStyleDetails = new ArrayList<HairStyleDetail>();

    private Merchant merchant;
    /*private List<Baber> baberSet = new ArrayList<Baber>();*/
    //店铺与 理发师 一对多 单项映射
    private Set<Barber> BarberSet = new HashSet<Barber>();

    //店铺与 活动 一对多
    private List<Activity> ActivityList = new ArrayList<>();

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopPicture() {
        return shopPicture;
    }

    public void setShopPicture(String shopPicture) {
        this.shopPicture = shopPicture;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopIntroduce() {
        return shopIntroduce;
    }

    public void setShopIntroduce(String shopIntroduce) {
        this.shopIntroduce = shopIntroduce;
    }

    public Set<ShopPicture> getShopPictureSet() {
        return ShopPictureSet;
    }

    public void setShopPictureSet(Set<ShopPicture> shopPictureSet) {
        ShopPictureSet = shopPictureSet;
    }

    public List<HairStyleDetail> getHairStyleDetails() {
        return hairStyleDetails;
    }

    public void setHairStyleDetails(List<HairStyleDetail> hairStyleDetails) {
        this.hairStyleDetails = hairStyleDetails;
    }

    public Set<Barber> getBarberSet() {
        return BarberSet;
    }

    public void setBarberSet(Set<Barber> barberSet) {
        BarberSet = barberSet;
    }

    public List<Activity> getActivityList() {
        return ActivityList;
    }

    public void setActivityList(List<Activity> activityList) {
        ActivityList = activityList;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", shopPicture='" + shopPicture + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopPhone='" + shopPhone + '\'' +
                ", shopIntroduce='" + shopIntroduce + '\'' +
                ", ShopPictureSet=" + ShopPictureSet +
                ", hairStyleDetails=" + hairStyleDetails +
                ", merchant=" + merchant +
                ", BarberSet=" + BarberSet +
                ", ActivityList=" + ActivityList +
                '}';
    }
}
