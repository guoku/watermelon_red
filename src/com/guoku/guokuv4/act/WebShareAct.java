/**

 */
package com.guoku.guokuv4.act;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.ItemService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.eventbus.ZanEB;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.share.CustomShareBoard;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow.OnDismissListener;
import de.greenrobot.event.EventBus;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-18 下午2:37:34 可以分享的webview
 */
public class WebShareAct extends NetWorkActivity {

	private static final int INFO_GOOD = 1001;// 商品
	private static final int INFO_USER = 1002;// 用户
	private static final int DIG = 1003;// 赞
	private static final int UN_DIG = 1004;// 取消赞

	String IF_ARTICLES = "/articles/";
	String IF_ENTITY = "guoku://entity/";
	String IF_USER = "guoku://user/";
	String IF_TMALL = "detail.tmall.com";
	String IF_TAOBAO = "taobao.com";

	@ViewInject(R.id.webview)
	private WebView view;
	Sharebean sharebean = new Sharebean();
	String urls;

	CheckBox checkZan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this);
		}

		setContentView(R.layout.actiivty_website);

		if (getIntent().getExtras() != null) {
			sharebean = (Sharebean) getIntent().getExtras().getSerializable(WebShareAct.class.getName());
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
		// 如果是图文
		if (sharebean.getAricleUrl().contains(IF_ARTICLES)) {
			initTitleZan();
		}
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setUseWideViewPort(true);
		view.getSettings().setLoadWithOverviewMode(true);
		view.getSettings().setUserAgentString("guoku-client");

		WebSettings webSettings = view.getSettings();
		webSettings.setSupportZoom(true);
		view.requestFocus();

		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.contains(IF_ENTITY)) {// 如果是商品链接
					url = StringUtils.isStringId(url, IF_ENTITY);
					sendConnection(Constant.PROINFO + url + "/", new String[] { "entity_id" }, new String[] { url },
							INFO_GOOD, true);
					umStatistics(Constant.UM_ARTICLE_TO_GOOD, url, IF_ENTITY);
					return true;
				}
				if (url.contains(IF_USER)) {// 如果是用户
					url = StringUtils.isStringId(url, IF_USER);
					sendConnection(Constant.USERINFO + url + "/", new String[] {}, new String[] {}, INFO_USER, true);
					umStatistics(Constant.UM_ARTICLE_TO_USER, url, IF_USER);
					return true;
				}
				if (url.contains(IF_TMALL) || url.contains(IF_TAOBAO)) {// 如果是淘宝商品
					urls = url;
					if (!StringUtils.isEmpty(urls)) {
						showPage(null);
						umStatistics(Constant.UM_ARTICLE_TO_TAOBAO, urls, IF_TAOBAO);
					}
					return true;
				}

				setGCenter(true, view.getTitle());

				// 如果是外链
//				if (sharebean.getAricleUrl().contains(IF_ARTICLES)) {
					checkZan.setVisibility(View.GONE);
//				}
				
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				webViewTitle.add(view.getTitle());
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

		});

		// 点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
		view.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && view.canGoBack()) {
						setGCenter(true, goBack(view));
						
//						
//						if(view.getUrl().contains(IF_ARTICLES)){
//							checkZan.setVisibility(View.VISIBLE);
//						}else{
//							checkZan.setVisibility(View.GONE);
//						}
//						
						return true; // 已处理
					}
				}
				
				checkZan.setVisibility(View.VISIBLE);
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

	private void initTitleZan() {

		checkZan = setTitleZan();

		if (sharebean.isIs_dig()) {
			checkZan.setChecked(true);
		} else {
			checkZan.setChecked(false);
		}

		checkZan.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (GuokuApplication.getInstance().getBean() == null) {
					buttonView.setChecked(false);
					openActivity(LoginAct.class);
				} else {
					if (isChecked) {
						if (!StringUtils.isEmpty(sharebean.getAricleId())) {
							
							umStatistics(Constant.UM_ARTICLE_ZAN, sharebean.getAricleId(), sharebean.getTitle());
							
							sendConnectionPOST(Constant.ARTICLES_DIG, new String[] { "aid" },
									new String[] { sharebean.getAricleId() }, DIG, false);
							checkZan.setChecked(true);
						}
					} else {
						if (!StringUtils.isEmpty(sharebean.getAricleId())) {
							
							umStatistics(Constant.UM_ARTICLE_ZAN_UN, sharebean.getAricleId(), sharebean.getTitle());
							
							sendConnectionPOST(Constant.ARTICLES_UNDIG, new String[] { "aid" },
									new String[] { sharebean.getAricleId() }, UN_DIG, false);
							checkZan.setChecked(false);
						}
					}
				}
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
			shareBoard.setShareContext(this, "", sharebean.getAricleUrl(), sharebean.getImgUrl(), view.getTitle());
		} else {
			shareBoard.setShareContext(this, sharebean.getContext() + "…… ",
					Constant.URL_ARTICLES_SHARE + sharebean.getAricleUrl(), Constant.URL_IMG + sharebean.getImgUrl(),
					sharebean.getTitle() + "：");
		}

		shareBoard.setAnimationStyle(R.style.popwin_anim_style);
		shareBoard.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.alpha = 0.6f;
		getWindow().setAttributes(params);

		shareBoard.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams params = getWindow().getAttributes();
				params.alpha = 1f;
				getWindow().setAttributes(params);
				if (isRefrech) {
					view.reload();
				}
			}
		});

	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		Intent intent;
		ZanEB zanEB = new ZanEB();
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
				UserBean userBean = (UserBean) JSON.parseObject(root.getString("user"), UserBean.class);
				intent = new Intent(mContext, UserBaseFrament.class);
				intent.putExtra("data", userBean);
				startActivity(intent);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case DIG:
			checkZan.setChecked(true);
			zanEB.setZan(true);
			EventBus.getDefault().post(zanEB);
			break;
		case UN_DIG:
			checkZan.setChecked(false);
			zanEB.setZan(false);
			EventBus.getDefault().post(zanEB);
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
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().unregister(this);
		}
	}

	public void showPage(View view) {
		ItemService itemService = AlibabaSDK.getService(ItemService.class);
		itemService.showPage(this, new TradeProcessCallback() {

			@Override
			public void onPaySuccess(TradeResult tradeResult) {

			}

			@Override
			public void onFailure(int code, String msg) {
				// if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
				// Toast.makeText(MainActivity.this, "确认交易订单失败",
				// Toast.LENGTH_SHORT).show();
				// } else {
				// Toast.makeText(MainActivity.this, "交易异常",
				// Toast.LENGTH_SHORT).show();
				// }
			}
		}, null, urls);
	}

	public void onEventMainThread(ZanEB zEb) {

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		addActGuide(R.id.layout_webview, 48, WebShareAct.class.getName());
	}

}
