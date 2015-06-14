package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.guoku.R;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

public class EntityAct extends NetWorkActivity {

	private static final int ENTITY = 10;
	private static final int PROINFO = 12;

	private EntityAdapter entityAdapter;

	@ViewInject(R.id.entity_act_lv)
	private PullToRefreshListView lv;
	private ArrayList<EntityBean> listEntity;
	private String uid;
	private String tag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entity_act);

		setGLeft(true, R.drawable.back_selector);
	}

	@Override
	protected void onSuccess(String result, int where) {
		lv.onRefreshComplete();
		switch (where) {
		case ENTITY:
			JSONObject root;
			try {
				root = new JSONObject(result);
				listEntity.addAll((ArrayList<EntityBean>) JSON.parseArray(
						root.getString("entity_list"), EntityBean.class));
				entityAdapter.setList(listEntity);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
	}

	@Override
	protected void setupData() {
		tag = getIntent().getStringExtra("name");
		uid = getIntent().getStringExtra("data");
		setGCenter(true, tag);
		entityAdapter = new EntityAdapter(context);
		listEntity = new ArrayList<EntityBean>();
		lv.setMode(Mode.DISABLED);
		lv.setAdapter(entityAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO
						+ listEntity.get(arg2 - 1).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { listEntity
								.get(arg2 - 1).getEntity_id() }, PROINFO, true);
			}
		});

		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
			}
		});
		sendConnection(Constant.GETTAGLIST + uid + "/tag/" + tag.trim() + "/",
				new String[] {}, new String[] {}, ENTITY, false);

	}

}
