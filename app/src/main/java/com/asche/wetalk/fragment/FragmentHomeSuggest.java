package com.asche.wetalk.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.bean.ItemBean;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

public class FragmentHomeSuggest extends Fragment {

    private PhoenixHeader header;
    private BallPulseFooter footer;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HomeSuggestRVAdapter adapter;
    private List<ItemBean> itemBeanList = new ArrayList<>();

    private int firstView = 0;
    private int lasttView = -1;

    public static StandardGSYVideoPlayer videoPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_suggest, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        header = getView().findViewById(R.id.header_home_suggest);
        footer = getView().findViewById(R.id.footer_home_suggest);
        recyclerView = getView().findViewById(R.id.recycle_home_suggest);

        final String videoSrc = "http://f10.v1.cn/site/15538790.mp4.f40.mp4";
        String videoSrc_ = "http://f04.v1.cn/transcode/14353624MOBILET2.mp4";
        String imgSrc = "http://img.mms.v1.cn/static/mms/images/2018-10-18/201810181131393298.jpg";
        String imgSrc_ = "http://img.mms.v1.cn/static/mms/images//201607201445331953.jpg";


        itemBeanList.add(new ItemBean(0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 666, 65, null, null));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 126, 63, R.drawable.img_avatar + "", null));
        itemBeanList.add(new ItemBean(2, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 456, 64, imgSrc, videoSrc));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 789, 62, R.drawable.img_avatar + "", null));

        itemBeanList.add(new ItemBean(2, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 666, 65, imgSrc_, videoSrc_));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 126, 63, R.drawable.img_avatar + "", null));
        itemBeanList.add(new ItemBean(0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 456, 64, null, null));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 789, 62, R.drawable.img_avatar + "", null));


        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new HomeSuggestRVAdapter(itemBeanList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                firstView = layoutManager.findLastVisibleItemPosition() - 1;

                if (firstView < 0) {
                    firstView = 0;
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE && firstView != lasttView) {
                    lasttView = firstView;
                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(lasttView);
                    if (viewHolder instanceof HomeSuggestRVAdapter.VideoHolder) {
                        Log.e(TAG, "onScrollStateChanged: -------------------------------->");
                        HomeSuggestRVAdapter.VideoHolder holder = (HomeSuggestRVAdapter.VideoHolder) viewHolder;
                        videoPlayer = holder.videoPlayer;

                        if (!videoPlayer.isInPlayingState())
                            videoPlayer.startPlayLogic();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, "setUserVisibleHint: " + isVisibleToUser);
        if (isVisibleToUser) {
            if (videoPlayer != null) {
                // videoPlayer.onVideoResume();
            }
        } else {
            if (videoPlayer != null && videoPlayer.isInPlayingState()) {
                videoPlayer.onVideoPause();
            }
        }
    }
}
