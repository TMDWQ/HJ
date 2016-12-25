package com.micro.android316.housekeeping.model;

/**
 * Created by Administrator on 2016/12/12.
 */
public class HomePageModel {
    private String picture;
    private double numStars;
    private String name,workRanges,briefInduction;
    private int id;
    private int age;
    private int workYears;
    private int browseTimes;
    private int commentTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getNumStars() {
        return numStars;
    }

    public void setNumStars(double numStars) {
        this.numStars = numStars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkRanges() {
        return workRanges;
    }

    public void setWorkRanges(String workRanges) {
        this.workRanges = workRanges;
    }

    public String getBriefInduction() {
        return briefInduction;
    }

    public void setBriefInduction(String briefInduction) {
        this.briefInduction = briefInduction;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWorkYears() {
        return workYears;
    }

    public void setWorkYears(int workYears) {
        this.workYears = workYears;
    }

    public int getBrowseTimes() {
        return browseTimes;
    }

    public void setBrowseTimes(int browseTimes) {
        this.browseTimes = browseTimes;
    }

    public int getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(int commentTimes) {
        this.commentTimes = commentTimes;
    }

    @Override
    public String toString() {
        return "HomePageModel{" +
                "picture='" + picture + '\'' +
                ", numStars=" + numStars +
                ", name='" + name + '\'' +
                ", workRanges='" + workRanges + '\'' +
                ", briefInduction='" + briefInduction + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", workYears=" + workYears +
                ", browseTimes=" + browseTimes +
                ", commentTimes=" + commentTimes +
                '}';
    }
}
