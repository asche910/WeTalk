package com.asche.wetalk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.fragment.FragmentWorkArticle;
import com.asche.wetalk.fragment.FragmentWorkRequirement;
import com.asche.wetalk.fragment.FragmentWorkTopic;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import static com.asche.wetalk.adapter.DraftRVAdapter.TYPE_ARTICLE;
import static com.asche.wetalk.adapter.DraftRVAdapter.TYPE_REQUIREMENT;
import static com.asche.wetalk.adapter.DraftRVAdapter.TYPE_TOPIC;

public class WorkActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout layoutToolbar;
    private ImageView imgBack, imgMore;
    private TextView textTitle;

    private SmartTabLayout tabLayout;
    private ViewPager viewPager;

    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton fabRequirement, fabArticle, fabTopic;

    //  为三个fragment提供数据
    public static UserBean userBean;
    public static boolean isOtherUser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        layoutToolbar = findViewById(R.id.layout_toolbar_universe);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        textTitle = findViewById(R.id.text_toolbar_title);
        tabLayout = findViewById(R.id.tab_work);
        viewPager = findViewById(R.id.viewpager_work);
        floatingActionsMenu = findViewById(R.id.btn_work_publish);
        fabRequirement = findViewById(R.id.fab_requirement);
        fabArticle = findViewById(R.id.fab_article);
        fabTopic = findViewById(R.id.fab_topic);

        userBean = (UserBean)getIntent().getSerializableExtra("userBean");
        if (!getCurUser().equals(userBean)){
            isOtherUser = true;
            textTitle.setText(userBean.getNickName() + "的作品");
            imgMore.setVisibility(View.GONE);
            floatingActionsMenu.setVisibility(View.GONE);

        }else {
            textTitle.setText("我的作品");
            imgMore.setImageResource(R.drawable.ic_draft);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layoutToolbar.setBackgroundColor(getColor(R.color.darkGreenLight));
        }
        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        fabRequirement.setOnClickListener(this);
        fabArticle.setOnClickListener(this);
        fabTopic.setOnClickListener(this);

        FragmentPagerItemAdapter fragmentPagerItemAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(getApplicationContext())
                .add("需求", FragmentWorkRequirement.class)
                .add("文章", FragmentWorkArticle.class)
                .add("话题", FragmentWorkTopic.class)
                .create());

        viewPager.setAdapter(fragmentPagerItemAdapter);
        tabLayout.setViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab_topic:
                nextActivity(TopicReplyActivity.class, TYPE_TOPIC);
                break;
            case R.id.fab_article:
                nextActivity(ArticlePublishActivity.class, TYPE_ARTICLE);
                break;
            case R.id.fab_requirement:
                nextActivity(ArticlePublishActivity.class, TYPE_REQUIREMENT);
                break;
            case R.id.img_toolbar_more:
                startActivity(new Intent(this, DraftActivity.class));
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }

    private void nextActivity(Class<?> cls, int type){
        Intent intent = new Intent(this, cls);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}
