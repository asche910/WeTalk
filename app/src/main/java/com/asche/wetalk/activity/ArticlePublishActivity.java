package com.asche.wetalk.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;
import com.asche.wetalk.bean.ArticleBean;
import com.asche.wetalk.bean.DraftItemBean;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.RequirementBean;
import com.asche.wetalk.bean.TopicReplyBean;
import com.asche.wetalk.data.DataUtils;
import com.asche.wetalk.helper.KeyboardHeightObserver;
import com.asche.wetalk.helper.KeyboardHeightProvider;
import com.asche.wetalk.other.MyScrollView;
import com.asche.wetalk.util.StringUtils;
import com.asche.wetalk.util.TimeUtils;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import jp.wasabeef.richeditor.RichEditor;

import static com.asche.wetalk.MyApplication.getContext;
import static com.asche.wetalk.activity.ChatActivity.keyboardHeight;
import static com.asche.wetalk.bean.HomeItem.TYPE_ARTICLE;
import static com.asche.wetalk.bean.HomeItem.TYPE_REQUIREMENT;
import static com.asche.wetalk.bean.HomeItem.TYPE_TOPIC;
import static com.asche.wetalk.fragment.FragmentWorkArticle.workArticleList;
import static com.asche.wetalk.fragment.FragmentWorkRequirement.workRequirementList;
import static com.asche.wetalk.fragment.FragmentWorkTopic.workTopicList;

@SuppressWarnings("DanglingJavadoc")
public class ArticlePublishActivity extends BaseActivity implements KeyboardHeightObserver, View.OnClickListener {

    private ImageView imgBack, imgMore;
    private TextView textTitle;

    private EditText editTitle;
    private RichEditor mEditor;
    private HorizontalScrollView layoutBottom;

    // 插入链接对话框的EditText
    EditText editLink = null, editText = null;


    // 数据对象
    private ArticleBean articleBean;
    private RequirementBean requirementBean;
    private TopicReplyBean topicReplyBean;
    private DraftItemBean draftItemBean;
    private int type = 0; // 文章、需求、话题回复
    boolean isNew = true;

    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_PICK_IMAGE = 11101;

