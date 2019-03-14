package com.asche.wetalk.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;

import java.io.File;

import androidx.annotation.Nullable;

import static com.asche.wetalk.activity.UserHomeActivity.PHOTO_FILE_NAME;
import static com.asche.wetalk.activity.UserHomeActivity.PHOTO_REQUEST_CAREMA;
import static com.asche.wetalk.activity.UserHomeActivity.PHOTO_REQUEST_CUT;
import static com.asche.wetalk.activity.UserHomeActivity.PHOTO_REQUEST_GALLERY;
import static com.asche.wetalk.activity.UserHomeActivity.tempFile;

public class IdentifyActivity extends BaseActivity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView textTitle;

    private ImageView imgPhoto;
    private Button btnUpload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        imgPhoto = findViewById(R.id.img_identify_img);
        btnUpload = findViewById(R.id.btn_identify_upload);


        textTitle.setText("专家认证");
        imgBack.setOnClickListener(this);
        imgPhoto.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.img_identify_img:
            case R.id.btn_identify_upload:
                new MaterialDialog.Builder(this)
                        .title("上传资质")
                        .items(new String[]{"拍照上传", "相册选择上传"})
                        .contentColor(Color.parseColor("#333333"))
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Toast.makeText(IdentifyActivity.this, "You Clickd item " + which, Toast.LENGTH_SHORT).show();
                                switch (which) {
                                    case 0:
                                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                        // 判断存储卡是否可以用，可用进行存储
                                        if (hasSdcard()) {
                                            tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                                            // 从文件中创建uri
                                            Uri uri = Uri.fromFile(tempFile);
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                        }
                                        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                                        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                                        intent1.setType("image/*");
                                        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                                        startActivityForResult(intent1, PHOTO_REQUEST_GALLERY);
                                        break;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }

    private boolean hasSdcard() {
        //判断ＳＤ卡是否安装好　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        // intent.putExtra("aspectX", 1);
        // intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小(相机回调时不添加会出现点击确认无反应bug)
         intent.putExtra("outputX", 300);
         intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                 crop(uri);

                // imgPhoto.setImageURI(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                 crop(Uri.fromFile(tempFile));
//                 imgPhoto.setImageURI(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(IdentifyActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                /**
                 * 获得图片
                 */
                imgPhoto.setImageBitmap(bitmap);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /*String url = AsHttpUtils.upImage(bitmapToFile(bitmap));
                        curUser.setImageurl(url);

                        AsHttpUtils.updateUserInfo(curUser);
*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(IdentifyActivity.this, "图片上传成功！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                thread.start();
                Toast.makeText(this, "图片上传中， 请稍等！", Toast.LENGTH_LONG).show();
            }

            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
