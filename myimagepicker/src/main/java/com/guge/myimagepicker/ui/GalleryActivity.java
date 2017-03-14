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

public class GalleryActivity extends ImageBaseActivity implements ImageDataSource.OnImagesLoadedListener, View.OnClickListener{

    private GridView mGridView;  //图片展示控件
    private View mFooterBar;     //底部栏
    private Button mBtnOk;       //确定按钮
    private Button mBtnDir;      //文件夹切换按钮
    private Button mBtnPre;      //预览按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        findViewById(R.id.btn_back).setOnClickListener(this);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnOk.setOnClickListener(this);
        mBtnDir = (Button) findViewById(R.id.btn_dir);
        mBtnDir.setOnClickListener(this);
        mBtnPre = (Button) findViewById(R.id.btn_preview);
        mBtnPre.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.gridview);
        mFooterBar = findViewById(R.id.footer_bar);
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {

    }

    @Override
    public void onClick(View v) {

    }
}
