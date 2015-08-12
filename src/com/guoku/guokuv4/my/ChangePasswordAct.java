/**

 */
package com.guoku.guokuv4.my;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.LinearLayout;

import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
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
	
	private void initView(){
		
		lyoutPsdOld.tv1.setText(R.string.tv_psd_old);
		lyoutPsdNew.tv1.setText(R.string.tv_psd_new);
		lyoutPsdOK.tv1.setText(R.string.tv_psd_ok);
		
	}
	
	protected void rightTextOnClick() {
		// TODO Auto-generated method stub
		sendConnectionPOST(Constant.USERUPDATA + "account/",
				new String[] { "current_password", "new_password", "password_confirmation"}, new String[] { 
				"zzzzzzzz","xxxxxxxx","xxxxxxxx"
		}, HTTP_CODE, false);
	}
	

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		ToastUtil.show(mContext, "1");
		finish();
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		ToastUtil.show(mContext, "2");
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		
	}
	


}
