/**

 */
package com.guoku.guokuv4.base;

import com.guoku.R;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.PersonalFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-21 下午4:54:54 
 * 用户信息
 */
public class UserBaseFrament extends BaseActivity{

	FragmentManager fm;
	PersonalFragment pFragment;
	
	 @Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.frament_user_base);
		pFragment = new PersonalFragment();
		pFragment.uBean = (UserBean) getIntent().getSerializableExtra("data");
		pFragment.userType = 1;
		fm = getSupportFragmentManager();
		fm.beginTransaction().add(R.id.fl_content, pFragment).commit();
		
		setGCenter(true, pFragment.uBean.getNickname());
		setGLeft(true, R.drawable.back_selector);
	}
}
