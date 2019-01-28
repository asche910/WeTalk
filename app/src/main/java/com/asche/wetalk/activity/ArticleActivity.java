package com.asche.wetalk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;
import com.asche.wetalk.adapter.BodyContentRVAdapter;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.adapter.OnItemMoreClickListener;
import com.asche.wetalk.adapter.TopicChipRVAdapter;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.BodyContentBean;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.RequirementBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.data.TechTags;
import com.asche.wetalk.data.UserUtils;
import com.asche.wetalk.fragment.FragmentDialogComment;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.service.AudioUtils;
import com.asche.wetalk.service.VibrateUtils;
import com.asche.wetalk.util.BodyContentUtil;
import com.asche.wetalk.util.DataUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.asche.wetalk.util.StringUtils;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ezy.ui.layout.LoadingLayout;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.asche.wetalk.MyApplication.getContext;
import static com.asche.wetalk.adapter.CommentRVAdapter.CLICK_BOTTOM;
import static com.asche.wetalk.fragment.FragmentDialogComment.commentNormalList;

/**
 * ******   此类包含Article和Requirement两种类型，尽管类名存在干扰
 */
public class ArticleActivity extends BaseActivity implements View.OnClickListener {

    // <editor-fold desc="属性变量">

    // toolbar
    private ImageView imgBack, imgMore;
    private TextView toolbarTitle;

    private WaveSwipeRefreshLayout swipeRefreshLayout;


    private RecyclerView recyclerTags;

    // 标题、时间
    private TextView textTitle, textTime;

    // 作者信息
    private TextView textAuthorName, textAuthorSignature;
    private ImageView imgAuthorAvatar, imgFollow;
    private boolean isFollow;

    // 文章内容
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BodyContentRVAdapter bodyContentRVAdapter;
    public List<BodyContentBean> bodyContentBeanList = new ArrayList<>();


    // 文章评论
    private RecyclerView recyclerComment;
    private LinearLayoutManager layoutManagerComment;
    private CommentRVAdapter commentRVAdapter;
    public static List<CommentItemBean> commentSimpleList = new ArrayList<>();

    private TextView textMoreComment;

    // 底部功能栏
    private LinearLayout layoutLike, layoutComment, layoutForward;
    private ImageView imgLike;
    private TextView textLikeNum, textCommentNum;


    public ArticleBean articleBean;
    public RequirementBean requirementBean;
    public boolean isArticle; // 默认为Requirement
    public UserBean author;

    private final String TAG = "Article";

    // </editor-fold>

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String action = getIntent().getStringExtra("action");
        if ("OPEN_COMMENT".equals(action)) {
            openComment();
        }


