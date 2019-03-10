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

public class PasswordActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;

    private EditText editPasswdOld, editPasswdNew, editPasswdAgain;

    private Button btnOk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        editPasswdOld = findViewById(R.id.edit_password_old);
        editPasswdNew = findViewById(R.id.edit_password_new);
        editPasswdAgain = findViewById(R.id.edit_password_again);
        btnOk  = findViewById(R.id.btn_password_ok);

        textTitle.setText("修改密码");
        imgBack.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_password_ok:
                Toast.makeText(this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
