package com.micro.android316.housekeeping.model;

/**
 * Created by Administrator on 2016/12/14.
 */

public class Coment {
    private String head;
    private  String content;
    private   String time;
    private  String name;


    public Coment(){}

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Coment{" +
                "head='" + head + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
