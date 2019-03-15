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
import com.asche.wetalk.activity.ArticlePublishActivity;
import com.asche.wetalk.bean.DraftItemBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.bean.HomeItem.TYPE_TOPIC;

public class DraftRVAdapter extends RecyclerView.Adapter<DraftRVAdapter.ViewHolder> {

    private List<DraftItemBean> list;
    private Context context;

    public DraftRVAdapter(List<DraftItemBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_draft, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DraftItemBean bean = list.get(position);
        final int pos = holder.getLayoutPosition();
        final int type = bean.getType();

        holder.textTitle.setText(bean.getTitle());
        holder.textContent.setText(bean.getContent());
        holder.textTime.setText(bean.getTime());

        holder.textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_TOPIC) {
                    Toast.makeText(context, "Go to topic page!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, ArticlePublishActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("object", bean);
                    context.startActivity(intent);
                    Toast.makeText(context, "Go to edit page!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Go to edit page!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, ArticlePublishActivity.class);
                intent.putExtra("type", type);
                intent.putExtra("object", bean);
                context.startActivity(intent);
            }
        });

        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_draft, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_draft_publish:
                                break;
                            case R.id.menu_draft_delete:
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
        TextView textTitle, textContent, textTime;
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
