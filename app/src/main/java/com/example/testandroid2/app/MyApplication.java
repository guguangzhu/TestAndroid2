package com.example.testandroid2.app;

import android.app.Application;

import com.example.testandroid2.tools.GlideImageLoader;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.guge.myimagepicker.MyImagePicker;
import com.guge.myimagepicker.view.CropImageView;

import timber.log.Timber;

/**
 * Created by Guge on 16/1/11.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Timber.plant(new Timber.DebugTree());
        initImagePicker();
    }

    private void initImagePicker(){
        MyImagePicker imagePicker = MyImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

}
