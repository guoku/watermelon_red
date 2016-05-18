/**

 */
package com.guoku.guokuv4.view;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年5月12日 下午5:25:20 带Edit的自定义view
 */
public class LayoutEditItemView extends RelativeLayout {

	TextView tv1;
	public SimpleDraweeView imageVie;
	public EditText editText;

	public LayoutEditItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public LayoutEditItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	@SuppressLint("Recycle")
	private void init(Context context, AttributeSet attrs) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.layout_edit_item_view, this);

		tv1 = (TextView) findViewById(R.id.textView1);
		editText = (EditText) findViewById(R.id.editText);
		imageVie = (SimpleDraweeView) findViewById(R.id.imageView1);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LayoutEditItemView);

		tv1.setText(a.getString(R.styleable.LayoutEditItemView_text_lefts));
		isShow(editText, a.getBoolean(R.styleable.LayoutEditItemView_edit_visibility, false));
		isShow(imageVie, a.getBoolean(R.styleable.LayoutEditItemView_img_visibility, false));
		editText.setFocusable(a.getBoolean(R.styleable.LayoutEditItemView_edit_focusable, true));
	}

	private void isShow(View view, boolean isShow) {
		if (isShow) {
			view.setVisibility(View.VISIBLE);
		} else {
			view.setVisibility(View.GONE);
		}
	}
}
