package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.SharePrenceUtil;
import com.guoku.R;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.adapter.GVAdapter;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class TabAct extends NetWorkActivity implements OnClickListener, OnCheckedChangeListener {

	private static final int CATABLIST = 10;
	private static final int STAT = 11;
	private static final int PROINFO = 12;
	private static final int LIST = 1;
	private static final int GRID = 2;

	@ViewInject(R.id.tab_lv)
	private ListView tab_lv;

	@ViewInject(R.id.tab_gv)
	private GridView tab_gv;

	@ViewInject(R.id.tab_tv_count)
	private TextView tab_tv_count;

	@ViewInject(R.id.check_box_lyout)
	private CheckBox cBoxLayout;

	private GVAdapter gvAdapter;
	private EntityAdapter lvAdapter;

	private String cid;
	private ArrayList<EntityBean> list;
	private String isLike;
	private int curTab = LIST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);
		
		init();
	}
	
	private void init(){
		
		cBoxLayout.setOnCheckedChangeListener(this);
	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void setLike(View v) {
		Intent intent = new Intent(mContext, TabAct.class);
		intent.putExtra("data", cid);
		intent.putExtra("name", "我喜爱的 " + getIntent().getStringExtra("name"));
		intent.putExtra("add", "like");
		mContext.startActivity(intent);
	}

	@Override
	protected void onSuccess(String result, int where) {
		switch (where) {
		case CATABLIST:
			list = (ArrayList<EntityBean>) JSON.parseArray(result,
					EntityBean.class);
			gvAdapter.setList(list);
			lvAdapter.setList(list);
			break;
		case STAT:
			JSONObject root;
			try {
				root = new JSONObject(result);
				if (isLike == null) {
					tab_tv_count
							.setText(root.getString("entity_count") + "件商品");
				} else
					tab_tv_count.setText(root.getString("like_count") + "件商品");
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
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {

	}

	@Override
	protected void setupData() {
		cid = getIntent().getStringExtra("data");
		setGCenter(true, getIntent().getStringExtra("name"));
		isLike = getIntent().getStringExtra("add");
		setGLeft(true, R.drawable.back_selector);
		if (isLike == null) {
			setGRigth(true, R.drawable.filter_ilike);
		}
		list = new ArrayList<EntityBean>();

		gvAdapter = new GVAdapter(context);
		lvAdapter = new EntityAdapter(context);
		tab_lv.setAdapter(lvAdapter);
		tab_gv.setAdapter(gvAdapter);

		tab_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO + list.get(arg2).getEntity_id()
						+ "/", new String[] { "entity_id" },
						new String[] { list.get(arg2).getEntity_id() },
						PROINFO, true);
			}
		});

		tab_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO + list.get(arg2).getEntity_id()
						+ "/", new String[] { "entity_id" },
						new String[] { list.get(arg2).getEntity_id() },
						PROINFO, true);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getCATABLIST(0 + "");
		sendConnection(Constant.CATAB + cid + "/stat/", new String[] {},
				new String[] {}, STAT, false);
	}

	private void getCATABLIST(String off) {
		if (isLike == null) {
			sendConnection(Constant.CATAB + cid + "/entity/", new String[] {
					"count", "offset", "reverse" }, new String[] { "30", off,
					"0" }, CATABLIST, true);
		} else if (EkwingApplication.getInstance().getBean() != null) {
			sendConnection(Constant.CATAB
					+ cid
					+ "/user/"
					+ EkwingApplication.getInstance().getBean().getUser()
							.getUser_id() + "/like/", new String[] { "count",
					"offset", "reverse" }, new String[] { "30", off, "0" },
					CATABLIST, true);
		} else {
			startActivity(new Intent(mContext, LoginAct.class));
			finish();
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SharePrenceUtil.setTAG(mContext, curTab);

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if(arg1){
			curTab = LIST;
			tab_gv.setVisibility(View.GONE);
			tab_lv.setVisibility(View.VISIBLE);
		}else{
			curTab = GRID;
			tab_gv.setVisibility(View.VISIBLE);
			tab_lv.setVisibility(View.GONE);
		}
	}

}
