package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ArticleActivity;
import com.asche.wetalk.activity.ReportActivity;
import com.asche.wetalk.activity.TopicActivity;
import com.asche.wetalk.activity.UserHomeActivity;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.ItemBean;
import com.asche.wetalk.bean.RequirementBean;
import com.asche.wetalk.bean.SuggestUserBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.service.AudioUtils;
import com.asche.wetalk.service.VibrateUtils;
import com.asche.wetalk.util.FileUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.asche.wetalk.util.ResourcesUtils;
import com.asche.wetalk.util.StringUtils;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.MyApplication.getContext;
import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;


public class HomeSuggestRVAdapter extends RecyclerView.Adapter implements  PopupMenu.OnMenuItemClickListener {

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_USER = 3; // 专家推荐
    public static final int TYPE_LOADING = 4; // 加载更多

    private List<HomeItem> list;
    private Context context;

    // 是否开启背景圆角和悬浮效果
    private boolean enableRadiusAndEle;

    private int curPosition = -1;

    private int count;


    // 此处用作comment的点击事件
    private OnItemClickListener onItemClickListener;

    public HomeSuggestRVAdapter(List<HomeItem> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public void setEnableRadiusAndEle(boolean enableRadiusAndEle) {
        this.enableRadiusAndEle = enableRadiusAndEle;
    }

    private class TextHolder extends RecyclerView.ViewHolder {
        private TextView textTitlt, textContent;
        private TextView textLike, textComment;
        private ImageView imgLike, imgMore;
        private LinearLayout layoutLike, layoutComment, layoutForward, layoutMain;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            imgMore = itemView.findViewById(R.id.img_item_main_more);
            layoutLike = itemView.findViewById(R.id.layout_item_main_like);
            layoutComment = itemView.findViewById(R.id.layout_item_main_comment);
            layoutForward = itemView.findViewById(R.id.layout_item_main_forward);
            layoutMain = itemView.findViewById(R.id.layout_item_main);
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private TextView textTitlt, textContent;
        public TextView textLike, textComment;
        public ImageView img, imgLike, imgMore;
        private LinearLayout layoutLike, layoutComment, layoutForward, layoutMain;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            img = itemView.findViewById(R.id.img_item_main);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            imgMore = itemView.findViewById(R.id.img_item_main_more);
            layoutLike = itemView.findViewById(R.id.layout_item_main_like);
            layoutComment = itemView.findViewById(R.id.layout_item_main_comment);
            layoutForward = itemView.findViewById(R.id.layout_item_main_forward);
            layoutMain = itemView.findViewById(R.id.layout_item_main);
        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private TextView textTitlt, textContent;
        private TextView textLike, textComment;
        public StandardGSYVideoPlayer videoPlayer;
        private ImageView imgLike, imgMore;
        private LinearLayout layoutLike, layoutComment, layoutForward, layoutMain;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            videoPlayer = itemView.findViewById(R.id.video_item_main);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            imgMore = itemView.findViewById(R.id.img_item_main_more);
            layoutLike = itemView.findViewById(R.id.layout_item_main_like);
            layoutComment = itemView.findViewById(R.id.layout_item_main_comment);
            layoutForward = itemView.findViewById(R.id.layout_item_main_forward);
            layoutMain = itemView.findViewById(R.id.layout_item_main);
        }
    }

