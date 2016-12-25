package com.micro.android316.housekeeping.model;

/**
 * Created by Administrator on 2016/12/21.
 */

public class OrderDate{
    private boolean isPayed;
    private String type;
    private long time;
    private String price;
    private String address;
    private String id;
    private String len;

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderDate{" +
                "isPayed=" + isPayed +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", id='" + id + '\'' +
                ", len='" + len + '\'' +
                '}';
    }
}
