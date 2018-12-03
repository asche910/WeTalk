package com.asche.wetalk.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间相关处理工具集
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    /**
     * 获取当前系统时间
     *
     * @return 形如：2018-12-03 19:12:15
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
}
