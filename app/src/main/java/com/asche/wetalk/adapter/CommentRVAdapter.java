package com.asche.wetalk.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.fragment.FragmentDialogCommentDetail;
import com.asche.wetalk.util.EmoticonUtils;
import com.asche.wetalk.util.StringUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CommentRVAdapter extends RecyclerView.Adapter{

    private List<CommentItemBean> list;
    private Context context;

    // 这里用作评论详情页的监听器
    private OnItemClickListener onItemClickListener;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SIMPLE = 1;

    public CommentRVAdapter(List<CommentItemBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        }else if(viewType == TYPE_SIMPLE){
            View view = LayoutInflater.from(context).inflate(R.layout.item_comment_simple, parent, false);
            return new SimpleHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        CommentItemBean bean = list.get(position);

        if (bean.getType() == TYPE_NORMAL){
            final NormalHolder normalHolder = (NormalHolder)holder;
            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getAvatarUrl()))
                        .into(normalHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getAvatarUrl())
                        .into(normalHolder.imgAvatar);
            }

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

            normalHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normalHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            normalHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                            normalHolder.textLikeNum.setText(StringUtils.addOne(normalHolder.textLikeNum.getText().toString()));

                        }
                    });
                }
            });

            List<CommentItemBean> subList = bean.getSubList();
            if (subList != null){
                CommentRVAdapter subAdapter = new CommentRVAdapter(subList);
                normalHolder.recyclerViewSub.setAdapter(subAdapter);

                normalHolder.textMore.setVisibility(View.VISIBLE);
                normalHolder.textMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position);
                    }
                });
            }
        }else if (bean.getType() == TYPE_SIMPLE){
            SimpleHolder simpleHolder = (SimpleHolder)holder;
            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getAvatarUrl()))
                        .into(simpleHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getAvatarUrl())
                        .into(simpleHolder.imgAvatar);
            }

            simpleHolder.textName.setText(bean.getName());
            simpleHolder.textContent.setText(EmoticonUtils.parseEmoticon(bean.getContent()));

        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getType();
        switch (type){
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
        if (holder instanceof NormalHolder){
            ((NormalHolder) holder).textMore.setVisibility(View.GONE);
        }
    }

    public class NormalHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgLike, imgComment;
        private TextView textName, textContent, textTime, textLikeNum, textMore;
        private RecyclerView recyclerViewSub;
        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_item_comment_avatar);
            imgLike = itemView.findViewById(R.id.img_item_comment_like);
            imgComment = itemView.findViewById(R.id.img_item_comment_comment);
            textContent = itemView.findViewById(R.id.text_item_comment_content);
            textName = itemView.findViewById(R.id.text_item_comment_name);
            textTime = itemView.findViewById(R.id.text_item_comment_time);
            textLikeNum = itemView.findViewById(R.id.text_item_comment_likenum);
            textMore = itemView.findViewById(R.id.text_item_comment_more);
            recyclerViewSub = itemView.findViewById(R.id.recycler_item_comment);

            recyclerViewSub.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        }
    }

    public class SimpleHolder extends RecyclerView.ViewHolder{
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
