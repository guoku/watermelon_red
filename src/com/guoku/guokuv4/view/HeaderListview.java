/**

 */
package com.guoku.guokuv4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-9 上午11:30:29 用来解决listView和scrollview的冲突
 */
public class HeaderListview extends ListView {

	public HeaderListview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
	// int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	// MeasureSpec.AT_MOST);
	// super.onMeasure(widthMeasureSpec, mExpandSpec);
	// }
}
