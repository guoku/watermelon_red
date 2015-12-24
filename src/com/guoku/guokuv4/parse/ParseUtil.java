package com.guoku.guokuv4.parse;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.guokuv4.bean.CategoryBean;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.ContentBean;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.MessageBean;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.guoku.guokuv4.entity.test.PBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.PointBean;
import com.guoku.guokuv4.entity.test.TabNoteBean;
import com.guoku.guokuv4.entity.test.TabTagBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.entity.test.UserTagBean;
import com.guoku.guokuv4.utils.SharePrenceUtil;

import android.content.Context;

public class ParseUtil {

	public static ArrayList<PBean> getJingXuanList(String result) {
		ArrayList<PBean> productBeans = new ArrayList<PBean>();
		try {
			JSONArray root = new JSONArray(result);
			JSONObject item;
			PBean bean;
			for (int i = 0; i < root.length(); i++) {
				bean = new PBean();
				item = root.getJSONObject(i);
				ContentBean contentBean = (ContentBean) JSON.parseObject(
						item.getString("content"), ContentBean.class);
				bean.setContent(contentBean);
				bean.setPost_time(item.getString("post_time"));
				bean.setType(item.getString("type"));
				productBeans.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productBeans;
	}

	public static ArrayList<EntityBean> getTabLikeList(String result) {
		ArrayList<EntityBean> productBeans = new ArrayList<EntityBean>();
		try {
			JSONObject root = new JSONObject(result);
			productBeans = (ArrayList<EntityBean>) JSON.parseArray(
					root.getString("entity_list"), EntityBean.class);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productBeans;
	}

	public static ArrayList<PointBean> getPointList(String result) {
		ArrayList<PointBean> productBeans = new ArrayList<PointBean>();
		try {
			productBeans = (ArrayList<PointBean>) JSON.parseArray(result,
					PointBean.class);
			Logger.i("getPointList", "getPointList-->" + productBeans.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return productBeans;
	}

	public static ArrayList<MessageBean> getMessageList(String result) {
		ArrayList<MessageBean> productBeans = new ArrayList<MessageBean>();
		productBeans = (ArrayList<MessageBean>) JSON.parseArray(result,
				MessageBean.class);
		Logger.i("getPointList", "getPointList-->" + productBeans.toString());
		return productBeans;
	}

	public static ArrayList<TabNoteBean> getTabNoteList(String result) {
		ArrayList<TabNoteBean> productBeans = new ArrayList<TabNoteBean>();
		productBeans = (ArrayList<TabNoteBean>) JSON.parseArray(result,
				TabNoteBean.class);
		return productBeans;
	}

	public static ArrayList<UserTagBean> getTabTagList(String result) {
		ArrayList<UserTagBean> productBeans = new ArrayList<UserTagBean>();
		TabTagBean bean = JSON.parseObject(result, TabTagBean.class);
		productBeans = bean.getTags();
		return productBeans;
	}

	public static ArrayList<EntityBean> getGuessList(String result) {
		ArrayList<EntityBean> productBeans = new ArrayList<EntityBean>();
		try {
			JSONArray root = new JSONArray(result);
			JSONObject item;
			EntityBean bean;
			for (int i = 0; i < root.length(); i++) {
				bean = new EntityBean();
				item = root.getJSONObject(i);
				bean = JSON.parseObject(item.toString(), EntityBean.class);
				productBeans.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return productBeans;
	}

	public static ArrayList<CategoryBean> getTab2List(Context context) {

		try {
			ArrayList<CategoryBean> cBean = (ArrayList<CategoryBean>) JSON.parseArray(SharePrenceUtil.getTabList(context), CategoryBean.class);
			return cBean;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}



	public static PInfoBean getPI(String result) {
		PInfoBean rootBean = new PInfoBean();
		try {
			JSONObject root = new JSONObject(result);
			EntityBean entityBean = (EntityBean) JSON.parseObject(
					root.getString("entity"), EntityBean.class);
			rootBean.setEntity(entityBean);
			JSONArray array1 = root.getJSONArray("note_list");
			JSONArray array2 = root.getJSONArray("like_user_list");
			ArrayList<NoteBean> noteBeans = new ArrayList<NoteBean>();
			ArrayList<UserBean> userBeans = new ArrayList<UserBean>();
			for (int i = 0; i < array1.length(); i++) {
				noteBeans.add(JSON.parseObject(array1.getString(i),
						NoteBean.class));
			}
			for (int i = 0; i < array2.length(); i++) {
				userBeans.add(JSON.parseObject(array2.getString(i),
						UserBean.class));
			}
			rootBean.setLike_user_list(userBeans);
			rootBean.setNote_list(noteBeans);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Logger.e("getPI", "rootBean-->" + rootBean.toString());
		return rootBean;
	}
}
