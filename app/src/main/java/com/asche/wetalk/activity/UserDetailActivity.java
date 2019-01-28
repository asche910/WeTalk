package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.asche.wetalk.R;

import androidx.annotation.Nullable;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;

    private TextView textName, textGender, textSignature;
    private TextView textCareer, textSchool, textLocation;
    private TextView textEmail, textDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);

        textName = findViewById(R.id.text_user_detail_name);
        textGender = findViewById(R.id.text_user_detail_gender);
        textSignature = findViewById(R.id.text_user_detail_signature);
        textCareer = findViewById(R.id.text_user_detail_career);
        textSchool = findViewById(R.id.text_user_detail_school);
        textLocation = findViewById(R.id.text_user_detail_location);
        textEmail = findViewById(R.id.text_user_detail_email);
        textDescription = findViewById(R.id.text_user_detail_description);

        textTitle.setText("详细资料");
        imgBack.setOnClickListener(this);
        textName.setOnClickListener(this);
        textGender.setOnClickListener(this);
        textSignature.setOnClickListener(this);
        textCareer.setOnClickListener(this);
        textSchool.setOnClickListener(this);
        textLocation.setOnClickListener(this);
        textEmail.setOnClickListener(this);
        textDescription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
