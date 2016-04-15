package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.FansAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.bean.AuthorizedUserListBean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.eventbus.FollowEB;
import com.guoku.guokuv4.gragment.PersonalFragment;
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
import de.greenrobot.event.EventBus;

public class FansAct extends NetWorkActivity implements OnClickListener {

	private static final int FANS = 10;
	private static final int FOLLOW1 = 13;
	private static final int FOLLOW0 = 14;
	private static final int LIST_DOWN = 15;
	private static final int LIST_UP = 16;

	@ViewInject(R.id.fans_lv)
	private PullToRefreshListView lv;
	private FansAdapter adapter;
	private String url;
	private UserBean bean;
	private ImageView view;
	private boolean isAuthonUser; // true=鉴权推荐用户 false＝普通用户
	int page = 1;
	int count = 30;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		setContentView(R.layout.fans);
		setGCenter(true, getIntent().getStringExtra("name"));
		setGLeft(true, R.drawable.back_selector);
		lv.setRefreshing();
	}

	@Override
	protected void onSuccess(String result, int where) {
		lv.onRefreshComplete();
		switch (where) {
		case FANS:
			if (isAuthonUser) {
				AuthorizedUserListBean bean = JSON.parseObject(result, AuthorizedUserListBean.class);
				adapter.setList(bean.getAuthorized_user());
			} else {
				adapter.setList((ArrayList<UserBean>) JSON.parseArray(result, UserBean.class));
			}
			break;
		case FOLLOW0:
			if (bean == null) {
				return;
			}
			bean.setRelation("0");
			adapter.setStatus(view, bean);
			FollowEB fEb = new FollowEB();
			fEb.setFollow(false);
			EventBus.getDefault().post(fEb);
			break;
		case FOLLOW1:
			if (bean == null) {
				return;
			}
			JSONObject root;
			try {
				root = new JSONObject(result);
				Logger.i(TAG, root.getString("relation"));
				if (root.getString("relation").equals("1")) {
					bean.setRelation("1");
				} else {
					bean.setRelation("3");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			adapter.setStatus(view, bean);

			FollowEB fEb2 = new FollowEB();
			fEb2.setFollow(true);
			EventBus.getDefault().post(fEb2);
			break;
		case LIST_DOWN:
			if (isAuthonUser) {
				AuthorizedUserListBean bean = JSON.parseObject(result, AuthorizedUserListBean.class);
				adapter.setList(bean.getAuthorized_user());
			} else {
				adapter.setList((ArrayList<UserBean>) JSON.parseArray(result, UserBean.class));
			}
			break;
		case LIST_UP:
			if (isAuthonUser) {
				AuthorizedUserListBean bean = JSON.parseObject(result, AuthorizedUserListBean.class);
				adapter.addListsLast(bean.getAuthorized_user());
			} else {
				ArrayList<UserBean> userBean = (ArrayList<UserBean>) JSON.parseArray(result, UserBean.class);
				adapter.addListsLast(userBean);
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

	private void getFans(int netType, boolean isShow, int off) {
		if (isAuthonUser) {
			sendConnection(url, new String[] { "count", "page" }, new String[] { count + "", page + "" }, netType,
					isShow);
		} else {
			sendConnection(url, new String[] { "count", "offset" }, new String[] { count + "", off + "" }, netType,
					isShow);
		}
	}

	@Override
	protected void setupData() {
		url = getIntent().getStringExtra("url");
		isAuthonUser = getIntent().getBooleanExtra(PersonalFragment.class.getName(), false);
		adapter = new FansAdapter(context, this);
		lv.setAdapter(adapter);
		lv.setMode(Mode.BOTH);

		lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				page = 1;
				getFans(LIST_DOWN, false, 0);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				if (adapter.getCount() > 0) {
					page++;
					getFans(LIST_UP, false, 30 * (page - 1));
				}
			}
		});

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (adapter.getCount() > 0) {
					Intent intent = new Intent(mContext, UserBaseFrament.class);
					intent.putExtra("data", adapter.getItem(arg2 - 1));
					startActivity(intent);
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fans_item_iv_status:
			view = (ImageView) v;
			bean = (UserBean) v.getTag();
			if (bean.getRelation().equals("0") || bean.getRelation().equals("2")) {
				sendConnectionPOST(Constant.FOLLOW + bean.getUser_id() + "/follow/1/", new String[] {}, new String[] {},
						FOLLOW1, true);
			} else {
				sendConnectionPOST(Constant.FOLLOW + bean.getUser_id() + "/follow/0/", new String[] {}, new String[] {},
						FOLLOW0, true);
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(FollowEB fEb) {

	}

}
