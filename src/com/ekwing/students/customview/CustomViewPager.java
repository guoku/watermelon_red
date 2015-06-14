package com.ekwing.students.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lidroid.xutils.util.LogUtils;

public class CustomViewPager extends ViewPager {

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		try {
		return super.onInterceptTouchEvent(arg0);
		}
		catch (IllegalArgumentException ex) {
		  LogUtils.d("处理4.1系统中的bug", ex);
		}
		return false;
	}

}
