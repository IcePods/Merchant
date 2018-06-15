package com.example.shan.merchant.Entity;

import java.io.Serializable;

/**
 * Created by shan on 2018/6/8.
 */

public class Merchant implements Serializable {
    private int merchantId;
    private String merchantAccount;
    private String merchantPassword;
    private Boolean merchantCondition;
    private int openSuccess;//是否开店成功  0为不成功 1为成功
    private String merchantToken;

    private Shop shop;

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount;
    }

    public String getMerchantPassword() {
        return merchantPassword;
    }

    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    public void setMerchantCondition(Boolean merchantCondition) {
        this.merchantCondition = merchantCondition;
    }

    public void setMerchantToken(String merchantToken) {
        this.merchantToken = merchantToken;
    }

    public String getMerchantToken() {

        return merchantToken;
    }

    public Boolean getMerchantCondition() {
        return merchantCondition;
    }

    public int getOpenSuccess() {
        return openSuccess;
    }

    public void setOpenSuccess(int openSuccess) {
        this.openSuccess = openSuccess;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId=" + merchantId +
                ", merchantAccount='" + merchantAccount + '\'' +
                ", merchantPassword='" + merchantPassword + '\'' +
                ", merchantCondition=" + merchantCondition +
                ", openSuccess=" + openSuccess +
                ", merchantToken='" + merchantToken + '\'' +
                ", shop=" + shop +
                '}';
    }
}
