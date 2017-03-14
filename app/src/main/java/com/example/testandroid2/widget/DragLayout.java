package com.example.testandroid2.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.testandroid2.R;

/**
 * Created by Guge on 16/2/25.
 */
public class DragLayout extends LinearLayout {

    private final ViewDragHelper mDragHelper;
    private int height,width;
    private View mDragView;
    private View mZoomView;
    private View mTextView;
    private int mTop;
    private int upPosition=200;    //回弹的最高位置
    private float posOffset;  //便宜百分比

    private int topX=100,topY=100;
    private float bottomX,bottomY;

    public DragLayout(Context context) {
        this(context, null);
    }
    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new MyViewDragCallback());
        height=getResources().getDisplayMetrics().heightPixels;
        width=getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mZoomView=getChildAt(0);
        mDragView=getChildAt(1);
        mTextView=mZoomView.findViewById(R.id.tv_temperature);

    }



    @Override
    public void computeScroll()
    {
        if(mDragHelper.continueSettling(true))
        {
            invalidate();
        }
    }

    private class MyViewDragCallback extends ViewDragHelper.Callback {


        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child==mDragView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int newTop =Math.min( Math.max(top, upPosition),mZoomView.getHeight());
//            Log.d("clampViewPositionV","dy--------------"+dy);
            if(bottomX==0){
                bottomX=mTextView.getX();
                bottomY=mTextView.getY();
            }

            return newTop;
        }



        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTop=top;
            posOffset=(float)top/(mZoomView.getHeight());
            Log.d("onViewPositionChanged", "posOffset--------------" + posOffset);
            mTextView.setScaleX(posOffset);
            mTextView.setScaleY(posOffset);
            mTextView.setX(bottomX * posOffset);
            mTextView.setY(bottomY * posOffset);
//            mTextView.setScrollX((int) (bottomX * posOffset));
//            mTextView.setScrollY((int) (bottomY * posOffset));
//            mDragHelper.smoothSlideViewTo(mTextView, (int)(bottomX * posOffset), (int)(bottomY * posOffset));
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel)
        {
            //mAutoBackView手指释放时可以自动回去

            int destinyY=upPosition;
            if (releasedChild == mDragView)
            {

                if (yvel > 0 || (yvel == 0 && mTop > (mZoomView.getHeight()-200)/2)){
                    destinyY=mZoomView.getHeight();
                }
                mDragHelper.setMinVelocity(1000);
                mDragHelper.settleCapturedViewAt(0, destinyY);
//                mDragHelper.smoothSlideViewTo(mDragView,0,destinyY);
                invalidate();
            }
        }
    }

}
