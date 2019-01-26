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

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class LoginActivity extends BaseActivity {

    private EditText editUsername, editPasswd;
    private boolean isPasswdVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
    }

    // 设置密码可见性
    private void setPasswdVisible(EditText editText) {
        if (EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD == editText.getInputType()) {
            // 如果不可见就设置为可见
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            // 如果可见就设置为不可见
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        // 执行上面的代码后光标会处于输入框的最前方,所以把光标位置挪到文字的最后面
        editText.setSelection(editText.getText().toString().length());
    }

}
