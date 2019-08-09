package com.jgo.demos.customview.views;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.jgo.demos.R;
import com.jgo.demos.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke-oh on 2019/08/08.
 *
 */

public class RadarView extends View {

    private static final int PADDING_FACTOR = (int) Utils.dip2px(5);
    private static final int DATA_CIRCLE_OUTER_RADIUS = (int) Utils.dip2px(3);
    private static final int DATA_CIRCLE_INNER_RADIUS = (int) Utils.dip2px(2);
    private static final int LEVEL_CNT = 5;
    private static final int SECTION_TOP          = 0;
    private static final int SECTION_LEFT_TOP     = 1;
    private static final int SECTION_LEFT_BOTTOM  = 2;
    private static final int SECTION_BOTTOM       = 3;
    private static final int SECTION_RIGHT_BOTTOM = 4;
    private static final int SECTION_RIGHT_TOP    = 5;

    private Context mContext;
    private Path mBackPath;
    private Paint mBackLinePaint;
    private Paint mValuePaint;
    private Paint mCirclePaint;

    private int mCenterX;
    private int mCenterY;

    private int mTopX;
    private int mTopY;

    private int mLeftTopX;
    private int mLeftTopY;

    private int mLeftBottomX;
    private int mLeftBottomY;

    private int mBottomX;
    private int mBottomY;

    private int mRightBottomX;
    private int mRightBottomY;

    private int mRightTopX;
    private int mRightTopY;

    private List<List<RadarPoint>> mRadarPointsList = new ArrayList<>();
    private List<List<RadarPoint>> mDataPointsList = new ArrayList<>();
    private List<RadarData> mRadarDataList = new ArrayList<>();
    private int[] valueColors = new int[]{R.color.dodgerblue, R.color.purple};

    public RadarView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     *
     */
    private void init() {
        mBackLinePaint = new Paint();
        mBackLinePaint.setAntiAlias(true);
        mBackLinePaint.setStyle(Paint.Style.STROKE);
        mBackLinePaint.setColor(ContextCompat.getColor(mContext, R.color.dark_gray));

        mValuePaint = new Paint();
        mValuePaint.setAntiAlias(true);
        mValuePaint.setStyle(Paint.Style.STROKE);
        mValuePaint.setStrokeWidth(5);
        mValuePaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setStrokeWidth(5);

        mBackPath = new Path();

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width, height) - 2 * PADDING_FACTOR;
        int triHeight = (int)(1.732 * size / 4);

        mCenterX = width / 2;
        mCenterY = height / 2;

        mTopX = mCenterX;
        mTopY = mCenterY - size / 2;
        mRadarPointsList.add(calculateFivePartXY(mCenterX, mCenterY, mTopX, mTopY));

        mLeftTopX = mCenterX - triHeight;
        mLeftTopY = mCenterY - size / 4;
        mRadarPointsList.add(calculateFivePartXY(mCenterX, mCenterY, mLeftTopX, mLeftTopY));

        mLeftBottomX = mCenterX - triHeight;
        mLeftBottomY = mCenterY + size / 4;
        mRadarPointsList.add(calculateFivePartXY(mCenterX, mCenterY, mLeftBottomX, mLeftBottomY));

        mBottomX = mCenterX;
        mBottomY = mCenterY + size / 2;
        mRadarPointsList.add(calculateFivePartXY(mCenterX, mCenterY, mBottomX, mBottomY));

        mRightBottomX = mCenterX + triHeight;
        mRightBottomY = mCenterY + size / 4;
        mRadarPointsList.add(calculateFivePartXY(mCenterX, mCenterY, mRightBottomX, mRightBottomY));

