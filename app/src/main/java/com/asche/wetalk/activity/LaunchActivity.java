package com.asche.wetalk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class LaunchActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
            }
        }, 2000);
    }
}
