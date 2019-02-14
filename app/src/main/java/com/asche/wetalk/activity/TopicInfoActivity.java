package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.TopicInfoRVAdapter;
import com.asche.wetalk.bean.TopicBean;
import com.asche.wetalk.bean.TopicReplyItemBean;
import com.asche.wetalk.data.DataUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class TopicInfoActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "TopicInfoActivity";

    private ImageView imgBack, imgMore;
    private TextView textToolbarTitle;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TopicInfoRVAdapter topicInfoRVAdapter;
    private List<TopicReplyItemBean> topicReplyItemList = new ArrayList<>();

    // 数据对象
    private TopicBean topicBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_info);


        imgBack = findViewById(R.id.img_toolbar_back);
        textToolbarTitle = findViewById(R.id.text_toolbar_title);
        imgMore = findViewById(R.id.img_toolbar_more);
        recyclerView = findViewById(R.id.recycler_topic_info);


//        topicBean = (TopicBean)getIntent().getSerializableExtra("topicInfo");

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


        textToolbarTitle.setText("话题详情");
        imgMore.setImageResource(R.drawable.ic_more_light);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_more:
                Toast.makeText(this, "More！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
