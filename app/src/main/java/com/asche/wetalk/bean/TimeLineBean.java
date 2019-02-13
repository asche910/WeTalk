package com.asche.wetalk.bean;

public class TimeLineBean {
    private int typeAction; // 点赞、评论、收藏；发动态
    private String username;    // 作者用户名
    private boolean isImage;
    private HomeItem homeItem;
    private HappenItemBean happenItemBean;

    public TimeLineBean() {
    }

    public TimeLineBean(int typeAction, String username, boolean isImage, HomeItem homeItem, HappenItemBean happenItemBean) {
        this.typeAction = typeAction;
        this.username = username;
        this.isImage = isImage;
        this.homeItem = homeItem;
        this.happenItemBean = happenItemBean;
    }

    public int getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(int typeAction) {
        this.typeAction = typeAction;
    }


    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HomeItem getHomeItem() {
        return homeItem;
    }

    public void setHomeItem(HomeItem homeItem) {
        this.homeItem = homeItem;
    }

    public HappenItemBean getHappenItemBean() {
        return happenItemBean;
    }

    public void setHappenItemBean(HappenItemBean happenItemBean) {
        this.happenItemBean = happenItemBean;
    }
}
