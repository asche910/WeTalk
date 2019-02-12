package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class ScanResultActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;

    private TextView textResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        textResult = findViewById(R.id.text_scan_result);


        String content = getIntent().getStringExtra("content");
        if (content != null){
            textResult.setText(content);
        }

        textTitle.setText("扫描结果");
        imgBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
