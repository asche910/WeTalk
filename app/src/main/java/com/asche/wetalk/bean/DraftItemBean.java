package com.asche.wetalk.bean;

import java.io.Serializable;

public class DraftItemBean implements Serializable {
    private int type; // 文章or需求or话题 --------->  0、1、2
    private String title;
    private String content;
    private String time;
    private HomeItem homeItem;

    public DraftItemBean() {
    }

    public DraftItemBean(int type, HomeItem homeItem) {
        this.type = type;
        this.homeItem = homeItem;
        title = homeItem.getTitle();
        content = homeItem.getBrief();
        switch (type){
            case HomeItem.TYPE_TOPIC:
                time = ((TopicReplyBean)homeItem).getTime();
                break;
            case HomeItem.TYPE_REQUIREMENT:
                time = ((RequirementBean)homeItem).getTime();
                break;
            case HomeItem.TYPE_ARTICLE:
                time = ((ArticleBean)homeItem).getTime();
                break;
        }
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

    public HomeItem getHomeItem() {
        return homeItem;
    }

    public void setHomeItem(HomeItem homeItem) {
        this.homeItem = homeItem;
    }
}
