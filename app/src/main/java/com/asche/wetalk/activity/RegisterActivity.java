package com.asche.wetalk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.http.AsHttpUtils;
import com.asche.wetalk.util.StringUtils;

import androidx.annotation.Nullable;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private EditText editUsername, editPasswd;
    private boolean isPasswdVisible;

    private Button btnRegister;
    private TextView textUserAgreements;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgBack = findViewById(R.id.img_register_back);
        editUsername = findViewById(R.id.edit_username);
        editPasswd = findViewById(R.id.edit_password);
        btnRegister = findViewById(R.id.btn_register);
        textUserAgreements = findViewById(R.id.text_user_agreements);

        editPasswd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = editPasswd.getCompoundDrawables()[2];
                if (drawable != null) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (event.getX() > editPasswd.getWidth() - editPasswd.getPaddingRight() - drawable.getIntrinsicWidth()) {
                            if (isPasswdVisible) {
                                // 设置不可见
                                editPasswd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                                editPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Drawable drawRight = getDrawable(R.drawable.ic_visible);
                                    drawRight.setBounds(0, 0, drawRight.getMinimumWidth(), drawRight.getMinimumHeight());
                                    editPasswd.setCompoundDrawables(null, null, drawRight, null);
                                    editPasswd.setSelection(editPasswd.getText().toString().length());
                                }
                                isPasswdVisible = false;
                            } else {
                                editPasswd.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                                editPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    Drawable drawRight = getDrawable(R.drawable.ic_invisible);
                                    drawRight.setBounds(0, 0, drawRight.getMinimumWidth(), drawRight.getMinimumHeight());
                                    editPasswd.setCompoundDrawables(null, null, drawRight, null);
                                    editPasswd.setSelection(editPasswd.getText().toString().length());
                                }
                                isPasswdVisible = true;
                            }
                        }
                    }
                }
                return false;
            }
        });

        imgBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        textUserAgreements.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                final String userName = editUsername.getText().toString();
                final String password = editPasswd.getText().toString();
                if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
                    Toast.makeText(this, "内容不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userName.length() < 4){
                    Toast.makeText(this, "用户名长度至少为4", Toast.LENGTH_SHORT).show();
                }
                if (password.length() < 6){
                    Toast.makeText(this, "密码长度至少为6", Toast.LENGTH_SHORT).show();
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final boolean succeed = AsHttpUtils.register(userName, password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (succeed){
                                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("注册成功")
                                            .setContentText("恭喜你注册成功！")
                                            .setConfirmText("确定")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    finish();
                                                }
                                            })
                                            .show();
                                }else {
                                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("注册失败")
                                            .setContentText("用户名已存在！")
                                            .show();
                                }
                            }
                        });

                    }
                }).start();
                break;
            case R.id.text_user_agreements:
                startActivity(new Intent(this, UserAgreementsActivity.class));
                break;
            case R.id.img_register_back:
                finish();
                break;
        }
    }
}
