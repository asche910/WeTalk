package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.BodyContentRVAdapter;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.BodyContentBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.util.BodyContentUtil;
import com.asche.wetalk.util.DataUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textTitle, textAuthorName, textAuthorFollowerNum, textAuthorFollowNum;
    private ImageView imgAuthorAvatar;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BodyContentRVAdapter bodyContentRVAdapter;
    public List<BodyContentBean> bodyContentBeanList = new ArrayList<>();

    public ArticleBean articleBean;
    public UserBean author;

    private final String TAG = "Article";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        recyclerView = findViewById(R.id.recycler_article);
        textTitle = findViewById(R.id.text_article_title);
        textAuthorName = findViewById(R.id.text_article_author_name);
        textAuthorFollowerNum = findViewById(R.id.text_article_author_followernum);
        textAuthorFollowNum = findViewById(R.id.text_article_author_follownum);
        imgAuthorAvatar = findViewById(R.id.img_article_author);

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

    }

    @Override
    public void onClick(View v) {

    }
}
