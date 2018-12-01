package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class FragmentDiscoverTopic extends Fragment {

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_discover_topic, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout = getView().findViewById(R.id.header_discover_topic);

        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(2000);
                            Log.e(TAG, "run: Thread finished!" );
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).start();
            }
        });

//        swipeRefreshLayout.setColorSchemeColors(R.color.white);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser );
    }

}
