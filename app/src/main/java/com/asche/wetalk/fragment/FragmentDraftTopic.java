package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.DraftRVAdapter;
import com.asche.wetalk.bean.DraftItemBean;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.util.StringUtils;
import com.asche.wetalk.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentDraftTopic extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    DraftRVAdapter draftRVAdapter;
    public static List<DraftItemBean> draftTopicList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draft, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.recycler_draft);

        if (draftTopicList.isEmpty()) {
            draftTopicList.add(new DraftItemBean(HomeItem.TYPE_TOPIC, "Title Title Title Title ", StringUtils.expandLength(30, "Content"), TimeUtils.getCurrentTime()));
            draftTopicList.add(new DraftItemBean(HomeItem.TYPE_TOPIC, "Title Title Title Title ", StringUtils.expandLength(30, "Content"), TimeUtils.getCurrentTime()));
            draftTopicList.add(new DraftItemBean(HomeItem.TYPE_TOPIC, "Title Title Title Title ", StringUtils.expandLength(30, "Content"), TimeUtils.getCurrentTime()));
            draftTopicList.add(new DraftItemBean(HomeItem.TYPE_TOPIC, "Title Title Title Title ", StringUtils.expandLength(30, "Content"), TimeUtils.getCurrentTime()));
        }

        draftRVAdapter = new DraftRVAdapter(draftTopicList);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(draftRVAdapter);

    }
}
