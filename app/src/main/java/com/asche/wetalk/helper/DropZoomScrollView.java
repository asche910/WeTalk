package com.asche.wetalk.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.asche.wetalk.R;
import com.asche.wetalk.other.MyScrollView;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;
import static com.asche.wetalk.activity.UserHomeActivity.textTitle;
import static com.asche.wetalk.other.MyScrollView.dip2px;


public class DropZoomScrollView extends ScrollView implements View.OnTouchListener {

    // 记录首次按下位置
    private float mFirstPosition = 0;
    // 是否正在放大
    private Boolean mScaling = false;

    private View dropZoomView;
    private int dropZoomViewWidth;
    private int dropZoomViewHeight;

    private View mTitleView;
    private AbsListView.OnScrollListener mListener;


    public DropZoomScrollView(Context context) {
        super(context);
    }

    public DropZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DropZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置缩放的背景图片
     *
     * @param view
     */
    public void setDropView(View view) {
        dropZoomView = view;
    }

    public void setListener(AbsListView.OnScrollListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置toolbar颜色渐变
     *
     * @param view
     */
    public void setupTitleView(View view) {
        this.mTitleView = view;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        setOnTouchListener(this);
   /*     if (getChildAt(0) != null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildAt(0) != null) {
                dropZoomView = vg.getChildAt(0);
                setOnTouchListener(this);

            }
        }*/
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        int px = dip2px(getContext(), 200 - 74);

        Log.e(TAG, "onOverScrolled: " + scrollY + "- -" + clampedY );
        if (scrollY >= px) {
            textTitle.setVisibility(View.VISIBLE);
        } else {
            textTitle.setVisibility(View.INVISIBLE);
        }

        if (scrollY >= px) {
            mTitleView.setBackgroundColor(getResources().getColor(R.color.blueLight));

        } else if (scrollY >= 0) {
            float percent = scrollY * 1f / ((float) px);
            int alpha = (int) (255 * percent);
            int color = Color.argb(alpha, 18, 149, 255);
            mTitleView.setBackgroundColor(color);
        }

        if (mListener != null) {
            mListener.onScroll(null, scrollX, scrollY, scrollY);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (dropZoomViewWidth <= 0 || dropZoomViewHeight <= 0) {
            dropZoomViewWidth = dropZoomView.getMeasuredWidth();
            dropZoomViewHeight = dropZoomView.getMeasuredHeight();
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //手指离开后恢复图片
                mScaling = false;
                replyImage();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScaling) {
                    if (getScrollY() == 0) {
                        mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
                    } else {
                        break;
                    }
                }
                int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
                if (distance < 0) { // 当前位置比记录位置要小，正常返回
                    break;
                }

                // 处理放大
                mScaling = true;
                setZoom(1 + distance);
                return true; // 返回true表示已经完成触摸事件，不再处理
        }
        return false;
    }

    // 回弹动画 (使用了属性动画)
    public void replyImage() {
        final float distance = dropZoomView.getMeasuredWidth() - dropZoomViewWidth;

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration((long) (distance * 0.7));

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                setZoom(distance - ((distance) * cVal));
            }
        });
        anim.start();
    }

    //缩放
    public void setZoom(float s) {
        if (dropZoomViewHeight <= 0 || dropZoomViewWidth <= 0) {
            return;
        }
        ViewGroup.LayoutParams params = dropZoomView.getLayoutParams();
        params.width = (int) (dropZoomViewWidth + s);
        params.height = (int) (dropZoomViewHeight * ((dropZoomViewWidth + s) / dropZoomViewWidth));
        ((MarginLayoutParams) params).setMargins(-(params.width - dropZoomViewWidth) / 2, 0, -(params.width - dropZoomViewWidth) / 2, dip2px(getContext(), 50));
        dropZoomView.setLayoutParams(params);
    }
}