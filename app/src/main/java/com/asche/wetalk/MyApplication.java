package com.asche.wetalk;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;

import com.mob.MobSDK;

import androidx.core.app.ActivityCompat;
import androidx.multidex.MultiDex;

/**
 *
 *
 *
 *                AAA                                                 hhhhhhh
 *               A:::A                                                h:::::h
 *              A:::::A                                               h:::::h
 *             A:::::::A                                              h:::::h
 *            A:::::::::A             ssssssssss       cccccccccccccccch::::h hhhhh           eeeeeeeeeeee
 *           A:::::A:::::A          ss::::::::::s    cc:::::::::::::::ch::::hh:::::hhh      ee::::::::::::ee
 *          A:::::A A:::::A       ss:::::::::::::s  c:::::::::::::::::ch::::::::::::::hh   e::::::eeeee:::::ee
 *         A:::::A   A:::::A      s::::::ssss:::::sc:::::::cccccc:::::ch:::::::hhh::::::h e::::::e     e:::::e
 *        A:::::A     A:::::A      s:::::s  ssssss c::::::c     ccccccch::::::h   h::::::he:::::::eeeee::::::e
 *       A:::::AAAAAAAAA:::::A       s::::::s      c:::::c             h:::::h     h:::::he:::::::::::::::::e
 *      A:::::::::::::::::::::A         s::::::s   c:::::c             h:::::h     h:::::he::::::eeeeeeeeeee
 *     A:::::AAAAAAAAAAAAA:::::A  ssssss   s:::::s c::::::c     ccccccch:::::h     h:::::he:::::::e
 *    A:::::A             A:::::A s:::::ssss::::::sc:::::::cccccc:::::ch:::::h     h:::::he::::::::e
 *   A:::::A               A:::::As::::::::::::::s  c:::::::::::::::::ch:::::h     h:::::h e::::::::eeeeeeee
 *  A:::::A                 A:::::As:::::::::::ss    cc:::::::::::::::ch:::::h     h:::::h  ee:::::::::::::e
 * AAAAAAA                   AAAAAAAsssssssssss        cccccccccccccccchhhhhhh     hhhhhhh    eeeeeeeeeeeeee
 *
 *
 *
 */

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
