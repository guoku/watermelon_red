package com.guoku.guokuv4.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class NavigationActivity extends BaseActivity {

	@ViewInject(R.id.icon_1)
	ImageView img1;
	@ViewInject(R.id.icon_2)
	ImageView img2;
	@ViewInject(R.id.icon_3)
	ImageView img3;
	@ViewInject(R.id.icon_4)
	ImageView img4;

	@ViewInject(R.id.nav_image_btn)
	TextView btn;

	private int currIndex = -1;

	@ViewInject(R.id.navigation_vp)
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_navigation_main);

		final ArrayList<View> viewList = new ArrayList<View>();

		LayoutInflater inflater = LayoutInflater.from(this);
		viewList.add(getNavView(inflater, R.drawable.android1));
		viewList.add(getNavView(inflater, R.drawable.android2));
		viewList.add(getNavView(inflater, R.drawable.android3));
		viewList.add(getNavView(inflater, R.drawable.android4));

		picViewPagerAdapter pagerAdapter = new picViewPagerAdapter(viewList);
		viewPager.setAdapter(pagerAdapter);
		currIndex = 0;
		viewPager.setCurrentItem(currIndex);
		img1.setImageResource(R.drawable.ced_d);

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, WelAct.class);
				mContext.startActivity(intent);
				NavigationActivity.this.finish();
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				switch (arg0) {
				case 0:
					img1.setImageResource(R.drawable.ced_d);
					img2.setImageResource(R.drawable.ced_n);
					img3.setImageResource(R.drawable.ced_n);
					img4.setImageResource(R.drawable.ced_n);
					btn.setVisibility(View.GONE);
					break;
				case 1:
					img2.setImageResource(R.drawable.ced_d);
					img1.setImageResource(R.drawable.ced_n);
					img3.setImageResource(R.drawable.ced_n);
					img4.setImageResource(R.drawable.ced_n);
					btn.setVisibility(View.GONE);
					break;
				case 2:
					img3.setImageResource(R.drawable.ced_d);
					img2.setImageResource(R.drawable.ced_n);
					img1.setImageResource(R.drawable.ced_n);
					img4.setImageResource(R.drawable.ced_n);
					btn.setVisibility(View.GONE);
					break;
				case 3:
					img4.setImageResource(R.drawable.ced_d);
					img2.setImageResource(R.drawable.ced_n);
					img1.setImageResource(R.drawable.ced_n);
					img3.setImageResource(R.drawable.ced_n);
					btn.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
				currIndex = arg0;
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

	class picViewPagerAdapter extends PagerAdapter {

		private List<View> listViews;

		public picViewPagerAdapter(List<View> list) {
			listViews = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(listViews.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(listViews.get(position));
			return listViews.get(position);
		}
	}
}
