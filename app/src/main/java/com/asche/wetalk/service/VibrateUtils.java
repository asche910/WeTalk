package com.asche.wetalk.service;

import android.os.Vibrator;

import static com.asche.wetalk.MyApplication.getContext;

public class VibrateUtils {

    /**
     * 点赞震动
     */
    public static void vibrateLike(){
        Vibrator vibrator = (Vibrator) getContext().getSystemService(getContext().VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }
}
