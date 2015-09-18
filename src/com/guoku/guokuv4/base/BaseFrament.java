package com.guoku.guokuv4.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Constant;
import com.ekwing.students.customview.CustomProgressDialog;
import com.ekwing.students.utils.HttputilHelp;
import com.ekwing.students.utils.NetUtil;
import com.ekwing.students.utils.SharePrenceUtil;
import com.ekwing.students.utils.StringUtil;
import com.ekwing.students.utils.ToastUtil;
import com.ekwing.students.utils.Utils;
import com.guoku.R;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.utils.LogGK;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;

public abstract class BaseFrament extends Fragment {
	
	protected final String TAG = getClass().getSimpleName();
	private CustomProgressDialog progressDialog;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private HttpUtils httpUtils;
	protected Activity context;
	private Vector<Integer> vector;
	private List<NameValuePair> bodyParams;
	protected Handler mHandler;
	protected View contentView;
	protected int w = EkwingApplication.screenW;
	protected DisplayImageOptions options;
	protected DisplayImageOptions optionsRound;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		progressDialog = new CustomProgressDialog(context);
		progressDialog.setMessage("载入中...");
		httpUtils = HttputilHelp.getHttpUtils();
		httpUtils.configUserAgent(EkwingApplication.userAgent);
		vector = new Vector<Integer>();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		imageLoader.clearMemoryCache();
	}

	private static String genSign(Map<String, String> paramsMap) {

		StringBuffer sb = new StringBuffer();
		paramsMap.put("api_key", "0b19c2b93687347e95c6b6f5cc91bb87");
		Set<String> keySet = paramsMap.keySet();
		if (keySet.size() > 0) {
			for (String key : keySet) {
				sb.append(key).append("=").append(paramsMap.get(key));
			}
		}
		sb.append("47b41864d64bd46");
		String strParam = sb.toString();
		String sign = StringUtil.md5(strParam);
		LogGK.e(sign);
		return sign;
	}

	protected abstract void init();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(getContentId(), container, false);
		ViewUtils.inject(this, contentView);
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.drawable.item100)
				.showImageForEmptyUri(R.drawable.item100).cacheInMemory(true)
				.showImageOnFail(R.drawable.item100).build();
		optionsRound = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true)
				.displayer(new RoundedBitmapDisplayer(90))
				.showImageForEmptyUri(R.drawable.user100)
				.showImageOnFail(R.drawable.user100)
				.showImageOnLoading(R.drawable.user100).build();
		init();
		setData();
		return contentView;
	}

	protected abstract int getContentId();

	/**
	 * 设置加载对话框是否可以被取消
	 * 
	 * @param canable
	 */
	public void setProgressCancelable(boolean canable) {
		progressDialog.setCancelable(canable);
	}

	/**
	 * 设置加载对话框提示的文字
	 * 
	 * @param message
	 */
	public void setProgressDialogMessage(String message) {
		progressDialog.setMessage(message);
	}

	/**
	 * 发送http请求
	 * 
	 * @param url
	 * @param argsKeys
	 * @param argsValues
	 * @param where
	 * @param showDialog
	 *            是否显示进度条
	 */
	private void sendConnection(HttpMethod method, String url,
			String[] argsKeys, String[] argsValues, int where,
			boolean showDialog, int umengId) {
		if (argsKeys.length != argsValues.length) {
			throw new IllegalArgumentException(
					"check your Params key or value length!");
		}
		if (showDialog) {
			vector.add(where);
		}
		HttpCallBack httpCallback = new HttpCallBack(where, showDialog, umengId);
		RequestParams params = new RequestParams();
		Map<String, String> paramsMap = new TreeMap<String, String>();
		for (int i = 0; i < argsKeys.length; i++) {
			params.addQueryStringParameter(argsKeys[i], argsValues[i]);
			LogGK.e(argsKeys[i] + ":"
					+ argsValues[i]);
			paramsMap.put(argsKeys[i], argsValues[i]);
		}
		if (EkwingApplication.getInstance().getBean() != null) {
			params.addQueryStringParameter("session", EkwingApplication
					.getInstance().getBean().getSession());
			paramsMap.put("session", EkwingApplication.getInstance().getBean()
					.getSession());
		}

		params.addQueryStringParameter("sign", genSign(paramsMap));
		params.addQueryStringParameter("api_key",
				"0b19c2b93687347e95c6b6f5cc91bb87");

		// params.addQueryStringParameter("token",
		// SharePrenceUtil.getLoginInfo(getActivity()).getToken());
		// params.addQueryStringParameter("v",
		// Utils.getVersionName(getActivity()));
		// params.addQueryStringParameter("author_id", SharePrenceUtil
		// .getLoginInfo(getActivity()).getUid());
		// params.addQueryStringParameter("uid",
		// SharePrenceUtil.getLoginInfo(getActivity()).getUid());
		LogGK.e("params----->" + ":" + params.toString());
		if (showDialog && !getActivity().isFinishing()) {
			showDialog();
		}
		httpUtils.send(method, url, params, httpCallback);
	}

	private void sendConnectionPost(HttpMethod method, String url,
			String[] argsKeys, String[] argsValues, int where,
			boolean showDialog, int umengId) {
		if (argsKeys.length != argsValues.length) {
			throw new IllegalArgumentException(
					"check your Params key or value length!");
		}
		if (showDialog) {
			vector.add(where);
		}
		HttpCallBack httpCallback = new HttpCallBack(where, showDialog, umengId);
		RequestParams params = new RequestParams();
		Map<String, String> paramsMap = new TreeMap<String, String>();
		for (int i = 0; i < argsKeys.length; i++) {
			params.addBodyParameter(argsKeys[i], argsValues[i]);
			LogGK.e("params----->" + argsKeys[i] + ":"
					+ argsValues[i]);
			paramsMap.put(argsKeys[i], argsValues[i]);
		}
		if (EkwingApplication.getInstance().getBean() != null) {
			params.addBodyParameter("session", EkwingApplication.getInstance()
					.getBean().getSession());
			paramsMap.put("session", EkwingApplication.getInstance().getBean()
					.getSession());
		} else if (Constant.LOGIN.equals(url) || Constant.REGISTER.equals(url)) {

		} else {
			Intent intent = new Intent(getActivity(), LoginAct.class);
			startActivity(intent);
			return;
		}
		params.addBodyParameter("sign", genSign(paramsMap));
		params.addBodyParameter("api_key", "0b19c2b93687347e95c6b6f5cc91bb87");

		LogGK.e("params----->" + ":" + params.toString());
		if (showDialog && !getActivity().isFinishing()) {
			showDialog();
		}
		httpUtils.send(method, url, params, httpCallback);
	}

	private void sendConnectionNoLoginOrPsw(HttpMethod method, String url,
			String[] argsKeys, String[] argsValues, int where,
			boolean showDialog, int umengId) {
		if (argsKeys.length != argsValues.length) {
			throw new IllegalArgumentException(
					"check your Params key or value length!");
		}
		if (showDialog) {
			vector.add(where);
		}
		HttpCallBack httpCallback = new HttpCallBack(where, showDialog, umengId);
		RequestParams params = new RequestParams();
		for (int i = 0; i < argsKeys.length; i++) {
			params.addBodyParameter(argsKeys[i], argsValues[i]);
			LogGK.e("=========length==============>" + argsKeys.length);
			LogGK.e("params----->" + argsKeys[i] + ":"
					+ argsValues[i]);
		}
		LogGK.e("params----->v" + ":" + Utils.getVersionName(getActivity()));
		LogGK.e("params----->token" + ":"
				+ SharePrenceUtil.getLoginInfo(getActivity()).getToken());
		LogGK.e("params----->author_id" + ":"
				+ SharePrenceUtil.getLoginInfo(getActivity()).getUid());
		LogGK.e("params----->uid" + ":"
				+ SharePrenceUtil.getLoginInfo(getActivity()).getUid());
		params.addBodyParameter("v", Utils.getVersionName(getActivity()));
		params.addBodyParameter("token",
				SharePrenceUtil.getLoginInfo(getActivity()).getToken());
		params.addBodyParameter("author_id",
				SharePrenceUtil.getLoginInfo(getActivity()).getUid());
		params.addBodyParameter("uid",
				SharePrenceUtil.getLoginInfo(getActivity()).getUid());

		if (showDialog && !getActivity().isFinishing()) {
			showDialog();
		}
		httpUtils.send(method, url, params, httpCallback);
	}

	/**
	 * 当为post请求是 调用改方法设置body体 参数,此方法在sendConnection 方法前调用
	 * 
	 * @param bodyKey
	 * @param bodyValue
	 */
	public void setBodyParams(String[] bodyKey, String[] bodyValue) {
		if (bodyKey.length != bodyValue.length) {
			throw new IllegalArgumentException(
					"check your BodyParams key or value length!");
		}
		bodyParams = new ArrayList<NameValuePair>();
		String bodyParam = "body参数：";
		for (int i = 0; i < bodyKey.length; i++) {
			NameValuePair param = new BasicNameValuePair(bodyKey[i],
					bodyValue[i]);
			bodyParam += bodyKey[i] + "=" + bodyValue[i] + "&";
			bodyParams.add(param);
		}
		if (bodyParams.lastIndexOf("&") > 0) {
			bodyParam = bodyParam.substring(0, bodyParam.length() - 1);
		}
		// LogUtils.e(bodyParam);
	}

	/**
	 * 连网调用的方法、这个方法默认会传driverCode、token、author_id三个参数 连网时可选择是否转圈
	 * showDialog=true--->会转。showDialog=false----->不会
	 * 
	 * @param url
	 *            地址
	 * @param argsKeys
	 *            传递的参数名
	 * @param argsValues
	 *            参数对应的值
	 * @param where
	 *            连网结果的状态值
	 * @param showDialog
	 *            是否显示dialog
	 */
	public void sendConnection(String url, String[] argsKeys,
			String[] argsValues, int where, boolean showDialog, int umengId) {
		if (NetUtil.checkNetWork(context)) {
			sendConnection(HttpMethod.POST, url, argsKeys, argsValues, where,
					showDialog, umengId);
		} else {
			ToastUtil.show(context, "网络连接失败");
		}
	}

	public void sendConnection(String url, String[] argsKeys,
			String[] argsValues, int where, boolean showDialog) {
		if (NetUtil.checkNetWork(context)) {
			sendConnection(HttpMethod.GET, url, argsKeys, argsValues, where,
					showDialog, 0);
		} else {
			ToastUtil.show(context, "网络连接失败");
		}
	}

	public void sendConnectionPost(String url, String[] argsKeys,
			String[] argsValues, int where, boolean showDialog) {
		if (NetUtil.checkNetWork(context)) {
			sendConnectionPost(HttpMethod.POST, url, argsKeys, argsValues,
					where, showDialog, 0);
		} else {
			ToastUtil.show(context, "网络连接失败");
		}
	}

	/**
	 * 除登录忘记密码界面外都要调用的方法（不传递任务参数） 连网时不转圈
	 * 
	 * @param url
	 * @param where
	 */
	public void sendConnection(String url, int where, int umengId) {
		sendConnectionNoLoginOrPsw(HttpMethod.POST, url, new String[] {},
				new String[] {}, where, false, umengId);
	}

	/**
	 * 除登录忘记密码界面外都要调用的方法、 连网时可选择是否转圈
	 * showDialog=true--->会转。showDialog=false----->不会
	 * 
	 * @param url
	 *            地址
	 * @param argsKeys
	 *            传递的参数名
	 * @param argsValues
	 *            参数对应的值
	 * @param where
	 *            连网结果的状态值
	 * @param showDialog
	 *            是否显示dialog
	 */
	public void sendConnectionNoLoginOrPsw(String url, String[] argsKeys,
			String[] argsValues, int where, boolean showDialog, int umengId) {
		sendConnectionNoLoginOrPsw(HttpMethod.POST, url, argsKeys, argsValues,
				where, showDialog, umengId);
	}

	// -------------------------需要重写或实现的方法---------------------
	/**
	 * 联网开始
	 * 
	 * @param where
	 */
	protected void onStart(int where) {
	}

	/**
	 * 停止
	 * 
	 * @param where
	 */
	protected void onStopped(int where) {
	}

	/**
	 * 加载中 回调改方法
	 * 
	 * @param total
	 * @param current
	 * @param isUploading
	 * @param where
	 */
	protected void onLoading(long total, long current, boolean isUploading,
			int where) {
	}

	/**
	 * 成功返回结果
	 * 
	 * @param result
	 * @param where
	 */

	protected abstract void onSuccess(String result, int where);

	/**
	 * 网络连接错误 或服务器返回错误结果时回调改方法
	 * 
	 * @param result
	 * @param where
	 */
	protected abstract void onFailure(String result, int where);

	// /**
	// * 可以在此方法中配置一些基本数据
	// */
	// protected abstract void setupData();

	// -------------------------------------------------

	private class HttpCallBack extends RequestCallBack<String> {
		private int where;
		private boolean showDialog;
		private int umengid;

		public HttpCallBack(int where, boolean showDialog, int umengId) {
			this.where = where;
			this.showDialog = showDialog;
			this.umengid = umengId;
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			LogGK.e("请求---------------->" + this.getRequestUrl());
			BaseFrament.this.onStart(where);
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			// TODO Auto-generated method stub
			super.onLoading(total, current, isUploading);
			BaseFrament.this.onLoading(total, current, isUploading, where);
		}

		public void onStopped() {
			LogGK.e("请求---------------->onStopped");
			BaseFrament.this.onStopped(where);
		}

		@Override
		public void onFailure(HttpException ex, String msg) {
			LogGK.e("请求---------------->url:>:" + this.getRequestUrl());
			LogGK.e("请求---------------->onFailure" + ex);
			LogGK.e("请求---------------->onFailure" + msg);
			dismissDialog();
			String result = context.getResources().getString(
					R.string.result_failure);
			BaseFrament.this.onFailure(result, where);
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			dismissDialog();
			String result = arg0.result;
			LogGK.e("返回:url--->" + this.getRequestUrl()
					+ "------------>" + result);
			BaseFrament.this.onSuccess(result, where);
		}

	}

	/**
	 * 隐藏加载对话框
	 */
	public void dismissDialog() {
		if (getActivity() != null && !getActivity().isFinishing()) {
			if (vector.size() > 1) {
				vector.remove(0);
			} else {
				progressDialog.dismiss();
				vector.clear();
			}
		}
	}

	/**
	 * 显示加载对话框
	 */
	public void showDialog() {
		// if(isDestroyed()){
		// return;
		// }
		if (!getActivity().isFinishing()) {
			progressDialog.show();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	protected abstract void setData();

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MobclickAgent.onPageEnd("MainScreen");
	}
	
	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(getActivity(), pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}

		startActivity(intent);
	}
}
