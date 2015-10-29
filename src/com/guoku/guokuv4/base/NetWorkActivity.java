package com.guoku.guokuv4.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.net.HttputilHelp;
import com.guoku.guokuv4.net.NetUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.CustomProgressDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * 带有网络操作的activity
 * 
 * @author bu.xuesong
 */
public abstract class NetWorkActivity extends BaseActivity {
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private CustomProgressDialog progressDialog;
	private HttpUtils httpUtils;
	protected NetWorkActivity context;
	private Vector<Integer> vector;
	private List<NameValuePair> bodyParams;
	protected DisplayImageOptions options;
	protected DisplayImageOptions optionsRound;

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		imageLoader.clearMemoryCache();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		progressDialog = new CustomProgressDialog(NetWorkActivity.this);
		progressDialog.setMessage("载入中...");
		httpUtils = HttputilHelp.getHttpUtils();
		// httpUtils.configUserAgent(GuokuApplication.userAgent);
		vector = new Vector<Integer>();
		options = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.EXACTLY).considerExifParams(true)
				.bitmapConfig(Config.RGB_565).showImageOnLoading(R.drawable.item240)
				.showImageForEmptyUri(R.drawable.item240).cacheInMemory(true).cacheOnDisk(true)
				.showImageOnFail(R.drawable.item240).build();
		optionsRound = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY).displayer(new RoundedBitmapDisplayer(90)).cacheInMemory(true)
				.cacheOnDisk(true).showImageForEmptyUri(R.drawable.user100).showImageOnFail(R.drawable.user100)
				.showImageOnLoading(R.drawable.user100).build();

	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		setupData();
	}

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

	public void commitFragment(int layoutId, Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(layoutId, fragment);
		transaction.commit();
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
	private void sendConnection(HttpMethod method, String url, String[] argsKeys, String[] argsValues, int where,
			boolean showDialog) {
		if (argsKeys.length != argsValues.length) {
			throw new IllegalArgumentException("check your Params key or value length!");
		}
		if (showDialog) {
			vector.add(where);
		}
		HttpCallBack httpCallback = new HttpCallBack(where, showDialog);
		RequestParams params = new RequestParams();
		Map<String, String> paramsMap = new TreeMap<String, String>();
		for (int i = 0; i < argsKeys.length; i++) {
			params.addQueryStringParameter(argsKeys[i], argsValues[i]);
			Logger.e("params", "params----->" + argsKeys[i] + ":" + argsValues[i]);
			paramsMap.put(argsKeys[i], argsValues[i]);
		}
		if (GuokuApplication.getInstance().getBean() != null) {
			params.addQueryStringParameter("session", GuokuApplication.getInstance().getBean().getSession());
			paramsMap.put("session", GuokuApplication.getInstance().getBean().getSession());
		}

		params.addQueryStringParameter("sign", genSign(paramsMap));
		params.addQueryStringParameter("api_key", "0b19c2b93687347e95c6b6f5cc91bb87");

		Logger.e("params", "params----->" + ":" + params.toString());
		if (showDialog && !isFinishing()) {
			showDialog();
		}
		httpUtils.send(method, url, params, httpCallback);
	}

	private void sendConnectionPost(HttpMethod method, String url, String[] argsKeys, String[] argsValues, int where,
			boolean showDialog) {
		if (argsKeys.length != argsValues.length) {
			throw new IllegalArgumentException("check your Params key or value length!");
		}
		if (showDialog) {
			vector.add(where);
		}
		HttpCallBack httpCallback = new HttpCallBack(where, showDialog);
		RequestParams params = new RequestParams();
		Map<String, String> paramsMap = new TreeMap<String, String>();
		for (int i = 0; i < argsKeys.length; i++) {
			if (argsValues[i] != null) {
				params.addBodyParameter(argsKeys[i], argsValues[i]);
				Logger.e("params", "params----->" + argsKeys[i] + ":" + argsValues[i]);
				paramsMap.put(argsKeys[i], argsValues[i]);
			}
		}
		if (GuokuApplication.getInstance().getBean() != null) {
			params.addBodyParameter("session", GuokuApplication.getInstance().getBean().getSession());
			paramsMap.put("session", GuokuApplication.getInstance().getBean().getSession());
		} else if (Constant.NeedLogin(url)) {

		} else {
			Intent intent = new Intent(this, LoginAct.class);
			startActivity(intent);
			return;
		}
		params.addBodyParameter("sign", genSign(paramsMap));
		params.addBodyParameter("api_key", "0b19c2b93687347e95c6b6f5cc91bb87");

		Logger.e("params", "sign----->" + ":" + genSign(paramsMap));
		Logger.e("params", "api_key----->" + ":0b19c2b93687347e95c6b6f5cc91bb87");
		Logger.e("params", "params----->" + ":" + params.toString());
		// Logger.e("params", "session----->" + ":" +
		// GuokuApplication.getInstance()
		// .getBean().getSession());
		if (showDialog && !isFinishing()) {
			showDialog();
		}
		httpUtils.send(method, url, params, httpCallback);
	}

	protected void upPic(String url, Bitmap bitmap, int where) {
		HttpCallBack httpCallback = new HttpCallBack(where, false);
		RequestParams params = new RequestParams();
		Map<String, String> paramsMap = new TreeMap<String, String>();
		if (GuokuApplication.getInstance().getBean() != null) {
			params.addBodyParameter("session", GuokuApplication.getInstance().getBean().getSession());
			paramsMap.put("session", GuokuApplication.getInstance().getBean().getSession());
		}
		params.addBodyParameter("image", new File(Constant.IMAGES_PATH + "temp.png"));
		params.addBodyParameter("sign", genSign(paramsMap));
		params.addBodyParameter("api_key", "0b19c2b93687347e95c6b6f5cc91bb87");
		httpUtils.send(HttpMethod.POST, url, params, httpCallback);
	}

	/**
	 * 当为post请求是 调用改方法设置body体 参数,此方法在sendConnection 方法前调用
	 * 
	 * @param bodyKey
	 * @param bodyValue
	 */
	public void setBodyParams(String[] bodyKey, String[] bodyValue) {
		if (bodyKey.length != bodyValue.length) {
			throw new IllegalArgumentException("check your BodyParams key or value length!");
		}
		bodyParams = new ArrayList<NameValuePair>();
		String bodyParam = "body参数：";
		for (int i = 0; i < bodyKey.length; i++) {
			NameValuePair param = new BasicNameValuePair(bodyKey[i], bodyValue[i]);
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
	public void sendConnection(String url, String[] argsKeys, String[] argsValues, int where, boolean showDialog) {
		if (NetUtil.checkNetWork(context)) {
			sendConnection(HttpMethod.GET, url, argsKeys, argsValues, where, showDialog);
		} else
			ToastUtil.show(getApplicationContext(), "网络连接失败");
		onFailure("网络连接失败", where);
	}

	public void sendConnectionPOST(String url, String[] argsKeys, String[] argsValues, int where, boolean showDialog) {
		if (NetUtil.checkNetWork(context)) {
			sendConnectionPost(HttpMethod.POST, url, argsKeys, argsValues, where, showDialog);
		} else
			ToastUtil.show(getApplicationContext(), "网络连接失败");
		onFailure("网络连接失败", where);
	}

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
	protected void onLoading(long total, long current, boolean isUploading, int where) {
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

	/**
	 * 可以在此方法中配置一些基本数据
	 */
	protected abstract void setupData();

	// -------------------------------------------------

	private class HttpCallBack extends RequestCallBack<String> {
		private int where;

		public HttpCallBack(int where, boolean showDialog) {
			this.where = where;
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			Logger.e("请求", "---------------->" + this.getRequestUrl());
			NetWorkActivity.this.onStart(where);
		}

		@Override
		public void onLoading(long total, long current, boolean isUploading) {
			// TODO Auto-generated method stub
			super.onLoading(total, current, isUploading);
			NetWorkActivity.this.onLoading(total, current, isUploading, where);
		}

		@Override
		public void onFailure(HttpException ex, String msg) {
			Logger.e(TAG, "onFailure--->" + msg);
			dismissDialog();
			switch (ex.getExceptionCode()) {
			case 400:
				ToastUtil.show(mContext, R.string.error_bad_request);
				break;

			default:
				break;
			}

			NetWorkActivity.this.onFailure(msg, where);
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			dismissDialog();
			String result = arg0.result;
			NetWorkActivity.this.onSuccess(result, where);
		}

	}

	/**
	 * 隐藏加载对话框
	 */
	public void dismissDialog() {
		if (!isFinishing()) {
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
		if (!isFinishing()) {
			progressDialog.show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		imageLoader.clearMemoryCache();
		super.onDestroy();
	}

	private static String genSign(Map<String, String> paramsMap) {

		StringBuffer sb = new StringBuffer();
		paramsMap.put("api_key", "0b19c2b93687347e95c6b6f5cc91bb87");
		// sb.append("api_key=").append(APIConstant.API.api_key);
		Set<String> keySet = paramsMap.keySet();
		for (String key : keySet) {
			sb.append(key).append("=").append(paramsMap.get(key));
		}
		sb.append("47b41864d64bd46");
		String strParam = sb.toString();
		String sign = StringUtils.md5(strParam);

		return sign;
	}

}
