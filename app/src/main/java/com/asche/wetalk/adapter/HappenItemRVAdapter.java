package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.HappenActivity;
import com.asche.wetalk.bean.HappenItemBean;
import com.asche.wetalk.service.AudioUtils;
import com.asche.wetalk.service.VibrateUtils;
import com.asche.wetalk.util.EmoticonUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.asche.wetalk.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HappenItemRVAdapter extends RecyclerView.Adapter<HappenItemRVAdapter.ViewHolder> {

    private List<HappenItemBean> list;
    private Context context;

    public HappenItemRVAdapter(List<HappenItemBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_happen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        HappenItemBean bean = list.get(position);

        LoaderUtils.loadImage(bean.getUserAvatar(), context, holder.imgAvatar);

        holder.userName.setText(bean.getUserName());
        holder.content.setText(EmoticonUtils.parseEmoticon(bean.getContent()));
        holder.time.setText(bean.getTime());

        if (bean.getUrlList() != null){
            holder.recycView.setAdapter(new GridImgRVAdapter(bean.getUrlList()));
        }

        holder.btnLike.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.btnLike.setBackground(context.getDrawable(R.drawable.ic_item_like_press));
                    holder.btnLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(context, R.anim.anim_like));

                    VibrateUtils.vibrateLike();
                    AudioUtils.playLike();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HappenActivity.class);
                intent.putExtra("happenBean", list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView userName;
        private TextView content;
        private RecyclerView recycView;
        private TextView time;
        private Button btnLike;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.img_item_happen_avatar);
            userName = itemView.findViewById(R.id.text_item_happen_name);
            content = itemView.findViewById(R.id.text_item_happen_content);
            recycView = itemView.findViewById(R.id.recycler_item_happen);
            time = itemView.findViewById(R.id.text_item_happen_time);
            btnLike = itemView.findViewById(R.id.btn_item_happen_like);

            GridLayoutManager layoutManager = new GridLayoutManager(itemView.getContext(), 3);
            recycView.setLayoutManager(layoutManager);
        }
    }
}
