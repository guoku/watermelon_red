/**

 */
package com.guoku.guokuv4.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.TextView;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.act.UserInfoAct;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.view.LayoutItemEdit;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.fb.model.UserInfo;

/**
 * @zhangyao
 * @Description: 修改邮箱
 * @date 2015-8-12 下午9:40:06
 */
public class ChangeEmailAct extends NetWorkActivity {

	private final int EMAIL = 1001;

	@ViewInject(R.id.tv_emali_now)
	TextView tvEmailNow;
	@ViewInject(R.id.layout_emali_new)
	LayoutItemEdit tvEmailNew;
	@ViewInject(R.id.layout_email_psd)
	LayoutItemEdit tvEmailPsd;
	@ViewInject(R.id.title_bar_rigth_tv)
	TextView rightTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_change_emali);
		setGCenter(true, R.string.tv_change_email);
		setGLeft(true, R.drawable.back_selector);
		setGRigthText(true, R.string.tv_save);

		initView();
	}

	private void initView() {

		tvEmailNow.setText(EkwingApplication.getInstance().getBean().getUser()
				.getEmail());
		tvEmailNew.tv1.setText(R.string.tv_email_new);
		tvEmailPsd.tv1.setText(R.string.tv_email_psd);

		tvEmailNew.tv1.setTextAppearance(mContext, R.style.edit_item_left);
		tvEmailPsd.tv1.setTextAppearance(mContext, R.style.edit_item_left);

		tvEmailPsd.edDel.setTransformationMethod(PasswordTransformationMethod
				.getInstance());

		tvEmailNew.edDel.addTextChangedListener(tWatcher1);
		tvEmailPsd.edDel.addTextChangedListener(tWatcher2);

		rightTv.setEnabled(false);
	}

	@Override
	protected void rightTextOnClick() {
		// TODO Auto-generated method stub
		super.rightTextOnClick();

		if (isCheckText()) {
			sendConnectionPOST(Constant.USERUPDATA + "email/", new String[] {
					"password", "email" }, new String[] {
					tvEmailPsd.edDel.getText().toString(),
					tvEmailNew.edDel.getText().toString() }, EMAIL, true);
		}
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case EMAIL:
			ToastUtil.show(mContext, R.string.tv_email_update_ok);
			EkwingApplication.getInstance().getBean().getUser()
					.setEmail(tvEmailNew.edDel.getText().toString());
			Intent intent = new Intent();
			setResult(UserInfoAct.INTENT_REQUEST_CODE, intent);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		ToastUtil.show(mContext, R.string.tv_email_update_error);
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub

	}

	private boolean isCheckText() {

		if (StringUtils.isEmpty(tvEmailNew.edDel.getText().toString())) {
			ToastUtil.show(mContext, "1");
			return false;
		}
		if (StringUtils.isEmpty(tvEmailPsd.edDel.getText().toString())) {
			ToastUtil.show(mContext, "2");
			return false;
		}
		if (!StringUtils.isEmail(tvEmailPsd.edDel.getText().toString())) {
			ToastUtil.show(mContext, R.string.tv_email_input_email_error);
			return false;
		}

		return true;
	}

	TextWatcher tWatcher1 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			if (arg0.length() > 0) {
				isSave();
			}

		}
	};

	TextWatcher tWatcher2 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			if (arg0.length() > 0) {
				isSave();
			}
		}
	};

	private void isSave() {

		if (StringUtils.isEmpty(tvEmailNew.edDel.getText().toString())
				|| StringUtils.isEmpty(tvEmailPsd.edDel.getText().toString())) {
			rightTv.setEnabled(false);
			rightTv.setTextColor(getResources()
					.getColor(R.color.title_bar_gray));
		} else {
			rightTv.setEnabled(true);
			rightTv.setTextColor(getResources()
					.getColor(R.color.title_bar_blue));
		}
	}

}
