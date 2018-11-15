package com.asche.wetalk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDUtils {

    //    生成14位文章ID
    public long genArticleId(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String time = format.format(new Date());

        int r1 = (int)(Math.random()*9 + 1);
        int r2 = (int)(Math.random()*10);
        long id = Long.parseLong(time + r1 + r2);
        return id;
    }
}
