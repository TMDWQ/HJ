package com.micro.android316.housekeeping.model;

/**
 * Created by Administrator on 2016/12/12.
 */

public class NannyInfo{
    private String image;
    private String title;
    private String content;

    public NannyInfo(String image, String title, String content) {
        this.image = image;
        this.title = title;
        this.content = content;
    }

    public NannyInfo(){}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "NannyInfo{" +
                "image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
