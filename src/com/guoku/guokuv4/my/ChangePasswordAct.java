/**

 */
package com.guoku.guokuv4.my;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;

import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.view.LayoutItemEdit;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: 修改密码
 * @date 2015-8-12 下午5:38:23
 */
public class ChangePasswordAct extends NetWorkActivity{

	private final int HTTP_CODE = 1001;

	@ViewInject(R.id.layout_psd_old)
	LayoutItemEdit lyoutPsdOld;
	@ViewInject(R.id.layout_psd_new)
	LayoutItemEdit lyoutPsdNew;
	@ViewInject(R.id.layout_psd_ok)
	LayoutItemEdit lyoutPsdOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_change_password);
		setGCenter(true, R.string.tv_change_psd);
		setGLeft(true, R.drawable.back_selector);
		setGRigthText(true, R.string.tv_save);

		initView();
	}

	private void initView() {

		lyoutPsdOld.tv1.setText(R.string.tv_psd_old);
		lyoutPsdNew.tv1.setText(R.string.tv_psd_new);
		lyoutPsdOK.tv1.setText(R.string.tv_psd_ok);
		
		lyoutPsdOld.edDel.setTransformationMethod(PasswordTransformationMethod.getInstance());
		lyoutPsdNew.edDel.setTransformationMethod(PasswordTransformationMethod.getInstance());
		lyoutPsdOK.edDel.setTransformationMethod(PasswordTransformationMethod.getInstance());
		
		lyoutPsdOld.tv1.setTextAppearance(mContext, R.style.edit_item_left);
		lyoutPsdNew.tv1.setTextAppearance(mContext, R.style.edit_item_left);
		lyoutPsdOK.tv1.setTextAppearance(mContext, R.style.edit_item_left);
	}

	protected void rightTextOnClick() {
		// TODO Auto-generated method stub
		if(isCheckText()){
			sendConnectionPOST(Constant.PASSWORD_CHANGE, new String[] { "password",
					"new_password", "confirm_password" }, new String[] {
					lyoutPsdOld.edDel.getText().toString(),
					lyoutPsdNew.edDel.getText().toString(),
					lyoutPsdOK.edDel.getText().toString() }, HTTP_CODE, true);
		}
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case HTTP_CODE:
			ToastUtil.show(mContext, R.string.tv_psd_update_ok);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		ToastUtil.show(mContext, R.string.tv_psd_update_error);
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub

	}

	private boolean isCheckText() {

		if (StringUtils.isEmpty(lyoutPsdOld.edDel.getText().toString())) {
			ToastUtil.show(mContext, "1");
			return false;
		}
		if (StringUtils.isEmpty(lyoutPsdNew.edDel.getText().toString())) {
			ToastUtil.show(mContext, "2");
			return false;
		}
		if (StringUtils.isEmpty(lyoutPsdOK.edDel.getText().toString())) {
			ToastUtil.show(mContext, "3");
			return false;
		}
		if(!lyoutPsdNew.edDel.getText().toString().equals(lyoutPsdOK.edDel.getText().toString())){
			ToastUtil.show(mContext, R.string.tv_psd_disaffinity);
			return false;
		}
		
		return true;
	}

}
