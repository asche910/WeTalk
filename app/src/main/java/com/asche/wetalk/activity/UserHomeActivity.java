package com.asche.wetalk.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.other.MyScrollView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener{

    private MyScrollView myScrollView;
    private RelativeLayout toolbarLayout;

    public static TextView textTitle;
    private ImageView imgBack, imgTwocode;

    private ImageView imgBg;

    private Button btnModifyTag, btnModify;
    private TagFlowLayout tagFlowLayout;
    private List<String> tagList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();
        setContentView(R.layout.activity_user_home);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |  View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#22000000"));
        }

        myScrollView = findViewById(R.id.scroll_user_home);
        toolbarLayout = findViewById(R.id.toolbar_user_home);
        textTitle = findViewById(R.id.text_user_home_title);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgTwocode = findViewById(R.id.img_toolbar_twocode);
        imgBg = findViewById(R.id.img_user_home_bg);
        btnModifyTag = findViewById(R.id.btn_user_home_modify_tag);
        btnModify = findViewById(R.id.btn_user_home_modify);
        tagFlowLayout = findViewById(R.id.tagflow_user);

        myScrollView.setupTitleView(toolbarLayout);
        imgBack.setOnClickListener(this);
        imgTwocode.setOnClickListener(this);
        imgBg.setOnClickListener(this);
        btnModifyTag.setOnClickListener(this);
        btnModify.setOnClickListener(this);

        tagList.add("计算机");
        tagList.add("医学");
        tagList.add("冶金");
        tagList.add("炼丹");
        tagList.add("机械");
        tagList.add("计算机");
        tagList.add("医学");
        tagList.add("冶金");
        tagList.add("炼丹");
        tagList.add("机械");

        tagFlowLayout.setAdapter(new TagAdapter<String>(tagList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView view = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_tagflow, tagFlowLayout, false);
                view.setText(s);
                return view;
            }
        });

        UserBean userBean = new UserBean();
        userBean.setNickName("Asche");
        userBean.setGender("Female");

        Log.e("sa", "onCreate: " + userBean.toString() );

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
        }
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user_home_modify:
                Intent intent = new Intent(this, UserModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_user_home_modify_tag:
                break;
            case R.id.img_user_home_bg:
                break;
            case R.id.img_toolbar_twocode:
                Intent intentTwoCodeode = new Intent(this, UserTwoCodeActivity.class);
                startActivity(intentTwoCodeode);
                break;
            case R.id.img_toolbar_back:
                onBackPressed();
                break;
        }
    }
    private void init(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            TransitionSet transitionSet = new TransitionSet();

            Slide slide = new Slide(Gravity.BOTTOM);

//            slide.addTarget(R.id.view_3);
            transitionSet.addTransition(slide);
            getWindow().setEnterTransition(transitionSet);
            transitionSet.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
//                    view_2.setVisibility(View.GONE);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
//                    view_2.setVisibility(View.VISIBLE);
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

}
