package com.asche.wetalk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.TopicInfoActivity;
import com.asche.wetalk.bean.TopicBean;
import com.asche.wetalk.util.LoaderUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RanklistRVAdapter extends RecyclerView.Adapter<RanklistRVAdapter.ViewHolder> {

    private List<TopicBean> list;
    private Context context;

    public RanklistRVAdapter(List<TopicBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_ranklist, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TopicBean bean = list.get(position);

        holder.textIndex.setText(String.valueOf(position + 1));

        holder.textContent.setText(bean.getName());
        holder.textFollowNum.setText(bean.getFollowerNum() + "人关注");

        LoaderUtils.loadImage(bean.getCoverUrl(), context, holder.imgCover);

        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TopicInfoActivity.class);
                intent.putExtra("topicInfo", bean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textIndex, textContent, textFollowNum;
        ImageView imgCover;
        LinearLayout layoutMain;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIndex = itemView.findViewById(R.id.text_item_ranklist_index);
            textContent = itemView.findViewById(R.id.text_item_ranklist_content);
            textFollowNum = itemView.findViewById(R.id.text_item_ranklist_follownum);
            imgCover = itemView.findViewById(R.id.img_item_ranklist_cover);
            layoutMain = itemView.findViewById(R.id.layout_home_ranklist);
        }
    }
}
