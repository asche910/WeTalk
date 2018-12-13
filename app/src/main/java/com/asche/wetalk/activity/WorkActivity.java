package com.asche.wetalk.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.fragment.FragmentHomeArticle;
import com.asche.wetalk.fragment.FragmentHomeRanklist;
import com.asche.wetalk.fragment.FragmentHomeRequirement;
import com.asche.wetalk.fragment.FragmentHomeSuggest;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class WorkActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout layoutToolbar;
    private ImageView imgBack;
    private TextView textTitle;

    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        layoutToolbar = findViewById(R.id.layout_toolbar_universe);
        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        tabLayout = findViewById(R.id.tab_work);
        viewPager = findViewById(R.id.viewpager_work);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layoutToolbar.setBackgroundColor(getColor(R.color.darkGreenLight));
        }
        textTitle.setText("我的作品");
        imgBack.setOnClickListener(this);

        FragmentPagerItemAdapter fragmentPagerItemAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(getApplicationContext())
                .add(R.string.tab_home_suggest, FragmentHomeSuggest.class)
                .add(R.string.tab_home_requirement, FragmentHomeRequirement.class)
                .add(R.string.tab_home_article, FragmentHomeArticle.class)
                .add(R.string.tab_home_ranklist, FragmentHomeRanklist.class)
                .create());

        viewPager.setAdapter(fragmentPagerItemAdapter);
        tabLayout.setViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
