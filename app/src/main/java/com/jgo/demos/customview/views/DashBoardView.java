package com.jgo.demos.customview.views;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.jgo.demos.R;


/**
 * Created by ke-oh on 2019/07/31.
 *
 */
public class DashBoardView extends View {
    
    private static final int MARGIN_HEIGHT = 50;
    private static final int TEXT_SIZE = 60;
    private static final int NUM_SIZE = 35;
    private static final int NUM_CNT = 10;
    private static final int DURATION = 1000;
    private static final float ANGLE_STEP = 180 / NUM_CNT;

    private Context mContext;
    private Paint paint;
    private Paint mScalePaint;
    private Paint mNumTextPaint;
    private Paint mPointerPaint;
    private Paint mTextPaint;
    private Paint mTextCenterPaint;
    private RectF mInnerRingRect;
    private RectF mOuterRingRect;
    private RectF mTextRect;
    private Path mPointerPath;
    private float mProgressValue;
    private float perPoint;
    private float mLength;
    private int mWidth;
    private SweepGradient mSweepGradient;
    private PaintFlagsDrawFilter mFilter;

    public DashBoardView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public DashBoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     *
     */
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        mScalePaint = new Paint();
        mScalePaint.setColor(ContextCompat.getColor(mContext, R.color.white));
        mScalePaint.setStrokeWidth(5);

        mNumTextPaint = new Paint();
        mNumTextPaint.setAntiAlias(true);
        mNumTextPaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mNumTextPaint.setStrokeWidth(1);
        mNumTextPaint.setTextSize(NUM_SIZE);
        mNumTextPaint.setTextAlign(Paint.Align.CENTER);

        mPointerPaint = new Paint();
        mPointerPaint.setAntiAlias(true);
        mPointerPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));

        mTextCenterPaint = new Paint();
        mTextCenterPaint.setAntiAlias(true);
        mTextCenterPaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));

        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPointerPath = new Path();

        int[] sweepGradientColor = new int[]{
                ContextCompat.getColor(mContext, R.color.ring_end_color),
                ContextCompat.getColor(mContext, R.color.ring_start_color),
                ContextCompat.getColor(mContext, R.color.ring_end_color)};
        mSweepGradient = new SweepGradient(0, 0, sweepGradientColor, null);

        mFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int height = mWidth / 2 + MARGIN_HEIGHT;
        mLength = mWidth / 2  / 4 * 3;

        mOuterRingRect = new RectF( -mLength, -mLength, mLength, mLength);
        mInnerRingRect = new RectF( - (mLength - mLength / 3f  - 2),
                -(mLength / 3f * 2f - 2),
                mLength - mLength / 3f -2 ,
                mLength / 3f * 2f - 2);
        mTextRect = new RectF(-(mLength / 4 ), - (mLength / 4), mLength / 4, mLength / 4);

        mPointerPath.moveTo(0 , - (float) (mWidth / 2 *  0.6));
        mPointerPath.lineTo(-10 , 0);
        mPointerPath.lineTo(10,0);
        mPointerPath.lineTo(0 , - (float) (mWidth / 2 *  0.6));
        mPointerPath.close();

        setMeasuredDimension(mWidth, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        //1.draw ring and scale
        paint.setStrokeWidth(1);
        canvas.save();
        canvas.translate(canvas.getWidth()/2, mWidth / 2);
        paint.setStyle(Paint.Style.FILL);

        paint.setShader(mSweepGradient);
        canvas.drawArc(mOuterRingRect, 170, 200f, true, paint);

        paint.setShader(null);
        paint.setColor(ContextCompat.getColor(mContext, R.color.white));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(-mLength  , (float) (Math.sin(Math.toRadians(10) ) * mLength /3f * 2f),
                mLength, (float) (Math.sin(Math.toRadians(10)) * mLength  + 100) , paint);

        paint.setColor(ContextCompat.getColor(mContext, R.color.white));
        paint.setShader(null);
        canvas.drawArc(mInnerRingRect, 0, 360, true, paint);

        //2.draw scale numm
        canvas.rotate(-90, 0f, 0f);
        for (int i = 0; i <= NUM_CNT; i++){
            canvas.drawText(String.valueOf(i * 10), 0, -mLength - 20f, mNumTextPaint);
            canvas.drawLine(0f, -mLength , 0, -mLength + mLength / 15, mScalePaint);
            canvas.rotate(ANGLE_STEP, 0f, 0f);
        }

        //3.draw pointer
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2, mWidth / 2);
        canvas.rotate(-90 + perPoint * 180 / 100, 0f, 0f);
        canvas.drawPath(mPointerPath, mPointerPaint);

        //4.draw text
        canvas.setDrawFilter(mFilter);
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth() / 2, mWidth / 2);
        canvas.drawArc(mTextRect, 0, 360, true, mTextCenterPaint);

        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.white));
        canvas.drawText(String.valueOf((int)mProgressValue), 0, 0, mTextPaint);
    }

    /**
     * Update progress 0 - 100
     *
     * @param progress 0 - 100
     */
    public void updateProgress(float progress) {
        if (progress < 0 || progress > 100) {
            return;
        }

        float preValue = this.mProgressValue;
        this.mProgressValue = progress;
        ValueAnimator animator =  ValueAnimator.ofFloat(preValue, progress);
        animator.setDuration(DURATION);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener((animation) -> {
                perPoint = (float) animation.getAnimatedValue();
                invalidate();
            });
        animator.start();
    }
}