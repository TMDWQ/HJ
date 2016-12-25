package com.micro.android316.housekeeping.model;

/**
 * Created by Administrator on 2016/12/14.
 */
public class Waitservice {
    String picture,ranges,times,currentPrice,orginalPrice;
    String id,price;
    String ispared;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getOrginalPrice() {
        return orginalPrice;
    }

    public void setOrginalPrice(String orginalPrice) {
        this.orginalPrice = orginalPrice;
    }
}
