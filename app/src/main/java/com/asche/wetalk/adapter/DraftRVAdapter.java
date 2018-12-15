package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ArticlePublishActivity;
import com.asche.wetalk.bean.DraftItemBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DraftRVAdapter extends RecyclerView.Adapter<DraftRVAdapter.ViewHolder> {

    private List<DraftItemBean> list;
    private Context context;

    public static final int TYPE_ARTICLE = 0;
    public static final int TYPE_REQUIREMENT = 1;
    public static final int TYPE_TOPIC = 2;

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textContent, textTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_item_draft_title);
            textContent = itemView.findViewById(R.id.text_item_draft_content);
            textTime = itemView.findViewById(R.id.text_item_draft_time);
        }
    }
}
