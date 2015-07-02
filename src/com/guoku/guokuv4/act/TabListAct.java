package com.guoku.guokuv4.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Logger;
import com.guoku.R;
import com.guoku.guokuv4.adapter.TABListAdapter;
import com.guoku.guokuv4.entity.test.TAB1Bean;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TabListAct extends NetWorkActivity {

	private TAB1Bean bean;

	@ViewInject(R.id.tab_list_gv_1)
	private GridView gv_1;

	@ViewInject(R.id.tab_list_gv_2)
	private GridView gv_2;

	@ViewInject(R.id.tab_list_tv_1)
	private TextView tv_1;

	@ViewInject(R.id.tab_list_tv_2)
	private TextView tv_2;

	private TABListAdapter adapter1, adapter2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_list_act);
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

		bean = (TAB1Bean) getIntent().getSerializableExtra("data");
		setGCenter(true, bean.getTitle());
		setGLeft(true, R.drawable.back_selector);

		adapter1 = new TABListAdapter(mContext);
		adapter2 = new TABListAdapter(mContext);
		gv_1.setAdapter(adapter1);
		gv_2.setAdapter(adapter2);
		Logger.i(TAG, "l1---->" + bean.getList1());
		Logger.i(TAG, "l2---->" + bean.getList2());
		if (bean.getList1() == null) {
			tv_1.setVisibility(View.GONE);
		} else {
			adapter1.setList(bean.getList1());
		}
		if (bean.getList1() == null) {
			tv_2.setVisibility(View.GONE);
		} else {
			adapter2.setList(bean.getList2());
		}

		gv_1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, TabAct.class);
				intent.putExtra("data", bean.getList1().get(arg2)
						.getCategory_id());
				intent.putExtra("name", bean.getList1().get(arg2)
						.getCategory_title());
				mContext.startActivity(intent);

			}
		});
		gv_2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(mContext, TabAct.class);
				intent.putExtra("data", bean.getList1().get(arg2)
						.getCategory_id());
				intent.putExtra("name", bean.getList1().get(arg2)
						.getCategory_title());
				mContext.startActivity(intent);

			}
		});

	}
}
