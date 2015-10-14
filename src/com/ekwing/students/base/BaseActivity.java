package com.ekwing.students.base;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
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
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.ekwing.students.EkwingApplication;
import com.guoku.R;
import com.guoku.guokuv4.utils.MyPreferences;
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

	public static final String LIKE = "like";
	public static final String TIME = "time";

	FrameLayout frameLayout;// 引导图
	View viewsOne, viewsTwo;

	TextView tvTwo;
	ImageView imgTrage;
	
	public static ArrayList<String> webViewTitle = new ArrayList<String>();

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

	protected void openActivityForResult(Class<?> pClass, Bundle pBundle,
			int requestCode) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
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
		addGuideImage();// 添加引导页
	}

	/**
	 * 添加引导图片
	 */
	public void addGuideImage() {
		View view = findViewById(R.id.content);// 查找通过setContentView上的根布局
		if (view == null)
			return;
		if (!MyPreferences.activityIsGuided(this, BaseActivity.this.getClass().getName())) {
//			 引导过了

			ViewParent viewParent = view.getParent();
			if (viewParent instanceof FrameLayout) {
				frameLayout = (FrameLayout) viewParent;

				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.CENTER;
				LayoutInflater lInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				;
				viewsOne = lInflater.inflate(R.layout.view_walkthrough_one,
						null);
				viewsTwo = lInflater.inflate(R.layout.view_walkthrough_two,
						null);

				final TextView textView = (TextView) viewsOne
						.findViewById(R.id.textView1);

				tvTwo = (TextView) viewsTwo.findViewById(R.id.textView3);// 下面一层textview
				imgTrage = (ImageView) viewsTwo
						.findViewById(R.id.img_walkthrough_trgl);

				textView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// frameLayout.removeViewAt(2);
						frameLayout.removeViewAt(2);
						showFrameLayoutTwo();
						setCurrentItems();
						MyPreferences.setIsGuided(getApplicationContext(), BaseActivity.this
								.getClass().getName());// 设为已引导
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
	
	
	public String goBack(WebView view){
		if (view.canGoBack()) {
			view.goBack(); // 后退
			if(webViewTitle.size() > 0){
				webViewTitle.remove(webViewTitle.size() - 1);
			}
		} else {
			finish();
		}
		
		return webViewTitle.get(webViewTitle.size() - 1);
	}
}
