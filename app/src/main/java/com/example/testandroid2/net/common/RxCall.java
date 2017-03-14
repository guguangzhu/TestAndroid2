package com.example.testandroid2.net.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.testandroid2.bean.ResultBean;
import com.example.testandroid2.net.enums.Task;
import com.example.testandroid2.tools.DialogUtils;
import com.example.testandroid2.tools.OtherUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/2/16 16:13
 */
public class RxCall implements NetCallback {

    // 网络加载进度条
    public ProgressDialog netDialog;

    private Context context;

    private boolean isShowDialog;

    private Task task;

    private Observable<ResultBean> observable;

    private Observer<ResultBean> observer;

    private static final int SUCCESS = 0x001;


    public RxCall(Context context,Observer<ResultBean> observer, Observable<ResultBean> observable, boolean isShowDialog, Task task) {
        this.context = context;
        this.isShowDialog = isShowDialog;
        this.task = task;
        this.observer=observer;
        this.observable = observable;

    }


    public void startRequest(Map<String, String> map) {
        if (!OtherUtils.isOpenNetwork(context)) {
            DialogUtils.createDialogToSettingNetWork(context);
            return;
        }
        showDialog(context);
        RequestUtil request = new RequestUtil();
        request.setCallback(this);

        try {
            request.request(task, map);
        } catch (Exception e) {
            e.printStackTrace();
            dismissDialog();
        }
    }

    /**
     * 显示网络进度提示
     *
     * @param context 上下文环境
     */
    public void showDialog(Context context) {
        try {
            if (netDialog == null) {
                netDialog = new ProgressDialog(context);
                netDialog.setMessage("正在获取数据...");
                netDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                netDialog.setCancelable(true);
                netDialog.setCanceledOnTouchOutside(false);
                netDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
//                        onBackCancle();
                    }
                });
            }
            if (context instanceof Activity
                    && !((Activity) context).isFinishing()) {
                netDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将进度提示框消失
     */
    public void dismissDialog() {
        try {
            if (netDialog != null && netDialog.isShowing()) {
                netDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(String result, Task task) {
        dismissDialog();
        final ResultBean data = new ResultBean();
        Object resultBean = null;
        try {
            resultBean = new Gson().fromJson(result, task.getJsonType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        data.setCode("200");
        data.setMsg("OK");
        data.setTask(task);
        data.setData(resultBean);
        observable = Observable.create(new ObservableOnSubscribe<ResultBean>() {

            @Override
            public void subscribe(ObservableEmitter<ResultBean> e) throws Exception {
                e.onNext(data);
                e.onComplete();
            }
        });
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
    }

    @Override
    public void onError(IOException e, Task task) {
        dismissDialog();
    }
}
