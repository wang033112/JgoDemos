package com.jgo.demos.customview.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/07/26.
 *
 */

public class ProgressCircleView extends FrameLayout {

    private static final String TAG = ProgressCircleView.class.getSimpleName();

    private static final int GAP_OUTER_INNER = 30;
    private static final int MARGIN_OUTER_CIRCLE = 6;
    private int mOutCircleRadius;
    private Context mContext;
    private Paint mPrimaryColorPaint;
    private Paint mGapPaint;
    private RectF mArcRect;

    private Canvas mInnerCanvas;
    private Bitmap mInnerBitmap;

    public ProgressCircleView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ProgressCircleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ProgressCircleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        mOutCircleRadius = Math.min(measuredWidth, measureHeight) / 2;

        float left = 0.0f;
        float right = measuredWidth;
        float top = 0.0f;
        float bottom = measureHeight;
        if (measuredWidth > measureHeight) {
            left = measuredWidth / 2 - mOutCircleRadius;
            right = measuredWidth - (measuredWidth - measureHeight) / 2;
        } else {
            top = measureHeight / 2 - mOutCircleRadius;
            bottom = measureHeight - (measureHeight - measuredWidth) / 2;
        }
        mArcRect = new RectF(
                left + MARGIN_OUTER_CIRCLE + GAP_OUTER_INNER / 2,
                top + MARGIN_OUTER_CIRCLE + GAP_OUTER_INNER / 2,
                right - MARGIN_OUTER_CIRCLE - GAP_OUTER_INNER / 2,
                bottom - MARGIN_OUTER_CIRCLE - GAP_OUTER_INNER / 2
        );

        mInnerBitmap = Bitmap.createBitmap(mOutCircleRadius / 2, mOutCircleRadius / 2 ,Bitmap.Config.ARGB_8888);
        mInnerCanvas = new Canvas(mInnerBitmap);
    }

    private void init() {
        mPrimaryColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPrimaryColorPaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));
        mPrimaryColorPaint.setStrokeWidth(3);
        mPrimaryColorPaint.setStyle(Paint.Style.STROKE);

        mGapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGapPaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mGapPaint.setStrokeWidth(GAP_OUTER_INNER);
        mGapPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mOutCircleRadius - MARGIN_OUTER_CIRCLE, mPrimaryColorPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mOutCircleRadius - MARGIN_OUTER_CIRCLE - GAP_OUTER_INNER, mPrimaryColorPaint);

        canvas.drawArc(mArcRect, -90.0f, 90.0f, false, mGapPaint);
        
    }

}
