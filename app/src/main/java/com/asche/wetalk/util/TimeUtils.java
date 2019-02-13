package com.asche.wetalk.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

/**
 * 时间相关处理工具集
 */
@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class TimeUtils {

    private static int[] days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * 获取当前系统时间
     *
     * @return 形如：2018-12-03 19:12:15
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 获取付费咨询的倒计时
     *
     * @param time
     * @return
     */
    public static String getCountTime(String time) {
        Log.e(TAG, "getCountTime: " + time);

        String startTime = time.substring(11);
        int startMonth = Integer.parseInt(time.substring(5, 7));
        int startDay = Integer.parseInt(time.substring(8, 10));
        int startHour = Integer.parseInt(startTime.substring(0, 2));
        int startMinute = Integer.parseInt(startTime.substring(3, 5));
        int startSec = Integer.parseInt(startTime.substring(6));

        int daysCurMonth = days[startMonth];

        Calendar calendar = Calendar.getInstance();
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        int nowMinute = calendar.get(Calendar.MINUTE);
        int nowSec = calendar.get(Calendar.SECOND);


        int hour = 0, minute = 0, second = 0;

        if (startDay == nowDay) {
            hour = startHour - nowHour + 24;
            minute = startMinute - nowMinute;
            second = startSec - nowSec;
            if (second < 0) {
                second += 60;
                minute--;
            }
            if (minute < 0) {
                minute += 60;
                hour--;
            }
        } else {
            if (startDay != daysCurMonth) {
                if ((startDay + 1) == nowDay) {
                    hour = startHour - nowHour;
                    minute = startMinute - nowMinute;
                    second = startSec - nowSec;
                    if (second < 0) {
                        second += 60;
                        minute--;
                    }
                    if (minute < 0) {
                        minute += 60;
                        hour--;
                    }
                    if (hour < 0)
                        return null;
                }else {
                    return null;
                }
            }else {
                if (nowDay == 1){
                    hour = startHour - nowHour;
                    minute = startMinute - nowMinute;
                    second = startSec - nowSec;
                    if (second < 0) {
                        second += 60;
                        minute--;
                    }
                    if (minute < 0) {
                        minute += 60;
                        hour--;
                    }
                    if (hour < 0)
                        return null;
                }else {
                    return null;
                }
            }
        }
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    /**
     * @param monthStr 形如 2018-12
     * @return 该月份的天数
     */
    private static int getDays(String monthStr) {
        int year = 0;
        int month = 0;
        try {
            year = Integer.parseInt(monthStr.substring(0, 4));
            month = Integer.parseInt(monthStr.split("-")[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            days[2] = 29;
        } else {
            days[2] = 28;
        }
        return days[month];
    }
}
