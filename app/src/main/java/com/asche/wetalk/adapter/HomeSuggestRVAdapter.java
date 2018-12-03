package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ArticleActivity;
import com.asche.wetalk.activity.TopicActivity;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.ItemBean;
import com.asche.wetalk.util.DataUtils;
import com.asche.wetalk.util.StringUtils;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;


public class HomeSuggestRVAdapter extends RecyclerView.Adapter{

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;

    private List<ItemBean> list;
    private Context context;

    public HomeSuggestRVAdapter(List<ItemBean> list) {
        this.list = list;
    }

    private class TextHolder extends RecyclerView.ViewHolder{
        private TextView textTitlt, textContent;
        private TextView textLike, textComment;
        private ImageView imgLike;
        private LinearLayout layoutLike, layoutComment, layoutForward, layoutMain;
        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            layoutLike = itemView.findViewById(R.id.layout_item_main_like);
            layoutComment = itemView.findViewById(R.id.layout_item_main_comment);
            layoutForward = itemView.findViewById(R.id.layout_item_main_forward);
            layoutMain = itemView.findViewById(R.id.layout_item_main);
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder{
        private TextView textTitlt, textContent;
        public TextView textLike, textComment;
        public ImageView img, imgLike;
        private LinearLayout layoutLike, layoutComment, layoutForward, layoutMain;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            img = itemView.findViewById(R.id.img_item_main);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            layoutLike = itemView.findViewById(R.id.layout_item_main_like);
            layoutComment = itemView.findViewById(R.id.layout_item_main_comment);
            layoutForward = itemView.findViewById(R.id.layout_item_main_forward);
            layoutMain = itemView.findViewById(R.id.layout_item_main);
        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder{
        private TextView textTitlt, textContent;
        private TextView textLike, textComment;
        public StandardGSYVideoPlayer videoPlayer;
        private ImageView imgLike;
        private LinearLayout layoutLike, layoutComment, layoutForward, layoutMain;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            videoPlayer = itemView.findViewById(R.id.video_item_main);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            layoutLike = itemView.findViewById(R.id.layout_item_main_like);
            layoutComment = itemView.findViewById(R.id.layout_item_main_comment);
            layoutForward = itemView.findViewById(R.id.layout_item_main_forward);
            layoutMain = itemView.findViewById(R.id.layout_item_main);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ItemBean itemBean = list.get(position);
        if (itemBean.getBodyType() == TYPE_TEXT){
            return TYPE_TEXT;
        }else if(itemBean.getBodyType() == TYPE_IMAGE){
            return TYPE_IMAGE;
        }if (itemBean.getBodyType() == TYPE_VIDEO){
            return TYPE_VIDEO;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (context == null){
            context = parent.getContext();
        }
        if (viewType == TYPE_TEXT){
            view = inflater.inflate(R.layout.item_main_text, parent, false);
            return new TextHolder(view);
        }else if(viewType == TYPE_IMAGE){
            view = inflater.inflate(R.layout.item_main_img, parent, false);
            return new ImageHolder(view);
        }else if(viewType == TYPE_VIDEO){
            view = inflater.inflate(R.layout.item_main_video, parent, false);
            return new VideoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemBean bean = list.get(position);
        if (bean.getBodyType() == TYPE_TEXT){
            final TextHolder textHolder = (TextHolder) holder;
            textHolder.textTitlt.setText(bean.getTitle());
            CharSequence sequence = Html.fromHtml(bean.getContent());
            textHolder.textContent.setText(sequence);
            textHolder.textLike.setText(bean.getLikeNum() + "");
            textHolder.textComment.setText(bean.getCommentNum() + "");

            textHolder.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    textHolder.textLike.setText(StringUtils.addOne(textHolder.textLike.getText().toString()));

                }
            });
            textHolder.layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == HomeItem.TYPE_ARTICLE){
                        nextActivity(ArticleActivity.class);
                    }else if (bean.getType() == HomeItem.TYPE_TOPIC){
                        nextActivity(TopicActivity.class);
                    }
                }
            });
        }else if (bean.getBodyType() == TYPE_IMAGE){
            final ImageHolder imgHolder = (ImageHolder) holder;
            imgHolder.textTitlt.setText(bean.getTitle());
            CharSequence sequence = Html.fromHtml(bean.getContent());

            imgHolder.textContent.setText(sequence);
            imgHolder.textLike.setText(bean.getLikeNum() + "");
            imgHolder.textComment.setText(bean.getCommentNum() + "");

            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getImgUrl()))
                        .into(imgHolder.img);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getImgUrl())
                        .into(imgHolder.img);
            }

            imgHolder.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    imgHolder.textLike.setText(StringUtils.addOne(imgHolder.textLike.getText().toString()));
                }
            });
            imgHolder.layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == HomeItem.TYPE_ARTICLE){
                        Intent intent = new Intent(context, ArticleActivity.class);
                        intent.putExtra("article", DataUtils.getArticle());
                        context.startActivity(intent);
                        // nextActivity(ArticleActivity.class);
                    }else if (bean.getType() == HomeItem.TYPE_TOPIC){
                        nextActivity(TopicActivity.class);
                    }
                }
            });
        }else if(bean.getBodyType() == TYPE_VIDEO){
            final VideoHolder videoHolder = (VideoHolder) holder;
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

            videoHolder.layoutLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    videoHolder.textLike.setText(StringUtils.addOne(videoHolder.textLike.getText().toString()));
                }
            });
            videoHolder.layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getType() == HomeItem.TYPE_ARTICLE){
                        nextActivity(ArticleActivity.class);
                    }else if (bean.getType() == HomeItem.TYPE_TOPIC){
                        nextActivity(TopicActivity.class);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Log.e(TAG, "onViewDetachedFromWindow: " );
        if (holder instanceof HomeSuggestRVAdapter.VideoHolder){
            HomeSuggestRVAdapter.VideoHolder viewHolder = (HomeSuggestRVAdapter.VideoHolder)holder;
            if (viewHolder.videoPlayer.isInPlayingState()) {
                viewHolder.videoPlayer.onVideoPause();
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.e(TAG, "onViewAttachedToWindow: 2" );
        if (holder instanceof HomeSuggestRVAdapter.VideoHolder){
            HomeSuggestRVAdapter.VideoHolder viewHolder = (HomeSuggestRVAdapter.VideoHolder)holder;

            if (viewHolder.videoPlayer.isInPlayingState()) {
                viewHolder.videoPlayer.onVideoResume();
            }
        }
    }

    private void nextActivity(Class<?> cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

}
