package com.asche.wetalk.activity;

import android.os.Bundle;
import android.util.Log;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.TopicInfoRVAdapter;
import com.asche.wetalk.bean.TopicBean;
import com.asche.wetalk.bean.TopicReplyItemBean;
import com.asche.wetalk.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TopicInfoActivity extends BaseActivity {

    private static final String TAG = "TopicInfoActivity";

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TopicInfoRVAdapter topicInfoRVAdapter;
    private List<TopicReplyItemBean> topicReplyItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_info);

        recyclerView = findViewById(R.id.recycler_topic_info);

        if (topicReplyItemList.isEmpty()) {
            TopicBean topicBean = new TopicBean("历史上有哪些如神一般存在的人物？", "历史上有哪些如神一般存在的人物？历史上有哪些如神一般存在的人物？历史上有哪些如神一般存在的人物？历史上有哪些如神一般存在的人物？", "2019-01-09 10:24", 472, 82);
            topicReplyItemList.add(new TopicReplyItemBean(topicBean));
            for (int i = 0; i < 6; i++)
                topicReplyItemList.add(DataUtils.getTopicReplyItem());
        }

        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        topicInfoRVAdapter = new TopicInfoRVAdapter(topicReplyItemList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(topicInfoRVAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; i < topicReplyItemList.size(); i++) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder instanceof TopicInfoRVAdapter.VideoHolder) {
                TopicInfoRVAdapter.VideoHolder videoHolder = (TopicInfoRVAdapter.VideoHolder) holder;
                if (videoHolder.videoPlayer.isInPlayingState()) {
                    videoHolder.videoPlayer.onVideoPause();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < topicReplyItemList.size(); i++) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder instanceof TopicInfoRVAdapter.VideoHolder) {
                TopicInfoRVAdapter.VideoHolder videoHolder = (TopicInfoRVAdapter.VideoHolder) holder;
                if (videoHolder.videoPlayer.isInPlayingState()) {
                    videoHolder.videoPlayer.onVideoReset();
                }
            }
        }
    }
}
