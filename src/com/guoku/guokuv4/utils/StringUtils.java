/**

 */
package com.guoku.guokuv4.utils;

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
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.view.NoLineClickSpan;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-8-12 下午2:44:00
 */
public class StringUtils {

	static OnNoteTag onNoteTag;

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0 || "null".equals(str));
	}

	/**
	 * 邮箱正则
	 * 
	 * @param mail
	 * @return
	 */
	public static boolean isEmail(String mail) {
		if (TextUtils.isEmpty(mail)) {
			return false;
		}
		String str = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(mail);
		return m.matches();
	}

	/**
	 * 设置评论里的标签
	 * 
	 * @param mContext
	 * @param context
	 * @param tView
	 * @param onNoteTags
	 */
	public static void setNoteTag(final Context mContext, String context,
			TextView tView, OnNoteTag onNoteTags) {

		onNoteTag = onNoteTags;

		if (!StringUtils.isEmpty(context)) {

			List<CheckTextNumModel> listNum = new ArrayList<CheckTextNumModel>();

			String regex = "#[^\\s^+#]+\\s?";
			Pattern pattern = Pattern.compile(regex);
			Matcher m = pattern.matcher(context);
			while (m.find()) {
				if (!m.group().isEmpty()) {

					listNum.add(setCheckTextNumModel(m.group(), m.start(),
							m.end()));
					SpannableStringBuilder style = new SpannableStringBuilder(
							context);

					if (listNum.size() > 0) {
						for (final CheckTextNumModel text : listNum) {
							style.setSpan(new NoLineClickSpan(text.getValue()) {
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									onNoteTag.setTagClick(text.getValue());
								}

							}, text.getStartPosttion(), text.getEndPosttion(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

							style.setSpan(
									new ForegroundColorSpan(mContext
											.getResources().getColor(
													R.color.tv_note_tag)), text
											.getStartPosttion(), text
											.getEndPosttion(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

	public static CheckTextNumModel setCheckTextNumModel(String value,
			int start, int end) {

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
				ApplicationInfo applicationInfo = packageManager
						.getApplicationInfo(ctx.getPackageName(),
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

}
