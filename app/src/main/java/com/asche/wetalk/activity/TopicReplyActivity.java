package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class TopicReplyActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_reply);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);

        textTitle.setText("话题回答");
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}