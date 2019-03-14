package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class ReportActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;
    private EditText editText;
    private Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        btnOk = findViewById(R.id.btn_report);

        textTitle.setText("举报");
        imgBack.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_report:
                Toast.makeText(this, "举报成功，感谢你的反馈！", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
