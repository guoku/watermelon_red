/**

 */
package com.guoku.guokuv4.my;

import android.os.Bundle;
import android.widget.TextView;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.guoku.R;
import com.guoku.guokuv4.view.LayoutItemEdit;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: 修改邮箱
 * @date 2015-8-12 下午9:40:06 
 */
public class ChangeEmailAct extends NetWorkActivity{
	
	@ViewInject(R.id.tv_emali_now)
	TextView tvEmailNow;
	@ViewInject(R.id.layout_emali_new)
	LayoutItemEdit tvEmailNew;
	@ViewInject(R.id.layout_email_psd)
	LayoutItemEdit tvEmailPsd;
	
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
	
	private void initView(){
		
		tvEmailNow.setText(EkwingApplication.getInstance().getBean().getUser().getEmail());
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		
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
