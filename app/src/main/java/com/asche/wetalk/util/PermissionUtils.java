package com.asche.wetalk.util;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import static com.asche.wetalk.MyApplication.getContext;

public class PermissionUtils {

    public static boolean checkAllPermission(){
        boolean b1 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.INTERNET));
        boolean b2 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE));
        boolean b3 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE));
        // boolean b4 = (PackageManager.PERMISSION_GRANTED ==ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA));

        return b1 && b2 && b3;
    }


}
