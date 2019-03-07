package com.asche.wetalk.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewConfiguration;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ScrollView;

import com.asche.wetalk.MyApplication;
import com.asche.wetalk.R;

import static com.asche.wetalk.activity.UserHomeActivity.textTitle;

@SuppressLint("NewApi")
public class MyScrollView extends ScrollView {

    private View mByWhichView;
    private View mTitleView;
    private boolean shouldSlowlyChange = true;
    private OnScrollListener mListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(OnScrollListener listener) {
        this.mListener = listener;
    }

    public void setShouldSlowlyChange(boolean slowlyChange) {
        this.shouldSlowlyChange = slowlyChange;
    }

    public void setupTitleView(View view) {
        this.mTitleView = view;
    }


    public void setupByWhichView(View view) {
        mByWhichView = view;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

        int px = dip2px(getContext(), 200 - 74);

        if (scrollY >= px) {
            textTitle.setVisibility(View.VISIBLE);
        } else {
            textTitle.setVisibility(View.INVISIBLE);
        }

        if (scrollY >= px) {
            mTitleView.setBackgroundColor(getResources().getColor(R.color.blueLight));

        } else if (scrollY >= 0) {
            if (!shouldSlowlyChange) {
                mTitleView.setBackgroundColor(Color.TRANSPARENT);
            } else {
                float percent = scrollY * 1f / ((float) px);
                int alpha = (int) (255 * percent);
                int color = Color.argb(alpha, 18, 149, 255);
                mTitleView.setBackgroundColor(color);
            }
        }

        if (mListener != null) {
            mListener.onScroll(null, scrollX, scrollY, scrollY);
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int dip2px( float dipValue) {
        final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}