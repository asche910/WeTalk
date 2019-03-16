package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ArticleActivity;
import com.asche.wetalk.activity.HappenActivity;
import com.asche.wetalk.activity.TopicActivity;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.HappenItemBean;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.RequirementBean;
import com.asche.wetalk.bean.TimeLineBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.util.LoaderUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

public class TimeLineRVAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;

    public static final int ACTION_LIKE = 2;
    public static final int ACTION_COMMENT = 3;
    public static final int ACTION_COLLECT = 4;
    public static final int ACTION_HAPPEN = 5;

    private List<TimeLineBean> list;
    private Context context;

    public TimeLineRVAdapter(List<TimeLineBean> timeLineBeans) {
        this.list = timeLineBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_TEXT) {
            View view = inflater.inflate(R.layout.item_timeline_text, parent, false);
            return new TextHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_timeline_image, parent, false);
            return new ImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final TimeLineBean bean = list.get(position);
        final int actionType = bean.getTypeAction();

        if (!bean.isImage()) {
            final TextHolder textHolder = (TextHolder) holder;
            final HomeItem homeItem = bean.getHomeItem();
            switch (actionType) {
                case ACTION_HAPPEN:
                    textHolder.imgAction.setImageResource(R.drawable.ic_action_note);

                    HappenItemBean happenItemBean = bean.getHappenItemBean();
                    textHolder.textTime.setText(happenItemBean.getTime());
                    textHolder.textTitle.setText("发表了新的点滴");
                    textHolder.textContent.setText(happenItemBean.getContent());
                    break;
                case ACTION_COLLECT:
                    textHolder.imgAction.setImageResource(R.drawable.ic_action_collect);
                    break;
                case ACTION_COMMENT:
                    textHolder.imgAction.setImageResource(R.drawable.ic_action_comment);
                    break;
                case ACTION_LIKE:
                    textHolder.imgAction.setImageResource(R.drawable.ic_action_like);
                    break;
            }

            if (homeItem != null) {
                switch (homeItem.getItemType()) {
                    case HomeItem.TYPE_TOPIC:
                        TopicReplyBean topicReplyBean = (TopicReplyBean) homeItem;
                        textHolder.textTitle.setText(topicReplyBean.getTitle());
                        CharSequence sequence = Html.fromHtml(topicReplyBean.getBrief());

                        textHolder.textContent.setText(sequence);
                        textHolder.textTime.setText(topicReplyBean.getTime());
                        break;
                    case HomeItem.TYPE_REQUIREMENT:
                        RequirementBean requirementBean = (RequirementBean) homeItem;
                        textHolder.textTitle.setText(requirementBean.getTitle());
                        textHolder.textContent.setText(requirementBean.getBrief());
                        textHolder.textTime.setText(requirementBean.getTime());
                        break;
                    case HomeItem.TYPE_ARTICLE:
                        ArticleBean articleBean = (ArticleBean) homeItem;
                        textHolder.textTitle.setText(articleBean.getTitle());
                        textHolder.textContent.setText(articleBean.getBrief());
                        textHolder.textTime.setText(articleBean.getTime());
                        break;
                }
                textHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: " + homeItem.getItemType() );
                        switch (homeItem.getItemType()) {
                            case HomeItem.TYPE_TOPIC:
                                TopicReplyBean topicReplyBean = (TopicReplyBean) homeItem;
                                Intent intentTopic = new Intent(context, TopicActivity.class);
                                intentTopic.putExtra("topicReply", topicReplyBean);
                                context.startActivity(intentTopic);
                                break;
                            case HomeItem.TYPE_REQUIREMENT:
                                RequirementBean requirementBean = (RequirementBean) homeItem;
                                Intent intentRequirement = new Intent(context, ArticleActivity.class);
                                intentRequirement.putExtra("requirement", requirementBean);
                                context.startActivity(intentRequirement);
                                break;
                            case HomeItem.TYPE_ARTICLE:
                                ArticleBean articleBean = (ArticleBean) homeItem;
                                Intent intentArticle = new Intent(context, ArticleActivity.class);
                                intentArticle.putExtra("article", articleBean);
                                context.startActivity(intentArticle);
                                break;
                        }
                    }
                });
            }else {
                textHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, HappenActivity.class);
                        intent.putExtra("happenBean", bean.getHappenItemBean());
                        context.startActivity(intent);
                    }
                });
            }
        } else {
            ImageHolder imageHolder = (ImageHolder) holder;
            final HomeItem homeItem = bean.getHomeItem();
            switch (actionType) {
                case ACTION_HAPPEN:
                    imageHolder.imgAction.setImageResource(R.drawable.ic_action_note);

                    HappenItemBean happenItemBean = bean.getHappenItemBean();
                    LoaderUtils.loadImage(happenItemBean.getUrlList().get(0), context, imageHolder.imgCover);
                    imageHolder.textTime.setText(happenItemBean.getTime());
                    imageHolder.textContent.setText(happenItemBean.getContent());
                    break;
                case ACTION_COLLECT:
                    imageHolder.imgAction.setImageResource(R.drawable.ic_action_collect);

                    break;
                case ACTION_COMMENT:
                    imageHolder.imgAction.setImageResource(R.drawable.ic_action_comment);

                    break;
                case ACTION_LIKE:
                    imageHolder.imgAction.setImageResource(R.drawable.ic_action_like);
                    break;
            }

            if (homeItem != null) {
                switch (homeItem.getItemType()) {
                    case HomeItem.TYPE_TOPIC:
                        TopicReplyBean topicReplyBean = (TopicReplyBean) homeItem;
                        LoaderUtils.loadImage(topicReplyBean.getImgUrl(), context, imageHolder.imgCover);

                        imageHolder.textContent.setText(topicReplyBean.getBrief());
                        imageHolder.textTime.setText(topicReplyBean.getTime());
                        break;
                    case HomeItem.TYPE_REQUIREMENT:
                        RequirementBean requirementBean = (RequirementBean) homeItem;
                        LoaderUtils.loadImage(requirementBean.getImgUrl(), context, imageHolder.imgCover);
                        imageHolder.textContent.setText(requirementBean.getBrief());
                        imageHolder.textTime.setText(requirementBean.getTime());
                        break;
                    case HomeItem.TYPE_ARTICLE:
                        ArticleBean articleBean = (ArticleBean) homeItem;
                        LoaderUtils.loadImage(articleBean.getImgUrl(), context, imageHolder.imgCover);
                        imageHolder.textContent.setText(articleBean.getBrief());
                        imageHolder.textTime.setText(articleBean.getTime());
                        break;
                }
                imageHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: " + homeItem.getItemType() );
                        switch (homeItem.getItemType()) {
                            case HomeItem.TYPE_TOPIC:
                                TopicReplyBean topicReplyBean = (TopicReplyBean) homeItem;
                                Intent intentTopic = new Intent(context, TopicActivity.class);
                                intentTopic.putExtra("topicReply", topicReplyBean);
                                context.startActivity(intentTopic);
                                break;
                            case HomeItem.TYPE_REQUIREMENT:
                                RequirementBean requirementBean = (RequirementBean) homeItem;
                                Intent intentRequirement = new Intent(context, ArticleActivity.class);
                                intentRequirement.putExtra("requirement", requirementBean);
                                context.startActivity(intentRequirement);
                                break;
                            case HomeItem.TYPE_ARTICLE:
                                ArticleBean articleBean = (ArticleBean) homeItem;
                                Intent intentArticle = new Intent(context, ArticleActivity.class);
                                intentArticle.putExtra("article", articleBean);
                                context.startActivity(intentArticle);
                                break;
                        }
                    }
                });
            }else {
                imageHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, HappenActivity.class);
                        intent.putExtra("happenBean", bean.getHappenItemBean());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        TimeLineBean bean = list.get(position);
        if (bean.isImage()) {
            return TYPE_IMAGE;
        } else {
            return TYPE_TEXT;
        }
    }

    class TextHolder extends RecyclerView.ViewHolder {
        private TextView textTime, textTitle, textContent;
        private ImageView imgAction;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textTime = itemView.findViewById(R.id.text_timeline_time);
            textTitle = itemView.findViewById(R.id.text_timeline_title);
            textContent = itemView.findViewById(R.id.text_timeline_content);
            imgAction = itemView.findViewById(R.id.img_timeline_action);
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        private TextView textTime, textContent;
        private ImageView imgAction, imgCover;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            textTime = itemView.findViewById(R.id.text_timeline_time);
            textContent = itemView.findViewById(R.id.text_timeline_content);
            imgAction = itemView.findViewById(R.id.img_timeline_action);
            imgCover = itemView.findViewById(R.id.img_timeline_cover);
        }
    }
}
