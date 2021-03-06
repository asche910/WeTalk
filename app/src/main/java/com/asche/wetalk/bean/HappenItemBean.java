package com.asche.wetalk.bean;

import com.asche.wetalk.adapter.HappenItemRVAdapter;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;

public class HappenItemBean implements Serializable {

    private int type;
    private String userAvatar;
    private String userName;
    private String content;
    private String time;

    private List<String> urlList;   // 图片url列表

    public HappenItemBean(String userHead, String userName, String content, String time, List<String> urlList) {
        type = HappenItemRVAdapter.TYPE_ITEM;
        this.userAvatar = userHead;
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.urlList = urlList;
    }

    public HappenItemBean(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
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
