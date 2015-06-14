package com.ekwing.students.connection;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Logger;
import com.ekwing.students.utils.SharePrenceUtil;
import com.ekwing.students.utils.ToastUtil;
import com.ekwing.students.utils.Utils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class LightHttpUtils {
	public static final int ON_START = 0;
	private static final int ON_LOADING = 1;
	public static final int ON_FAIL = 3;

//	public static void getDataByNet(final Handler handler, String url, String[] key, String[] value, final int what) {
//		RequestParams params = new RequestParams();
//		for (int i = 0; i < key.length; i++) {
//			params.addQueryStringParameter(key[i], value[i]);
//		}
//		// 附加参数
////		params.addQueryStringParameter("referer", "android_traveler_app");
//		com.lidroid.xutils.HttpUtils utils = new com.lidroid.xutils.HttpUtils();
//		utils.configUserAgent(EkwingApplication.userAgent);
//		utils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
//
//			@Override
//			public void onStart() {
//				handler.sendEmptyMessage(ON_START);
//				Logger.i("hu", "start:" + getRequestUrl());
//			}
//
//			@Override
//			public void onLoading(long total, long current, boolean isUploading) {
//				Logger.i("hu", "onLoading:" + current);
//
//				Message message = Message.obtain();
//				message.what = ON_LOADING;
//				message.arg1 = (int) current;
//				handler.sendMessage(message);
//			}
//
//			@Override
//			public void onSuccess(ResponseInfo<String> responseInfo) {
//				Logger.i("hu", "onSuccess:" + responseInfo.result);
//
//				String result = responseInfo.result;
//				Message message = Message.obtain();
//				message.what = what;
//				message.obj = result;
//				handler.sendMessage(message);
//			}
//
//			@Override
//			public void onFailure(HttpException error, String msg) {
//				Logger.i("hu", "onFailure:" + msg);
//
//				Message message = Message.obtain();
//				message.what = ON_FAIL;
//				message.obj = msg;
//				handler.sendMessage(message);
//
//			}
//		});
//	}

	public static void getDataByNetPost(final Context context, final Handler handler, String url, String[] key, String[] value, final int what,boolean istoken) {
		Logger.d("lightHttpUtils", "pramas=========>url:"+url);
		RequestParams params = new RequestParams();
		for (int i = 0; i < key.length; i++) {
			params.addBodyParameter(key[i], value[i]);
			Logger.d("lightHttpUtils", "pramas=========>"+key[i]+":"+value[i]);
		}
		if(istoken){
			params.addBodyParameter("driverCode", Utils.getVersionName(context));
			params.addBodyParameter("token", SharePrenceUtil.getLoginInfo(context).getToken());
			params.addBodyParameter("author_id", SharePrenceUtil.getLoginInfo(context).getUid());
			Logger.d("lightHttpUtils", "pramas=========>driverCode:"+Utils.getVersionName(context));
			Logger.d("lightHttpUtils", "pramas=========>token:"+SharePrenceUtil.getLoginInfo(context).getToken());
			Logger.d("lightHttpUtils", "pramas=========>author_id"+SharePrenceUtil.getLoginInfo(context).getUid());
			
		}

		// 附加参数
		// params.addQueryStringParameter("referer", "android_traveler_app");

		com.lidroid.xutils.HttpUtils utils = new com.lidroid.xutils.HttpUtils();
		utils.configUserAgent(EkwingApplication.userAgent);
		utils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				handler.sendEmptyMessage(ON_START);
				Logger.i("hu", "start:" + getRequestUrl());
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				Logger.i("hu", "onLoading:" + current);

				Message message = Message.obtain();
				message.what = ON_LOADING;
				message.arg1 = (int) current;
				handler.sendMessage(message);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Logger.i("hu", "onSuccess:" + responseInfo.result);

				String result = responseInfo.result;
				Message message = Message.obtain();
				message.what = what;
				message.obj = result;
				handler.sendMessage(message);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Logger.i("hu", "onFailure:" + msg);

				Message message = Message.obtain();
				message.what = ON_FAIL;
				message.obj = msg;
				handler.sendMessage(message);

			}
		});
	}
	public static void getDataByNetPost2(final Context context, final Handler handler, String url, String[] key, String[] value, final int what) {
		Logger.d("lightHttpUtils", "pramas=========>url:"+url);
		RequestParams params = new RequestParams();
		for (int i = 0; i < key.length; i++) {
			params.addBodyParameter(key[i], value[i]);
			Logger.d("lightHttpUtils", "pramas=========>"+key[i]+":"+value[i]);
		}
		com.lidroid.xutils.HttpUtils utils = new com.lidroid.xutils.HttpUtils();
		utils.configUserAgent(EkwingApplication.userAgent);
		utils.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {
			
			@Override
			public void onStart() {
				handler.sendEmptyMessage(ON_START);
				Logger.i("hu", "start:" + getRequestUrl());
			}
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				Logger.i("hu", "onLoading:" + current);
				
				Message message = Message.obtain();
				message.what = ON_LOADING;
				message.arg1 = (int) current;
				handler.sendMessage(message);
			}
			
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Logger.i("hu", "onSuccess:" + responseInfo.result);
				
				String result = responseInfo.result;
				Message message = Message.obtain();
				message.what = what;
				message.obj = result;
				handler.sendMessage(message);
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				Logger.i("hu", "onFailure:" + msg);
				
				Message message = Message.obtain();
				message.what = ON_FAIL;
				message.obj = msg;
				handler.sendMessage(message);
				
			}
		});
	}

	public static void getDataByNetGet(final Handler handler, String url, String[] key, String[] value, final int what) {
		RequestParams params = new RequestParams();
		for (int i = 0; i < key.length; i++) {
			params.addQueryStringParameter(key[i], value[i]);
		}
		// 附加参数
		// params.addQueryStringParameter("referer" , "android_traveler_app");

		com.lidroid.xutils.HttpUtils utils = new com.lidroid.xutils.HttpUtils();
		utils.configUserAgent(EkwingApplication.userAgent);
		utils.configTimeout(30 * 1000);
		utils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<String>() {

			@Override
			public void onStart() {
				handler.sendEmptyMessage(ON_START);
				Logger.i("hu", "start:" + getRequestUrl());
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				Logger.i("hu", "onLoading:" + current);

				Message message = Message.obtain();
				message.what = ON_LOADING;
				message.arg1 = (int) current;
				handler.sendMessage(message);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				Logger.i("hu", "onSuccess:" + responseInfo.result);

				String result = responseInfo.result;
				Message message = Message.obtain();
				message.what = what;
				message.obj = result;
				handler.sendMessage(message);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Logger.i("hu", "onFailure:" + msg);

				Message message = Message.obtain();
				message.what = ON_FAIL;
				message.obj = msg;
				handler.sendMessage(message);

			}
		});
	}

	public static boolean isRigth(String json) {
		boolean isRight = false;
		if (json == null) {
			return isRight;
		}
		try {
			JSONObject object = new JSONObject(json);
			if (object.has("status")) {
				if ("1".equals(object.getString("status"))) {
					isRight = false;
				} else if ("0".equals(object.getString("status"))) {
					isRight = true;
				} else
					isRight = false;

			} else
				isRight = false;
		} catch (JSONException e) {
			e.printStackTrace();
			isRight = false;
			// Logger.e("httputils", "isRight---------------------------------"
			// + isRight);
		}
		return isRight;
	}

	public static void showError(String json, Context context) {
		JSONObject object;
		// Logger.e("errlog", "errlog-------------->" + json);
		if (json != null) {
			try {
				object = new JSONObject(json);
				if (object.has("data")) {
					if (object.getJSONObject("data").has("errlog")) {
						Toast.makeText(context, object.getJSONObject("data").getString("errlog"), Toast.LENGTH_SHORT).show();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// Logger.e("JSONException", "JSONException----------->" +
				// e.toString());
			}
		}

	}

	public static String getErrorMsg(String json, Context context) {
		String msg = "";
		JSONObject object;
		try {
			object = new JSONObject(json);
			if (object.has("error_msg")) {
				msg = object.getString("error_msg");

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 返回失败errlog
	 * 
	 * @param context
	 * @param result
	 */
	public static void showFailureResult(Context context, String result) {
		try {
			JSONObject root = new JSONObject(result);
			if (root.has("errlog")) {
				ToastUtil.show(context, root.getString("errlog"));
			}
		} catch (JSONException e) {
			ToastUtil.timeOut(context);
			e.printStackTrace();
		}
	}
}
