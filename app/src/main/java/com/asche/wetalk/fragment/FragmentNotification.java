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

import java.util.ArrayList;
import java.util.List;

public class FragmentNotification extends Fragment {


    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private NotificationRVAdapter notificationRVAdapter;
    private List<NotificationItemBean> notiBeanList = new ArrayList<>();

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
        recyclerView = getView().findViewById(R.id.recycler_notification);

        notiBeanList.add(new NotificationItemBean(0, "Asche", "11.13 15:43", "Hello, World!", R.drawable.img_avatar + ""));
        notiBeanList.add(new NotificationItemBean(0, "Donna裹love橙", "今天19：42", "我们看待这个世界的方式，决定了我们度过一生的方式。写的很好，很深刻", "https://cdn2.jianshu.io/assets/default_avatar/3-9a2bcc21a5d89e21dafc73b39dc5f582.jpg"));
        notiBeanList.add(new NotificationItemBean(0, "米洛斯", "11.13 22:45", "Hello, World!", R.drawable.img_avatar_default + ""));
        notiBeanList.add(new NotificationItemBean(0, "Judy妞", "昨天19：42", "很是深刻，思维方式决定了我们的人生和格局", "https://cdn2.jianshu.io/assets/default_avatar/7-0993d41a595d6ab6ef17b19496eb2f21.jpg"));

        notificationRVAdapter = new NotificationRVAdapter(notiBeanList);
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(notificationRVAdapter);

        notificationRVAdapter.setOnItemClickListener(new NotificationRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getContext(), ChatActivity.class));
            }
        });

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
