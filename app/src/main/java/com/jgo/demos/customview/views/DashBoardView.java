package com.jgo.demos.customview.views;

import android.animation.ValueAnimator;
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

    private static final String TAG = DashBoardView.class.getSimpleName();
    private static final int MARGIN_HEIGHT = 50;
    private Context mContext;

    private Paint paint , tmpPaint , textPaint ,  strokePain;
    private RectF rect;
    //private int backGroundColor;
    private float pointLength;
    private float per;
    private float perPoint;
    private float perOld;
    private float mLength;
    private int mWidth;

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
        rect = new RectF();
        textPaint = new Paint();
        tmpPaint = new Paint();
        strokePain = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int height = mWidth / 2 + MARGIN_HEIGHT;

        mLength = mWidth / 2  / 4 * 3;
        pointLength =  - (float) (mWidth / 2 *  0.6);
        setMeasuredDimension(mWidth, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        //颜色指示的环
        initRing(canvas);
        //刻度文字
        initScale(canvas);
        //指针
        initPointer(canvas);
        //提示内容
        initText(canvas);
    }

    private void initText(Canvas canvas) {
        //抗锯齿
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, mWidth / 2);

        float rIndex = mLength ;

        //设置文字展示的圆环
        paint.setColor(Color.parseColor("#eeeeee"));
        paint.setShader(null);
        paint.setShadowLayer(5, 0, 0, 0x54000000);
        rect = new RectF( - (rIndex/ 3 ), - (rIndex / 3), rIndex / 3, rIndex / 3);
        canvas.drawArc(rect, 0, 360, true, paint);

        paint.clearShadowLayer();

        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2f , mWidth / 2);


        textPaint.setStrokeWidth(1);
        textPaint.setAntiAlias(true);

        textPaint.setTextSize(60);
        textPaint.setColor(Color.parseColor("#fc6555"));
        textPaint.setTextAlign(Paint.Align.RIGHT);

        int _per = (int) (per * 120);

        if (_per < 60){
            textPaint.setColor(Color.parseColor("#ff6450"));
        }else if (_per < 100) {
            textPaint.setColor(Color.parseColor("#f5a623"));
        }else {
            textPaint.setColor(Color.parseColor("#79d062"));
        }

        float swidth = textPaint.measureText(String.valueOf(_per));
        //计算偏移量 是的数字和百分号整体居中显示
        swidth =   (swidth - (swidth + 22) / 2);


        canvas.translate( swidth , 0);
        canvas.drawText("" + _per, 0, 0, textPaint);

        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.LEFT);

        canvas.drawText("%" , 0, 0, textPaint);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.parseColor("#999999"));


        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2  , mWidth / 2 + mLength / 3 /2 );
        canvas.drawText("完成率" , 0, 0, textPaint);
    }

    private void initScale(Canvas canvas) {
        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, mWidth / 2);
        paint.setColor(Color.parseColor("#999999"));

        tmpPaint = new Paint(paint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(1);
        tmpPaint.setTextSize(35);
        tmpPaint.setTextAlign(Paint.Align.CENTER);



        canvas.rotate(-90,0f,0f);

        float  y = mLength;
        y = - y;
        int count = 12; //总刻度数
        paint.setColor(ContextCompat.getColor(mContext, R.color.white));

        float tempRou = 180 / 12f;

        paint.setColor(ContextCompat.getColor(mContext, R.color.white));
        paint.setStrokeWidth(5);

        //绘制刻度和百分比
        for (int i = 0 ; i <= count ; i++){

            if (i % 2 == 0 ) {
                canvas.drawText(String.valueOf((i) * 10), 0, y - 20f, tmpPaint);
            }

            canvas.drawLine(0f, y , 0, y + mLength / 15, paint);


            canvas.rotate(tempRou,0f,0f);
        }

    }


    private void initPointer(Canvas canvas) {
        paint.setColor(Color.BLACK);


        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, mWidth / 2);
        float change;

        if (perPoint < 1 ){
            change = perPoint * 180;
        }else {
            change = 180;
        }

        //根据参数得到旋转角度
        canvas.rotate(-90 + change,0f,0f);

        //绘制三角形形成指针
        Path path = new Path();
        path.moveTo(0 , pointLength);
        path.lineTo(-10 , 0);
        path.lineTo(10,0);
        path.lineTo(0 , pointLength);
        path.close();

        canvas.drawPath(path, paint);

    }

    private void initRing(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        canvas.save();
        canvas.translate(canvas.getWidth()/2, mWidth / 2);


        //前100红黄渐变圆环
        paint.setStyle(Paint.Style.FILL);
        int[] colors = {Color.parseColor("#F95A37"), Color.parseColor("#f9cf45"), Color.parseColor("#00ff00")};
        float[] positions = {0.5f - 10f/180f * 0.5f, 0.5f + 0.5f * 5f / 6f, 1.0f};
        SweepGradient sweepGradient = new SweepGradient(0, 0, colors, positions);
        paint.setShader(sweepGradient);
        rect = new RectF( -mLength, -mLength, mLength, mLength);
        canvas.drawArc(rect, 170, 10f + 180f / 6f * 5f, true, paint);



        //100之后绿色渐变圆环
        paint.setStyle(Paint.Style.FILL);
        canvas.rotate(10,0f,0f);
        int[] colors2 = {Color.parseColor("#79D062"),  Color.parseColor("#3FBF55")};
        float[] positions2 = {0.5f + 0.5f * ( 144f / 180f), 1.0f};
        sweepGradient = new SweepGradient(0, 0, colors2, positions2);
        paint.setShader(sweepGradient);
        rect = new RectF( -mLength, -mLength, mLength, mLength);
        canvas.drawArc(rect, 180f + 180f * (140f / 180f), 180f / 6 + 10, true, paint);



        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, mWidth / 2);

        strokePain = new Paint(paint);

        strokePain.setColor(0x3f979797);
        strokePain.setStrokeWidth(10);
        strokePain.setShader(null);
        strokePain.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rect, 170, 200, true, strokePain);



        canvas.restore();
        canvas.save();
        canvas.translate(canvas.getWidth()/2, mWidth / 2);

        //底边水平
        paint.setShader(null);
        paint.setColor(ContextCompat.getColor(mContext, R.color.white));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(-mLength  , (float) (Math.sin(Math.toRadians(10) ) * mLength /3f * 2f), mLength  ,  (float) (Math.sin(Math.toRadians(10)) * mLength  + 100) , paint);
        canvas.drawRect(-mLength  , (float) (Math.sin(Math.toRadians(10) ) * mLength /3f * 2f), mLength  ,  (float) (Math.sin(Math.toRadians(10) ) * mLength /3f * 2f) , strokePain);


        //内部背景色填充
        paint.setColor(ContextCompat.getColor(mContext, R.color.white));
        paint.setShader(null);
        rect = new RectF( - (mLength - mLength / 3f  - 2), -(mLength / 3f * 2f - 2), mLength - mLength / 3f -2 , mLength / 3f * 2f - 2);
        canvas.drawArc(rect, 170, 200, true, strokePain);
        canvas.drawArc(rect, 0, 360, true, paint);



    }



    public void cgangePer(float per ){
        this.perOld = this.per;
        this.per = per;
        ValueAnimator va =  ValueAnimator.ofFloat(perOld,per);
        va.setDuration(1000);
        va.setInterpolator(new OvershootInterpolator());
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                perPoint = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        va.start();

    }
}