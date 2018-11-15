package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.HomeSuggestRVAdapter;
import com.asche.wetalk.bean.ItemBean;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentHomeSuggest extends Fragment {

    private PhoenixHeader header;
    private BallPulseFooter footer;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HomeSuggestRVAdapter adapter;
    private List<ItemBean> itemBeanList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home_suggest, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        header = getView().findViewById(R.id.header_home_suggest);
        footer = getView().findViewById(R.id.footer_home_suggest);
        recyclerView = getView().findViewById(R.id.recycle_home_suggest);


        itemBeanList.add(new ItemBean(0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 666, 65, null));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 126, 63, R.drawable.img_avatar + ""));
        itemBeanList.add(new ItemBean(0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 456, 64, null));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 789, 62, R.drawable.img_avatar + ""));

        itemBeanList.add(new ItemBean(0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 666, 65, null));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 126, 63, R.drawable.img_avatar + ""));
        itemBeanList.add(new ItemBean(0, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 456, 64, null));
        itemBeanList.add(new ItemBean(1, "被当成外国人是怎样的体验？", getResources().getString(R.string.topic_reply), 789, 62, R.drawable.img_avatar + ""));


        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        adapter = new HomeSuggestRVAdapter(itemBeanList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
}
