package com.asche.wetalk.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.AchievementActivity;
import com.asche.wetalk.activity.AgendaActivity;
import com.asche.wetalk.activity.ArticleActivity;
import com.asche.wetalk.activity.ArticlePublishActivity;
import com.asche.wetalk.activity.BookActivity;
import com.asche.wetalk.activity.ClientActivity;
import com.asche.wetalk.activity.ClockInActivity;
import com.asche.wetalk.activity.CollectActivity;
import com.asche.wetalk.activity.CommentActivity;
import com.asche.wetalk.activity.DraftActivity;
import com.asche.wetalk.activity.SettingActivity;
import com.asche.wetalk.activity.UserHomeActivity;
import com.asche.wetalk.activity.WalletActivity;
import com.asche.wetalk.activity.WorkActivity;
import com.asche.wetalk.adapter.UserToolRVAdapter;
import com.asche.wetalk.bean.ImageTextBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

@SuppressWarnings("FieldCanBeLocal")
public class FragmentUser extends Fragment implements View.OnClickListener {

    private LinearLayout userInfoLayout;
    private ImageView imgAvatar;

    private ImageView imgSetting;

    private RecyclerView recyclerView;
    private UserToolRVAdapter userToolRVAdapter;
    private List<ImageTextBean> imageTextBeans = new ArrayList<>();

    private RecyclerView recyclerViewPanel;
    private UserToolRVAdapter userToolRVAdapterPanel;
    private List<ImageTextBean> imageTextBeansPanel = new ArrayList<>();

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

        userInfoLayout = getView().findViewById(R.id.layout_user_info);
        imgAvatar = getView().findViewById(R.id.img_user_avatar);
        imgSetting = getView().findViewById(R.id.img_toolbar_setting);
        recyclerView = getView().findViewById(R.id.recycle_user_tool);
        recyclerViewPanel = getView().findViewById(R.id.recycle_user_panel);


        imageTextBeans.add(new ImageTextBean(R.drawable.ic_wallet + "", "钱包"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_book + "", "书架"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_achievement + "", "成就"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_collect + "", "收藏"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_signin + "", "签到"));

        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_draft_color + "", "草稿"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_query_color + "", "咨询"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_answer_color + "", "作品"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_comment_color + "", "评价管理"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_client_color + "", "客户"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_agendum_color + "", "待办事项"));


        userToolRVAdapter = new UserToolRVAdapter(imageTextBeans);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(userToolRVAdapter);

        userToolRVAdapterPanel = new UserToolRVAdapter(imageTextBeansPanel);
        recyclerViewPanel.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewPanel.setAdapter(userToolRVAdapterPanel);

        imgSetting.setOnClickListener(this);
        userInfoLayout.setOnClickListener(this);
        userToolRVAdapter.setOnItemClickListener(new UserToolRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 4:
                        nextActivity(ClockInActivity.class);
                        Toast.makeText(getActivity(), "签到", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        nextActivity(ArticlePublishActivity.class);
//                        nextActivity(CollectActivity.class);
                        Toast.makeText(getActivity(), "收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        nextActivity(AchievementActivity.class);
                        Toast.makeText(getActivity(), "成就", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        nextActivity(BookActivity.class);
                        Toast.makeText(getActivity(), "书架", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        nextActivity(WalletActivity.class);
                        Toast.makeText(getActivity(), "钱包", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        userToolRVAdapterPanel.setOnItemClickListener(new UserToolRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 5:
                        nextActivity(AgendaActivity.class);
                        Toast.makeText(getActivity(), "待办事项", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        nextActivity(ClientActivity.class);
                        Toast.makeText(getActivity(), "客户", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        nextActivity(CommentActivity.class);
                        Toast.makeText(getActivity(), "评价管理", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        nextActivity(WorkActivity.class);
                        Toast.makeText(getActivity(), "作品", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "咨询", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        nextActivity(DraftActivity.class);
                        Toast.makeText(getActivity(), "草稿", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    private void nextActivity(Class<?> cls){
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
