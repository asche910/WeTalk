package com.asche.wetalk.util;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.bean.UserBean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Random;

import static com.asche.wetalk.MyApplication.getContext;
import static com.zhihu.matisse.internal.utils.PathUtils.getDataColumn;
import static com.zhihu.matisse.internal.utils.PathUtils.isDownloadsDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isExternalStorageDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isMediaDocument;

/**
 * generate simulated data
 */
public class DataUtils {

    private static Random random = new Random();

    public static UserBean getUser(){
        UserBean userBean = new UserBean();
        int id = random.nextInt(999_999_999) +  100_000;
        userBean.setId(id + "");
        userBean.setUserName("User_" + id);
        return userBean;
    }

    public static String getArticleStr(){
        return getContent(R.raw.article_1);
    }

    public static TopicReplyBean getTopicReply(){
        TopicReplyBean bean = new TopicReplyBean();
        int n = random.nextInt(2);
        switch (n){
            case 1:
                bean.setId(222 + "");
                bean.setContent(getContent(R.raw.topic_2));
                bean.setImgUrl("https://pic1.zhimg.com/v2-d54e01339bc69e3c80c760479b941ebc_b.jpg");
                break;
            case 0:
                bean.setId(111 + "");
                bean.setContent(getContent(R.raw.topic_1));
                bean.setImgUrl("https://pic1.zhimg.com/v2-91e5d2f6c85c151f235b09e4cf229509_b.jpg");
                break;
        }
        bean.setTopicId(444 + "");
        bean.setAuthorId(4 + "");
        bean.setTime("2018-08-09");
        bean.setLikeNum(78);
        bean.setCommentNum(89);
        return bean;
    }

    public static ArticleBean getArticle(){
        ArticleBean bean = new ArticleBean();
        bean.setTitle("读大学前后对比照");
        bean.setBrief("言语已无法形容我此时的惊讶之情！来感受下：只有经历了军训的黝黑 才能享受洗礼过后的美颜  我也来！14vs31岁");
        bean.setLikeNum(23);
        bean.setCommentNum(45);
        bean.setImgUrl("http://upload-images.jianshu.io/upload_images/10289013-75155d935b219002");
        return bean;
    }

    public static String getTitle(String idStr){
        switch (idStr){
            case "222":
                return "最让程序猿自豪的事情是什么？";
            case "111":
                return "你在大学有过哪些「骚操作」？";
            default:
                return "Hello, World!";
        }
    }

    private static String getContent(Integer resourceId){
        // Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.article_2);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = getContext().getResources().openRawResource(resourceId);
            //  InputStream inputStream = new FileInputStream(new File(getPath(getContext(), uri)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ( (line = reader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            reader.close();
            inputStream.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 待测试
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id
                        = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
