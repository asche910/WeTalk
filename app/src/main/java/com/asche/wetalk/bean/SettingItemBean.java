package com.asche.wetalk.bean;

import com.asche.wetalk.adapter.SettingRVAdapter;

public class SettingItemBean {
    private int type;
    private String title;
    private String description;
    private boolean hasSwitch;
    private boolean flag; // switch 的开关状态

    public SettingItemBean() {
    }

    public SettingItemBean(String title, String description, boolean hasSwitch) {
        this.title = title;
        this.description = description;
        this.hasSwitch = hasSwitch;
        type = SettingRVAdapter.TYPE_ITEM;
    }

    public SettingItemBean(String title) {
        this.title = title;
        type = SettingRVAdapter.TYPE_TITLE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasSwitch() {
        return hasSwitch;
    }

    public void setHasSwitch(boolean hasSwitch) {
        this.hasSwitch = hasSwitch;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
