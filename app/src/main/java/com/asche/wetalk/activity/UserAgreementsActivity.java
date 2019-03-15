package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.data.DataUtils;

import androidx.annotation.Nullable;

public class UserAgreementsActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreements);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        webView = findViewById(R.id.web_user_agreements);

        textTitle.setText("用户协议与版权声明");
        imgBack.setOnClickListener(this);


        webView.loadData(DataUtils.getContent(R.raw.user_agreements), "text/html", "utf-8");

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
