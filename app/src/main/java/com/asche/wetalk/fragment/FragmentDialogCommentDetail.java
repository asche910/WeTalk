package com.asche.wetalk.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.MyApplication;
import com.asche.wetalk.R;
import com.asche.wetalk.activity.AtActivity;
import com.asche.wetalk.activity.BaseActivity;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.adapter.OnItemMoreClickListener;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.helper.KeyboardHeightObserver;
import com.asche.wetalk.helper.KeyboardHeightProvider;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.util.EmoticonUtils;
import com.asche.wetalk.util.StringUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.activity.ChatActivity.inputMethodManager;
import static com.asche.wetalk.activity.ChatActivity.keyboardHeight;
import static com.asche.wetalk.adapter.CommentRVAdapter.CLICK_COMMENT;
import static com.asche.wetalk.adapter.CommentRVAdapter.TYPE_SIMPLE;
import static com.asche.wetalk.fragment.FragmentDialogComment.commentNormalList;
import static com.asche.wetalk.util.TimeUtils.getCurrentTime;


public class FragmentDialogCommentDetail extends BaseDialogFragment implements View.OnClickListener, KeyboardHeightObserver {

    private ImageView imgBack;

    private ImageView imgAvatar, imgLike, imgComment;
    private TextView textName, textContent, textTime, textLikeNum, textMore;
    private RecyclerView recyclerViewSub;
    private CommentRVAdapter subAdapter;

    private ImageView imgEmoticon, imgAt, imgSend;
    private EditText editText;
    private LinearLayout layoutBottom;
    private FrameLayout emoticonLayout;

    private CommentItemBean commentBean;
    private List<CommentItemBean> subList;
    // 当前评论的位置
    private int position;

    private boolean isEmoticonPressed;
    private boolean isInputMethodShow;
    private KeyboardHeightProvider keyboardHeightProvider;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        position = bundle.getInt("position");
        commentBean = commentNormalList.get(position);

        if (savedInstanceState == null)
            savedInstanceState = new Bundle();
        savedInstanceState.putInt("layoutId", R.layout.fragment_comment_detail);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        // 设置背景透明，从而显示顶部圆角
        getDialog().getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgBack = getView().findViewById(R.id.img_fragment_comment_close);
        imgAvatar = getView().findViewById(R.id.img_item_comment_avatar);
        imgLike = getView().findViewById(R.id.img_item_comment_like);
        imgComment = getView().findViewById(R.id.img_item_comment_comment);
        textContent = getView().findViewById(R.id.text_item_comment_content);
        textName = getView().findViewById(R.id.text_item_comment_name);
        textTime = getView().findViewById(R.id.text_item_comment_time);
        textLikeNum = getView().findViewById(R.id.text_item_comment_likenum);
        textMore = getView().findViewById(R.id.text_item_comment_more);
        recyclerViewSub = getView().findViewById(R.id.recycler_item_comment);

        imgEmoticon = getView().findViewById(R.id.img_comment_reply_emoticon);
        imgAt = getView().findViewById(R.id.img_comment_reply_at);
        imgSend = getView().findViewById(R.id.img_comment_reply_send);
        editText = getView().findViewById(R.id.edit_comment_reply);
        layoutBottom = getView().findViewById(R.id.layout_bottom);
        emoticonLayout = getView().findViewById(R.id.frame_emoticon);


        recyclerViewSub.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        try {
            Glide.with(getContext())
                    .load(Integer.parseInt(commentBean.getAvatarUrl()))
                    .into(imgAvatar);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Glide.with(getContext())
                    .load(commentBean.getAvatarUrl())
                    .into(imgAvatar);
        }

        textName.setText(commentBean.getName());
        textContent.setText(EmoticonUtils.parseEmoticon(commentBean.getContent()));
        textTime.setText(commentBean.getTime());
        editText.setHint("@" + commentBean.getName() + ":");

        if (commentBean.getLikeNum() != 0) {
            textLikeNum.setText(commentBean.getLikeNum() + "");
        }

        subList = commentBean.getSubList();
        if (subList == null) {
            subList = new ArrayList<>();
        }
        subAdapter = new CommentRVAdapter(subList, CLICK_COMMENT);
        recyclerViewSub.setAdapter(subAdapter);

