/**

 */
package com.guoku.guokuv4.act.seach;

import com.guoku.R;
import com.guoku.guokuv4.act.seach.SearchInterface.OnActivityChangeListener;
import com.guoku.guokuv4.act.seach.SearchInterface.OnFragmentChangeListener;
import com.guoku.guokuv4.base.BaseActivity;
import com.guoku.guokuv4.gragment.GuangFragment;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.EditTextWithDel;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月5日 下午2:46:20 
 */
public class SearchAct extends BaseActivity implements OnActivityChangeListener{
	
	@ViewInject(R.id.ed_search)
	EditTextWithDel edTextWithDel;
	
	public static String searchStr;
	
	public static OnFragmentChangeListener onFragmentChangeListener;
	   
	public static void setOnFragmentChangeListener(
			OnFragmentChangeListener onFragmentChangeListener) {
		SearchAct.onFragmentChangeListener = onFragmentChangeListener;
	}
	
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
		edTextWithDel.addTextChangedListener(watcher);
		edTextWithDel.clearFocus();
	}
	
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		onFragmentChangeListener = (OnFragmentChangeListener) fragment;
	}
	
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			searchStr = s.toString();
			ToastUtil.show(mContext, searchStr);
			if (s.length() > 0) {
//				search(0, seachConten);
				onFragmentChangeListener.onFragmentChange(s.toString());
			} else {
//				isShowSeachResult(false);
			}
		}
	};

	@Override
	public void onActivityChange() {
		// TODO Auto-generated method stub
		
	}

}
