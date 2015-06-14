package com.ekwing.students.utils;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;

import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.entity.UploadIdBean;
import com.ekwing.students.entity.WordsBean;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.analytics.MobclickAgent;

public class UploadUtils extends RequestCallBack<String> {

	private HttpUtils http;
	private Context context;
	private int position;
	private DbUtils dbutils;
	private ArrayList<WordsBean> lists;
	private String tid;
	private String url;

	public UploadUtils(Context context) {
		http = new HttpUtils();
		this.context = context;
	}

	public void upload(String url, ArrayList<WordsBean> lists, String tid,
			int position, DbUtils dbutils) {
		RequestParams params = new RequestParams();
		this.position = position;
		this.dbutils = dbutils;
		this.lists = lists;
		this.url = url;
		this.tid = tid;
		UploadIdBean bean = new UploadIdBean();
		bean.setSid(lists.get(position).getId());
		bean.setPath(Constant.RECORD_PATH
				+ lists.get(position).getReSoundPath() + ".wav");
		bean.setCid(tid);
		bean.setType("1");
		params.addBodyParameter(bean.getSid(), new File(bean.getPath()));
		try {
			dbutils.saveOrUpdate(bean);
		} catch (DbException e) {
			e.printStackTrace();
		}
		params.addBodyParameter("type", "1");
		params.addBodyParameter("cid", tid);
		// params.addBodyParameter("fileKeys", fileKeys);
		params.addBodyParameter("driverCode", Utils.getVersionName(context));
		params.addBodyParameter("token", SharePrenceUtil.getLoginInfo(context)
				.getToken());
		params.addBodyParameter("author_id",
				SharePrenceUtil.getLoginInfo(context).getUid());
		http.send(HttpRequest.HttpMethod.POST, Constant.EKWING_UPGRADES,
				params, this);

	}

	@Override
	public void onStart() {
		super.onStart();
		Logger.e("UploadUtils", "onStart--------------------->" + position);
	}

	@Override
	public void onLoading(long total, long current, boolean isUploading) {
		super.onLoading(total, current, isUploading);
		Logger.e("UploadUtils", "onLoading--------------------->" + total + ":"
				+ current + ":" + isUploading);
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {
		Logger.d("UploadUtils", "onFailure--------------------->" + arg1);
		lists.get(position).setIsSubmit("2");
		if (NetUtil.checkNetWork(context)) {
			upload(url, lists, tid, position, dbutils);
		}
	}

	@Override
	public void onSuccess(ResponseInfo<String> arg0) {
		Logger.e("uploadUtils", "onSuccess--------------------->" + arg0.result);
		try {
			JSONObject root = new JSONObject(arg0.result);
			if (root.has("status") && "0".equals(root.getString("status"))) {
				// dbutils.deleteAll(lists);
				lists.get(position).setIsSubmit("1");
				Logger.v("uploadUtils", "onSuccess============>" + position);
				Logger.v("uploadUtils", "onSuccess=====getIsSubmit=======>"
						+ lists.get(position).getIsSubmit());
				JSONObject data = root.getJSONObject("data");
				if (data.has("success")) {
					String sid = data.getString("success");
					String[] ids = sid.split(",");
					for (int i = 0; i < ids.length; i++) {
						dbutils.delete(UploadIdBean.class,
								WhereBuilder.b("sid", "==", ids[i]));
					}
				}
				// dbutils.delegteAll(lists);
			}

		} catch (Exception e) {
			e.printStackTrace();
			MobclickAgent.onEvent(context, "ykxs228");
		}
	}
}
