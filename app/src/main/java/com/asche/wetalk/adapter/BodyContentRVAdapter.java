package com.asche.wetalk.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.BodyContentBean;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class BodyContentRVAdapter extends RecyclerView.Adapter {

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    private List<BodyContentBean> list;
    private Context context;
    private Map<Integer, HashMap<String, Integer>> imgSizeMap = new HashMap<>();

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
        if (bean.getType() == TYPE_TEXT) {
            TextHolder textHolder = (TextHolder) holder;
            CharSequence sequence = Html.fromHtml(bean.getText());
            textHolder.textView.setText(sequence);
            textHolder.textView.setMovementMethod(LinkMovementMethod.getInstance());

        } else if (bean.getType() == TYPE_IMAGE) {
            final ImageHolder imageHolder = (ImageHolder) holder;

            RequestOptions requestOptions = new RequestOptions();
//                    .placeholder(R.drawable.img_avatar);
            if (imgSizeMap != null){
                Map values = imgSizeMap.get(position);
                if (values != null){
//                    requestOptions = requestOptions.override((int)values.get("width"), (int)values.get("height"));
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)imageHolder.imageView.getLayoutParams();
                    params.width = (int)values.get("width");
                    params.height = (int)values.get("height");
//                    imageHolder.imageView.setLayoutParams(params);
                }
            }

            Glide.with(context)
                    .load(bean.getImgUrl())
                    .apply(requestOptions)
                    .into(new DrawableImageViewTarget(imageHolder.imageView){
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            super.onResourceReady(resource, transition);
                            Log.e(TAG, "onResourceReady: --------->" + position + ": " + imageHolder.imageView.getWidth() + "*" + imageHolder.imageView.getHeight() );

                            if (imgSizeMap != null){
                                Map values = imgSizeMap.get(position);
                                if (values == null){
                                    HashMap<String, Integer> map = new HashMap<>();

                                    int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                                    int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                                    imageHolder.imageView.measure(w, h);
                                    int width = imageHolder.imageView.getMeasuredWidth();
                                    int height = imageHolder.imageView.getMeasuredHeight();

                                    map.put("width", width);
                                    map.put("height", height);
                                    imgSizeMap.put(position, map);

                                    Log.e(TAG, "onBindViewHolder: --> " + position + ": " + width + "*" + height );
                                }
                            }

                        }
                    });

//            Log.e(TAG, "onBindViewHolder: " + imageHolder.imageView.getWidth() + "*" +  imageHolder.imageView.getHeight() );
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
}
