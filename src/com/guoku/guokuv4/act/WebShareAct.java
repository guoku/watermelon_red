/**

 */
package com.guoku.guokuv4.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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
public class WebShareAct extends NetWorkActivity{
	
	private static final int INFO_GOOD = 1001;//商品
	private static final int INFO_USER = 1002;//用户
	
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
				setGCenter(true, title);
			}

		};

		view.setWebChromeClient(wvcc);
		setGLeft(true, R.drawable.back_selector);
		setGRigth(true, R.drawable.more);
		if(StringUtils.isEmpty(sharebean.getTitle())){//banner过来的
			view.loadUrl(sharebean.getAricleUrl());
		}else{
			view.loadUrl(Constant.URL_ARTICLES + sharebean.getAricleUrl());
		}
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setUseWideViewPort(true);
		view.getSettings().setLoadWithOverviewMode(true);
		view.getSettings().setUserAgentString("guoku-client");
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.contains(IF_ENTITY)){//如果是商品链接
					url = StringUtils.isStringId(url, IF_ENTITY);
					sendConnection(Constant.PROINFO + url + "/",
							new String[] { "entity_id" },
							new String[] { url }, INFO_GOOD, true);
					
					return true;
				}
				if(url.contains(IF_USER)){//如果是用户
					url = StringUtils.isStringId(url, IF_USER);
					sendConnection(Constant.USERINFO + url + "/",
							new String[] { "user_id" },
							new String[] { url }, INFO_USER, true);
					return true;
				}
				
				
				return super.shouldOverrideUrlLoading(view, url);
			}
		});

	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void right(View v) {
		postShare();
	}

	private void postShare() {
		CustomShareBoard shareBoard = new CustomShareBoard(this);
		if(StringUtils.isEmpty(sharebean.getTitle())){//banner过来的
			shareBoard.setShareContext(this, "",
					sharebean.getAricleUrl(),
					sharebean.getImgUrl(), view.getTitle());
		}else{
			shareBoard.setShareContext(this, sharebean.getContext() + "…… ",
					Constant.URL_ARTICLES_SHARE + sharebean.getAricleUrl(),
					Constant.URL_IMG + sharebean.getImgUrl(), sharebean.getTitle() + "：");
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
			UserBean ussrBean = JSON.parseObject(result, UserBean.class);
			intent = new Intent(mContext, UserAct.class);
			intent.putExtra("data",ussrBean);
			startActivity(intent);
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

}
