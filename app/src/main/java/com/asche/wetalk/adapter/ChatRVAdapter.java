package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.BaseActivity;
import com.asche.wetalk.activity.UserHomeActivity;
import com.asche.wetalk.bean.ChatItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.util.EmoticonUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRVAdapter extends RecyclerView.Adapter {

    public static final int TYPE_CHAT_ME = 0;
    public static final int TYPE_CHAT_FRIEND = 1;
    private List<ChatItemBean> list;
    private Context context;
    private UserBean userBean;

    class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView textContent;
        private ImageView imgAvatar, imgContent;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_item_chat_content);
            imgAvatar = itemView.findViewById(R.id.img_item_chat_avatar);
            imgContent = itemView.findViewById(R.id.img_item_chat_imgcontent);
        }
    }

    class MeViewHolder extends RecyclerView.ViewHolder {
        private TextView textContent;
        private ImageView imgAvatar, imgContent;

        public MeViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_item_chat_content);
            imgAvatar = itemView.findViewById(R.id.img_item_chat_avatar);
            imgContent = itemView.findViewById(R.id.img_item_chat_imgcontent);

        }
    }

    public ChatRVAdapter(List<ChatItemBean> list) {
        this.list = list;
    }

    public ChatRVAdapter(List<ChatItemBean> list, UserBean userBean) {
        this.list = list;
        this.userBean = userBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == TYPE_CHAT_ME){
            view = inflater.inflate(R.layout.item_chat_me, parent, false);
            return new MeViewHolder(view);
        }else if (viewType == TYPE_CHAT_FRIEND){
            view = inflater.inflate(R.layout.item_chat_friend, parent, false);
            return new FriendViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatItemBean bean = list.get(position);
        if (bean.getType() == TYPE_CHAT_ME){
            MeViewHolder meViewHolder = (MeViewHolder) holder;

            if (bean.getImgUrl() != null){
                Glide.with(context)
                        .load(bean.getImgUrl())
                        .into(meViewHolder.imgContent);
                meViewHolder.imgContent.setVisibility(View.VISIBLE);
                meViewHolder.textContent.setVisibility(View.GONE);
            }else {
                meViewHolder.textContent.setText(EmoticonUtils.parseEmoticon(bean.getContent()));
            }

            LoaderUtils.loadImage(BaseActivity.getCurUser().getImgAvatar(), context, meViewHolder.imgAvatar);

            meViewHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserHomeActivity.class);
                    intent.putExtra("user", BaseActivity.getCurUser());
                    context.startActivity(intent);
                }
            });
        }else if (bean.getType() == TYPE_CHAT_FRIEND){
            FriendViewHolder friendViewHolder = (FriendViewHolder) holder;

            if (bean.getImgUrl() != null){
                Glide.with(context)
                        .load(bean.getImgUrl())
                        .into(friendViewHolder.imgContent);
                friendViewHolder.imgContent.setVisibility(View.VISIBLE);
                friendViewHolder.textContent.setVisibility(View.GONE);
            }else {
                friendViewHolder.textContent.setText(EmoticonUtils.parseEmoticon(bean.getContent()));
            }

            LoaderUtils.loadImage(userBean.getImgAvatar(), context, friendViewHolder.imgAvatar);

            friendViewHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserHomeActivity.class);
                    intent.putExtra("user", userBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        if (holder instanceof  FriendViewHolder){
            ((FriendViewHolder) holder).imgContent.setVisibility(View.GONE);
            ((FriendViewHolder) holder).textContent.setVisibility(View.VISIBLE);
        }else if (holder instanceof MeViewHolder){
            ((MeViewHolder) holder).imgContent.setVisibility(View.GONE);
            ((MeViewHolder) holder).textContent.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatItemBean bean = list.get(position);
        if (bean.getType() == TYPE_CHAT_ME) {
            return TYPE_CHAT_ME;
        } else if (bean.getType() == TYPE_CHAT_FRIEND) {
            return TYPE_CHAT_FRIEND;
        }
        return super.getItemViewType(position);
    }
}
