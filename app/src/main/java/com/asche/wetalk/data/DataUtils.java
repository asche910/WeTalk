package com.asche.wetalk.data;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.RequirementBean;
import com.asche.wetalk.bean.TopicBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.bean.TopicReplyItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.util.TimeUtils;
import com.asche.wetalk.util.ZhihuUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import static com.asche.wetalk.MyApplication.getContext;
import static com.asche.wetalk.adapter.TopicInfoRVAdapter.TYPE_IMAGE;
import static com.asche.wetalk.adapter.TopicInfoRVAdapter.TYPE_TEXT;
import static com.asche.wetalk.adapter.TopicInfoRVAdapter.TYPE_VIDEO;
import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;
import static com.zhihu.matisse.internal.utils.PathUtils.getDataColumn;
import static com.zhihu.matisse.internal.utils.PathUtils.isDownloadsDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isExternalStorageDocument;
import static com.zhihu.matisse.internal.utils.PathUtils.isMediaDocument;

/**
 * generate simulated data
 */
public class DataUtils {

    public static Random random = new Random();

    public static String imgAvatar_1 = "https://cdn2.jianshu.io/assets/default_avatar/7-0993d41a595d6ab6ef17b19496eb2f21.jpg"; // 企鹅
    public static String imgAvatar_2 = "https://cdn2.jianshu.io/assets/default_avatar/3-9a2bcc21a5d89e21dafc73b39dc5f582.jpg"; // 袋鼠

    public static String videoSrc;

    /**
     * 根据id获取相应话题回复，为空则随机返回
     * @param ids
     * @return
     */
    public static TopicReplyBean getTopicReply(String... ids) {
        int n;
        if (ids.length == 1) {
            n = (int) ids[0].charAt(0) - 49;
        } else {
            n = random.nextInt(3);
        }
        final TopicReplyBean bean = new TopicReplyBean();
        switch (n) {
            case 2:
                bean.setTopicId(333 + "");
                bean.setContent(getContent(R.raw.topic_3));
                bean.setImgUrl("https://pic4.zhimg.com/v2-2d9c0d41de839b90b41c5de52ef10323_b.jpg");
            case 1:
                bean.setTopicId(222 + "");
                bean.setContent(getContent(R.raw.topic_2));
//                bean.setVideoUrl("https://vdn.vzuu.com/SD/39b9fd06-0055-11e9-b21e-0a580a4b9614.mp4?disable_local_cache=1&bu=com&expiration=1550229624&auth_key=1550229624-0-0-511cd4b9595c380632e6f6477aa8937a&f=mp4&v=ali");

                if (videoSrc != null){
                    bean.setVideoUrl(videoSrc);
                }

                // TODO  副线程内不能创建Handler
           /*     final Handler handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.what == 100 && msg.obj != null){
                            Log.e(TAG, "handleMessage: " + msg.obj.toString());
                            bean.setVideoUrl(msg.obj.toString());
                        }else {
                            Toast.makeText(getContext(), "请求失败！", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.obj = ZhihuUtils.getVideoSrc("https://www.zhihu.com/video/1057342788332605440");
                        message.what = 100;
                        handler.sendMessage(message);
                    }
                }).start();*/
                bean.setImgUrl("https://p0.cdn.img9.top/ipfs/QmZzufMWG8Shd2XGszhNnQz5ifsnLpmfaRYtCRxPaFJ52b?0.png");
                break;
            case 0:
                bean.setTopicId(111 + "");
                bean.setContent(getContent(R.raw.topic_1));
                bean.setImgUrl("https://pic1.zhimg.com/v2-91e5d2f6c85c151f235b09e4cf229509_b.jpg");
                break;
            default:
                bean.setTopicId(111 + "");
                bean.setContent(getContent(R.raw.topic_1));
                bean.setImgUrl("https://pic1.zhimg.com/v2-91e5d2f6c85c151f235b09e4cf229509_b.jpg");
        }
        bean.setAuthorUserName(4 + "");
        bean.setTime("2018-08-09");
        bean.setLikeNum(78);
        bean.setCommentNum(89);
        return bean;
    }

