package com.example.testandroid2.net.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.testandroid2.net.enums.Task;
import com.example.testandroid2.tools.DialogUtils;
import com.example.testandroid2.tools.OtherUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Guge on 16/1/29.
 */
public class BaseCall implements NetCallback{

    // 网络加载进度条
    public ProgressDialog netDialog;

    private Context context;
    private boolean isShowDialog;
    private Task task;
    private RequestCallback requestCallback;
    private static final int SUCCESS=0x001;

    private static Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    Map map=(Map)msg.obj;
                    RequestCallback listener=(RequestCallback)map.get("callback");
                    Object resultBean=map.get("result");
                    Task task= (Task) map.get("task");
                    if(listener!=null)
                        listener.response(task, resultBean);
            }
        }
    };

    public BaseCall(Context context,RequestCallback requestCallback,boolean isShowDialog,Task task){
        this.context=context;
        this.requestCallback=requestCallback;
        this.isShowDialog=isShowDialog;
        this.task=task;
    }


    public void startRequest(Map<String,String> map){
        if (!OtherUtils.isOpenNetwork(context)) {
            DialogUtils.createDialogToSettingNetWork(context);
            return;
        }
        showDialog(context);
        RequestUtil request=new RequestUtil();
        request.setCallback(this);

        try {
            request.request(task,map);
        } catch (Exception e) {
            e.printStackTrace();
            dismissDialog();
        }
    }

    /**
     * 显示网络进度提示
     *
     * @param context
     *            上下文环境
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
        Object resultBean=null;
        try {
            resultBean =new Gson().fromJson(result, task.getJsonType());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(requestCallback!=null){
            Map<String,Object> map=new HashMap<>();
            map.put("callback",requestCallback);
            map.put("result", resultBean);
            map.put("task", task);
//            requestCallback.response(task,resultBean);
            handler.sendMessage(handler.obtainMessage(SUCCESS,map));
        }

    }

    @Override
    public void onError(IOException e, Task task) {
        dismissDialog();
    }
}
