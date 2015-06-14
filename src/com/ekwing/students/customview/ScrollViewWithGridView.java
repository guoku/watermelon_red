package com.ekwing.students.customview;

import android.widget.GridView;

/**
 * 
 * @Description: scrollview中内嵌listview的简单实现
 * 
 * @File: ScrollViewWithListView.java
 * 
 * @Paceage com.meiya.ui
 * 
 * 
 * @Date 下午03:02:38
 * 
 * @Version
 */
public class ScrollViewWithGridView extends GridView {

	public ScrollViewWithGridView(android.content.Context context,
			android.util.AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
