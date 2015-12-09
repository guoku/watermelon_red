package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.FansAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class LikesAct extends NetWorkActivity implements OnClickListener {

	private static final int FOLLOW1 = 13;
	private static final int FOLLOW0 = 14;
	private static final int Likes = 10;

	@ViewInject(R.id.fans_lv)
	private PullToRefreshListView lv;
	private FansAdapter adapter;
	private UserBean bean;
	private String eid;
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fans);
		setGCenter(true, getIntent().getStringExtra("name"));
		setGLeft(true, R.drawable.back_selector);

		getFans();
	}

	@Override
	protected void onSuccess(String result, int where) {
		lv.onRefreshComplete();
		switch (where) {
		case Likes:
			JSONObject data;
			try {
				data = new JSONObject(result);
				adapter.addLists((ArrayList<UserBean>) JSON.parseArray(
						data.getString("data"), UserBean.class));
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;
		case FOLLOW0:
			if (bean == null) {
				return;
			}
			bean.setRelation("0");
			adapter.setStatus(imView, bean);
			break;
		case FOLLOW1:
			if (bean == null) {
				return;
			}

			ToastUtil.show(context, "关注成功");
			JSONObject root;
			try {
				root = new JSONObject(result);
				Logger.i(TAG, root.getString("relation"));
				if (root.getString("relation").equals("1")) {
					bean.setRelation("1");
				} else {
					bean.setRelation("3");
				}
				adapter.setStatus(imView, bean);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		lv.onRefreshComplete();
		switch (where) {
		case FOLLOW0:
			ToastUtil.show(context, "取消关注失败");
			break;
		case FOLLOW1:
			ToastUtil.show(context, "关注失败");
			break;
		default:
			break;
		}
	}

	private void getFans() {
		sendConnection(Constant.PROINFO + eid + "/liker/",
				new String[] { "page" }, new String[] { page + "" }, Likes,
				false);
	}

	@Override
	protected void setupData() {
		eid = getIntent().getStringExtra("data");
		adapter = new FansAdapter(context, this);
		lv.setAdapter(adapter);
		lv.setMode(Mode.PULL_FROM_END);

		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (adapter.getCount() > 0) {
					page ++;
					getFans();
				} else {
				}
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (adapter.getCount() > 0) {
					Intent intent = new Intent(mContext, UserBaseFrament.class);
					intent.putExtra("data", adapter.getItem(arg2 - 1));
					startActivity(intent);
				}
			}
		});

	}

	private ImageView imView;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fans_item_iv_status:
			imView = (ImageView) v;
			bean = (UserBean) v.getTag();
			if (bean.getRelation().equals("0")
					|| bean.getRelation().equals("2")) {
				sendConnectionPOST(Constant.FOLLOW + bean.getUser_id()
						+ "/follow/1/", new String[] {}, new String[] {},
						FOLLOW1, true);
			} else {
				sendConnectionPOST(Constant.FOLLOW + bean.getUser_id()
						+ "/follow/0/", new String[] {}, new String[] {},
						FOLLOW0, true);
			}
			break;
		default:
			break;
		}
	}

}
