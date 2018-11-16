package com.asche.wetalk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.ItemBean;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;


public class HomeSuggestRVAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    private List<ItemBean> list;
    private Context context;

    public HomeSuggestRVAdapter(List<ItemBean> list) {
        this.list = list;
    }

    private class TextHolder extends RecyclerView.ViewHolder{
        private TextView textTitlt, textContent;
        private TextView textLike, textComment;
        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
        }
    }

    private class ImageHolder extends RecyclerView.ViewHolder{
        private TextView textTitlt, textContent;
        private TextView textLike, textComment;
        private ImageView img;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            img = itemView.findViewById(R.id.img_item_main);
        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder{
        private TextView textTitlt, textContent;
        private TextView textLike, textComment;
        public StandardGSYVideoPlayer videoPlayer;
        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            textTitlt = itemView.findViewById(R.id.text_item_main_title);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            videoPlayer = itemView.findViewById(R.id.video_item_main);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ItemBean itemBean = list.get(position);
        if (itemBean.getType() == TYPE_TEXT){
            return TYPE_TEXT;
        }else if(itemBean.getType() == TYPE_IMAGE){
            return TYPE_IMAGE;
        }if (itemBean.getType() == TYPE_VIDEO){
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
        ItemBean bean = list.get(position);
        if (bean.getType() == TYPE_TEXT){
            TextHolder textHolder = (TextHolder) holder;
            textHolder.textTitlt.setText(bean.getTitle());
            textHolder.textContent.setText(bean.getContent());
            textHolder.textLike.setText(bean.getLikeNum() + "");
            textHolder.textComment.setText(bean.getCommentNum() + "");
        }else if (bean.getType() == TYPE_IMAGE){
            ImageHolder imgHolder = (ImageHolder) holder;
            imgHolder.textTitlt.setText(bean.getTitle());
            imgHolder.textContent.setText(bean.getContent());
            imgHolder.textLike.setText(bean.getLikeNum() + "");
            imgHolder.textComment.setText(bean.getCommentNum() + "");
            Glide.with(context)
                    .load(Integer.parseInt(bean.getImgUrl()))
                    .into(imgHolder.img);
        }else if(bean.getType() == TYPE_VIDEO){
            final VideoHolder videoHolder = (VideoHolder) holder;
            videoHolder.textTitlt.setText(bean.getTitle());
            videoHolder.textContent.setText(bean.getContent());
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

}
