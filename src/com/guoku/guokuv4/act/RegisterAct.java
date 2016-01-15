package com.guoku.guokuv4.act;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.main.MainActivity2;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterAct extends NetWorkActivity {

	private static final int REGISTER = 10;
	@ViewInject(R.id.register_email)
	private EditText ed_email;
	@ViewInject(R.id.register_name)
	private EditText ed_name;
	@ViewInject(R.id.register_pass)
	private EditText ed_pass;
	private String type;

	@ViewInject(R.id.com)
	private TextView tv_com;

	@OnClick(R.id.reg_tv_l)
	public void tv_l(View v) {
		finish();
	}

	@OnClick(R.id.com)
	public void com(View v) {
		Intent intent = new Intent(mContext, WebAct.class);
		intent.putExtra("data", "http://www.guoku.com/agreement/");
		startActivity(intent);
	}

	@OnClick(R.id.reg_tv_r)
	public void tv_R(View v) {
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTintManager.setStatusBarTintResource(0);
		
		setContentView(R.layout.register_act);
	}

	@OnClick(R.id.register_btn)
	public void register(View v) {
		String pass = ed_pass.getText().toString();
		String email = ed_email.getText().toString();
		String name = ed_name.getText().toString();
		if (email != null && pass != null && name != null) {
			if (StringUtils.checkEmail(email)) {
				if (16 >= ed_pass.length() && ed_pass.length() >= 8) {
					if (name.length() >= 3 && name.length() <= 30) {
						if (StringUtils.isNickName(name)) {
							if (type != null && type.equals("sina")) {
								sendConnectionPOST(
										Constant.SINAREGISTER,
										new String[] { "nickname", "email",
												"password", "screen_name",
												"sina_id", "sina_token" },
										new String[] {
												name,
												email,
												pass,
												getIntent().getStringExtra(
														"name"),
												getIntent()
														.getStringExtra("id"),
												getIntent().getStringExtra(
														"token") }, REGISTER,
										false);
							} else
								sendConnectionPOST(Constant.REGISTER,
										new String[] { "nickname", "email",
												"password" }, new String[] {
												name, email, pass }, REGISTER,
										false);
						} else {
							ToastUtil.show(mContext, "请输入合法昵称");
						}
					} else {
						ToastUtil.show(mContext, "请输入3位以上－30位以下的文字或字母");
					}
				} else {
					ToastUtil.show(mContext, "密码必须为6-16位");
				}
			} else {
				ToastUtil.show(mContext, "请输入有效的邮件");
			}
		} else {
			ToastUtil.show(mContext, "输入内容不能为空");
		}
	}

	@Override
	protected void onSuccess(String result, int where) {
		switch (where) {
		case REGISTER:
			try {
				JSONObject root = new JSONObject(result);
				if (!root.has("message")) {
					AccountBean bean = new AccountBean();
					bean.setSession(root.getString("session"));
					bean.setUser(JSON.parseObject(root.getString("user"),
							UserBean.class));
					GuokuApplication.getInstance().login(bean);
					Intent intent = new Intent(this, MainActivity2.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(new Intent(intent));
					overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
					ToastUtil.show(context, "注册成功");
				} else {
					ToastUtil.show(context, root.getString("message"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		if ("sina".equals(type)) {
			ToastUtil.show(mContext, "用户名或邮箱已被使用");
		} else {
			ToastUtil.show(mContext, "用户名或邮箱已被使用");
		}
	}

	@Override
	protected void setupData() {
		type = getIntent().getStringExtra("type");
		if ("sina".equals(type)) {
			ed_name.setText(getIntent().getStringExtra("name"));
		} else if ("taobao".equals(type)) {
			ed_name.setText(getIntent().getStringExtra("name"));
		}

		tv_com.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

}
