package com.ekwing.students.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	/**
	 * 检查网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context) {
		// 判断WIFI联网情况
		boolean isWifi = isWifi(context);
		// 判断MOBILE联网情况
		boolean isMobile = isMobile(context);
		if (!isWifi && !isMobile) {
			// 如果都不能联网，提示用户
			return false;
		}
		// 判断MOBILE是否连接
		/*
		 * if (isMobile) { // 如果是，判断一下是否是wap, proxy port如果不空就是wap
		 * readAPN(context); }
		 */
		return true;
	}

	/**
	 * 读取APN列表中当前正在处于连接的配置信息
	 * 
	 * @param context
	 */
	/*
	 * private static void readAPN(Context context) { ContentResolver resolver =
	 * context.getContentResolver(); Cursor cursor =
	 * resolver.query(PREFERRED_APN_URI, null, null, null,
	 * null);//uri:不能获取所有的apn列表信息，获取当前被选中 if (cursor != null &&
	 * cursor.moveToFirst()) { GlobalParams.PROXY_IP =
	 * cursor.getString(cursor.getColumnIndex("proxy")); GlobalParams.PROXY_PORT
	 * = cursor.getInt(cursor.getColumnIndex("port")); } }
	 */
	/**
	 * 判断Wifi是否处于连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断Mobile是否处于连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobile(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
}
