package com.guoku.guokuv4.base;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.trade.ItemService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.avos.avoscloud.AVAnalytics;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.WebAct;
import com.guoku.guokuv4.bean.LaunchBean;
import com.guoku.guokuv4.config.AlibabaConfig;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.service.DownLoadService;
import com.guoku.guokuv4.utils.MyPreferences;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.taobao.tae.sdk.webview.TaeWebViewUiSettings;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMSsoHandler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 基类activity 每一个acitivty都要继承该类
 * 
 * @date 2013-12-12
 */

public abstract class BaseActivity extends FragmentActivity {
	protected final String TAG = getClass().getSimpleName();
	protected Context mContext;
	protected Handler mHandler;
	protected boolean isLive;

	public static final String LIKE = "like";
	public static final String TIME = "time";

	FrameLayout frameLayout;// 引导图
	View viewsOne, viewsTwo;

	TextView tvTwo;
	ImageView imgTrage;

	public UMSocialService mController;// 初始化友盟分享

	public static boolean isRefrech;// 在弹出的popwindos是否刷新

	public static ArrayList<String> webViewTitle = new ArrayList<String>();

	public Animation animationBackShow;
	public Animation animationBackHide;
	public Animation animationllShow;
	public Animation animationllHide;
	public final int animTime = 300;
	public boolean animIsRunning = false;

	public View listView;
	public View backView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		GuokuApplication.getInstance().addActivity(this);
		mContext = this;
		isLive = true;
		getWindow().setWindowAnimations(R.style.ActivityAnimation);

		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		ViewUtils.inject(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 友盟数据统计
		MobclickAgent.onResume(this);
		AVAnalytics.onResume(this);

		// jpush推送统计
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		AVAnalytics.onPause(this);

	}

	@Override
	protected void onDestroy() {
		GuokuApplication.getInstance().removeActivity(this);
		isLive = false;
		super.onDestroy();
	}

