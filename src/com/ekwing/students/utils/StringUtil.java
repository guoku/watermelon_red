package com.ekwing.students.utils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ekwing.students.config.Logger;

public class StringUtil {
	private static final String TAG = "StringUtil";

	public static Date str2Date(String s) {
		Date d;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
			return d;
		} catch (Exception e) {
		}
		return null;
	}

	public static Date longToDate(long time) {
		return new Date(time);
	}

	public static String date2String(Date date) {
		String d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		} catch (Exception e) {
		}
		return d;
	}

	public static String double2String(double d, int fraction) {
		DecimalFormat df = new DecimalFormat();
		df.setGroupingUsed(false);
		df.setMaximumFractionDigits(fraction);
		df.setMinimumFractionDigits(fraction);
		String retValue = df.format(d);
		return retValue;
	}

	// public static TextView setTextColor(TextView view, String root, String
	// text) {
	// SpannableStringBuilder builder = new SpannableStringBuilder(root);
	//
	// ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(66,
	// 126, 192));
	//
	// builder.setSpan(redSpan, 0, text.length(),
	// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	//
	// view.setText(builder);
	// return view;
	// }
	//
	// public static TextView setTextColor(TextView view, String root,
	// String text1, String text2) {
	// SpannableStringBuilder builder = new SpannableStringBuilder(root);
	//
	// ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(66,
	// 126, 192));
	// ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.rgb(66,
	// 126, 192));
	//
	// builder.setSpan(redSpan, 0, text1.length(),
	// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	// builder.setSpan(redSpan2, root.indexOf(text2), root.indexOf(text2)
	// + text2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	//
	// view.setText(builder);
	// return view;
	// }

	public static TextView setTextColor(TextView view, String root,
			String text, String time) {
		SpannableStringBuilder builder = new SpannableStringBuilder(root + " ");

		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(66,
				126, 192));

		ForegroundColorSpan oSpan = new ForegroundColorSpan(Color.rgb(157, 158,
				159));
		Logger.i("index", "index-->" + root);
		Logger.i("index", "text-->" + text);
		Logger.i("index", "time-->" + time);
		Logger.i("index", "index-->" + root.length());
		Logger.i("index", "text.length()-->" + text.length());
		Logger.i("index", " root.indexOf(time)-->" + root.indexOf(time));
		Logger.i("index", " last-->" + (root.indexOf(time) + time.length()));
		builder.setSpan(redSpan, 0, text.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		builder.setSpan(oSpan, root.indexOf(time),
				(root.indexOf(time) + time.length()),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		view.setText(builder);
		return view;
	}

	public static TextView setTextColoPoint(TextView view, String root,
			int index) {
		if ("".equals(root)) {
			return view;
		}
		SpannableStringBuilder builder = new SpannableStringBuilder(root + " ");

		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(66,
				126, 192));

		builder.setSpan(redSpan, index, index + 1,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		view.setText(builder);
		return view;
	}

	public static void setTextColoPoint(TextView tv, String root) {
		if ("".equals(root)) {
			return;
		}
		if (root.contains("#")) {
			int cIndex = 0;
			int cLength = 0;
			// while (cIndex < root.length()) {
			cIndex = root.indexOf("#", cIndex);
			cLength = root.indexOf(" ", cIndex) == -1 ? root.length() : root
					.indexOf(" ", cIndex);
			SpannableString spannableString1 = new SpannableString(root);
			spannableString1.setSpan(new ClickableSpan() {
				@Override
				public void onClick(View widget) {
					Logger.i(TAG, "onclick--->");
				}
			}, cIndex, cLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			spannableString1.setSpan(new ForegroundColorSpan(Color.RED),
					cIndex, cLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv.setText(spannableString1);
			tv.setMovementMethod(LinkMovementMethod.getInstance());
			// }
		}
	}

//	public static void setTextColoPoint2(TextView tv, String root,
//			final Handler handler, final String uid) {
//		if ("".equals(root)) {
//			return;
//		}
//		SpannableStringBuilder builder = new SpannableStringBuilder(root + " ");
//		if (root.contains("#")) {
//			int cIndex = 0;
//			int cLength = 0;
//			while (cIndex < root.length()) {
//				cIndex = root.indexOf("#", cIndex);
//				if (cIndex == -1) {
//					break;
//				}
//				cLength = root.indexOf(" ", cIndex) == -1 ? root.length()
//						: root.indexOf(" ", cIndex);
//				MyClickableSpan span = new MyClickableSpan(root.substring(
//						cIndex, cLength)) {
//
//					@Override
//					public void onClick(View arg0) {
//						Message msg = Message.obtain();
//						msg.what = 200;
//						msg.obj = text + "&&" + uid;
//						handler.sendMessage(msg);
//					}
//				};
//				builder.setSpan(span, cIndex, cLength,
//						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//				builder.setSpan(new ForegroundColorSpan(Color.RED), cIndex,
//						cLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//				tv.setText(builder);
//				tv.setMovementMethod(LinkMovementMethod.getInstance());
//				cIndex++;
//			}
//		} else {
//			tv.setText(root);
//		}
//	}

	public static TextView setTextColor(TextView view, String root, String time) {
		SpannableStringBuilder builder = new SpannableStringBuilder(root + " ");

		ForegroundColorSpan oSpan = new ForegroundColorSpan(Color.rgb(157, 158,
				159));
		Logger.i("index", "index-->" + root.length());
		Logger.i("index", " root.indexOf(time)-->" + root.indexOf(time));
		Logger.i("index", " last-->" + root.indexOf(time) + time.length());

		builder.setSpan(oSpan, root.indexOf(time),
				root.indexOf(time) + time.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		view.setText(builder);
		return view;
	}

	public static TextView setTextColor(TextView view, String root,
			String text1, String text2, String time) {
		SpannableStringBuilder builder = new SpannableStringBuilder(root);

		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(66,
				126, 192));
		ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.rgb(66,
				126, 192));
		ForegroundColorSpan oSpan = new ForegroundColorSpan(Color.rgb(157, 158,
				159));
		Logger.i(TAG, "root-->" + root);
		Logger.i(TAG, "text1-->" + text1);
		Logger.i(TAG, "text2-->" + text2);
		Logger.i(TAG, "time-->" + time);

		builder.setSpan(redSpan, 0, text1.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(redSpan2, root.indexOf(text2),
				(root.indexOf(text2) + text2.length()),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		builder.setSpan(oSpan, root.indexOf(time),
				(root.indexOf(time) + time.length()),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		view.setText(builder);
		return view;
	}

	public static String md5(String strToBeMd5) {
		MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("MD5");

			byte[] array = digester.digest(strToBeMd5.getBytes("UTF-8"));

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static boolean checkEmail(String email) {
		if (email != null && !email.trim().equals("")) {
			String check = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			return matcher.matches();
		}
		return false;
	}

	public static String formatUpdateText(long lastTime) {
		long seconds = (int) ((System.currentTimeMillis() - lastTime) / 1000);
		int rDay = (int) (seconds / (60 * 60 * 24));
		int rHour = (int) (seconds - rDay * 60 * 60 * 24) / (60 * 60);
		int rMinute = (int) ((seconds - -rDay * 60 * 60 * 24 - rHour * 60 * 60) / 60);
		String txt = "";

		if (rDay > 0) {
			txt = rDay + "天前更新";
		} else if (rHour > 0) {
			txt = rHour + "小时前更新";
		} else if (rMinute > 0) {
			txt = rMinute + "分钟前更新";
		} else if (seconds > 0) {
			txt = seconds + "秒钟前更新";
		}
		return txt;
	}
}
