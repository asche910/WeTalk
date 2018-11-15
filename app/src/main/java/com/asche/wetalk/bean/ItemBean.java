package com.asche.wetalk.bean;

public class ItemBean {
    private int type;
    private String title;
    private String content;
    private int likeNum;
    private int commentNum;
    private String imgUrl;

    public ItemBean(int type, String title, String content, int likeNum, int commentNum, String imgUrl) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.imgUrl = imgUrl;
    }

    public ItemBean() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
