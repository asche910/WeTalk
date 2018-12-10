package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.UserToolRVAdapter;
import com.asche.wetalk.bean.ImageTextBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class FragmentChatPanel extends Fragment {

    private RecyclerView recyclerView;
    private UserToolRVAdapter adapter;
    private List<ImageTextBean> imageTextList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_panel, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getView().findViewById(R.id.recycler_chat_panel);

        imageTextList.add(new ImageTextBean(R.drawable.ic_draft_color + "", "草稿"));
        imageTextList.add(new ImageTextBean(R.drawable.ic_query_color + "", "咨询"));
        imageTextList.add(new ImageTextBean(R.drawable.ic_answer_color + "", "回答"));
        imageTextList.add(new ImageTextBean(R.drawable.ic_comment_color + "", "评价管理"));
        imageTextList.add(new ImageTextBean(R.drawable.ic_client_color + "", "客户"));
        imageTextList.add(new ImageTextBean(R.drawable.ic_agendum_color + "", "待办事项"));

        adapter = new UserToolRVAdapter(imageTextList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new UserToolRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(), "You click " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
