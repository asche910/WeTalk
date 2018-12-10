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
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.bean.TopicReplyItemBean;
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
import static com.asche.wetalk.adapter.TopicInfoRVAdapter.TYPE_IMAGE;
import static com.asche.wetalk.adapter.TopicInfoRVAdapter.TYPE_TEXT;
import static com.asche.wetalk.adapter.TopicInfoRVAdapter.TYPE_VIDEO;
import static com.zhihu.matisse.internal.utils.PathUtils.getDataColumn;
import static com.zhihu.matisse.internal.utils.PathUtils.isDownloadsDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isExternalStorageDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isMediaDocument;

/**
 * generate simulated data
 */
public class DataUtils {

    private static Random random = new Random();

    private static String imgUrl_1 = "https://cdn2.jianshu.io/assets/default_avatar/7-0993d41a595d6ab6ef17b19496eb2f21.jpg";
    private static String imgUrl_2 = "https://cdn2.jianshu.io/assets/default_avatar/3-9a2bcc21a5d89e21dafc73b39dc5f582.jpg";

    public static UserBean getUser(){
        UserBean userBean = new UserBean();
        int id = random.nextInt(999_999_999) +  100_000;
        userBean.setId(id + "");
        userBean.setUserName("User_" + id);

        userBean.setImgAvatar(random.nextBoolean() ? imgUrl_1: imgUrl_2);

        userBean.setFollowerNum(6_000);
        userBean.setFollowNum(6);
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
        int n = random.nextInt(2);
        switch (n){
            case 1:
                bean.setTitle("读大学前后对比照");
                bean.setBrief("言语已无法形容我此时的惊讶之情！来感受下：只有经历了军训的黝黑 才能享受洗礼过后的美颜  我也来！14vs31岁");
                bean.setContent(getArticleStr());
                bean.setImgUrl("http://upload-images.jianshu.io/upload_images/10289013-75155d935b219002");
                break;
            case 0:
                bean.setTitle("爱是勇敢行四方");
                bean.setBrief("小雪人一出生就在冰天雪地里，或者阳光永远也照射不到的地方。因为每一个制造雪人的人都很明白，雪人怕热，怕太阳，雪人得到温暖的时候，生命也就结束了。");
                bean.setContent(getContent(R.raw.article_2));
                bean.setImgUrl("http://upload-images.jianshu.io/upload_images/12118808-731cedddfc5f5a60.jpg");
                break;
        }
        bean.setLikeNum(23);
        bean.setCommentNum(45);
        return bean;
    }

    /**
     * 0 -> normal；
     * 1 -> simple;
     * @param type
     * @return
     */
    public static CommentItemBean getComment(int type){
        CommentItemBean bean = new CommentItemBean();
        bean.setType(type);
        switch (random.nextInt(4)){
            case 3:
                bean.setAvatarUrl(imgUrl_1);
                bean.setContent("退一万步讲，就算收费了，其实对我们影响也不大，反正在天朝，盗版是一种习惯。");
                bean.setName("John Smith");
                bean.setTime("12:24");
                bean.setLikeNum(6);
                break;
            case 2:
                bean.setAvatarUrl(imgUrl_2);
                bean.setContent("我们现在总归也开始用正版了嘛（严肃脸）～");
                bean.setName("防弹纸尿裤");
                bean.setTime("刚刚");

                break;
            case 1:
                bean.setAvatarUrl(R.drawable.img_avatar + "");
                bean.setContent("欧盟罚款借口是谷歌强行在安卓绑定自家应用，阻碍了欧洲市场的创新。所以谷歌说那我不绑定了，但是你要用我的软件就得交钱。谷歌旗下app在欧洲市场占据统治力，欧洲没有与之抗衡的，所以谷歌有自信");
                bean.setName("Ethereal");
                bean.setTime("14:36");
                bean.setLikeNum(789);

                break;
            case 0:
                bean.setAvatarUrl(R.drawable.img_avatar_default + "");
                bean.setContent("Android收费，和我安卓有什么关系（手动狗头）");
                bean.setName("Albert Zhang");
                bean.setTime("昨天17:25");

                break;
            default:
        }
        return bean;
    }

    public static TopicReplyItemBean getTopicReplyItem(){
        TopicReplyItemBean bean = new TopicReplyItemBean();
        bean.setAuthorName("Asche");
        bean.setAuthorSignature("It专业的一只菜鸟");
        bean.setAuthorAvatar(R.drawable.img_avatar + "");

        bean.setContent(getContext().getResources().getString(R.string.topic_reply));
        bean.setLikeNum(456);
        bean.setCommentNum(82);
        bean.setTime("10-24");

        int n = random.nextInt(3);
        switch (n){
            case 2:
                bean.setBodyType(TYPE_VIDEO);
                bean.setVideoUrl("http://f10.v1.cn/site/15538790.mp4.f40.mp4");
                bean.setImgUrl("http://img.mms.v1.cn/static/mms/images/2018-10-18/201810181131393298.jpg");
                break;
            case 1:
                bean.setBodyType(TYPE_IMAGE);
                bean.setImgUrl(R.drawable.img_avatar + "");
                break;
            default:
                bean.setBodyType(TYPE_TEXT);
                break;
        }
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
