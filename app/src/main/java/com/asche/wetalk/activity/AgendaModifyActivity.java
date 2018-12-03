package com.asche.wetalk.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.AgendaItemBean;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.asche.wetalk.activity.AgendaActivity.agendaList;

public class AgendaModifyActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack, imgMore;
    private TextView textTitle;
    private EditText editText;

    private AgendaItemBean agendaItemBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_modify);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#22000000"));
        }

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        imgMore = findViewById(R.id.img_toolbar_more);
        editText = findViewById(R.id.edit_agenda);

        int position = getIntent().getIntExtra("agenda", 0);
        agendaItemBean = agendaList.get(position);

        if (agendaItemBean != null){
            editText.setText(agendaItemBean.getContent());
        }

        textTitle.setText("修改");
        imgMore.setBackgroundResource(R.drawable.ic_save);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_more:
                Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
                String inputStr = editText.getText().toString();
                agendaItemBean.setContent(inputStr);
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
