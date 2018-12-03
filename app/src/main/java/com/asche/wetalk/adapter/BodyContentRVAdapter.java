package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.BodyContentBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

import static com.asche.wetalk.MyApplication.getContext;
import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class BodyContentRVAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    private List<BodyContentBean> list;
    private Context context;
    private Map<Integer, HashMap<String, Integer>> imgSizeMap = new HashMap<>();
    private List<ImageInfo> imageInfoList;
    private Map<Integer, Integer> indexMap = new HashMap<>();

    class TextHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_item_body);
        }
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_item_body);
        }
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        public StandardGSYVideoPlayer videoPlayer;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            videoPlayer = itemView.findViewById(R.id.video_item_body);
        }
    }

    public BodyContentRVAdapter(List<BodyContentBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_TEXT) {
            View view = inflater.inflate(R.layout.item_body_text, parent, false);
            return new TextHolder(view);
        } else if (viewType == TYPE_IMAGE) {
            View view = inflater.inflate(R.layout.item_body_img, parent, false);
            return new ImageHolder(view);
        } else if (viewType == TYPE_VIDEO) {
            View view = inflater.inflate(R.layout.item_body_video, parent, false);
            return new VideoHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        BodyContentBean bean = list.get(position);

        if (imageInfoList == null) {
            imageInfoList = new ArrayList<>();
            ImageInfo imageInfo;
            int index = 0;
            for (int i = 0; i < list.size(); i++){
                if (list.get(i).getType() == TYPE_IMAGE){
                    imageInfo = new ImageInfo();
                    imageInfo.setOriginUrl(list.get(i).getImgUrl());
                    imageInfo.setThumbnailUrl(list.get(i).getImgUrl());
                    imageInfoList.add(imageInfo);
                    indexMap.put(i, index++);
                    imageInfo = null;
                }
            }
        }

        if (bean.getType() == TYPE_TEXT) {
            TextHolder textHolder = (TextHolder) holder;
            CharSequence sequence = Html.fromHtml(bean.getText());
            textHolder.textView.setText(sequence);
            textHolder.textView.setMovementMethod(LinkMovementMethod.getInstance());

        } else if (bean.getType() == TYPE_IMAGE) {
            final ImageHolder imageHolder = (ImageHolder) holder;
       /*     if (imgSizeMap != null){
                Map values = imgSizeMap.get(position);
                if (values != null){
//                    requestOptions = requestOptions.override((int)values.get("width"), (int)values.get("height"));
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imageHolder.imageView.getLayoutParams();
                    params.width = (int)values.get("width");
                    params.height = (int)values.get("height");
                    imageHolder.imageView.setLayoutParams(params);
                }
            }*/

            Glide.with(context)
                    .load(bean.getImgUrl())
//                    .apply(requestOptions)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            int originW = resource.getIntrinsicWidth();
                            int originH = resource.getIntrinsicHeight();

                            Rect rect = handleProportion(originW, originH);

                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imageHolder.imageView.getLayoutParams();
                            params.width = rect.width();
                            params.height = rect.height();
                            imageHolder.imageView.setLayoutParams(params);
                            imageHolder.imageView.setImageDrawable(resource);

                           /* if (imgSizeMap != null){
                                Map values = imgSizeMap.get(position);
                                if (values == null){
                                    HashMap<String, Integer> map = new HashMap<>();

                                    map.put("width", rect.width());
                                    map.put("height", rect.height());
                                    imgSizeMap.put(position, map);

                                }
                            }*/
                        }
                    });

            imageHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImagePreview
                            .getInstance()
                            .setContext(context)
                            .setImageInfoList(imageInfoList)
                            .setIndex(indexMap.get(position))
                            .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                            .setEnableClickClose(false)
                            .setEnableDragClose(true)
                            .setShowCloseButton(true)
                            .start();
                }
            });
        } else if (bean.getType() == TYPE_VIDEO) {
            final VideoHolder videoHolder = (VideoHolder) holder;

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Glide.with(context)
                    .load(bean.getImgUrl())
                    .into(imageView);

            StandardGSYVideoPlayer videoPlayer = videoHolder.videoPlayer;
            videoPlayer.setThumbImageView(imageView);
            videoPlayer.setUp(bean.getVideoUrl(), true, null, null, "");
            videoPlayer.getTitleTextView().setVisibility(View.GONE);
            videoPlayer.getBackButton().setVisibility(View.GONE);
            videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoHolder.videoPlayer.startWindowFullscreen(context, false, true);
                }
            });
            videoPlayer.setShowFullAnimation(true);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        BodyContentBean bean = list.get(position);
        return bean.getType();
    }

    // 比例处理
    private Rect handleProportion(int width, int height){
        Rect rect = new Rect(0, 0, width, height);

        Resources resources = getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int screenW = dm.widthPixels;
        float density = dm.density;

        // 大于此宽的压缩至此宽显示；小于则原尺寸显示
        int standW = (int)(screenW - 20 * density);
        if(width > standW){
            rect.bottom = standW * (height / width);
        }
        return rect;
    }
}
