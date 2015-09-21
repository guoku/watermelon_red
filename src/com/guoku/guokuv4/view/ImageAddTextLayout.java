/**

 */
package com.guoku.guokuv4.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-8 下午3:51:43 图片上加文字
 */
public class ImageAddTextLayout extends FrameLayout {

	public SimpleDraweeView imView;

	public TextView tView;

	public ImageAddTextLayout(Context context) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.image_and_text_layout, this);

		init();
	}

	public ImageAddTextLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private void init() {

		imView = (SimpleDraweeView) findViewById(R.id.img);
		tView = (TextView) findViewById(R.id.text);
	}


}
