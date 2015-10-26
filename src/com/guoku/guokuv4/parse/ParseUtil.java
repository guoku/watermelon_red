package com.guoku.guokuv4.parse;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.ContentBean;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.MessageBean;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.guoku.guokuv4.entity.test.PBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.PointBean;
import com.guoku.guokuv4.entity.test.TAB1Bean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.entity.test.TabNoteBean;
import com.guoku.guokuv4.entity.test.TabTagBean;
import com.guoku.guokuv4.entity.test.UserTagBean;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.entity.test.UserBean;

public class ParseUtil {

	private static ArrayList<Tab2Bean> tab1Beans;

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
		productBeans = (ArrayList<PointBean>) JSON.parseArray(result,
				PointBean.class);
		Logger.i("getPointList", "getPointList-->" + productBeans.toString());
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

	public static ArrayList<TAB1Bean> getTabList(String result) {
		if (result == null || "".equals(result)) {
			return new ArrayList<TAB1Bean>();
		}
		ArrayList<TAB1Bean> tab1Beans = new ArrayList<TAB1Bean>();
		try {
			JSONArray root = new JSONArray(result);
			int size1 = root.length();
			int size2;
			TAB1Bean tab1Bean;
			for (int i = 0; i < size1; i++) {
				tab1Bean = new TAB1Bean();
				tab1Bean.setCategory_count(root.getJSONObject(i).getString(
						"category_count"));
				tab1Bean.setGroup_id(root.getJSONObject(i)
						.getString("group_id"));
				tab1Bean.setStatus(root.getJSONObject(i).getString("status"));
				tab1Bean.setTitle(root.getJSONObject(i).getString("title"));
				JSONArray array = root.getJSONObject(i).getJSONArray("content");
				size2 = array.length();
				ArrayList<Tab2Bean> list1 = new ArrayList<Tab2Bean>();
				ArrayList<Tab2Bean> list2 = new ArrayList<Tab2Bean>();
				Tab2Bean tab2Bean;
				for (int j = 0; j < size2; j++) {
					tab2Bean = new Tab2Bean();
					tab2Bean = JSON.parseObject(array.getString(j),
							Tab2Bean.class);
					if (tab2Bean.getStatus().equals("1")) {
						list1.add(tab2Bean);
					} else {
						list2.add(tab2Bean);
					}
				}
				tab1Bean.setList1(list1);
				tab1Bean.setList2(list2);
				tab1Beans.add(tab1Bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tab1Beans;
	}

	public static ArrayList<Tab2Bean> getTab2List(Context context) {

		ArrayList<Tab2Bean> tab1Beans = new ArrayList<Tab2Bean>();
		ArrayList<TAB1Bean> list = SharePrenceUtil.getTabList(context);
		Logger.i("root", list.toString());
		for (TAB1Bean bean : list) {
			tab1Beans.addAll(bean.getList1());
		}
		return tab1Beans;
	}

	public static ArrayList<Tab2Bean> getTab2ALL(Context context) {
		if (tab1Beans == null) {
			tab1Beans = new ArrayList<Tab2Bean>();
			ArrayList<TAB1Bean> list = SharePrenceUtil.getTabList(context);
			Logger.i("root", list.toString());
			for (TAB1Bean bean : list) {
				tab1Beans.addAll(bean.getList1());
				tab1Beans.addAll(bean.getList2());
			}
		}
		return tab1Beans;
	}

	public static ArrayList<TAB1Bean> getTab1List(String result) {
		if (result == null || "".equals(result)) {
			return new ArrayList<TAB1Bean>();
		}
		ArrayList<TAB1Bean> tab1Beans = new ArrayList<TAB1Bean>();
		try {
			JSONArray root = new JSONArray(result);
			int size1 = root.length();
			int size2;
			TAB1Bean tab1Bean;
			for (int i = 0; i < size1; i++) {
				if (root.getJSONObject(i).getString("status").equals("0")) {
					continue;
				}
				tab1Bean = new TAB1Bean();
				tab1Bean.setCategory_count(root.getJSONObject(i).getString(
						"category_count"));
				tab1Bean.setGroup_id(root.getJSONObject(i)
						.getString("group_id"));
				tab1Bean.setStatus(root.getJSONObject(i).getString("status"));
				tab1Bean.setTitle(root.getJSONObject(i).getString("title"));
				JSONArray array = root.getJSONObject(i).getJSONArray("content");
				size2 = array.length();
				ArrayList<Tab2Bean> list1 = new ArrayList<Tab2Bean>();
				ArrayList<Tab2Bean> list2 = new ArrayList<Tab2Bean>();
				Tab2Bean tab2Bean;
				for (int j = 0; j < size2; j++) {
					tab2Bean = new Tab2Bean();
					tab2Bean = JSON.parseObject(array.getString(j),
							Tab2Bean.class);
					if (tab2Bean.getStatus().equals("1")) {
						list1.add(tab2Bean);
					} else {
						list2.add(tab2Bean);
					}
				}
				tab1Bean.setList1(list1);
				tab1Bean.setList2(list2);
				tab1Beans.add(tab1Bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tab1Beans;
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
