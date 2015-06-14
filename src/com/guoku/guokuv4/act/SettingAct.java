package com.guoku.guokuv4.act;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.customview.CustomShareBoard;
import com.ekwing.students.utils.ToastUtil;
import com.ekwing.students.utils.Utils;
import com.guoku.R;
import com.guoku.guokuv4.main.MainActivity2;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;

public class SettingAct extends NetWorkActivity {
	@ViewInject(R.id.set_logout)
	private Button button;
	@ViewInject(R.id.setting_tv_code)
	private TextView tv;
	private CustomShareBoard shareBoard;
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingact);
		setGCenter(true, "设置");
		setGLeft(true, R.drawable.back_selector);
		if (EkwingApplication.getInstance().getBean() == null) {
			button.setBackgroundResource(R.color.g_blue);
			button.setText("登录");
		}
		tv.setText(Utils.getVersionName(mContext));
		shareBoard = new CustomShareBoard(this);
		shareBoard
				.setShareContext(
						"果库 - 精英消费指南。帮助你发现互联网上最有趣、最人气、最实用的好商品，恪守选品标准和美学格调，开拓精英视野与生活想象。http://www.guoku.com/download/",
						new UMImage(mContext,
								"http://static.guoku.com/static/images/download_app.jpg"),
						"http://www.guoku.com/");
	}

	@OnClick(R.id.setting_ll_advice)
	public void advice(View v) {
		FeedbackAgent agent = new FeedbackAgent(context);
		agent.startFeedbackActivity();
	}

	@OnClick(R.id.setting_ll_weixin)
	public void weixin(View v) {
		shareBoard
				.setShareContext(
						"帮助你发现互联网上最有趣、最人气、最实用的好商品，恪守选品标准和美学格调，开拓精英视野与生活想象。http://www.guoku.com/download/",
						new UMImage(mContext, BitmapFactory.decodeResource(
								getResources(), R.drawable.share_ic)),
						"http://www.guoku.com/", "果库 - 精英消费指南");

		shareBoard.shareW();

	}

	@OnClick(R.id.setting_ll_sina)
	public void sina(View v) {
		shareBoard
				.setShareContext(
						"果库，精英消费指南。帮助你发现互联网上最有趣、最人气、最实用的好商品，恪守选品标准和美学格调，开拓精英视野与生活想象。http://www.guoku.com/download/",
						new UMImage(mContext,
								"http://static.guoku.com/static/images/download_app.jpg"),
						"http://www.guoku.com/download/");
		shareBoard.shareS();
	}

	@OnClick(R.id.setting_ll_clear)
	public void clear(View v) {
		ImageLoader.getInstance().clearDiskCache();
		ImageLoader.getInstance().clearMemoryCache();
		ToastUtil.show(context, "清理完成");
	}

	@Override
	protected void onSuccess(String result, int where) {
		switch (where) {

		default:
			break;
		}
	}

	@OnClick(R.id.set_logout)
	public void LogOut(View v) {
		if (EkwingApplication.getInstance().getBean() == null) {
			startActivity(new Intent(mContext, LoginAct.class));
		} else {
			EkwingApplication.getInstance().logout();
			startActivity(new Intent(mContext, MainActivity2.class));
			finish();
		}
	}

	@Override
	protected void onFailure(String result, int where) {
	}

	@Override
	protected void setupData() {
	}

}