    private KeyboardHeightProvider keyboardHeightProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_publish);

        imgBack = findViewById(R.id.img_toolbar_back);
        textTitle = findViewById(R.id.text_toolbar_title);
        imgMore = findViewById(R.id.img_toolbar_more);
        editTitle = findViewById(R.id.edit_publish_title);
        mEditor = findViewById(R.id.editor);
        layoutBottom = findViewById(R.id.layout_bottom);


        textTitle.setText("编辑文章");
        imgMore.setBackgroundResource(R.drawable.ic_send_color);
        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);

        //初始化编辑高度
        // mEditor.setEditorHeight(200);
        //初始化字体大小
        mEditor.setEditorFontSize(18);
        //初始化字体颜色
        mEditor.setEditorFontColor(Color.parseColor("#444444"));

        //初始化内边距
        mEditor.setPadding(10, 10, 10, 10);

        //设置编辑框背景，可以是网络图片
        // mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        // mEditor.setBackgroundColor(Color.BLUE);
        //mEditor.setBackgroundResource(R.drawable.bg);
        //设置默认显示语句
        mEditor.setPlaceholder("Insert text here...");
        //设置编辑器是否可用
        mEditor.setInputEnabled(true);


        Intent intent = getIntent();
        if (intent != null) {
            // type必传
            type = intent.getIntExtra("type", 0);
            isNew = !intent.hasExtra("object");

            if (type == TYPE_REQUIREMENT) {
                textTitle.setText("编辑需求");
            } else if (type == TYPE_TOPIC) {
                textTitle.setText("话题回复");
            } else if (type == TYPE_ARTICLE) {
                textTitle.setText("编辑文章");
            }

            if (!isNew) {
                // 旧的
                draftItemBean = (DraftItemBean) intent.getSerializableExtra("object");
                if (type != TYPE_TOPIC) {
                    editTitle.setText(draftItemBean.getTitle());
                } else {
                    editTitle.setEnabled(false);
                    editTitle.setText("回答：#" + draftItemBean.getTitle());
                }
                mEditor.setHtml(draftItemBean.getContent());
            }
        }

        keyboardHeightProvider = new KeyboardHeightProvider(this, R.layout.activity_article_publish);
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
            case R.id.img_toolbar_more:
                String title =editTitle.getText().toString();
                String content = mEditor.getHtml();
                if (StringUtils.isEmpty(content) || StringUtils.isEmpty(title)) {
                    Toast.makeText(this, "内容不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (type == TYPE_ARTICLE) {
                    if (articleBean == null) {
                        articleBean = new ArticleBean();
                    }
                    articleBean.setTitle(title);
                    articleBean.setContent(content);
                    articleBean.setBrief(content);
                    articleBean.setAuthorUserName(getCurUser().getUserName());
                    articleBean.setTime(TimeUtils.getCurrentTime());

                    workArticleList.add(new DraftItemBean(TYPE_ARTICLE, articleBean));

                    finish();
                    Intent intent = new Intent(this, ArticleActivity.class);
                    intent.putExtra("article", articleBean);
                    startActivity(intent);
                } else if (type == TYPE_REQUIREMENT) {
                    if (requirementBean == null) {
                        requirementBean = new RequirementBean();
                    }
                    requirementBean.setTitle(title);
                    requirementBean.setContent(content);
                    requirementBean.setBrief(content);
                    requirementBean.setAuthorUserName(getCurUser().getUserName());
                    requirementBean.setTime(TimeUtils.getCurrentTime());

                    workRequirementList.add(new DraftItemBean(TYPE_REQUIREMENT, requirementBean));

                    finish();
                    Intent intent = new Intent(this, ArticleActivity.class);
                    intent.putExtra("requirement", requirementBean);
                    startActivity(intent);
                } else if (type == TYPE_TOPIC) {
                    if (topicReplyBean == null) {
                        topicReplyBean = new TopicReplyBean();
                    }
                    topicReplyBean.setContent(content);
                    topicReplyBean.setAuthorUserName(getCurUser().getUserName());
                    topicReplyBean.setTime(TimeUtils.getCurrentTime());
                    topicReplyBean.setTopicId("444");

                    workTopicList.add(new DraftItemBean(HomeItem.TYPE_TOPIC, topicReplyBean));

                    finish();
                    Intent intent = new Intent(this, TopicActivity.class);
                    intent.putExtra("topicReply", topicReplyBean);
                    startActivity(intent);
                }

                Toast.makeText(this, "发布成功！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_toolbar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //<editor-fold defaultstate="collapsed" desc="Component init">
        /**
         * 撤销当前标签状态下所有内容
         */
        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });
        /**
         * 恢复撤销的内容
         */
        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });
        /**
         * 加粗
         */
        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setBold();
            }
        });
        /**
         * 斜体
         */
        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setItalic();
            }
        });
        /**
         * 下角表
         */
        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                if (mEditor.getHtml() == null) {
                    return;
                }
                mEditor.setSubscript();
            }
        });
        /**
         * 上角标
         */
        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                if (mEditor.getHtml() == null) {
                    return;
                }
                mEditor.setSuperscript();
            }
        });

        /**
         * 删除线
         */
        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setStrikeThrough();
            }
        });
        /**
         *下划线
         */
        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setUnderline();
            }
        });
        /**
         * 设置标题（1到6）
         */
        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });
        /**
         * 设置字体颜色
         */
        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                new MaterialDialog.Builder(ArticlePublishActivity.this)
                        .title("选择字体颜色")
                        .items(R.array.color_items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                                dialog.dismiss();
                                switch (which) {
                                    case 0://红
                                        mEditor.setTextColor(Color.RED);
                                        break;
                                    case 1://黄
                                        mEditor.setTextColor(Color.YELLOW);
                                        break;
                                    case 2://蓝
                                        mEditor.setTextColor(Color.GREEN);
                                        break;
                                    case 3://绿
                                        mEditor.setTextColor(Color.BLUE);
                                        break;
                                    case 4://黑
                                        mEditor.setTextColor(Color.BLACK);
                                        break;
                                }
                                return false;
                            }
                        }).show();
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                new MaterialDialog.Builder(ArticlePublishActivity.this)
                        .title("选择字体背景颜色")
                        .items(R.array.text_back_color_items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                                dialog.dismiss();
                                switch (which) {
                                    case 0://红
                                        mEditor.setTextBackgroundColor(Color.RED);
                                        break;
                                    case 1://黄
                                        mEditor.setTextBackgroundColor(Color.YELLOW);
                                        break;
                                    case 2://蓝
                                        mEditor.setTextBackgroundColor(Color.GREEN);
                                        break;
                                    case 3://绿
                                        mEditor.setTextBackgroundColor(Color.BLUE);
                                        break;
                                    case 4://黑
                                        mEditor.setTextBackgroundColor(Color.BLACK);
                                        break;
                                    case 5://透明
                                        mEditor.setTextBackgroundColor(R.color.transparent);
                                        break;
                                }
                                return false;
                            }
                        }).show();
            }
        });
        /**
         * 向右缩进
         */
        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setIndent();
            }
        });
        /**
         * 向左缩进
         */
        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setOutdent();
            }
        });
        /**
         *文章左对齐
         */
        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.setAlignLeft();
            }
        });
        /**
         * 文章居中对齐
         */
        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });
        /**
         * 文章右对齐
         */
        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });
        /**
         * 无序排列
         */
        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });
        /**
         * 有序排列
         */
        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setNumbers();
            }
        });
        /**
         * 引用
         */
        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        /**
         * 插入图片
         */
        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                ActivityCompat.requestPermissions(ArticlePublishActivity.this, mPermissionList, 100);
            }
        });
        /**
         * 插入连接
         */
        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog inputDialog = new MaterialDialog.Builder(ArticlePublishActivity.this)
                        .title("插入链接")
                        .customView(R.layout.layout_publish_insertlink, false)
                        .negativeText("取消")
                        .positiveText("确认")
                        .autoDismiss(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                String textLink = editLink.getText().toString();
                                if (StringUtils.isEmpty(textLink)) {
                                    Toast.makeText(ArticlePublishActivity.this, "链接地址不能为空！", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String textShow = editText.getText().toString();
                                textShow = StringUtils.isEmpty(textShow) ? textLink : textShow;
                                mEditor.insertLink(textLink, textShow);
                                dialog.dismiss();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                editLink = (EditText) inputDialog.findViewById(R.id.edit_insertlink_link);
                editText = (EditText) inputDialog.findViewById(R.id.edit_insertlink_text);
            }
        });
        /**
         * 选择框
         */
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.focusEditor();
                mEditor.insertTodo();
            }
        });

        /**
         * 显示Html
         */
        findViewById(R.id.tv_showhtml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isEmpty(mEditor.getHtml())) {
                    Toast.makeText(ArticlePublishActivity.this, "再写点内容吧！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(v.getContext(), ArticlePublishPreviewActivity.class);
                intent.putExtra("contextURL", mEditor.getHtml());
                startActivity(intent);
            }
        });
        //</editor-fold>
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要的权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                        String realPathFromUri = DataUtils.getPath(this, data.getData());
                        Log.e("", "onActivityResult: " + realPathFromUri);

                        // 设置alt属性时同时加上最大尺寸
                        mEditor.insertImage(realPathFromUri, realPathFromUri + "\" style=\"max-width:90%");
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        if (layoutBottom == null)
            return;

        if (height > 0) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
            params.bottomMargin = MyScrollView.dip2px(getContext(), keyboardHeight);
            layoutBottom.setLayoutParams(params);

        } else {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutBottom.getLayoutParams();
            params.bottomMargin = 0;
            layoutBottom.setLayoutParams(params);
        }
    }

}
