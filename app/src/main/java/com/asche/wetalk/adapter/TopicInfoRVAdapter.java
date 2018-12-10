package com.asche.wetalk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.TopicActivity;
import com.asche.wetalk.bean.TopicBean;
import com.asche.wetalk.bean.TopicReplyItemBean;
import com.asche.wetalk.util.StringUtils;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;


public class TopicInfoRVAdapter extends RecyclerView.Adapter implements  PopupMenu.OnMenuItemClickListener {

    public static final int TYPE_TEXT = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 2;
    public static final int TYPE_HEADER = 3;

    private List<TopicReplyItemBean> list;
    private Context context;

    private int curPosition = -1;


    public static int n = 0;

    public TopicInfoRVAdapter(List<TopicReplyItemBean> list) {
        this.list = list;
    }

    private class TextHolder extends RecyclerView.ViewHolder {
        private TextView textName, textSignature, textContent;
        private TextView textLike, textComment;
        private ImageView imgLike, imgMore, imgAvatar;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_topic_author_name);
            textSignature = itemView.findViewById(R.id.text_topic_author_signature);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            imgMore = itemView.findViewById(R.id.img_item_main_more);
            imgAvatar = itemView.findViewById(R.id.img_topic_author_avatar);

        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        private TextView textName,textSignature, textContent;
        public TextView textLike, textComment;
        public ImageView img, imgLike, imgMore, imgAvatar;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_topic_author_name);
            textSignature = itemView.findViewById(R.id.text_topic_author_signature);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            img = itemView.findViewById(R.id.img_item_main);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            imgMore = itemView.findViewById(R.id.img_item_main_more);
            imgAvatar = itemView.findViewById(R.id.img_topic_author_avatar);

        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        private TextView textName, textSignature, textContent;
        private TextView textLike, textComment;
        public StandardGSYVideoPlayer videoPlayer;
        private ImageView imgLike, imgMore, imgAvatar;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_topic_author_name);
            textSignature = itemView.findViewById(R.id.text_topic_author_signature);
            textContent = itemView.findViewById(R.id.text_item_main_content);
            textLike = itemView.findViewById(R.id.text_item_main_likenum);
            textComment = itemView.findViewById(R.id.text_item_main_commentnum);
            videoPlayer = itemView.findViewById(R.id.video_item_main);
            imgLike = itemView.findViewById(R.id.img_item_main_like);
            imgMore = itemView.findViewById(R.id.img_item_main_more);
            imgAvatar = itemView.findViewById(R.id.img_topic_author_avatar);

        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder{
        TextView textTitle, textContent, textFollowerNum, textReplyNum;
        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_topic_title);
            textContent = itemView.findViewById(R.id.text_topic_content);
            textFollowerNum = itemView.findViewById(R.id.text_topic_followernum);
            textReplyNum = itemView.findViewById(R.id.text_topic_replynum);
        }
    }

    @Override
    public int getItemViewType(int position) {
        TopicReplyItemBean itemBean = list.get(position);
        if (itemBean.getBodyType() == TYPE_TEXT) {
            return TYPE_TEXT;
        } else if (itemBean.getBodyType() == TYPE_IMAGE) {
            return TYPE_IMAGE;
        }else if(itemBean.getBodyType() == TYPE_VIDEO) {
            return TYPE_VIDEO;
        } else if (itemBean.getBodyType() == TYPE_HEADER){
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: " + (n++) );
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == TYPE_TEXT) {
            view = inflater.inflate(R.layout.item_topic_reply_text, parent, false);
            return new TextHolder(view);
        } else if (viewType == TYPE_IMAGE) {
            view = inflater.inflate(R.layout.item_topic_reply_img, parent, false);
            return new ImageHolder(view);
        } else if (viewType == TYPE_VIDEO) {
            view = inflater.inflate(R.layout.item_topic_reply_video, parent, false);
            return new VideoHolder(view);
        } else if(viewType == TYPE_HEADER){
            view = inflater.inflate(R.layout.item_topic_reply_header, parent, false);
            return new HeaderHolder(view);
        }
        return null;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final TopicReplyItemBean bean = list.get(position);
        if (bean.getBodyType() == TYPE_TEXT) {
            final TextHolder textHolder = (TextHolder) holder;
            textHolder.textName.setText(bean.getAuthorName());
            textHolder.textSignature.setText(bean.getAuthorSignature());
            CharSequence sequence = Html.fromHtml(bean.getContent());
            textHolder.textContent.setText(sequence);
            textHolder.textLike.setText(bean.getLikeNum() + "");
            textHolder.textComment.setText(bean.getCommentNum() + "");

            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getAuthorAvatar()))
                        .into(textHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getAuthorAvatar())
                        .into(textHolder.imgAvatar);
            }

            textHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPosition = position;
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item_suggest, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(TopicInfoRVAdapter.this);
                }
            });

            textHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    textHolder.textLike.setText(StringUtils.addOne(textHolder.textLike.getText().toString()));

                }
            });

            textHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(TopicActivity.class);
                }
            });


        } else if (bean.getBodyType() == TYPE_IMAGE) {
            final ImageHolder imgHolder = (ImageHolder) holder;
            imgHolder.textName.setText(bean.getAuthorName());
            imgHolder.textSignature.setText(bean.getAuthorSignature());
            CharSequence sequence = Html.fromHtml(bean.getContent());

            imgHolder.textContent.setText(sequence);
            imgHolder.textLike.setText(bean.getLikeNum() + "");
            imgHolder.textComment.setText(bean.getCommentNum() + "");

            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getAuthorAvatar()))
                        .into(imgHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getAuthorAvatar())
                        .into(imgHolder.imgAvatar);
            }

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

            imgHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPosition = position;
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item_suggest, popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(TopicInfoRVAdapter.this);
                }
            });

            imgHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    imgHolder.textLike.setText(StringUtils.addOne(imgHolder.textLike.getText().toString()));
                }
            });

            imgHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(TopicActivity.class);
                }
            });
        } else if (bean.getBodyType() == TYPE_VIDEO) {
            final VideoHolder videoHolder = (VideoHolder) holder;
            videoHolder.textName.setText(bean.getAuthorName());
            videoHolder.textSignature.setText(bean.getAuthorSignature());
            CharSequence sequence = Html.fromHtml(bean.getContent());

            videoHolder.textContent.setText(sequence);
            videoHolder.textLike.setText(bean.getLikeNum() + "");
            videoHolder.textComment.setText(bean.getCommentNum() + "");

            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getAuthorAvatar()))
                        .into(videoHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getAuthorAvatar())
                        .into(videoHolder.imgAvatar);
            }


            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            try {
                Glide.with(context)
                        .load(bean.getImgUrl())
                        .into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

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
                    popupMenu.setOnMenuItemClickListener(TopicInfoRVAdapter.this);
                }
            });

            videoHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    videoHolder.textLike.setText(StringUtils.addOne(videoHolder.textLike.getText().toString()));
                }
            });

            videoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(TopicActivity.class);
                }
            });
        } else if (bean.getBodyType() == TYPE_HEADER){
            HeaderHolder headerHolder = (HeaderHolder)holder;
            TopicBean topicBean = bean.getTopicBean();
            headerHolder.textTitle.setText(topicBean.getName());
            headerHolder.textContent.setText(topicBean.getContent());
            String textFollowerNum = String.format("关注:%d - ", topicBean.getFollowerNum());

            headerHolder.textFollowerNum.setText(textFollowerNum);
            headerHolder.textReplyNum.setText("回答:" + topicBean.getReplyNum());
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
                Toast.makeText(context, "举报", Toast.LENGTH_SHORT).show();
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
        Log.e(TAG, "onViewDetachedFromWindow: " );
        if (holder instanceof TopicInfoRVAdapter.VideoHolder) {
            TopicInfoRVAdapter.VideoHolder viewHolder = (TopicInfoRVAdapter.VideoHolder) holder;
            if (viewHolder.videoPlayer.isInPlayingState()) {
                viewHolder.videoPlayer.onVideoPause();
            }
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Log.e(TAG, "onViewAttachedToWindow: " );
        if (holder instanceof TopicInfoRVAdapter.VideoHolder) {
            TopicInfoRVAdapter.VideoHolder viewHolder = (TopicInfoRVAdapter.VideoHolder) holder;

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
