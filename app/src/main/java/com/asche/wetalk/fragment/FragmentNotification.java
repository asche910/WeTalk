package com.asche.wetalk.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ChatActivity;
import com.asche.wetalk.adapter.NotificationRVAdapter;
import com.asche.wetalk.bean.NotificationItemBean;
import com.asche.wetalk.data.UserUtils;
import com.asche.wetalk.helper.FlexibleScrollView;
import com.asche.wetalk.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentNotification extends Fragment {


    private WaveSwipeRefreshLayout swipeRefreshLayout;
    private FlexibleScrollView flexibleScrollView;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotificationRVAdapter notificationRVAdapter;
    public static List<NotificationItemBean> notiBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_notification, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout = getView().findViewById(R.id.header_notification);
        flexibleScrollView = getView().findViewById(R.id.scroll_notification);
        recyclerView = getView().findViewById(R.id.recycler_notification);

        flexibleScrollView.setEnablePullDown(false);

        if (notiBeanList.isEmpty()) {
            notiBeanList.add(new NotificationItemBean(UserUtils.getUser(0), TimeUtils.getCurrentTime(), "你好，我是小灵机器人！"));
            notiBeanList.add(new NotificationItemBean(UserUtils.getUser(1), "11.13 15:43", "Hello, World!"));
            notiBeanList.add(new NotificationItemBean(UserUtils.getUser(2), "今天19：42", "我们看待这个世界的方式，决定了我们度过一生的方式。写的很好，很深刻"));
            notiBeanList.add(new NotificationItemBean(UserUtils.getUser(4), "11.13 22:45", "Hello, World!"));
            notiBeanList.add(new NotificationItemBean(UserUtils.getUser(3), "昨天19：42", "很是深刻，思维方式决定了我们的人生和格局"));
        }

        notificationRVAdapter = new NotificationRVAdapter(notiBeanList);
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationRVAdapter);

/*        notificationRVAdapter.setOnItemClickListener(new NotificationRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra("user", ...);
                startActivity(intent);
            }
        });*/

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
}
