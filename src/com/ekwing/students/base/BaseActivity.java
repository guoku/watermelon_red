package com.ekwing.students.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
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
	
	private int guideResourceId=0;//引导页图片资源id

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
		addGuideImage();//添加引导页
	}
	
    /**
     * 添加引导图片
     */
    public void addGuideImage() {
        View view = findViewById(R.id.content);//查找通过setContentView上的根布局
        if(view==null)return;
        if(MyPreferences.activityIsGuided(this, this.getClass().getName())){
            //引导过了
            return;
        }
        ViewParent viewParent = view.getParent();
        if(viewParent instanceof FrameLayout){
            final FrameLayout frameLayout = (FrameLayout)viewParent;
            if(guideResourceId!=0){//设置了引导图片
                final ImageView guideImage = new ImageView(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                guideImage.setLayoutParams(params);
                guideImage.setScaleType(ScaleType.FIT_XY);
                guideImage.setImageResource(guideResourceId);
                guideImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        frameLayout.removeView(guideImage);
                        MyPreferences.setIsGuided(getApplicationContext(), BaseActivity.this.getClass().getName());//设为已引导
                    }
                });
                frameLayout.addView(guideImage);//添加引导图片
                
            }
        }
    }
    
    /**子类在onCreate中调用，设置引导图片的资源id
     *并在布局xml的根元素上设置android:id="@id/my_content_view"
     * @param resId
     */
    protected void setGuideResId(int resId){
        this.guideResourceId=resId;
    }
}
