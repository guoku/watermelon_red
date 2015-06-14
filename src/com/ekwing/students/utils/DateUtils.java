package com.ekwing.students.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import android.text.TextUtils;

import com.ekwing.students.config.Logger;
import com.ekwing.students.entity.WeekBean;

public class DateUtils {
	private static int TIME = 24 * 60 * 60 * 1000;
	public static SimpleDateFormat formatS = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat formatM = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static SimpleDateFormat formatL = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat formatsingle = new SimpleDateFormat("dd");

	public static String getStandardDate(String timeStr) {

		StringBuffer sb = new StringBuffer();

		long t = (long) Double.parseDouble(timeStr) * 1000;
		long time = System.currentTimeMillis() - t;
		long mill = (long) Math.ceil(time / 1000);// 秒前

		long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

		long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

		long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

		if (day - 1 > 0) {
			Logger.i("time", "day--->" + day);
			if (day / 7 > 100) {
				sb.append("很久很久以前");
			} else if (day / 7 > 0) {
				sb.append(day / 7 + "周前");
			} else if (day - 1 > 2) {
				sb.append(day - 1 + "天前");
			} else if (day - 1 > 1) {
				sb.append("前天");
			} else if (day - 1 > 0) {
				sb.append("昨天");
			}
		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时前");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟前");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟");
			} else {
				sb.append(mill + "秒前");
			}
		} else {
			sb.append("刚刚");
		}
		// if (!sb.toString().equals("刚刚")) {
		// sb.append("");
		// }
		return sb.toString();
	}

	/**
	 * 得到当前日式在的前30天的日期
	 * 
	 * @return
	 */
	public static ArrayList<WeekBean> getWeekData() {
		ArrayList<WeekBean> lists = new ArrayList<WeekBean>();
		WeekBean bean = null;
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < 30; i++) {
			bean = new WeekBean();
			String data = formatsingle.format(currentTimeMillis);
			String data1 = formatS.format(currentTimeMillis);
			currentTimeMillis = currentTimeMillis - TIME;
			bean.setDays(data);
			bean.setWeeks(getWeek(data1));
			if (i == 0) {
				bean.setStatus("1");
				// bean.setTimes(parse2Seconds()+"");
			} else {
				bean.setStatus("0");
			}
			lists.add(bean);
		}
		Collections.reverse(lists);
		return lists;
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param pTime
	 *            设置的需要判断的时间 //格式如2012-09-08
	 * 
	 * 
	 * @return dayForWeek 判断结果
	 * @Exception 发生异常
	 */
	public static String getWeek(String pTime) {
		String Week = "";
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(formatS.parse(pTime));

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			Week += "Sun";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 2) {
			Week += "Mon";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 3) {
			Week += "Tue";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 4) {
			Week += "Wed";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 5) {
			Week += "Thu";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 6) {
			Week += "Fri";
		}
		if (c.get(Calendar.DAY_OF_WEEK) == 7) {
			Week += "Sat";
		}
		return Week;
	}

	/**
	 * 得到一个日期型的完全时间 ，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatL.format(date);
	}

	/**
	 * �?String�???��?��?? ??��??为�?? yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		try {
			if (date.length() <= 10) {
				return formatS.parse(date);
			} else if (date.length() == 16) {
				return formatM.parse(date);
			} else {
				return formatL.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTime(String last) {
		SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
		return sdf.format(last);
	}

	/**
	 * �?string ?????��?��?��??�?:2012-1-1�?�???��??�?�????
	 * 
	 * @param date
	 * @return
	 */
	public static long parseString(String date) {
		if (TextUtils.isEmpty(date)) {
			return 0;
		}
		try {
			return formatS.parse(date).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static String parseLongToStr(long millis) {
		if (millis != 0) {
			Date date = new Date();
			date.setTime(millis);
			return formatS.format(date);
		} else {
			return null;
		}
	}

	/**
	 * 返回当天00：00：00的秒时间戳
	 * 
	 * @return
	 */
	public static long parse2Seconds() {
		long currentTimeMillis = System.currentTimeMillis();
		String format = formatS.format(currentTimeMillis);
		try {
			return formatS.parse(format).getTime() / 1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 得到根据给定的时间字符串计算出时分秒的时间格式
	 */
	public static String getsmsTime(String time) {
		String aa = "";
		try {
			long diff = Long.parseLong(time);
			long days = diff / (60 * 60 * 24);
			long hours = (diff - days * (60 * 60 * 24)) / (60 * 60);
			long minutes = (diff - days * (60 * 60 * 24) - hours * (60 * 60)) / (60);
			long seconds = (diff - days * (60 * 60 * 24) - hours * (60 * 60) - minutes * (60));
			if ((hours + "").length() < 2) {
				aa = "0" + hours;
			} else {
				aa = "" + hours;
			}
			if ((minutes + "").length() < 2) {
				aa = aa + ":0" + minutes;
			} else {
				aa = aa + ":" + minutes;
			}
			if ((seconds + "").length() < 2) {
				aa = aa + ":0" + seconds;
			} else {
				aa = aa + ":" + seconds;
			}
			// aa = hours + ":" + minutes + ":" + seconds;
		} catch (Exception e) {
			aa = "00:00:00";
		}
		return aa;
	}
}
