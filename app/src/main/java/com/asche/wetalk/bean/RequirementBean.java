package com.asche.wetalk.bean;

import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

// 这里在ArticleBean的基础上加了个type
public class RequirementBean implements HomeItem, Serializable {
    private String id;
    private int type; // 招技术or卖技术;
    private String authorUserName;
    private String title;
    private String brief;
    private String content;
    private String time;
    private String imgUrl;
    private String videoUrl;
    private int likeNum;
    private int commentNum;

    public RequirementBean(String id, int type, String authorUserName, String title, String brief, String content, String time, int likeNum, int commentNum) {
        this.id = id;
        this.type = type;
        this.authorUserName = authorUserName;
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.time = time;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }

    public RequirementBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAuthorUserName() {
        return authorUserName;
    }

    public void setAuthorUserName(String authorUserName) {
        this.authorUserName = authorUserName;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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


    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int getItemType() {
        return TYPE_REQUIREMENT;
    }

    @Override
    public int getItemBodyType() {
        if (getVideoUrl() != null){
            return HomeSuggestRVAdapter.TYPE_VIDEO;
        }else if (getImgUrl() != null){
            return HomeSuggestRVAdapter.TYPE_IMAGE;
        }
        return 0;
    }

    @Override
    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    @Override
    public int getCommentNum() {
        return commentNum;
    }


    @Override
    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String getVideoUrl() {
        return videoUrl;
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
