package com.example.testandroid2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 原型进度条
 * Created by Guge on 16/2/14.
 */
public class ProgressView extends View {

    private int height, width;
    private Paint circlePaint;
    private Paint progressPaint;
    private Paint shapePaint;

    private Shader mShader;
    private Shader progressShader;

    private int MaxProgress = 260;
    private int progress = 0;
    private int startProgress = -90;

    private int radius;

    private float littleX;
    private float littleY;

    private RectF circleBounds;

    // 动画
    private Matrix matrix;


    // 旋转角度
    private int start;
    // Handler定时动画
    private Handler handler = new Handler();
    private Runnable run = new Runnable() {

        @Override
        public void run() {
//            start = start + 1;
//            matrix = new Matrix();
            // 参数：旋转角度，围绕点坐标的x,y坐标点
//            matrix.postRotate(1, width / 2, height / 2);
            progress += 2;
            // 刷新重绘
            ProgressView.this.invalidate();
            // 继续循环
            if (progress < MaxProgress)
                handler.postDelayed(run, 6);
        }
    };

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        height = getResources().getDisplayMetrics().heightPixels;
        width = getResources().getDisplayMetrics().widthPixels;
        initView();

        handler.post(run);
    }


    private void initView() {
        circlePaint = new Paint();
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(30);

        progressPaint = new Paint();
        progressPaint.setColor(Color.GREEN);
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(30);

        shapePaint = new Paint();
        shapePaint.setColor(Color.RED);
        shapePaint.setAntiAlias(true);

        radius = width / 2 - (width / 10) * 2;

        mShader = new SweepGradient(width / 2, height / 2, Color.TRANSPARENT,
                Color.parseColor("#AAAAAAAA"));

//        progressShader = new SweepGradient(width / 2, height / 2, new int[]{Color.RED,Color.BLUE}
//                ,new float[]{0.75f,0.25f});
        progressShader = new SweepGradient(width / 2, height / 2, Color.RED, Color.BLUE);
        matrix = new Matrix();
//        progressShader.setLocalMatrix(matrix);
        progressPaint.setShader(progressShader);

        shapePaint.setShader(mShader);


        circleBounds = new RectF(width / 2 - radius,
                height / 2 - radius,
                width / 2 + radius,
                height / 2 + radius);


    }

    /**
     * 测量
     *
     * @author LGL
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置铺满
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(width/2,height/2,width/2,circlePaint);
//        canvas.drawCircle(width / 2, height / 2, width / 2 - width / 10, circlePaint);
        canvas.drawCircle(width / 2, height / 2, width / 2 - (width / 10) * 2, circlePaint);
//        canvas.drawCircle(width/2,height/2,width/2-(width/10)*3,circlePaint);
        canvas.drawArc(circleBounds, startProgress, progress, false,
                progressPaint);
        canvas.drawCircle(littleX, littleY, 20, circlePaint);
        matrix.setRotate(progress, width / 2, height / 2);
        progressShader.setLocalMatrix(matrix);
        littleX = (float) (radius * Math.cos(Math.toRadians(progress - 90))) + width / 2;
        littleY = (float) (radius * Math.sin(Math.toRadians(progress - 90))) + height / 2;

        // 增加旋转动画，使用矩阵实现
//        canvas.concat(matrix); // 前置动画
//        canvas.drawCircle(width/2,height/2,width/2, shapePaint);
    }

}
