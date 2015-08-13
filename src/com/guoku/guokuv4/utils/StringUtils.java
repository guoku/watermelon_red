/**

 */
package com.guoku.guokuv4.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.TextUtils;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-8-12 下午2:44:00 
 */
public class StringUtils {
	
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0 || "null".equals(str.toLowerCase()));
	}
	
	/**
	 * 邮箱正则
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

}
