package com.asche.wetalk.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.adapter.GridImgRVAdapter;
import com.asche.wetalk.adapter.OnItemClickListener;
import com.asche.wetalk.adapter.OnItemMoreClickListener;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.HappenItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.fragment.FragmentEmoticon;
import com.asche.wetalk.helper.FlexibleScrollView;
import com.asche.wetalk.helper.KeyboardHeightObserver;
import com.asche.wetalk.helper.KeyboardHeightProvider;
import com.asche.wetalk.service.AudioUtils;
import com.asche.wetalk.service.VibrateUtils;
import com.asche.wetalk.util.LoaderUtils;
import com.asche.wetalk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.asche.wetalk.activity.ChatActivity.inputMethodManager;
import static com.asche.wetalk.adapter.CommentRVAdapter.CLICK_IMG_COMMENT;
import static com.asche.wetalk.adapter.CommentRVAdapter.TYPE_NORMAL;
import static com.asche.wetalk.adapter.CommentRVAdapter.TYPE_SIMPLE;
import static com.asche.wetalk.fragment.FragmentDialogComment.commentNormalList;
import static com.asche.wetalk.util.TimeUtils.getCurrentTime;

public class HappenActivity extends BaseActivity implements View.OnClickListener, KeyboardHeightObserver {

    private ImageView imgBack, imgMore;
    private TextView textTitle;

    private WaveSwipeRefreshLayout swipeRefreshLayout;
    private FlexibleScrollView flexibleScrollView;

    // 主评论相关
    private ImageView imgAvatar;
    private TextView userName;
    private TextView content;
    private RecyclerView recycView;
    private TextView time;
    private Button btnLike, btnComment, btnForward;

    // 主评论下的回复
    private RecyclerView recyclerComment;
    private LinearLayoutManager layoutManagerComment;
    private CommentRVAdapter commentRVAdapter;

    // 底部评论框
    private ImageView imgEmoticon, imgAt, imgSend;
    private EditText editText;
    // private LinearLayout layoutBottom;
    private FrameLayout emoticonLayout;

    // 回复的某个评论的位置，-1代表发布新评论
    private int replyPosition = -1;

    private boolean isEmoticonPressed;
    private KeyboardHeightProvider keyboardHeightProvider;

