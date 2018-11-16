package com.asche.wetalk.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.SettingActivity;
import com.asche.wetalk.activity.UserHomeActivity;
import com.asche.wetalk.adapter.UserToolRVAdapter;
import com.asche.wetalk.bean.ImageTextBean;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentUser extends Fragment implements View.OnClickListener{

    private RefreshLayout refreshLayout;
    private LinearLayout userInfoLayout;
    private ImageView imgAvatar;

    private ImageView imgSetting;

    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private UserToolRVAdapter userToolRVAdapter;
    private List<ImageTextBean> imageTextBeans = new ArrayList<>();

    private final String TAG = "FragmentUser";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_user, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(600);
            getActivity().getWindow().setExitTransition(explode);
        }

        refreshLayout = getView().findViewById(R.id.refreshLayout_user);
        userInfoLayout = getView().findViewById(R.id.layout_user_info);
        imgAvatar = getView().findViewById(R.id.img_user_avatar);
        imgSetting = getView().findViewById(R.id.img_toolbar_setting);
        recyclerView = getView().findViewById(R.id.recycle_user_tool);

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onLoadMore: " );
                refreshLayout.finishLoadMore(2000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh: " );
                refreshLayout.finishRefresh(2000);
            }
        });

        imageTextBeans.add(new ImageTextBean(R.drawable.ic_nav_notification_pressed + "", "通知"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_nav_notification_pressed + "", "通知"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_nav_notification_pressed + "", "通知"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_nav_notification_pressed + "", "通知"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_nav_notification_pressed + "", "通知"));

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        userToolRVAdapter = new UserToolRVAdapter(imageTextBeans);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(userToolRVAdapter);

        imgSetting.setOnClickListener(this);
        userInfoLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layout_user_info:
                Intent intent = new Intent(getContext(), UserHomeActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgAvatar, "user_avatar");
                    startActivity(intent, compat.toBundle());
                }
                break;
            case R.id.img_toolbar_setting:
                Intent intentSetting = new Intent(getActivity(), SettingActivity.class);
                startActivity(intentSetting);
                break;
        }
    }
}
