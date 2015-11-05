/**

 */
package com.guoku.guokuv4.view;

import com.guoku.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月5日 下午4:21:43
 */
public class LayoutSearchBar extends RelativeLayout {

	public EditTextWithDel editTextWithDel;
	TextView tvClean;

	public LayoutSearchBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public LayoutSearchBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.layout_search_bar, this);

		editTextWithDel = (EditTextWithDel) findViewById(R.id.ed_search);
		tvClean = (TextView) findViewById(R.id.sou_tv_btn);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LayoutSearch);

		boolean isShow = a.getBoolean(R.styleable.LayoutSearch_textRight, false);//取消按钮
		if (isShow) {
			tvClean.setVisibility(View.VISIBLE);
		} else {
			tvClean.setVisibility(View.GONE);
		}
	}

}
