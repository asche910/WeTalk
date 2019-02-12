package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.fragment.FragmentMessagedMoney;
import com.asche.wetalk.fragment.FragmentMessagingMoney;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MessageMoneyActivity extends BaseActivity implements View.OnClickListener {


    private ImageView imgBack;
    private TextView textTitle;

    SmartTabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_money);

        tabLayout = findViewById(R.id.tab_message_money);
        viewPager = findViewById(R.id.viewpager_message_money);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);


        FragmentPagerItemAdapter fragmentPagerItemAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(getApplicationContext())
                .add("进行中", FragmentMessagingMoney.class)
                .add("已结束", FragmentMessagedMoney.class)
                .create());

        textTitle.setText("我的咨询");
        imgBack.setOnClickListener(this);

        viewPager.setAdapter(fragmentPagerItemAdapter);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
