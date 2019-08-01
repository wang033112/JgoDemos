package com.jgo.demos.customview.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
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
    private static final int HOOK_WIDTH = 20;

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

    private boolean mIsDrawHooking;
    private float mHookStartX;
    private float mHookStartY;

    private float mHookMiddleX;
    private float mHookMiddleY;

    private float mHookEndX;
    private float mHookEndY;

    private float mHookProgressX;
    private float mHookProgressY;
    private Path mHookPath;
    private Handler mHandler;
    private Paint mHookPaint;

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

        mHookStartX = mInnerBitmap.getWidth() / 4 - 10;
        mHookStartY = mInnerBitmap.getHeight() * 3 / 8 + 10;
        mHookMiddleX = mInnerBitmap.getWidth() * 3 / 8 + 10;
        mHookMiddleY = mInnerBitmap.getHeight() * 5 / 8;
        mHookEndX = mInnerBitmap.getWidth() * 3 / 4 + 20;
        mHookEndY = mInnerBitmap.getHeight() / 4 + 10;

        mHandler = new Handler();
    }

    private void init() {
        mPrimaryColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPrimaryColorPaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));
        mPrimaryColorPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
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
        mInnerCanvasTextPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
        mInnerCanvasTextPaint.setStyle(Paint.Style.FILL);
        mInnerCanvasTextPaint.setTextSize(TEXT_SIZE);
        mInnerCanvasTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = mInnerCanvasTextPaint.getFontMetrics();
        mInnerTextCenter = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

        mHookPath = new Path();
        mHookPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHookPaint.setColor(ContextCompat.getColor(mContext, R.color.white));
        mHookPaint.setStrokeWidth(HOOK_WIDTH);
        mHookPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mOutCircleRadius - MARGIN_OUTER_CIRCLE, mPrimaryColorPaint);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mOutCircleRadius - MARGIN_OUTER_CIRCLE - GAP_OUTER_INNER, mPrimaryColorPaint);
        canvas.drawArc(mArcRect, -90.0f, ((float) mProgressValue / 100) * 360, false, mGapPaint);

        mInnerCanvas.drawCircle(mInnerBitmap.getWidth() / 2, mInnerBitmap.getHeight() / 2, mInnerBitmap.getWidth() / 2 - PAINT_STROKE_WIDTH, mInnerCanvasCirclePaint);

        if (mIsDrawHooking) {
            mHookPath.moveTo(mHookStartX, mHookStartY);

            if (mHookProgressX > mHookMiddleX) {
                mInnerCanvas.drawLine(mHookStartX, mHookStartY, mHookMiddleX, mHookMiddleY, mHookPaint);
                mInnerCanvas.drawLine(mHookMiddleX - HOOK_WIDTH / 2, mHookMiddleY, mHookProgressX, getHookProgressY(), mHookPaint);
            } else {
                mInnerCanvas.drawLine(mHookStartX, mHookStartY, mHookProgressX, getHookProgressY(), mHookPaint);
            }
        } else {
            mInnerCanvas.drawText(String.valueOf(mProgressValue), mInnerBitmap.getWidth() / 2, mInnerBitmap.getHeight() / 2 + mInnerTextCenter, mInnerCanvasTextPaint);
            if (mProgressValue == 100) mHandler.postDelayed(this::startDrawHook, 100);
        }

        canvas.drawBitmap(mInnerBitmap, mMeasuredWidth / 2 - mInnerBitmap.getWidth() / 2,  mMeasureHeight / 2 - mInnerBitmap.getHeight() / 2, mPrimaryColorPaint);
    }

    private void startDrawHook() {
        ValueAnimator animator = ValueAnimator.ofFloat(mHookStartX, mHookEndX);
        animator.addUpdateListener((valueUpdate) -> {
            mHookProgressX = (float) valueUpdate.getAnimatedValue();
            invalidate();

        });

        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //mIsDrawHooking = false;

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mHookPath.reset();
                mIsDrawHooking = true;
            }
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(200);
        animator.start();
    }

    /**
     *
     *     (endX - startX)        (endY - startY)
     *  -------------------- = ---------------------
     *  (progressX - startX)     (progressY - startY)
     *
     * @return progressY
     */
    private float getHookProgressY() {
        float progressY = 0;
        if (mHookProgressX <= mHookMiddleX) {
            progressY = (mHookProgressX - mHookStartX ) * (mHookMiddleY - mHookStartY) / (mHookMiddleX - mHookStartX) + mHookStartY;
        } else {
            progressY = (mHookProgressX - mHookMiddleX ) * (mHookEndY - mHookMiddleY) / (mHookEndX - mHookMiddleX) + mHookMiddleY;
        }
        return progressY;
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
        mIsDrawHooking = false;
        invalidate();
    }

}
