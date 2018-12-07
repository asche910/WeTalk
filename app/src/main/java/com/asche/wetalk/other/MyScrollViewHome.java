package com.asche.wetalk.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.asche.wetalk.activity.BaseActivity;
import com.asche.wetalk.storage.BaseStorage;

import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.fragment.FragmentHome.tabLayout;
import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

@SuppressLint("NewApi")
public class MyScrollViewHome extends ScrollView {

    private int downY;
    private int touchSlop; // 32px / 4 = 8dp

    private boolean isExpanded = true;
    private boolean isScrolling;

    public MyScrollViewHome(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

    }

    public MyScrollViewHome(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollViewHome(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
       /* switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                // TODO -------> 类似Google PlayStore的滑动效果（上滑隐藏， 下滑出现）
                int moveY = (int) ev.getRawY();
                int deltaY = downY - moveY;
                Log.e(TAG, "onInterceptTouchEvent: " + moveY );

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabLayout.getLayoutParams();
                int standTop = MyScrollView.dip2px(getContext(), 36);
                int standBottom = MyScrollView.dip2px(getContext(), 84);

                // 大于0表示手指上滑
                if (deltaY > touchSlop){

                    if (isExpanded) {
                        if (deltaY > standTop) {
                            isExpanded = false;
//                            return true;
                        }
                    }

                    if (params.topMargin > MyScrollView.dip2px(getContext(), 36)) {
                 *//*       Animation animation = new TranslateAnimation(0, 0, standBottom, standTop);
                        animation.setDuration(1500);
                        animation.setFillEnabled(true);
                        animation.setFillAfter(true);
                        tabLayout.setAnimation(animation);
                        animation.startNow();*//*

//                        return true;
                    }
                }else if (deltaY < -touchSlop){
                    // 下滑

                    if (!isExpanded){
                        if (deltaY < -standTop){
                            isExpanded = true;
//                            return true;
                        }
                    }
                  *//*  if (params.topMargin <= MyScrollView.dip2px(getContext(), 36)) {
                        while (params.topMargin <= standBottom){
                            params.topMargin++;
                            tabLayout.setLayoutParams(params);
                        }
                    }*//*
                }
        }
        return super.onInterceptTouchEvent(ev);*/
    }

    /**
     *  scrollY是相对ScrollView的高度
     */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        // scrollY 返回0 ?
        Log.e(TAG, "onOverScrolled: " + scrollY );
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        Log.e(TAG, "onScrollChanged: " + t + oldt );
    }
}
