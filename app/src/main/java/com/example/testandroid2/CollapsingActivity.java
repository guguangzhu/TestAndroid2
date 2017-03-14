package com.example.testandroid2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.example.testandroid2.adapter.NewsAdapter.OnItemClickLitener;
import com.example.testandroid2.adapter.NewsAdapter;
import com.example.testandroid2.bean.NewsBean;
import com.example.testandroid2.decoration.DividerItemDecoration;
import com.example.testandroid2.net.common.RequestCallback;
import com.example.testandroid2.net.common.RequestQueue;
import com.example.testandroid2.net.enums.Task;
import com.example.testandroid2.tools.Constant;
import com.example.testandroid2.widget.RevealBackgroundView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

public class CollapsingActivity extends AppCompatActivity implements
		OnItemClickLitener, RevealBackgroundView.OnStateChangeListener,RequestCallback{
	private Context context;
	private RecyclerView mRecyclerView;
	private RevealBackgroundView vRevealBackground;
	private AppBarLayout mAppBarLayout;
	private NewsBean newsBean;
	private NewsAdapter adapter;
	private SimpleDraweeView draweeView;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_collapsing);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
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
		Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		vRevealBackground = (RevealBackgroundView) findViewById(R.id.revealBackgroundView);
		draweeView = (SimpleDraweeView) findViewById(R.id.backdrop);
		mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
		mAppBarLayout.setVisibility(View.INVISIBLE);
		// 使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
		CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		mCollapsingToolbarLayout.setTitle("Collapsing");
		// 通过CollapsingToolbarLayout修改字体颜色
		mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);// 设置还没收缩时状态下字体颜色
		mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);// 设置收缩后Toolbar上字体的颜色
		mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		// mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
		//设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		//添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(
				this, DividerItemDecoration.HORIZONTAL_LIST));
		setupRevealBackground(savedInstanceState);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}


	private void setupRevealBackground(Bundle savedInstanceState) {
		vRevealBackground.setOnStateChangeListener(this);
		if (savedInstanceState == null) {
			final int[] startingLocation = getIntent().getIntArrayExtra(Constant.START_LOCATION);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.collapsing, menu);
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

		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(View view, int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemLongClick(View view, int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStateChange(int state) {
		if (RevealBackgroundView.STATE_FINISHED == state) {
			mAppBarLayout.setVisibility(View.VISIBLE);
//			setStatusBarColor(Color.TRANSPARENT);
			getData();
		}
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
		adapter = new NewsAdapter(newsBean.getStories(), context);
		adapter.setOnItemClickLitener(this);
		mRecyclerView.setAdapter(adapter);
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Collapsing Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.testandroid2/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Collapsing Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://com.example.testandroid2/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
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
				adapter = new NewsAdapter(newsBean.getStories(), context);
				adapter.setOnItemClickLitener(this);
				mRecyclerView.setAdapter(adapter);
			}
				break;
		}
	}
}
