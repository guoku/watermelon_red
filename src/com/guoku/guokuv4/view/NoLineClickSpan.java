/**

 */
package com.guoku.guokuv4.view;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-8-26 下午3:09:35 无下划线的LineClickSpan
 */
public class NoLineClickSpan extends ClickableSpan {

	String text;

	public NoLineClickSpan(String texts) {
		// TODO Auto-generated constructor stub
		super();
		this.text = texts;
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		// TODO Auto-generated method stub
		ds.setColor(ds.linkColor);
		ds.setUnderlineText(false); // 去掉下划线
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}

}
