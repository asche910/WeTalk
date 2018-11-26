package com.asche.wetalk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.AgendaItemBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaRVAdapter extends RecyclerView.Adapter<AgendaRVAdapter.ViewHolder> {

    private List<AgendaItemBean> list;
    private OnItemClickListener onItemClickListener;

    public AgendaRVAdapter(List<AgendaItemBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        AgendaItemBean bean = list.get(position);
        holder.textTitle.setText(bean.getContent());
        holder.textTime.setText(bean.getTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_item_agenda_title);
            textTime = itemView.findViewById(R.id.text_item_agenda_time);
        }
    }
}
