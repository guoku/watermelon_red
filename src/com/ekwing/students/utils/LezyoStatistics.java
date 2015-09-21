package com.ekwing.students.utils;

import android.content.Context;

import com.ekwing.students.config.Constant;
import com.umeng.analytics.MobclickAgent;

public class LezyoStatistics {

	public static void onResume(Context mContext) {
		if (Constant.UMENG_LOG)
			MobclickAgent.onResume(mContext);
	}

	public static void onPause(Context mContext) {
		if (Constant.UMENG_LOG)
			MobclickAgent.onPause(mContext);
	}

	/**
	 * 统计事件次数
	 * 
	 * @param mContext
	 * @param eventID
	 */
	public static void onEvent(Context mContext, String eventID) {
		if (Constant.UMENG_LOG)
			MobclickAgent.onEvent(mContext, eventID);
	}

	/**
	 * 计数事件时长：开始
	 * 
	 * @param mContext
	 * @param eventId
	 */
	public static void onEventBegin(Context mContext, String eventId) {
		if (Constant.UMENG_LOG)
			MobclickAgent.onEventBegin(mContext, eventId);
	}

	/**
	 * 计数事件时长：结束
	 * 
	 * @param mContext
	 * @param eventId
	 */
	public static void onEventEnd(Context mContext, String eventId) {
		if (Constant.UMENG_LOG)
			MobclickAgent.onEventEnd(mContext, eventId);
	}

}
