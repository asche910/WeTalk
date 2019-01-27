package com.asche.wetalk;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;

import com.mob.MobSDK;

import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        MobSDK.init(this);

        // In this way the VM ignores the file URI exposure.
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
