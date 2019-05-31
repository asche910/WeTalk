package com.asche.wetalk.spider;

import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.util.TimeUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * 每日一文爬虫
 */
public class ArticleSpider {
//    private static String targetSite = "http://w.ihx.cc/";
    private static String targetSite = "https://meiriyiwen.com";
    private static ArticleBean articleBean;

    public static void start(){
        try {

            Element element = Jsoup.connect(targetSite).get().selectFirst("div[id=article_show]");
            String title = element.selectFirst("h1").text();
            String articleBody = element.selectFirst("div.article_text").toString();

            // 获取目标文章发表日期
            String time = TimeUtils.getCurrentTime();

            articleBean = new ArticleBean();
            articleBean.setTitle(title);
            articleBean.setTime(time);
            articleBean.setBrief(articleBody);
            articleBean.setContent(articleBody);
            articleBean.setAuthorUserName("robot");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNull(){
        return articleBean == null;
    }

    public static ArticleBean getArticleBean() {
        return articleBean;
    }
}
