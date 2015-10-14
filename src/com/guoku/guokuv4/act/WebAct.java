package com.guoku.guokuv4.act;

import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow.OnDismissListener;

import com.ekwing.students.base.BaseActivity;
import com.ekwing.students.customview.CustomShareBoard;
import com.guoku.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WebAct extends BaseActivity {
	@ViewInject(R.id.webview)
	private WebView view;
	private String name;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actiivty_website);

		WebChromeClient wvcc = new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				name = title;
				webViewTitle.add(title);
				setGCenter(true, title);

			}

		};
		if ("banner".equals(getIntent().getStringExtra("type"))) {
			setGRigth(true, R.drawable.more);
		}

		view.setWebChromeClient(wvcc);
		setGLeft(true, R.drawable.back_selector);
		url = getIntent().getStringExtra("data");
		view.loadUrl(url);
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setUseWideViewPort(true);
		view.getSettings().setLoadWithOverviewMode(true);

		if ("UA".equals(getIntent().getStringExtra("UA"))) {

		} else
			view.getSettings().setUserAgentString("guoku-client");
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				setGCenter(true, view.getTitle());
				return true;
			}
			
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				webViewTitle.add(view.getTitle());
			}
		});

		// 点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && view.canGoBack()) {
						setGCenter(true, goBack(view));
						return true; // 已处理
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
		shareBoard.setShareContext(name, url);
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webViewTitle.clear();
	}
}
