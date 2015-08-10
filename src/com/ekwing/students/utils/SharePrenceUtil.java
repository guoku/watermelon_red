package com.ekwing.students.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.entity.HHWBean;
import com.ekwing.students.entity.HHWDetailsBean;
import com.ekwing.students.entity.HWIndexBean;
import com.ekwing.students.entity.HasIcon;
import com.ekwing.students.entity.LevelBean;
import com.ekwing.students.entity.PetsBean;
import com.ekwing.students.entity.ThemeBean;
import com.ekwing.students.entity.UserInfoBean;
import com.ekwing.students.entity.UserLoginBean;
import com.ekwing.students.entity.WeekBean;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.TAB1Bean;
import com.guoku.guokuv4.parse.ParseUtil;

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
	 * 存储登录的基本信息
	 * 
	 * @param context
	 * @param bean
	 */
	public static void setLoginInfo(Context context, UserLoginBean bean) {

		context.getSharedPreferences(Constant.SP_USEREBEAN, 0).edit()
				.putString(Constant.SP_USEREBEAN_UID, bean.getUid()).commit();
		context.getSharedPreferences(Constant.SP_USEREBEAN, 0).edit()
				.putString(Constant.SP_USERBEAN_TOKEN, bean.getToken())
				.commit();
		context.getSharedPreferences(Constant.SP_USEREBEAN, 0).edit()
				.putString(Constant.SP_USERBEAN_PSW, bean.getPmm()).commit();
		context.getSharedPreferences(Constant.SP_USEREBEAN, 0).edit()
				.putString(Constant.SP_USERBEAN_IDENTITY, bean.getIdentity())
				.commit();
	}

	/**
	 * 得到用户登录的基本信息，并封装成javabean
	 * 
	 * @param context
	 * @return
	 */
	public static UserLoginBean getLoginInfo(Context context) {
		UserLoginBean bean = new UserLoginBean();
		SharedPreferences sp = context.getSharedPreferences(
				Constant.SP_USEREBEAN, 0);
		bean.setUid(sp.getString(Constant.SP_USEREBEAN_UID, ""));
		bean.setToken(sp.getString(Constant.SP_USERBEAN_TOKEN, ""));
		bean.setPmm(sp.getString(Constant.SP_USERBEAN_PSW, ""));
		bean.setIdentity(sp.getString(Constant.SP_USERBEAN_IDENTITY, ""));

		return bean;
	}

	/**
	 * 得到用户的基本信息
	 * 
	 * @param context
	 * @return
	 */
	public static UserInfoBean getUserInfoBean(Context context) {
		UserInfoBean bean = new UserInfoBean();
		SharedPreferences sp = context.getSharedPreferences(
				Constant.SP_USERINFO, 0);
		bean.setCodePic(sp.getString(Constant.SP_CODEPIC, null));
		bean.setClasses(sp.getString(Constant.SP_CLASSES, null));
		bean.setBirthday(sp.getString(Constant.SP_BIRTHDAY, null));
		bean.setSex(sp.getString(Constant.SP_SEX, null));
		bean.setNextLevel(sp.getString(Constant.SP_NEXTLEVEL, null));
		bean.setUserEmail(sp.getString(Constant.SP_USEREMAIL, null));
		bean.setNicename(sp.getString(Constant.SP_NICENAME, null));
		bean.setAvatar(sp.getString(Constant.SP_AVATAR, null));
		bean.setUsername(sp.getString(Constant.SP_USERNAME, null));
		bean.setSchool(sp.getString(Constant.SP_SCHOOL, null));
		bean.setLevels(sp.getString(Constant.SP_LEVELS, null));
		bean.setLevel(sp.getString(Constant.SP_LEVEL, null));
		bean.setHasFlower(sp.getString(Constant.SP_HASFLOWER, null));
		bean.setUserPhone(sp.getString(Constant.SP_USERPHONE, null));
		bean.setParentPhone(sp.getString(Constant.SP_PARENTPHONE, null));
		bean.setEbean(sp.getString(Constant.SP_EBEAN, null));
		bean.setFirst(sp.getString(Constant.SP_FIRST, "2"));
		bean.setVip(sp.getBoolean(Constant.SP_VIP, false));
		bean.setGrade(sp.getString(Constant.SP_GRADE, ""));
		String hasIson = sp.getString(Constant.SP_HASICON, "[]");
		List<HasIcon> hasLists = JSON.parseArray(hasIson, HasIcon.class);
		bean.setHasIcon(hasLists);

		List<PetsBean> petLists = null;
		try {
			String petString = sp.getString(Constant.SP_PETS, "[]");
			petLists = JSON.parseArray(petString, PetsBean.class);
		} catch (Exception e) {
			petLists = new ArrayList<PetsBean>();
		}
		if (petLists != null && !"".equals(petLists)) {
			bean.setPet((ArrayList<PetsBean>) petLists);
		}
		return bean;
	}

	/**
	 * 存储用户的基本信息
	 * 
	 * @param context
	 * @param bean
	 */
	public static void setUserInfoBean1(Context context, UserInfoBean bean) {
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_CODEPIC, bean.getCodePic()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_CLASSES, bean.getClasses()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_BIRTHDAY, bean.getBirthday()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_SEX, bean.getSex()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_NEXTLEVEL, bean.getNextLevel()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_USEREMAIL, bean.getUserEmail()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_NICENAME, bean.getNicename()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_AVATAR, bean.getAvatar()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_USERNAME, bean.getUsername()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_SCHOOL, bean.getSchool()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_LEVELS, bean.getLevels()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_LEVEL, bean.getLevel()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_HASFLOWER, bean.getHasFlower()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_USERPHONE, bean.getUserPhone()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_PARENTPHONE, bean.getParentPhone())
				.commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_EBEAN, bean.getEbean()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_FIRST, bean.getFirst()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putBoolean(Constant.SP_VIP, bean.isVip()).commit();
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_GRADE, bean.getGrade()).commit();

		// context.getSharedPreferences(Constant.SP_USERINFO,
		// 0).edit().putString(Constant.SP_HASICON, bean.getIcon()).commit();
		List<HasIcon> hasLists = bean.getHasIcon();
		String jsonString = JSON.toJSONString(hasLists, true);
		context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
				.putString(Constant.SP_HASICON, jsonString).commit();

		ArrayList<PetsBean> petLists = bean.getPet();
		if (petLists != null && !"".equals(petLists)) {
			String petString = JSON.toJSONString(petLists);
			context.getSharedPreferences(Constant.SP_USERINFO, 0).edit()
					.putString(Constant.SP_PETS, petString).commit();
		}
	}

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
				.getString(Constant.STUDENT_TAB, "homework");
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

	public static ArrayList<TAB1Bean> getTab1List(Context context) {
		return ParseUtil.getTab1List(context.getSharedPreferences(
				Constant.GUOKU_TAB, 0).getString(Constant.GUOKU_TAB_LIST, ""));
	}

	// public static ArrayList<Tab2Bean> getTab2List(Context context) {
	// return ParseUtil.getTab2List(
	// context.getSharedPreferences(Constant.GUOKU_TAB, 0).getString(
	// Constant.GUOKU_TAB_LIST, ""), context);
	// }

}
