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
        textView = findViewById(R.id.text_article);
        recyclerView = findViewById(R.id.recycler_article);

        String text="我的URL：<a href='https://github.com/asche910'>click here</a>\n";	//这里的\n是换行符
        text += "我的Email:asche910@163.com\n";
        text += "我的<b>电话</b>：+86148998933";
        text += "<img src=\"https://avatars1.githubusercontent.com/u/13347412?s=400&amp;u=dff9d7b137708303a966cdd62ee4151d50b85b79&amp;v=4\" width=\"200px\" height=\"400px\" alt=\"图片\" style=\"max-width:100%;\">";
        text += "hello, World!";

        CharSequence sequence = Html.fromHtml(text);

        String[] strs = text.split("<a .+>.*</a>|<img .+>");

        Matcher matcher = Pattern.compile("<a .+>.*</a>|<img .+>").matcher(text);
        while (matcher.find()){
            Log.e(TAG, "onCreate: " + matcher.group() );

        }

        Log.e(TAG, "onCreate: "+ strs.length + Arrays.toString(strs) );
        textView.setText(sequence);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        bodyContentBeanList.add(new BodyContentBean(0, "我的URL：", null, null));
        bodyContentBeanList.add(new BodyContentBean(1, null, "https://avatars1.githubusercontent.com/u/13347412?s=400&amp;u=dff9d7b137708303a966cdd62ee4151d50b85b79&amp;v=4", null));
        bodyContentBeanList.add(new BodyContentBean(0, "我的Email:asche910@163.com\n    我的<b>电话</b>：+86148998933", null, null));
        bodyContentBeanList.add(new BodyContentBean(2, null, "http://img.mms.v1.cn/static/mms/images/2018-10-18/201810181131393298.jpg", "http://f10.v1.cn/site/15538790.mp4.f40.mp4"));
        bodyContentBeanList.add(new BodyContentBean(0, " hello, World!", null, null));

        bodyContentBeanList.add(new BodyContentBean(0, "我的URL：", null, null));
        bodyContentBeanList.add(new BodyContentBean(1, null, "https://avatars1.githubusercontent.com/u/13347412?s=400&amp;u=dff9d7b137708303a966cdd62ee4151d50b85b79&amp;v=4", null));
        bodyContentBeanList.add(new BodyContentBean(0, "我的Email:asche910@163.com\n    我的<b>电话</b>：+86148998933", null, null));
        bodyContentBeanList.add(new BodyContentBean(2, null, "http://img.mms.v1.cn/static/mms/images/2018-10-18/201810181131393298.jpg", "http://f10.v1.cn/site/15538790.mp4.f40.mp4"));
        bodyContentBeanList.add(new BodyContentBean(0, " hello, World!", null, null));

        bodyContentRVAdapter = new BodyContentRVAdapter(BodyContentUtil.parseHtml(DataUtils.getArticle()));
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bodyContentRVAdapter);

    }

    @Override
    public void onClick(View v) {

    }
}
