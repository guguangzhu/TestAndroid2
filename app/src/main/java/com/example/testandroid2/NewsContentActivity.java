package com.example.testandroid2;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.testandroid2.adapter.GridAdapter;
import com.example.testandroid2.bean.NewsBean;
import com.example.testandroid2.bean.NewsContengBean;
import com.example.testandroid2.net.common.RequestCallback;
import com.example.testandroid2.net.common.RequestQueue;
import com.example.testandroid2.net.enums.Task;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

public class NewsContentActivity extends AppCompatActivity implements RequestCallback {
    private NewsBean.StoriesEntity entity;
    private SimpleDraweeView draweeView;
    private WebView webView;
    private NewsContengBean newsBean;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        draweeView = (SimpleDraweeView) findViewById(R.id.backdrop);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        entity = (NewsBean.StoriesEntity) getIntent().getSerializableExtra("news");
        if (entity != null) {
            setTitle();
        }
    }

    private void setTitle() {
        getSupportActionBar().setTitle(entity.getTitle());
        if (entity.getImages() != null) {
            draweeView.setImageURI(Uri.parse(entity.getImages().get(0)));
//            getData(entity.getId());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
//            ActivityCompat.finishAfterTransition(this);
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void getData(int newsId) {
        RequestQueue.addToQueue(this, this, true, Task.LATEST_NEWS, null);
    }

    private void parseLatestJson(String responseString) {
        Gson gson = new Gson();
        newsBean = gson.fromJson(responseString, NewsContengBean.class);
        webView.getSettings().setDefaultTextEncodingName("UTF -8") ;
//        webView.loadData(newsBean.getBody(), "text/html", "UTF-8") ;
//        webView.loadData(newsBean.getBody(), "text/html", "gbk");
        webView.loadData(newsBean.getBody(), "text/html; charset=UTF-8", null);//这种写法可以正确解码

    }

    @Override
    public void response(Task task, Object result) {

    }
}
