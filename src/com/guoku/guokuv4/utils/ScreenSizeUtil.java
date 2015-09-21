/**

 */
package com.guoku.guokuv4.utils;

import android.app.Activity;

/**
 * @zhangyao
 * @Description: 系统屏幕属性
 * @date 2015-8-12 下午3:27:34
 */
public class ScreenSizeUtil {

	public static int getScreenWidth(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getWidth();
	}

	public static int getScreenHeight(Activity activity) {
		return activity.getWindowManager().getDefaultDisplay().getHeight();
	}

}
