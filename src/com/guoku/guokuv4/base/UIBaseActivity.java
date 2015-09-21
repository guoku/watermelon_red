package com.guoku.guokuv4.base;

//package com.guoku.guokuv4.base;
//
//import com.ekwing.students.R;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.Window;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.FrameLayout;
//
///**
// * @Description: 所有activity的基类 在此扩展[如果需要封装更深层,建议extends context]
// * @author:zhangqian
// * @see:
// * @since:
// * @copyright © ysyx.com
// * @Date:2014-12-12
// */
//public abstract class UIBaseActivity extends FragmentActivity implements
//		View.OnClickListener {
//
//	/** 网络连接失败 */
//	protected static final int MSG_NETWORK_ERROR = 5000;
//
//	protected static String TAG = null;
//	protected Context mContext;
//	protected Resources mResources;
//	protected View mViewLoading;
//	protected View contentView;
//
//	@Override
//	protected void onCreate(Bundle bundle) {
//		super.onCreate(bundle);
//		setNotitle();
//		TAG = getClass().getSimpleName();
//		mContext = this;
//		mResources = this.getResources();
//		contentView = View.inflate(mContext, getContentId(), null);
//		setContentView(contentView);
//		mViewLoading = View.inflate(mContext, R.layout.view_progressbar_layout,
//				null);
////		mViewDef = View.inflate(mContext, R.layout.nodata, null);
//		findView();
//		init(getIntent());
//		initListener();
//		initLoadView(mViewLoading);
////		initLoadView(mViewDef);
////		hideByView(mViewDef);
//		operateData();
////		mViewDef.findViewById(R.id.iv_refresh).setOnClickListener(this);
////		mViewDef.findViewById(R.id.iv_refresh).setOnClickListener(
////				new OnClickListener() {
////
////					@Override
////					public void onClick(View v) {
////						UIBaseFragment.retryListener.onRetryClick();
////					}
////				});
//
//	}
//
//	protected void setNotitle() {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//	}
//
//	public void commitFragment(int layoutId, Fragment fragment) {
//		FragmentTransaction transaction = getSupportFragmentManager()
//				.beginTransaction();
//		transaction.replace(layoutId, fragment);
//		transaction.commit();
//	}
//
//	public void hideInputMode() {
//		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		View view = this.getCurrentFocus();
//		if (view != null) {
//			imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);
//		}
//	}
//
//	private void initLoadView(View view) {
//		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//				FrameLayout.LayoutParams.MATCH_PARENT,
//				FrameLayout.LayoutParams.MATCH_PARENT);
//		params.topMargin = 0;
//		params.gravity = Gravity.CENTER;
//		addContentView(view, params);
//	}
//
//	public void hideByView(final View view) {
//		runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				if (view != null && view.getVisibility() != View.GONE)
//					view.setVisibility(View.GONE);
//			}
//		});
//	}
//
//	public void showByView(final View view) {
//		runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				if (view != null && view.getVisibility() != View.VISIBLE)
//					view.setVisibility(View.VISIBLE);
//			}
//		});
//
//	}
//
//	/** 初始化contentview */
//	protected abstract int getContentId();
//
//	/** 初始化数据 如intent之类 */
//	protected abstract void init(Intent intent);
//
//	/** 初始化view */
//	protected abstract void findView();
//
//	/** 初始化事件 */
//	protected abstract void initListener();
//
//	/** 处理数据[如:请求网络] */
//	protected abstract void operateData();
//
//	/** view处理数据成功[注意:该方法为handler 回调] */
//	protected abstract void handlerMessageCallBack(Message msg);
//
//	// /** view处理数据失败[注意:该方法为handler 回调] */
//	// protected abstract void handlerMessageError(Message msg);
//
//	@SuppressWarnings("unchecked")
//	protected <T extends View> T getViewById(int id) {
//		return (T) findViewById(id);
//	}
//
//	@SuppressWarnings("unchecked")
//	protected <T extends View> T getViewById(View view, int id) {
//		return (T) view.findViewById(id);
//	}
//
//	public void postDelayedInMain(Runnable runnable, long delay) {
//		mHandler.postDelayed(runnable, delay);
//	}
//
//	/**
//	 * 通过类名启动Activity
//	 * 
//	 * @param pClass
//	 */
//	public void openActivity(Class<?> pClass) {
//		openActivity(pClass, null);
//	}
//
//	/**
//	 * 通过类名启动Activity，并且含有Bundle数据
//	 * 
//	 * @param pClass
//	 * @param pBundle
//	 */
//	protected void openActivity(Class<?> pClass, Bundle pBundle) {
//		Intent intent = new Intent(this, pClass);
//		if (pBundle != null) {
//			intent.putExtras(pBundle);
//		}
//		startActivity(intent);
//	}
//
//	/**
//	 * 通过Action启动Activity
//	 * 
//	 * @param pAction
//	 */
//	protected void openActivity(String pAction) {
//		openActivity(pAction, null);
//	}
//
//	/**
//	 * 通过Action启动Activity，并且含有Bundle数据
//	 * 
//	 * @param pAction
//	 * @param pBundle
//	 */
//	protected void openActivity(String pAction, Bundle pBundle) {
//		Intent intent = new Intent(pAction);
//		if (pBundle != null) {
//			intent.putExtras(pBundle);
//		}
//		startActivity(intent);
//	}
//
//	@Override
//	protected void onStart() {
//		super.onStart();
//	}
//
//	@Override
//	protected void onRestart() {
//
//		super.onRestart();
//	}
//
//	@Override
//	protected void onResume() {
//
//		super.onResume();
//	}
//
//	@Override
//	protected void onPause() {
//
//		super.onPause();
//	}
//
//	@Override
//	protected void onStop() {
//
//		super.onStop();
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
////		ActivityManager.pop(this);
//	}
//
//	@SuppressLint("HandlerLeak")
//	protected Handler mHandler = new Handler() {
////
////		public void handleMessage(Message msg) {
////			hideByView(mViewLoading);
////			switch (msg.what) {
////			case AppController.MSG_SUCCESS_DEF:
////				handlerMessageCallBack(msg);
////				break;
////			case AppController.MSG_FAIL_DEF:
////				handlerMessageCallBack(msg);
////				break;
////			case MSG_NETWORK_ERROR:
////				Utils.toastShow(R.string.no_net);
////				break;
////			default:
////				handlerMessageCallBack(msg);
////				break;
////			}
////		};
//	};
//
// }
