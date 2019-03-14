package com.asche.wetalk.spider;

import com.asche.wetalk.bean.ArticleBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * 每日一文爬虫
 */
public class ArticleSpider {
    private static String targetSite = "http://w.ihx.cc/";
    private static ArticleBean articleBean;

    public static void start(){
        try {
            Document document = Jsoup.connect(targetSite).get();

            Element element = document.selectFirst("div.cat-box-content");
            Element ele = element.child(0);
            Element targetEle = ele.selectFirst("h2.post-box-title").child(0);
            // System.out.println(targetEle.toString());

            // 获取目标文章url
            String articleUrl = targetEle.attr("href");
            // 获取目标文章title
            String title = targetEle.text();


            Document doc = Jsoup.connect(articleUrl).get();
            // 获取目标文章发表日期
            String time = doc.selectFirst("span.tie-date").text();
            // 获取目标文章内容
            String articleBody = doc.selectFirst("div.entry").toString();

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
