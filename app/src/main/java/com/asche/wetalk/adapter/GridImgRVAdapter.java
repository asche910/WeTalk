package com.asche.wetalk.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asche.wetalk.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

/**
 * 动态页item中显示多个图片的adapter
 */
public class GridImgRVAdapter extends RecyclerView.Adapter<GridImgRVAdapter.ViewHolder> {

    private List<String> uriList;
    private Context context;
    private List<ImageInfo> imageInfoList = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_item_happen_img);
        }
    }

    public GridImgRVAdapter(List<String> list){
        uriList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        if (imageInfoList.isEmpty()){
            initImg();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_happen_img, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String uri = uriList.get(position);
        Glide.with(context)
                .load(uri)
                .apply(new RequestOptions().centerCrop())
                .into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePreview
                        .getInstance()
                        .setContext(context)
                        .setImageInfoList(imageInfoList)
                        .setIndex(position)
                        .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                        .setEnableClickClose(false)
                        .setEnableDragClose(true)
                        .setShowCloseButton(true)
                        .start();
            }
        });
    }

    /**
     * 初始化图片放大控件的数据
     */
    void initImg(){
        for (int i = 0; i < uriList.size(); i++){
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setOriginUrl(uriList.get(i));
            imageInfo.setThumbnailUrl(uriList.get(i));
            imageInfoList.add(imageInfo);
        }
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

}