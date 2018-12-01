package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.DiscoverHappenPublishActivity;
import com.asche.wetalk.helper.Glide4Engine;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

public class GridImgPublishRVAdapter extends RecyclerView.Adapter{

    private List<ImageInfo> imageInfoList = new ArrayList<>();
    private List<String> list;
    private Context context;
    private OnSizeChangedListener onSizeChangedListener;
    private OnItemClickListener onItemClickListener;

    public static final int TYPE_IMAGE = 0;
    public static final int TYPE_FOOTER = 1;

    public GridImgPublishRVAdapter(List<String> uriList) {
        this.list = uriList;
    }

    public void setOnSizeChangedListener(OnSizeChangedListener onSizeChangedListener) {
        this.onSizeChangedListener = onSizeChangedListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (imageInfoList.size() != list.size()-1) {
            initImg();
        }
        if (viewType == TYPE_IMAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_happen_publish_img, parent, false);
            return new ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer_happen_publish, parent, false);
            return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        String content = list.get(position);
        if (!content.equals("1")){
            ViewHolder holder1 = (ViewHolder)holder;
            String uri = list.get(position);
            try {
                Glide.with(context)
                        .load(Uri.parse(uri))
                        .apply(new RequestOptions().centerCrop())
                        .into(holder1.img);
            } catch (Exception e) {
                e.printStackTrace();
                Glide.with(context)
                        .load(uri)
                        .apply(new RequestOptions().centerCrop())
                        .into(holder1.img);
            }

            holder1.img.setOnClickListener(new View.OnClickListener() {
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

            holder1.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: " + position);
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, list.size() - position);
                    onSizeChangedListener.onSizeChanged(list.size());
                }
            });
        }else {
            FooterHolder footerHolder = (FooterHolder)holder;
            footerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).equals("1")){
            return TYPE_FOOTER;
        }
        return TYPE_IMAGE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img, imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_item_happen_publish_img);
            imgDelete = itemView.findViewById(R.id.img_item_happen_publish_delete);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder{
        public FooterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * 初始化图片放大控件的数据
     */
    void initImg() {
        imageInfoList.clear();
        for (int i = 0; i < list.size() - 1; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setOriginUrl(list.get(i));
            imageInfo.setThumbnailUrl(list.get(i));
            imageInfoList.add(imageInfo);
        }
    }
}
