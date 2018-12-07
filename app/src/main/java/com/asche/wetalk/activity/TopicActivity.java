package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.BodyContentRVAdapter;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.adapter.OnItemMoreClickListener;
import com.asche.wetalk.bean.BodyContentBean;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.fragment.FragmentDialogComment;
import com.asche.wetalk.util.BodyContentUtil;
import com.asche.wetalk.util.DataUtils;
import com.asche.wetalk.util.StringUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.asche.wetalk.adapter.CommentRVAdapter.CLICK_BOTTOM;
import static com.asche.wetalk.fragment.FragmentDialogComment.commentNormalList;

/**
 * 话题回复的答案页， 展示单个回复
 */
public class TopicActivity extends BaseActivity implements View.OnClickListener{
    private TextView textTitle;

    private WaveSwipeRefreshLayout swipeRefreshLayout;

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

    private LinearLayout layoutLike, layoutComment, layoutForward;
    private ImageView imgLike;
    private TextView textLikeNum, textCommentNum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        swipeRefreshLayout = findViewById(R.id.header_topic);
        textTitle = findViewById(R.id.text_topic_title);
        recyclerView = findViewById(R.id.recycler_topic);
        recyclerComment = findViewById(R.id.recycler_topic_comment);
        textMoreComment = findViewById(R.id.text_topic_morecomment);
        layoutLike = findViewById(R.id.layout_item_main_like);
        layoutComment = findViewById(R.id.layout_item_main_comment);
        layoutForward = findViewById(R.id.layout_item_main_forward);
        imgLike = findViewById(R.id.img_item_main_like);
        textLikeNum = findViewById(R.id.text_item_main_likenum);
        textCommentNum = findViewById(R.id.text_item_main_commentnum);


        bodyContentBeanList = BodyContentUtil.parseHtml(getResources().getString(R.string.topic_reply));
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
            case R.id.layout_item_main_forward:
                PopupMenu popupMenu = new PopupMenu(this, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.menu_item_suggest, popupMenu.getMenu());

       /*         Menu menu = popupMenu.getMenu();
                menu.add(1, 0, 0, "不感兴趣");
                menu.add(1, 1, 1, "屏蔽");
                menu.add(1, 2, 2, "举报");
*/
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_suggest_2:
                                Toast.makeText(TopicActivity.this, "举报", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_suggest_0:
                                Toast.makeText(TopicActivity.this, "不感兴趣此话题", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_suggest_share:
                                Toast.makeText(TopicActivity.this, "分享成功！", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });

                break;
            case R.id.text_topic_morecomment:
            case R.id.layout_item_main_comment:
                openComment();
                break;
            case R.id.layout_item_main_like:
                imgLike.setImageResource(R.drawable.ic_like_pressed);
                textLikeNum.setText(StringUtils.addOne(textLikeNum.getText().toString()));
                break;
        }
    }

    private void openComment() {
        BottomSheetDialogFragment fragment = new FragmentDialogComment();
        fragment.show(getSupportFragmentManager(), "commentFragment");
    }
}
