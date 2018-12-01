package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.BodyContentRVAdapter;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.BodyContentBean;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.fragment.FullSheetDialogFragment;
import com.asche.wetalk.util.BodyContentUtil;
import com.asche.wetalk.util.DataUtils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.asche.wetalk.fragment.FullSheetDialogFragment.commentNormalList;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    private TextView textTitle, textAuthorName, textAuthorFollowerNum, textAuthorFollowNum;
    private ImageView imgAuthorAvatar;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BodyContentRVAdapter bodyContentRVAdapter;
    public List<BodyContentBean> bodyContentBeanList = new ArrayList<>();


    private RecyclerView recyclerComment;
    private LinearLayoutManager layoutManagerComment;
    private CommentRVAdapter commentRVAdapter;
    public List<CommentItemBean> commentSimpleList = new ArrayList<>();

    public ArticleBean articleBean;
    public UserBean author;

    private LinearLayout layoutLike, layoutComment, layoutForward;


    private final String TAG = "Article";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#22000000"));
        }

        swipeRefreshLayout = findViewById(R.id.header_article);
        recyclerView = findViewById(R.id.recycler_article);
        recyclerComment = findViewById(R.id.recycler_article_comment);
        textTitle = findViewById(R.id.text_article_title);
        textAuthorName = findViewById(R.id.text_article_author_name);
        textAuthorFollowerNum = findViewById(R.id.text_article_author_followernum);
        textAuthorFollowNum = findViewById(R.id.text_article_author_follownum);
        imgAuthorAvatar = findViewById(R.id.img_article_author);

        layoutLike = findViewById(R.id.layout_item_main_like);
        layoutComment = findViewById(R.id.layout_item_main_comment);
        layoutForward = findViewById(R.id.layout_item_main_forward);

        articleBean = (ArticleBean) getIntent().getSerializableExtra("article");

        if (articleBean != null){
            author = DataUtils.getUser();
            textTitle.setText(articleBean.getTitle());
            // TODO Author暂时使用测试数据
            textAuthorFollowerNum.setText("粉丝 " + author.getFollowerNum());
            textAuthorFollowNum.setText("关注 " + author.getFollowNum());
            try {
                Glide.with(getApplicationContext())
                        .load(Integer.parseInt(articleBean.getImgUrl()))
                        .into(imgAuthorAvatar);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Glide.with(getApplicationContext())
                        .load(articleBean.getImgUrl())
                        .into(imgAuthorAvatar);
            }
            bodyContentBeanList = BodyContentUtil.parseHtml(articleBean.getContent());

        }else {
            bodyContentBeanList = BodyContentUtil.parseHtml(DataUtils.getArticleStr());
        }

        bodyContentRVAdapter = new BodyContentRVAdapter(bodyContentBeanList);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bodyContentRVAdapter);
        recyclerView.setItemViewCacheSize(Integer.MAX_VALUE);

        commentSimpleList.add(DataUtils.getComment(1));
        commentSimpleList.add(DataUtils.getComment(1));
//        commentSimpleList.add(DataUtils.getComment());
//        commentSimpleList.add(DataUtils.getComment());

        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));
        commentNormalList.add(DataUtils.getComment(0));

        commentRVAdapter = new CommentRVAdapter(commentSimpleList);
        layoutManagerComment = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManagerComment);
        recyclerComment.setAdapter(commentRVAdapter);

        layoutComment.setOnClickListener(this);

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
        switch (v.getId()){
            case R.id.layout_item_main_forward:
                break;
            case R.id.layout_item_main_comment:
                Log.e(TAG, "onClick: --------->" );
                BottomSheetDialogFragment fragment = new FullSheetDialogFragment();
                fragment.show(getSupportFragmentManager(), "commentFragment");

                break;
            case R.id.layout_item_main_like:
                break;
        }
    }
}
