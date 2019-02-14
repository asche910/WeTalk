package com.asche.wetalk.bean;

import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.data.DataUtils;
import com.asche.wetalk.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class TopicReplyBean implements HomeItem, Serializable {
    private String id;
    private String topicId;
    private String authorId;
    private String content;
    private String time;
    private String imgUrl;
    private String videoUrl;
    private int likeNum;
    private int commentNum;

    public TopicReplyBean(String id, String topicId, String authorId, String content, String time, String imgUrl, String videoUrl, int likeNum, int commentNum) {
        this.id = id;
        this.topicId = topicId;
        this.authorId = authorId;
        this.content = content;
        this.time = time;
        this.imgUrl = imgUrl;
        this.videoUrl = videoUrl;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }

    public TopicReplyBean(String id, String topicId, String authorId, String content, String time, int likeNum, int commentNum) {
        this.id = id;
        this.topicId = topicId;
        this.authorId = authorId;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
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
        return TYPE_TOPIC;
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
    public String getTitle() {
        // TODO implement getTitle();
        return DataUtils.getTitle(getTopicId());
    }

    @Override
    public String getBrief() {
        return content;
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
