package com.guoku.guokuv4.act;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.StringUtil;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.main.MainActivity2;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

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
		startActivity(new Intent(mContext, LoginAct.class));
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_act);
	}

	@OnClick(R.id.register_btn)
	public void register(View v) {
		String pass = ed_pass.getText().toString();
		String email = ed_email.getText().toString();
		String name = ed_name.getText().toString();
		if (email != null && pass != null && name != null) {
			if (StringUtil.checkEmail(email)) {
				if (16 >= ed_pass.length() && ed_pass.length() >= 6) {
					if (name.length() >= 3) {

						if (type != null && type.equals("sina")) {
							sendConnectionPOST(
									Constant.SINAREGISTER,
									new String[] { "nickname", "email",
											"password", "screen_name",
											"sina_id", "sina_token" },
									new String[] { name, email, pass,
											getIntent().getStringExtra("name"),
											getIntent().getStringExtra("id"),
											getIntent().getStringExtra("token") },
									REGISTER, false);
						} else
							sendConnectionPOST(Constant.REGISTER, new String[] {
									"nickname", "email", "password" },
									new String[] { name, email, pass },
									REGISTER, false);
					} else {
						ToastUtil.show(mContext, "请输入4位以上文字/字母");
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
					EkwingApplication.getInstance().login(bean);
					startActivity(new Intent(this, MainActivity2.class));
					finish();
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
