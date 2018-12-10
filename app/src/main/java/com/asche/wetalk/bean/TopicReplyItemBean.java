package com.asche.wetalk.bean;

import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.util.DataUtils;
import com.asche.wetalk.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

import static com.asche.wetalk.adapter.TopicInfoRVAdapter.TYPE_HEADER;

public class TopicReplyItemBean implements Serializable {
    private int bodyType;
    private String authorAvatar;
    private String authorName;
    private String authorSignature;
    private String content;
    private String time;
    private String imgUrl;
    private String videoUrl;
    private TopicBean topicBean;
    private int likeNum;
    private int commentNum;

    public TopicReplyItemBean() {
    }

    public TopicReplyItemBean(int bodyType, String authorAvatar, String authorName, String authorSignature, String content, String time, String imgUrl, String videoUrl, int likeNum, int commentNum) {
        this.bodyType = bodyType;
        this.authorAvatar = authorAvatar;
        this.authorName = authorName;
        this.authorSignature = authorSignature;
        this.content = content;
        this.time = time;
        this.imgUrl = imgUrl;
        this.videoUrl = videoUrl;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
    }

    public TopicReplyItemBean(TopicBean topicBean) {
        bodyType = TYPE_HEADER;
        this.topicBean = topicBean;
    }

    public int getBodyType() {
        return bodyType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSignature() {
        return authorSignature;
    }

    public void setAuthorSignature(String authorSignature) {
        this.authorSignature = authorSignature;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public TopicBean getTopicBean() {
        return topicBean;
    }

    public void setTopicBean(TopicBean topicBean) {
        this.topicBean = topicBean;
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
