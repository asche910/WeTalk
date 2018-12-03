package com.asche.wetalk.bean;

import com.asche.wetalk.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import androidx.annotation.NonNull;

public class CommentItemBean {

    // 哪种方式显示：simple or normal
    private int type;
    private String id;
    private String parentId;
    private String avatarUrl;
    private String name;
    private String content;
    private String time;
    private int likeNum;

    private List<CommentItemBean> subList;

    public CommentItemBean() {
    }

    // for simple
    public CommentItemBean(String avatarUrl, String name, String content) {
        this.type = 1;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.content = content;
    }

    // All except subList
    public CommentItemBean(int type, String id, String parentId, String avatarUrl, String name, String content, String time, int likeNum) {
        this.type = type;
        this.id = id;
        this.parentId = parentId;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.content = content;
        this.time = time;
        this.likeNum = likeNum;
    }

    // All
    public CommentItemBean(int type, String id, String parentId, String avatarUrl, String name, String content, String time, int likeNum, List<CommentItemBean> subList) {
        this.type = type;
        this.id = id;
        this.parentId = parentId;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.content = content;
        this.time = time;
        this.likeNum = likeNum;
        this.subList = subList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public List<CommentItemBean> getSubList() {
        return subList;
    }

    public void setSubList(List<CommentItemBean> subList) {
        this.subList = subList;
    }

    @NonNull
    @Override
    public String toString() {
        // TODO list serial
        try {
            return StringUtils.objToString(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
