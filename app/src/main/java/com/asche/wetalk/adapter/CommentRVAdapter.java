package com.asche.wetalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.util.EmoticonUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentRVAdapter extends RecyclerView.Adapter{

    private List<CommentItemBean> list;
    private Context context;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SIMPLE = 1;

    public CommentRVAdapter(List<CommentItemBean> list) {
        this.list = list;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
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

            normalHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    normalHolder.imgLike.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            normalHolder.imgLike.setImageResource(R.drawable.ic_like_pressed);
                        }
                    });
                }
            });
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

    public class NormalHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgLike, imgComment;
        private TextView textName, textContent, textTime, textLikeNum;
        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_item_comment_avatar);
            imgLike = itemView.findViewById(R.id.img_item_comment_like);
            imgComment = itemView.findViewById(R.id.img_item_comment_comment);
            textContent = itemView.findViewById(R.id.text_item_comment_content);
            textName = itemView.findViewById(R.id.text_item_comment_name);
            textTime = itemView.findViewById(R.id.text_item_comment_time);
            textLikeNum = itemView.findViewById(R.id.text_item_comment_likenum);
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
