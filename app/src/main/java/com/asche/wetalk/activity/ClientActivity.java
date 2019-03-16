package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.NotificationRVAdapter;
import com.asche.wetalk.bean.NotificationItemBean;
import com.asche.wetalk.data.UserUtils;
import com.asche.wetalk.helper.FlexibleScrollView;
import com.asche.wetalk.util.TimeUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ClientActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack;
    private TextView textTitle;

    private WaveSwipeRefreshLayout swipeRefreshLayout;
    private FlexibleScrollView flexibleScrollView;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotificationRVAdapter clientRVAdapter;
    public static List<NotificationItemBean> clientBeanList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        swipeRefreshLayout = findViewById(R.id.refreshLayout_client);
        flexibleScrollView = findViewById(R.id.scroll_client);
        recyclerView = findViewById(R.id.recycler_client);

        flexibleScrollView.setEnablePullDown(false);

        if (clientBeanList.isEmpty()){
            clientBeanList.add(new NotificationItemBean(UserUtils.getUser(0), TimeUtils.getCurrentTime(), "你好，我是小灵机器人！"));
            clientBeanList.add(new NotificationItemBean(UserUtils.getUser(1), "11.13 15:43", "Hello, World!"));
            clientBeanList.add(new NotificationItemBean(UserUtils.getUser(2), "今天19：42", "我们看待这个世界的方式，决定了我们度过一生的方式。写的很好，很深刻"));
        }

        clientRVAdapter = new NotificationRVAdapter(clientBeanList, true);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(clientRVAdapter);


        textTitle.setText("我的客户");
        imgBack.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
