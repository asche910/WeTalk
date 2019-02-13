package com.asche.wetalk.util;

import android.graphics.Bitmap;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.asche.wetalk.fragment.FragmentEmoticon.emoticonList;
import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

/**
 *  [emoji-0] ~~ [emoji-99]
 */
public class EmoticonUtils {

    public static SpannableString parseEmoticon(String text){
        if (text == null){
            return new SpannableString("A error occurred!");
        }
        SpannableString spannableString = new SpannableString(text);
        Matcher matcher = Pattern.compile("\\[emoji-(\\d+)+\\]").matcher(text);
        while (matcher.find()){
            try {
                int index =  Integer.parseInt(matcher.group(1));
                int start = matcher.start();
                int end = matcher.end();
                Log.e(TAG, "parseEmoticon: " + index  + " --->  " + start + "-" + end );
                Bitmap bitmap = emoticonList.get(index);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, 450, 450, true);
                ImageSpan imageSpan = new ImageSpan(scaleBitmap);

                spannableString.setSpan(imageSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return spannableString;
    }
}
