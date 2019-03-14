package com.asche.wetalk.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.asche.wetalk.R;
import com.asche.wetalk.activity.MainActivity;
import com.asche.wetalk.activity.ScanResultActivity;
import com.asche.wetalk.activity.SettingActivity;
import com.asche.wetalk.other.MyScrollViewHome;
import com.asche.wetalk.other.OnScrollListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import static android.app.Activity.RESULT_OK;
import static com.asche.wetalk.activity.SettingActivity.THEME_CURRENT;
import static com.asche.wetalk.activity.SettingActivity.THEME_DARK;
import static com.asche.wetalk.activity.SettingActivity.THEME_DEFAULT;
import static com.asche.wetalk.fragment.FragmentHomeSuggest.videoPlayer;

public class FragmentHome extends Fragment {

    private final String TAG = "FragmentHome";
    private FloatingSearchView floatingSearchView;

    public static SmartTabLayout tabLayout;
    private ViewPager viewPager;

    private MyScrollViewHome scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container, false);
        Log.e(TAG, "onCreateView: ------->");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        floatingSearchView = getView().findViewById(R.id.floating_search_view);
        tabLayout = getView().findViewById(R.id.tab_home);
        viewPager = getView().findViewById(R.id.viewpager_home);
//        scrollView = getView().findViewById(R.id.scroll_home);


        FragmentPagerItemAdapter fragmentPagerItemAdapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.tab_home_suggest, FragmentHomeSuggest.class)
                .add(R.string.tab_home_requirement, FragmentHomeRequirement.class)
                .add(R.string.tab_home_article, FragmentHomeArticle.class)
                .add(R.string.tab_home_ranklist, FragmentHomeRanklist.class)
                .create());

        viewPager.setAdapter(fragmentPagerItemAdapter);
        tabLayout.setViewPager(viewPager);

        floatingSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
            }

            @Override
            public void onFocusCleared() {

            }
        });

        floatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                Log.e(TAG, "onActionMenuItemSelected: " + item.getTitle());
                // TODO group 分界效果
                switch (item.getItemId()) {
                    case R.id.menu_main_about:
                        break;
                    case R.id.menu_main_setting:
                        startActivity(new Intent(getContext(), SettingActivity.class));
                        break;
                    case R.id.menu_main_night:

                        if (THEME_CURRENT == THEME_DARK) {
                            item.setChecked(true);
                        } else {
                            item.setChecked(false);
                        }

                        THEME_CURRENT = THEME_CURRENT == THEME_DEFAULT ? THEME_DARK : THEME_DEFAULT;

                        getActivity().finish();
                        Intent intent = getActivity().getIntent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().overridePendingTransition(0, 0);

                        break;
                    case R.id.menu_main_scan:
                        Intent intentScan = new Intent(getContext(), CaptureActivity.class);
                        startActivityForResult(intentScan, 101);
                        break;
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            if (videoPlayer != null && videoPlayer.isInPlayingState()) {
                videoPlayer.onVideoPause();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
//                Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), ScanResultActivity.class);
                intent.putExtra("content", content);
                startActivity(intent);
            }
        }
    }
}
