package com.micro.android316.housekeeping.model;

/**
 * Created by Administrator on 2016/12/10.
 */

public class ElderlyDate {

    private String headURL;
    private String evaluateURL;
    private String name;
    private String age;
    private String experience;
    private String workSpace;
    private String briefIntroduction;
    private String guanZhu_count;
    private int pingLun_count;
    private int zhan_count;
    private String id;
    private float stars;

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public ElderlyDate(String headURL, String evaluateURL, String name, String age, String experience, String workSpace, String briefIntroduction, String guanZhu_count, int pingLun_count, int zhan_count) {
        this.headURL = headURL;
        this.evaluateURL = evaluateURL;
        this.name = name;
        this.age = age;
        this.experience = experience;
        this.workSpace = workSpace;
        this.briefIntroduction = briefIntroduction;
        this.guanZhu_count = guanZhu_count;
        this.pingLun_count = pingLun_count;
        this.zhan_count = zhan_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ElderlyDate(){}

    public String getHeadURL() {
        return headURL;
    }

    public void setHeadURL(String headURL) {
        this.headURL = headURL;
    }

    public String getEvaluateURL() {
        return evaluateURL;
    }

    public void setEvaluateURL(String evaluateURL) {
        this.evaluateURL = evaluateURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWorkSpace() {
        return workSpace;
    }

    public void setWorkSpace(String workSpace) {
        this.workSpace = workSpace;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public String getGuanZhu_count() {
        return guanZhu_count;
    }

    public void setGuanZhu_count(String guanZhu_count) {
        this.guanZhu_count = guanZhu_count;
    }

    public int getPingLun_count() {
        return pingLun_count;
    }

    public void setPingLun_count(int pingLun_count) {
        this.pingLun_count = pingLun_count;
    }

    public int getZhan_count() {
        return zhan_count;
    }

    public void setZhan_count(int zhan_count) {
        this.zhan_count = zhan_count;
    }
}
