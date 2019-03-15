package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ArticleActivity;
import com.asche.wetalk.activity.TopicActivity;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.DraftItemBean;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.RequirementBean;
import com.asche.wetalk.bean.TopicReplyBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class WorkRVAdapter extends RecyclerView.Adapter<WorkRVAdapter.ViewHolder> {

    private List<DraftItemBean> list;
    private Context context;

    public WorkRVAdapter(List<DraftItemBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_draft, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final DraftItemBean bean = list.get(position);
        final int pos = holder.getLayoutPosition();
        final HomeItem homeItem = bean.getHomeItem();

        holder.textTitle.setText(bean.getTitle());
        holder.textContent.setText(bean.getContent());
        holder.textTime.setText(bean.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (bean.getType()){
                    case HomeItem.TYPE_TOPIC:
                        Intent intentTopic = new Intent(context, TopicActivity.class);
                        intentTopic.putExtra("topicReply", (TopicReplyBean)homeItem);
                        context.startActivity(intentTopic);
                        break;
                    case HomeItem.TYPE_REQUIREMENT:
                        Intent intentReu = new Intent(context, ArticleActivity.class);
                        intentReu.putExtra("requirement", (RequirementBean)homeItem);
                        context.startActivity(intentReu);
                        break;
                    case HomeItem.TYPE_ARTICLE:
                        Intent intentArt = new Intent(context, ArticleActivity.class);
                        intentArt.putExtra("article", (ArticleBean)homeItem);
                        context.startActivity(intentArt);
                        break;
                }
            }
        });

        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_work, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_work_edit:
                                break;
                            case R.id.menu_work_delete:
                                // position是最初的position
                                notifyItemRemoved(pos);
                                list.remove(pos);
                                Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle,  textContent, textTime;
        ImageView imgMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_item_draft_title);
            textContent = itemView.findViewById(R.id.text_item_draft_content);
            textTime = itemView.findViewById(R.id.text_item_draft_time);
            imgMore = itemView.findViewById(R.id.img_item_draft_more);
        }
    }
}
