package com.jgo.demos.listview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by ke-oh on 2019/07/15.
 *
 */

public class CouponDetailView extends RelativeLayout {

    private Paint mPaint;

    public CouponDetailView(Context context) {
        super(context);
        init();
    }

    public CouponDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CouponDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * init the paint for draw
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawLine(2.5f, 0, getWidth() - 2.5f, 0, mPaint);
    }
}
