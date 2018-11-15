package com.asche.wetalk.bean;

import com.asche.wetalk.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class CommentBean {
    private int id;
    private int parentId; // 是否是某个comment下的回复， 否则置0
    private int bodyId; // 文章、话题、动态  -> 之一的id
    private int authorId;
    private int type; // 话题、文章、说说、
    private String content;
    private String time;
    private int likeNum;

    public CommentBean(int id, int parentId, int bodyId, int authorId, int type, String content, String time, int likeNum) {
        this.id = id;
        this.parentId = parentId;
        this.bodyId = bodyId;
        this.authorId = authorId;
        this.type = type;
        this.content = content;
        this.time = time;
        this.likeNum = likeNum;
    }

    public CommentBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getBodyId() {
        return bodyId;
    }

    public void setBodyId(int bodyId) {
        this.bodyId = bodyId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
