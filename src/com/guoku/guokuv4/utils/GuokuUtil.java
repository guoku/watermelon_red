package com.guoku.guokuv4.utils;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class GuokuUtil {

	public static void hideKeyboard(Context context, EditText editText) {
		editText.setText("");
		editText.setHint("");
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 获取当前的版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context context) {
		String version = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 在网络异常的情况下关闭listview的刷新效果
	 */
	public static void closeListViewHeader(final PullToRefreshListView lisview) {

		if (lisview != null) {
			lisview.postDelayed(new Runnable() {
				@Override
				public void run() {
					lisview.onRefreshComplete();
				}
			}, 1000);
		}
	}

	/**
	 * 隐藏键盘
	 */
	public static void hideKeyBoard(Activity mContext) {
		InputMethodManager im = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
		if (isKeyShow(mContext)) {
			im.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static boolean isKeyShow(Activity mContext) {

		if (mContext.getWindow()
				.getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
			return true;
		}
		return false;
	}

}
