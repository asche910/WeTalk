package com.asche.wetalk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.AgendaRVAdapter;
import com.asche.wetalk.adapter.OnItemClickListener;
import com.asche.wetalk.bean.AgendaItemBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBack;
    private TextView textTitle;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AgendaRVAdapter agendaRVAdapter;
    public static List<AgendaItemBean> agendaList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#22000000"));
        }

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        recyclerView = findViewById(R.id.recycler_agenda);

        if (agendaList.isEmpty()) {
            agendaList.add(new AgendaItemBean("Hello, World!", "2018-10-24"));
            agendaList.add(new AgendaItemBean("java实现类似跳一跳的外挂（2018年10月4日）", "2018-10-24"));
            agendaList.add(new AgendaItemBean("java后台实现 聊天功能（2018.7.14）", "2018-10-24"));
        }

        agendaRVAdapter = new AgendaRVAdapter(agendaList);
        layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(agendaRVAdapter);

        textTitle.setText("待办事项");
        imgBack.setOnClickListener(this);

        agendaRVAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(AgendaActivity.this, AgendaModifyActivity.class);
                intent.putExtra("agenda", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        agendaRVAdapter.notifyDataSetChanged();
    }
}
