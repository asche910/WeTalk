package com.asche.wetalk.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IDUtils {

    private static String words = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static String wordsSmall = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static Random random = new Random();

    // 生成8位长度用户名
    public static String getUserName(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++){
            char ch = wordsSmall.charAt(random.nextInt(wordsSmall.length()));
            builder.append(ch);
        }
        return builder.toString();
    }

    // 生成14位文章ID
    public static long genArticleId(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String time = format.format(new Date());

        int r1 = (int)(Math.random()*9 + 1);
        int r2 = (int)(Math.random()*10);
        long id = Long.parseLong(time + r1 + r2);
        return id;
    }
}
