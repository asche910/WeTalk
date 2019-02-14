package com.asche.wetalk.bean;

import com.asche.wetalk.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class TopicBean implements Serializable {
    private String id;
    private String name; //  问题
    private String content; // 问题描述
    private String coverUrl;
    private String time;
    private int followerNum;
    private int replyNum;
    private String authorId;

    public TopicBean(String name, String content, String time, int followerNum, int replyNum) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.followerNum = followerNum;
        this.replyNum = replyNum;
    }

    public TopicBean(String id, String name, String content, String time, int followerNum, int replyNum, String authorId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.time = time;
        this.followerNum = followerNum;
        this.replyNum = replyNum;
        this.authorId = authorId;
    }

    public TopicBean(String id, String name, String content, String coverUrl, String time, int followerNum, int replyNum, String authorId) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.coverUrl = coverUrl;
        this.time = time;
        this.followerNum = followerNum;
        this.replyNum = replyNum;
        this.authorId = authorId;
    }

    public TopicBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(int followerNum) {
        this.followerNum = followerNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @NonNull
    @Override
    public String toString() {
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
