package com.guoku.guokuv4.act;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
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

}
