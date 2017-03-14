package com.guge.myimagepicker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.guge.myimagepicker.ImageDataSource;
import com.guge.myimagepicker.R;
import com.guge.myimagepicker.bean.ImageFolder;

import java.util.List;

public class GalleryActivity extends ImageBaseActivity implements ImageDataSource.OnImagesLoadedListener{

    private GridView mGridView;  //图片展示控件
    private View mFooterBar;     //底部栏
    private Button mBtnOk;       //确定按钮
    private Button mBtnDir;      //文件夹切换按钮
    private Button mBtnPre;      //预览按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {

    }
}
