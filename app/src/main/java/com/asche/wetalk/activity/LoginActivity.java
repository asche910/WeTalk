package com.asche.wetalk.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;
import com.asche.wetalk.http.AsHttpUtils;
import com.asche.wetalk.util.StringUtils;

import java.util.HashMap;

import androidx.annotation.Nullable;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.linkedin.LinkedIn;
import cn.sharesdk.tencent.qq.QQ;

public class LoginActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener {

    private EditText editUsername, editPasswd;
    private boolean isPasswdVisible;

    private Button btnLogin;
    private TextView textRegister;

    private TextView textUserAgreements;

    private ImageView imgQQ, imgWeChat, imgLinkedIn;

    private SweetAlertDialog pDialog;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.edit_username);
        editPasswd = findViewById(R.id.edit_password);
        btnLogin = findViewById(R.id.btn_login);
        textRegister = findViewById(R.id.text_register);
        textUserAgreements = findViewById(R.id.text_user_agreements);
        imgQQ = findViewById(R.id.img_login_qq);
        imgWeChat = findViewById(R.id.img_login_wechat);
        imgLinkedIn = findViewById(R.id.img_login_linkedin);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("登录中...");
        pDialog.setCancelable(true);

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
        textRegister.setOnClickListener(this);
        textUserAgreements.setOnClickListener(this);
        imgQQ.setOnClickListener(this);
        imgWeChat.setOnClickListener(this);
        imgLinkedIn.setOnClickListener(this);

    /*    new Thread(new Runnable() {
            @Override
            public void run() {
                AsHttpUtils.login("root", "root");
            }
        }).start();*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_user_agreements:
                startActivity(new Intent(this, UserAgreementsActivity.class));
                break;
            case R.id.text_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
                final String userName = editUsername.getText().toString();
                final String password = editPasswd.getText().toString();
                if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
                    Toast.makeText(this, "内容不能为空哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
                pDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean succeed = AsHttpUtils.login(userName, password);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (succeed) {
                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            pDialog.cancel();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("登录失败")
                                            .setContentText("用户名不存在或密码错误！")
                                            .show();
                                    pDialog.cancel();
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.img_login_qq:
                Platform platQQ = ShareSDK.getPlatform(QQ.NAME);
                platQQ.removeAccount(false); //移除授权状态和本地缓存，下次授权会重新授权
                platQQ.SSOSetting(false); // SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权

                platQQ.setPlatformActionListener(this);
                platQQ.showUser(null);

                pDialog.show();
                break;
            case R.id.img_login_wechat:
                new MaterialDialog.Builder(LoginActivity.this)
                        .title("提示")
                        .content("目前版本暂不支持该方式，后期将推出，还望理解！")
                        .positiveText("确认")
                        .show();
                break;
            case R.id.img_login_linkedin:
                Platform platLinkedIn = ShareSDK.getPlatform(LinkedIn.NAME);
                platLinkedIn.removeAccount(false); //移除授权状态和本地缓存，下次授权会重新授权
                platLinkedIn.SSOSetting(false); // SSO授权，传false默认是客户端授权，没有客户端授权或者不支持客户端授权会跳web授权

                platLinkedIn.setPlatformActionListener(this);
                platLinkedIn.showUser(null);

                pDialog.show();
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        // nickname, gender(1女2男), icon, userID(唯一识别码)
        Log.e("-------", platform.getDb().exportData());
        AsHttpUtils.parseThirdPartyData(platform.getDb().exportData());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                pDialog.cancel();
            }
        });
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

        Log.e("-------", "onError: ---------->");
    }

    @Override
    public void onCancel(Platform platform, int i) {

        Log.e("-------", "onCancel: ---------->");
    }
}
