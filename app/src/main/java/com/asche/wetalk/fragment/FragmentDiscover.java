package com.asche.wetalk.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class FragmentDiscover extends Fragment {

    private SmartTabLayout tabLayoutDiscover;
    private ViewPager viewPagerDiscover;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_discover, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayoutDiscover = getView().findViewById(R.id.tab_discover);
        viewPagerDiscover = getView().findViewById(R.id.viewpager_discover);

        FragmentPagerItemAdapter pagerItemAdapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.tab_discover_happen, FragmentDiscoverHappen.class)
                .add(R.string.tab_discover_topic, FragmentDiscoverTopic.class)
                .add(R.string.tab_discover_live, FragmentDiscoverLive.class)
                .create());

        viewPagerDiscover.setAdapter(pagerItemAdapter);
        tabLayoutDiscover.setViewPager(viewPagerDiscover);
    }
}
