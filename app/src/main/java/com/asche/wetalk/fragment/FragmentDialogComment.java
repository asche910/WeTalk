package com.asche.wetalk.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TimeUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.asche.wetalk.MyApplication;
import com.asche.wetalk.R;
import com.asche.wetalk.activity.BaseActivity;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.adapter.OnItemClickListener;
import com.asche.wetalk.adapter.OnItemMoreClickListener;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.helper.KeyboardHeightObserver;
import com.asche.wetalk.helper.KeyboardHeightProvider;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.util.StringUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.activity.ChatActivity.inputMethodManager;
import static com.asche.wetalk.activity.ChatActivity.keyboardHeight;
import static com.asche.wetalk.adapter.CommentRVAdapter.CLICK_DETAIL;
import static com.asche.wetalk.adapter.CommentRVAdapter.CLICK_IMG_COMMENT;
import static com.asche.wetalk.adapter.CommentRVAdapter.TYPE_NORMAL;
import static com.asche.wetalk.adapter.CommentRVAdapter.TYPE_SIMPLE;
import static com.asche.wetalk.util.TimeUtils.getCurrentTime;
import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class FragmentDialogComment extends BaseDialogFragment implements KeyboardHeightObserver, View.OnClickListener {

    private ImageView imgClose;

    private RecyclerView recyclerComment;
    private LinearLayoutManager layoutManagerComment;
    private CommentRVAdapter commentRVAdapter;
    public static List<CommentItemBean> commentNormalList = new ArrayList<>();

    private ImageView imgEmoticon, imgAt, imgSend;
    private EditText editText;
    private LinearLayout layoutBottom;
    private FrameLayout emoticonLayout;

    // 回复的某个评论的位置，-1代表发布新评论
    private int replyPosition = -1;

    private boolean isEmoticonPressed;
    private boolean isInputMethodShow;
    private KeyboardHeightProvider keyboardHeightProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            savedInstanceState = new Bundle();
        savedInstanceState.putInt("layoutId", R.layout.fragment_comment);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        recyclerComment = getView().findViewById(R.id.recycler_dialog_comment);
        imgClose = getView().findViewById(R.id.img_fragment_comment_close);
        imgEmoticon = getView().findViewById(R.id.img_comment_reply_emoticon);
        imgAt = getView().findViewById(R.id.img_comment_reply_at);
        imgSend = getView().findViewById(R.id.img_comment_reply_send);
        editText = getView().findViewById(R.id.edit_comment_reply);
        layoutBottom = getView().findViewById(R.id.layout_bottom);
        emoticonLayout = getView().findViewById(R.id.frame_emoticon);


        commentRVAdapter = new CommentRVAdapter(commentNormalList, CLICK_DETAIL);
        layoutManagerComment = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManagerComment);
        recyclerComment.setAdapter(commentRVAdapter);


        Bundle bundle = getArguments();
        if (bundle != null) {
            replyPosition = bundle.getInt("replyUserPosition");
            editText.setText("@" + commentNormalList.get(replyPosition).getName() + ":");
        }

        imgClose.setOnClickListener(this);
        imgEmoticon.setOnClickListener(this);
        imgAt.setOnClickListener(this);
        imgSend.setOnClickListener(this);

        commentRVAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                BottomSheetDialogFragment commentDetail = new FragmentDialogCommentDetail();

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                commentDetail.setArguments(bundle);
                commentDetail.show(getFragmentManager(), "commentDetail");

            }
        });

        commentRVAdapter.setOnItemMoreClickListener(new OnItemMoreClickListener() {
            @Override
            public void onItemMoreClick(int position, int args) {
                if (args == CLICK_DETAIL) {
                    BottomSheetDialogFragment commentDetail = new FragmentDialogCommentDetail();

                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    commentDetail.setArguments(bundle);
                    commentDetail.show(getFragmentManager(), "commentDetail");
                }else if (args == CLICK_IMG_COMMENT){
                    editText.requestFocus();
                    editText.setText("@" + commentNormalList.get(position).getName() + ":");
                    editText.setSelection(editText.length());

                    replyPosition = position;
                    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_comment_reply_send:
                String inputStr = editText.getText().toString();
                if (StringUtils.isEmpty(inputStr)) {
                    Toast.makeText(getContext(), "内容不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!inputStr.matches("@.+:.*")) {
                    replyPosition = -1;
                }

                // 先关闭输入法，以防影响后面recyclerview的滑动
                editText.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(getDialog().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

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
                    // 大于2则跳转至详情页
                    if (subList.size() > 2){
                        BottomSheetDialogFragment commentDetail = new FragmentDialogCommentDetail();

                        Bundle bundle = new Bundle();
                        bundle.putInt("position", replyPosition);
                        commentDetail.setArguments(bundle);
                        commentDetail.show(getFragmentManager(), "commentDetail");
                    }else {
                        recyclerComment.scrollToPosition(replyPosition);
                    }
                }
                Toast.makeText(getContext(), "评论成功！", Toast.LENGTH_SHORT).show();
                editText.setText("");
                 commentRVAdapter.notifyItemRangeChanged(0, replyPosition);
                break;
            case R.id.img_comment_reply_at:
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
            case R.id.img_fragment_comment_close:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        keyboardHeightProvider.close();
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        Log.e(TAG, "onKeyboardHeightChanged: " + height);
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

        recyclerComment.scrollBy(0, height);
//        recyclerComment.scrollBy(0, height);
    }

    private void beginFalling() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = 0;
        layoutBottom.setLayoutParams(params);

        recyclerComment.scrollBy(0, -MyScrollView.dip2px(MyApplication.getContext(), keyboardHeight));
//        recyclerComment.scrollBy(0, -MyScrollView.dip2px(MyApplication.getContext(), keyboardHeight));
    }

    private void emoticonRising() {
        emoticonLayout.setVisibility(View.VISIBLE);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        FragmentEmoticon fragmentEmoticon = new FragmentEmoticon();
        fragmentEmoticon.setEditText(editText);
        transaction.add(R.id.frame_emoticon, fragmentEmoticon, "emoticon");
        transaction.commit();

        inputMethodManager.hideSoftInputFromWindow(getDialog().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
