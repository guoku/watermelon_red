/**

 */
package com.guoku.guokuv4.act.seach;

import com.guoku.R;
import com.guoku.guokuv4.base.BaseActivity;
import com.guoku.guokuv4.gragment.GuangFragment;
import com.guoku.guokuv4.view.EditTextWithDel;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月5日 下午2:46:20 
 */
public class SearchAct extends BaseActivity{
	
	@ViewInject(R.id.ed_search)
	EditTextWithDel edTextWithDel;
	
	public String searchStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seach);
		
		searchStr = getIntent().getStringExtra(GuangFragment.class.getName());
		init();
	}
	
	private void init(){
		
		edTextWithDel.setText(searchStr);
	}

}
