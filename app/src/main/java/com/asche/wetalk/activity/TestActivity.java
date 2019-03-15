package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;

import com.asche.wetalk.R;
import com.chinalwb.are.AREditor;

import androidx.annotation.Nullable;

public class TestActivity extends BaseActivity implements View.OnClickListener {

    private AREditor arEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        arEditor = this.findViewById(R.id.areditor);
        arEditor.setExpandMode(AREditor.ExpandMode.FULL);
        arEditor.setHideToolbar(false);
        arEditor.setToolbarAlignment(AREditor.ToolbarAlignment.BOTTOM);
    }

    @Override
    public void onClick(View v) {

    }
}
