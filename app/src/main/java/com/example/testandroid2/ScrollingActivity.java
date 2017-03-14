package com.example.testandroid2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.example.testandroid2.adapter.GridAdapter;
import com.example.testandroid2.adapter.NewsAdapter;
import com.example.testandroid2.bean.NewsBean;
import com.example.testandroid2.decoration.DividerItemDecoration;
import com.example.testandroid2.net.common.RequestCallback;
import com.example.testandroid2.net.common.RequestQueue;
import com.example.testandroid2.net.enums.Task;
import com.example.testandroid2.tools.Constant;
import com.example.testandroid2.widget.RevealBackgroundView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class ScrollingActivity extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener,GridAdapter.OnItemClickLitener,RequestCallback {
    private RevealBackgroundView vRevealBackground;
    private CoordinatorLayout coordinatorLayout;
    private SimpleDraweeView draweeView;
    private Context context;
    private RecyclerView mRecyclerView;
    private NewsBean newsBean;
    private GridAdapter adapter;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        draweeView= (SimpleDraweeView) findViewById(R.id.backdrop);
        vRevealBackground = (RevealBackgroundView) findViewById(R.id.revealBackgroundView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        coordinatorLayout.setVisibility(View.INVISIBLE);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setTitle("Scrolling");
        // 通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);// 设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);// 设置收缩后Toolbar上字体的颜色
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(
//                this, DividerItemDecoration.VERTICAL_LIST));
        setupRevealBackground(savedInstanceState);
    }



    private void setupRevealBackground(Bundle savedInstanceState) {
        vRevealBackground.setOnStateChangeListener(this);
        if (savedInstanceState == null) {
            final int[] startingLocation = getIntent().getIntArrayExtra(Constant.START_LOCATION);
//            if(startingLocation==null){
//                startingLocation= new int[]{getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getWidth()};
//            }
            vRevealBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    vRevealBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                    vRevealBackground.startFromLocation(startingLocation);
                    return true;
                }
            });
        } else {
            vRevealBackground.setToFinishedFrame();
        }
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            coordinatorLayout.setVisibility(View.VISIBLE);
            getData();
        }
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

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        RequestQueue.addToQueue(this, this, true, Task.LATEST_NEWS, null);
    }

    private void parseLatestJson(String responseString) {
        Gson gson = new Gson();
        newsBean = gson.fromJson(responseString, NewsBean.class);

        if (newsBean == null)
            return;
        if (newsBean.getStories() != null && newsBean.getStories().get(0).getImages() != null) {
            draweeView.setImageURI(Uri.parse(newsBean.getStories().get(0).getImages().get(0)));
        }
        adapter = new GridAdapter(newsBean.getStories(), context);
        adapter.setOnItemClickLitener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(this,NewsContentActivity.class);
        intent.putExtra("news", newsBean.getStories().get(position));
        scaleUpAnimation(intent,view);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


    private void scaleUpAnimation(Intent intent,View view) {
        //让新的Activity从一个小的范围扩大到全屏
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
                        (int)view.getWidth()/2, (int)view.getHeight()/2, //拉伸开始的坐标
                        0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
        startNewAcitivity(intent, options);

    }

    private void startNewAcitivity(Intent intent,ActivityOptionsCompat options) {
//		Intent intent = new Intent(this,DetailActivity.class);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    public void response(Task task, Object result) {
        switch (task){
            case LATEST_NEWS:{
                newsBean= (NewsBean) result;
                if (newsBean == null)
                    return;
                if (newsBean.getStories() != null && newsBean.getStories().get(0).getImages() != null) {
                    draweeView.setImageURI(Uri.parse(newsBean.getStories().get(0).getImages().get(0)));
                }
                adapter = new GridAdapter(newsBean.getStories(), context);
                adapter.setOnItemClickLitener(this);
                mRecyclerView.setAdapter(adapter);
            }
            break;
        }
    }
}
