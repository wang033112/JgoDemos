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

public class CouponTitleView extends RelativeLayout {

    private static final int CIRCLE_COUNT = 25;
    private Context mContext;
    private Paint mPaint;

    public CouponTitleView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CouponTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public CouponTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     * init the paint for draw
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
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

        int radius = getWidth() / (CIRCLE_COUNT * 2);
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            canvas.drawPoint(radius * (i * 2 + 1), getHeight(), mPaint);
        }
    }
}
