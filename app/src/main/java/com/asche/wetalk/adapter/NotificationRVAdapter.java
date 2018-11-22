package com.asche.wetalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.NotificationItemBean;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationRVAdapter extends RecyclerView.Adapter{

    private final int TYPE_FRIEND = 0;
    private List<NotificationItemBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    class FriendHolder extends RecyclerView.ViewHolder{
        private ImageView imgAvatar, imgMore;
        private TextView textName, textContent, textTime;
        public FriendHolder(@NonNull View itemView) {
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
        if (context == null){
            context = parent.getContext();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if (viewType == TYPE_FRIEND){
            view = inflater.inflate(R.layout.item_noti_friend, parent, false);
            return new FriendHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NotificationItemBean bean = list.get(position);
        if (bean.getType() == TYPE_FRIEND){
            FriendHolder friendHolder = (FriendHolder)holder;

            friendHolder.textName.setText(bean.getName());
            friendHolder.textContent.setText(bean.getContent());
            friendHolder.textTime.setText(bean.getTime());

            try {
                Glide.with(context)
                        .load(Integer.parseInt(bean.getImgUrl()))
                        .into(friendHolder.imgAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(bean.getImgUrl())
                        .into(friendHolder.imgAvatar);
            }

            friendHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
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
        if (bean.getType() == TYPE_FRIEND){
            return TYPE_FRIEND;
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
