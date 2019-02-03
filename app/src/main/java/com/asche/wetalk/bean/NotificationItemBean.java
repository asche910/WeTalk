package com.asche.wetalk.bean;

import com.asche.wetalk.util.StringUtils;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class NotificationItemBean {
    public static final int TYPE_CHAT = 0;
    public static final int TYPE_SYSTEM = 1;

    private int type; // 0: 聊天消息  1: 系统消息
    private UserBean userBean;
    private String time;
    private String content;

    public NotificationItemBean(UserBean userBean, String time, String content) {
        type = TYPE_CHAT;
        this.userBean = userBean;
        this.time = time;
        this.content = content;
    }

    public NotificationItemBean(int type) {
        this.type = type;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
