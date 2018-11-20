package com.asche.wetalk.util;

import android.util.Log;

import com.asche.wetalk.bean.BodyContentBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

public class BodyContentUtil {

    public static List<BodyContentBean> parseHtml(String html){
        List<BodyContentBean> contentBeanList = new ArrayList<>();
        List<BodyContentBean> tempList = new ArrayList<>();
        String[] strs = html.split("<img .+>");
        Log.e(TAG, "parseHtml: " + strs.length );

        Matcher matcher = Pattern.compile("<img .+>").matcher(html);
        while (matcher.find()){
            String content = matcher.group();

            Matcher mat = Pattern.compile("src=\"(.+?)\"").matcher(content);
            while (mat.find()){
                String url = mat.group(1);
                Log.e(TAG, "parseHtml: " + url );

                tempList.add(new BodyContentBean(1, null, handleUrl(url), null));
                break;
            }
        }
        Log.e(TAG, "parseHtml: " + tempList.size() );
        // bodyContentBeanList.add(new BodyContentBean(2, null, "http://img.mms.v1.cn/static/mms/images/2018-10-18/201810181131393298.jpg", "http://f10.v1.cn/site/15538790.mp4.f40.mp4"));

        Iterator<BodyContentBean> iterator = tempList.iterator();
        for (String str: strs){
            contentBeanList.add(new BodyContentBean(0, str, null, null));
            if (iterator.hasNext()){
                contentBeanList.add(iterator.next());
            }
        }
        return contentBeanList;
    }

    public static String handleUrl(String url){
        if (url != null){
            if (url.trim().substring(0, 2).equals("//")){
                return "http:" + url;
            }else {
                return url;
            }
        }
        return null;
    }
}
