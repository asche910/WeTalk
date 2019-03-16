package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.WorkRVAdapter;
import com.asche.wetalk.bean.DraftItemBean;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.data.DataUtils;
import com.asche.wetalk.util.StringUtils;
import com.asche.wetalk.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentWorkRequirement extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    WorkRVAdapter workRVAdapter;
    public static List<DraftItemBean> workRequirementList = new ArrayList<>();

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

        if (workRequirementList.isEmpty()) {
            workRequirementList.add(new DraftItemBean(HomeItem.TYPE_REQUIREMENT, DataUtils.getRequirement()));
            workRequirementList.add(new DraftItemBean(HomeItem.TYPE_REQUIREMENT, DataUtils.getRequirement()));
            workRequirementList.add(new DraftItemBean(HomeItem.TYPE_REQUIREMENT, DataUtils.getRequirement()));
            workRequirementList.add(new DraftItemBean(HomeItem.TYPE_REQUIREMENT, DataUtils.getRequirement()));
        }

        workRVAdapter = new WorkRVAdapter(workRequirementList);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(workRVAdapter);

    }
}
