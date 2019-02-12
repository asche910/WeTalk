package com.asche.wetalk.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.yzq.zxinglibrary.encode.CodeCreator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UserTwoCodeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack, imgMore;
    private TextView textTitle;

    private ImageView imgTwoCode;

    // 二维码Bitmap
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_twocode);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        imgMore = findViewById(R.id.img_toolbar_more);
        imgTwoCode = findViewById(R.id.img_user_twocode);

        textTitle.setText("我的二维码");
        imgMore.setBackgroundResource(R.drawable.ic_share_light);

        String content = "wetalk:profile:" + getCurUser().getUserName();
        bitmap = CodeCreator.createQRCode(content, 400, 400, null);
        imgTwoCode.setImageBitmap(bitmap);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_toolbar_more:
                Toast.makeText(this, "分享成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }
}
