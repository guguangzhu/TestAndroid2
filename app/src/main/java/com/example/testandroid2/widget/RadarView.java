package com.example.testandroid2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Guge on 16/2/14.
 */
public class RadarView extends View {

    private int height,width;
    private Paint circlePaint;
    private Paint shapePaint;

    private Shader mShader;

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
            matrix.postRotate(1, width / 2, height / 2);
            // 刷新重绘
            RadarView.this.invalidate();
            // 继续循环
            handler.postDelayed(run, 6);
        }
    };

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        height=getResources().getDisplayMetrics().heightPixels;
        width=getResources().getDisplayMetrics().widthPixels;
        initView();

        handler.post(run);
    }



    private void initView(){
        circlePaint=new Paint();
        circlePaint.setColor(Color.WHITE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);

        shapePaint=new Paint();
        shapePaint.setColor(Color.RED);
        shapePaint.setAntiAlias(true);


        mShader = new SweepGradient(width / 2, height / 2, Color.TRANSPARENT,
                Color.parseColor("#AAAAAAAA"));

        shapePaint.setShader(mShader);

        matrix = new Matrix();
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
        canvas.drawCircle(width/2,height/2,width/2,circlePaint);
        canvas.drawCircle(width / 2, height / 2, width / 2 - width / 10, circlePaint);
        canvas.drawCircle(width/2,height/2,width/2-(width/10)*2,circlePaint);
        canvas.drawCircle(width/2,height/2,width/2-(width/10)*3,circlePaint);


        // 增加旋转动画，使用矩阵实现
        canvas.concat(matrix); // 前置动画
        canvas.drawCircle(width/2,height/2,width/2, shapePaint);
    }

}
