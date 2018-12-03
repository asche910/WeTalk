package com.asche.wetalk.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import com.asche.wetalk.MyApplication;
import com.asche.wetalk.R;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.adapter.OnItemClickListener;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.helper.KeyboardHeightObserver;
import com.asche.wetalk.helper.KeyboardHeightProvider;
import com.asche.wetalk.other.MyScrollView;
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
        init();
    }

    private void init() {
        recyclerComment = getView().findViewById(R.id.recycler_dialog_comment);
        imgClose = getView().findViewById(R.id.img_fragment_comment_close);
        imgEmoticon = getView().findViewById(R.id.img_comment_reply_emoticon);
        imgAt = getView().findViewById(R.id.img_comment_reply_at);
        imgSend = getView().findViewById(R.id.img_comment_reply_send);
        editText = getView().findViewById(R.id.edit_comment_reply);
        layoutBottom = getView().findViewById(R.id.layout_bottom);
        emoticonLayout = getView().findViewById(R.id.frame_emoticon);


        commentRVAdapter = new CommentRVAdapter(commentNormalList);
        layoutManagerComment = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManagerComment);
        recyclerComment.setAdapter(commentRVAdapter);

        imgClose.setOnClickListener(this);
        imgEmoticon.setOnClickListener(this);
        imgAt.setOnClickListener(this);

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
        switch (v.getId()){
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
        Log.e(TAG, "onKeyboardHeightChanged: " + height );
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
    }

    private void beginFalling() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = 0;
        layoutBottom.setLayoutParams(params);

        recyclerComment.scrollBy(0, -MyScrollView.dip2px(MyApplication.getContext(), keyboardHeight));
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