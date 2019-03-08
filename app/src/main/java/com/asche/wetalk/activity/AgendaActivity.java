package com.asche.wetalk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;
import com.asche.wetalk.adapter.AgendaRVAdapter;
import com.asche.wetalk.adapter.OnItemClickListener;
import com.asche.wetalk.adapter.OnLongClickListener;
import com.asche.wetalk.bean.AgendaItemBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgBack;
    private TextView textTitle;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AgendaRVAdapter agendaRVAdapter;
    public static List<AgendaItemBean> agendaList = new ArrayList<>();


    private FloatingActionButton btnNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        recyclerView = findViewById(R.id.recycler_agenda);
        btnNew = findViewById(R.id.btn_agenda_new);

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
        btnNew.setOnClickListener(this);

        agendaRVAdapter.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(AgendaActivity.this, AgendaModifyActivity.class);
                intent.putExtra("agenda", position);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(final int position) {
                new MaterialDialog.Builder(AgendaActivity.this)
                        .title("确定删除此事项？")
                        .positiveText("确定")
                        .negativeText("返回")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                agendaList.remove(position);
                                agendaRVAdapter.notifyDataSetChanged();
                                Toast.makeText(AgendaActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_agenda_new:
                Intent intent = new Intent(AgendaActivity.this, AgendaModifyActivity.class);
                startActivity(intent);
                break;
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
