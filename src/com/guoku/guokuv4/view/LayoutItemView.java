/**

 */
package com.guoku.guokuv4.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guoku.R;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-22 上午11:48:45
 */
public class LayoutItemView extends RelativeLayout {

	public TextView tv1;
	ImageView imageVie;

	public LayoutItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public LayoutItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.layout_item_view, this);

		tv1 = (TextView) findViewById(R.id.textView1);
		imageVie = (ImageView) findViewById(R.id.imageView1);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.LayoutItemView);

		tv1.setText(a.getString(R.styleable.LayoutItemView_textLeft));
		tv1.setTextColor(a.getInt(R.styleable.LayoutItemView_textColorLeft,
				R.color.g_other));
	}
}
