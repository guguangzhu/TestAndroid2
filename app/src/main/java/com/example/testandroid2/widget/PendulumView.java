package com.example.testandroid2.widget;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.testandroid2.R;


/**
 * 类描述：钟摆
 * 创建人：G.G.Z
 * 创建时间：2017/2/20 15:23
 */
public class PendulumView extends View {
    private int mGlobeNum=5;
    private int mGlobeRadius=15;
    private int mGlobeColor=Color.BLUE;
    private int mSwingRadius=16;

    private boolean isStarted=false;
    private Paint mPaint;
    private Point mLeftPoint,mRightPoint;
    private boolean lastSlope;
    private float mLastFraction;
    private boolean isNext;

    public PendulumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //使用TypedArray读取自定义的属性值
        TypedArray ta = context.getResources().obtainAttributes(attrs, R.styleable.PendulumView);
        if(ta!=null){
            mGlobeNum = ta.getInt( R.styleable.PendulumView_globeNum, 5);
            mGlobeRadius = ta.getDimensionPixelSize(R.styleable.PendulumView_globeRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics()));
            mGlobeColor = ta.getColor(R.styleable.PendulumView_globeColor, Color.BLUE);
            mSwingRadius = ta.getDimensionPixelSize(R.styleable.PendulumView_swingRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 80, getResources().getDisplayMetrics()));
            ta.recycle();  //避免下次读取时出现问题
        }
        mPaint = new Paint();
        mPaint.setColor(mGlobeColor);

        //初始化最左右两小球坐标
//        mLeftPoint = new Point(mSwingRadius, mSwingRadius);
//        mRightPoint = new Point(mSwingRadius + mGlobeRadius * 2 * (mGlobeNum - 1), mSwingRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //高度为小球半径+摆动半径
        int height = mGlobeRadius + mSwingRadius;
        //宽度为2*摆动半径+（小球数量-1）*小球直径
        int width = mSwingRadius + mGlobeRadius * 2 * (mGlobeNum - 1) + mSwingRadius;
        //如果测量模式为EXACTLY，则直接使用推荐值，如不为EXACTLY（一般处理wrap_content情况），使用自己计算的宽高
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawInnerBall(canvas);


        if (mLeftPoint == null || mRightPoint == null) {
            //初始化最左右两小球坐标
            mLeftPoint = new Point(mSwingRadius, mSwingRadius);
            mRightPoint = new Point(mSwingRadius + mGlobeRadius * 2 * (mGlobeNum - 1), mSwingRadius);
            //开启摆动动画
            startPendulumAnimation();
        }

        drawSideBall(canvas);
    }


    /**
     * 画内部小球
     */
    private void drawInnerBall(Canvas canvas){
        for(int i=0;i<mGlobeNum-2;i++){
            canvas.drawCircle(mSwingRadius+mGlobeRadius*2*(i+1),mSwingRadius,mGlobeRadius,mPaint);
        }
    }

    /**
     *
     * @param canvas
     */
    private void drawSideBall(Canvas canvas){
        //绘制左右两小球
        canvas.drawCircle(mLeftPoint.x, mLeftPoint.y, mGlobeRadius, mPaint);
        canvas.drawCircle(mRightPoint.x, mRightPoint.y, mGlobeRadius, mPaint);
    }

    public void startPendulumAnimation() {
        //使用属性动画
        final ValueAnimator anim = ValueAnimator.ofObject(new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                //参数fraction用于表示动画的完成度，我们根据它来计算当前的动画值
                double angle = Math.toRadians(90 * fraction);
                int x = (int) ((mSwingRadius - mGlobeRadius) * Math.sin(angle));
                int y = (int) ((mSwingRadius - mGlobeRadius) * Math.cos(angle));
                Point point = new Point(x, y);
                return point;
            }
        }, new Point(), new Point());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Point point = (Point) animation.getAnimatedValue();
                //获得当前的fraction值
                float fraction = anim.getAnimatedFraction();
                //判断是否是fraction先减小后增大，即是否处于即将向上摆动状态
                //在每次即将向上摆动时切换小球
                if (lastSlope && fraction > mLastFraction) {
                    isNext = !isNext;
                }
                //通过不断改动左右小球的x,y坐标值实现动画效果
                //利用isNext来判断应该是左边小球动，还是右边小球动
                if (isNext) {
                    //当左边小球摆动时，右边小球置于初始位置
                    mRightPoint.x = mSwingRadius + mGlobeRadius * 2 * (mGlobeNum - 1);
                    mRightPoint.y = mSwingRadius;
                    mLeftPoint.x = mSwingRadius - point.x;
                    mLeftPoint.y = mGlobeRadius + point.y;
                } else {
                    //当右边小球摆动时，左边小球置于初始位置
                    mLeftPoint.x = mSwingRadius;
                    mRightPoint.y = mSwingRadius;
                    mRightPoint.x = mSwingRadius + (mGlobeNum - 1) * mGlobeRadius * 2 + point.x;
                    mRightPoint.y = mGlobeRadius + point.y;

                }

                invalidate();
                lastSlope = fraction < mLastFraction;
                mLastFraction = fraction;
            }
        });
        //设置永久循环播放
        anim.setRepeatCount(ValueAnimator.INFINITE);
        //设置循环模式为倒序播放
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setDuration(200);
        //设置补间器，控制动画的变化速率
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
    }
}