        final LoadingLayout loadingLayout = findViewById(R.id.loading_article);
        loadingLayout.showLoading();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingLayout.showContent();
                    }
                });
            }
        }).start();


        //<editor-fold defaultstate="collapsed" desc="Id 初始化">

        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        toolbarTitle = findViewById(R.id.text_toolbar_title);

        recyclerTags = findViewById(R.id.recycler_article_tags);
        swipeRefreshLayout = findViewById(R.id.header_article);
        recyclerView = findViewById(R.id.recycler_article);
        recyclerComment = findViewById(R.id.recycler_article_comment);
        textTitle = findViewById(R.id.text_article_title);
        textTime = findViewById(R.id.text_article_time);
        textAuthorName = findViewById(R.id.text_article_author_name);
        textAuthorSignature = findViewById(R.id.text_article_author_signature);
        imgAuthorAvatar = findViewById(R.id.img_article_author);
        imgFollow = findViewById(R.id.img_follow);

        layoutLike = findViewById(R.id.layout_item_main_like);
        layoutComment = findViewById(R.id.layout_item_main_comment);
        layoutForward = findViewById(R.id.layout_item_main_forward);
        imgLike = findViewById(R.id.img_item_main_like);
        textLikeNum = findViewById(R.id.text_item_main_likenum);
        textCommentNum = findViewById(R.id.text_item_main_commentnum);
        textMoreComment = findViewById(R.id.text_article_morecomment);

        //</editor-fold>

        articleBean = (ArticleBean) getIntent().getSerializableExtra("article");
        requirementBean = (RequirementBean) getIntent().getSerializableExtra("requirement");

        if (articleBean != null) {
            /**  这里表明接收到Article类型       */
            isArticle = true;

            author = UserUtils.getUser();
            textTitle.setText(articleBean.getTitle());
            textTime.setText(articleBean.getTime());
            // TODO Author暂时使用测试数据
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
//            bodyContentBeanList = BodyContentUtil.parseHtml(DataUtils.getContent(R.raw.requirement_1));
            bodyContentBeanList = BodyContentUtil.parseHtml(articleBean.getContent());

        } else {
            /**  接收到为Requirement类型  */
            isArticle = false;

            textTitle.setText(requirementBean.getTitle());
            textTime.setText(requirementBean.getTime());
            // TODO Author暂时使用测试数据

            bodyContentBeanList = BodyContentUtil.parseHtml(requirementBean.getContent());

        }

        author = UserUtils.getUser();

        textAuthorName.setText(author.getNickName());
        textAuthorSignature.setText(author.getSignature());
        LoaderUtils.loadImage(author.getImgAvatar(), getApplicationContext(), imgAuthorAvatar);

        toolbarTitle.setText("文章");
        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        textAuthorName.setOnClickListener(this);
        textAuthorSignature.setOnClickListener(this);
        imgAuthorAvatar.setOnClickListener(this);


        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(getApplicationContext())
                .setScrollingEnabled(false)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER)
                .withLastRow(true)
                .build();
        recyclerTags.setLayoutManager(chipsLayoutManager);

        TopicChipRVAdapter tagsRVAdapter = new TopicChipRVAdapter(TechTags.getTagsList().subList(0, 1));
        recyclerTags.addItemDecoration(new SpacingItemDecoration(MyScrollView.dip2px(getContext(), 4), MyScrollView.dip2px(getContext(), 6)));
        recyclerTags.setAdapter(tagsRVAdapter);

        bodyContentRVAdapter = new BodyContentRVAdapter(bodyContentBeanList);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bodyContentRVAdapter);
        recyclerView.setItemViewCacheSize(Integer.MAX_VALUE);

        if (commentSimpleList.isEmpty()) {
            commentSimpleList.add(DataUtils.getComment(1));
            commentSimpleList.add(DataUtils.getComment(1));
        }

        if (commentNormalList.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                CommentItemBean bean = DataUtils.getComment(0);
                if (i == 4)
                    bean.setSubList(commentSimpleList);
                commentNormalList.add(bean);
            }
        }

        commentRVAdapter = new CommentRVAdapter(commentSimpleList, CLICK_BOTTOM);
        layoutManagerComment = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManagerComment);
        recyclerComment.setAdapter(commentRVAdapter);


        imgFollow.setOnClickListener(this);
        textMoreComment.setOnClickListener(this);
        layoutLike.setOnClickListener(this);
        layoutComment.setOnClickListener(this);
        layoutForward.setOnClickListener(this);

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

        commentRVAdapter.setOnItemMoreClickListener(new OnItemMoreClickListener() {
            @Override
            public void onItemMoreClick(int position, int args) {
                if (args == CLICK_BOTTOM) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("replyUserPosition", position);

                    BottomSheetDialogFragment fragment = new FragmentDialogComment();
                    fragment.setArguments(bundle);
                    fragment.show(getSupportFragmentManager(), "commentFragment");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_article_author_name:
            case R.id.text_article_author_signature:
            case R.id.img_article_author:
                Intent intentDetail = new Intent(this, UserHomeActivity.class);
                intentDetail.putExtra("user", author);
                startActivity(intentDetail);
                break;
            case R.id.img_follow:
                if (!isFollow) {
                    Toast.makeText(this, "关注成功！", Toast.LENGTH_SHORT).show();
                    imgFollow.setImageResource(R.drawable.ic_follow_light);
                    isFollow = true;
                } else {
                    new MaterialDialog.Builder(this)
                            .content("您确定不再关注此用户吗？")
                            .positiveText("确定")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    imgFollow.setImageResource(R.drawable.ic_follow_color);
                                    isFollow = false;
                                }
                            })
                            .show();
                }
                break;
            case R.id.layout_item_main_forward:
                Intent intent = new Intent(this, TopicInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.text_article_morecomment:
            case R.id.layout_item_main_comment:
                openComment();
                break;
            case R.id.layout_item_main_like:
                imgLike.setImageResource(R.drawable.ic_like_pressed);
                imgLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_like));
                textLikeNum.setText(StringUtils.addOne(textLikeNum.getText().toString()));

                VibrateUtils.vibrateLike();
                AudioUtils.playLike();
                break;
            case R.id.img_toolbar_more:
                Toast.makeText(this, "More Clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }

    private void openComment() {
        BottomSheetDialogFragment fragment = new FragmentDialogComment();
        fragment.show(getSupportFragmentManager(), "commentFragment");
    }
}
