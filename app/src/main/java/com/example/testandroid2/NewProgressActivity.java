package com.example.testandroid2;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.testandroid2.bean.NewsBean;
import com.example.testandroid2.bean.ResultBean;
import com.example.testandroid2.net.common.RequestQueue;
import com.example.testandroid2.net.enums.Task;

import java.util.List;

import io.reactivex.Observer;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import timber.log.Timber;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/2/20 18:02
 */
@RuntimePermissions
public class NewProgressActivity extends BaseActivity {
    ActivityManager activityManager;
    String TAG="NewProgressActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_progress);
        getData();
//        initManager();
//        initLocationManager();
        NewProgressActivityPermissionsDispatcher.initLocationManagerWithCheck(this);
    }

    private void initManager(){
        activityManager= (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();//存放内存信息的对象
        activityManager.getMemoryInfo(memInfo);//传入参数，将获得数据保存在memInfo对象中
        long availMem = memInfo.availMem/1000000;//可用内存
        boolean isLowMem = memInfo.lowMemory;//是否达到最低内存
        long threshold = memInfo.threshold/1000000;//临界值，达到这个值，进程就要被杀死
        long totalMem = memInfo.totalMem/1000000;//总内存
        Timber.e("avail:" + availMem + ",isLowMem:" + isLowMem + ",threshold:" + threshold + ",totalMem:" + totalMem);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void initLocationManager(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> list = locationManager.getAllProviders();
        if (list != null && list.size() != 0) {
            for(String str : list){
                Log.i(TAG, "providers: " + str);
            }
        }
        LocationProvider locationProvider = locationManager.getProvider("gps");
        // 精度
        int accuracy = locationProvider.getAccuracy();
        Log.i(TAG, "accuracy: "+accuracy);
        //返回所需电量
        int powerRequirement = locationProvider.getPowerRequirement();
        Log.i(TAG, "powerRequirement: "+powerRequirement);
        //是否有使用费
        boolean hasMonetaryCost = locationProvider.hasMonetaryCost();
        Log.i(TAG, "hasMonetaryCost: "+hasMonetaryCost);
        //是否需要使用手机网络：基于手机基站的网络
        boolean requiresCell = locationProvider.requiresCell();
        Log.i(TAG, "requiresCell: "+requiresCell);
        //是否需要使用数据网络：因特莱特
        boolean requiresNetwork = locationProvider.requiresNetwork();
        Log.i(TAG, "requiresNetwork: "+requiresNetwork);
        //是否需要使用基于卫星定位的系统
        boolean requiresSatellite = locationProvider.requiresSatellite();
        Log.i(TAG, "requiresSatellite: "+requiresSatellite);
        //是否提供海拔信息
        boolean supportsAltitude = locationProvider.supportsAltitude();
        Log.i(TAG, "supportsAltitude:　"+supportsAltitude);
        //是否提供方位信息
        boolean supportsBearing = locationProvider.supportsBearing();
        Log.i(TAG, "supportsBearing： "+supportsBearing);
        //是否提供速度信息
        boolean supportsSpeed = locationProvider.supportsSpeed();
        Log.i(TAG, "supportsSpeed： "+supportsSpeed);
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationaleForRecord(final PermissionRequest request){
        new AlertDialog.Builder(this)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("不给", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("挑战需要录音权限，应用将要申请录音权限")
                .show();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForCamera() {
       toast("被拒绝");
    }
    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForCamera() {
        toast("不再提醒");
    }

    private void getData() {

//        RequestQueue.addToRxQueus(this,this,observable, true, Task.LATEST_NEWS, null);
        RequestQueue.addToRxQueus(this,this,observable, true, Task.TEST_HTTP_2, null);
    }

    @Override
    public void onNext(ResultBean resultBean) {
        super.onNext(resultBean);

        switch (resultBean.getTask()){
            case LATEST_NEWS:
                NewsBean bean= (NewsBean) resultBean.getData();
                Timber.e(bean.getDate());
                break;
            case TEST_REQUEST:

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        NewProgressActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
