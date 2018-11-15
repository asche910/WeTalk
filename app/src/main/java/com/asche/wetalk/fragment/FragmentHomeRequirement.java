package com.asche.wetalk.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.asche.wetalk.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHomeRequirement extends Fragment {

    private RefreshLayout refreshLayout;
    private final String TAG = "FragmentHomeRequirement";

    private VideoView videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_requirement, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshLayout = getView().findViewById(R.id.refreshLayout_home_requirement);
        videoView = getView().findViewById(R.id.video_item_main);



        refreshLayout.setOnLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onLoadMore: " );
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: " );
                refreshLayout.finishRefresh(2000);
            }
        });

        String path = Environment.getExternalStorageDirectory().getPath()+"/video.mp4";
        Uri uri = Uri.parse(path);//将路径转换成uri
        videoView.setVideoURI(uri);
//        video.setMediaController(new MediaController(Main2Activity.this));//显示控制栏
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });

    }
}
