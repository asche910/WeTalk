package com.asche.wetalk.bean;

public interface HomeItem {
    int TYPE_ARTICLE = 0;
    int TYPE_REQUIREMENT = 1;
    int TYPE_TOPIC = 2;

    int getItemType(); // 文章、需求、话题回复

    int getItemBodyType(); // 文本、图片、视频

    String getTitle();

    String getBrief();

    String getImgUrl();

    String getVideoUrl();

    int getLikeNum();

    int getCommentNum();
}
