package com.example.testandroid2;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testandroid2.adapter.MyPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity implements
		ViewPager.OnPageChangeListener {
	private ViewPager viewPager;
	private List<View> viewList;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			// getWindow().addFlags(
			// WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			// Window window = getWindow();
			// window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
			// | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			// window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
			// | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
			// | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			// window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			// window.setStatusBarColor(Color.TRANSPARENT);
			// window.setNavigationBarColor(Color.TRANSPARENT);
		}
		viewPager = (ViewPager) findViewById(R.id.vPager);
		viewList = new ArrayList<View>();
		initView();
		viewPager.setAdapter(new MyPagerAdapter(viewList));
		viewPager.setPageTransformer(true, new ParallaxTransformer(1.2f, 1.5f));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_pager, menu);
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

	private void initView() {
		for (int i = 0; i < 4; i++) {
			LinearLayout layout = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			layout.setLayoutParams(params);
			layout.setBackgroundColor(getResources().getColor(
					R.color.material_blue_700));
			layout.setId(i);
			layout.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			TextView tv = new TextView(this);
			tv.setTextColor(Color.WHITE);
			tv.setText(i + "");
			tv.setTextSize(50);
			tv.setGravity(Gravity.CENTER);
			if (i == 2) {
				layout.setBackgroundColor(getResources().getColor(
						R.color.material_blue_500));
				TextView tv1 = new TextView(this);
				tv1.setTextColor(Color.WHITE);
				tv1.setText(i + "fast");
				tv1.setTextSize(50);
				tv1.setGravity(Gravity.CENTER);
				layout.addView(tv1, params1);
			}

			layout.addView(tv, params1);
			viewList.add(layout);
		}

	}

	class ParallaxTransformer implements ViewPager.PageTransformer {

		float parallaxCoefficient;
		float distanceCoefficient;

		public ParallaxTransformer(float parallaxCoefficient,
				float distanceCoefficient) {
			this.parallaxCoefficient = parallaxCoefficient;
			this.distanceCoefficient = distanceCoefficient;
		}

		@Override
		public void transformPage(View page, float position) {
			switch (page.getId()) {
			case 0: {
				float scrollXOffset = page.getWidth() * parallaxCoefficient;

				// ...
				// layer is the id collection of views in this page
				View view = ((LinearLayout) page).getChildAt(0);
				if (view != null) {
					view.setTranslationX(scrollXOffset * position);
				}
				scrollXOffset *= distanceCoefficient;
			}
				break;
			case 1: {
				float scrollXOffset = page.getWidth();
				float scrollYOffset = page.getHeight();

				// ...
				// layer is the id collection of views in this page
				View view = ((LinearLayout) page).getChildAt(0);
				if (view != null) {
					view.setTranslationY(scrollYOffset * position);
					view.setTranslationX(-scrollXOffset * position);
				}
				// scrollXOffset *= distanceCoefficient;
			}
				break;
			case 2: {
				float scrollXOffset = page.getWidth() * 0.8f;
				float scrollYOffset = page.getHeight();

				// ...
				// layer is the id collection of views in this page
				View view = ((LinearLayout) page).getChildAt(0);
				if (view != null) {
					// view.setTranslationY(scrollYOffset * position);
					view.setTranslationX(scrollXOffset * position);
				}
			}
				break;
			case 3: {

			}
				break;
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}
}
