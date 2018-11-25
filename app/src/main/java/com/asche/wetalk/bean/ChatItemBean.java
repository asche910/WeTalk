package com.asche.wetalk.bean;

import com.asche.wetalk.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class ChatItemBean {
    private int type; // 发送or收到
    private String content;
    private String imgUrl; // 图片内容
    private String imgAvatar;

    /**
     * 使用内容为文本时的情况
     */
    public ChatItemBean(int type, String content, String imgAvatar) {
        this.type = type;
        this.content = content;
        this.imgAvatar = imgAvatar;
    }

    public ChatItemBean(int type, String content, String imgUrl, String imgAvatar) {
        this.type = type;
        this.content = content;
        this.imgUrl = imgUrl;
        this.imgAvatar = imgAvatar;
    }

    public ChatItemBean() {
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
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
