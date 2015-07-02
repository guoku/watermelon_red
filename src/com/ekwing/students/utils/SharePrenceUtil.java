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

	/**
	 * 设置声音的状态
	 * 
	 * @param context
	 * @param state
	 */
	public static void setSound(Context context, boolean state) {
		context.getSharedPreferences(Constant.SP_SWITCH_STATE, 0).edit()
				.putBoolean(Constant.SOUND, state).apply();
	}

	/**
	 * 得到声音的状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getSound(Context context) {
		return context.getSharedPreferences(Constant.SP_SWITCH_STATE, 0)
				.getBoolean(Constant.SOUND, true);
	}

	/**
	 * 设置震动的状态
	 * 
	 * @param context
	 * @param state
	 */
	public static void setVibrator(Context context, boolean state) {
		context.getSharedPreferences(Constant.SP_SWITCH_STATE, 0).edit()
				.putBoolean(Constant.VIBRAT, state).commit();
	}

	/**
	 * 得到震动的状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getVibrator(Context context) {
		return context.getSharedPreferences(Constant.SP_SWITCH_STATE, 0)
				.getBoolean(Constant.VIBRAT, true);
	}

	/**
	 * 设置2g/3g提示的状态
	 * 
	 * @param context
	 * @param state
	 */
	public static void setNetRemind(Context context, boolean state) {
		context.getSharedPreferences(Constant.SP_SWITCH_STATE, 0).edit()
				.putBoolean(Constant.NETREMIND, state).commit();
	}

	/**
	 * 得到2g/3g提示状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getNetRemind(Context context) {
		return context.getSharedPreferences(Constant.SP_SWITCH_STATE, 0)
				.getBoolean(Constant.NETREMIND, true);
	}

	// public static void saveUserEntity(Context context, UserEntity entity) {
	// Editor edit = context.getSharedPreferences(Constant.SP_USERENTITY, 0)
	// .edit();
	// edit.putString(Constant.SP_USERENTITY_avatar, entity.getAvatar());
	// edit.putString(Constant.SP_USERENTITY_city, entity.getCity());
	// edit.putString(Constant.SP_USERENTITY_email, entity.getEmail());
	// edit.putString(Constant.SP_USERENTITY_mobile, entity.getMobile());
	// edit.putString(Constant.SP_USERENTITY_name, entity.getName());
	// edit.putString(Constant.SP_USERENTITY_province, entity.getProvince());
	// edit.putString(Constant.SP_USERENTITY_token, entity.getToken());
	// edit.putString(Constant.SP_USERENTITY_uid, entity.getUid());
	// edit.putString(Constant.SP_USERENTITY_sc, entity.getStrC());
	// edit.commit();
	// }
	//
	// public static UserEntity getUserEntity(Context context) {
	// UserEntity entity = new UserEntity();
	// SharedPreferences sp = context.getSharedPreferences(
	// Constant.SP_USERENTITY, 0);
	// entity.setAvatar(sp.getString(Constant.SP_USERENTITY_avatar, null));
	// entity.setCity(sp.getString(Constant.SP_USERENTITY_city, null));
	// entity.setEmail(sp.getString(Constant.SP_USERENTITY_email, null));
	// entity.setMobile(sp.getString(Constant.SP_USERENTITY_mobile, null));
	// entity.setName(sp.getString(Constant.SP_USERENTITY_name, null));
	// entity.setProvince(sp.getString(Constant.SP_USERENTITY_province, null));
	// entity.setToken(sp.getString(Constant.SP_USERENTITY_token, null));
	// entity.setUid(sp.getString(Constant.SP_USERENTITY_uid, null));
	// entity.setStrC(sp.getString(Constant.SP_USERENTITY_sc, null));
	// // entity.setUid(sp.getString(Constant.SP_USERENTITY_uid, null));
	// return entity;
	// }

	public static String getTab(Context context) {
		return context.getSharedPreferences(Constant.SP_STUDENT_TAB, 0)
				.getString(Constant.STUDENT_TAB, "homework");
	}

	public static void setTab(Context context, String tab) {
		context.getSharedPreferences(Constant.SP_STUDENT_TAB, 0).edit()
				.putString(Constant.STUDENT_TAB, tab).commit();
	}

	/**
	 * 是否是第一次进入课文听读 ，默认是没有，没有显示涂层
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getFirstArticle(Context context) {
		return context
				.getSharedPreferences(Constant.EKWING_IS_FIRST_ARTICLE, 0)
				.getBoolean(Constant.EKWING_ARTICLE, false);
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
	 * 设置是不是第一次进入课文听读
	 * 
	 * @param context
	 * @param isfirst
	 */
	public static void setFirstArticle(Context context, boolean isfirst) {
		context.getSharedPreferences(Constant.EKWING_IS_FIRST_ARTICLE, 0)
				.edit().putBoolean(Constant.EKWING_ARTICLE, isfirst).commit();
	}

	/**
	 * 是否是第一次进入单词听读 ，默认是没有，没有显示涂层
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getFirstWords(Context context) {
		return context
				.getSharedPreferences(Constant.EKWING_IS_FIRST_ARTICLE, 0)
				.getBoolean(Constant.EKWING_WORDS_FIRST, false);
	}

	/**
	 * 设置是不是第一次进入单词听读
	 * 
	 * @param context
	 * @param isfirst
	 */
	public static void setFirstWords(Context context, boolean isfirst) {
		context.getSharedPreferences(Constant.EKWING_IS_FIRST_ARTICLE, 0)
				.edit().putBoolean(Constant.EKWING_WORDS_FIRST, isfirst)
				.commit();
	}

	/**
	 * 是否是第一次进入听力理解 ，默认是没有，没有显示涂层
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getFirstListenUnderstand(Context context) {
		return context
				.getSharedPreferences(Constant.EKWING_IS_FIRST_ARTICLE, 0)
				.getBoolean(Constant.EKWING_LISTEN_UNDERSTAND, false);
	}

	/**
	 * 设置是不是第一次进入听力理解
	 * 
	 * @param context
	 * @param isfirst
	 */
	public static void setFirstListenUnderstand(Context context, boolean isfirst) {
		context.getSharedPreferences(Constant.EKWING_IS_FIRST_ARTICLE, 0)
				.edit().putBoolean(Constant.EKWING_LISTEN_UNDERSTAND, isfirst)
				.commit();
	}

	/**
	 * 是否是第一次进入单词听写，默认是没有，没有显示涂层
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getFirstListenWrite(Context context) {
		return context.getSharedPreferences(Constant.EKWING_IS_FIRST, 0)
				.getBoolean(Constant.EKWING_LISTEN_WRITE, false);
	}

	/**
	 * 设置是不是第一次进入单词听写
	 * 
	 * @param context
	 * @param isfirst
	 */
	public static void setFirstListenWrite(Context context, boolean isfirst) {
		context.getSharedPreferences(Constant.EKWING_IS_FIRST, 0).edit()
				.putBoolean(Constant.EKWING_LISTEN_WRITE, isfirst).commit();
	}

	/**
	 * 是否是第一次进入阅读理解，默认是没有，没有显示涂层
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getFirstReadUnderstand(Context context) {
		return context.getSharedPreferences(Constant.EKWING_IS_FIRST, 0)
				.getBoolean(Constant.EKWING_READ_UNDERSTAND, false);
	}

	/**
	 * 设置是不是第一次进入阅读理解
	 * 
	 * @param context
	 * @param isfirst
	 */
	public static void setFirstReadUnderstand(Context context, boolean isfirst) {
		context.getSharedPreferences(Constant.EKWING_IS_FIRST, 0).edit()
				.putBoolean(Constant.EKWING_READ_UNDERSTAND, isfirst).commit();
	}

	public static boolean getFirstReadUnderstandTitle(Context context) {
		return context.getSharedPreferences(Constant.EKWING_IS_FIRST, 0)
				.getBoolean(Constant.EKWING_READ_UNDERSTAND_TITLE, false);
	}

	/**
	 * 设置是不是第一次进入阅读理解
	 * 
	 * @param context
	 * @param isfirst
	 */
	public static void setFirstReadUnderstandTitle(Context context,
			boolean isfirst) {
		context.getSharedPreferences(Constant.EKWING_IS_FIRST, 0).edit()
				.putBoolean(Constant.EKWING_READ_UNDERSTAND_TITLE, isfirst)
				.commit();
	}

	/**
	 * 单词听读是否自动播放，默认是不能播放的
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAutoPlay(Context context) {
		return context.getSharedPreferences(Constant.EKWING_IS_AUTO_PLAY, 0)
				.getBoolean(Constant.EKWING_WORDS_PLAY, false);
	}

	/**
	 * 设置是否自动播放
	 * 
	 * @param context
	 * @param isauto
	 */
	public static void setAutoPlay(Context context, boolean isauto) {
		context.getSharedPreferences(Constant.EKWING_IS_AUTO_PLAY, 0).edit()
				.putBoolean(Constant.EKWING_WORDS_PLAY, isauto).commit();
	}

	/**
	 * 存储作业信息
	 * 
	 * @param context
	 * @param bean
	 */
	public static void saveHWJson(Context context, List<HWIndexBean> lists,
			String time) {
		if (lists != null) {
			ArrayList<HWIndexBean> buffLists = new ArrayList<HWIndexBean>();
			ArrayList<HWIndexBean> compList = new ArrayList<HWIndexBean>();
			if (lists != null && lists.size() > 0) {
				for (HWIndexBean bean : lists) {
					if ("2".equals(bean.getHwStatus())) {
						buffLists.add(bean);
					} else {
						compList.add(bean);
					}
				}
				buffLists.addAll(compList);
			}

			ArrayList<HWIndexBean> pro = getHWJson(context, time);
			time = time.substring(0, 6) + "d";
			if (pro != null) {
				for (int i = 0; i < pro.size(); i++) {
					for (int j = 0; j < buffLists.size(); j++) {
						if (pro.get(i).getHwId()
								.equals(buffLists.get(j).getHwId())) {
							String proStu = pro.get(i).getHwStatus();
							String listStu = buffLists.get(j).getHwStatus();
							// if ("1".equals(listStu)) {
							// buffLists.get(j).setHwStatus(listStu);
							// } else {
							if ("3".equals(proStu)) {
								buffLists.get(j).setHwStatus(proStu);
							} else {
								buffLists.get(j).setHwStatus(listStu);
								// }
							}
						}
					}
				}
			} else {
				int hwSize = buffLists.size();
				for (int i = 0; i < hwSize; i++) {
					buffLists.get(i).setHwStatus("3");
				}
			}

			String json = JSON.toJSONString(buffLists, true);
			context.getSharedPreferences(
					Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
					.edit().putString(time, json).commit();
		} else {
			context.getSharedPreferences(
					Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
					.edit().putString(time, null).commit();
		}
	}

	public static void saveHWJson1(Context context, List<HWIndexBean> lists,
			String time) {
		String json = JSON.toJSONString(lists, true);
		time = time.substring(0, 6) + "d";
		// Logger.w("存储", "getHWJson--->" + time);
		// Logger.w("存储", "json--->" +
		// context.getSharedPreferences(Constant.SP_EKWING_HW,
		// 0).getString(time, null));
		context.getSharedPreferences(
				Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
				.edit().putString(time, json).commit();
	}

	public static void saveHWJson2(Context context, List<HHWBean> lists,
			String time) {
		time = time.substring(0, 6) + "d";
		if (lists != null) {
			ArrayList<HHWBean> buffLists = new ArrayList<HHWBean>();
			ArrayList<HHWBean> compList = new ArrayList<HHWBean>();
			if (lists != null && lists.size() > 0) {
				for (HHWBean bean : lists) {
					if ("2".equals(bean.getHwStatus())) {
						buffLists.add(bean);
					} else {
						compList.add(bean);
					}
				}
				buffLists.addAll(compList);
			}
			String json = JSON.toJSONString(buffLists, true);
			context.getSharedPreferences(
					Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
					.edit().putString(time, json).commit();
		} else {
			context.getSharedPreferences(
					Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
					.edit().putString(time, null).commit();
		}
	}

	/**
	 * 得到作业信息
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<HWIndexBean> getHWJson(Context context, String time) {
		// Logger.e("读取", "time--------->" + time);
		time = time.substring(0, 6) + "d";
		// Logger.w("读取", "getHWJson--->" + time);
		// Logger.w("读取", "json--->" +
		// context.getSharedPreferences(Constant.SP_EKWING_HW,
		// 0).getString(time, null));

		return (ArrayList<HWIndexBean>) JSON.parseArray(
				context.getSharedPreferences(
						Constant.SP_EKWING_HW + getLoginInfo(context).getUid(),
						0).getString(time, null), HWIndexBean.class);
	}

	public static ArrayList<HHWBean> getHWJson2(Context context, String time) {
		// Logger.e("读取", "time--------->" + time);
		time = time.substring(0, 6) + "d";
		// Logger.w("读取", "getHWJson--->" + time);
		// Logger.w("读取", "json--->" +
		// context.getSharedPreferences(Constant.SP_EKWING_HW,
		// 0).getString(time, null));

		return (ArrayList<HHWBean>) JSON.parseArray(
				context.getSharedPreferences(
						Constant.SP_EKWING_HW + getLoginInfo(context).getUid(),
						0).getString(time, null), HHWBean.class);
	}

	/**
	 * 保存作业详情的信息
	 * 
	 * @param context
	 * @param list
	 * @param time
	 */
	public static void saveHWDJson(Context context, List<HHWDetailsBean> list,
			String hwId) {
		// ArrayList<HhwDetailListBean> lists = (ArrayList<HhwDetailListBean>)
		// list.get(0).getHwList();
		if (list != null) {
			// ArrayList<HhwDetailListBean> buffLists = new
			// ArrayList<HhwDetailListBean>();
			// ArrayList<HhwDetailListBean> compList = new
			// ArrayList<HhwDetailListBean>();
			// if (lists != null && lists.size() > 0) {
			// for (HhwDetailListBean bean : lists) {
			// if ("0".equals(bean.getTstatus())) {
			// buffLists.add(bean);
			// } else {
			// compList.add(bean);
			// }
			// }
			// buffLists.addAll(compList);
			// }
			// list.get(0).setHwList(buffLists);
			String json = JSON.toJSONString(list, true);
			context.getSharedPreferences(
					Constant.SP_EKWING_HW_DETAILS
							+ getLoginInfo(context).getUid(), 0).edit()
					.putString(hwId, json).commit();
		} else {
			context.getSharedPreferences(
					Constant.SP_EKWING_HW_DETAILS
							+ getLoginInfo(context).getUid(), 0).edit()
					.putString(hwId, null).commit();
		}
	}

	/**
	 * 得到作业详情的信息
	 * 
	 * @param context
	 * @param time
	 * @return
	 */
	public static ArrayList<HHWDetailsBean> getHWDJson(Context context,
			String hwId) {
		Logger.d("Share", "==share文件 名===========>"
				+ Constant.SP_EKWING_HW_DETAILS
				+ getLoginInfo(context).getUid());
		return (ArrayList<HHWDetailsBean>) JSON.parseArray(
				context.getSharedPreferences(
						Constant.SP_EKWING_HW_DETAILS
								+ getLoginInfo(context).getUid(), 0).getString(
						hwId, null), HHWDetailsBean.class);
	}

	/**
	 * 存储训练作业信息的信息
	 * 
	 * @param context
	 * @param bean
	 */
	public static void setExerciseContent(Context context,
			List<LevelBean> lists, String title) {
		String uid = getLoginInfo(context).getUid();
		ArrayList<LevelBean> pro = getExerciseDataContent(context, title);
		if (pro != null) {
			pro.addAll(lists);
		} else {
			pro = (ArrayList<LevelBean>) lists;
		}
		String json = JSON.toJSONString(pro, true);
		context.getSharedPreferences(
				Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
				.edit().putString(title + uid, json).commit();
	}

	/**
	 * 进入翼学院二级界面更新列表信息、服务器数据和本地比对，若本地有，服务器没有，则本地的不变，否则用服务器的数据更新本地的数据
	 * 
	 * @param context
	 *            上下文
	 * @param lists
	 *            服务器的lists
	 * @param title
	 *            是单词还是句子、作为标题存储
	 */
	public static void setUpdateExerciseContent(Context context,
			List<LevelBean> lists, String title) {
		if (lists == null || lists.size() <= 0) {
			return;
		}
		Logger.e("", "传进来的值==========>" + lists.size());
		Logger.e("", "传进来的值==========>" + JSON.toJSONString(lists));

		String uid = getLoginInfo(context).getUid();
		List<LevelBean> pro = new ArrayList<LevelBean>();
		pro = getExerciseDataContent(context, title);
		if (pro != null && pro.size() > 0) {
			if (lists.size() >= pro.size()) {
				pro = lists;
			} else if (pro.size() > lists.size()) {
				for (int i = 0; i < pro.size(); i++) {
					for (int j = 0; j < lists.size(); j++) {
						if (i < pro.size()) {
							if (lists.get(j).getThemeid()
									.equals(pro.get(i).getThemeid())) {
								// lists.get(j).setThemees(pro.get(i).getThemees());
								// for (int j2 = 0; j2 <
								// pro.get(i).getThemees().size(); j2++) {
								// Logger.e("sharePre", "");
								// lists.get(j).getThemees().get(j2).setLocalUrl(pro.get(i).getThemees().get(j2).getLocalUrl());
								// }
								pro.remove(i);
							}
						}
					}
				}
				pro.addAll(0, lists);
			}
		} else {
			pro = new ArrayList<LevelBean>();
			pro = lists;
		}

		// Logger.e("pros",
		// "lists.size()-----------YYYYYYY-------------------------------------===="
		// +pros.size());
		// Logger.e("pros",
		// " lists.toString()------YYYYYYY------------------------------------------===="
		// + JSON.toJSONString(pros));
		// Logger.i("pro", "pro--------YYYYYYYYYYYYY----->" + pros.size());
		String json = JSON.toJSONString(pro, true);
		context.getSharedPreferences(
				Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
				.edit().putString(title + uid, json).commit();

	}

	// /**
	// * 下载成功后设置本地的url
	// *
	// * @param context
	// * @param themeBean
	// * @param title
	// * @param themeId
	// */
	// public static void setUpdateExerciseLocalUrl(Context context, String
	// levelid, String levelUrl, String title, String themeId) {
	// // if (themeBean == null) {
	// // return;
	// // }
	// String uid = getLoginInfo(context).getUid();
	// List<LevelBean> pro = new ArrayList<LevelBean>();
	// pro = getExerciseDataContent(context, title);
	// Logger.e("setUpdateExerciseNestContent",
	// "pro----------------------------->" + pro.toString());
	// boolean breaks = false;
	// if (pro != null) {
	// for (int i = 0; i < pro.size(); i++) {
	// if (pro.get(i).getThemeid().equals(themeId)) {
	// Logger.e("setUpdateExerciseNestContent",
	// "list----------------------------->" + pro.toString());
	// List<ThemeBean> list = pro.get(i).getThemees();
	// for (int j = 0; j < list.size(); j++) {
	// if (list.get(j).getLevelid().equals(levelid) && (j + 1) < list.size()) {
	// // list.get(j + 1).setLevelStatus("2");
	// list.get(j).setLocalUrl(levelUrl);
	// breaks = true;
	// break;
	// }
	// }
	// }
	// if (breaks) {
	// break;
	// }
	// }
	// }
	// String json = JSON.toJSONString(pro, true);
	// context.getSharedPreferences(Constant.SP_EKWING_HW +
	// getLoginInfo(context).getUid(), 0).edit().putString(title + uid,
	// json).commit();
	//
	// }

	/**
	 * 闯关成功后将下一关解锁
	 * 
	 * @param context
	 *            上下文
	 * @param lists
	 *            服务器的lists
	 * @param title
	 *            是单词还是句子、作为标题存储
	 */
	public static void setUpdateExerciseNestContent(Context context,
			ThemeBean themeBean, String title, String themeId) {
		if (themeBean == null) {
			return;
		}
		String uid = getLoginInfo(context).getUid();
		List<LevelBean> pro = new ArrayList<LevelBean>();
		pro = getExerciseDataContent(context, title);
		Logger.e("setUpdateExerciseNestContent",
				"pro----------------------------->" + pro.toString());
		boolean breaks = false;
		if (pro != null) {
			for (int i = 0; i < pro.size(); i++) {
				if (pro.get(i).getThemeid().equals(themeId)) {
					Logger.e(
							"setUpdateExerciseNestContent",
							"list----------------------------->"
									+ pro.toString());
					List<ThemeBean> list = pro.get(i).getThemees();
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).getLevelid()
								.equals(themeBean.getLevelid())
								&& (j + 1) < list.size()) {
							list.get(j + 1).setLevelStatus("2");
							breaks = true;
							break;
						}
					}
				}
				if (breaks) {
					break;
				}
			}
		}
		String json = JSON.toJSONString(pro, true);
		context.getSharedPreferences(
				Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
				.edit().putString(title + uid, json).commit();

	}

	public static void setUpdateExerciseNestContent2(Context context,
			ThemeBean themeBean, String title, String themeId) {
		if (themeBean == null) {
			return;
		}
		String uid = getLoginInfo(context).getUid();
		List<LevelBean> pro = new ArrayList<LevelBean>();
		pro = getExerciseDataContent(context, title);
		// Logger.e("setUpdateExerciseNestContent",
		// "pro----------------------------->" + pro.toString());
		boolean breaks = false;
		if (pro != null) {
			for (int i = 0; i < pro.size(); i++) {
				if (pro.get(i).getThemeid().equals(themeId)) {
					Logger.e(
							"setUpdateExerciseNestContent",
							"list----------------------------->"
									+ pro.toString());
					List<ThemeBean> list = pro.get(i).getThemees();
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).getLevelid()
								.equals(themeBean.getLevelid())
								&& (j + 1) < list.size()) {
							list.get(j + 1).setIsLocked("0");
							breaks = true;
							break;
						}
					}
				}
				if (breaks) {
					break;
				}
			}
		}
		String json = JSON.toJSONString(pro, true);
		context.getSharedPreferences(
				Constant.SP_EKWING_HW + getLoginInfo(context).getUid(), 0)
				.edit().putString(title + uid, json).commit();

	}

	/**
	 * 得到训练作业信息
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<LevelBean> getExerciseDataContent(Context context,
			String title) {
		String uid = getLoginInfo(context).getUid();
		return (ArrayList<LevelBean>) JSON.parseArray(
				context.getSharedPreferences(
						Constant.SP_EKWING_HW + getLoginInfo(context).getUid(),
						0).getString(title + uid, null), LevelBean.class);
	}

	/**
	 * 得到训练单个作业的Bean,即LevelBean
	 * 
	 * @param context
	 * @return
	 */
	public static LevelBean getExerciseSingleContent(Context context,
			String title, String themeid) {
		LevelBean bean = new LevelBean();
		List<LevelBean> pro = new ArrayList<LevelBean>();
		pro = getExerciseDataContent(context, title);
		for (int i = 0; i < pro.size(); i++) {
			if (pro.get(i).getThemeid().equals(themeid)) {
				bean = pro.get(i);
			}
		}
		return bean;
	}

	/**
	 * 保存日期
	 * 
	 * @param context
	 * @param beanList
	 */
	public static void saveDateWeek(Context context,
			ArrayList<WeekBean> beanList) {
		String json = JSON.toJSONString(beanList, true);
		context.getSharedPreferences(
				Constant.SP_EKWING_DATE + getLoginInfo(context).getUid(), 0)
				.edit().putString(Constant.EKWING_DATE, json).commit();
	}

	/**
	 * 得到日期
	 * 
	 * @param context
	 * @return
	 */
	public static ArrayList<WeekBean> getDateWeek(Context context) {
		return (ArrayList<WeekBean>) JSON.parseArray(
				context.getSharedPreferences(
						Constant.SP_EKWING_DATE
								+ getLoginInfo(context).getUid(), 0).getString(
						Constant.EKWING_DATE, null), WeekBean.class);
	}

	/**
	 * 存储意见反馈信息保存
	 * 
	 * @param context
	 * @param json
	 */
	public static void setSuggestBack(Context context, String json) {
		context.getSharedPreferences(
				Constant.SP_SUGGEST + getLoginInfo(context).getUid(), 0).edit()
				.putString(Constant.SUGGEST, json).commit();
	}

	/**
	 * 获取意见反馈信息，退出本界面，再次进来能得到之前存储的信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getSuggestBack(Context context) {
		return context.getSharedPreferences(
				Constant.SP_SUGGEST + getLoginInfo(context).getUid(), 0)
				.getString(Constant.SUGGEST, null);
	}

	/**
	 * 存储登录账号的信息
	 * 
	 * @param context
	 * @param json
	 */
	public static void setLoginAccount(Context context, String json) {
		context.getSharedPreferences(Constant.SP_ACCOUNT, 0).edit()
				.putString(Constant.ACCOUNT, json).commit();
	}

	/**
	 * 获取之前登录的账号信息，重新登录时EditText中显示账号信息
	 * 
	 * @param context
	 * @return
	 */
	public static String getLoginAccount(Context context) {
		return context.getSharedPreferences(Constant.SP_ACCOUNT, 0).getString(
				Constant.ACCOUNT, null);
	}

	/**
	 * 设置气泡的数据
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBubbleNums(Context context, String key, int value) {
		context.getSharedPreferences(Constant.BUBBLE, 0).edit()
				.putInt(key, value).commit();
	}

	/**
	 * 得到气泡的数量
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getBubblesNums(Context context, String key) {
		return context.getSharedPreferences(Constant.BUBBLE, 0).getInt(key, 0);
	}

	/**
	 * 登陆账户查看
	 * 
	 * @param context
	 * @param key
	 *            :
	 * @param value
	 */
	public static void setLoginSchool(Context context, String value) {
		context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0).edit()
				.putString(Constant.LOGIN_SELECT_SCHOOL_ID, value).commit();
	}

	/**
	 * 登陆学习存储
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static String getLoginSchool(Context context) {

		return context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0)
				.getString(Constant.LOGIN_SELECT_SCHOOL_ID, "");
	}

	public static void setLoginSchoolName(NetWorkActivity context,
			String stringExtra) {
		context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0).edit()
				.putString(Constant.LOGIN_SELECT_SCHOOL_NAME, stringExtra)
				.commit();
	}

	public static String getLoginSchoolName(Context context) {
		return context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0)
				.getString(Constant.LOGIN_SELECT_SCHOOL_NAME, "");
	}

	public static void setLoginProvince(NetWorkActivity context,
			String stringExtra) {
		context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0).edit()
				.putString(Constant.LOGIN_SELECT_PROVINCE, stringExtra)
				.commit();
	}

	public static String getLoginProvince(Context context) {
		return context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0)
				.getString(Constant.LOGIN_SELECT_PROVINCE, "");
	}

	public static void setLoginProvinceId(NetWorkActivity context,
			String stringExtra) {
		context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0).edit()
				.putString(Constant.LOGIN_SELECT_PROVINCE_ID, stringExtra)
				.commit();
	}

	public static String getLoginProvinceId(Context context) {
		return context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0)
				.getString(Constant.LOGIN_SELECT_PROVINCE_ID, "");
	}

	/**
	 * 得到连读开关状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getContinueRead(Context context) {
		String uid = getLoginInfo(context).getUid();
		return context.getSharedPreferences(Constant.SP_SETTING, 0).getBoolean(
				Constant.SP_CONTINUE_PLAY + uid, true);
	}

	/**
	 * 设置连读开关状态
	 * 
	 * @param context
	 * @param islogin
	 */
	public static void setContinueRead(Context context, boolean isopen) {
		String uid = getLoginInfo(context).getUid();
		context.getSharedPreferences(Constant.SP_SETTING, 0).edit()
				.putBoolean(Constant.SP_CONTINUE_PLAY + uid, isopen).commit();
	}

	/**
	 * 得到智能纠错的开关状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getCorrection(Context context) {
		// 如果用户是vip，那么智能纠错默认开启
		boolean isvip = context.getSharedPreferences(Constant.SP_USERINFO, 0)
				.getBoolean(Constant.SP_VIP, false);
		String uid = getLoginInfo(context).getUid();
		return context.getSharedPreferences(Constant.SP_SETTING, 0).getBoolean(
				Constant.SP_CORRECT + uid, isvip);
	}

	/**
	 * 设置智能纠错的开关状态
	 * 
	 * @param context
	 * @param islogin
	 */
	public static void setCorrection(Context context, boolean isopen) {
		String uid = getLoginInfo(context).getUid();
		context.getSharedPreferences(Constant.SP_SETTING, 0).edit()
				.putBoolean(Constant.SP_CORRECT + uid, isopen).commit();
	}

	/**
	 * 存储省，到选择省时跳到相应的位置
	 * 
	 * @param context
	 * @param stringExtra
	 */
	public static void setRealProvince(NetWorkActivity context,
			String stringExtra) {
		context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0).edit()
				.putString(Constant.REAL_SELECT_PROVINCE, stringExtra).commit();
	}

	/**
	 * 得到省，跳到相应的位置
	 * 
	 * @param context
	 * @return
	 */
	public static String getRealProvince(Context context) {
		return context.getSharedPreferences(Constant.SP_SCHOOL_SELECT, 0)
				.getString(Constant.REAL_SELECT_PROVINCE, "");
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
