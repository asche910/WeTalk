package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.fragment.FragmentDraftArticle;
import com.asche.wetalk.fragment.FragmentDraftRequirement;
import com.asche.wetalk.fragment.FragmentDraftTopic;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class CollectActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout layoutToolbar;
    private ImageView imgBack;
    private TextView textTitle;

    private SmartTabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        layoutToolbar = findViewById(R.id.layout_toolbar_universe);
        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        tabLayout = findViewById(R.id.tab_collect);
        viewPager = findViewById(R.id.viewpager_collect);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (SettingActivity.THEME_CURRENT == SettingActivity.THEME_DARK){
                layoutToolbar.setBackgroundColor(Color.parseColor("#1e1e2a"));
            }else {
                layoutToolbar.setBackgroundColor(getColor(R.color.darkGreenLight));
            }
        }

        FragmentPagerItemAdapter fragmentPagerItemAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(getApplicationContext())
                .add("需求", FragmentDraftRequirement.class)
                .add("文章", FragmentDraftArticle.class)
                .add("话题", FragmentDraftTopic.class)
                .create());

        viewPager.setAdapter(fragmentPagerItemAdapter);
        tabLayout.setViewPager(viewPager);
        

        textTitle.setText("收藏");
        imgBack.setOnClickListener(this);

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
