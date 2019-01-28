package com.asche.wetalk.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;

import java.util.HashMap;

import androidx.annotation.Nullable;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.linkedin.LinkedIn;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener{

    private EditText editUsername, editPasswd;
    private boolean isPasswdVisible;

    private Button btnLogin;
    private ImageView imgQQ, imgWeChat, imgLinkedIn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editPasswd = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_login);
        imgQQ = findViewById(R.id.img_login_qq);
        imgWeChat =findViewById(R.id.img_login_wechat);
        imgLinkedIn = findViewById(R.id.img_login_linkedin);

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


        btnLogin.setOnClickListener(this);
        imgQQ.setOnClickListener(this);
        imgWeChat.setOnClickListener(this);
        imgLinkedIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_login:
                break;
            case R.id.img_login_qq:
                Platform platQQ = ShareSDK.getPlatform(QQ.NAME);
                platQQ.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
                platQQ.SSOSetting(false); // SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权

                platQQ.setPlatformActionListener(this);
                platQQ.showUser(null);
                break;
            case R.id.img_login_wechat:
                MaterialDialog inputDialog = new MaterialDialog.Builder(LoginActivity.this)
                        .title("提示")
                        .content("目前版本暂不支持该方式，后期将推出，还望理解！")
                        .positiveText("确认")
                        .show();
                break;
            case R.id.img_login_linkedin:
                Platform platLinkedIn = ShareSDK.getPlatform(LinkedIn.NAME);
                platLinkedIn.removeAccount(true); //移除授权状态和本地缓存，下次授权会重新授权
                platLinkedIn.SSOSetting(false); // SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权

                platLinkedIn.setPlatformActionListener(this);
                platLinkedIn.showUser(null);
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        // nickname, gender(1女2男), icon, userID(唯一识别码)
        Log.e("-------", platform.getDb().exportData());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

        Log.e("-------", "onError: ---------->" );
    }

    @Override
    public void onCancel(Platform platform, int i) {

        Log.e("-------", "onCancel: ---------->" );
    }
}
