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
import com.asche.wetalk.bean.ItemBean;
import com.asche.wetalk.bean.TopicPageBean;
import com.asche.wetalk.helper.GlideImageLoader;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.util.StringUtils;
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
import static com.asche.wetalk.fragment.FragmentHomeSuggest.videoSrc;
import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class FragmentDiscoverTopic extends Fragment {

    private WaveSwipeRefreshLayout swipeRefreshLayout;


    private RecyclerView recyclerView;
    private List<TopicPageBean> topicPageBeanList = new ArrayList<>();
    private TopicPageRVAdapter topicPageRVAdapter;
    private LinearLayoutManager layoutManager;


    List<String> tagList = new ArrayList<>();
    List<ItemBean> itemBeanList = new ArrayList<>();

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
        imgsList.add(R.drawable.img_wallet);
        imgsList.add(R.drawable.img_camera);

        List<String> titleList = new ArrayList<>();
        titleList.add("Title_1");
        titleList.add("Title_2");
        titleList.add("Title_3");
        titleList.add("Title_4");


        for (int i = 0; i < 10; i++) {
            tagList.add(StringUtils.expandLength(i % 3, "计算"));
        }


        itemBeanList.add(new ItemBean(2, 0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 666, 65, null, null));
        itemBeanList.add(new ItemBean(2, 1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 126, 63, R.drawable.img_avatar + "", null));
        itemBeanList.add(new ItemBean(2, 2, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 456, 64, imgSrc, videoSrc));
        itemBeanList.add(new ItemBean(2, 1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 789, 62, R.drawable.img_avatar + "", null));


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

                        swipeRefreshLayout.setRefreshing(false);
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
