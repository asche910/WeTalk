package com.asche.wetalk.adapter;

import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.ItemBean;

public class HomeItemAdapter {

    public static ItemBean adapt (HomeItem homeItem) {
        ItemBean itemBean = new ItemBean();
        itemBean.setType(homeItem.getItemType());
        itemBean.setBodyType(homeItem.getItemBodyType());
        itemBean.setTitle(homeItem.getTitle());
        itemBean.setContent(homeItem.getBrief());
        itemBean.setImgUrl(homeItem.getImgUrl());
        itemBean.setVideoUrl(homeItem.getVideoUrl());
        itemBean.setLikeNum(homeItem.getLikeNum());
        itemBean.setCommentNum(homeItem.getCommentNum());
        return itemBean;
    }
}
