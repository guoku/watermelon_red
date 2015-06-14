///*
// * Copyright (C) 2011 The Android Open Source Project
// * Copyright (C) 2011 Jake Wharton
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.ekwing.students.customview;
//
//import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
//import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.HorizontalScrollView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.ekwing.students.config.Constant;
//import com.ekwing.students.config.Logger;
//import com.ekwing.students.entity.WeekBean;
//import com.guoku.R;
//import com.umeng.analytics.MobclickAgent;
//
///**
// * This widget implements the dynamic action bar tab behavior that can change
// * across different configurations or circumstances.
// */
//public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {
//	/** Title text used when no title is provided by the adapter. */
//	private static final CharSequence EMPTY_TITLE = "";
//
//	/**
//	 * Interface for a callback when the selected tab has been reselected.
//	 */
//	public interface OnTabReselectedListener {
//		/**
//		 * Callback when the selected tab has been reselected.
//		 * 
//		 * @param position
//		 *            Position of the current center item.
//		 */
//		void onTabReselected(int position);
//	}
//
//	private Runnable mTabSelector;
//
//	private final OnClickListener mTabClickListener = new OnClickListener() {
//		public void onClick(View view) {
//			View tabView = (View) view;
//			tabView.setBackgroundResource(R.drawable.calculate_bottom_tab_normal);
//			final int oldSelected = mViewPager.getCurrentItem();
//			final int newSelected = (Integer) tabView.getTag();
//			mViewPager.setCurrentItem(newSelected);
//			if (oldSelected == newSelected && mTabReselectedListener != null) {
//				mTabReselectedListener.onTabReselected(newSelected);
//			}
//		}
//	};
//
//	private final IcsLinearLayout mTabLayout;
//
//	private ViewPager mViewPager;
//	private ViewPager.OnPageChangeListener mListener;
//
//	private int mMaxTabWidth;
//	private int mSelectedTabIndex;
//
//	private OnTabReselectedListener mTabReselectedListener;
//
//	public TabPageIndicator(Context context) {
//		this(context, null);
//	}
//
//	public TabPageIndicator(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		setHorizontalScrollBarEnabled(false);
//
//		mTabLayout = new IcsLinearLayout(context, R.attr.vpiTabPageIndicatorStyle);
//		addView(mTabLayout, new ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT));
//	}
//
//	public void setOnTabReselectedListener(OnTabReselectedListener listener) {
//		mTabReselectedListener = listener;
//	}
//
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
//		setFillViewport(lockedExpanded);
//
//		final int childCount = mTabLayout.getChildCount();
//		if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
//			if (childCount > 2) {
//				mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
//			} else {
//				mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
//			}
//		} else {
//			mMaxTabWidth = -1;
//		}
//
//		final int oldWidth = getMeasuredWidth();
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		final int newWidth = getMeasuredWidth();
//
//		if (lockedExpanded && oldWidth != newWidth) {
//			// Recenter the tab display if we're at a new (scrollable) size.
//			setCurrentItem(mSelectedTabIndex);
//		}
//	}
//
//	private void animateToTab(final int position) {
//		final View tabView = mTabLayout.getChildAt(position);
//		if (mTabSelector != null) {
//			removeCallbacks(mTabSelector);
//		}
//		mTabSelector = new Runnable() {
//			public void run() {
//				final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
//				smoothScrollTo(scrollPos, 0);
//				mTabSelector = null;
//			}
//		};
//		post(mTabSelector);
//	}
//
//	@Override
//	public void onAttachedToWindow() {
//		super.onAttachedToWindow();
//		if (mTabSelector != null) {
//			// Re-post the selector we saved
//			post(mTabSelector);
//		}
//	}
//
//	@Override
//	public void onDetachedFromWindow() {
//		super.onDetachedFromWindow();
//		if (mTabSelector != null) {
//			removeCallbacks(mTabSelector);
//		}
//	}
//
//	private void addTab(int index, WeekBean bean, int iconResId) {
//		View view = LayoutInflater.from(getContext()).inflate(R.layout.week_item, null);
//		Logger.e("dddddd", "addtab---------------------->" + Constant.screenW);
//		LayoutParams lp = new LayoutParams(Constant.screenW / 7, android.widget.AbsListView.LayoutParams.MATCH_PARENT);
//		view.setLayoutParams(lp);
//		final TextView tv1 = (TextView) view.findViewById(R.id.week_item_up);
//		final TextView tv2 = (TextView) view.findViewById(R.id.week_item_down);
//		ImageView iv_icon = (ImageView) view.findViewById(R.id.hw_main_iv_icon);
//		tv1.setText(bean.getWeeks());
//		tv2.setText(bean.getDays());
//		view.setFocusable(true);
//		view.setTag(index);
//		String status = bean.getStatus();
//		// Logger.e(VIEW_LOG_TAG, "status------------------->" + status);
//		if ("0".equals(status)) {
//			iv_icon.setVisibility(View.INVISIBLE);
//		} else if ("1".equals(status)) {
//			iv_icon.setVisibility(View.VISIBLE);
//			iv_icon.setBackgroundResource(R.drawable.red_circle_bg);
//		} else if ("2".equals(status)) {
//			iv_icon.setVisibility(View.VISIBLE);
//			iv_icon.setBackgroundResource(R.drawable.green_circle_bg);
//		}
//
//		view.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View view) {
//				MobclickAgent.onEvent(getContext(), "ykxs005");
//				View tabView = (View) view;
//				// tabView.setBackgroundResource(R.drawable.calculate_bottom_tab_normal);
//				final int oldSelected = mViewPager.getCurrentItem();
//				final int newSelected = (Integer) tabView.getTag();
//				mViewPager.setCurrentItem(newSelected);
//				if (oldSelected == newSelected && mTabReselectedListener != null) {
//					mTabReselectedListener.onTabReselected(newSelected);
//				}
//
//			}
//		});
//
//		mTabLayout.addView(view);
//	}
//
//	@Override
//	public void onPageScrollStateChanged(int arg0) {
//		if (mListener != null) {
//			mListener.onPageScrollStateChanged(arg0);
//		}
//	}
//
//	@Override
//	public void onPageScrolled(int arg0, float arg1, int arg2) {
//		if (mListener != null) {
//			mListener.onPageScrolled(arg0, arg1, arg2);
//		}
//	}
//
//	@Override
//	public void onPageSelected(int arg0) {
//		setCurrentItem(arg0);
//		if (mListener != null) {
//			mListener.onPageSelected(arg0);
//		}
//	}
//
//	@Override
//	public void setViewPager(ViewPager view) {
//		if (mViewPager == view) {
//			return;
//		}
//		if (mViewPager != null) {
//			mViewPager.setOnPageChangeListener(null);
//		}
//		final PagerAdapter adapter = view.getAdapter();
//		if (adapter == null) {
//			throw new IllegalStateException("ViewPager does not have adapter instance.");
//		}
//		mViewPager = view;
//		view.setOnPageChangeListener(this);
//		notifyDataSetChanged();
//	}
//
//	public void notifyDataSetChanged(ArrayList<WeekBean> list) {
//		mTabLayout.removeAllViews();
//		PagerAdapter adapter = mViewPager.getAdapter();
//		IconPagerAdapter iconAdapter = null;
//		if (adapter instanceof IconPagerAdapter) {
//			iconAdapter = (IconPagerAdapter) adapter;
//		}
//		final int count = adapter.getCount();
//		for (int i = 0; i < count; i++) {
//			int iconResId = 0;
//			if (iconAdapter != null) {
//				iconResId = iconAdapter.getIconResId(i);
//			}
//			addTab(i, list.get(i), iconResId);
//		}
//		if (mSelectedTabIndex > count) {
//			mSelectedTabIndex = count - 1;
//		}
//		setCurrentItem(mSelectedTabIndex);
//		requestLayout();
//		notifyDataSetChanged();
//	}
//
//	@Override
//	public void setViewPager(ViewPager view, int initialPosition) {
//		setViewPager(view);
//		setCurrentItem(initialPosition);
//	}
//
//	@Override
//	public void setCurrentItem(int item) {
//		if (mViewPager == null) {
//			throw new IllegalStateException("ViewPager has not been bound.");
//		}
//		mSelectedTabIndex = item;
//		mViewPager.setCurrentItem(item);
//
//		final int tabCount = mTabLayout.getChildCount();
//		for (int i = 0; i < tabCount; i++) {
//			final View child = mTabLayout.getChildAt(i);
//			final boolean isSelected = (i == item);
//			TextView up = (TextView) child.findViewById(R.id.week_item_up);
//			TextView down = (TextView) child.findViewById(R.id.week_item_down);
//			if (isSelected == true) {
//				down.setTextColor(Color.rgb(255, 122, 81));
//				up.setTextColor(Color.rgb(0, 0, 0));
//			} else {
//				down.setTextColor(Color.rgb(140, 140, 140));
//				up.setTextColor(Color.rgb(140, 140, 140));
//			}
//			child.setSelected(isSelected);
//			if (isSelected) {
//				animateToTab(item);
//			}
//		}
//	}
//
//	@Override
//	public void setOnPageChangeListener(OnPageChangeListener listener) {
//		mListener = listener;
//	}
//
//	//
//	// private class TabView extends TextView {
//	// private int mIndex;
//	//
//	// public TabView(Context context) {
//	// super(context, null, R.attr.vpiTabPageIndicatorStyle);
//	// }
//	//
//	// @Override
//	// public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//	// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	//
//	// // Re-measure if we went beyond our maximum size.
//	// if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
//	// super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth,
//	// MeasureSpec.EXACTLY), heightMeasureSpec);
//	// }
//	// }
//	//
//	// public int getIndex() {
//	// return mIndex;
//	// }
//	// }
//
//	@Override
//	public void notifyDataSetChanged() {
//
//	}
// }
