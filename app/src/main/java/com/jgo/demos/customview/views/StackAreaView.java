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
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.jgo.demos.R;
import com.jgo.demos.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ke-oh on 2019/08/10.
 *
 */

public class StackAreaView extends View {

    private static final int PADDING_FACTOR = (int) Utils.dip2px(20);
    private static final int PADDING_TEXT = (int) Utils.dip2px(8);

    private String[] mXAxis = new String[]{};
    private List<Category> mCategoryList = new ArrayList<>();

    private Context mContext;

    private int mWidth;
    private int mHeight;
    private int mSectionWidth;
    private Paint mLinePaint;
    private Paint mValuePaint;
    private Paint mCirclePaint;
    private float mProgress;

    private int[] valueColors = new int[]{R.color.dodgerblue, R.color.purple};
    public StackAreaView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public StackAreaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public StackAreaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     *
     */
    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mValuePaint = new Paint();
        mValuePaint.setAntiAlias(true);
        mValuePaint.setStyle(Paint.Style.FILL);
        mValuePaint.setStrokeWidth(5);
        mValuePaint.setTextSize(20);
        mValuePaint.setTextAlign(Paint.Align.CENTER);
        mValuePaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setStrokeWidth(5);

    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mLinePaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));
        canvas.drawLine(PADDING_FACTOR, mHeight - PADDING_FACTOR, mWidth - PADDING_FACTOR, mHeight - PADDING_FACTOR, mLinePaint);
        canvas.drawLine(PADDING_FACTOR, PADDING_FACTOR, PADDING_FACTOR, mHeight - PADDING_FACTOR, mLinePaint);

        mValuePaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));
        for (int i = 0; i < mXAxis.length; i++) {
            canvas.drawText(mXAxis[i], PADDING_FACTOR + mSectionWidth * i, mHeight - PADDING_FACTOR + PADDING_TEXT, mValuePaint);
        }

        int loopNum = 0;
        for (Category category : mCategoryList) {
            //category.datas.get()
            mValuePaint.setColor(ContextCompat.getColor(mContext, valueColors[loopNum % 2]));
            for (int i = 0; i < mXAxis.length - 1; i++) {
                PointXY startPointXY = category.getPoints().get(mXAxis[i]);
                PointXY endPointXY = category.getPoints().get(mXAxis[i + 1]);
                canvas.drawLine(startPointXY.x, startPointXY.y,
                        calculateProgressPointX(startPointXY.x, endPointXY.x, mProgress),
                        calculateProgressPointY(startPointXY.y, endPointXY.y, mProgress), mValuePaint);
            }


            for (String mXAxi : mXAxis) {
                PointXY pointXY = category.getPoints().get(mXAxi);
                mValuePaint.setColor(ContextCompat.getColor(mContext, valueColors[loopNum % 2]));
                canvas.drawCircle(pointXY.x, pointXY.y, 8, mValuePaint);

                mValuePaint.setColor(ContextCompat.getColor(mContext, R.color.white));
                canvas.drawCircle(pointXY.x, pointXY.y, 5, mValuePaint);
            }

            loopNum++;
        }

    }

    public static class Category {

        Map<String, Integer> datas = new HashMap<>();
        Map<String, PointXY> points = new HashMap<>();

        public Category(Map<String, Integer> datas) {
            if (datas != null) {
                this.datas = datas;
            }
        }

        public boolean isValid() {
            return datas.size() > 0;
        }

        public Map<String, PointXY> getPoints() {
            return points;
        }

        public void setPoints(Map<String, PointXY> points) {
            this.points = points;
        }

        public Map<String, Integer> getDatas() {
            return datas;
        }
    }

    public void setXAxis(String[] axis) {
        if (axis == null || axis.length == 0) {
            return;
        }

        mXAxis = axis;
        mSectionWidth = (mWidth - 2 * PADDING_FACTOR) / (mXAxis.length - 1);
    }

    public void addCategory(Category category) {
        if (category != null && category.isValid()) {

            Map<String, PointXY> points = new HashMap<>();

            //Calculate points X Y at view.
            for (int i = 0; i < mXAxis.length; i++) {
                Map<String, Integer> map =  category.getDatas();

                int xAtView = PADDING_FACTOR + mSectionWidth * i;
                int yAtView = mHeight - (PADDING_FACTOR + map.get(mXAxis[i]) * (mHeight - 2 * PADDING_FACTOR) / 1000);
                PointXY pointXY = new PointXY(xAtView, yAtView);
                points.put(mXAxis[i], pointXY);
            }
            category.setPoints(points);
            mCategoryList.add(category);
        }
    }

    public void clearDatas() {
        mCategoryList.clear();
    }

    public void showData() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener((updateValue) -> {
            mProgress = (int)updateValue.getAnimatedValue();

            invalidate();
        });
        animator.setDuration(500);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private class PointXY {
        int x;
        int y;

        PointXY(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private int calculateProgressPointX(int startX, int endX, float progress) {
        return startX + (int)((endX - startX) * progress / 100);
    }

    private int calculateProgressPointY(int startY, int endY, float progress) {
        return startY + (int)((endY - startY) * progress / 100);
    }
}
