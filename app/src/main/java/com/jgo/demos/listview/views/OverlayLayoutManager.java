package com.jgo.demos.listview.views;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ke-oh on 2019/07/20.
 *
 */

public class OverlayLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "OverFlyingLayoutManager";

    private static final float OVERLAY_FACTOR = 0.2f;
    private static final float DIVIDE_FACTOR = 10;

    private int mOverlayOffSet;

    // mTotalHeight = viewHeight * itemCount
    private int mTotalHeight = 0;
    private int mScrollOffset;
    private int mItemViewHeight;

    public OverlayLayoutManager() {

    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State recyclerViewState) {
        super.onLayoutChildren(recycler, recyclerViewState);

        if (getItemCount() == 0 || (getChildCount() == 0 && recyclerViewState.isPreLayout())) {
            return;
        }

        //detach all views
        detachAndScrapAttachedViews(recycler);

        calculateChildren(recycler);
        addAndLayoutView(recycler);
    }

    /**
     *
     *
     * @param recycler
     */
    private void calculateChildren(RecyclerView.Recycler recycler) {
        View view = recycler.getViewForPosition(0);
        measureChildWithMargins(view, 0, 0);
        calculateItemDecorationsForChild(view, new Rect());

        mItemViewHeight = getDecoratedMeasuredHeight(view);
        mOverlayOffSet = 4 * mItemViewHeight;
        mTotalHeight = getItemCount() * mItemViewHeight;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State recyclerViewState) {
        int scrolledDy = dy;
        if (mScrollOffset <= mTotalHeight - getUseDisplayHeight()) {
            mScrollOffset += scrolledDy;
        }

        if (mScrollOffset > mTotalHeight - getUseDisplayHeight()) {
            mScrollOffset = mTotalHeight - getUseDisplayHeight();
            scrolledDy = 0;
        } else if (mScrollOffset < 0) {
            mScrollOffset = 0;
            scrolledDy = 0;
        }
        detachAndScrapAttachedViews(recycler);

        //Add view
        int itemCount = getItemCount();
        if (itemCount > 0 && !recyclerViewState.isPreLayout()) {
            addAndLayoutView(recycler);
        }

        return scrolledDy;
    }

    /**
     * Add view to the recyclerView
     *
     * @param recycler recycler
     */
    private void addAndLayoutView(RecyclerView.Recycler recycler) {
        int displayHeight = getUseDisplayHeight();
        for (int i = getItemCount() - 1; i >= 0; i--) {
            int bottomOffset = (i + 1) * mItemViewHeight - mScrollOffset;
            int topOffset = i * mItemViewHeight - mScrollOffset;
            boolean shouldAddToRecyclerView = true;
            if (bottomOffset - displayHeight >= mOverlayOffSet) {
                shouldAddToRecyclerView = false;
            }
            if (topOffset < -mOverlayOffSet && i != 0 || topOffset < -mOverlayOffSet) {
                shouldAddToRecyclerView = false;
            }
            if (shouldAddToRecyclerView) {
                View view = recycler.getViewForPosition(i);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int width = getDecoratedMeasuredWidth(view);
                int height = getDecoratedMeasuredHeight(view);

                calculateItemDecorationsForChild(view, new Rect());
                int realBottomOffset = bottomOffset;
                int realWidth;

                if (i != getItemCount() - 1) {
                    if (displayHeight - bottomOffset <= height * OVERLAY_FACTOR) {
                        int edgeDist = (int) (displayHeight - height * OVERLAY_FACTOR);
                        int bottom = (int) (edgeDist + (bottomOffset - edgeDist) / DIVIDE_FACTOR);
                        bottom = Math.min(bottom, displayHeight);
                        realBottomOffset = bottom;
                    }
                } else {
                    realBottomOffset = mTotalHeight > displayHeight ? displayHeight : mTotalHeight;
                }

                //Calculate the scale rate as offset at vertical.
                float scaleRate = ((float)bottomOffset - realBottomOffset) / bottomOffset;

                //Set width
                realWidth =  (int)(width * (1 - scaleRate));

                //Set alpha
                view.setAlpha(1 - scaleRate);

                Log.d(TAG, "layout View Left : " + (width - realWidth) / 2 + ", Top : " + (realBottomOffset - height) + ", Right : " + realWidth + ", Bottom : " + realBottomOffset);
                layoutDecoratedWithMargins(view, (width - realWidth) / 2, realBottomOffset - height, (width - realWidth) / 2 + realWidth, realBottomOffset);
            }
        }
    }

    /**
     * The height can use in display.
     *
     * @return height
     */
    private int getUseDisplayHeight() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }
}

