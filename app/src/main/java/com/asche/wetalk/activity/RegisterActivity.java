package com.asche.wetalk.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private EditText editUsername, editPasswd;
    private boolean isPasswdVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgBack = findViewById(R.id.img_register_back);
        editPasswd = findViewById(R.id.edit_password);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_register_back:
                finish();
                break;
        }
    }
}