    public static TopicBean getTopic(String... ids) {
        int n;
        if (ids.length == 1) {
            n = (int) ids[0].charAt(0) - 49;
        } else {
            n = random.nextInt(3);
        }
        TopicBean bean = new TopicBean();
        switch (n) {
            case 2:
                bean.setName("你很长时间都不能忘记的一部电影是什么？");
                bean.setCoverUrl("https://pic4.zhimg.com/v2-2d9c0d41de839b90b41c5de52ef10323_b.jpg");
                break;
            case 1:
                bean.setName("什么能力很重要，但大多数人却没有？");
                bean.setCoverUrl("https://p0.cdn.img9.top/ipfs/QmZzufMWG8Shd2XGszhNnQz5ifsnLpmfaRYtCRxPaFJ52b?0.png");
                break;
            case 0:
                bean.setName("如何评价丁香园售卖 1980 元一双的矫形鞋垫？");
                bean.setCoverUrl(R.drawable.img_avatar + "");
                break;
            default:
                bean.setName("你很长时间都不能忘记的一部电影是什么？");
                bean.setCoverUrl("https://pic4.zhimg.com/v2-2d9c0d41de839b90b41c5de52ef10323_b.jpg");
        }
        bean.setReplyNum(n * 10);
        bean.setFollowerNum(n * 20);
        bean.setTime(TimeUtils.getCurrentTime());
        bean.setAuthorId(UserUtils.getUser().getId() + "");
        return bean;
    }


    /**
     * @param index 指定需求的索引(为空即随机)
     * @return
     */
    public static RequirementBean getRequirement(int... index) {
        RequirementBean bean = new RequirementBean();
        int n = random.nextInt(4);
        if (index.length != 0)
            n = index[0];
        switch (n) {
            case 3:
                bean.setTitle("寻找指定食材的体积估算、图像识别方案");
                bean.setBrief("目前烤箱利用烤箱里面的摄像头结合深度学习智能算法可以识别烤箱中的食材种类和数量，但无法实现食材的体积/重量检测和烘焙模具大小的检测。");
                bean.setContent(getContent(R.raw.requirement_4));
                bean.setLikeNum(n * 10);
                break;
            case 2:
                bean.setTitle("寻找洗衣机杀菌新方案");
                bean.setBrief("寻找可以应用在洗衣机上，杀死水中细菌的新技术，使洗衣机在洗涤过程中不仅能清除污垢，也能起到一定的杀菌效果，并效果可以呈现（用户能够感受到）");
                bean.setContent(getContent(R.raw.requirement_3));
                bean.setLikeNum(n * 10);
                break;
            case 1:
                bean.setTitle("寻找高性能超亲水涂层（硬度、耐盐雾、寿命）");
                bean.setBrief("寻找用于家电产品上的高性能超亲水涂层，之前对接的资源在硬度、耐盐雾实验、使用寿命上均达不到要求，希望寻找硬度、耐盐雾、寿命方面高性能超亲水涂层。");
                bean.setContent(getContent(R.raw.requirement_2));
                bean.setLikeNum(n * 10);
                break;
            case 0:
                bean.setTitle("寻找油烟扩散模拟仿真公司及烟机结构设计优化方案");
                bean.setBrief("寻找集成灶黄金吸烟区的仿真设计方案，即在目前集成灶大吸力、吸油烟效果好的条件下，通过模拟分析及优化设计，确定不同类别油烟、拢烟板长度及安装位置、吸风口与油烟产生源之间的距离等因素对集成灶吸烟效果、热效率、油烟气味、噪音的耦合影响，进行头部优化设计，得到最佳吸烟效果，同时不影响灶具的热效率。");
                bean.setContent(getContent(R.raw.requirement_1));
                bean.setImgUrl("https://hope.haier.com/iws/ckeditor_assets/pictures/28741/original_image.png");
                bean.setLikeNum(n * 10);
                break;
            default:
                bean.setTitle("寻找油烟扩散模拟仿真公司及烟机结构设计优化方案");
                bean.setBrief("寻找集成灶黄金吸烟区的仿真设计方案，即在目前集成灶大吸力、吸油烟效果好的条件下，通过模拟分析及优化设计，确定不同类别油烟、拢烟板长度及安装位置、吸风口与油烟产生源之间的距离等因素对集成灶吸烟效果、热效率、油烟气味、噪音的耦合影响，进行头部优化设计，得到最佳吸烟效果，同时不影响灶具的热效率。");
                bean.setContent(getContent(R.raw.requirement_1));
                bean.setImgUrl("https://hope.haier.com/iws/ckeditor_assets/pictures/28741/original_image.png");
                bean.setLikeNum(n * 10);
        }
        bean.setCommentNum(10 + n);
        bean.setTime(TimeUtils.getCurrentTime());
        return bean;
    }


