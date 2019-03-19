package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.LoadingBean;
import com.asche.wetalk.data.DataUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


public class FragmentHomeRequirement extends Fragment {

    private final String TAG = "FragmentHomeRequirement";

    private WaveSwipeRefreshLayout swipeRefreshLayout;


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HomeSuggestRVAdapter adapter;
    private List<HomeItem> itemBeanList = new ArrayList<>();

    // 是否正在加载更多数据
    private boolean isLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_requirement, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout = getView().findViewById(R.id.header_home_requirement);
        recyclerView = getView().findViewById(R.id.recycle_home_requirement);


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

        if (itemBeanList.isEmpty()) {
            for (int i = 0; i < 4; i++)
                itemBeanList.add(DataUtils.getRequirement(i));
            for (int i = 0; i < 4; i++)
                itemBeanList.add(DataUtils.getRequirement(i));
            itemBeanList.add(new LoadingBean());
        }

        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new HomeSuggestRVAdapter(itemBeanList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int pos = layoutManager.findLastVisibleItemPosition() + 1;
                if (pos == itemBeanList.size() && !isLoading){
                    loadMore();
                    isLoading = true;
                }
            }
        });
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 10){
                adapter.notifyItemRangeInserted(itemBeanList.size() - 4, 4);
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
                for (int i = 0; i < 4; i++)
                    itemBeanList.add(itemBeanList.size() - 1, DataUtils.getRequirement(i));

                Message message = new Message();
                message.what = 10;
                handler.sendMessage(message);
            }
        }).start();
    }
}
