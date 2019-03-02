package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class VIPCenterActivity extends BaseActivity implements View.OnClickListener {


    private LinearLayout layoutToolbar;

    private ImageView imgBack;
    private TextView textTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipcenter);

        layoutToolbar = findViewById(R.id.layout_toolbar_universe);
        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);

        layoutToolbar.setBackgroundColor(Color.parseColor("#3D3226"));

        textTitle.setText("会员中心");
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
