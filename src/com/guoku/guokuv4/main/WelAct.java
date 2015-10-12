package com.guoku.guokuv4.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.SharePrenceUtil;
import com.guoku.R;
import com.guoku.guokuv4.config.ConfigGK;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

public class WelAct extends NetWorkActivity {
	
	private static final int TAG_CATEGORY = 1001;// 分类

	@ViewInject(R.id.wecome)
	private RelativeLayout wecome;
	@ViewInject(R.id.wecome_flash_iv)
	private ImageView wecome_iv;
	@ViewInject(R.id.imageView1)
	private ImageView imgBaidu;// 百度首发icon
	String channel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welact);
		ViewUtils.inject(this);
		MobclickAgent.updateOnlineConfig(this);

//		if (SharePrenceUtil.getFirstUsed(WelAct.this, Constant.SP_FIRST_URED)) {
			anim = new AlphaAnimation(0.5f, 1.0f);
			anim.setDuration(2000);
			wecome.startAnimation(anim);
			anim.setAnimationListener(am);
//		} else {
//			startActivity(new Intent(WelAct.this, NavigationActivity.class));
//			finish();
//		}
//		init();
	}

	private void init() {

		channel = StringUtils.getAppMetaData(this, "UMENG_CHANNEL");
		if (!StringUtils.isEmpty(channel)) {
			if (channel.equals(ConfigGK.CHANNEL_BAIDU)) {
				imgBaidu.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private AnimationListener am = new AnimationListener() {
		@Override
		public void onAnimationEnd(Animation animation) {
			// 动画执行结束的时候去主页面
			startActivity(new Intent(WelAct.this, MainActivity2.class));
			finish();
		}

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}
	};
	private AlphaAnimation anim;

	@Override
	protected void onPause() {
		super.onPause();
		// JPushInterface.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// JPushInterface.onResume(this);

	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case TAG_CATEGORY:
			SharePrenceUtil.setTab(context, result);//储存品类数据
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
		sendConnection(Constant.CATAB, new String[] {}, new String[] {},
				TAG_CATEGORY, false);
	}

}
