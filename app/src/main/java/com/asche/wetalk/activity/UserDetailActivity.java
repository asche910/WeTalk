package com.asche.wetalk.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.http.AsHttpUtils;

import androidx.annotation.Nullable;

public class UserDetailActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack, imgSave;
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
        imgSave = findViewById(R.id.img_toolbar_more);
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
                imgSave.setVisibility(View.GONE);

            }else {
                imgSave.setBackgroundResource(R.drawable.ic_save);

            }
        }


        textTitle.setText("详细资料");
        imgBack.setOnClickListener(this);
        imgSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_more:
                String gender = textGender.getText().toString();
                String sig = textSignature.getText().toString();
                String profession = textCareer.getText().toString();
                String address = textLocation.getText().toString();
                String desc = textDescription.getText().toString();

                UserBean userBean = getCurUser();
                userBean.setGender(gender);
                userBean.setSignature(sig);
                userBean.setProfession(profession);
                userBean.setAddress(address);
                userBean.setDescription(desc);

                setCurUser(userBean);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AsHttpUtils.updateUser();
                    }
                }).start();

                Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