        textMore.setVisibility(View.VISIBLE);
        textMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        subAdapter.setOnItemMoreClickListener(new OnItemMoreClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemMoreClick(int position, int args) {
                if (args == CLICK_COMMENT) {
                    String name = subList.get(position).getName();
                    editText.setText("@" + name + ":");
                }
            }
        });


        imgBack.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        textName.setOnClickListener(this);
        textMore.setOnClickListener(this);
        imgLike.setOnClickListener(this);
        imgComment.setOnClickListener(this);
        imgEmoticon.setOnClickListener(this);
        imgAt.setOnClickListener(this);
        imgSend.setOnClickListener(this);


        keyboardHeightProvider = new KeyboardHeightProvider(getActivity(), R.layout.fragment_comment);
        keyboardHeightProvider.setKeyboardHeightObserver(this);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                keyboardHeightProvider.start();
            }
        });

        /*   解决输入法弹出后导致标题栏也上移的问题       */
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        keyboardHeightProvider.close();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_comment_reply_send:
                String inputStr = editText.getText().toString();
                if (StringUtils.isEmpty(inputStr)) {
                    Toast.makeText(getContext(), "内容不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserBean user = BaseActivity.getCurUser();
                CommentItemBean newBean = new CommentItemBean(user.getImgAvatar(), user.getUserName(), inputStr);
                newBean.setTime(getCurrentTime());
                // int type = inputStr.matches("@.+:.*") ? TYPE_SIMPLE : TYPE_NORMAL;
                newBean.setType(TYPE_SIMPLE);
                subList.add(newBean);

                subAdapter.notifyItemInserted(subList.size() - 1);
                recyclerViewSub.scrollToPosition(commentNormalList.size() - 1);

                Toast.makeText(getContext(), "评论成功！", Toast.LENGTH_SHORT).show();
                editText.setText("");
                editText.clearFocus();

                View curView = getDialog().getCurrentFocus();
                if (curView == null)
                    break;
                inputMethodManager.hideSoftInputFromWindow(getDialog().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                subAdapter.notifyItemRangeChanged(0, subList.size() - 1);

                break;
            case R.id.img_comment_reply_at:
                startActivityForResult(new Intent(getContext(), AtActivity.class), 10);
                break;
            case R.id.img_comment_reply_emoticon:
                if (!isEmoticonPressed) {
                    isEmoticonPressed = true;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon_pressed);

                    emoticonRising();
                    beginRising();

                } else {
                    isEmoticonPressed = false;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon);

                    emoticonfalling();
                    beginFalling();
                }
                break;
            case R.id.text_item_comment_more:
                textMore.setText("暂无更多！");
                break;
            case R.id.img_item_comment_comment:
                editText.requestFocus();
                editText.setSelection(editText.length());

                inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

                break;
            case R.id.img_item_comment_like:
                imgLike.setImageResource(R.drawable.ic_like_pressed);
                imgLike.startAnimation(android.view.animation.AnimationUtils.loadAnimation(getContext(), R.anim.anim_like));
                textLikeNum.setText(StringUtils.addOne(textLikeNum.getText().toString()));
                break;
            case R.id.text_item_comment_name:
            case R.id.img_item_comment_avatar:
                Toast.makeText(getContext(), "User Clicked!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_fragment_comment_close:
                dismiss();
                break;
        }
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
        int height = MyScrollView.dip2px(MyApplication.getContext(), keyboardHeight);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = height;
        layoutBottom.setLayoutParams(params);
    }

    private void beginFalling() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = 0;
        layoutBottom.setLayoutParams(params);
    }

    private void emoticonRising() {
        emoticonLayout.setVisibility(View.VISIBLE);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        FragmentEmoticon fragmentEmoticon = new FragmentEmoticon();
        fragmentEmoticon.setEditText(editText);
        transaction.add(R.id.frame_emoticon, fragmentEmoticon, "emoticon");
        transaction.commit();

        View view = getDialog().getCurrentFocus();
        if (view != null)
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void emoticonfalling() {
        try {
            emoticonLayout.setVisibility(View.GONE);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(getChildFragmentManager().findFragmentByTag("emoticon"));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
