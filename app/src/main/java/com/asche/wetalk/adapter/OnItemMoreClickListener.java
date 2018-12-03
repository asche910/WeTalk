package com.asche.wetalk.adapter;

/**
 * 回调接口，用于比OnItemClickListener更复杂的情况
 */
public interface OnItemMoreClickListener {
    /**
     *
     * @param position
     * @param args 依具体Adapter而定
     */
    void onItemMoreClick(int position, int args);
}
