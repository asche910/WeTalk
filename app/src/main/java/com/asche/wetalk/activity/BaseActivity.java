package com.asche.wetalk.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.asche.wetalk.MyApplication.getContext;

public abstract class BaseActivity extends AppCompatActivity {

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
                        Toast.makeText(this, "获取权限失败， 软件可能无法正常工作！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
