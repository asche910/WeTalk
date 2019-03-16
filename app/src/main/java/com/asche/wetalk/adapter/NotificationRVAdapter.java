package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.BaseActivity;
import com.asche.wetalk.activity.ChatActivity;
import com.asche.wetalk.bean.NotificationItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.util.LoaderUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.activity.ClientActivity.clientBeanList;

public class NotificationRVAdapter extends RecyclerView.Adapter {

    private final int TYPE_CHAT = 0;
    private List<NotificationItemBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    // 是否是ClientActivity的activity
    private boolean isClient;

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

    public NotificationRVAdapter(List<NotificationItemBean> list, boolean isClient) {
        this.list = list;
        this.isClient = isClient;
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final NotificationItemBean bean = list.get(position);
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
                    intent.putExtra("chatWith", list.get(holder.getLayoutPosition()).getUserBean());
                    context.startActivity(intent);
                }
            });

            chatHolder.imgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context, v);
                    MenuInflater menuInflater = popupMenu.getMenuInflater();
                    menuInflater.inflate(R.menu.menu_item_notification, popupMenu.getMenu());
                    if (isClient){
                        popupMenu.getMenu().findItem(R.id.menu_item_noti_client).setVisible(false);
                    }
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        int pos = holder.getLayoutPosition();
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.menu_item_noti_client:
                                    clientBeanList.add(bean);
                                    Toast.makeText(context, "添加成功！", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.menu_item_noti_top:
                                    NotificationItemBean itemBean = list.get(pos);
                                    list.remove(pos);
                                    list.add(0, itemBean);
                                    notifyItemMoved(pos, 0);
                                    notifyItemRangeChanged(0, pos + 1);
//                                    notifyDataSetChanged();
                                    break;
                                case R.id.menu_item_noti_delete:
                                    list.remove(pos);
                                    notifyItemRemoved(pos);
                                    break;
                            }
                            return false;
                        }
                    });
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
