package com.example.testandroid2.tools;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.testandroid2.BlueToothActivity;
import com.example.testandroid2.app.MyApplication;

import java.io.IOException;
import java.util.UUID;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/8/30 15:39
 */
public class ConnectBTThread extends Thread{
    private BluetoothDevice mBTDevice;
    private BluetoothSocket mBTSocket;
    private BluetoothAdapter mBTAdapter;
    public static UUID STR_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public ConnectBTThread (BluetoothDevice device,BluetoothAdapter adapter){
        this.mBTDevice = device;
        this.mBTAdapter = adapter;
        BluetoothSocket socketTemp = null;
        try{
            socketTemp = device.createInsecureRfcommSocketToServiceRecord(STR_UUID);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        Log.e("eee", "进入连接线程的构造方法....");
        mBTSocket = socketTemp;
        //设为全局的socket
        MyApplication.getInstance().mSocket = mBTSocket;
    }
    @Override
    public void run() {
        Log.e("eee","进入线程的run()...");

        try {

            //连接之前先断掉设备扫描
            mBTAdapter.cancelDiscovery();
            if (!mBTSocket.isConnected()) {
                //进行连接
                mBTSocket.connect();
                //使用反射进行连接
//                mBTSocket =(BluetoothSocket) mBTDevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mBTDevice,1);
//                mBTSocket.connect();
            }

        } catch (IOException ex) {
            Log.e("eee", "连接失败...");
            try {
                mBTSocket.close();
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        }
        Log.e("eee","run()运行结束...");
    }

    //关闭socket
    public void close(){
        try{
            mBTSocket.close();
        }catch (IOException ex){
            //...
        }
    }
}
