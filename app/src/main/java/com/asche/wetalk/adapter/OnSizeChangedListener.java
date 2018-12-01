package com.asche.wetalk.adapter;

/**
 * list大小变化的接口，目的在于当list为空时的回调
 */
public interface OnSizeChangedListener {
    // 参数为list的size
    void onSizeChanged(int size);
}
