package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.UserHomeActivity;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.util.LoaderUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SuggestUserRVAdapter extends RecyclerView.Adapter<SuggestUserRVAdapter.ViewHolder> {

    private List<UserBean> list;
    private Context context;

    public SuggestUserRVAdapter(List<UserBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_suggest_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final UserBean userBean = list.get(position);
        LoaderUtils.loadImage(userBean.getImgAvatar(), context, holder.imgAvatar);
        holder.textName.setText(userBean.getNickName());

        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btnFollow.getText().toString().equals("已关注")){
                    holder.btnFollow.setText("关注");
                    holder.btnFollow.setBackgroundResource(R.drawable.bg_btn_blue);
                }else {
                    holder.btnFollow.setText("已关注");
                    holder.btnFollow.setBackgroundResource(R.drawable.bg_btn_gray);
                    Toast.makeText(context, "关注成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail = new Intent(context, UserHomeActivity.class);
                intentDetail.putExtra("user", userBean);
                context.startActivity(intentDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView textName;
        private Button btnFollow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_item_user_avatar);
            textName = itemView.findViewById(R.id.text_item_user_name);
            btnFollow = itemView.findViewById(R.id.btn_item_user_follow);
        }
    }
}
