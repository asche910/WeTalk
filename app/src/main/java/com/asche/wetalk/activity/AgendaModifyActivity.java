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
import com.asche.wetalk.util.StringUtils;
import com.asche.wetalk.util.TimeUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.asche.wetalk.activity.AgendaActivity.agendaList;

public class AgendaModifyActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack, imgMore;
    private TextView textTitle;
    private EditText editText;

    private AgendaItemBean agendaItemBean;

    private boolean isNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_modify);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        imgMore = findViewById(R.id.img_toolbar_more);
        editText = findViewById(R.id.edit_agenda);

        int position = getIntent().getIntExtra("agenda", -1);
        if (position == -1){
            agendaItemBean = new AgendaItemBean();
            textTitle.setText("新增事项");
            isNew = true;
        }else {
            agendaItemBean = agendaList.get(position);
            editText.setText(agendaItemBean.getContent());
            textTitle.setText("修改事项");
        }

        imgMore.setImageResource(R.drawable.ic_save);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_more:
                String inputStr = editText.getText().toString();
                if (StringUtils.isEmpty(inputStr)){
                    Toast.makeText(this, "内容不能为空哦！", Toast.LENGTH_SHORT).show();
                    break;
                }
                agendaItemBean.setContent(inputStr);

                if (isNew){
                    agendaItemBean.setTime(TimeUtils.getCurrentTime());
                    agendaList.add(agendaItemBean);
                }
                Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
