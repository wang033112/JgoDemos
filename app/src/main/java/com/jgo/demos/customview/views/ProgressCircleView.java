package com.jgo.demos.customview.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
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
    private static final int PAINT_STROKE_WIDTH = 3;
    private static final int TEXT_SIZE = 80;

    private int mMeasuredWidth;
    private int mMeasureHeight;
    private int mOutCircleRadius;
    private Context mContext;
    private Paint mPrimaryColorPaint;
    private Paint mGapPaint;
    private RectF mArcRect;

    private Canvas mInnerCanvas;
    private Bitmap mInnerBitmap;
    private Paint mInnerCanvasCirclePaint;
    private Paint mInnerCanvasTextPaint;
    private float mInnerTextCenter;
    private int mProgressValue = 0;

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
        mMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);

        mOutCircleRadius = Math.min(mMeasuredWidth, mMeasureHeight) / 2;

        float left = 0.0f;
        float right = mMeasuredWidth;
        float top = 0.0f;
        float bottom = mMeasureHeight;
        if (mMeasuredWidth > mMeasureHeight) {
            left = mMeasuredWidth / 2 - mOutCircleRadius;
            right = mMeasuredWidth - (mMeasuredWidth - mMeasureHeight) / 2;
        } else {
            top = mMeasureHeight / 2 - mOutCircleRadius;
            bottom = mMeasureHeight - (mMeasureHeight - mMeasuredWidth) / 2;
        }
        mArcRect = new RectF(
                left + MARGIN_OUTER_CIRCLE + GAP_OUTER_INNER / 2,
                top + MARGIN_OUTER_CIRCLE + GAP_OUTER_INNER / 2,
                right - MARGIN_OUTER_CIRCLE - GAP_OUTER_INNER / 2,
                bottom - MARGIN_OUTER_CIRCLE - GAP_OUTER_INNER / 2
        );

        mInnerBitmap = Bitmap.createBitmap(mOutCircleRadius, mOutCircleRadius, Bitmap.Config.ARGB_8888);
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

        mInnerCanvasCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCanvasCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mInnerCanvasCirclePaint.setStrokeWidth(PAINT_STROKE_WIDTH);
        mInnerCanvasCirclePaint.setStyle(Paint.Style.FILL);

        mInnerCanvasTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerCanvasTextPaint.setColor(ContextCompat.getColor(mContext, R.color.white));
        mInnerCanvasTextPaint.setStrokeWidth(3);
        mInnerCanvasTextPaint.setStyle(Paint.Style.FILL);
        mInnerCanvasTextPaint.setTextSize(TEXT_SIZE);
        mInnerCanvasTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mInnerCanvasTextPaint.getFontMetrics();
        mInnerTextCenter = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mOutCircleRadius - MARGIN_OUTER_CIRCLE, mPrimaryColorPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mOutCircleRadius - MARGIN_OUTER_CIRCLE - GAP_OUTER_INNER, mPrimaryColorPaint);
        canvas.drawArc(mArcRect, -90.0f, ((float) mProgressValue / 100) * 360, false, mGapPaint);

        mInnerCanvas.drawCircle(mInnerBitmap.getWidth() / 2, mInnerBitmap.getHeight() / 2, mInnerBitmap.getWidth() / 2 - PAINT_STROKE_WIDTH, mInnerCanvasCirclePaint);
        mInnerCanvas.drawText(String.valueOf(mProgressValue), mInnerBitmap.getWidth() / 2, mInnerBitmap.getHeight() / 2 + mInnerTextCenter, mInnerCanvasTextPaint);
        canvas.drawBitmap(mInnerBitmap, mMeasuredWidth / 2 - mInnerBitmap.getWidth() / 2,  mMeasureHeight /2 - mInnerBitmap.getHeight() / 2, mPrimaryColorPaint);
    }

    /**
     *
     * Set the progress value.
     *
     * @param value progress value
     */
    @UiThread
    public void setProgressValue(int value) {
        if (value < 0 ) {
            mProgressValue = 0;
        } else if (value > 100) {
            mProgressValue = 100;
        }

        mProgressValue = value;
        invalidate();
    }

}
