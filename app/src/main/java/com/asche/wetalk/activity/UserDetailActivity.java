package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;

import androidx.annotation.Nullable;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack, imgMore;
    private TextView textTitle;

    private EditText textUsername, textGender, textSignature;
    private EditText textCareer, textSchool, textLocation;
    private EditText textEmail, textDescription;

    private UserBean userBean;
    private boolean isOtherUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        imgBack = findViewById(R.id.img_toolbar_back);
        imgMore = findViewById(R.id.img_toolbar_more);
        textTitle = findViewById(R.id.text_toolbar_title);

        textUsername = findViewById(R.id.edit_user_detail_username);
        textGender = findViewById(R.id.edit_user_detail_gender);
        textSignature = findViewById(R.id.edit_user_detail_signature);
        textCareer = findViewById(R.id.edit_user_detail_career);
        textSchool = findViewById(R.id.edit_user_detail_school);
        textLocation = findViewById(R.id.edit_user_detail_location);
        textEmail = findViewById(R.id.edit_user_detail_email);
        textDescription = findViewById(R.id.edit_user_detail_description);


        userBean = (UserBean)getIntent().getSerializableExtra("userBean");
        if (userBean != null){
            textUsername.setText(userBean.getUserName());
            textGender.setText(userBean.getGender());
            textSignature.setText(userBean.getSignature());
            textCareer.setText(userBean.getProfession());
            textSchool.setText(userBean.getSchool());
            textLocation.setText(userBean.getAddress());
            textEmail.setText(userBean.getEmail());
            textDescription.setText(userBean.getDescription());

            if (!getCurUser().equals(userBean)){
                isOtherUser = true;

                textUsername.setEnabled(false);
                textGender.setEnabled(false);
                textSignature.setEnabled(false);
                textCareer.setEnabled(false);
                textSchool.setEnabled(false);
                textLocation.setEnabled(false);
                textEmail.setEnabled(false);
                textDescription.setEnabled(false);
                imgMore.setVisibility(View.GONE);

            }else {
                imgMore.setBackgroundResource(R.drawable.ic_save);

            }
        }


        textTitle.setText("详细资料");
        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_more:
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
