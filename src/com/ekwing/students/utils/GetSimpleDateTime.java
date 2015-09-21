package com.ekwing.students.utils;

import java.text.SimpleDateFormat;

import android.util.Log;

public class GetSimpleDateTime {

	/**
	 * 根据时间戳获取格式化的时候
	 * 
	 * @return
	 */
	public static String getFormatDate(Long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sDateFormat.format(time);
		return date;
	}

	// public static String getFormatDate(Long time) {
	// SimpleDateFormat sDateFormat = new
	// SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
	// String date = sDateFormat.format(time);
	// return date;
	// }

	/**
	 * 时分形式
	 */
	public static String getTimeFormatDate(Long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd\nHH:mm");
		String date = sDateFormat.format(time);
		return date;
	}

	/**
	 * 时分形式
	 */
	public static String getLastTime(Long time) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH小时mm分钟");
		String date = sDateFormat.format(time);
		return date;
	}

	/**
	 * 获得格式化的当前系统时间
	 * 
	 * @return
	 */
	public static String getSimeDate() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd   HH:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}

	/**
	 * 得到系统的毫秒值
	 * 
	 * @return
	 */
	public static String getCurrentTimeMillisData() {
		long currentTimeMillis = System.currentTimeMillis() / 1000;
		String current = currentTimeMillis + "";
		return current;
	}

	public static String getFormatMonthDay(long l) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月\ndd");
		String date = sDateFormat.format(l);

		return date;
	}

	public static String getFormaHourstMinutes(long l) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm");
		String date = sDateFormat.format(l);
		return date;
	}

	public static String getFormaatTime(long lefttime) {
		// String lasttime = String.valueOf(lefttime);
		StringBuffer result = new StringBuffer();
		int day = (int) (lefttime / (24 * 60 * 60));
		result.append(day).append("天");
		if (day > 0) {
			lefttime = lefttime - (long) (day * 24 * 3600);
		}
		int hour = (int) (lefttime / 3600);
		result.append(hour).append("时");
		if (hour > 0) {
			lefttime = lefttime - hour * 3600;
		}
		int minute = (int) (lefttime / 60);
		result.append(minute).append("分");
		return result.toString();
	}

}
