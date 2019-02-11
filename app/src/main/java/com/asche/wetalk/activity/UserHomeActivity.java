package com.asche.wetalk.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;
import com.asche.wetalk.bean.UserBean;
import com.asche.wetalk.data.TechTags;
import com.asche.wetalk.data.UserUtils;
import com.asche.wetalk.helper.DropZoomScrollView;
import com.asche.wetalk.util.LoaderUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

import static com.asche.wetalk.MyApplication.getContext;

public class UserHomeActivity extends BaseActivity implements View.OnClickListener {

    private DropZoomScrollView dropZoomScrollView;
    private RelativeLayout toolbarLayout;

    public static TextView textTitle;
    private ImageView imgBack, imgTwocode;

    private ImageView imgBg, imgAvatar;
    private TextView textNickName, textSignature, textFollowerNum, textFollowNum, textCareer, textLocation;


    private Button btnDetail, btnWork;
    private TagFlowLayout tagFlowLayout;
    private List<String> tagList = new ArrayList<>();


    private UserBean userBean;
    private boolean isOtherUser; // 判断查看的是否是用户自己

    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    /* 头像名称 *///
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        init();
        setContentView(R.layout.activity_user_home);

        //<editor-fold defaultstate="collapsed" desc="Id Initialization">
        toolbarLayout = findViewById(R.id.toolbar_user_home);
        textTitle = findViewById(R.id.text_user_home_title);
        imgBack = findViewById(R.id.img_toolbar_back);
        imgTwocode = findViewById(R.id.img_toolbar_twocode);
        imgBg = findViewById(R.id.img_user_home_bg);
        imgAvatar = findViewById(R.id.img_user_home_avatar);
        textNickName = findViewById(R.id.text_user_home_nickname);
        textSignature = findViewById(R.id.text_user_home_signature);
        textFollowerNum = findViewById(R.id.text_user_home_follower);
        textFollowNum = findViewById(R.id.text_user_home_follow);
        textCareer = findViewById(R.id.text_user_home_career);
        textLocation = findViewById(R.id.text_user_home_location);
        btnDetail = findViewById(R.id.btn_user_home_detail);
        btnWork = findViewById(R.id.btn_user_home_work);
        tagFlowLayout = findViewById(R.id.tagflow_user);
        dropZoomScrollView = findViewById(R.id.scroll_user_home);
        //</editor-fold>

        dropZoomScrollView.setDropView(imgBg);
        dropZoomScrollView.setupTitleView(toolbarLayout);

        imgBack.setOnClickListener(this);
        imgTwocode.setOnClickListener(this);
        imgBg.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
        btnDetail.setOnClickListener(this);
        btnWork.setOnClickListener(this);

        tagList.addAll(TechTags.getTagsList().subList(0, 5));

        tagFlowLayout.setAdapter(new TagAdapter<String>(tagList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView view = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_tagflow, tagFlowLayout, false);
                view.setText(s);
                return view;
            }
        });

        UserBean user = (UserBean) getIntent().getSerializableExtra("user");
        if (user != null) {
            userBean = user;

            LoaderUtils.loadImage(userBean.getImgAvatar(), getApplicationContext(), imgAvatar);
            LoaderUtils.loadImage(userBean.getImgBg(), getApplicationContext(), imgBg);

            textTitle.setText(userBean.getNickName() + "的主页");
            textNickName.setText(userBean.getNickName());
            textSignature.setText(userBean.getSignature());
            textFollowerNum.setText("粉丝 " + userBean.getFollowerNum());
            textFollowNum.setText("关注 " + userBean.getFollowNum());
            textCareer.setText(userBean.getProfession());
            textLocation.setText(userBean.getAddress());

            if (!getCurUser().equals(userBean)) {
                isOtherUser = true;
            }
            Log.e("---", "onCreate: " + isOtherUser );

            Log.e("---", "onCreate: " + (UserUtils.getUser(1)).equals(UserUtils.getUser(1)) );
        }else {
            userBean = UserUtils.getUser(1);
        }

        Log.e("sa", "onCreate: " + userBean.toString());


/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_user_home_work:
                Intent intent = new Intent(this, UserModifyActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_user_home_detail:
                showMenu(getApplicationContext(), v);
                break;
            case R.id.img_user_home_avatar:
                new MaterialDialog.Builder(this)
                        .items(new String[]{"大图预览", "拍照上传", "相册选择上传"})
                        .contentColor(Color.parseColor("#333333"))
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                Toast.makeText(UserHomeActivity.this, "You Clickd item " + which, Toast.LENGTH_SHORT).show();
                                switch (which) {
                                    case 0:
                                        ImageInfo imageInfo = new ImageInfo();
                                        imageInfo.setOriginUrl(userBean.getImgAvatar());

                                        List<ImageInfo> infoList = new ArrayList<>();
                                        infoList.add(imageInfo);

                                        ImagePreview
                                                .getInstance()
                                                .setContext(UserHomeActivity.this)
                                                .setImageInfoList(infoList)
                                                .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                                                .setEnableClickClose(false)
                                                .setEnableDragClose(true)
                                                .setShowCloseButton(true)
                                                .start();
                                        break;
                                    case 1:
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
                                    case 2:
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

    private void showMenu(final Context context, View anchor) {
        PopupMenu popupMenu = new PopupMenu(context, anchor);
        popupMenu.inflate(R.menu.menu_user_home);

        if (isOtherUser){
            // 其它用户
            if (userBean.isExpert()){
                popupMenu.getMenu().findItem(R.id.menu_user_message).setVisible(false);
            }else {
                popupMenu.getMenu().findItem(R.id.menu_user_message_money).setVisible(false);
            }
        }else {
            // 用户自己
            popupMenu.getMenu().findItem(R.id.menu_user_follow).setVisible(false);
            popupMenu.getMenu().findItem(R.id.menu_user_message).setVisible(false);
            popupMenu.getMenu().findItem(R.id.menu_user_message_money).setVisible(false);
        }

        popupMenu.getMenu().findItem(R.id.menu_user_follow).setChecked(true);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_user_follow:
                        if (item.isChecked()){
                            item.setChecked(false);
                        }else {
                            item.setChecked(true);
                        }
                        break;
                    case R.id.menu_user_message:
                        Intent intent = new Intent(getContext(), ChatActivity.class);
                        intent.putExtra("chatWith", userBean);
                        startActivity(intent);
                        break;
                    case R.id.menu_user_message_money:
                        break;
                    case R.id.menu_user_detail:
                        Intent intentDetail = new Intent(UserHomeActivity.this, UserDetailActivity.class);
                        intentDetail.putExtra("userBean", userBean);
                        startActivity(intentDetail);

                        break;
                    case R.id.menu_user_report:
                        break;
                    default:
                }
                Toast.makeText(context, "You clicked item " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // setIconEnable(popupMenu.getMenu(), true);

        popupMenu.show();
    }


    private void setIconEnable(Menu menu, boolean enable) {
        try {
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
            Method m = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);
            m.setAccessible(true);
            //传入参数
            m.invoke(menu, enable);
        } catch (Exception e) {
            e.printStackTrace();
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
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
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(UserHomeActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                /**
                 * 获得图片
                 */
                imgAvatar.setImageBitmap(bitmap);

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
                                Toast.makeText(UserHomeActivity.this, "图片上传成功！", Toast.LENGTH_SHORT).show();
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
