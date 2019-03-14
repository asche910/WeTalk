package com.asche.wetalk.activity;

import android.os.Bundle;
import android.util.Log;

import com.asche.wetalk.R;
import com.asche.wetalk.fragment.FragmentDiscover;
import com.asche.wetalk.fragment.FragmentHome;
import com.asche.wetalk.fragment.FragmentNotification;
import com.asche.wetalk.fragment.FragmentUser;
import com.asche.wetalk.spider.ArticleSpider;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author Asche
 * @date 2018-11-9 17:9:53
 */
public class MainActivity extends BaseActivity {

    private BottomNavigationBar bottomNavigationBar;

    private FragmentHome fragmentHome;
    private FragmentDiscover fragmentDiscover;
    private FragmentNotification fragmentNotification;
    private FragmentUser fragmentUser;
    private List<Fragment> fragmentList = new ArrayList<>();

    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkAllPermission()){
            requestAllPermissions();
        }

        // 耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArticleSpider.start();
            }
        }).start();

        bottomNavigationBar = findViewById(R.id.nav_bottom_home);

        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_nav_home_default, "首页").setInActiveColor("#818080"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_nav_discover_default, "发现").setInActiveColor("#818080"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_nav_notification_default, "消息").setInActiveColor("#818080"));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_nav_user_default, "用户").setInActiveColor("#818080"));

        bottomNavigationBar.setFirstSelectedPosition(0);
        bottomNavigationBar.initialise();

        fragmentHome = new FragmentHome();
        fragmentList.add(fragmentHome);
        fragmentList.add(fragmentDiscover);
        fragmentList.add(fragmentNotification);
        fragmentList.add(fragmentUser);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_main, fragmentHome);
        fragmentTransaction.commit();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.e(TAG, "onTabSelected: " + position);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = null;
                if (fragmentList.get(position) == null) {
                    switch (position) {
                        case 3:
                            fragmentUser = new FragmentUser();
                            fragment = fragmentUser;
                            break;
                        case 2:
                            fragmentNotification = new FragmentNotification();
                            fragment = fragmentNotification;
                            break;
                        case 1:
                            fragmentDiscover = new FragmentDiscover();
                            fragment = fragmentDiscover;
                            break;
                        case 0:
                            fragmentHome = new FragmentHome();
                            fragment = fragmentHome;
                            break;
                    }
                    transaction.add(R.id.frame_main, fragment);
                } else {
                    fragment = fragmentList.get(position);
                }
                hideAllFragment(transaction);
                transaction.show(fragment);
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
                Log.e(TAG, "onTabReselected: " + position);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (fragmentHome != null) {
            transaction.hide(fragmentHome);
        }
        if (fragmentDiscover != null) {
            transaction.hide(fragmentDiscover);
        }
        if (fragmentNotification != null) {
            transaction.hide(fragmentNotification);
        }
        if (fragmentUser != null) {
            transaction.hide(fragmentUser);
        }
    }
}
