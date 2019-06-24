package com.jgo.demos.listview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/06/20.
 *
 */

public class RoundCornerLayout extends LinearLayout {

    private static final float DEFAULT_RADIUS = 30f;
    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;

    private Paint mImagePaint;
    private Paint mRoundPaint;

    public RoundCornerLayout(Context context) {
        this(context, null);
    }

    public RoundCornerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundCornerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout);
            float radius = ta.getDimension(R.styleable.RoundRelativeLayout_radius, DEFAULT_RADIUS);
            mTopLeftRadius = ta.getDimension(R.styleable.RoundRelativeLayout_topLeftRadius, radius);
            mTopRightRadius = ta.getDimension(R.styleable.RoundRelativeLayout_topRightRadius, radius);
            mBottomLeftRadius = ta.getDimension(R.styleable.RoundRelativeLayout_bottomLeftRadius, radius);
            mBottomRightRadius = ta.getDimension(R.styleable.RoundRelativeLayout_bottomRightRadius, radius);
            ta.recycle();
        }

        mRoundPaint = new Paint();
        mRoundPaint.setColor(Color.WHITE);
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setStyle(Paint.Style.FILL);
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mImagePaint = new Paint();
        mImagePaint.setXfermode(null);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.restore();
    }

    /**
     * Draw top left
     *
     * @param canvas
     */
    private void drawTopLeft(Canvas canvas) {
        if (mTopLeftRadius > 0) {
            Path path = new Path();
            path.moveTo(0, mTopLeftRadius);
            path.lineTo(0, 0);
            path.lineTo(mTopLeftRadius, 0);
            path.arcTo(new RectF(0, 0, mTopLeftRadius * 2, mTopLeftRadius * 2), -90, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    /**
     * Draw top right
     *
     * @param canvas
     */
    private void drawTopRight(Canvas canvas) {
        if (mTopRightRadius > 0) {
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mTopRightRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, mTopRightRadius);
            path.arcTo(new RectF(width - 2 * mTopRightRadius, 0, width, mTopRightRadius * 2), 0, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    /**
     * Draw bottom left
     *
     * @param canvas
     */
    private void drawBottomLeft(Canvas canvas) {
        if (mBottomLeftRadius > 0) {
            int height = getHeight();
            Path path = new Path();
            path.moveTo(0, height - mBottomLeftRadius);
            path.lineTo(0, height);
            path.lineTo(mBottomLeftRadius, height);
            path.arcTo(new RectF(0, height - 2 * mBottomLeftRadius, mBottomLeftRadius * 2, height), 90, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }

    /**
     * Draw bottom right
     *
     * @param canvas
     */
    private void drawBottomRight(Canvas canvas) {
        if (mBottomRightRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            Path path = new Path();
            path.moveTo(width - mBottomRightRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - mBottomRightRadius);
            path.arcTo(new RectF(width - 2 * mBottomRightRadius, height - 2 * mBottomRightRadius, width, height), 0, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
        }
    }
}
