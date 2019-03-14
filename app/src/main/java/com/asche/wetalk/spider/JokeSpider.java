package com.asche.wetalk.spider;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import java.io.IOException;

import static com.asche.wetalk.activity.DiscoverHappenPublishActivity.TAG;

/**
 * 每日一笑爬虫
 */
public class JokeSpider {
    private static final String JokeApi = "https://www.biedoul.com/late/";

    public static String getJoke(){
        try {
            Document document = Jsoup.connect(JokeApi).get();
            Element eles = document.body().select("div.nr").first();
            Element ele = eles.child(0);
            Element removedEle = ele.select("div.c_a").first();
            removedEle.remove();

            Log.e(TAG, "getJoke: ---> " + ele.toString() );

            return ele.html();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
