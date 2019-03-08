package com.asche.wetalk.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.asche.wetalk.R;
import com.asche.wetalk.activity.AchievementActivity;
import com.asche.wetalk.activity.AgendaActivity;
import com.asche.wetalk.activity.BaseActivity;
import com.asche.wetalk.activity.BookActivity;
import com.asche.wetalk.activity.ClientActivity;
import com.asche.wetalk.activity.ClockInActivity;
import com.asche.wetalk.activity.IdentifyActivity;
import com.asche.wetalk.activity.VIPCenterActivity;
import com.asche.wetalk.activity.DraftActivity;
import com.asche.wetalk.activity.LoginActivity;
import com.asche.wetalk.activity.MessageMoneyActivity;
import com.asche.wetalk.activity.SettingActivity;
import com.asche.wetalk.activity.UserHomeActivity;
import com.asche.wetalk.activity.WalletActivity;
import com.asche.wetalk.activity.WorkActivity;
import com.asche.wetalk.adapter.UserToolRVAdapter;
import com.asche.wetalk.bean.ImageTextBean;
import com.asche.wetalk.helper.FlexibleScrollView;
import com.asche.wetalk.spider.JokeSpider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

@SuppressWarnings("FieldCanBeLocal")
public class FragmentUser extends Fragment implements View.OnClickListener {

    private FlexibleScrollView flexibleScrollView;

    private LinearLayout userInfoLayout;
    private ImageView imgAvatar;

    private ImageView imgSetting;

    private RecyclerView recyclerView;
    private UserToolRVAdapter userToolRVAdapter;
    private List<ImageTextBean> imageTextBeans = new ArrayList<>();

    private RecyclerView recyclerViewPanel;
    private UserToolRVAdapter userToolRVAdapterPanel;
    private List<ImageTextBean> imageTextBeansPanel = new ArrayList<>();

    // 每日推荐
    private LinearLayout layoutArticle, layoutJoke;

    // 笑话推荐显示View
    private WebView webView;
    private Handler handler = null;


    private final String TAG = "FragmentUser";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_user, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(600);
            getActivity().getWindow().setExitTransition(explode);
        }*/

        flexibleScrollView = getView().findViewById(R.id.scroll_user_frag);
        userInfoLayout = getView().findViewById(R.id.layout_user_info);
        imgAvatar = getView().findViewById(R.id.img_user_avatar);
        imgSetting = getView().findViewById(R.id.img_toolbar_setting);
        recyclerView = getView().findViewById(R.id.recycle_user_tool);
        recyclerViewPanel = getView().findViewById(R.id.recycle_user_panel);
        layoutArticle = getView().findViewById(R.id.layout_user_daily_article);
        layoutJoke = getView().findViewById(R.id.layout_user_daily_joke);

        flexibleScrollView.setEnablePullDown(false);

        imageTextBeans.add(new ImageTextBean(R.drawable.ic_wallet + "", "钱包"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_identify + "", "认证"));
//        imageTextBeans.add(new ImageTextBean(R.drawable.ic_book + "", "书架"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_achievement + "", "成就"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_collect + "", "收藏"));
        imageTextBeans.add(new ImageTextBean(R.drawable.ic_signin + "", "签到"));

        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_draft_color + "", "草稿"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_query_color + "", "咨询"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_answer_color + "", "作品"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_comment_color + "", "会员中心"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_client_color + "", "客户"));
        imageTextBeansPanel.add(new ImageTextBean(R.drawable.ic_agendum_color + "", "待办事项"));


        userToolRVAdapter = new UserToolRVAdapter(imageTextBeans);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(userToolRVAdapter);

        userToolRVAdapterPanel = new UserToolRVAdapter(imageTextBeansPanel);
        recyclerViewPanel.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerViewPanel.setAdapter(userToolRVAdapterPanel);

        imgSetting.setOnClickListener(this);
        userInfoLayout.setOnClickListener(this);
        userToolRVAdapter.setOnItemClickListener(new UserToolRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 4:
                        nextActivity(ClockInActivity.class);
                        Toast.makeText(getActivity(), "签到", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        nextActivity(LoginActivity.class);
//                        nextActivity(CollectActivity.class);
                        Toast.makeText(getActivity(), "收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        nextActivity(AchievementActivity.class);
                        Toast.makeText(getActivity(), "成就", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        nextActivity(IdentifyActivity.class);
                        Toast.makeText(getActivity(), "认证", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        nextActivity(WalletActivity.class);
                        Toast.makeText(getActivity(), "钱包", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        userToolRVAdapterPanel.setOnItemClickListener(new UserToolRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 5:
                        nextActivity(AgendaActivity.class);
                        Toast.makeText(getActivity(), "待办事项", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        nextActivity(ClientActivity.class);
                        Toast.makeText(getActivity(), "客户", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        nextActivity(VIPCenterActivity.class);
                        Toast.makeText(getActivity(), "会员中心", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Intent intentWork = new Intent(getContext(), WorkActivity.class);
                        intentWork.putExtra("userBean", BaseActivity.getCurUser());
                        startActivity(intentWork);
                        Toast.makeText(getActivity(), "作品", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        nextActivity(MessageMoneyActivity.class);
                        Toast.makeText(getActivity(), "咨询", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        nextActivity(DraftActivity.class);
                        Toast.makeText(getActivity(), "草稿", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        layoutArticle.setOnClickListener(this);
        layoutJoke.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_user_daily_joke:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String content = JokeSpider.getJoke();
                        Message message = new Message();
                        message.what = 100;
                        message.obj = content;
                        handler.sendMessage(message);
                    }
                }).start();

                MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                        .title("每日一笑")
                        .customView(R.layout.layout_joke, false)
                        .positiveText("确认")
                        .show();
                webView = (WebView) dialog.findViewById(R.id.web_joke);
                WebSettings webSettings = webView.getSettings();
                webSettings.setSupportZoom(true);
                webSettings.setBuiltInZoomControls(true);
                webSettings.setDisplayZoomControls(false);

                handler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.what == 100) {
                            if (msg.obj != null) {
                                webView.loadData(msg.obj.toString(), "text/html", "utf-8");
                            } else {
                                Toast.makeText(getContext(), "连接失败，请重试！", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return false;
                    }
                });

                break;
            case R.id.layout_user_daily_article:
                break;
            case R.id.layout_user_info:
                Intent intent = new Intent(getContext(), UserHomeActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imgAvatar, "user_avatar");
                    startActivity(intent, compat.toBundle());
                }
                break;
            case R.id.img_toolbar_setting:
                Intent intentSetting = new Intent(getActivity(), SettingActivity.class);
                startActivity(intentSetting);
                break;
        }
    }

    private void nextActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}