	protected void setGLeft(boolean show, int resid) {
		ImageView view = (ImageView) findViewById(R.id.title_bar_left_iv);
		ImageView line = (ImageView) findViewById(R.id.title_bar_left_iv1);
		if (show) {
			view.setVisibility(View.VISIBLE);
			line.setVisibility(View.VISIBLE);
			view.setImageResource(resid);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					leftOnClick();
					finish();
				}
			});
		} else {
			view.setVisibility(View.GONE);
			line.setVisibility(View.GONE);
		}
	}

	protected ImageView getGLeft() {
		ImageView view = (ImageView) findViewById(R.id.title_bar_left_iv);
		return view;
	}

	protected void setGRigth(boolean show, int resid) {
		ImageView view = (ImageView) findViewById(R.id.title_bar_rigth_iv);
		if (show) {
			view.setVisibility(View.VISIBLE);
			view.setImageResource(resid);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	protected void setGRigthWang(boolean show) {
		ImageView wang = (ImageView) findViewById(R.id.alibaba_wang);
		if (show) {
			wang.setVisibility(View.VISIBLE);
		} else {
			wang.setVisibility(View.GONE);
		}
	}

	protected ImageView getGRigthWang() {
		ImageView wang = (ImageView) findViewById(R.id.alibaba_wang);
		return wang;
	}

	protected void setGRigthText(boolean show, int resid) {
		TextView rightTv = (TextView) findViewById(R.id.title_bar_rigth_tv);
		if (show) {
			rightTv.setVisibility(View.VISIBLE);
			rightTv.setText(getResources().getString(resid));
		} else {
			rightTv.setVisibility(View.GONE);
		}
		rightTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				rightTextOnClick();
			}
		});
	}

	protected void setGCenter(boolean show, int resid) {
		TextView view = (TextView) findViewById(R.id.title_bar_centrt_tv);
		if (show) {
			view.setVisibility(View.VISIBLE);
			view.setText(getResources().getString(resid));
		} else {
			view.setVisibility(View.GONE);
		}
	}

	protected void setGCenter(boolean show, String resid) {
		TextView view = (TextView) findViewById(R.id.title_bar_centrt_tv);
		if (show) {
			view.setVisibility(View.VISIBLE);
			view.setText(resid);
		} else {
			view.setVisibility(View.GONE);
		}
	}

	protected void rightTextOnClick() {

	}

	protected void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	protected void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}

		startActivity(intent);
	}

	protected void openActivityForResult(Class<?> pClass, int requestCode) {
		openActivityForResult(pClass, null, requestCode);
	}

	protected void openActivityForResult(Class<?> pClass, Bundle pBundle, int requestCode) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}

	protected void openShopInfo(String result) {
		try {
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(mContext, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void openLogin() {
		startActivity(new Intent(this, LoginAct.class));
	}

	public void leftOnClick() {

	}

	public LinearLayout getTitleLayout() {

		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_title_more);
		return linearLayout;
	}

	/***
	 * 搜索及品类列表右上角的
	 * 
	 * @return
	 */
	public LinearLayout getTitleLayoutSeach() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_title_seach);
		return linearLayout;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// addGuideImage();// 添加引导页
	}

	/**
	 * 添加引导图片
	 */
	public void addGuideImage() {
		View view = findViewById(R.id.content);// 查找通过setContentView上的根布局
		if (view == null)
			return;
		if (!MyPreferences.activityIsGuided(this, BaseActivity.this.getClass().getName())) {
			// 引导过了

			ViewParent viewParent = view.getParent();
			if (viewParent instanceof FrameLayout) {
				frameLayout = (FrameLayout) viewParent;

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER;
				LayoutInflater lInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				viewsOne = lInflater.inflate(R.layout.view_walkthrough_one, null);
				// ImageView imageView = (ImageView)
				// viewsOne.findViewById(R.id.img1);
				// imageView.setLayoutParams(new
				// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				// RelativeLayout.LayoutParams.WRAP_CONTENT));

				viewsTwo = lInflater.inflate(R.layout.view_walkthrough_two, null);

				final TextView textView = (TextView) viewsOne.findViewById(R.id.textView1);

				tvTwo = (TextView) viewsTwo.findViewById(R.id.textView3);// 下面一层textview
				imgTrage = (ImageView) viewsTwo.findViewById(R.id.img_walkthrough_trgl);

				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// frameLayout.removeViewAt(2);
						frameLayout.removeViewAt(2);
						showFrameLayoutTwo();
						setCurrentItems();
						MyPreferences.setIsGuided(getApplicationContext(), BaseActivity.this.getClass().getName());// 设为已引导
					}
				});
				frameLayout.addView(viewsTwo);// 添加引导图片
				frameLayout.addView(viewsOne);// 添加引导图片
			}
		}
	}

	public void setCurrentItems() {
	}

	// 显示第二层引导页
	public void showFrameLayoutTwo() {
		if (tvTwo != null) {
			tvTwo.setVisibility(View.VISIBLE);
		}
		if (imgTrage != null) {
			imgTrage.setVisibility(View.VISIBLE);
		}
	}

	// 删除引导页
	public void removeFrameLayout() {
		if (frameLayout != null) {
			if (viewsOne != null) {
				frameLayout.removeView(viewsOne);
			}
			if (viewsTwo != null) {
				frameLayout.removeView(viewsTwo);
			}
		}
	}

	public interface OnViewPageCurrentItem {
		void onCurrentItem();
	}

	public String goBack(WebView view) {
		if (view.canGoBack()) {
			view.goBack(); // 后退
			if (webViewTitle.size() > 0) {
				webViewTitle.remove(webViewTitle.size() - 1);
				return webViewTitle.get(webViewTitle.size() - 1);
			}
		} else {
			finish();
		}
		return "";
	}

	/**
	 * 分享的sso回调
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(arg0);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(arg0, arg1, arg2);
		}
	}

	public void showSearchWhat() {
		showBackBlack();
		if (animationllShow == null) {
			animationllShow = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		}
		animationllShow.setDuration(animTime);
		listView.startAnimation(animationllShow);
	}

	public void hideSearchWhat() {
		hideBackBlack();
		if (animationllHide == null) {
			animationllHide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
		}
		animationllHide.setDuration(animTime);
		listView.startAnimation(animationllHide);
	}

	public void showBackBlack() {
		if (animationBackShow == null) {
			backView.setVisibility(View.VISIBLE);
			animationBackShow = new AlphaAnimation(0.0f, 1.0f);
			animationBackShow.setAnimationListener(animationShowListener);
		}
		animationBackShow.setDuration(animTime);
		backView.startAnimation(animationBackShow);
	}

	public void hideBackBlack() {
		if (animationBackHide == null) {
			animationBackHide = new AlphaAnimation(1.0f, 0.0f);
			animationBackHide.setAnimationListener(animationHideListener);
		}
		animationBackHide.setDuration(animTime);
		backView.startAnimation(animationBackHide);
	}

	AnimationListener animationShowListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			animIsRunning = true;
			backView.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			animIsRunning = false;
		}
	};

	AnimationListener animationHideListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			animIsRunning = true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			backView.setVisibility(View.INVISIBLE);
			listView.setVisibility(View.INVISIBLE);
			animIsRunning = false;
		}
	};

	/**
	 * 双击tab接口
	 * 
	 * @zhangyao
	 * @Description: TODO
	 * @date 2015年11月13日 下午3:18:51
	 */
	public interface OnDoubleClickListener {
		public void OnSingleClick(View v);

		public void OnDoubleClick(View v);
	}

	/**
	 * 设置数据为空或不为空ui的显示（例如用户的喜爱）
	 */
	public void isDataEmpty(boolean isEmpty, View data, View unData) {
		if (isEmpty) {
			unData.setVisibility(View.VISIBLE);
			data.setVisibility(View.GONE);
		} else {
			data.setVisibility(View.VISIBLE);
			unData.setVisibility(View.GONE);
		}
	}

	/**
	 * 跳到淘宝商品详情页
	 */
	
	public void gotoTaoBao(PInfoBean productBean, long origin_id) {
		AVAnalytics.onEvent(this, "buy");
		MobclickAgent.onEvent(this, "buy");

		try {
			
			if(productBean != null){
				JSONArray array;
				array = new JSONArray(productBean.getEntity().getItem_list());
				origin_id = array.getJSONObject(0).getLong("origin_id");
				
				if (!(array.getJSONObject(0).getString("origin_source").contains("taobao")
						|| array.getJSONObject(0).getString("origin_source").contains("tmall"))) {
					Intent intent = new Intent(mContext, WebAct.class);
					intent.putExtra("data", array.getJSONObject(0).getString("buy_link"));
					intent.putExtra("name", "  ");
					intent.putExtra("UA", "UA");
					startActivity(intent);
					return;
				}
			}
			
			TaeWebViewUiSettings taeWebViewUiSettings = new TaeWebViewUiSettings();
			TaokeParams taokeParams = new TaokeParams();
			taokeParams.pid = AlibabaConfig.PID;
			taokeParams.unionId = "null";

			if (AlibabaSDK.isInitSucceed()) {
				ItemService itemService = AlibabaSDK.getService(ItemService.class);
				itemService.showTaokeItemDetailByItemId(this, new TradeProcessCallback() {

					@Override
					public void onPaySuccess(TradeResult tradeResult) {
//						ToastUtil.show(mContext, "支付成功");
					}

					@Override
					public void onFailure(int code, String msg) {
						if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
							// ToastUtil.show(mContext, "确认交易订单失败");
						} else {
							// ToastUtil.show(mContext, "交易取消");
						}
					}

				}, taeWebViewUiSettings, origin_id, 1, null, taokeParams);
			} else {
				ToastUtil.show(mContext, "淘宝小二开小差喽，请稍后再试");
				return;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
