/**

 */
package com.guoku.guokuv4.utils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.view.NoLineClickSpan;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-8-12 下午2:44:00
 */
public class StringUtils {

	private static final String TAG = "StringUtil";

	static OnNoteTag onNoteTag;

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0 || "null".equals(str));
	}


	/**
	 * 设置评论里的标签
	 * 
	 * @param mContext
	 * @param context
	 * @param tView
	 * @param onNoteTags
	 */
	public static void setNoteTag(final Context mContext, String context, TextView tView, OnNoteTag onNoteTags) {

		onNoteTag = onNoteTags;

		if (!StringUtils.isEmpty(context)) {

			List<CheckTextNumModel> listNum = new ArrayList<CheckTextNumModel>();

			String regex = "#[^\\s^+#]+\\s?";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(context);
			while (m.find()) {
				if (!m.group().isEmpty()) {

					listNum.add(setCheckTextNumModel(m.group(), m.start(), m.end()));
					SpannableStringBuilder style = new SpannableStringBuilder(context);

					if (listNum.size() > 0) {
						for (final CheckTextNumModel text : listNum) {
							style.setSpan(new NoLineClickSpan(text.getValue()) {
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									onNoteTag.setTagClick(text.getValue());
								}

							}, text.getStartPosttion(), text.getEndPosttion(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

							style.setSpan(
									new ForegroundColorSpan(mContext.getResources().getColor(R.color.tv_note_tag)),
									text.getStartPosttion(), text.getEndPosttion(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						}
					}

					tView.setText(style);
					tView.setMovementMethod(LinkMovementMethod.getInstance());
					tView.setFocusable(false);
					tView.setClickable(false);
				}
			}

			// tView.setText(context);
		}
	}

	/**
	 * 处理通知中的nickname
	 */
	public static void setNickName(Context mContext, final String context, int start, int end, TextView tView,
			final OnNoteTag onNoteTag) {

		SpannableStringBuilder style = new SpannableStringBuilder(context);
		style.setSpan(new NoLineClickSpan(context) {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onNoteTag.setTagClick(context);
			}

		}, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		style.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.tv_note_tag)), start, end,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		tView.setText(style);
		tView.setMovementMethod(LinkMovementMethod.getInstance());
		tView.setFocusable(false);
		tView.setClickable(false);
	}

	public static CheckTextNumModel setCheckTextNumModel(String value, int start, int end) {

		CheckTextNumModel checkTextNumModel = new CheckTextNumModel();
		checkTextNumModel.setValue(value);
		checkTextNumModel.setStartPosttion(start);
		checkTextNumModel.setEndPosttion(end);
		return checkTextNumModel;
	}

	public interface OnNoteTag {

		void setTagClick(String tagName);
	}

	/**
	 * 获取application中指定的meta-data
	 * 
	 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
	 */
	public static String getAppMetaData(Context ctx, String key) {
		if (ctx == null || TextUtils.isEmpty(key)) {
			return null;
		}
		String resultData = null;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			if (packageManager != null) {
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(),
						PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						resultData = applicationInfo.metaData.getString(key);
					}
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return resultData;
	}

	/**
	 * 是否为合法用户名
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isNickName(String name) {
		Pattern p = Pattern.compile("^[^0-9][\\w-]{3,30}$");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	/**
	 * 获取string中的entity id 或 user id
	 */
	public static String isStringId(String oldStr, String newStr) {
		String value = oldStr.replace(newStr, "");
		return value.substring(0, value.length() - 1);
	}

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

	public static TextView setTextColoPoint(TextView view, String root, int index) {
		if ("".equals(root)) {
			return view;
		}
		SpannableStringBuilder builder = new SpannableStringBuilder(root + " ");

		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(66, 126, 192));

		builder.setSpan(redSpan, index, index + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

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
			cLength = root.indexOf(" ", cIndex) == -1 ? root.length() : root.indexOf(" ", cIndex);
			SpannableString spannableString1 = new SpannableString(root);
			spannableString1.setSpan(new ClickableSpan() {
				@Override
				public void onClick(View widget) {
					Logger.i(TAG, "onclick--->");
				}
			}, cIndex, cLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			spannableString1.setSpan(new ForegroundColorSpan(Color.RED), cIndex, cLength,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			tv.setText(spannableString1);
			tv.setMovementMethod(LinkMovementMethod.getInstance());
			// }
		}
	}

	public static TextView setTextColor(TextView view, String root, String time) {
		SpannableStringBuilder builder = new SpannableStringBuilder(root + " ");

		ForegroundColorSpan oSpan = new ForegroundColorSpan(Color.rgb(157, 158, 159));
		Logger.i("index", "index-->" + root.length());
		Logger.i("index", " root.indexOf(time)-->" + root.indexOf(time));
		Logger.i("index", " last-->" + root.indexOf(time) + time.length());

		builder.setSpan(oSpan, root.indexOf(time), root.indexOf(time) + time.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		view.setText(builder);
		return view;
	}

//	public static TextView setTextColor(TextView view, String root, String text1, String text2, String time) {
//		SpannableStringBuilder builder = new SpannableStringBuilder(root);
//
//		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(66, 126, 192));
//		ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.rgb(66, 126, 192));
//		ForegroundColorSpan oSpan = new ForegroundColorSpan(Color.rgb(157, 158, 159));
//
//		builder.setSpan(redSpan, 0, text1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		builder.setSpan(redSpan2, root.indexOf(text2), (root.indexOf(text2) + text2.length()),
//				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		builder.setSpan(oSpan, root.indexOf(time), (root.indexOf(time) + time.length()),
//				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		view.setText(builder);
//		return view;
//	}

	public static String md5(String strToBeMd5) {
		MessageDigest digester;
		try {
			digester = MessageDigest.getInstance("MD5");

			byte[] array = digester.digest(strToBeMd5.getBytes("UTF-8"));

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
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

	/**
	 * 截取字符串，用在用户喜欢、评论、图文等页面，数据为null时的显示
	 */
	public static String setSubstring(String str, int strStert, int strEnd) {

		return str.substring(str.length() - 2, str.length());
	}
	
	/**
	 * 设置引导图名称
	 */
	public static String setReplace(String oldStr){
		if(!isEmpty(oldStr)){
			return oldStr.replace(Constant.URL_IMG + "images/", "");
		}
		return "";
	}

}
