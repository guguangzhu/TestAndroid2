package com.example.testandroid2.net.common;

import android.app.VoiceInteractor;
import android.util.Log;

import com.example.testandroid2.net.config.HttpConfig;
import com.example.testandroid2.net.enums.Task;
import com.example.testandroid2.tools.StringUtils;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

/**
 * Created by Guge on 16/1/26.
 */
public class RequestUtil {
    private NetCallback callback;

    private static OkHttpClient mOkHttpClient;


    public NetCallback getCallback() {
        return callback;
    }

    public void setCallback(NetCallback callback) {
        this.callback = callback;
    }

    public RequestUtil() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//                .connectTimeout(connectTime, TimeUnit.SECONDS)
//                .readTimeout(readTime, TimeUnit.SECONDS)
//                .writeTimeout(writeTime, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(false);
        mOkHttpClient = builder.build();
    }


    public void request(final Task task, Map<String, String> params) throws Exception {

        final Request request;
        if(params==null)
            params=new HashMap<>();

        if (task.getType() == HttpConfig.GET) {    //GET
            String queryUrl= StringUtils.getQueryUrl(task.getUrl(),params);
            Log.e("request","请求地址："+queryUrl);
            request = new Request.Builder()
                    .url(queryUrl)
                    .build();
        } else {   //POST
            FormBody.Builder builder = new FormBody.Builder();
            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
//                params.put((String) key, (String) value);
                builder.add((String) key, (String) value);
            }
            request = new Request.Builder()
                    .url(task.getUrl())
                    .post(builder.build())
                    .build();

        }
        DeviceBandwidthSampler.getInstance().startSampling();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DeviceBandwidthSampler.getInstance().stopSampling();
                Log.e("request","错误："+e.toString());
                if (callback != null) {
                    callback.onError(e, task);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DeviceBandwidthSampler.getInstance().stopSampling();
                String result=response.body().string();
                Timber.e("返回结果：",result);
                if (callback != null) {
                    callback.onSuccess(result, task);
                }
            }

        });
//        mOkHttpClient.cancel()
    }

}
