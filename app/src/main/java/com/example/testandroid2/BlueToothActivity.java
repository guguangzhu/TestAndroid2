package com.example.testandroid2;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.testandroid2.app.MyApplication;
import com.example.testandroid2.tools.ClsUtils;
import com.example.testandroid2.tools.ConnectBTThread;
import com.example.testandroid2.tools.ConnectedBTThread;

import org.w3c.dom.Text;

import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class BlueToothActivity extends BaseActivity {
    public static final int REQUEST_ENABLE_BT = 111;  //打开蓝牙
    public static final int MESSAGE_FOUND = 221; //发现设备
    public static final int MESSAGE_READ = 222; //读取信息
    public static final int MESSAGE_CONNECTED = 223; //连接成功
    public static final int MESSAGE_NOT_FOUND = 224; //没有发现设备
    public static final int MESSAGE_CONNECT_FAIL = 225; //连接失败
    public static final int MESSAGE_BONDED = 226; //绑定成功
    public static final String ACTION_CONNECT_FAIL="com.bage.action.ACTION_CONNECT_FAIL";

    private String btAddress="00:0E:0E:0C:BC:F8"; //蓝牙地址

    BluetoothAdapter mBluetoothAdapter;
    @InjectView(R.id.button8)
    Button button8;
    @InjectView(R.id.textView4)
    TextView textView4;
    private boolean isConnected;
    ConnectedBTThread connectedThread;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_READ:
                    byte[] buffer = (byte[]) msg.obj;
                    toast(buffer.toString());
                    break;
//                case MESSAGE_FOUND:
//                    BluetoothDevice device = (BluetoothDevice) msg.obj;
//                    new ConnectBTThread(device, mBluetoothAdapter, mHandler).start();
//                    textView4.setText("找到设备----"+device.getAddress());
//                    break;
                case MESSAGE_CONNECT_FAIL:
                    isConnected = false;
                    toast("连接失败");
                    textView4.setText("连接失败");
                    break;
//                case MESSAGE_BONDED:{
//                    BluetoothDevice device1 = (BluetoothDevice) msg.obj;
//                    new ConnectBTThread(device1, mBluetoothAdapter, mHandler).start();
//                }
//                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);
        ButterKnife.inject(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            return;
        }
        BlueToothActivityPermissionsDispatcher.checkBlueToothWithCheck(this);
        registerListener();
//        checkBlueTooth();


    }

    private void registerListener() {
        IntentFilter filter = new IntentFilter(ACTION_CONNECT_FAIL);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
    }

    private void unregisterListener() {
        unregisterReceiver(mReceiver);
    }

    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void checkBlueTooth() {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if(!checkBondedDevice(btAddress))
                mBluetoothAdapter.startDiscovery();
        }
    }

    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showRationaleForRecord(final PermissionRequest request) {
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

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showDeniedForCamera() {
        toast("被拒绝");
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION)
    void showNeverAskForCamera() {
        toast("不再提醒");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    if(!checkBondedDevice(btAddress))
                        mBluetoothAdapter.startDiscovery();
                }
                break;
        }
    }

    private boolean checkBondedDevice(String address){
        Set<BluetoothDevice> bonedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device:bonedDevices){
            if(TextUtils.equals(device.getAddress(),address)){
                mHandler.obtainMessage(MESSAGE_BONDED, device).sendToTarget();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        BlueToothActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterListener();
        if(connectedThread!=null){
            connectedThread.cancel();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
//                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//                Timber.e(device.getName() + "\n" + device.getAddress());
                Log.e("BroadcastReceiver", device.getName() + "\n" + device.getAddress());
                if (TextUtils.equals(MyApplication.getInstance().btName,device.getAddress())) {
//                    mHandler.obtainMessage(MESSAGE_FOUND, device).sendToTarget();
//                    mHandler.sendEmptyMessage(MESSAGE_FOUND);
                    textView4.setText("连接成功");
                }

            }else if(ACTION_CONNECT_FAIL.equals(action)){
                textView4.setText("连接失败");
            }
        }
    };

    @OnClick(R.id.button8)
    public void onViewClicked() {
        if (isConnected) {
            toast("发送指令");
            connectedThread.write(new byte[]{0, 1});
        }
    }


}
