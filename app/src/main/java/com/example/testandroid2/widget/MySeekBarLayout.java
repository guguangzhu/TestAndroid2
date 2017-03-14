package com.example.testandroid2.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.testandroid2.R;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/2/6 15:50
 */
public class MySeekBarLayout extends LinearLayout implements MySeekBar.OnThumbTouchListener ,ValueAnimator.AnimatorUpdateListener{
    TextView indicator;
    MySeekBar seekBar;
    ValueAnimator waxAnim,diminishAnim;


    public MySeekBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        LinearLayout content = (LinearLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.layout_seek_bar, this);
        indicator = (TextView) content.findViewById(R.id.tv_indicator);

        seekBar = (MySeekBar) content.findViewById(R.id.seek_bar);
        seekBar.setOnThumbTouchListener(this);
        seekBar.setProgress(0);
        seekBar.setMax(5);

        waxAnim = ObjectAnimator
                .ofFloat(1.0F,  1.4F)
                .setDuration(100);
        waxAnim.addUpdateListener(this);

        diminishAnim = ObjectAnimator
                .ofFloat(1.4F,  1.0F)
                .setDuration(100);
        diminishAnim.addUpdateListener(this);
    }


    @Override
    public void onThumbDown(MotionEvent event) {
        waxAnim.start();
    }

    @Override
    public void onThumbMove(MotionEvent event) {
        indicator.setX(getXPosition(seekBar));
        indicator.setText(seekBar.getProgress()+"");
    }

    @Override
    public void onThumbUp(MotionEvent event) {
        diminishAnim.start();
    }

    private float getXPosition(SeekBar seekBar){
        float val = (((float)seekBar.getProgress() * (float)(seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax());
        float offset = seekBar.getThumbOffset()/2;

        return val-offset;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        indicator.setX(getXPosition(seekBar));
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float cVal = (Float) animation.getAnimatedValue();
        indicator.setScaleX(cVal);
        indicator.setScaleY(cVal);
    }
}
