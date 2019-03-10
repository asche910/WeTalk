package com.asche.wetalk.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.fragment.FragmentDialogCommentDetail;
import com.asche.wetalk.service.AudioUtils;
import com.asche.wetalk.service.VibrateUtils;
import com.asche.wetalk.util.EmoticonUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.asche.wetalk.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

/**
 * 评论的RecyclerView超级适配器
 */
public class CommentRVAdapter extends RecyclerView.Adapter {

    private List<CommentItemBean> list;
    private Context context;

    // 评论显示的类型
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SIMPLE = 1;

    // 用于不同场景TYPE_SIMPLE的点击事件; 默认为CLICK_BOTTOM
    public static final int CLICK_BOTTOM = 0; // 打开评论Fragment列表
    public static final int CLICK_DETAIL = 1; // 进入评论详情页
    public static final int CLICK_COMMENT = 2; // 处于详情页，回复此条评论
    public static final int CLICK_IMG_COMMENT = 3; // 设置imgComment的点击事件
    private int clickType;


    // 这里用作评论详情页的监听器 --> normalHolder.textContent
    private OnItemClickListener onItemClickListener;

    // 用于TYPE_SIMPLE不同场景对应不同的点击事件
    private OnItemMoreClickListener onItemMoreClickListener;


    public CommentRVAdapter(List<CommentItemBean> list) {
        this.list = list;
    }

    public CommentRVAdapter(List<CommentItemBean> list, int clickType) {
        this.list = list;
        this.clickType = clickType;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemMoreClickListener(OnItemMoreClickListener onItemMoreClickListener) {
        this.onItemMoreClickListener = onItemMoreClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
            return new NormalHolder(view);
        } else if (viewType == TYPE_SIMPLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_comment_simple, parent, false);
            return new SimpleHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        CommentItemBean bean = list.get(position);

        if (bean.getType() == TYPE_NORMAL) {
            final NormalHolder normalHolder = (NormalHolder) holder;
            LoaderUtils.loadImage(bean.getAvatarUrl(), context, normalHolder.imgAvatar);

            normalHolder.textName.setText(bean.getName());
            normalHolder.textContent.setText(EmoticonUtils.parseEmoticon(bean.getContent()));
            normalHolder.textTime.setText(bean.getTime());
            if (bean.getLikeNum() != 0) {
                normalHolder.textLikeNum.setText(bean.getLikeNum() + "");
            }

            normalHolder.textContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });


            normalHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Item Menu!", Toast.LENGTH_SHORT).show();
                }
            });

            normalHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normalHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                    normalHolder.imgLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim_like));
                    normalHolder.textLikeNum.setText(StringUtils.addOne(normalHolder.textLikeNum.getText().toString()));

                    VibrateUtils.vibrateLike();
                    AudioUtils.playLike();
                }
            });

            normalHolder.imgComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemMoreClickListener.onItemMoreClick(position, CLICK_IMG_COMMENT);
                }
            });

            List<CommentItemBean> subList = bean.getSubList();
            final int pos = position;
            if (subList != null) {
                CommentRVAdapter subAdapter = new CommentRVAdapter(subList, CLICK_DETAIL);
                normalHolder.recyclerViewSub.setVisibility(View.VISIBLE);
                normalHolder.recyclerViewSub.setAdapter(subAdapter);

                if (subList.size() > 2) {
                    normalHolder.textMore.setVisibility(View.VISIBLE);
                }

                normalHolder.textMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position);
                    }
                });
                subAdapter.setOnItemMoreClickListener(new OnItemMoreClickListener() {
                    @Override
                    public void onItemMoreClick(int position, int args) {
                        onItemMoreClickListener.onItemMoreClick(pos, args);
                    }
                });
            }
        } else if (bean.getType() == TYPE_SIMPLE) {
            SimpleHolder simpleHolder = (SimpleHolder) holder;

            LoaderUtils.loadImage(bean.getAvatarUrl(), context, simpleHolder.imgAvatar);

            simpleHolder.textName.setText(bean.getName());

            if (clickType == CLICK_COMMENT) {
                simpleHolder.textContent.setMaxLines(Integer.MAX_VALUE);
            }
            simpleHolder.textContent.setText(EmoticonUtils.parseEmoticon(bean.getContent()));

            simpleHolder.textContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemMoreClickListener.onItemMoreClick(position, clickType);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getType();
        switch (type) {
            case TYPE_SIMPLE:
                return TYPE_SIMPLE;
            case TYPE_NORMAL:
                return TYPE_NORMAL;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    /*    if (holder instanceof NormalHolder){
            ((NormalHolder) holder).textMore.setVisibility(View.GONE);
            ((NormalHolder) holder).recyclerViewSub.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof NormalHolder) {
            ((NormalHolder) holder).textMore.setVisibility(View.GONE);
            ((NormalHolder) holder).recyclerViewSub.setVisibility(View.GONE);
        }
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgLike, imgComment, imgMore;
        private TextView textName, textContent, textTime, textLikeNum, textMore;
        private RecyclerView recyclerViewSub;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_item_comment_avatar);
            imgLike = itemView.findViewById(R.id.img_item_comment_like);
            imgComment = itemView.findViewById(R.id.img_item_comment_comment);
            imgMore = itemView.findViewById(R.id.img_item_comment_more);
            textContent = itemView.findViewById(R.id.text_item_comment_content);
            textName = itemView.findViewById(R.id.text_item_comment_name);
            textTime = itemView.findViewById(R.id.text_item_comment_time);
            textLikeNum = itemView.findViewById(R.id.text_item_comment_likenum);
            textMore = itemView.findViewById(R.id.text_item_comment_more);
            recyclerViewSub = itemView.findViewById(R.id.recycler_item_comment);

            recyclerViewSub.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        }
    }

    public class SimpleHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView textName, textContent;

        public SimpleHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_item_comment_avatar);
            textContent = itemView.findViewById(R.id.text_item_comment_content);
            textName = itemView.findViewById(R.id.text_item_comment_name);
        }
    }
}
