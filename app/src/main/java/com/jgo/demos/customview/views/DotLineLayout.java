package com.jgo.demos.customview.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

import com.jgo.demos.R;
import com.jgo.demos.util.Utils;

/**
 * Created by ke-oh on 2019/07/23.
 *
 */

public class DotLineLayout extends FrameLayout {

    private static final String TAG = DotLineLayout.class.getSimpleName();

    private static final int PAINT_WIDTH = Utils.px2dip(10);
    private static final int RADIUS = Utils.px2dip(160) - PAINT_WIDTH;
    private Context mContext;
    private Paint mDotLinePaint;
    private Paint mLinePaint;

    private boolean isUpdatingBorder;
    private boolean mIsSelected;

    private float mLeftBorderLength = 0.0f;
    private float mRightBorderLength = 0.0f;
    private float mTopBorderLength = 0.0f;
    private float mBottomBorderLength = 0.0f;

    private Path mLeftBorderPath;
    private Path mRightBorderPath;
    private Path mTopBorderPath;
    private Path mBottomBorderPath;
    private RectF mCircleRecF;
    private float mSweepAngle;

    public DotLineLayout(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DotLineLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public DotLineLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    /**
     * init the paint.
     */
    private void init() {
        mDotLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotLinePaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));
        mDotLinePaint.setStrokeWidth(PAINT_WIDTH);
        mDotLinePaint.setStyle(Paint.Style.STROKE);
        mDotLinePaint.setPathEffect(new DashPathEffect(new float[] {16, 8}, 0));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));
        mLinePaint.setStrokeWidth(PAINT_WIDTH);
        mLinePaint.setStyle(Paint.Style.STROKE);

        mLeftBorderPath = new Path();
        mRightBorderPath = new Path();
        mTopBorderPath = new Path();
        mBottomBorderPath = new Path();

        mCircleRecF = new RectF(PAINT_WIDTH, PAINT_WIDTH, 2 * RADIUS, 2 * RADIUS);

        setPadding(RADIUS, RADIUS, RADIUS, RADIUS);

        setOnClickListener((view) -> {
            if (isUpdatingBorder) {
                return;
            }

            mIsSelected = !mIsSelected;
            performOnClick();
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (mIsSelected) {
            drawLineBorder(canvas);
        } else {
            drawDashLineBorder(canvas);
        }
    }

    /**
     *
     * @param canvas
     */
    private void drawDashLineBorder(Canvas canvas) {
        Path path = new Path();
        path.moveTo(RADIUS * 2, RADIUS);//top-left-start
        path.lineTo(getWidth() - RADIUS, RADIUS);//top - right
        path.lineTo(getWidth() - RADIUS, getHeight() - RADIUS);//bottom - right
        path.lineTo(RADIUS, getHeight() - RADIUS);//bottom - left
        path.lineTo(RADIUS, RADIUS * 2);//top - left - end

        canvas.drawPath(path, mDotLinePaint);
        canvas.drawCircle(RADIUS + PAINT_WIDTH, RADIUS + PAINT_WIDTH, RADIUS, mDotLinePaint);
    }

    /**
     *
     * @param canvas
     */
    private void drawLineBorder(Canvas canvas) {
        //top-border
        mTopBorderPath.lineTo(RADIUS * 2 + mTopBorderLength, RADIUS);
        canvas.drawPath(mTopBorderPath, mLinePaint);

        //right-border
        mRightBorderPath.lineTo(getWidth() - RADIUS, RADIUS + mRightBorderLength);
        canvas.drawPath(mRightBorderPath, mLinePaint);

        //bottom-border
        mBottomBorderPath.lineTo(getWidth() - RADIUS - mBottomBorderLength, getHeight() - RADIUS);
        canvas.drawPath(mBottomBorderPath, mLinePaint);

        //left-border
        mLeftBorderPath.lineTo(RADIUS, getHeight() - RADIUS - mLeftBorderLength);
        canvas.drawPath(mLeftBorderPath, mLinePaint);

        canvas.drawArc(mCircleRecF, 0, mSweepAngle, false, mLinePaint);

    }

    /**
     * perform when clicked.
     */
    private void performOnClick() {
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.addUpdateListener((valueAnimator) -> {
            float updateValue = (float)valueAnimator.getAnimatedValue();
            mLeftBorderLength = updateValue * (getHeight() - 3 * RADIUS);
            mRightBorderLength = updateValue * (getHeight() - 2 * RADIUS);
            mTopBorderLength = updateValue * (getWidth() - 3 * RADIUS);
            mBottomBorderLength = updateValue * (getWidth() - 2 * RADIUS);
            mSweepAngle = updateValue * 360;
            invalidate();
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isUpdatingBorder = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isUpdatingBorder = true;

                if (mIsSelected) {
                    mTopBorderPath.reset();
                    mTopBorderPath.moveTo(RADIUS * 2, RADIUS);//top-left-start

                    mRightBorderPath.reset();
                    mRightBorderPath.moveTo(getWidth() - RADIUS, RADIUS);//top - right

                    mBottomBorderPath.reset();
                    mBottomBorderPath.moveTo(getWidth() - RADIUS, getHeight() - RADIUS);//bottom - right

                    mLeftBorderPath.reset();
                    mLeftBorderPath.moveTo(RADIUS, getHeight() - RADIUS);//bottom - left
                }
            }
        });

        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(200);
        animator.start();
    }
}
