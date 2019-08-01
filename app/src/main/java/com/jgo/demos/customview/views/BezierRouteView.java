package com.jgo.demos.customview.views;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jgo.demos.R;

/**
 * Created by ke-oh on 2019/07/28.
 *
 */

public class BezierRouteView extends FrameLayout implements View.OnClickListener {

    private static final String TAG = BezierRouteView.class.getSimpleName();
    private Context mContext;
    private PointF mPointFEnd;
    private PointF mPointFStart;

    public BezierRouteView(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BezierRouteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public BezierRouteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        View view = findViewById(R.id.bezier_target_img);
        mPointFEnd = new PointF(view.getX(), view.getY());
        Log.d(TAG, "mPointFEnd : " + view.getX() + ", " + view.getY());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        findViewById(R.id.besier_route_a).setOnClickListener(this);
        findViewById(R.id.besier_route_b).setOnClickListener(this);
        findViewById(R.id.besier_route_c).setOnClickListener(this);
        findViewById(R.id.besier_route_d).setOnClickListener(this);

    }

    private void init() {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void onClick(View v) {
        String showStr = "";
        switch (v.getId()) {
            case R.id.besier_route_a :
                showStr = "A";
                break;
            case R.id.besier_route_b :
                showStr = "B";
                break;
            case R.id.besier_route_c :
                showStr = "C";
                break;
            case R.id.besier_route_d :
                showStr = "D";
                break;
        }
        Toast.makeText(mContext, showStr, Toast.LENGTH_LONG).show();
    }

    private void startBezierMove(View view) {
        //ObjectAnimator animator = ObjectAnimator.ofObject(view, View.TRANSLATION_X, )
    }

    private class PointFTypeEvaluator implements TypeEvaluator<PointF> {

        PointF control;
        PointF mPointF = new PointF();
        public PointFTypeEvaluator(PointF control) {
            this.control = control;
        }
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return getBezierPoint(startValue, endValue, control, fraction);
        }

        private PointF getBezierPoint(PointF start, PointF end, PointF control, float t) {
            mPointF.x = (1 - t) * (1 - t) * start.x + 2 * t * (1 - t) * control.x + t * t * end.x;
            mPointF.y = (1 - t) * (1 - t) * start.y + 2 * t * (1 - t) * control.y + t * t * end.y;
            return mPointF;
        }
    }
}
