package com.guoku.guokuv4.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.SharePrenceUtil;
import com.guoku.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

public class WelAct extends NetWorkActivity {
	private static final int TAB = 11;

	@ViewInject(R.id.wecome)
	private RelativeLayout wecome;
	@ViewInject(R.id.wecome_flash_iv)
	private ImageView wecome_iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welact);
		ViewUtils.inject(this);
		MobclickAgent.updateOnlineConfig(this);

		sendConnection(Constant.TAB, new String[] {}, new String[] {}, TAB,
				false);

		if (SharePrenceUtil.getFirstUsed(WelAct.this, Constant.SP_FIRST_URED)) {
			anim = new AlphaAnimation(0.5f, 1.0f);
			anim.setDuration(2000);
			wecome.startAnimation(anim);
			anim.setAnimationListener(am);
		} else {
			startActivity(new Intent(WelAct.this, NavigationActivity.class));
			finish();
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
		case TAB:
			SharePrenceUtil.setTabList(context, result);
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

}
