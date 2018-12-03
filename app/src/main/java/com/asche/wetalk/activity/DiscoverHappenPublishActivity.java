package com.asche.wetalk.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.GridImgPublishRVAdapter;
import com.asche.wetalk.adapter.OnItemClickListener;
import com.asche.wetalk.adapter.OnSizeChangedListener;
import com.asche.wetalk.bean.HappenItemBean;
import com.asche.wetalk.fragment.FragmentEmoticon;
import com.asche.wetalk.helper.Glide4Engine;
import com.asche.wetalk.helper.KeyboardHeightObserver;
import com.asche.wetalk.helper.KeyboardHeightProvider;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.util.StringUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.MyApplication.getContext;
import static com.asche.wetalk.activity.ChatActivity.inputMethodManager;
import static com.asche.wetalk.activity.ChatActivity.keyboardHeight;
import static com.asche.wetalk.fragment.FragmentDiscoverHappen.happenItemBeanList;
import static com.asche.wetalk.fragment.FragmentDiscoverHappen.isPublishNewOne;
import static com.asche.wetalk.storage.ChatStorage.storeChatRecord;

public class DiscoverHappenPublishActivity extends BaseActivity implements View.OnClickListener, KeyboardHeightObserver {

    private ImageView imgBack, imgMore;
    private TextView textTitle;
    private EditText editText;

    private RecyclerView recycView;
    private GridLayoutManager layoutManager;
    private GridImgPublishRVAdapter imgPublishRVAdapter;
    private List<String> imgUrlList = new ArrayList<>();

    private ImageView imgEmoticon, imgImg, imgAt;
    private LinearLayout layoutBottom;
    private FrameLayout emoticonLayout;

    private boolean isEmoticonPressed;
    private boolean isInputMethodShow;
    private KeyboardHeightProvider keyboardHeightProvider;

    public static final String TAG = "Publish";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_happen_publish);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.parseColor("#22000000"));
        }

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        imgMore = findViewById(R.id.img_toolbar_more);
        editText = findViewById(R.id.edit_happen);
        recycView = findViewById(R.id.recycler_happen_publish);
        imgEmoticon = findViewById(R.id.img_happen_publish_emoticon);
        imgImg = findViewById(R.id.img_happen_publish_img);
        imgAt = findViewById(R.id.img_happen_publish_at);
        layoutBottom = findViewById(R.id.layout_bottom);
        emoticonLayout = findViewById(R.id.frame_emoticon);

        textTitle.setText("记录点滴");
        imgMore.setBackgroundResource(R.drawable.ic_save);

        imgUrlList.add("http://upload-images.jianshu.io/upload_images/1202579-b291e1de4d4bccd1");
        imgUrlList.add("http://upload-images.jianshu.io/upload_images/1202579-192492ce6870cfb6");
        imgUrlList.add("http://upload-images.jianshu.io/upload_images/13638982-6ae385e9769862b5.png");
        imgUrlList.add("http://upload-images.jianshu.io/upload_images/13638982-3acfa5602653ac1d.png");
        imgUrlList.add("1");

        imgPublishRVAdapter = new GridImgPublishRVAdapter(imgUrlList);
        layoutManager = new GridLayoutManager(getContext(), 3);
        recycView.setLayoutManager(layoutManager);
        recycView.setAdapter(imgPublishRVAdapter);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        imgEmoticon.setOnClickListener(this);
        imgImg.setOnClickListener(this);
        imgAt.setOnClickListener(this);

        imgPublishRVAdapter.setOnSizeChangedListener(new OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int size) {
                if (size == 0) {
                    recycView.setVisibility(View.GONE);
                }
            }
        });

        // 此处的点击事件只绑定footer
        imgPublishRVAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Matisse.from(DiscoverHappenPublishActivity.this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(9)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(2);
            }
        });

        keyboardHeightProvider = new KeyboardHeightProvider(this, R.layout.activity_discover_happen_publish);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                keyboardHeightProvider.start();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_happen_publish_at:
                break;
            case R.id.img_happen_publish_img:
                if (imgUrlList.size() == 9) {
                    Toast.makeText(this, "最多支持9张图片！", Toast.LENGTH_SHORT).show();
                    return;
                }

                Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(9)
//                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(2);

                break;
            case R.id.img_happen_publish_emoticon:
                if (!isEmoticonPressed) {
                    isEmoticonPressed = true;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon_pressed);

                    beginFalling();
                    emoticonRising();

                } else {
                    isEmoticonPressed = false;
                    imgEmoticon.setImageResource(R.drawable.ic_emoticon);

                    emoticonfalling();
                }
                break;
            case R.id.img_toolbar_more:
                Toast.makeText(this, "发表成功！", Toast.LENGTH_SHORT).show();
                String inputStr = editText.getText().toString();

                if (StringUtils.isEmpty(inputStr)){
                    Toast.makeText(this, "内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                happenItemBeanList.add(0, new HappenItemBean(R.drawable.img_avatar+"", "Asche", inputStr, "2018-12-1", imgUrlList.subList(0, imgUrlList.size()-1)));
                isPublishNewOne = true;
                finish();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            List<Uri> list = Matisse.obtainResult(data);
            for (Uri uri : list) {
                if (recycView.getVisibility() == View.GONE) {
                    recycView.setVisibility(View.VISIBLE);
                }
                System.out.println(uri);

                imgUrlList.add(imgUrlList.size()-1, uri.toString());
                imgPublishRVAdapter.notifyItemInserted(imgUrlList.size() - 1);
                if (imgUrlList.size() == 10) {
                    imgUrlList.remove(imgUrlList.size() - 1);
                    Toast.makeText(this, "最多支持9张图片！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

//            uri = list.get(0);

//            File file = new File(getPath(getApplicationContext(), Uri.parse(imgUri)));


    /*        ContentResolver contentResolver = getContentResolver();
            try {
                InputStream inputStream = contentResolver.openInputStream(list.get(0));

                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        }
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        Log.e(TAG, "onKeyboardHeightChanged: " + height );
        if (height > 0) {
            if (isEmoticonPressed) {
                emoticonfalling();
                isEmoticonPressed = false;
                imgEmoticon.setImageResource(R.drawable.ic_emoticon);
            } else {
                beginRising();
            }
        } else {
            if (isEmoticonPressed) {
            } else {
                beginFalling();
            }
        }
    }

    private void beginRising() {
        int height = MyScrollView.dip2px(getApplicationContext(), keyboardHeight);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = height;
        layoutBottom.setLayoutParams(params);
    }

    private void beginFalling() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
        params.bottomMargin = 0;
        layoutBottom.setLayoutParams(params);
    }

    private void emoticonRising() {
        emoticonLayout.setVisibility(View.VISIBLE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FragmentEmoticon fragmentEmoticon = new FragmentEmoticon();
        fragmentEmoticon.setEditText(editText);
        transaction.add(R.id.frame_emoticon, fragmentEmoticon, "emoticon");
        transaction.commit();
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void emoticonfalling() {
        try {
            emoticonLayout.setVisibility(View.GONE);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(getSupportFragmentManager().findFragmentByTag("emoticon"));
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }
}
