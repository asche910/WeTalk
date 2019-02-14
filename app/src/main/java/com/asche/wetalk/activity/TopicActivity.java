package com.asche.wetalk.activity;

import android.content.Intent;
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
import com.asche.wetalk.bean.BodyContentBean;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.TopicBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.data.UserUtils;
import com.asche.wetalk.fragment.FragmentDialogComment;
import com.asche.wetalk.service.AudioUtils;
import com.asche.wetalk.service.VibrateUtils;
import com.asche.wetalk.util.BodyContentUtil;
import com.asche.wetalk.data.DataUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.asche.wetalk.util.StringUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.asche.wetalk.adapter.CommentRVAdapter.CLICK_BOTTOM;
import static com.asche.wetalk.fragment.FragmentDialogComment.commentNormalList;

/**
 * 话题回复的答案页， 展示单个回复
 */
public class TopicActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack, imgMore;
    private TextView toolbarTitle;

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    // 话题 --> 问题区
    private TextView textTitle, textReply, textAllReply;

    // 作者信息区
    private TextView textAuthorName, textAuthorSignature;
    private ImageView imgAuthorAvatar, imgFollow;
    private boolean isFollow;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BodyContentRVAdapter bodyContentRVAdapter;
    private List<BodyContentBean> bodyContentBeanList = new ArrayList<>();


    // 底部评论区
    private RecyclerView recyclerComment;
    private LinearLayoutManager layoutManagerComment;
    private CommentRVAdapter commentRVAdapter;
    public static List<CommentItemBean> commentSimpleList = new ArrayList<>();

    private TextView textMoreComment;


    // 底部功能栏
    private LinearLayout layoutLike, layoutComment, layoutForward;
    private ImageView imgLike;
    private TextView textLikeNum, textCommentNum;


    // 数据对象
    private TopicBean topicBean;
    private TopicReplyBean topicReplyBean;
    private UserBean author;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Intent intentData = getIntent();
        String action = intentData.getStringExtra("action");
        if ("OPEN_COMMENT".equals(action)){
            openComment();
        }


        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        toolbarTitle = findViewById(R.id.text_toolbar_title);
        swipeRefreshLayout = findViewById(R.id.header_topic);
        textTitle = findViewById(R.id.text_topic_title);
        textReply = findViewById(R.id.text_topic_reply);
        textAllReply = findViewById(R.id.text_topic_allreply);
        imgAuthorAvatar = findViewById(R.id.img_topic_author_avatar);
        imgFollow = findViewById(R.id.img_follow);
        textAuthorName = findViewById(R.id.text_topic_author_name);
        textAuthorSignature = findViewById(R.id.text_topic_author_signature);
        recyclerView = findViewById(R.id.recycler_topic);
        recyclerComment = findViewById(R.id.recycler_topic_comment);
        textMoreComment = findViewById(R.id.text_topic_morecomment);
        layoutLike = findViewById(R.id.layout_item_main_like);
        layoutComment = findViewById(R.id.layout_item_main_comment);
        layoutForward = findViewById(R.id.layout_item_main_forward);
        imgLike = findViewById(R.id.img_item_main_like);
        textLikeNum = findViewById(R.id.text_item_main_likenum);
        textCommentNum = findViewById(R.id.text_item_main_commentnum);


        topicReplyBean = (TopicReplyBean) intentData.getSerializableExtra("topicReply");
        if (topicReplyBean != null){
            topicBean = DataUtils.getTopic(topicReplyBean.getTopicId());
            textTitle.setText(topicBean.getName());
        }

        // TODO get TopicBean and AuthorBean from ids

        author = UserUtils.getUser();

        LoaderUtils.loadImage(author.getImgAvatar(), getApplicationContext(), imgAuthorAvatar);
        textAuthorName.setText(author.getNickName());
        textAuthorSignature.setText(author.getSignature());


        toolbarTitle.setText("话题");

        bodyContentBeanList = BodyContentUtil.parseHtml(topicReplyBean.getContent());
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        bodyContentRVAdapter = new BodyContentRVAdapter(bodyContentBeanList);
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

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        textTitle.setOnClickListener(this);
        textReply.setOnClickListener(this);
        textAllReply.setOnClickListener(this);
        imgAuthorAvatar.setOnClickListener(this);
        imgFollow.setOnClickListener(this);
        textAuthorName.setOnClickListener(this);
        textAuthorSignature.setOnClickListener(this);
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
            case R.id.img_topic_author_avatar:
            case R.id.text_topic_author_name:
            case R.id.text_topic_author_signature:
                Intent intentDetail = new Intent(this, UserHomeActivity.class);
                intentDetail.putExtra("user", author);
                startActivity(intentDetail);
                break;
            case R.id.img_follow:
                if (!isFollow) {
                    Toast.makeText(this, "关注成功！", Toast.LENGTH_SHORT).show();
                    imgFollow.setImageResource(R.drawable.ic_follow_light);
                    isFollow = true;
                }else {
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
            case R.id.text_topic_reply:
                // TODO reply the topic!
                Toast.makeText(this, "Reply the topic!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_topic_title:
            case R.id.text_topic_allreply:
                Intent intent = new Intent(this, TopicInfoActivity.class);
                intent.putExtra("topicInfo", topicBean);
                startActivity(intent);
                break;
            case R.id.layout_item_main_forward:
                Toast.makeText(this, "you forward this topic reply!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_topic_morecomment:
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
