package com.example.testandroid2;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.testandroid2.adapter.GridAdapter;
import com.example.testandroid2.bean.NewsBean;
import com.google.gson.Gson;

public class AutoHideTitleActivity extends AppCompatActivity implements GridAdapter.OnItemClickLitener{
    private Toolbar mToolbar;
    private Context context;
    private RecyclerView mRecyclerView;
    private NewsBean newsBean;
    private GridAdapter adapter;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_hide_title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
//			getWindow().addFlags(
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        context = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.drawable.ic_launcher);
        // toolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle("AutoHideTitleActivity");// 标题的文字需在setSupportActionBar之前，不然会无效
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        getData();
    }

//    private void getData() {
//        OkHttpUtils
//                .get()
//                .url("http://news-at.zhihu.com/api/4/news/latest")
//                .addParams("username", "hyman")
//                .addParams("password", "123")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        parseLatestJson(response);
//                    }
//                });
//    }
//
//    private void parseLatestJson(String responseString) {
//        Gson gson = new Gson();
//        newsBean = gson.fromJson(responseString, NewsBean.class);
//
//        if (newsBean == null)
//            return;
////        if (newsBean.getStories() != null && newsBean.getStories().get(0).getImages() != null) {
////            draweeView.setImageURI(Uri.parse(newsBean.getStories().get(0).getImages().get(0)));
////        }
//        adapter = new GridAdapter(newsBean.getStories(), context);
//        adapter.setOnItemClickLitener(this);
//        mRecyclerView.setAdapter(adapter);
//    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
