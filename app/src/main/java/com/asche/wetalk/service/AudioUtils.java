package com.asche.wetalk.service;

import android.media.MediaPlayer;

import com.asche.wetalk.R;

import java.io.IOException;

import static com.asche.wetalk.MyApplication.getContext;

public class AudioUtils {


    public static void play(String path){
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mediaPlayer.prepare();
        // TODO play audio
    }

    /**
     * 播放点赞音效
     */
    public static void playLike(){
        // 使用create()则不能使用prepare()
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.audio_like);
        mediaPlayer.start();
    }
}
