package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.WorkRVAdapter;
import com.asche.wetalk.bean.DraftItemBean;
import com.asche.wetalk.util.StringUtils;
import com.asche.wetalk.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentWorkTopic extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    WorkRVAdapter workRVAdapter;
    private static List<DraftItemBean> draftItemBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.recycler_work);

        if (draftItemBeanList.isEmpty()) {
            draftItemBeanList.add(new DraftItemBean(1, "Title Title Title Title ", StringUtils.expandLength(30, "文本消息"), TimeUtils.getCurrentTime()));
            draftItemBeanList.add(new DraftItemBean(1, "Title Title Title Title ", StringUtils.expandLength(30, "文本"), TimeUtils.getCurrentTime()));
            draftItemBeanList.add(new DraftItemBean(1, "Title Title Title Title ", StringUtils.expandLength(30, "文本"), TimeUtils.getCurrentTime()));
            draftItemBeanList.add(new DraftItemBean(1, "Title Title Title Title ", StringUtils.expandLength(30, "文本消息"), TimeUtils.getCurrentTime()));
            draftItemBeanList.add(new DraftItemBean(1, "Title Title Title Title ", StringUtils.expandLength(30, "文本"), TimeUtils.getCurrentTime()));
            draftItemBeanList.add(new DraftItemBean(1, "Title Title Title Title ", StringUtils.expandLength(30, "文本"), TimeUtils.getCurrentTime()));
            draftItemBeanList.add(new DraftItemBean(1, "Title Title Title Title ", StringUtils.expandLength(30, "文本"), TimeUtils.getCurrentTime()));
        }

        workRVAdapter = new WorkRVAdapter(draftItemBeanList);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(workRVAdapter);

    }
}
