package com.guoku.guokuv4.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.ekwing.students.base.BaseActivity;
import com.guoku.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NavigationActivity extends BaseActivity {
	@ViewInject(R.id.navigation_vp)
	private ViewPager viewPager;
	private TextView btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_navigation_main);
		setupData();
	}

	private void setupData() {
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> viewList = new ArrayList<View>();
		viewList.add(getNavView(inflater, R.drawable.android1));
		viewList.add(getNavView(inflater, R.drawable.android2));
		viewList.add(getNavView(inflater, R.drawable.android3));
		viewList.add(getNavView(inflater, R.drawable.android4));
		btn = (TextView) findViewById(R.id.nav_image_btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, MainActivity2.class);
				mContext.startActivity(intent);
				NavigationActivity.this.finish();
			}
		});
		NavAdapter adapter = new NavAdapter();
		adapter.setDatas(viewList);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 == 3) {
					btn.setVisibility(View.VISIBLE);
				} else {
					btn.setVisibility(View.GONE);
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private View getNavView(LayoutInflater inflater, int imageId) {
		View normalView = inflater.inflate(R.layout.temp_navigation_normal,
				null);
		ImageView img = (ImageView) normalView.findViewById(R.id.nav_image_bg);
		img.setScaleType(ScaleType.CENTER_INSIDE);
		img.setBackgroundColor(Color.WHITE);
		img.setImageResource(imageId);
		return normalView;
	}

	private class NavAdapter extends PagerAdapter {
		private List<View> views = new ArrayList<View>();

		public void setDatas(List<View> views) {
			this.views.clear();
			this.views.addAll(views);
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1));
			return views.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
}
