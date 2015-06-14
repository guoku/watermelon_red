package com.ekwing.students.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.ekwing.students.EkwingApplication;
import com.guoku.R;
import com.lidroid.xutils.ViewUtils;
import com.umeng.analytics.MobclickAgent;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		EkwingApplication.getInstance().addActivity(this);
		mContext = this;
		isLive = true;
		getWindow().setWindowAnimations(R.style.ActivityAnimation);
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
		EkwingApplication.getInstance().removeActivity(this);
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
					finish();
				}
			});
		} else {
			view.setVisibility(View.GONE);
			line.setVisibility(View.GONE);
		}
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

	protected void setGCenter(boolean show, int resid) {
		TextView view = (TextView) findViewById(R.id.title_bar_centrt_tv);
		if (show) {
			view.setVisibility(View.VISIBLE);
			view.setText(resid);
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

	// protected void setLeftIC(boolean show, int resid) {
	// ImageView view = (ImageView) findViewById(R.id.title_iv_left);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setImageResource(resid);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setRigthIC(boolean show, int resid) {
	// ImageView view = (ImageView) findViewById(R.id.title_iv_rigth);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setImageResource(resid);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setCenterIC(boolean show, int resid) {
	// ImageView view = (ImageView) findViewById(R.id.title_iv_center);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setImageResource(resid);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setCenterHWIC(boolean show, int resid, Animation anim) {
	// ImageView view = (ImageView) findViewById(R.id.title_iv_center);
	// view.setAnimation(anim);
	// view.setVisibility(View.VISIBLE);
	// view.setImageResource(resid);
	// }
	//
	// protected void setCenterHWICHidden(boolean show) {
	// ImageView view = (ImageView) findViewById(R.id.title_iv_center);
	// view.setAnimation(null);
	// view.setVisibility(View.GONE);
	// }
	//
	// protected void setLeftText(boolean show, String text) {
	// TextView view = (TextView) findViewById(R.id.title_tv_left);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setText(text);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setLeftText1(boolean show, int text) {
	// TextView view = (TextView) findViewById(R.id.title_tv_left);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setText(text);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setRightText(boolean show, String text) {
	// TextView view = (TextView) findViewById(R.id.title_tv_rigth);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setText(text);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setRightText1(boolean show, int text) {
	// TextView view = (TextView) findViewById(R.id.title_tv_rigth);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setText(text);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setText(boolean show, String text) {
	// TextView view = (TextView) findViewById(R.id.title_tv_title);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setText(text);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void setText1(boolean show, int text) {
	// TextView view = (TextView) findViewById(R.id.title_tv_title);
	// if (show) {
	// view.setVisibility(View.VISIBLE);
	// view.setText(text);
	// } else {
	// view.setVisibility(View.GONE);
	// }
	// }
	//
	// protected void settitleBG(int color) {
	// RelativeLayout view = (RelativeLayout) findViewById(R.id.title_bg);
	// view.setBackgroundColor(color);
	// }
}
