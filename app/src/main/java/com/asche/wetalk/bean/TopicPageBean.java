package com.asche.wetalk.bean;

import java.util.List;

import static com.asche.wetalk.adapter.TopicPageRVAdapter.TYPE_BANNER;
import static com.asche.wetalk.adapter.TopicPageRVAdapter.TYPE_CHIP;
import static com.asche.wetalk.adapter.TopicPageRVAdapter.TYPE_TEXT;
import static com.asche.wetalk.adapter.TopicPageRVAdapter.TYPE_TOPIC_LIST;

public class TopicPageBean {
    private int type;
    private List<Integer> imgList;
    private List<String> titleList; // banner标题或chips标签

    private String textTitle;
    private List<ItemBean> itemBeanList;

    public TopicPageBean(List<Integer> imgList, List<String> titleList) {
        type = TYPE_BANNER;
        this.imgList = imgList;
        this.titleList = titleList;
    }

    public TopicPageBean(String textTitle) {
        type = TYPE_TEXT;
        this.textTitle = textTitle;
    }

    public TopicPageBean(List<String> titleList) {
        type = TYPE_CHIP;
        this.titleList = titleList;
    }

    public TopicPageBean(int type, List<ItemBean> itemBeanList) {
        this.type = TYPE_TOPIC_LIST;
        this.itemBeanList = itemBeanList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Integer> getImgList() {
        return imgList;
    }

    public void setImgList(List<Integer> imgList) {
        this.imgList = imgList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public List<ItemBean> getItemBeanList() {
        return itemBeanList;
    }

    public void setItemBeanList(List<ItemBean> itemBeanList) {
        this.itemBeanList = itemBeanList;
    }
}
