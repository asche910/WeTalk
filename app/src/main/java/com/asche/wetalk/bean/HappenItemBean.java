package com.asche.wetalk.bean;

import java.util.List;

import androidx.annotation.NonNull;

public class HappenItemBean {

    private String userHead;
    private String userName;
    private String content;
    private String time;

    private List<String> urlList;

    public HappenItemBean(String userHead, String userName, String content, String time, List<String> urlList) {
        this.userHead = userHead;
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.urlList = urlList;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }


    @NonNull
    @Override
    public String toString() {
        // TODO 待序列化（包含数组）
        return super.toString();
    }
}
