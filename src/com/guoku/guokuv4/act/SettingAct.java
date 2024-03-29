package com.guoku.guokuv4.act;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.login.LoginService;
import com.alibaba.sdk.android.login.callback.LogoutCallback;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.main.MainActivity2;
import com.guoku.guokuv4.my.ChangeEmailAct;
import com.guoku.guokuv4.my.ChangePasswordAct;
import com.guoku.guokuv4.share.CustomShareBoard;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.fb.FeedbackAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

public class SettingAct extends NetWorkActivity {
	
	@ViewInject(R.id.img_red_round)
	ImageView redRound;
	@ViewInject(R.id.view_line)
	View viewLine;
	@ViewInject(R.id.user_info_ll_pass)
	View userPass;
	@ViewInject(R.id.user_info_ll_email)
	View userEmail;
	@ViewInject(R.id.user_info_tv_email)
	private TextView tv_email;
	@ViewInject(R.id.set_logout)
	private TextView button;
	@ViewInject(R.id.set_login)
	Button btLogin;
	@ViewInject(R.id.setting_tv_code)
	private TextView tv;
	private CustomShareBoard shareBoard;
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	
	boolean isLoginAct;//判断是否跳到登录页面
	
	private AccountBean bean;

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
	
	public void onEventMainThread(String email) {
		bean = GuokuApplication.getInstance().getBean();
		if(bean.getUser().isMail_verified()){
			isCheckEmail(true);
		}else{
			isCheckEmail(false);
		}
	}
	
	@SuppressLint("ResourceAsColor")
	private void isCheckEmail(boolean isCheck){
		if(isCheck){
			tv_email.setTextColor(getResources().getColor(R.color.g_main));
			tv_email.setText(bean.getUser().getEmail());
			redRound.setVisibility(View.GONE);
		}else{
			tv_email.setText(bean.getUser().getEmail() + getResources().getString(R.string.tv_email_uncheck));
			tv_email.setTextColor(getResources().getColor(R.color.gray_fzxx));
			redRound.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		EventBus.getDefault().register(this);
		
		setContentView(R.layout.settingact);
		setGCenter(true, "设置");
		setGLeft(true, R.drawable.back_selector);
		if (GuokuApplication.getInstance().getBean() == null) {
			btLogin.setVisibility(View.VISIBLE);
			button.setVisibility(View.GONE);
			userEmail.setVisibility(View.GONE);
			userPass.setVisibility(View.GONE);
			viewLine.setVisibility(View.GONE);
		}else{
			bean = GuokuApplication.getInstance().getBean();
			if(bean.getUser().isMail_verified()){
				isCheckEmail(true);
			}else{
				isCheckEmail(false);
			}
		}
		tv.setText(getResources().getString(R.string.tv_set_version_code, GuokuUtil.getVersionName(mContext)));
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
	
	@OnClick(R.id.set_login)
	public void Login(View v){
		startActivity(new Intent(mContext, LoginAct.class));
		isLoginAct = true;
	}

	@OnClick(R.id.set_logout)
	public void LogOut(View v) {
		GuokuApplication.getInstance().logout();
		mController.deleteOauth(mContext, SHARE_MEDIA.SINA,
				new SocializeClientListener() {
					@Override
					public void onStart() {
					}

					@Override
					public void onComplete(int status,
							SocializeEntity entity) {
						if (status == 200) {
							// Toast.makeText(mContext, "删除成功.",
							// Toast.LENGTH_SHORT).show();
						} else {
							// Toast.makeText(mContext, "删除失败",
							// Toast.LENGTH_SHORT).show();
						}
					}
				});

		LoginService loginService = AlibabaSDK
				.getService(LoginService.class);
		loginService.logout(this, new LogoutCallback() {

			@Override
			public void onFailure(int code, String msg) {
				// Toast.makeText(SettingAct.this, "登出失败",
				// Toast.LENGTH_SHORT)
				// .show();

			}

			@Override
			public void onSuccess() {
				// Toast.makeText(SettingAct.this, "登出成功",
				// Toast.LENGTH_SHORT)
				// .show();

			}
		});

		Intent intent = new Intent(mContext, MainActivity2.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	protected void onFailure(String result, int where) {
	}

	@Override
	protected void setupData() {
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isLoginAct){
			if (GuokuApplication.getInstance().getBean() != null){
				finish();
			}
		}
	}
	
	@OnClick(R.id.user_info_ll_pass)
	public void pass(View v) {

		startActivity(new Intent(this, ChangePasswordAct.class));
	}
	
	@OnClick(R.id.user_info_ll_email)
	public void email(View v) {
		if(bean.getUser().isMail_verified()){
			openActivity(ChangeEmailAct.class);
		}else{
			openActivity(EmailCheckAct.class);
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
}
