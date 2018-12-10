package com.asche.wetalk.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.TopicPageRVAdapter;
import com.asche.wetalk.bean.ItemBean;
import com.asche.wetalk.bean.TopicPageBean;
import com.asche.wetalk.fragment.FragmentChatPanel;
import com.asche.wetalk.util.DataUtils;
import com.asche.wetalk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.richeditor.RichEditor;

import static com.asche.wetalk.MyApplication.getContext;
import static com.asche.wetalk.fragment.FragmentHomeSuggest.imgSrc;
import static com.asche.wetalk.fragment.FragmentHomeSuggest.videoSrc;

public class CommentActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack;
    private TextView textTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);

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
