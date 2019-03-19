package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.adapter.TopicChipRVAdapter;
import com.asche.wetalk.adapter.TopicPageRVAdapter;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.ItemBean;
import com.asche.wetalk.bean.TopicPageBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.helper.GlideImageLoader;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.util.StringUtils;
import com.asche.wetalk.util.TimeUtils;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.asche.wetalk.fragment.FragmentHomeSuggest.imgSrc;
import static com.asche.wetalk.fragment.FragmentHomeSuggest.imgSrc_;
import static com.asche.wetalk.fragment.FragmentHomeSuggest.videoSrc;
import static com.asche.wetalk.fragment.FragmentHomeSuggest.videoSrc_;
import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class FragmentDiscoverTopic extends Fragment {

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private List<TopicPageBean> topicPageBeanList = new ArrayList<>();
    private TopicPageRVAdapter topicPageRVAdapter;
    private LinearLayoutManager layoutManager;


    List<String> tagList = new ArrayList<>();
    List<HomeItem> itemBeanList = new ArrayList<>();

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
        recyclerView = getView().findViewById(R.id.recycler_discover_topic);

        List<Integer> imgsList = new ArrayList<>();
        imgsList.add(R.drawable.img_avatar);
        imgsList.add(R.drawable.img_avatar_default);
        imgsList.add(R.drawable.bg_weather);
        imgsList.add(R.drawable.bg);

        List<String> titleList = new ArrayList<>();
        titleList.add("历史上有哪些如神一般存在的人物？");
        titleList.add("你很长时间都不能忘记的一部电影是什么？");
        titleList.add("被当成外国人是怎样的体验？");
        titleList.add("如何评价丁香园售卖 1980 元一双的矫形鞋垫？");


        for (int i = 0; i < 10; i++) {
            tagList.add(StringUtils.expandLength(i % 3, "计算"));
        }


        itemBeanList.add(new TopicReplyBean("0", "1", "2", getResources().getString(R.string.topic_reply), TimeUtils.getCurrentTime(), null, null, 65, 44));
        itemBeanList.add(new TopicReplyBean("0", "1", "2", getResources().getString(R.string.topic_reply), TimeUtils.getCurrentTime(), imgSrc, videoSrc, 65, 44));
        itemBeanList.add(new TopicReplyBean("0", "1", "2", getResources().getString(R.string.topic_reply), TimeUtils.getCurrentTime(), R.drawable.img_avatar + "", null, 65, 44));
        itemBeanList.add(new TopicReplyBean("0", "1", "2", getResources().getString(R.string.topic_reply), TimeUtils.getCurrentTime(), imgSrc_, videoSrc_, 65, 44));

//        itemBeanList.add(new ItemBean(2, 0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 666, 65, null, null));
//        itemBeanList.add(new ItemBean(2, 1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 126, 63, R.drawable.img_avatar + "", null));
//        itemBeanList.add(new ItemBean(2, 2, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 456, 64, imgSrc, videoSrc));
//        itemBeanList.add(new ItemBean(2, 1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 789, 62, R.drawable.img_avatar + "", null));


        topicPageBeanList.add(new TopicPageBean(imgsList, titleList));
        topicPageBeanList.add(new TopicPageBean("热门标签"));
        topicPageBeanList.add(new TopicPageBean(tagList));
        topicPageBeanList.add(new TopicPageBean("热门话题"));
        topicPageBeanList.add(new TopicPageBean(0, itemBeanList));


        topicPageRVAdapter = new TopicPageRVAdapter(topicPageBeanList);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(topicPageRVAdapter);


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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

      /*  if (banner != null) {
            if (isVisibleToUser){
                banner.startAutoPlay();
            }else {
                banner.stopAutoPlay();
            }
        }*/
    }

}
