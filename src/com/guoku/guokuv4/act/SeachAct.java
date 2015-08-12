package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.customview.ScrollViewWithGridView;
import com.ekwing.students.utils.SharePrenceUtil;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.adapter.FansAdapter;
import com.guoku.guokuv4.adapter.SeachCommodityTypeAdapter;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.BroadUtil;
import com.guoku.guokuv4.utils.ScreenSizeUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SeachAct extends NetWorkActivity implements OnClickListener {
	private static final int SEARCH = 10;
	private static final int SEARCHADD = 15;
	private static final int TAB = 11;
	private static final int PROINFO = 12;
	private static final int FOLLOW1 = 13;
	private static final int FOLLOW0 = 14;

	@ViewInject(R.id.sou_tv_tab2)
	private TextView sou_tv_tab2;

	@ViewInject(R.id.sou_tv_tab1)
	private TextView sou_tv_tab1;

	@ViewInject(R.id.sou_tv_tab3)
	private TextView sou_tv_tab3;

	@ViewInject(R.id.sou_ed_text)
	private EditText ed_text;

	@ViewInject(R.id.sou_iv_tab1)
	private ImageView sou_iv_tab1;

	@ViewInject(R.id.sou_iv_tab2)
	private ImageView sou_iv_tab2;

	@ViewInject(R.id.sou_iv_tab3)
	private ImageView sou_iv_tab3;

	@ViewInject(R.id.sou_lv_1)
	private PullToRefreshListView lv;

	@ViewInject(R.id.gd_commodity_type)
	private ScrollViewWithGridView sGridView;// 品类

	@ViewInject(R.id.view_def_img)
	private ImageView view_def;// 搜索内容为null时默认显示

	@ViewInject(R.id.layout_content)
	private View view_content;

	private EntityAdapter entityAdapter;
	private FansAdapter fansAdapter;
	private SeachCommodityTypeAdapter seachCommodityTypeAdapter;

	private String curTab = "entity/search/";

	private ArrayList<EntityBean> listEntity;
	private ArrayList<UserBean> listUser;
	private ArrayList<Tab2Bean> listTab, curList;

	UserBean beanFowllow;// 刷新关注按钮相关
	ImageView imgFowllow;
	
	private String seachConten;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sou);
		init();
	}

	private void init() {

		ed_text.addTextChangedListener(watcher);
		view_def.setLayoutParams(new LinearLayout.LayoutParams(ScreenSizeUtil
				.getScreenWidth(this) / 2, ScreenSizeUtil.getScreenHeight(this) / 2));
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		isShowSeachResult(true);
		lv.onRefreshComplete();
		ed_text.setEnabled(true);
		switch (where) {
		case SEARCH:
			Logger.i(TAG, "set");
			if (result.contains("stat") && result.contains("entity_list")) {
				JSONObject root;
				try {
					root = new JSONObject(result);
					// sou_tv_tab1 .setText("商品 "
					// + root.getJSONObject("stat").getString(
					// "all_count"));
					entityAdapter.setList((ArrayList<EntityBean>) JSON
							.parseArray(root.getString("entity_list"),
									EntityBean.class));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (entityAdapter.getCount() == 0) {
					ToastUtil.show(context, "没有相关结果");
				}
			} else if (result.contains("user_id")
					&& result.contains("avatar_large")) {
				ArrayList<UserBean> list = (ArrayList<UserBean>) JSON
						.parseArray(result, UserBean.class);
				ArrayList<UserBean> bufList = new ArrayList<UserBean>();
				if (list != null && list.size() > 0) {

					for (UserBean bean : list) {
						if (!bean.getIs_active().equals("0")) {
							bufList.add(bean);
						}

					}
					fansAdapter.setList(bufList);
				}

				if (fansAdapter.getCount() == 0) {
					ToastUtil.show(context, "没有相关结果");
				}

			} else {
				fansAdapter.clear();
				entityAdapter.clear();
			}
			break;
		case SEARCHADD:
			Logger.i(TAG, "add");
			if (result.contains("stat") && result.contains("entity_list")) {
				JSONObject root;
				try {
					root = new JSONObject(result);
					listEntity.addAll((ArrayList<EntityBean>) JSON.parseArray(
							root.getString("entity_list"), EntityBean.class));
					entityAdapter.setList(listEntity);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				listUser.addAll((ArrayList<UserBean>) JSON.parseArray(result,
						UserBean.class));
				fansAdapter.setList(listUser);
			}
			break;
		case TAB:
			SharePrenceUtil.setTabList(context, result);
			listTab = ParseUtil.getTab2List(mContext);
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case FOLLOW0:
			ToastUtil.show(context, "取消关注成功");
			fansAdapter.setStatus(imgFowllow, beanFowllow);
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY,
					Constant.INTENT_ACTION_VALUE_FOLLOW);
			break;
		case FOLLOW1:
			ToastUtil.show(context, "关注成功");
			fansAdapter.setStatus(imgFowllow, beanFowllow);
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY,
					Constant.INTENT_ACTION_VALUE_FOLLOW);
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
		case FOLLOW1:
			ToastUtil.show(context, "关注失败");
		default:
			break;
		}
	}

	@Override
	protected void setupData() {
		fansAdapter = new FansAdapter(context, this);
		seachCommodityTypeAdapter = new SeachCommodityTypeAdapter(mContext);
		entityAdapter = new EntityAdapter(context);

		listEntity = new ArrayList<EntityBean>();
		listUser = new ArrayList<UserBean>();
		listTab = ParseUtil.getTab2List(mContext);
		curList = new ArrayList<Tab2Bean>();

		lv.setMode(Mode.PULL_FROM_END);
		lv.setAdapter(entityAdapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (curTab.equals("entity/search/")) {
					sendConnection(
							Constant.PROINFO
									+ entityAdapter.getItem(arg2 - 1)
											.getEntity_id() + "/",
							new String[] { "entity_id" },
							new String[] { entityAdapter.getItem(arg2 - 1)
									.getEntity_id() }, PROINFO, true);
				} else if (curTab.equals("tab")) {
					Intent intent = new Intent(mContext, TabAct.class);
					intent.putExtra("data", curList.get(arg2 - 1)
							.getCategory_id());
					intent.putExtra("name", curList.get(arg2 - 1)
							.getCategory_title());
					mContext.startActivity(intent);
				} else if (curTab.equals("user/search/")) {
					Intent intent = new Intent(mContext, UserAct.class);
					intent.putExtra("data", fansAdapter.getItem(arg2 - 1));
					startActivity(intent);
				}
			}
		});

		ed_text.setOnKeyListener(new OnKeyListener() {// 输入完后按键盘上的搜索键【回车键改为了搜索键】

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {// 修改回车键功能
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(SeachAct.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					search(0, seachConten);
				}
				return false;
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
				if (curTab.equals("entity/search/")) {
					search(entityAdapter.getCount(), seachConten);
				} else if (curTab.equals("user/search/")) {
					search(fansAdapter.getCount(), seachConten);
				} else {
					lv.onRefreshComplete();
				}
			}
		});

	}

	private void search(int off, String content) {

		if (StringUtils.isEmpty(content)) {
			return;
		}

		if (curTab.equals("tab")) {
			Tab2(null);
		} else
			sendConnection(Constant.SEARCH + curTab, new String[] { "count",
					"offset", "q", "type" }, new String[] { "30", off + "",
					content, "all" }, off == 0 ? SEARCH : SEARCHADD, false);
	}

	@OnClick(R.id.sou_ll_tab1)
	public void Tab1(View v) {

		isShowCT(false);
		sou_iv_tab1.setVisibility(View.VISIBLE);
		sou_iv_tab2.setVisibility(View.INVISIBLE);
		sou_iv_tab3.setVisibility(View.INVISIBLE);
		// // sou_gv.setVisibility(View.VISIBLE);
		// sou_lv_1.setVisibility(View.GONE);
		// sou_lv_2.setVisibility(View.GONE);
		curTab = "entity/search/";

		lv.setAdapter(entityAdapter);
		if (listEntity.size() > 0) {
			entityAdapter.setList(listEntity);
		} else {
			search(0, seachConten);
		}

		sou_tv_tab1.setTextColor(Color.rgb(65, 66, 67));
		sou_tv_tab2.setTextColor(Color.rgb(157, 158, 159));
		sou_tv_tab3.setTextColor(Color.rgb(157, 158, 159));

	}

	@OnClick(R.id.sou_ll_tab2)
	public void Tab2(View v) {

		isShowCT(true);
		sou_iv_tab1.setVisibility(View.INVISIBLE);
		sou_iv_tab2.setVisibility(View.VISIBLE);
		sou_iv_tab3.setVisibility(View.INVISIBLE);
		curTab = "tab";

		Logger.i(TAG, "listtab" + listTab.size());
		Logger.i(TAG, "listtab" + listTab.toString());

		sGridView.setAdapter(seachCommodityTypeAdapter);
		curList.clear();
		if (listTab.size() > 0) {
			if(!StringUtils.isEmpty(seachConten)){
				for (Tab2Bean bean : listTab) {
					if (bean.getCategory_title().contains(
							seachConten)) {
						curList.add(bean);
					}
				}
				seachCommodityTypeAdapter.setList(curList);
				if (seachCommodityTypeAdapter.getCount() == 0) {
					ToastUtil.show(context, "没有相关结果");
				}
			}
		} else {
			sendConnection(Constant.TAB, new String[] {}, new String[] {}, TAB,
					false);
		}
		sendConnection(Constant.TAB, new String[] {}, new String[] {}, TAB,
				false);

		sou_tv_tab2.setTextColor(Color.rgb(65, 66, 67));
		sou_tv_tab1.setTextColor(Color.rgb(157, 158, 159));
		sou_tv_tab3.setTextColor(Color.rgb(157, 158, 159));

	}

	@OnClick(R.id.sou_tv_btn)
	public void concel(View v) {
		finish();
	}

	@OnClick(R.id.sou_ll_tab3)
	public void Tab3(View v) {

		isShowCT(false);
		sou_iv_tab1.setVisibility(View.INVISIBLE);
		sou_iv_tab2.setVisibility(View.INVISIBLE);
		sou_iv_tab3.setVisibility(View.VISIBLE);
		curTab = "user/search/";

		lv.setAdapter(fansAdapter);

		if (listUser.size() > 0) {
			fansAdapter.setList(listUser);
		} else {
			search(0, seachConten);
		}

		sou_tv_tab3.setTextColor(Color.rgb(65, 66, 67));
		sou_tv_tab2.setTextColor(Color.rgb(157, 158, 159));
		sou_tv_tab1.setTextColor(Color.rgb(157, 158, 159));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fans_item_iv_status:
			beanFowllow = (UserBean) v.getTag();
			imgFowllow = (ImageView) v;

			if (beanFowllow.getRelation().equals("0")
					|| beanFowllow.getRelation().equals("2")) {
				sendConnectionPOST(Constant.FOLLOW + beanFowllow.getUser_id()
						+ "/follow/1/", new String[] {}, new String[] {},
						FOLLOW1, false);
				beanFowllow.setRelation("1");
			} else {
				sendConnectionPOST(Constant.FOLLOW + beanFowllow.getUser_id()
						+ "/follow/0/", new String[] {}, new String[] {},
						FOLLOW0, false);
				beanFowllow.setRelation("0");
			}
			// fansAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	// 品类显隐
	private void isShowCT(boolean isShow) {
		if (isShow) {
			sGridView.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
		} else {
			sGridView.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}

	}

	// 搜索结果显隐
	private void isShowSeachResult(boolean isShow) {

		if (isShow) {
			view_content.setVisibility(View.VISIBLE);
			view_def.setVisibility(View.GONE);
		} else {
			view_content.setVisibility(View.GONE);
			view_def.setVisibility(View.VISIBLE);
		}
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
			seachConten = s.toString();
			if (s.length() > 0) {
				search(0, seachConten);
			}
		}
	};

}
