package com.example.testandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.guge.myimagepicker.ui.GalleryActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 类描述：
 * 创建人：G.G.Z
 * 创建时间：2017/3/16 16:16
 */
public class ImagePickerActivity extends BaseActivity {
    public static final int CHOOSE_PIC=111;
    @InjectView(R.id.button6)
    Button button6;
    @InjectView(R.id.button7)
    Button button7;
    @InjectView(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button6, R.id.button7})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button6:
                Intent intent = new Intent(this, GalleryActivity.class);
                startActivityForResult(intent, CHOOSE_PIC);
                break;
            case R.id.button7:
                break;
        }
    }
}
