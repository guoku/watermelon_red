/**

 */
package com.guoku.guokuv4.act;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow.OnDismissListener;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.customview.CustomShareBoard;
import com.guoku.R;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-18 下午2:37:34 可以分享的webview
 */
public class WebShareAct extends NetWorkActivity {

	private static final int INFO_GOOD = 1001;// 商品
	private static final int INFO_USER = 1002;// 用户

	String IF_ENTITY = "guoku://entity/";
	String IF_USER = "guoku://user/";

	@ViewInject(R.id.webview)
	private WebView view;
	Sharebean sharebean = new Sharebean();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actiivty_website);

		if (getIntent().getExtras() != null) {
			sharebean = (Sharebean) getIntent().getExtras().getSerializable(
					WebShareAct.class.getName());
		}

		WebChromeClient wvcc = new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				webViewTitle.add(title);
				setGCenter(true, title);
			}

		};

		view.setWebChromeClient(wvcc);
		setGLeft(true, R.drawable.back_selector);
		setGRigth(true, R.drawable.more);
		if (StringUtils.isEmpty(sharebean.getTitle())) {// banner过来的
			view.loadUrl(sharebean.getAricleUrl());
		} else {
			view.loadUrl(Constant.URL_ARTICLES + sharebean.getAricleUrl());
		}
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setUseWideViewPort(true);
		view.getSettings().setLoadWithOverviewMode(true);
		view.getSettings().setUserAgentString("guoku-client");
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.contains(IF_ENTITY)) {// 如果是商品链接
					url = StringUtils.isStringId(url, IF_ENTITY);
					sendConnection(Constant.PROINFO + url + "/",
							new String[] { "entity_id" }, new String[] { url },
							INFO_GOOD, true);

					return true;
				}
				if (url.contains(IF_USER)) {// 如果是用户
					url = StringUtils.isStringId(url, IF_USER);
					sendConnection(Constant.USERINFO + url + "/",
							new String[] {}, new String[] {}, INFO_USER, true);

					return true;
				}
				setGCenter(true, view.getTitle());
				
				return super.shouldOverrideUrlLoading(view, url);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				webViewTitle.add(view.getTitle());
			}
		});
		
		//点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)    
		view.setOnKeyListener(new View.OnKeyListener() {    
	        @Override    
	        public boolean onKey(View v, int keyCode, KeyEvent event) {    
	            if (event.getAction() == KeyEvent.ACTION_DOWN) {    
	                if (keyCode == KeyEvent.KEYCODE_BACK && view.canGoBack()) {
	                	setGCenter(true, goBack(view));
	                    return true;    //已处理    
	                }    
	            }    
	            return false;    
	        }    
	    });
		
		getGLeft().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setGCenter(true, goBack(view));
			}
		});

	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void right(View v) {
		postShare();
	}

	private void postShare() {
		CustomShareBoard shareBoard = new CustomShareBoard(this);
		if (StringUtils.isEmpty(sharebean.getTitle())) {// banner过来的
			shareBoard.setShareContext(this, "", sharebean.getAricleUrl(),
					sharebean.getImgUrl(), view.getTitle());
		} else {
			shareBoard.setShareContext(this, sharebean.getContext() + "…… ",
					Constant.URL_ARTICLES_SHARE + sharebean.getAricleUrl(),
					Constant.URL_IMG + sharebean.getImgUrl(),
					sharebean.getTitle() + "：");
		}

		shareBoard.showAtLocation(this.getWindow().getDecorView(),
				Gravity.BOTTOM, 0, 0);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.alpha = 0.6f;
		getWindow().setAttributes(params);

		shareBoard.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams params = getWindow().getAttributes();
				params.alpha = 1f;
				getWindow().setAttributes(params);

			}
		});

	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (where) {
		case INFO_GOOD:
			PInfoBean bean = ParseUtil.getPI(result);
			intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case INFO_USER:

			JSONObject root;
			try {
				root = new JSONObject(result);
				UserBean userBean = (UserBean) JSON.parseObject(
						root.getString("user"), UserBean.class);
				intent = new Intent(mContext, UserAct.class);
				intent.putExtra("data", userBean);
				startActivity(intent);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webViewTitle.clear();
	}

}
