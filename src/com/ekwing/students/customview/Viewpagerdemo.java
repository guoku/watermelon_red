package com.ekwing.students.customview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;

public class Viewpagerdemo extends ViewPager {
	boolean isenable = false;
	private Handler handler;

	public Viewpagerdemo(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Viewpagerdemo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (!isenable)
			return false;

		// switch (arg0.getAction()) {
		// case MotionEvent.ACTION_MOVE:
		// Logger.e("aaaaaaaaaaaaaaaaaaaaaaa", "==================sdfasfas");
		// handler.sendEmptyMessage(Constant.ISHAND);
		// break;
		//
		// default:
		// break;
		// }
		return super.onTouchEvent(arg0);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (!isenable)
			return false;
		return super.onInterceptTouchEvent(arg0);
	}

	public void setViewPageStatus(boolean enable) {
		// this.handler = handler;
		this.isenable = enable;
	}

}
