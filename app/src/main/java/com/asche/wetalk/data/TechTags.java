package com.asche.wetalk.data;

import java.util.Arrays;
import java.util.List;

public class TechTags {
    private static String[] tags = new  String[]{
            "减振降噪", "新材料应用", "传感器", "绿色能源",
            "射频技术", "人工智能", "流体机械", "水处理技术",
            "食品技术", "制冷系统设计", "燃烧技术", "空气净化技术",
            "机械结构设计", "加热技术", "其它"};

    public static String[] getTagsArray(){
        return tags;
    }

    public static List<String> getTagsList(){
        return Arrays.asList(tags);
    }
}
