/**

 */
package com.guoku.guokuv4.act;

import android.os.Bundle;
import android.widget.GridView;

import com.ekwing.students.base.BaseActivity;
import com.guoku.R;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-17 下午6:09:21 
 * 品类二级列表
 */
public class CategoryListAct extends BaseActivity{
	
	@ViewInject(R.id.tab_list_gv)
	private GridView gv_1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);
	}
	
	private void init(){
		
	}

}
