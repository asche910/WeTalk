package com.asche.wetalk.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.GridImgRVAdapter;
import com.asche.wetalk.bean.HappenItemBean;
import com.asche.wetalk.service.AudioUtils;
import com.asche.wetalk.service.VibrateUtils;
import com.asche.wetalk.util.LoaderUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class HappenActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack, imgMore;
    private TextView textTitle;

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private ImageView imgAvatar;
    private TextView userName;
    private TextView content;
    private RecyclerView recycView;
    private TextView time;
    private Button btnLike;

    private HappenItemBean happenBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happen);

        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        textTitle = findViewById(R.id.text_toolbar_title);
        swipeRefreshLayout = findViewById(R.id.header_happen);
        imgAvatar = findViewById(R.id.img_item_happen_avatar);
        userName = findViewById(R.id.text_item_happen_name);
        content = findViewById(R.id.text_item_happen_content);
        recycView = findViewById(R.id.recycler_happen);
        time = findViewById(R.id.text_item_happen_time);
        btnLike = findViewById(R.id.btn_item_happen_like);

        imgMore.setImageResource(R.drawable.ic_more_light);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recycView.setLayoutManager(layoutManager);


        happenBean = (HappenItemBean) getIntent().getSerializableExtra("happenBean");
        if (happenBean != null){
            LoaderUtils.loadImage(happenBean.getUserAvatar(), this, imgAvatar);
            userName.setText(happenBean.getUserName());
            content.setText(happenBean.getContent());
            time.setText(happenBean.getTime());

            if (happenBean.getUrlList() != null){
                recycView.setAdapter(new GridImgRVAdapter(happenBean.getUrlList()));
            }
        }

        textTitle.setText("详情");

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        btnLike.setOnClickListener(this);


        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_item_happen_like:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnLike.setBackground(getDrawable(R.drawable.ic_item_like_press));
                    btnLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.anim_like));

                    VibrateUtils.vibrateLike();
                    AudioUtils.playLike();
                }
                break;
            case R.id.img_toolbar_more:
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
