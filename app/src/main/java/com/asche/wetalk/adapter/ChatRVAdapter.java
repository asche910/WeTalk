package com.asche.wetalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.ChatItemBean;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRVAdapter extends RecyclerView.Adapter {

    public static final int TYPE_CHAT_ME = 0;
    public static final int TYPE_CHAT_FRIEND = 1;
    private List<ChatItemBean> list;
    private Context context;

    class FriendViewHolder extends RecyclerView.ViewHolder {
        private TextView textContent;
        private ImageView imgAvatar;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_item_chat_content);
            imgAvatar = itemView.findViewById(R.id.img_item_chat_avatar);
        }
    }

    class MeViewHolder extends RecyclerView.ViewHolder {
        private TextView textContent;
        private ImageView imgAvatar;

        public MeViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_item_chat_content);
            imgAvatar = itemView.findViewById(R.id.img_item_chat_avatar);
        }
    }

    public ChatRVAdapter(List<ChatItemBean> list) {
        this.list = list;
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
            meViewHolder.textContent.setText(bean.getContent());

            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getImgAvatar()))
                        .into(meViewHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getImgAvatar())
                        .into(meViewHolder.imgAvatar);
            }

        }else if (bean.getType() == TYPE_CHAT_FRIEND){
            FriendViewHolder friendViewHolder = (FriendViewHolder) holder;
            friendViewHolder.textContent.setText(bean.getContent());

            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getImgAvatar()))
                        .into(friendViewHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getImgAvatar())
                        .into(friendViewHolder.imgAvatar);
            }

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
