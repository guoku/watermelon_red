/**

 */
package com.guoku.guokuv4.act;

import com.guoku.R;
import com.guoku.guokuv4.adapter.CategoryListAdapter;
import com.guoku.guokuv4.base.BaseActivity;
import com.guoku.guokuv4.bean.TagBean;
import com.guoku.guokuv4.config.Constant;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-17 下午6:09:21 品类二级列表
 */
public class CategoryListAct extends BaseActivity implements
		OnItemClickListener {

	@ViewInject(R.id.tab_list_gv)
	private GridView gv_1;

	CategoryListAdapter cAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_list);

		init();
	}

	private void init() {

		setGLeft(true, R.drawable.back_selector);
		TagBean tagBean = (TagBean) getIntent().getExtras().getSerializable(
				TabAct.class.getName());
		setGCenter(true, tagBean.getTitle());
		cAdapter = new CategoryListAdapter(mContext);
		cAdapter.addLists(tagBean.getContent());
		gv_1.setAdapter(cAdapter);
		gv_1.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putSerializable(TabAct.SECOND_ACT_ONTENT, cAdapter.getItem(arg2));
		openActivity(SecondCategoryAct.class, bundle);
		
		umStatistics(Constant.UM_SUGGESTED_SEC_CLICK, String.valueOf(cAdapter.getItem(arg2).getCategory_id()), cAdapter.getItem(arg2).getCategory_title());
	}

}
