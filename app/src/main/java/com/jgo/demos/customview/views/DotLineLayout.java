package com.jgo.demos.customview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jgo.demos.R;
import com.jgo.demos.util.Utils;

/**
 * Created by ke-oh on 2019/07/23.
 *
 */

public class DotLineLayout extends FrameLayout {

    private static final int RADIUS = Utils.px2dip(160);
    private Context mContext;
    private Paint mPaint;

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
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.dodgerblue));
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setPathEffect(new DashPathEffect(new float[] {16, 8}, 0));

        setPadding(RADIUS, RADIUS, RADIUS, RADIUS);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        Path path = new Path();
        path.moveTo(RADIUS * 2, RADIUS);
        path.lineTo(getWidth() - RADIUS, RADIUS);
        path.lineTo(getWidth() - RADIUS, getHeight() - RADIUS);
        path.lineTo(RADIUS, getHeight() - RADIUS);
        path.lineTo(RADIUS, RADIUS * 2);
        //path.close();

        canvas.drawPath(path, mPaint);
        canvas.drawCircle(RADIUS, RADIUS, RADIUS, mPaint);


    }
}
