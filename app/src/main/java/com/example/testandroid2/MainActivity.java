package com.example.testandroid2;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.testandroid2.adapter.HomeAdapter;
import com.example.testandroid2.adapter.HomeAdapter.OnItemClickLitener;
import com.example.testandroid2.bean.NewsBean;
import com.example.testandroid2.tools.Constant;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements
        OnItemClickLitener {
    private Context context;
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private Toolbar mToolbar;

    private NewsBean newsBean;
    private HomeAdapter adapter;
    MixpanelAPI mixpanel;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (VERSION.SDK_INT > VERSION_CODES.KITKAT) {
            // 透明状态栏
            // getWindow().addFlags(
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            // getWindow().addFlags(
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        context = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextAppearance(context,getResources().getColor(R.color.white));
        // App Logo
        mToolbar.setLogo(R.drawable.ic_launcher);
        // toolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle("Rocko");// 标题的文字需在setSupportActionBar之前，不然会无效
        // toolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("tab1"));
        tabLayout.addTab(tabLayout.newTab().setText("tab2"));
        tabLayout.addTab(tabLayout.newTab().setText("tab3"));
        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new HomeAdapter(mDatas, context);
        mRecyclerView.setAdapter(adapter
                .setOnItemClickLitener(this));

        //mRecyclerView.setAdapter(adapter).setOnItemClickLitener(this));
        initMixPanel();
        addAction();

    }

    private void initMixPanel(){
        String projectToken = "65e6f8c37840a6e49bac404d60cbbe39"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
        mixpanel = MixpanelAPI.getInstance(this, projectToken);
    }

    private void addAction(){
        try {
            JSONObject props = new JSONObject();
            props.put("Gender", "Male");
            props.put("Logged in", false);
            mixpanel.track("MainActivity - onCreate called", props);
        } catch (JSONException e) {
            Log.e("MYAPP", "Unable to add properties to JSONObject", e);
        }
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i == 0) {
                mDatas.add("TextInput");
                continue;
            }
            if (i == 1) {
                mDatas.add("CollapsingActivity");
                continue;
            }
            if (i == 2) {
                mDatas.add("ViewPagerActivity");
                continue;
            }
            if (i == 3) {
                mDatas.add("DialogPlusActivity");
                continue;
            }
            if (i == 4) {
                mDatas.add("ScrollingActivity");
                continue;
            }
            if (i == 5) {
                mDatas.add("AutoHideTitleActivity");
                continue;
            }
            if (i == 6) {
                mDatas.add("RadarActivity");
                continue;
            }
            if (i == 7) {
                mDatas.add("TagCloudActivity");
                continue;
            }
            if (i == 8) {
                mDatas.add("LightSensorActivity");
                continue;
            }
            if (i == 9) {
                mDatas.add("DragActivity");
                continue;
            }
            if (i == 10) {
                mDatas.add("ProgressActivity");
                continue;
            }
            if (i == 11) {
                mDatas.add("HttpsActivity");
                continue;
            }
            if (i == 12) {
                mDatas.add("UUIDActivity");
                continue;
            }
            if (i == 13) {
                mDatas.add("ViewAnimationActivity");
                continue;
            }

            if (i == 14) {
                mDatas.add("SeekBarActivity");
                continue;
            }

            if (i == 15) {
                mDatas.add("NewProgressActivity");
                continue;
            }
            if (i == 16) {
                mDatas.add("ImagePickerActivity");
                continue;
            }


            mDatas.add("" + i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnText(View v) {
        startActivity(new Intent(this, TextInputActivity.class));
    }

    @Override
    public void onItemClick(View view, int position) {
        // TODO Auto-generated method stub
        Intent intent = null;
        switch (position) {
            case 0: {
                intent = new Intent(this, TextInputActivity.class);
//			startActivity(new Intent(this, TextInputActivity.class));
            }
            break;
            case 1: {
                intent = new Intent(this, CollapsingActivity.class);
//			startActivity(new Intent(this, CollapsingActivity.class));
            }
            break;
            case 2: {
                intent = new Intent(this, ViewPagerActivity.class);
//			startActivity(new Intent(this, ViewPagerActivity.class));
            }
            break;
            case 3: {
                intent = new Intent(this, DialogPlusActivity.class);
//			startActivity(new Intent(this, DialogPlusActivity.class));
            }
            break;
            case 4: {
                intent = new Intent(this, ScrollingActivity.class);
//			startActivity(new Intent(this, ScrollingActivity.class));
            }
            break;
            case 5: {
                intent = new Intent(this, AutoHideTitleActivity.class);
//			startActivity(new Intent(this, ScrollingActivity.class));
            }
            break;
            case 6: {
                intent = new Intent(this, RadarActivity.class);
            }
            break;
            case 7: {
                intent = new Intent(this, TagCloudActivity.class);
            }
            break;

            case 8: {
                intent = new Intent(this, LightSensorActivity.class);
            }
            break;
            case 9: {
                intent = new Intent(this, DragActivity.class);
            }
            break;
            case 10: {
                intent = new Intent(this, ProgressActivity.class);
            }
            break;
            case 11: {
                intent = new Intent(this, HttpsActivity.class);
            }
            break;
            case 12: {
                intent = new Intent(this, UUIDActivity.class);
            }
            break;
            case 13: {
                intent = new Intent(this, ViewAnimationActivity.class);
            }
            break;
            case 14: {
                intent = new Intent(this, SeekBarActivity.class);
            }
            break;
            case 15: {
                intent = new Intent(this, NewProgressActivity.class);
            }
            break;
            case 16: {
                intent = new Intent(this, ImagePickerActivity.class);
            }
            break;

        }
        if (intent != null && position == 1 || position == 4) {
            int[] startingLocation = new int[2];
            view.getLocationOnScreen(startingLocation);
            startingLocation[0] += view.getWidth() / 2;
            intent.putExtra(Constant.START_LOCATION, startingLocation);
            startActivity(intent);
            overridePendingTransition(0, 0);
            return;
        }

        if (intent != null) {
            int[] startingLocation = new int[2];
            view.getLocationOnScreen(startingLocation);
            startingLocation[0] += view.getWidth() / 2;
            intent.putExtra(Constant.START_LOCATION, startingLocation);
            scaleUpAnimation(intent, view);
        }
    }

    private void scaleUpAnimation(Intent intent, View view) {
        //让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                        (int) view.getWidth() / 2, (int) view.getHeight() / 2, //拉伸开始的坐标
                        0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        startNewAcitivity(intent, options);

    }

    private void startNewAcitivity(Intent intent, ActivityOptionsCompat options) {
//		Intent intent = new Intent(this,DetailActivity.class);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    public void onItemLongClick(View view, int position) {
        // TODO Auto-generated method stub

    }


}
