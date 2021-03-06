package com.asche.wetalk.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.ChatRVAdapter;
import com.asche.wetalk.bean.ChatItemBean;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.data.UserUtils;
import com.asche.wetalk.fragment.FragmentChatPanel;
import com.asche.wetalk.fragment.FragmentEmoticon;
import com.asche.wetalk.helper.KeyboardHeightObserver;
import com.asche.wetalk.helper.KeyboardHeightProvider;
import com.asche.wetalk.http.ChatHttp;
import com.asche.wetalk.http.HttpCallBack;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.storage.ChatStorage;
import com.asche.wetalk.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.MyApplication.getContext;
import static com.asche.wetalk.storage.ChatStorage.storeChatRecord;

public class ChatActivity extends BaseActivity implements View.OnClickListener, KeyboardHeightObserver, HttpCallBack {

    private ImageView imgBack, imgMore;
    private TextView textTitle;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ChatRVAdapter chatRVAdapter;
    public static List<ChatItemBean> chatItemBeanList = new ArrayList<>();

    private ImageView imgEmoticon, imgAt, imgInputMore, imgSend;
    private EditText editChatInput;

    private KeyboardHeightProvider keyboardHeightProvider;
    public static InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    private LinearLayout layoutBottom;

    private UserBean userBean;

    // 默认278dp，若可以则更新为系统输入法高度
    public static int keyboardHeight = 278;
    private boolean isEmoticonPressed;
    private boolean isPanelPressed;
    private boolean isInputMethodShow;

    private final String TAG = "ChatActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        textTitle = findViewById(R.id.text_toolbar_title);
        recyclerView = findViewById(R.id.recycler_chat);
        editChatInput = findViewById(R.id.edit_chat);
        imgEmoticon = findViewById(R.id.img_chat_emoticon);
        imgAt = findViewById(R.id.img_chat_at);
        imgSend = findViewById(R.id.img_chat_send);
        imgInputMore = findViewById(R.id.img_chat_more);
        layoutBottom = findViewById(R.id.layout_chat_bottom);


        userBean = (UserBean) getIntent().getSerializableExtra("chatWith");
        if (userBean == null){
            userBean = UserUtils.getUser(0);
        }else {
            textTitle.setText(userBean.getNickName());
        }

        try {
            chatItemBeanList = ChatStorage.readChatRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (chatItemBeanList == null || chatItemBeanList.isEmpty()) {
            chatItemBeanList = new ArrayList<>();

            //  每次可能会变， 则导致两次相同资源文件的id对应不同文件
            chatItemBeanList.add(new ChatItemBean(0, "Hi, Robot!", R.drawable.img_avatar + ""));
            chatItemBeanList.add(new ChatItemBean(1, "Hello, Asche!", R.drawable.img_avatar_default + ""));
            chatItemBeanList.add(new ChatItemBean(0, "How old are you ? Robot?", R.drawable.img_avatar + ""));
            chatItemBeanList.add(new ChatItemBean(1, "I'm 18 years old!", R.drawable.img_avatar_default + ""));
            chatItemBeanList.add(new ChatItemBean(0, "How old are you ? Robot?", R.drawable.img_avatar + ""));
            chatItemBeanList.add(new ChatItemBean(1, "I'm 18 years old!", R.drawable.img_avatar_default + ""));
        }

        chatRVAdapter = new ChatRVAdapter(chatItemBeanList, userBean);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatRVAdapter);
        recyclerView.scrollToPosition(chatItemBeanList.size() - 1);


        imgMore.setImageResource(R.drawable.ic_more_light);
        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        imgEmoticon.setOnClickListener(this);
        imgAt.setOnClickListener(this);
        imgSend.setOnClickListener(this);
        imgInputMore.setOnClickListener(this);

        editChatInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                if (!StringUtils.isEmpty(str)){
                    imgInputMore.setVisibility(View.GONE);
                    imgSend.setVisibility(View.VISIBLE);
                }else {
                    imgInputMore.setVisibility(View.VISIBLE);
                    imgSend.setVisibility(View.GONE);
                }
            }
        });

        keyboardHeightProvider = new KeyboardHeightProvider(this, R.layout.activity_chat);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                keyboardHeightProvider.start();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_chat_send:
                String inputStr = editChatInput.getText().toString();
                if (StringUtils.isEmpty(inputStr)) {
                    Toast.makeText(this, "消息不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatHttp chatHttp = new ChatHttp(inputStr);
                chatHttp.setHttpCallBack(this);
                chatHttp.send();

                chatItemBeanList.add(new ChatItemBean(0, inputStr, R.drawable.img_avatar + ""));
                chatRVAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
                recyclerView.scrollToPosition(chatItemBeanList.size() - 1);
                editChatInput.setText("");

                break;
            case R.id.img_chat_more:
                if (!isPanelPressed) {
                    isPanelPressed = true;
                    panelRising();
                    beginRising();
                } else {
                    isPanelPressed = false;
                    panelFalling();
                    beginFalling();
                }
                break;
            case R.id.img_chat_at:
                Toast.makeText(this, "@somebody", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_chat_emoticon:
                if (!isEmoticonPressed) {
                    isEmoticonPressed = true;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon_pressed);

                    emoticonRising();
                    beginRising();
                } else {
                    isEmoticonPressed = false;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon);

                    emoticonFalling();
                    beginFalling();
                }
                break;
            case R.id.img_toolbar_more:
                Toast.makeText(this, "More!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void callBack(String flag) {
        if (flag.equals("update")){
            chatRVAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
            recyclerView.scrollToPosition(chatItemBeanList.size() - 1);
        }
    }

    /**
     *  判断点击外界是否关闭表情栏或输入法
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldFalling(View v, MotionEvent event) {
        if (v != null && !(v instanceof ImageView)) {
            int[] leftTop = new int[]{0, 0};
            layoutBottom.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1];
            int bottom = top + v.getHeight(), right = left + v.getWidth();
            return event.getY() < top;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldFalling(view, ev)) {
                if (isEmoticonPressed) {
                    emoticonFalling();
                    beginFalling();
                    isEmoticonPressed = false;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon);
                }
                if (isInputMethodShow) {
                    inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
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
    public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();

        try {
            storeChatRecord(chatItemBeanList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        Log.e(TAG, "onKeyboardHeightChanged: " + (height / 4) + "-" + orientation);
        isInputMethodShow = height != 0;

        try {
            if (height > 0) {
                if (isEmoticonPressed || isPanelPressed) {
                    if (isEmoticonPressed) {
                        emoticonFalling();
                        isEmoticonPressed = false;
                        imgEmoticon.setImageResource(R.drawable.ic_emoticon);
                    }else {
                        panelFalling();
                        isPanelPressed = false;
                    }
                } else {
                    beginRising();
                }
            } else {
                if (isEmoticonPressed || isPanelPressed) {
                } else {
                    beginFalling();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 表情栏或更多点击时 ---> 输入框向上托起
     */
    private void beginRising() {
        int height = MyScrollView.dip2px(getApplicationContext(), keyboardHeight);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = height;
        layoutBottom.setLayoutParams(params);
        recyclerView.scrollBy(0, height);
    }

    private void beginFalling() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = 0;
        layoutBottom.setLayoutParams(params);
        recyclerView.scrollBy(0, 0);
    }

    private void emoticonRising() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentEmoticon fragmentEmoticon = new FragmentEmoticon();
        fragmentEmoticon.setEditText(editChatInput);
        transaction.add(R.id.frame_chat, fragmentEmoticon, "emoticon");
        transaction.commit();

        View curView = this.getCurrentFocus();
        if (curView != null)
            inputMethodManager.hideSoftInputFromWindow(curView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        if (isPanelPressed){
            panelFalling();
            isPanelPressed = false;
        }
    }

    private void emoticonFalling() {
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(getSupportFragmentManager().findFragmentByTag("emoticon"));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void panelRising(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentChatPanel fragmentChatPanel = new FragmentChatPanel();
        if (isEmoticonPressed){
            transaction.replace(R.id.frame_chat, fragmentChatPanel, "panel");
            isEmoticonPressed = false;
            imgEmoticon.setImageResource(R.drawable.ic_emoticon);
        }else {
            transaction.add(R.id.frame_chat, fragmentChatPanel, "panel");
        }
        transaction.commit();

        View curView = this.getCurrentFocus();
        if (curView != null)
            inputMethodManager.hideSoftInputFromWindow(curView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void panelFalling(){
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(getSupportFragmentManager().findFragmentByTag("panel"));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
