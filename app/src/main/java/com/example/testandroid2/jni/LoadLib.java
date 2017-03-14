package com.example.testandroid2.jni;


/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：16/5/26 11:21
 */
public class LoadLib {
    static {//载入lib的库的名字
        System.loadLibrary("mylibSo");
    }
    public native int addInt(int a, int b);//调用库里面的方法


    public native String stringFromJNI();//调用库里面的方法
}
