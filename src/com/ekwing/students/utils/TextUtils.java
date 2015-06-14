package com.ekwing.students.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.entity.WordsBean;

/**
 * 处理文字的工具类
 * 
 * @author think
 * 
 */
public class TextUtils {

	public static void setStringText(TextView tv, int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder(tv
				.getText().toString());
		ForegroundColorSpan blueSpan = new ForegroundColorSpan(color);
		builder.setSpan(blueSpan, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(builder);
	}

	/**
	 * 根据坐标确定颜色
	 * 
	 * @param tv
	 * @param limit
	 * @param color
	 */
	public static void setColor(TextView tv, int[] limit, int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder(tv
				.getText().toString());
		for (int i = 0; i < limit.length;) {
			ForegroundColorSpan blueSpan = new ForegroundColorSpan(color);
			System.out.println("" + limit[i] + "," + limit[i + 1]);
			builder.setSpan(blueSpan, limit[i], limit[i + 1],
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			i = i + 2;
		}

		tv.setText(builder);
	}

	/**
	 * 根据字符串确定颜色
	 * 
	 * @param tv
	 * @param limit
	 * @param color
	 */
	public static void setColor(TextView tv, String[] limit, int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder(tv
				.getText().toString());
		for (int i = 0; i < limit.length; i++) {
			ForegroundColorSpan blueSpan = new ForegroundColorSpan(color);
			int index = tv.getText().toString().indexOf(limit[i]);
			builder.setSpan(blueSpan, index, index + limit[i].length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		tv.setText(builder);
	}

	public static void setColor(TextView tv, ArrayList<String> limit, int color) {
		// Logger.i("TextUtils", "limit-->" + limit.toString());
		SpannableStringBuilder builder = new SpannableStringBuilder(tv
				.getText().toString().toLowerCase());
		if (limit != null && limit.size() > 0) {
			for (int i = 0; i < limit.size(); i++) {
				ForegroundColorSpan blueSpan = new ForegroundColorSpan(color);
				int index = builder.toString().indexOf(limit.get(i));
				if (index == -1) {
					continue;
				}
				builder.setSpan(blueSpan, index, index + limit.get(i).length(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		tv.setText(builder);
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\n|\r");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		// Logger.i("DDDDD", dest);
		return replaceHao(dest);
	}

	public static String replaceWork(ArrayList<WordsBean> wordsList) {

		// Log.i("jjjj", "a" + wordsList.toString());
		// Log.i("jjjj", "a" + JSON.toJSONString(wordsList));
		// Log.i("jjjj", "b" + JSON.toJSONString(wordsList));
		String raet = JSON.toJSONString(wordsList).replaceAll("\\\\", "");
		// Log.i("jjjj", "c" + raet);
		raet = raet.replaceAll("\"ret\":\"", "\"ret\":");
		String a = "\",\"retext\"";
		String b = ",\"retext\"";
		raet = raet.replaceAll(a, b);
		return raet;
	}

	public static String replaceHao(String text) {
		// String bbc = text.replaceAll("\'", "");
		return text.replaceAll("\"", "");
	}

}
