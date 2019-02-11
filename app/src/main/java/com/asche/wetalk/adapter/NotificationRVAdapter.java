package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ChatActivity;
import com.asche.wetalk.bean.NotificationItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.util.LoaderUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationRVAdapter extends RecyclerView.Adapter {

    private final int TYPE_CHAT = 0;
    private List<NotificationItemBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    class ChatHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar, imgMore;
        private TextView textName, textContent, textTime;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_noti_avatar);
            imgMore = itemView.findViewById(R.id.img_noti_more);
            textName = itemView.findViewById(R.id.text_noti_name);
            textContent = itemView.findViewById(R.id.text_noti_content);
            textTime = itemView.findViewById(R.id.text_noti_time);
        }
    }

    public NotificationRVAdapter(List<NotificationItemBean> list) {
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
        if (viewType == TYPE_CHAT) {
            view = inflater.inflate(R.layout.item_noti_friend, parent, false);
            return new ChatHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NotificationItemBean bean = list.get(position);
        if (bean.getType() == TYPE_CHAT) {
            ChatHolder chatHolder = (ChatHolder) holder;

            UserBean userBean = bean.getUserBean();

            chatHolder.textName.setText(userBean.getNickName());
            chatHolder.textContent.setText(bean.getContent());
            chatHolder.textTime.setText(bean.getTime());

            LoaderUtils.loadImage(userBean.getImgAvatar(), context, chatHolder.imgAvatar);

            chatHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("chatWith", list.get(position).getUserBean());
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        NotificationItemBean bean = list.get(position);
        if (bean.getType() == TYPE_CHAT) {
            return TYPE_CHAT;
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
