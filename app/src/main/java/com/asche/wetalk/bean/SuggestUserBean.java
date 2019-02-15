package com.asche.wetalk.bean;

import com.asche.wetalk.adapter.HomeSuggestRVAdapter;

import java.util.List;

public class SuggestUserBean implements HomeItem {

    private List<UserBean> userBeanList;

    public SuggestUserBean(List<UserBean> userBeanList) {
        this.userBeanList = userBeanList;
    }

    public List<UserBean> getUserBeanList() {
        return userBeanList;
    }

    public void setUserBeanList(List<UserBean> userBeanList) {
        this.userBeanList = userBeanList;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public int getItemBodyType() {
        return HomeSuggestRVAdapter.TYPE_USER;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getBrief() {
        return null;
    }

    @Override
    public String getImgUrl() {
        return null;
    }

    @Override
    public String getVideoUrl() {
        return null;
    }

    @Override
    public int getLikeNum() {
        return 0;
    }

    @Override
    public int getCommentNum() {
        return 0;
    }
}
