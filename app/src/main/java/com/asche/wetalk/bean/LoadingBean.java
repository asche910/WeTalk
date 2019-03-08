package com.asche.wetalk.bean;

import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.adapter.SuggestUserRVAdapter;

public class LoadingBean implements HomeItem {

    public LoadingBean() {
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public int getItemBodyType() {
        return HomeSuggestRVAdapter.TYPE_LOADING;
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