        mRightTopX = mCenterX + triHeight;
        mRightTopY = mCenterY - size / 4;
        mRadarPointsList.add(calculateFivePartXY(mCenterX, mCenterY, mRightTopX, mRightTopY));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRadarPointsList.size() <= 0) {
            return;
        }

        for (int level = 0; level < LEVEL_CNT; level++) {
            mBackPath.reset();
            mBackPath.moveTo(mRadarPointsList.get(SECTION_TOP).get(level).x, mRadarPointsList.get(SECTION_TOP).get(level).y);
            mBackPath.lineTo(mRadarPointsList.get(SECTION_LEFT_TOP).get(level).x, mRadarPointsList.get(SECTION_LEFT_TOP).get(level).y);
            mBackPath.lineTo(mRadarPointsList.get(SECTION_LEFT_BOTTOM).get(level).x, mRadarPointsList.get(SECTION_LEFT_BOTTOM).get(level).y);
            mBackPath.lineTo(mRadarPointsList.get(SECTION_BOTTOM).get(level).x, mRadarPointsList.get(SECTION_BOTTOM).get(level).y);
            mBackPath.lineTo(mRadarPointsList.get(SECTION_RIGHT_BOTTOM).get(level).x, mRadarPointsList.get(SECTION_RIGHT_BOTTOM).get(level).y);
            mBackPath.lineTo(mRadarPointsList.get(SECTION_RIGHT_TOP).get(level).x, mRadarPointsList.get(SECTION_RIGHT_TOP).get(level).y);
            mBackPath.lineTo(mRadarPointsList.get(SECTION_TOP).get(level).x, mRadarPointsList.get(SECTION_TOP).get(level).y);
            canvas.drawPath(mBackPath, mBackLinePaint);

            if (level == LEVEL_CNT - 1) {
                canvas.drawLine(mCenterX, mCenterY, mTopX, mTopY, mBackLinePaint);
                canvas.drawLine(mCenterX, mCenterY, mLeftTopX, mLeftTopY, mBackLinePaint);
                canvas.drawLine(mCenterX, mCenterY, mLeftBottomX, mLeftBottomY, mBackLinePaint);
                canvas.drawLine(mCenterX, mCenterY, mBottomX, mBottomY, mBackLinePaint);
                canvas.drawLine(mCenterX, mCenterY, mRightBottomX, mRightBottomY, mBackLinePaint);
                canvas.drawLine(mCenterX, mCenterY, mRightTopX, mRightTopY, mBackLinePaint);
            }
        }

        if (mRadarDataList == null || mRadarDataList.size() == 0) {
            return;
        }

        int loopNum = 0;
        for (List<RadarPoint> points : mDataPointsList) {
            mBackPath.reset();
            mValuePaint.setColor(ContextCompat.getColor(mContext, valueColors[loopNum]));
            mBackPath.moveTo(points.get(SECTION_TOP).x, points.get(SECTION_TOP).y);
            mBackPath.lineTo(points.get(SECTION_LEFT_TOP).x, points.get(SECTION_LEFT_TOP).y);
            mBackPath.lineTo(points.get(SECTION_LEFT_BOTTOM).x, points.get(SECTION_LEFT_BOTTOM).y);
            mBackPath.lineTo(points.get(SECTION_BOTTOM).x, points.get(SECTION_BOTTOM).y);
            mBackPath.lineTo(points.get(SECTION_RIGHT_BOTTOM).x, points.get(SECTION_RIGHT_BOTTOM).y);
            mBackPath.lineTo(points.get(SECTION_RIGHT_TOP).x, points.get(SECTION_RIGHT_TOP).y);
            mBackPath.lineTo(points.get(SECTION_TOP).x, points.get(SECTION_TOP).y);
            canvas.drawPath(mBackPath, mValuePaint);

            drawValueCircle(canvas, points, DATA_CIRCLE_OUTER_RADIUS, valueColors[loopNum]);
            drawValueCircle(canvas, points, DATA_CIRCLE_INNER_RADIUS, R.color.white);

            loopNum++;
        }

    }

    private void drawValueCircle(Canvas canvas, List<RadarPoint> points, int radius, int color) {
        mCirclePaint.setColor(ContextCompat.getColor(mContext, color));
        canvas.drawCircle(points.get(SECTION_TOP).x, points.get(SECTION_TOP).y, radius, mCirclePaint);
        canvas.drawCircle(points.get(SECTION_LEFT_TOP).x, points.get(SECTION_LEFT_TOP).y, radius, mCirclePaint);
        canvas.drawCircle(points.get(SECTION_LEFT_BOTTOM).x, points.get(SECTION_LEFT_BOTTOM).y, radius, mCirclePaint);
        canvas.drawCircle(points.get(SECTION_BOTTOM).x, points.get(SECTION_BOTTOM).y, radius, mCirclePaint);
        canvas.drawCircle(points.get(SECTION_RIGHT_BOTTOM).x, points.get(SECTION_RIGHT_BOTTOM).y, radius, mCirclePaint);
        canvas.drawCircle(points.get(SECTION_RIGHT_TOP).x, points.get(SECTION_RIGHT_TOP).y, radius, mCirclePaint);
    }

    private List<RadarPoint> calculateFivePartXY(int fromX, int fromY, int toX, int toY) {
        List<RadarPoint> radarPoints = new ArrayList<>();
        for (int i = 1; i <= LEVEL_CNT; i++) {
            radarPoints.add(calculatePoint(fromX, fromY, toX, toY, (float) i / LEVEL_CNT));
        }
        return radarPoints;
    }

    private RadarPoint calculatePoint(int fromX, int fromY, int toX, int toY, float percent) {
        float x;
        float y;
        if (toX > fromX) {
            x = fromX + (toX - fromX) * percent;
        } else {
            x = fromX - (fromX - toX) * percent;
        }

        if (toY > fromY) {
            y = fromY + (toY - fromY) * percent;
        } else {
            y = fromY - (fromY - toY) * percent;
        }

        return new RadarPoint((int)x, (int)y);

    }

    @UiThread
    public void setRadarData(List<RadarData> radarDataList) {
        if (radarDataList == null || radarDataList.size() == 0) {
            return;
        }
        mRadarDataList.clear();
        mDataPointsList.clear();
        mRadarDataList.addAll(radarDataList);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.addUpdateListener((updateValue) -> {
            float progress = (float) updateValue.getAnimatedValue();

            mDataPointsList.clear();
            for (RadarData radarData : mRadarDataList) {
                List<RadarPoint> radarPoints = new ArrayList<>();
                radarPoints.add(SECTION_TOP, calculatePoint(mCenterX, mCenterY, mTopX, mTopY, radarData.topValue * progress));
                radarPoints.add(SECTION_LEFT_TOP, calculatePoint(mCenterX, mCenterY, mLeftTopX, mLeftTopY, radarData.leftTopValue * progress));
                radarPoints.add(SECTION_LEFT_BOTTOM, calculatePoint(mCenterX, mCenterY, mLeftBottomX, mLeftBottomY, radarData.leftBottomValue * progress));
                radarPoints.add(SECTION_BOTTOM, calculatePoint(mCenterX, mCenterY, mBottomX, mBottomY, radarData.bottomValue * progress));
                radarPoints.add(SECTION_RIGHT_BOTTOM, calculatePoint(mCenterX, mCenterY, mRightBottomX, mRightBottomY, radarData.rightBottomValue * progress));
                radarPoints.add(SECTION_RIGHT_TOP, calculatePoint(mCenterX, mCenterY, mRightTopX, mRightTopY, radarData.rightTopValue * progress));

                mDataPointsList.add(radarPoints);
            }
            invalidate();
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    private class RadarPoint {
        int x;
        int y;

        RadarPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class RadarData {
        float topValue;
        float leftTopValue;
        float leftBottomValue;
        float bottomValue;
        float rightBottomValue;
        float rightTopValue;

        public RadarData(float topValue, float leftTopValue, float leftBottomValue,
                         float bottomValue, float rightBottomValue, float rightTopValue) {
            this.topValue = topValue;
            this.leftTopValue = leftTopValue;
            this.leftBottomValue = leftBottomValue;
            this.bottomValue = bottomValue;
            this.rightBottomValue = rightBottomValue;
            this.rightTopValue = rightTopValue;
        }
    }
}
