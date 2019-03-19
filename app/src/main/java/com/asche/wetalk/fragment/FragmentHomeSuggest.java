package com.asche.wetalk.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.LoadingBean;
import com.asche.wetalk.bean.SuggestUserBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.data.DataUtils;
import com.asche.wetalk.data.UserUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

public class FragmentHomeSuggest extends Fragment {

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HomeSuggestRVAdapter adapter;
    private List<HomeItem> itemBeanList = new ArrayList<>();

    private int firstView = 0;
    private int lasttView = -1;

    public static StandardGSYVideoPlayer videoPlayer;

    // 是否正在加载更多数据
    private boolean isLoading;

    public static String videoSrc = "http://f10.v1.cn/site/15538790.mp4.f40.mp4";
    public static String videoSrc_ = "http://f04.v1.cn/transcode/14353624MOBILET2.mp4";
    public static String imgSrc = "http://img.mms.v1.cn/static/mms/images/2018-10-18/201810181131393298.jpg";
    public static String imgSrc_ = "http://img.mms.v1.cn/static/mms/images//201607201445331953.jpg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_suggest, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout = getView().findViewById(R.id.header_home_suggest);
        recyclerView = getView().findViewById(R.id.recycle_home_suggest);


        if (itemBeanList.isEmpty()) {
            List<UserBean> userBeanList = new ArrayList<>();
            for (int i = 0; i < 6; i++){
                userBeanList.add(UserUtils.getUser());
            }

            itemBeanList.add(DataUtils.getArticle());
            itemBeanList.add(DataUtils.getRequirement());
            itemBeanList.add(DataUtils.getTopicReply());
            itemBeanList.add(new SuggestUserBean(userBeanList));
            itemBeanList.add(DataUtils.getTopicReply());
            itemBeanList.add(DataUtils.getArticle());
            itemBeanList.add(new LoadingBean());
        }

        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new HomeSuggestRVAdapter(itemBeanList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                firstView = layoutManager.findLastVisibleItemPosition() - 1;

                // 滑动至底部
                if (firstView + 2 == itemBeanList.size() && !isLoading){
                    loadMore();
                    isLoading = true;
                }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                }
            });
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 10){
                adapter.notifyItemRangeInserted(itemBeanList.size() - 4, 3);
                isLoading = false;
            }
            return false;
        }
    });

    private void loadMore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                itemBeanList.add(itemBeanList.size() - 1, DataUtils.getArticle());
                itemBeanList.add(itemBeanList.size() - 1, DataUtils.getTopicReply());
                itemBeanList.add(itemBeanList.size() - 1, DataUtils.getRequirement());

                Message message = new Message();
                message.what = 10;
                handler.sendMessage(message);
            }
        }).start();
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

    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayer != null && videoPlayer.isInPlayingState()) {
            videoPlayer.onVideoPause();
        }
    }
}
