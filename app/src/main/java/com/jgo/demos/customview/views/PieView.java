package com.jgo.demos.customview.views;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.jgo.demos.R;
import com.jgo.demos.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/08/11.
 *
 */

public class PieView extends View {

    private static final int PADDING_FACTOR = (int) Utils.dip2px(20);
    private Context mContext;

    private int mWidth;
    private int mHeight;
    private int mRadius;
    private Paint mCirclePaint;
    private int mCircleSweepAngle;

    private RectF mCircleRecF;
    private List<PieFruit> mData = new ArrayList<>();

    private int[] valueColors = new int[]{R.color.dodgerblue, R.color.purple,
            R.color.cornflower_blue, R.color.chocolate, R.color.gold};
    public PieView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     *
     */
    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mRadius = Math.min(mWidth, mHeight) / 2 - PADDING_FACTOR;
        mCircleRecF = new RectF(mWidth / 2 - mRadius, mHeight / 2 - mRadius,
                mWidth / 2 + mRadius, mHeight / 2 + mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mData != null && mData.size() > 0) {
            mCirclePaint.setStyle(Paint.Style.FILL);

            int startArc = 0;
            int loopNum = 0;
            int totalAngle = 0;
            for (PieFruit pieFruit : mData) {
                mCirclePaint.setColor(ContextCompat.getColor(mContext, valueColors[loopNum]));
                int totalSweepAngle = (int) ((float)pieFruit.percent * 360 / 100);
                int drawSweepAngle = 0;
                boolean shouldBreak = false;
                if (startArc + totalSweepAngle > mCircleSweepAngle) {
                    drawSweepAngle = totalSweepAngle - (startArc + totalSweepAngle - mCircleSweepAngle);
                    shouldBreak = true;
                } else {
                    drawSweepAngle = totalSweepAngle;
                }

                //Fix sweepangle
                if (loopNum == mData.size() - 1) {
                    drawSweepAngle = 360 - totalAngle;
                } else {
                    totalAngle += drawSweepAngle;
                }

                canvas.drawArc(getAnimatorRectF(startArc, totalSweepAngle), startArc,
                        drawSweepAngle, true, mCirclePaint);
                startArc += drawSweepAngle;
                loopNum++;

                if (shouldBreak) break;
            }
        } else {
            mCirclePaint.setColor(ContextCompat.getColor(mContext, valueColors[0]));
            mCirclePaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mCirclePaint);
        }

    }

    private RectF getAnimatorRectF(int startArc, int sweepAngle) {
        float factorRadius = 0;
        if (sweepAngle == 0) {
            return mCircleRecF;
        }

        float sectionProgress =  ((float)mCircleSweepAngle - startArc) / sweepAngle;
        if (sectionProgress > 1) {
            return mCircleRecF;
        }

        if (sectionProgress <= 0.5) {
            factorRadius =  mRadius * (1.0f + 0.2f *  (sectionProgress * 2));
        } else {
            factorRadius =  mRadius * (1.0f + 0.2f * ((1.0f - sectionProgress) * 2));
        }
        return new RectF(mWidth / 2 - factorRadius, mHeight / 2 - factorRadius,
                mWidth / 2 + factorRadius, mHeight / 2 + factorRadius);
    }

    public void setData(List<PieFruit> dataMap) {
        if (dataMap == null || dataMap.size() == 0) {
            return;
        }

        mData.clear();
        mData.addAll(dataMap);
        startShowData();
    }

    private void startShowData() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 360);
        animator.addUpdateListener((updateValue) -> {
            mCircleSweepAngle = (int) updateValue.getAnimatedValue();
            invalidate();
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    public static class PieFruit {

        String label;
        int percent;

        public PieFruit(String label, int percent) {
            this.label = label;
            this.percent = percent;
        }
    }
}
