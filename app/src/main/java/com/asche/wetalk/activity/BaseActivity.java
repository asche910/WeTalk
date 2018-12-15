package com.asche.wetalk.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.asche.wetalk.MyApplication.getContext;

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 当前登陆的用户
     */
    private static UserBean curUser;

    static {
        UserBean userBean = new UserBean();
        userBean.setId(String.valueOf(100_000));
        userBean.setUserName("Asche");

        userBean.setImgAvatar(String.valueOf(R.drawable.img_avatar));
        userBean.setFollowerNum(6_000);
        userBean.setFollowNum(12);

        curUser = userBean;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#22000000"));
        }
        // 设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 检测是否登陆
     * @return 登陆返回true，反之false
     */
    public boolean checkIfLogin(){
        return curUser != null;
    }

    public static UserBean getCurUser() {
        return curUser;
    }

    public static void setCurUser(UserBean curUser) {
        BaseActivity.curUser = curUser;
    }

    /**
     * 跳转至当前应用的设置详情页
     */
    public void goToAppSettingsPage() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
        finish();
    }

    public void requestAllPermissions(){
        boolean isAllGranted = checkAllPermission();
        if(!isAllGranted){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 0);
        }
    }

    public boolean checkAllPermission(){
        boolean b1 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET));
        boolean b2 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE));
        boolean b3 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE));
        // boolean b4 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA));
        return b1 && b2 && b3;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                for(int result: grantResults){
                    Log.e("", "onRequestPermissionsResult: " + result );
                    if(result != 0){
                        //  TODO 当用户点击不再询问时，引导用户手动授权
                        //  boolean b = shouldShowRequestPermissionRationale(permissions[0]);

                        Toast.makeText(this, "获取权限失败， 软件可能无法正常工作！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
