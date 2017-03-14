package com.example.testandroid2.net.common;

import android.content.Context;

import com.example.testandroid2.bean.ResultBean;
import com.example.testandroid2.net.enums.Task;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Guge on 16/1/29.
 */
public class RequestQueue {


    public static void addToQueue(Context context,RequestCallback requestCallback,boolean isShowDialog, Task task
            , Map<String, String> params) {


        BaseCall call=new BaseCall(context,requestCallback,isShowDialog,task);
        call.startRequest(params);
    }


    public static void addToRxQueus(Context context,Observer<ResultBean> observer, Observable<ResultBean> observable, boolean isShowDialog
            , Task task, Map<String,String> params){
        RxCall call=new RxCall(context,observer,observable,isShowDialog,task);
        call.startRequest(params);

    }

}
