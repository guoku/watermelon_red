package com.guoku.guokuv4.act;

import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.CallbackContext;
import com.alibaba.sdk.android.login.LoginService;
import com.alibaba.sdk.android.login.callback.LoginCallback;
import com.alibaba.sdk.android.session.model.Session;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.main.MainActivity2;
import com.guoku.guokuv4.utils.DialogUtils;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAct extends NetWorkActivity {

	private static final int LOGIN = 10;
	private static final int SINALOGIN = 12;
	private static final int WXLOGIN = 15;
	private static final int TAOBAOLOGIN = 14;
	private static final int FORGET = 13;

	@ViewInject(R.id.login_username)
	private EditText ed_name;

	@ViewInject(R.id.login_pass)
	private EditText ed_pass;
	private Session session;
	private Bundle value;
	private Map<String, Object> sinainfo;
	private Session taobaoSession;

	UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.login");

	@OnClick(R.id.reg_tv_l)
	public void tv_l(View v) {
		finish();
		overridePendingTransition(R.anim.push_down_in,
				R.anim.push_down_out);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		CallbackContext.onActivityResult(requestCode, resultCode, data);
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@OnClick(R.id.forget)
	public void forget(View v) {
		// finish();
		final EditText editText = new EditText(mContext);
		DialogUtils.getEDialog(mContext, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				sendConnectionPOST(Constant.FORGET, new String[] { "email" },
						new String[] { editText.getText().toString() }, FORGET,
						true);

			}
		}, "忘记密码", "输入注册时的邮箱", editText).show();
	}

	@OnClick(R.id.login_no_wx)
	public void wx(View v) {
		UMWXHandler wxHandler = new UMWXHandler(this, Constant.WX_APPID,
				Constant.WX_SECRET);
		wxHandler.addToSocialSDK();

		mController.doOauthVerify(mContext, SHARE_MEDIA.WEIXIN,
				new UMAuthListener() {
					@Override
					public void onStart(SHARE_MEDIA platform) {
						// Toast.makeText(mContext, "授权开始", Toast.LENGTH_SHORT)
						// .show();
					}

					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
						Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						// Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT)
						// .show();
						// 获取相关授权信息
						mController.getPlatformInfo(LoginAct.this,
								SHARE_MEDIA.WEIXIN, new UMDataListener() {
									@Override
									public void onStart() {
										// Toast.makeText(LoginAct.this,
										// "获取平台数据开始...",
										// Toast.LENGTH_SHORT).show();
									}

									@Override
									public void onComplete(int status,
											Map<String, Object> info) {
										if (status == 200 && info != null) {
											sendConnectionPOST(
													Constant.WXLOGIN,
													new String[] { "unionid",
															"nickname",
															"headimgurl" },
													new String[] {
															info.get("unionid")
																	.toString(),
															info.get("nickname")
																	.toString(),
															info.get(
																	"headimgurl")
																	.toString() },
													WXLOGIN, true);
											//
										} else {
										}

									}
								});
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT)
								.show();
					}
				});

	}

	@OnClick(R.id.login_no_sina)
	public void sina(View v) {
		mController.doOauthVerify(this, SHARE_MEDIA.SINA, new UMAuthListener() {
			@Override
			public void onError(SocializeException e, SHARE_MEDIA platform) {
			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA platform) {
				if (value != null) {
					LoginAct.this.value = value;
					Logger.i(TAG, value.toString());

					mController.getPlatformInfo(mContext, SHARE_MEDIA.SINA,
							new UMDataListener() {
								@Override
								public void onStart() {

								}

								@Override
								public void onComplete(int status,
										Map<String, Object> info) {

									if (status == 200 && info != null) {
										sinainfo = info;
										StringBuilder sb = new StringBuilder();
										Set<String> keys = info.keySet();
										for (String key : keys) {
											sb.append(key + "="
													+ info.get(key).toString()
													+ "\r\n");
										}

										sendConnectionPOST(
												Constant.SINALOGIN,
												new String[] { "sina_id",
														"sina_token",
														"screen_name" },
												new String[] {
														LoginAct.this.sinainfo
																.get("uid")
																.toString(),
														LoginAct.this.sinainfo
																.get("access_token")
																.toString(),
														LoginAct.this.sinainfo
																.get("screen_name")
																.toString() },
												SINALOGIN, true);

									} else {
									}
								}
							});

				} else {
				}
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
			}

			@Override
			public void onStart(SHARE_MEDIA platform) {
			}
		});
	}

	@OnClick(R.id.login_no_tao2)
	public void taobao(View v) {
		LoginService loginService = AlibabaSDK.getService(LoginService.class);
		loginService.showLogin(this, new LoginCallback() {

			@Override
			public void onSuccess(Session session) {
				taobaoSession = session;
				sendConnectionPOST(Constant.TAOBAOLOGIN, new String[] {
						"user_id", "nick" }, new String[] {
						session.getUserId(), session.getUser().nick, },
						TAOBAOLOGIN, true);

			}

			@Override
			public void onFailure(int code, String message) {
				Toast.makeText(LoginAct.this, "授权取消", Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	@OnClick(R.id.reg_tv_r)
	public void tv_R(View v) {
		startActivity(new Intent(mContext, RegisterAct.class));
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_act);
	}

	@OnClick(R.id.login_btn_login)
	public void Push(View v) {

		if (ed_name.getText().toString() != null
				&& !"".equals(ed_name.getText().toString().trim())
				&& ed_pass.getText().toString() != null
				&& !"".equals(ed_pass.getText().toString().trim())) {
			sendConnectionPOST(Constant.LOGIN, new String[] { "email",
					"password" }, new String[] { ed_name.getText().toString(),
					ed_pass.getText().toString() }, LOGIN, true);
		} else {
			ToastUtil.show(context, "输入内容不能为空");
		}
	}

	@OnClick(R.id.title_bar_left_iv)
	public void Left(View v) {
		finish();
	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void Rigth(View v) {
		startActivity(new Intent(mContext, RegisterAct.class));
		finish();
	}

	@Override
	protected void onSuccess(String result, int where) {
		// Utils.hideKeyboard(context, text);
		switch (where) {
		case LOGIN:
			try {
				JSONObject root = new JSONObject(result);
				if (!root.has("message")) {
					AccountBean bean = new AccountBean();
					bean.setSession(root.getString("session"));
					bean.setUser(JSON.parseObject(root.getString("user"),
							UserBean.class));
					GuokuApplication.getInstance().login(bean);
					startActivity(new Intent(this, MainActivity2.class));
					finish();
				} else {
					ToastUtil.show(context, "邮箱或密码错误");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case WXLOGIN:
			try {
				JSONObject root = new JSONObject(result);
				if (!root.has("message")) {
					AccountBean bean = new AccountBean();
					bean.setSession(root.getString("session"));
					bean.setUser(JSON.parseObject(root.getString("user"),
							UserBean.class));
					GuokuApplication.getInstance().login(bean);
					startActivity(new Intent(this, MainActivity2.class));
					finish();
				} else {
					ToastUtil.show(context, "用户名或密码错误");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case SINALOGIN:
			try {
				JSONObject root = new JSONObject(result);
				if (!root.has("message")) {
					AccountBean bean = new AccountBean();
					bean.setSession(root.getString("session"));
					bean.setUser(JSON.parseObject(root.getString("user"),
							UserBean.class));
					GuokuApplication.getInstance().login(bean);
					startActivity(new Intent(this, MainActivity2.class));
					finish();
				} else {
					ToastUtil.show(context, "用户名或密码错误");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case TAOBAOLOGIN:
			try {
				JSONObject root = new JSONObject(result);
				if (!root.has("message")) {
					AccountBean bean = new AccountBean();
					bean.setSession(root.getString("session"));
					bean.setUser(JSON.parseObject(root.getString("user"),
							UserBean.class));
					GuokuApplication.getInstance().login(bean);
					startActivity(new Intent(this, MainActivity2.class));
					finish();
				} else {
					ToastUtil.show(context, "用户名或密码错误");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case FORGET:
			ToastUtil.show(context, "已发送");
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		switch (where) {
		case LOGIN:
			ToastUtil.show(context, "登录失败,用户名或密码错误");
			break;

		case WXLOGIN:
			ToastUtil.show(context, "登录失败,用户名或密码错误");
			break;
		case SINALOGIN:

			if (sinainfo != null) {
				Intent intent = new Intent(mContext, RegisterAct.class);
				intent.putExtra("type", "sina");
				intent.putExtra("name", sinainfo.get("screen_name").toString());
				intent.putExtra("id", sinainfo.get("uid").toString());
				intent.putExtra("token", sinainfo.get("access_token")
						.toString());
				intent.putExtra("avatar", sinainfo.get("profile_image_url")
						.toString());
				intent.putExtra("description", sinainfo.get("description")
						.toString());
				startActivity(intent);
			} else {
				ToastUtil.show(mContext, "sina微博授权失败");
			}
			break;
		case TAOBAOLOGIN:

			if (taobaoSession != null) {
				Intent intent = new Intent(mContext, RegisterAct.class);
				intent.putExtra("type", "taobao");
				intent.putExtra("name", taobaoSession.getUser().nick);
				startActivity(intent);
			} else {
				ToastUtil.show(mContext, "淘宝授权失败");
			}
			break;
		}
		GuokuUtil.hideKeyboard(context, ed_pass);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void setupData() {

	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			overridePendingTransition(R.anim.push_down_in,
					R.anim.push_down_out);
		}
		return super.onKeyDown(keyCode, event);
	}

}
