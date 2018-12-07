package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CollectActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);

        textTitle.setText("收藏");
        imgBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
