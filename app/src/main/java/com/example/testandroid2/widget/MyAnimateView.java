package com.example.testandroid2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：16/9/13 18:44
 */
public class MyAnimateView extends View{
    private Paint circlePaint,textPaint;
    private int width;
    private final Matrix mCircleMatrix = new Matrix();
    private int drawTime=0;

    public MyAnimateView(Context context) {
        super(context);
        init();
    }

    public MyAnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyAnimateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        circlePaint=new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);

        textPaint=new Paint(circlePaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width=canvas.getWidth()/2/10*8;

        canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2); //将位置移动画纸的坐标点:150,150
//        for(int i=100;i>0;i--){
//            canvas.drawCircle(0, 0, width*i/100, circlePaint); //画圆圈
//            invalidate();
////            canvas.scale(width*i/100,width*i/100);
//
//        }
        canvas.drawCircle(0, 0, width*drawTime/100, circlePaint); //画圆圈
        drawTime++;
        if(drawTime<100)
            postInvalidateDelayed(10);

    }

    private void drawAnimation(Canvas canvas){


    }
}
