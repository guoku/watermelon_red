package com.guoku.guokuv4.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ekwing.students.base.BaseActivity;
import com.guoku.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class UIBaseFragment extends Fragment implements
		OnClickListener {
	public static OnRetryListener retryListener;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public interface OnRetryListener {
		public void onRetryClick();
	}

	/** 请求成功 */
	public static final int MSG_SUCCESS = 2000;
	/** 请求失败 */
	public static final int MSG_FAIL = 4000;
	/** 网络连接失败 */
	protected static final int MSG_NETWORK_ERROR = 5000;
	/** 请求成功2 */
	protected static final int MSG_DATA_SUCCESS = MSG_SUCCESS + 1;

	protected static String TAG = null;
	protected Context mContext;
	protected Resources mResources;
	protected View contentView;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		TAG = getClass().getSimpleName();
		mContext = getActivity();
		mResources = this.getResources();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(getContentId(), container, false);
		findView();
		retryListener = new OnRetryListener() {

			@Override
			public void onRetryClick() {
				UIBaseFragment.this.onRetryClick();
			}
		};
		initListener();
		operateData();
		return contentView;
	}

	/** 初始化contentview */
	protected abstract int getContentId();

	protected void onRetryClick() {
	}

	/** 初始化view */
	protected abstract void findView();

	/** 初始化事件 */
	protected abstract void initListener();

	/** 处理数据[如:请求网络] */
	protected abstract void operateData();

	/** view处理数据成功[注意:该方法为handler 回调] */
	protected abstract void handlerMessageCallBack(Message msg);

	// /** view处理数据失败[注意:该方法为handler 回调] */
	// protected abstract void handlerMessageError(Message msg);

	@SuppressWarnings("unchecked")
	protected <T extends View> T getViewById(int id) {
		return (T) contentView.findViewById(id);
	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T getViewById(View view, int id) {
		return (T) view.findViewById(id);
	}

	public void postDelayedInMain(Runnable runnable, long delay) {
		mHandler.postDelayed(runnable, delay);
	}

	protected void showFragmentView() {
		if (contentView.getVisibility() != View.VISIBLE)
			contentView.setVisibility(View.VISIBLE);
	}

	protected void hideFragmentView() {
		if (contentView.getVisibility() != View.GONE)
			contentView.setVisibility(View.GONE);
	}

	// protected void showLoadView() {
	// getBaseActivity().showByView(getBaseActivity().mViewLoading);
	// }
	//
	// protected void hideLoadView() {
	// getBaseActivity().hideByView(getBaseActivity().mViewLoading);
	// }
	//
	// protected void showContentView() {
	// getBaseActivity().showByView(getBaseActivity().contentView);
	// }
	//
	// protected void hideContentView() {
	// getBaseActivity().hideByView(getBaseActivity().contentView);
	// }

	// protected void showDefView() {
	// getBaseActivity().showByView(getBaseActivity().mViewDef);
	// }
	//
	// protected void hideDefView() {
	// getBaseActivity().hideByView(getBaseActivity().mViewDef);
	// }

	public BaseActivity getBaseActivity() {
		return (BaseActivity) getActivity();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@SuppressLint("HandlerLeak")
	protected Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			// hideLoadView();
			switch (msg.what) {
			case MSG_SUCCESS:
				handlerMessageCallBack(msg);
				break;
			case MSG_FAIL:
				handlerMessageCallBack(msg);
				break;
			case MSG_NETWORK_ERROR:
				Toast.makeText(getActivity(), R.string.error_network,
						Toast.LENGTH_LONG).show();
				break;
			case MSG_DATA_SUCCESS:
				handlerMessageCallBack(msg);
				break;
			}
		};
	};

}
