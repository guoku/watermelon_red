package com.guoku.guokuv4.main;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.LaunchBean;
import com.guoku.guokuv4.bean.UnReadData;
import com.guoku.guokuv4.config.ConfigGK;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.LogGK;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.jpush.android.api.JPushInterface;

public class WelAct extends NetWorkActivity {

	private static final int TAG_CATEGORY = 1001;// 分类
	private static final int LAUNCH = 1002;// 引导页图片
	private static final int SHOP_COUNT = 1003;// 有多少个更新商品
	private static final int TAG_ARTICLE = 1004;// 精选图文
	private static final int JINGXUAN_DOWN = 1005;// 精选商品
	private static final int DISCOVER = 1006;// 发现页
	private static final int APNS = 1007;// 发现页

	@ViewInject(R.id.wecome)
	private RelativeLayout wecome;
	@ViewInject(R.id.wecome_flash_iv)
	private ImageView wecome_iv;
	@ViewInject(R.id.imageView1)
	private ImageView imgBaidu;// 百度首发icon
	String channel;

	public static String tempArticle;// 预加载精选图文
	public static String tempGood;// 预加载精选商品
	public static String tempDiscover;// 预加载发现

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mTintManager.setStatusBarTintEnabled(false);
		setContentView(R.layout.welact);
		ViewUtils.inject(this);
		MobclickAgent.updateOnlineConfig(this);

		// if (SharePrenceUtil.getFirstUsed(WelAct.this,
		// Constant.SP_FIRST_URED)) {
		anim = new AlphaAnimation(0.5f, 1.0f);
		anim.setDuration(2000);
		wecome.startAnimation(anim);
		anim.setAnimationListener(am);
		// } else {
		// startActivity(new Intent(WelAct.this, NavigationActivity.class));
		// finish();
		// }
		// init();
	}

	private void init() {

		channel = StringUtils.getAppMetaData(this, "UMENG_CHANNEL");
		if (!StringUtils.isEmpty(channel)) {
			if (channel.equals(ConfigGK.CHANNEL_ZHIHUIYUN)) {
				imgBaidu.setVisibility(View.VISIBLE);
			} else {
				imgBaidu.setVisibility(View.GONE);
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
			int VERSION = Integer.parseInt(android.os.Build.VERSION.SDK);
			if (VERSION >= 5) {
				overridePendingTransition(R.anim.act_fade_in, R.anim.act_fade_out);
			}
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
			SharePrenceUtil.setTab(context, result);// 储存品类数据
			break;
		case LAUNCH:
			if (!StringUtils.isEmpty(result)) {
				try {
					LaunchBean launchBean = JSON.parseObject(result, LaunchBean.class);
					launchBean.getLaunch_image_url().replace(".jpg", ".gif");
					GuokuApplication.getInstance().setLaunchBean(launchBean);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			break;
		case SHOP_COUNT:
			UnReadData unReadData = JSON.parseObject(result, UnReadData.class);
			// unReadData.setUnread_selection_count(12);
			GuokuApplication.getInstance().setUnReadData(unReadData);
			break;
		case TAG_ARTICLE:
			tempArticle = result;
			break;
		case JINGXUAN_DOWN:
			tempGood = result;
			break;
		case DISCOVER:
			tempDiscover = result;
			break;
		case APNS:
			LogGK.d("发送apns成功");
			break;
			
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case LAUNCH:
			// ToastUtil.show(this, "引导页加载失败");
			break;
		case APNS:
			LogGK.d("发送apns失败");
			break;
		default:
			break;
		}
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		sendConnection(Constant.CATAB, new String[] {}, new String[] {}, TAG_CATEGORY, false);

		sendConnection(Constant.LAUNCH, new String[] {}, new String[] {}, LAUNCH, false);

		sendConnection(Constant.ARTICLES, new String[] { "page", "size" }, new String[] { "1", "20" }, TAG_ARTICLE,
				false);

		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp", "rcat" },
				new String[] { "30", System.currentTimeMillis() / 1000 + "", "" }, JINGXUAN_DOWN, false);

		sendConnection(Constant.DISCOVER, new String[] {}, new String[] {}, DISCOVER, false);

		// 暂时先屏蔽浏览位置记录相关功能
		// sendConnection(Constant.SHOP_UNREAD, new String[] {}, new String[]
		// {}, SHOP_COUNT, false);

		if (!StringUtils.isEmpty(JPushInterface.getRegistrationID(getApplicationContext()))) {

			sendConnectionPOST(Constant.APNS_TOKEN, new String[] { "rid", "model", "version", "session" },
					new String[] { JPushInterface.getRegistrationID(getApplicationContext()), "Android",
							GuokuUtil.getVersionName(mContext),
							GuokuApplication.getInstance().getSession()},
					APNS, false);
		}

	}
}
