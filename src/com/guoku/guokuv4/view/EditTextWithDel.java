package com.guoku.guokuv4.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

import com.guoku.R;

/**
 * @zhangyao
 * @Description: 带删除按钮的edittext
 * @date 2015-8-12 下午5:38:23 
 */
public class EditTextWithDel extends EditText {
	private final static String TAG = "EditTextWithDel";
	private Drawable imgInable;
	private Context mContext;
	public boolean isShowDel = true;
	
	public EditTextWithDel(Context context) {
		super(context);
		mContext = context;
		init();
	}
	
	public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		init();
	}
	
	public EditTextWithDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}
	
	@SuppressLint("NewApi")
	private void init() {
		setBackground(mContext.getResources().getDrawable(R.color.white));
		imgInable = mContext.getResources().getDrawable(R.drawable.button_clear);
		addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setDrawable();
	}
	
	private void setDrawable() {
		if (length() < 1) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		} else {
			if(isShowDel){
				setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null);
				setEnabled(true);
			}else{
				setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
				setEnabled(false);
			}
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isShowDel) {
			int eventX = (int) event.getRawX();
			int eventY = (int) event.getRawY();
			Log.e(TAG, "eventX = " + eventX + "; eventY = " + eventY);
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);
			rect.left = rect.right - 50;
			if (rect.contains(eventX, eventY))
				setText("");
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}
