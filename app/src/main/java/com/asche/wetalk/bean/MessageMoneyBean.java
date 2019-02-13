package com.asche.wetalk.bean;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 付费咨询Bean
 */
public class MessageMoneyBean  {
    private String sourceUsername;
    private String destUsername;
    private String lastMessage;
    private String time;
    private boolean isEnd; // false --> 正在进行

    public MessageMoneyBean() {
    }

    public MessageMoneyBean(String sourceUsername, String destUsername, String lastMessage, String time, boolean isEnd) {
        this.sourceUsername = sourceUsername;
        this.destUsername = destUsername;
        this.lastMessage = lastMessage;
        this.time = time;
        this.isEnd = isEnd;
    }

    public String getSourceUsername() {
        return sourceUsername;
    }

    public void setSourceUsername(String sourceUsername) {
        this.sourceUsername = sourceUsername;
    }

    public String getDestUsername() {
        return destUsername;
    }

    public void setDestUsername(String destUsername) {
        this.destUsername = destUsername;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
