package com.asche.wetalk.other;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 为RecyclerView获取滑动事件
 */
public class MyRecyclerView extends RecyclerView {

    // 按下的Y坐标
    private int downY;
    // 判断是否滑动的临界值
    private int touchSlop;

    public MyRecyclerView(Context context) {
        this(context, null);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downY = (int)ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaY = downY - (int)ev.getY();
                if (Math.abs(deltaY) > touchSlop && getTop() > 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
