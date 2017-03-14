package com.example.testandroid2.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/2/6 15:14
 */
public class MySeekBar extends SeekBar {
    OnThumbTouchListener listener;

    public MySeekBar(Context context) {
        super(context);
    }

    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(listener!=null){
                    listener.onThumbDown(event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(listener!=null){
                    listener.onThumbMove(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(listener!=null){
                    listener.onThumbUp(event);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if(listener!=null){
                    listener.onThumbUp(event);
                }
                break;
        }

        return super.onTouchEvent(event);

    }

    public void setOnThumbTouchListener(OnThumbTouchListener listener){
        this.listener=listener;
    }

    public interface OnThumbTouchListener{
        void onThumbDown(MotionEvent event);
        void onThumbMove(MotionEvent event);
        void onThumbUp(MotionEvent event);
    }
}
