package com.guoku.guokuv4.act;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ekwing.students.base.BaseActivity;
import com.guoku.R;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WebAct extends BaseActivity {
	@ViewInject(R.id.webview)
	private WebView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actiivty_website);
		// if (getIntent().getStringExtra("name") != null
		// && !"".equals(getIntent().getStringExtra("name"))) {
		// setGCenter(true, getIntent().getStringExtra("name"));
		// } else {
		// setGCenter(true, "用户协议");
		// }

		WebChromeClient wvcc = new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				// if (title.length() > 7) {
				// title = title.substring(0, 7);
				// }
				setGCenter(true, title);

			}

		};
		view.setWebChromeClient(wvcc);
		setGLeft(true, R.drawable.back_selector);
		view.loadUrl(getIntent().getStringExtra("data"));
		view.getSettings().setJavaScriptEnabled(true);
		// Log.e(TAG, "url-->" + getIntent().getStringExtra("data"));
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

	}
}
