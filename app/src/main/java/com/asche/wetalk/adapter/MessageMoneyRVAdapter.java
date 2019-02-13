package com.asche.wetalk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ChatActivity;
import com.asche.wetalk.bean.MessageMoneyBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.data.UserUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.asche.wetalk.util.TimeUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageMoneyRVAdapter extends RecyclerView.Adapter<MessageMoneyRVAdapter.ViewHolder> {

    private List<MessageMoneyBean> list;
    private Context context;

    public MessageMoneyRVAdapter(List<MessageMoneyBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_money, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MessageMoneyBean bean = list.get(position);
        // TODO get UserBean from username
        final UserBean userBean = UserUtils.getUser();

        LoaderUtils.loadImage(userBean.getImgAvatar(), context, holder.imgAvatar);
        holder.textNickname.setText(userBean.getNickName());
        holder.textLastMessage.setText(bean.getLastMessage());
        holder.textStartTime.setText(bean.getTime());


        if (!bean.isEnd()) {
            final Handler handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case 100:
                            String countTime = TimeUtils.getCountTime(bean.getTime());
                            if (countTime != null) {
                                holder.textCountTime.setText("剩余时间 " + countTime);
                            } else {
                                holder.textCountTime.setText("已结束");
                            }
                            break;
                    }
                    return false;
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            Message message = new Message();
                            message.what = 100;
                            handler.sendMessage(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }else {
            holder.textCountTime.setText("已结束");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("chatWith", userBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView textNickname, textLastMessage, textStartTime, textCountTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_message_money_avatar);
            textNickname = itemView.findViewById(R.id.text_message_money_name);
            textLastMessage = itemView.findViewById(R.id.text_message_money_content);
            textStartTime = itemView.findViewById(R.id.text_message_money_starttime);
            textCountTime = itemView.findViewById(R.id.text_message_money_counttime);
        }
    }
}
