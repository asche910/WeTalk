package com.asche.wetalk.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asche.wetalk.R;
import com.asche.wetalk.util.EmoticonUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.activity.ChatActivity.editChatInput;

public class EmoticonRVAdapter extends RecyclerView.Adapter<EmoticonRVAdapter.ViewHolder> {

    private List<Bitmap> list;
    private Context context;

    public EmoticonRVAdapter(List<Bitmap> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_emoticon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Bitmap bitmap = list.get(position);

        Glide.with(context)
                .load(bitmap)
                .into(holder.imgEmoticon);

        holder.imgEmoticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpannableString spannableString = EmoticonUtils.parseEmoticon("[emoji-" + position + "]");
                editChatInput.append(spannableString);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgEmoticon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEmoticon = itemView.findViewById(R.id.img_emoticon);
        }
    }
}
