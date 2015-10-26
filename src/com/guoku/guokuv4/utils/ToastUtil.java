/**
 * 
 */
package com.guoku.guokuv4.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	private static Toast mToast = null;

	public static void timeOut(Context mContext) {
		Toast.makeText(mContext, "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
	}

	public static void show(Context context, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}

		mToast.show();
	}

	public static void show(Context context, int id) {
		if (mToast == null) {
			mToast = Toast.makeText(context,
					context.getResources().getString(id), Toast.LENGTH_SHORT);
		} else {
			mToast.setText(context.getResources().getString(id));
			mToast.setDuration(Toast.LENGTH_SHORT);
		}

		mToast.show();
	}
}
