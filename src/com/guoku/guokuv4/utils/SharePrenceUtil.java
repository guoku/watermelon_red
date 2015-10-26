package com.guoku.guokuv4.utils;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.TAB1Bean;
import com.guoku.guokuv4.parse.ParseUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

//import com.guoku.guokuv4.entity.UserBean;

public class SharePrenceUtil {

	public static boolean isLogin(Context context) {
		return context.getSharedPreferences(Constant.EKWING_LOGIN, 0)
				.getBoolean(Constant.SP_HAS_LOGIN, false);
	}

	// public static void setLogin(Context context, boolean islogin) {
	// context.getSharedPreferences(Constant.EKWING_LOGIN, 0).edit()
	// .putBoolean(Constant.SP_HAS_LOGIN, islogin).commit();
	// }



	/**
	 * 存储用户的基本信息
	 * 
	 * @param context
	 * @param bean
	 */
	public static void setUserBean(Context context, AccountBean bean) {
		String str;
		if (bean != null)
			str = JSON.toJSONString(bean);
		else
			str = "";
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_CODEPIC, str).commit();
	}

	/**
	 * 得到用户的基本信息
	 * 
	 * @param context
	 * @return
	 */
	public static AccountBean getUserBean(Context context) {
		AccountBean bean = null;
		SharedPreferences sp = context.getSharedPreferences(
				Constant.SP_USERINFO, 0);
		if (sp.getString(Constant.SP_CODEPIC, "") != null
				&& !"".equals(sp.getString(Constant.SP_CODEPIC, ""))) {
			bean = JSON.parseObject(sp.getString(Constant.SP_CODEPIC, ""),
					AccountBean.class);
			Logger.d("getUserBean", bean.toString());
			// setLogin(context, true);
		} else {
			// setLogin(context, false);
			Logger.d("getUserBean", "null");
		}
		return bean;
	}

	/**
	 * 是否是第一次使用
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getFirstUsed(Context context, String key) {
		boolean isFirstUsed = false;
		SharedPreferences sp = context.getSharedPreferences(Constant.LEZYO, 0);
		isFirstUsed = sp.getBoolean(key, false);
		if (!isFirstUsed) {
			Editor edit = sp.edit();
			edit.putBoolean(key, true).commit();
		}
		return isFirstUsed;
	}

	public static String getTab(Context context) {
		return context.getSharedPreferences(Constant.SP_STUDENT_TAB, 0)
				.getString(Constant.STUDENT_TAB, "");
	}

	public static void setTab(Context context, String tab) {
		context.getSharedPreferences(Constant.SP_STUDENT_TAB, 0).edit()
				.putString(Constant.STUDENT_TAB, tab).commit();
	}

	public static int getTAG(Context context) {
		return context.getSharedPreferences(Constant.TAG, 0).getInt(
				Constant.TAG, 1);
	}

	public static void setTAG(Context context, int isfirst) {
		context.getSharedPreferences(Constant.TAG, 0).edit()
				.putInt(Constant.TAG, isfirst).commit();
	}

	/**
	 * 记录key
	 * 
	 * @param context
	 * @param stringExtra
	 */
	public static void setTabList(Context context, String text) {
		context.getSharedPreferences(Constant.GUOKU_TAB, 0).edit()
				.putString(Constant.GUOKU_TAB_LIST, text).commit();
	}

	/**
	 * 得到key
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<TAB1Bean> getTabList(Context context) {
		return ParseUtil.getTabList(context.getSharedPreferences(
				Constant.GUOKU_TAB, 0).getString(Constant.GUOKU_TAB_LIST, ""));
	}

	// public static ArrayList<TAB1Bean> getTab1List(Context context) {
	// return ParseUtil.getTab1List(context.getSharedPreferences(
	// Constant.GUOKU_TAB, 0).getString(Constant.GUOKU_TAB_LIST, ""));
	// }
}
