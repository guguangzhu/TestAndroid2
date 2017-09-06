package com.example.testandroid2.tools;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.testandroid2.BlueToothActivity;
import com.example.testandroid2.app.MyApplication;

import java.io.IOException;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/8/29 18:02
 */
public class BluetoothReceiver extends BroadcastReceiver {

    public BluetoothReceiver() {

    }

    //广播接收器，当远程蓝牙设备被发现时，回调函数onReceiver()会被执行
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction(); //得到action
        Log.e("action1=", action);
        BluetoothDevice btDevice=null;  //创建一个蓝牙device对象
        // 从Intent中获取设备对象
        btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if(!TextUtils.equals(btDevice.getName(), MyApplication.getInstance().btName))
            return;

        if(BluetoothDevice.ACTION_FOUND.equals(action)){  //发现设备
            Log.e("发现设备:", "["+btDevice.getName()+"]"+":"+btDevice.getAddress());

                if (btDevice.getBondState() == BluetoothDevice.BOND_NONE) {

                    Log.e("ywq", "attemp to bond:"+"["+btDevice.getName()+"]");
                    try {
                        //通过工具类ClsUtils,调用createBond方法
                        ClsUtils.createBond(btDevice.getClass(), btDevice);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(btDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    startConnect(btDevice);
                }
        }else if(action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) //再次得到的action，会等于PAIRING_REQUEST
        {
            Log.e("action2=", action);
                try {

                    //1.确认配对
                    ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
                    //2.终止有序广播
                    Log.i("order...", "isOrderedBroadcast:"+isOrderedBroadcast()+",isInitialStickyBroadcast:"+isInitialStickyBroadcast());
                    abortBroadcast();//如果没有将广播终止，则会出现一个一闪而过的配对框。
                    //3.调用setPin方法进行配对...
                    boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, MyApplication.getInstance().pin);

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }else if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){  //配对状态
            int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
            switch (state) {
                case BluetoothDevice.BOND_NONE:
                    Log.e("aaa", "BOND_NONE 删除配对");
                    if(TextUtils.equals(MyApplication.getInstance().pin,"1234")){ //如果ping1234 绑定失败尝试使用0000
                        MyApplication.getInstance().pin="0000";
                        try {
                            //通过工具类ClsUtils,调用createBond方法
                            ClsUtils.createBond(btDevice.getClass(), btDevice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        Log.e("aaa", "配对失败");
                        context.sendBroadcast(new Intent(BlueToothActivity.ACTION_CONNECT_FAIL));
                    }
                    break;
                case BluetoothDevice.BOND_BONDING:
                    Log.e("aaa", "BOND_BONDING 正在配对");
                    break;
                case BluetoothDevice.BOND_BONDED:
                    Log.e("aaa", "BOND_BONDED 配对成功");
                    startConnect(btDevice); //开始连接
                    break;
            }
        }else if(action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
            MyApplication.getInstance().isConnected=false;
            if(MyApplication.getInstance().mSocket!=null){
                try {
                    MyApplication.getInstance().mSocket.close();
                    MyApplication.getInstance().mSocket=null;
                } catch (IOException e) {
                    MyApplication.getInstance().mSocket=null;
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * 开始连接
     * @param btDevice
     */
    private void startConnect(BluetoothDevice btDevice){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            // Device does not support Bluetooth
            mBluetoothAdapter.cancelDiscovery();
        }
        new ConnectBTThread(btDevice, mBluetoothAdapter).start();
    }
}