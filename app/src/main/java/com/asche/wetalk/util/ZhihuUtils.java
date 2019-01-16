package com.asche.wetalk.util;

import com.asche.wetalk.http.HttpUtils;

import org.json.JSONObject;

import java.io.IOException;

import static com.asche.wetalk.http.HttpUtils.getResponseContent;

public class ZhihuUtils {

    private static final String ApiUrl = "https://lens.zhihu.com/api/videos/";
    private static String id;

    public static String getVideoSrc(String playUrl){
        String[] strs = playUrl.split("/");
        for (String str : strs)
            if (str.matches("\\d{8,}"))
                id = str;
        try {
            return parseJsonForSrc(ApiUrl + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 由api提取出最高清晰度的url
     *
     * @param url
     * @return play_url
     */
    private static String parseJsonForSrc(String url) throws Exception {
        String json = getResponseContent(url);

        JSONObject jsonObject = new JSONObject(json).getJSONObject("playlist");

        if (jsonObject.has("hd")) {
            JSONObject jb = jsonObject.getJSONObject("hd");
            return jb.getString("play_url");
        } else if (jsonObject.has("sd")) {
            JSONObject jb = jsonObject.getJSONObject("sd");
            return jb.getString("play_url");
        } else if (jsonObject.has("ld")) {
            JSONObject jb = jsonObject.getJSONObject("ld");
            return jb.getString("play_url");
        }
        return null;
    }
}