    private HappenItemBean happenBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happen);

        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        textTitle = findViewById(R.id.text_toolbar_title);
        swipeRefreshLayout = findViewById(R.id.header_happen);
        flexibleScrollView = findViewById(R.id.scroll_happen);
        imgAvatar = findViewById(R.id.img_item_happen_avatar);
        userName = findViewById(R.id.text_item_happen_name);
        content = findViewById(R.id.text_item_happen_content);
        recycView = findViewById(R.id.recycler_happen);
        time = findViewById(R.id.text_item_happen_time);
        btnLike = findViewById(R.id.btn_item_happen_like);
        btnComment = findViewById(R.id.btn_item_happen_comment);
        btnForward = findViewById(R.id.btn_item_happen_forward);
        recyclerComment = findViewById(R.id.recycler_happen_comment_normal);
        imgEmoticon = findViewById(R.id.img_comment_reply_emoticon);
        imgAt = findViewById(R.id.img_comment_reply_at);
        imgSend = findViewById(R.id.img_comment_reply_send);
        editText = findViewById(R.id.edit_comment_reply);
        // layoutBottom = findViewById(R.id.layout_bottom);
        emoticonLayout = findViewById(R.id.frame_emoticon_happen);


        flexibleScrollView.setEnablePullDown(false);
        imgMore.setImageResource(R.drawable.ic_more_light);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recycView.setLayoutManager(layoutManager);


        happenBean = (HappenItemBean) getIntent().getSerializableExtra("happenBean");
        if (happenBean != null) {
            LoaderUtils.loadImage(happenBean.getUserAvatar(), this, imgAvatar);
            userName.setText(happenBean.getUserName());
            content.setText(happenBean.getContent());
            time.setText(happenBean.getTime());

            if (happenBean.getUrlList() != null) {
                recycView.setAdapter(new GridImgRVAdapter(happenBean.getUrlList()));
            }
        }

        // TODO CLICK_NULL
        commentRVAdapter = new CommentRVAdapter(commentNormalList);
        layoutManagerComment = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManagerComment);
        recyclerComment.setAdapter(commentRVAdapter);
        recyclerComment.setHasFixedSize(true);
        recyclerComment.setNestedScrollingEnabled(false);

        textTitle.setText("详情");

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        btnLike.setOnClickListener(this);
        btnComment.setOnClickListener(this);
        btnForward.setOnClickListener(this);
        imgEmoticon.setOnClickListener(this);
        imgAt.setOnClickListener(this);
        imgSend.setOnClickListener(this);


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

        commentRVAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                editText.requestFocus();
                editText.setText("@" + commentNormalList.get(position).getName() + ":");
                editText.setSelection(editText.length());

                replyPosition = position;
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        commentRVAdapter.setOnItemMoreClickListener(new OnItemMoreClickListener() {
            @Override
            public void onItemMoreClick(int position, int args) {
                if (args == CLICK_IMG_COMMENT){
                    // imgComment的点击事件
                    editText.requestFocus();
                    editText.setText("@" + commentNormalList.get(position).getName() + ":");
                    editText.setSelection(editText.length());

                    replyPosition = position;
                    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        keyboardHeightProvider = new KeyboardHeightProvider(this, R.layout.activity_happen);
        keyboardHeightProvider.setKeyboardHeightObserver(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                keyboardHeightProvider.start();
            }
        });

        /*   解决输入法弹出后导致标题栏也上移的问题       */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_comment_reply_send:
                String inputStr = editText.getText().toString();
                if (StringUtils.isEmpty(inputStr)) {
                    Toast.makeText(this, "内容不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!inputStr.matches("@.+:.*")) {
                    replyPosition = -1;
                }

                // 先关闭输入法，以防影响后面recyclerview的滑动
                editText.clearFocus();
                View curView = getWindow().getCurrentFocus();
                if (curView != null)
                    inputMethodManager.hideSoftInputFromWindow(curView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                UserBean user = BaseActivity.getCurUser();
                CommentItemBean newBean = new CommentItemBean(user.getImgAvatar(), user.getUserName(), inputStr);
                newBean.setTime(getCurrentTime());

                if (replyPosition == -1) {
                    newBean.setType(TYPE_NORMAL);
                    commentNormalList.add(newBean);
                    commentRVAdapter.notifyItemInserted(commentNormalList.size() - 1);
                    recyclerComment.scrollToPosition(commentNormalList.size() - 1);
                } else {
                    newBean.setType(TYPE_SIMPLE);
                    CommentItemBean originBean = commentNormalList.get(replyPosition);
                    List<CommentItemBean> subList = originBean.getSubList();
                    if (subList == null) {
                        subList = new ArrayList<>();
                    }
                    subList.add(newBean);
                    originBean.setSubList(subList);
                    commentRVAdapter.notifyItemChanged(replyPosition);
                    recyclerComment.scrollToPosition(replyPosition);
                }
                Toast.makeText(this, "评论成功！", Toast.LENGTH_SHORT).show();
                editText.setText("");
                commentRVAdapter.notifyItemRangeChanged(0, replyPosition);
                break;
            case R.id.img_comment_reply_at:
                startActivityForResult(new Intent(this, AtActivity.class), 10);
                break;
            case R.id.img_comment_reply_emoticon:
                if (!isEmoticonPressed) {
                    isEmoticonPressed = true;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon_pressed);

                    beginRising();
                    emoticonRising();
                } else {
                    isEmoticonPressed = false;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon);

                    beginFalling();
                    emoticonfalling();
                }
                break;
            case R.id.btn_item_happen_like:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnLike.setBackground(getDrawable(R.drawable.ic_item_like_press));
                    btnLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.anim_like));

                    VibrateUtils.vibrateLike();
                    AudioUtils.playLike();
                }
                break;
            case R.id.btn_item_happen_comment:
                editText.requestFocus();
                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.btn_item_happen_forward:
                Intent intentText = new Intent(Intent.ACTION_SEND);
                intentText.setType("text/plain");
                intentText.putExtra(Intent.EXTRA_TEXT, happenBean.getContent());
                startActivity(Intent.createChooser(intentText, "分享"));
                break;
            case R.id.img_toolbar_more:
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        if (height > 0) {
            if (isEmoticonPressed) {
                emoticonfalling();
                isEmoticonPressed = false;
                imgEmoticon.setImageResource(R.drawable.ic_emoticon);
            } else {
                beginRising();
            }
        } else {
            if (isEmoticonPressed) {
            } else {
                beginFalling();
            }
        }
    }

    private void beginRising() {
        emoticonLayout.setVisibility(View.VISIBLE);
    }

    private void beginFalling() {
        emoticonLayout.setVisibility(View.GONE);
    }

    private void emoticonRising() {

        FragmentEmoticon fragmentEmoticon = new FragmentEmoticon();
        fragmentEmoticon.setEditText(editText);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_emoticon_happen, fragmentEmoticon, "emoticon");
        transaction.commit();

        View view = getWindow().getCurrentFocus();
        if (view != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void emoticonfalling() {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(getSupportFragmentManager().findFragmentByTag("emoticon"));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
