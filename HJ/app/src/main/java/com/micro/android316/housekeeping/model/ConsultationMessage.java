package com.micro.android316.housekeeping.model;

/**
 * Created by Administrator on 2016/12/20.
 */

public class ConsultationMessage{
    private int headImage;
    private String content;
    private boolean isUser;

    public int getHeadImage() {
        return headImage;
    }

    public void setHeadImage(int headImage) {
        this.headImage = headImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}
