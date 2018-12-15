package com.asche.wetalk.bean;

import java.io.Serializable;

public class DraftItemBean implements Serializable {
    private int type; // 文章or需求or话题 --------->  0、1、2
    private String title;
    private String content;
    private String time;

    public DraftItemBean() {
    }

    public DraftItemBean(int type, String title, String content, String time) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
