/**

 */
package com.guoku.guokuv4.base;

import com.guoku.R;
import com.guoku.app.GuokuApplication;
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
		if(pFragment.uBean.isAuthorized_author()){
			pFragment.userType = 2;
		}else{
			pFragment.userType = 1;
		}
		if(GuokuApplication.getInstance().getBean() != null){
			if(pFragment.uBean.getUser_id().equals(GuokuApplication.getInstance().getBean().getUser().getUser_id())){
				pFragment.userType = 0;
				pFragment.isUserList = true;
			}
		}
		
		
		fm = getSupportFragmentManager();
		fm.beginTransaction().add(R.id.fl_content, pFragment).commit();
		
		setGCenter(true, pFragment.uBean.getNick());
		setGLeft(true, R.drawable.back_selector);
	}
}
