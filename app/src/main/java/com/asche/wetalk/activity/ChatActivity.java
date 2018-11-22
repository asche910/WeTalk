package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.ChatRVAdapter;
import com.asche.wetalk.bean.ChatItemBean;
import com.asche.wetalk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgBack, imgMore, imgEmoticon;
    private TextView textTitle;
    private EditText editInput;
    private Button btnSend;


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ChatRVAdapter chatRVAdapter;
    private List<ChatItemBean> chatItemBeanList = new ArrayList<>();

    private boolean isEmoticonPressed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#22000000"));
        }

        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        textTitle = findViewById(R.id.text_toolbar_title);
        recyclerView = findViewById(R.id.recycler_chat);
        editInput = findViewById(R.id.edit_chat);
        imgEmoticon = findViewById(R.id.img_chat_emoticon);
        btnSend = findViewById(R.id.btn_chat_send);

        chatItemBeanList.add(new ChatItemBean(0, "Hi, Robot!", R.drawable.img_avatar + ""));
        chatItemBeanList.add(new ChatItemBean(1, "Hello, Asche!", R.drawable.img_avatar_default + ""));
        chatItemBeanList.add(new ChatItemBean(0, "How old are you ? Robot?", R.drawable.img_avatar + ""));
        chatItemBeanList.add(new ChatItemBean(1, "I'm 18 years old!", R.drawable.img_avatar_default + ""));
        chatItemBeanList.add(new ChatItemBean(0, "How old are you ? Robot?", R.drawable.img_avatar + ""));
        chatItemBeanList.add(new ChatItemBean(1, "I'm 18 years old!", R.drawable.img_avatar_default + ""));

        chatRVAdapter = new ChatRVAdapter(chatItemBeanList);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(chatRVAdapter);


        textTitle.setText("聊天");
        imgMore.setImageResource(R.drawable.ic_more_light);
        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        imgEmoticon.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chat_send:
                String inputStr = editInput.getText().toString();
                if (StringUtils.isEmpty(inputStr)){
                    Toast.makeText(this, "消息不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                chatItemBeanList.add(new ChatItemBean(0, inputStr, R.drawable.img_avatar + ""));
                chatRVAdapter.notifyItemInserted(chatItemBeanList.size() - 1);
                recyclerView.scrollToPosition(chatItemBeanList.size() - 1);

                break;
            case R.id.img_chat_emoticon:
                if (!isEmoticonPressed){
                    isEmoticonPressed = true;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon_pressed);
                }else {
                    isEmoticonPressed = false;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon);
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
}