    public class UserHolder extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_suggest_user);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    public class LoadingHolder extends RecyclerView.ViewHolder{
        public LoadingHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        HomeItem homeItem = list.get(position);
        int type = homeItem.getItemBodyType();
        if (type == TYPE_TEXT) {
            return TYPE_TEXT;
        } else if (type == TYPE_IMAGE) {
            return TYPE_IMAGE;
        }else if (type == TYPE_VIDEO) {
            return TYPE_VIDEO;
        }else if (type == TYPE_LOADING){
            return TYPE_LOADING;
        }
        return TYPE_USER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == TYPE_TEXT) {
            view = inflater.inflate(R.layout.item_main_text, parent, false);
            return new TextHolder(view);
        } else if (viewType == TYPE_IMAGE) {
            view = inflater.inflate(R.layout.item_main_img, parent, false);
            return new ImageHolder(view);
        } else if (viewType == TYPE_VIDEO) {
            view = inflater.inflate(R.layout.item_main_video, parent, false);
            return new VideoHolder(view);
        }else if(viewType == TYPE_USER){
            view = inflater.inflate(R.layout.layout_suggest_user, parent, false);
            return new UserHolder(view);
        }else if (viewType == TYPE_LOADING){
            view = inflater.inflate(R.layout.layout_bottom_loading, parent, false);
            return new LoadingHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: " + (count ++) );
        final ItemBean bean = HomeItemAdapter.adapt(list.get(position));
        if (bean.getBodyType() == TYPE_TEXT) {
            final TextHolder textHolder = (TextHolder) holder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && enableRadiusAndEle) {
                textHolder.itemView.setBackground(context.getDrawable(R.drawable.bg_item));
                textHolder.itemView.setElevation(MyScrollView.dip2px(context, 2));
            }

            textHolder.textTitlt.setText(bean.getTitle());
            CharSequence sequence = Html.fromHtml(bean.getContent());
            textHolder.textContent.setText(sequence);
            textHolder.textLike.setText(bean.getLikeNum() + "");
            textHolder.textComment.setText(bean.getCommentNum() + "");


            textHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPosition = position;
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item_suggest, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(HomeSuggestRVAdapter.this);
                }
            });

            textHolder.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    textHolder.imgLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim_like));
                    textHolder.textLike.setText(StringUtils.addOne(textHolder.textLike.getText().toString()));

                    VibrateUtils.vibrateLike();
                    AudioUtils.playLike();
                }
            });
            textHolder.layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // onItemClickListener.onItemClick(position);
                    if (bean.getType() == HomeItem.TYPE_ARTICLE) {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("article", (ArticleBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    } else if (bean.getType() == HomeItem.TYPE_TOPIC) {
                        Intent intent = new Intent(context, TopicActivity.class);
                        intent.putExtra("topicReply", (TopicReplyBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("requirement", (RequirementBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    }
                }
            });
            textHolder.layoutForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentText = new Intent(Intent.ACTION_SEND);
                    intentText.setType("text/plain");
                    intentText.putExtra(Intent.EXTRA_SUBJECT, bean.getTitle());
                    intentText.putExtra(Intent.EXTRA_TEXT, bean.getContent());
                    context.startActivity(Intent.createChooser(intentText, "分享二维码"));
                }
            });
            textHolder.layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == HomeItem.TYPE_ARTICLE) {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("article", (ArticleBean)list.get(position));
                        context.startActivity(intent);
                    } else if (bean.getType() == HomeItem.TYPE_TOPIC) {
                        Intent intent = new Intent(context, TopicActivity.class);
                        intent.putExtra("topicReply", (TopicReplyBean)list.get(position));
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("requirement", (RequirementBean)list.get(position));
                        context.startActivity(intent);
                    }
                }
            });


        } else if (bean.getBodyType() == TYPE_IMAGE) {
            final ImageHolder imgHolder = (ImageHolder) holder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && enableRadiusAndEle) {
                imgHolder.itemView.setBackground(context.getDrawable(R.drawable.bg_item));
                imgHolder.itemView.setElevation(MyScrollView.dip2px(context, 2));
            }

            imgHolder.textTitlt.setText(bean.getTitle());
            CharSequence sequence = Html.fromHtml(bean.getContent());

            imgHolder.textContent.setText(sequence);
            imgHolder.textLike.setText(bean.getLikeNum() + "");
            imgHolder.textComment.setText(bean.getCommentNum() + "");


            LoaderUtils.loadImage(bean.getImgUrl(), context, imgHolder.img);

            imgHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPosition = position;
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item_suggest, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(HomeSuggestRVAdapter.this);
                }
            });

            imgHolder.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    imgHolder.imgLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim_like));
                    imgHolder.textLike.setText(StringUtils.addOne(imgHolder.textLike.getText().toString()));

                    VibrateUtils.vibrateLike();
                    AudioUtils.playLike();
                }
            });
            imgHolder.layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == HomeItem.TYPE_ARTICLE) {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("article", (ArticleBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    } else if (bean.getType() == HomeItem.TYPE_TOPIC) {
                        Intent intent = new Intent(context, TopicActivity.class);
                        intent.putExtra("topicReply", (TopicReplyBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("requirement", (RequirementBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    }
                }
            });
            imgHolder.layoutForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* new Thread(new Runnable() {
                        @Override
                        public void run() {
                            File file  = FileUtils.getFileFromURL(bean.getImgUrl());
                            uri = Uri.fromFile(file);
                        }
                    }).start();
*/
                    Intent intentImage = new Intent(Intent.ACTION_SEND);
                    intentImage.setType("text/plain");
                    intentImage.putExtra(Intent.EXTRA_SUBJECT, bean.getTitle());
                    intentImage.putExtra(Intent.EXTRA_TEXT, bean.getContent());
                    // intentImage.putExtra(Intent.EXTRA_STREAM, uri);
                    context.startActivity(Intent.createChooser(intentImage, "分享二维码"));
                }
            });


            imgHolder.layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == HomeItem.TYPE_ARTICLE) {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("article", (ArticleBean)list.get(position));
                        context.startActivity(intent);
                        // nextActivity(ArticleActivity.class);
                    } else if (bean.getType() == HomeItem.TYPE_TOPIC) {
                        Intent intent = new Intent(context, TopicActivity.class);
                        intent.putExtra("topicReply", (TopicReplyBean)list.get(position));
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("requirement", (RequirementBean)list.get(position));
                        context.startActivity(intent);
                        // nextActivity(ArticleActivity.class);
                    }
                }
            });
        } else if (bean.getBodyType() == TYPE_VIDEO) {
            final VideoHolder videoHolder = (VideoHolder) holder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && enableRadiusAndEle) {
                videoHolder.itemView.setBackground(context.getDrawable(R.drawable.bg_item));
                videoHolder.itemView.setElevation(MyScrollView.dip2px(context, 2));
            }

            videoHolder.textTitlt.setText(bean.getTitle());
            CharSequence sequence = Html.fromHtml(bean.getContent());

            videoHolder.textContent.setText(sequence);
            videoHolder.textLike.setText(bean.getLikeNum() + "");
            videoHolder.textComment.setText(bean.getCommentNum() + "");

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Glide.with(context)
                    .load(bean.getImgUrl())
                    .into(imageView);

            final StandardGSYVideoPlayer videoPlayer = videoHolder.videoPlayer;
            videoPlayer.setThumbImageView(imageView);
            videoPlayer.setUp(bean.getVideoUrl(), true, null, null, "");
            videoPlayer.getTitleTextView().setVisibility(View.GONE);
            videoPlayer.getBackButton().setVisibility(View.GONE);
            videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoHolder.videoPlayer.startWindowFullscreen(context, false, true);
                }
            });
            videoPlayer.setShowFullAnimation(true);


            videoHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPosition = position;
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item_suggest, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(HomeSuggestRVAdapter.this);
                }
            });

            videoHolder.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    videoHolder.imgLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim_like));
                    videoHolder.textLike.setText(StringUtils.addOne(videoHolder.textLike.getText().toString()));
                    VibrateUtils.vibrateLike();
                    AudioUtils.playLike();
                }
            });
            videoHolder.layoutComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    onItemClickListener.onItemClick(position);
                    if (bean.getType() == HomeItem.TYPE_ARTICLE) {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("article", (ArticleBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    } else if (bean.getType() == HomeItem.TYPE_TOPIC) {
                        Intent intent = new Intent(context, TopicActivity.class);
                        intent.putExtra("topicReply", (TopicReplyBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("requirement", (RequirementBean)list.get(position));
                        intent.putExtra("action", "OPEN_COMMENT");
                        context.startActivity(intent);
                    }
                }
            });
            videoHolder.layoutForward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentVideo = new Intent(Intent.ACTION_SEND);
                    intentVideo.setType("text/plain");
                    intentVideo.putExtra(Intent.EXTRA_SUBJECT, bean.getTitle());
                    intentVideo.putExtra(Intent.EXTRA_TEXT, bean.getContent());
                    context.startActivity(Intent.createChooser(intentVideo, "分享二维码"));
                }
            });
            videoHolder.layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == HomeItem.TYPE_ARTICLE) {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("article", (ArticleBean)list.get(position));
                        context.startActivity(intent);
                        // nextActivity(ArticleActivity.class);
                    } else if (bean.getType() == HomeItem.TYPE_TOPIC) {
                        Intent intent = new Intent(context, TopicActivity.class);
                        intent.putExtra("topicReply", (TopicReplyBean)list.get(position));
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("requirement", (RequirementBean)list.get(position));
                        context.startActivity(intent);
                        // nextActivity(ArticleActivity.class);
                    }
                }
            });
        }else if (bean.getBodyType() == TYPE_USER){
            HomeItem homeItem = list.get(position);
            SuggestUserBean suggestUserBean = (SuggestUserBean) homeItem;
            List<UserBean> userBeanList = suggestUserBean.getUserBeanList();
            SuggestUserRVAdapter suggestUserRVAdapter = new SuggestUserRVAdapter(userBeanList);

            UserHolder userHolder = (UserHolder)holder;
            userHolder.recyclerView.setAdapter(suggestUserRVAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.e(TAG, "onMenuItemClick: " + curPosition );
        switch (item.getItemId()){
            case R.id.menu_suggest_2:
                context.startActivity(new Intent(context, ReportActivity.class));
                break;
            case R.id.menu_suggest_0:
                Toast.makeText(context, "不感兴趣此话题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_suggest_share:
                Toast.makeText(context, "分享成功！", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
//        Log.e(TAG, "onViewDetachedFromWindow: " );
        if (holder instanceof HomeSuggestRVAdapter.VideoHolder) {
            HomeSuggestRVAdapter.VideoHolder viewHolder = (HomeSuggestRVAdapter.VideoHolder) holder;
            if (viewHolder.videoPlayer.isInPlayingState()) {
                viewHolder.videoPlayer.onVideoPause();
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
//        Log.e(TAG, "onViewAttachedToWindow: 2" );
        if (holder instanceof HomeSuggestRVAdapter.VideoHolder) {
            HomeSuggestRVAdapter.VideoHolder viewHolder = (HomeSuggestRVAdapter.VideoHolder) holder;

            if (viewHolder.videoPlayer.isInPlayingState()) {
                viewHolder.videoPlayer.onVideoResume();
            }
        }
    }

    private void nextActivity(Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    private void nextActivity(Class<?> cls, String action) {
        Intent intent = new Intent(context, cls);
        intent.putExtra("action", action);
        context.startActivity(intent);
    }

}
