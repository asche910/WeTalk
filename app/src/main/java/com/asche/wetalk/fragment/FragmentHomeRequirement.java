package com.asche.wetalk.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.asche.wetalk.R;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class FragmentHomeRequirement extends Fragment {

    private final String TAG = "FragmentHomeRequirement";

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private StandardGSYVideoPlayer videoPlayer;
    public static OrientationUtils orientationUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_requirement, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout = getView().findViewById(R.id.header_home_requirement);
        videoPlayer = getView().findViewById(R.id.video_item_main);


        String videoSrc = "http://f10.v1.cn/site/15542573.mp4.f40.mp4";
        String imgSrc = "http://img.mms.v1.cn/static/mms/images/2018-11-14/201811140932452264.jpg";

        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(getActivity())
                .load(imgSrc)
                .into(imageView);

        orientationUtils = new OrientationUtils(getActivity(), videoPlayer);
        orientationUtils.setEnable(true);

        videoPlayer.setUp(videoSrc, false, "美女诈骗9男友200万 感谢被抓：收不住");
        videoPlayer.setThumbImageView(imageView);
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.startWindowFullscreen(getContext(), false, true);
            }
        });

        videoPlayer.setIsTouchWiget(true);
//        videoPlayer.startPlayLogic();

        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).start();
            }
        });

    }

}
