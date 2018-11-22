package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.BodyContentRVAdapter;
import com.asche.wetalk.bean.BodyContentBean;
import com.asche.wetalk.util.BodyContentUtil;
import com.asche.wetalk.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TopicActivity extends AppCompatActivity {
    private TextView textTitle;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BodyContentRVAdapter bodyContentRVAdapter;
    private List<BodyContentBean> bodyContentBeanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        textTitle = findViewById(R.id.text_topic_title);
        recyclerView = findViewById(R.id.recycler_topic);

        bodyContentBeanList = BodyContentUtil.parseHtml(getResources().getString(R.string.topic_reply));
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        bodyContentRVAdapter = new BodyContentRVAdapter(bodyContentBeanList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bodyContentRVAdapter);
        recyclerView.setItemViewCacheSize(Integer.MAX_VALUE);

    }
}
