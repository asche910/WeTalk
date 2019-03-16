package com.asche.wetalk.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.DiscoverHappenPublishActivity;
import com.asche.wetalk.adapter.HappenItemRVAdapter;
import com.asche.wetalk.bean.CommentItemBean;
import com.asche.wetalk.bean.HappenItemBean;
import com.asche.wetalk.data.DataUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

import static com.asche.wetalk.activity.ArticleActivity.commentSimpleList;
import static com.asche.wetalk.fragment.FragmentDialogComment.commentNormalList;
import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class FragmentDiscoverHappen extends Fragment {

    private WaveSwipeRefreshLayout swipeRefreshLayout;

    public static List<HappenItemBean> happenItemBeanList = new ArrayList<>();
    private RecyclerView recycHappen;
    private LinearLayoutManager layoutManager;
    private HappenItemRVAdapter happenItemRVAdapter;

    private FloatingActionButton btnPublish;

    // 图片url的List
    private List<String> urlList;

    // 是否正在加载更多数据
    private boolean isLoading;

    // 判断用户是否发表了新动态
    public static boolean isPublishNewOne;

    public static String str_1 = "早起的鸟儿有虫吃， 首先做到23：50前躺下，6：30早起打卡！充满元气的一天！！！";
    public static String str_2 = "2016年10月28日 - 这篇文章主要为大家详细介绍了Android RefreshLayout实现下拉刷新布局,具有一定的参考价值,感兴趣的小伙伴们可以参考一下项目中需要下拉刷新的功能,但...";

    // 初始化评论数据
    static {
        if (commentSimpleList.isEmpty()) {
            commentSimpleList.add(DataUtils.getComment(1));
            commentSimpleList.add(DataUtils.getComment(1));
        }

        if (commentNormalList == null) {
            commentNormalList = new ArrayList<>();
        }

        if (commentNormalList.isEmpty()) {
            for (int i = 0; i < 8; i++) {
                CommentItemBean bean = DataUtils.getComment(0);
                if (i == 4)
                    bean.setSubList(commentSimpleList);
                commentNormalList.add(bean);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_discover_happen, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recycHappen = getView().findViewById(R.id.recycler_happen);
        swipeRefreshLayout = getView().findViewById(R.id.header_discover_happen);
        btnPublish = getView().findViewById(R.id.btn_happen_publish);

        if (happenItemBeanList.isEmpty()){
            urlList = new ArrayList<>();
            urlList.add("http://upload-images.jianshu.io/upload_images/1202579-b291e1de4d4bccd1");
            urlList.add("http://upload-images.jianshu.io/upload_images/1202579-192492ce6870cfb6");
            urlList.add("http://upload-images.jianshu.io/upload_images/13638982-6ae385e9769862b5.png");
            urlList.add("http://upload-images.jianshu.io/upload_images/13638982-3acfa5602653ac1d.png");

            urlList.add("http://upload-images.jianshu.io/upload_images/1202579-b291e1de4d4bccd1");
            urlList.add("http://upload-images.jianshu.io/upload_images/1202579-192492ce6870cfb6");
            urlList.add("http://upload-images.jianshu.io/upload_images/13638982-6ae385e9769862b5.png");
            urlList.add("http://upload-images.jianshu.io/upload_images/13638982-3acfa5602653ac1d.png");

            happenItemBeanList.add(new HappenItemBean("https://cdn2.jianshu.io/assets/default_avatar/7-0993d41a595d6ab6ef17b19496eb2f21.jpg", "Donna裹love橙", str_1, "上午10：24", urlList));
            happenItemBeanList.add(new HappenItemBean("https://cdn2.jianshu.io/assets/default_avatar/3-9a2bcc21a5d89e21dafc73b39dc5f582.jpg", "飞翔的企鹅", str_2, "10-25 10：24", null));
            happenItemBeanList.add(new HappenItemBean(R.drawable.img_avatar+"", "Asche", str_1, "下午10：24", urlList));
            happenItemBeanList.add(new HappenItemBean(HappenItemRVAdapter.TYPE_LOADING));
        }

        happenItemRVAdapter = new HappenItemRVAdapter(happenItemBeanList);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recycHappen.setLayoutManager(layoutManager);
        recycHappen.setAdapter(happenItemRVAdapter);


        swipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }).start();
            }
        });

        recycHappen.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int last = layoutManager.findLastVisibleItemPosition() - 1;

                // 滑动至底部
                if (last + 2 == happenItemBeanList.size() && !isLoading){
                    loadMore();
                    isLoading = true;
                }

            }
        });

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Publish clicked!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), DiscoverHappenPublishActivity.class));
            }
        });
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 10){
                happenItemRVAdapter.notifyItemRangeInserted(happenItemBeanList.size() - 3, 2);
                isLoading = false;
            }
            return false;
        }
    });

    private void loadMore(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                happenItemBeanList.add(happenItemBeanList.size() - 1, new HappenItemBean("https://cdn2.jianshu.io/assets/default_avatar/7-0993d41a595d6ab6ef17b19496eb2f21.jpg", "Donna裹love橙", str_1, "上午10：24", urlList));
                happenItemBeanList.add(happenItemBeanList.size() - 1, new HappenItemBean("https://cdn2.jianshu.io/assets/default_avatar/3-9a2bcc21a5d89e21dafc73b39dc5f582.jpg", "飞翔的企鹅", str_2, "10-25 10：24", null));

                Message message = new Message();
                message.what = 10;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: -------->" );
        happenItemRVAdapter.notifyDataSetChanged();
    /*    if (isPublishNewOne) {
            happenItemRVAdapter.notifyItemInserted(0);
            recycHappen.scrollToPosition(0);
            isPublishNewOne = false;
        }*/
    }

}