    /**
     * @param index 指定文章的索引(为空即随机)
     * @return
     */
    public static ArticleBean getArticle(int... index) {
        ArticleBean bean = new ArticleBean();
        int n = random.nextInt(4);
        if (index.length != 0)
            n = index[0];
        switch (n) {
            case 3:
                bean.setTitle("应用SIMSOLID软件测算搅拌桶体刚性强度");
                String content_3 = getContent(R.raw.article_4);
                if (content_3.length() < 1000)
                    bean.setBrief(content_3);
                else
                    bean.setBrief(content_3.substring(0, 1000));
                bean.setContent(content_3);
                break;
            case 2:
                bean.setTitle("彭瑜:美国流程工业领跑德国工业4.0");
                String content_2 = getContent(R.raw.article_3);
                if (content_2.length() < 1000)
                    bean.setBrief(content_2);
                else
                    bean.setBrief(content_2.substring(0, 1000));
                bean.setImgUrl("https://img.jishulink.com/201901/imgs/03af6da5ff144e909815c65cf93b74f7");
                bean.setContent(content_2);
                break;
            case 1:
                bean.setTitle("猪猪这么可爱，我们用它们制造了子弹、牛排和水泥");
                String content_1 = getContent(R.raw.article_2);
                if (content_1.length() < 1000)
                    bean.setBrief(content_1);
                else
                    bean.setBrief(content_1.substring(0, 1000));
                bean.setContent(content_1);
                break;
            case 0:
                bean.setTitle("基于simsolid和AnsysWorkbench齿轮夹臂机构静力学分析对比");
                String content_0 = getContent(R.raw.article_1);
                if (content_0.length() < 1000)
                    bean.setBrief(content_0);
                else
                    bean.setBrief(content_0.substring(0, 1000));
                bean.setImgUrl("https://img.jishulink.com/upload/201901/030fad1e394c4bdab2610b436bd6d1a6.png");
                bean.setContent(content_0);
                break;
            default:
        }
        bean.setTime(TimeUtils.getCurrentTime());
        bean.setLikeNum(n * 10);
        bean.setCommentNum(10 + n);

        return bean;
    }

    /**
     * 0 -> normal；
     * 1 -> simple;
     *
     * @param type
     * @return
     */
    public static CommentItemBean getComment(int type) {
        CommentItemBean bean = new CommentItemBean();
        bean.setType(type);
        switch (random.nextInt(4)) {
            case 3:
                bean.setAvatarUrl(imgAvatar_1);
                bean.setContent("退一万步讲，就算收费了，其实对我们影响也不大，反正在天朝，盗版是一种习惯。");
                bean.setName("John Smith");
                bean.setTime("12:24");
                bean.setLikeNum(6);
                break;
            case 2:
                bean.setAvatarUrl(imgAvatar_2);
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

    public static TopicReplyItemBean getTopicReplyItem(int... index) {
        TopicReplyItemBean bean = new TopicReplyItemBean();
        UserBean author = UserUtils.getUser();

        bean.setAuthorName(author.getNickName());
        bean.setAuthorSignature(author.getSignature());
        bean.setAuthorAvatar(author.getImgAvatar());

        int n;
        if (index.length == 1) {
            n = index[0];
        } else {
            n = random.nextInt(3);
        }
        switch (n) {
            case 2:
                bean.setId("333");
                bean.setContent(getContent(R.raw.topic_3));
                bean.setImgUrl("https://pic4.zhimg.com/v2-2d9c0d41de839b90b41c5de52ef10323_b.jpg");
                bean.setBodyType(TYPE_IMAGE);
                bean.setTopicBean(getTopic("333"));
                break;
            case 1:
                bean.setId("222");
                bean.setContent(getContent(R.raw.topic_2));
                bean.setVideoUrl(ZhihuUtils.getVideoSrc("https://www.zhihu.com/video/1057342788332605440"));
                bean.setImgUrl("https://p0.cdn.img9.top/ipfs/QmZzufMWG8Shd2XGszhNnQz5ifsnLpmfaRYtCRxPaFJ52b?0.png");
                bean.setBodyType(TYPE_VIDEO);
                bean.setTopicBean(getTopic("222"));
                break;
            case 0:
                bean.setId("111");
                bean.setContent(getContent(R.raw.topic_1));
                bean.setBodyType(TYPE_TEXT);
                bean.setTopicBean(getTopic("111"));
                break;
            default:
                bean.setId("111");
                bean.setContent(getContent(R.raw.topic_1));
                bean.setBodyType(TYPE_TEXT);
                bean.setTopicBean(getTopic("111"));
                break;
        }

        bean.setLikeNum(100 * n);
        bean.setCommentNum(5 * n);
        bean.setTime(TimeUtils.getCurrentTime());

        return bean;
    }

    public static String getTitle(String idStr) {
        switch (idStr) {
            case "444":
                return "历史上有哪些如神一般存在的人物？";
            case "333":
                return "你很长时间都不能忘记的一部电影是什么？";
            case "222":
                return "什么能力很重要，但大多数人却没有？";
            case "111":
                return "如何评价丁香园售卖 1980 元一双的矫形鞋垫？";
            default:
                return "Hello, World!";
        }
    }

    public static String getContent(Integer resourceId) {
        // Uri uri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.article_2);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = getContext().getResources().openRawResource(resourceId);
            //  InputStream inputStream = new FileInputStream(new File(getPath(getContext(), uri)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
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

    // 有待测试
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {
        Log.e(TAG, "getPath: " + uri.toString());
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
