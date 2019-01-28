package com.asche.wetalk.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class LoaderUtils {

    /**
     * 图片加载器
     * @param src
     * @param context
     * @param imageView
     */
    public static void loadImage(String src, Context context, ImageView imageView){
        if (src.charAt(0) == 'h'){
            Glide.with(context)
                    .load(src)
                    .into(imageView);
        }else {
            try {

                Glide.with(context)
                        .load(Integer.parseInt(src))
                        .into(imageView);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(src)
                        .into(imageView);
            }
        }
    }
}
