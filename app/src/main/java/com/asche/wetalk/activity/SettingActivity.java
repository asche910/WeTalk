package com.asche.wetalk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.OnItemClickListener;
import com.asche.wetalk.adapter.SettingRVAdapter;
import com.asche.wetalk.bean.SettingItemBean;
import com.asche.wetalk.helper.FlexibleScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private FlexibleScrollView flexibleScrollView;
    private ImageView imgBack;
    private TextView textTitle;

    private RecyclerView recyclerView;
    private SettingRVAdapter settingRVAdapter;
    private List<SettingItemBean> itemBeanList = new ArrayList<>();

    // APP 皮肤主题 --> 白天黑夜
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_DARK = 1;
    public static int THEME_CURRENT = 0;

    public static boolean isThemeChanged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        flexibleScrollView = findViewById(R.id.scroll_setting);
        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        recyclerView = findViewById(R.id.recycler_setting);


        // TODO 神奇(～￣▽￣)～
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        flexibleScrollView.setEnablePullDown(false);
//            <!--  账号、通用、关于  -->
//<!--  修改密码、注销登录 - 夜间模式、免打扰、省流模式、清除缓存 - 反馈、关于、检查更新  -->

        if (itemBeanList.isEmpty()) {
            itemBeanList.add(new SettingItemBean("账号"));
            itemBeanList.add(new SettingItemBean("修改密码", "定期修改密码来保证账号的安全", false));
            itemBeanList.add(new SettingItemBean("注销登录", "退出登录或更换账号登录", false));
            itemBeanList.add(new SettingItemBean("通用"));
            itemBeanList.add(new SettingItemBean("夜间模式", "光线较弱时可开启此模式", true));
            itemBeanList.add(new SettingItemBean("免打扰", "应用不会通过状态栏发送通知", true));
            itemBeanList.add(new SettingItemBean("省流模式", "仅在Wifi环境下自动加载图片或视频", true));
            itemBeanList.add(new SettingItemBean("清除缓存", "清除缓存使手机运行更流畅", false));
            itemBeanList.add(new SettingItemBean("关于"));
            itemBeanList.add(new SettingItemBean("反馈", "反馈你遇到的问题或建议", false));
            itemBeanList.add(new SettingItemBean("检查更新", "Version 1.0", false));
            itemBeanList.add(new SettingItemBean("关于", "软件版权相关声明", false));
        }

        settingRVAdapter = new SettingRVAdapter(itemBeanList);
        recyclerView.setAdapter(settingRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        textTitle.setText("设置");
        imgBack.setOnClickListener(this);

        settingRVAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                THEME_CURRENT = THEME_CURRENT == THEME_DEFAULT ? THEME_DARK : THEME_DEFAULT;

                finish();
                Intent intent = getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);

                isThemeChanged = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_toolbar_back:
                if (isThemeChanged){
                    finish();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                    isThemeChanged = false;
                }else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isThemeChanged){
            finish();
            startActivity(new Intent(SettingActivity.this, MainActivity.class));
            isThemeChanged = false;
        }else {
            finish();
        }
    }
}
