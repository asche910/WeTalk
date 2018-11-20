package com.asche.wetalk.activity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.BodyContentRVAdapter;
import com.asche.wetalk.bean.BodyContentBean;
import com.asche.wetalk.util.BodyContentUtil;
import com.asche.wetalk.util.DataUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BodyContentRVAdapter bodyContentRVAdapter;
    public static List<BodyContentBean> bodyContentBeanList = new ArrayList<>();


    private final String TAG = "Article";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        recyclerView = findViewById(R.id.recycler_article);

        bodyContentBeanList = BodyContentUtil.parseHtml(DataUtils.getArticle());


        bodyContentRVAdapter = new BodyContentRVAdapter(bodyContentBeanList);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bodyContentRVAdapter);
        recyclerView.setItemViewCacheSize(999);

    }

    @Override
    public void onClick(View v) {

    }
}
