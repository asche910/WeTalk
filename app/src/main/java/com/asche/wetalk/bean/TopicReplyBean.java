package com.asche.wetalk.bean;

import com.asche.wetalk.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class TopicReplyBean {
    private String id;
    private String topicId;
    private String authorNum;
    private String content;
    private String time;
    private int likeNum;
    private int commentNum;

    public TopicReplyBean(String id, String topicId, String authorNum, String content, String time, int likeNum, int commentNum) {
        this.id = id;
        this.topicId = topicId;
        this.authorNum = authorNum;
        this.content = content;
        this.time = time;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }

    public TopicReplyBean() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getAuthorNum() {
        return authorNum;
    }

    public void setAuthorNum(String authorNum) {
        this.authorNum = authorNum;
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

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
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
