package com.example.testandroid2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.testandroid2.bean.ResultBean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/2/16 15:39
 */
public class BaseActivity extends AppCompatActivity implements Observer<ResultBean> {
    Observable<ResultBean> observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResultBean resultBean) {
        Timber.e("onNext",resultBean.getCode());
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        Timber.e("onComplete");
    }


    /***
     * 显示toast，默认显示时间为LENGTH_SHORT
     *
     * @param msg 显示信息
     */
    public void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast，防止一直点击弹出Toast
     *
     * @param text     显示的信息
     * @param duration 信息显示的时间
     */
    public void toast(String text, int duration) {
        Toast.makeText(this, text, duration).show();
    }

}
