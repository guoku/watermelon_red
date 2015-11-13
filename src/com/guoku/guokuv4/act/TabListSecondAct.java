/**

 */
package com.guoku.guokuv4.act;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.adapter.SeachCommodityTypeAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.TagTwo;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-24 下午3:02:42 二级分类商品结果
 */
public class TabListSecondAct extends NetWorkActivity implements
		OnCheckedChangeListener{

	private final int TAG_CATABLIST = 1001;// 二级品类商品
	private final int TAG_CATABLIST_UP = 10011;// 二级品类商品上拉
	private final int TAG_PROINFO = 1002;// 获取商品信息
	

	@ViewInject(R.id.sv)
	private PullToRefreshScrollView sv;//gridview上拉刷新
	
	@ViewInject(R.id.pull_listview)
	private PullToRefreshListView tab_lv;

	@ViewInject(R.id.tab_gv)
	private ScrollViewWithGridView tab_gv;

	@ViewInject(R.id.check_box_like_time)
	CheckBox cbLikeTime;// tab上的喜欢、时间切换按钮

	@ViewInject(R.id.check_box_show)
	CheckBox cbShow;// tab上的显示方式按钮

	private GridViewAdapter gvAdapter;
	private EntityAdapter lvAdapter;

	TagTwo tagTwo;// 从品类分类过来的
	Tab2Bean tab2Bean;// 从品类搜索页过来的

	String title;// 标题

	String id;// 分类id

	private String defWgat = TIME;// 默认按时间排序

	private int page = 0;

	private ArrayList<EntityBean> listTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_list_second);

		init();
		sendData(TAG_CATABLIST, true);
	}

	private void init() {

		gvAdapter = new GridViewAdapter(mContext, 3);
		tab_gv.setAdapter(gvAdapter);
		tab_gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				sendConnection(Constant.PROINFO + listTag.get(arg2).getEntity_id()
						+ "/", new String[] { "entity_id" }, new String[] { listTag
						.get(arg2).getEntity_id() }, TAG_PROINFO, true);
			}
		});

		lvAdapter = new EntityAdapter(mContext);
		tab_lv.setAdapter(lvAdapter);
		tab_lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				arg2 -=1;
				sendConnection(Constant.PROINFO + listTag.get(arg2).getEntity_id()
						+ "/", new String[] { "entity_id" }, new String[] { listTag
						.get(arg2).getEntity_id() }, TAG_PROINFO, true);
			}
		});
		tab_lv.setPullToRefreshOverScrollEnabled(false);
		tab_lv.setScrollingWhileRefreshingEnabled(false);
		tab_lv.setMode(Mode.BOTH);
		tab_lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page = 0;
				sendData(TAG_CATABLIST, false);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if(listTag != null){
					if (listTag.size() > 0) {
						page += 30;
						sendData(TAG_CATABLIST_UP, false);
					} else {
						// Toast
					}
				}
			}
		});
		
		sv.setMode(Mode.BOTH);
		sv.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				page = 0;
				sendData(TAG_CATABLIST, false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				page += 30;
				sendData(TAG_CATABLIST_UP, false);
			}
		});
		
		

		cbLikeTime.setOnCheckedChangeListener(this);
		cbShow.setOnCheckedChangeListener(this);

	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		tab_lv.onRefreshComplete();
		sv.onRefreshComplete();
		switch (where) {
		case TAG_CATABLIST:
			listTag = (ArrayList<EntityBean>) JSON.parseArray(result,
					EntityBean.class);
			gvAdapter.setList(listTag);
			lvAdapter.setList(listTag);
			break;
		case TAG_CATABLIST_UP:
			try {
				ArrayList<EntityBean> lists = (ArrayList<EntityBean>) JSON.parseArray(result,
						EntityBean.class);
				listTag.addAll(lists);
				gvAdapter.addListsLast(lists);
				lvAdapter.addListsLast(lists);
			} catch (Exception e) {
				// TODO: handle exception
				ToastUtil.show(this, R.string.error_data_json);
			}
			break;
		case TAG_PROINFO:
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
		// TODO Auto-generated method stub
		tab_lv.onRefreshComplete();
		sv.onRefreshComplete();
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		tagTwo = (TagTwo) getIntent().getExtras().getSerializable(
				TabAct.SECOND_ACT_ONTENT);
		tab2Bean = (Tab2Bean) getIntent().getExtras().getSerializable(
				SeachCommodityTypeAdapter.SEACH_TAG);

		if (tagTwo != null) {
			id = String.valueOf(tagTwo.getCategory_id());
			title = tagTwo.getCategory_title();
		}
		if (tab2Bean != null) {
			id = tab2Bean.getCategory_id();
			title = tab2Bean.getCategory_title();
		}
		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);
		getTitleLayoutSeach().setVisibility(View.VISIBLE);
	}

	private void sendData(int tag, boolean isLoding) {

		sendConnection(Constant.CATEGORY + id + "/entity/", new String[] {
				"offset", "count", "reverse", "sort" }, new String[] { String.valueOf(page),
				"30", "0", defWgat }, tag, isLoding);

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub

		if (arg0 == cbLikeTime) {
			page = 0;
			if (arg1) {
				defWgat = LIKE;
				sendData(TAG_CATABLIST, true);
			} else {
				defWgat = TIME;
				sendData(TAG_CATABLIST, true);
			}
		}

		if (arg0 == cbShow) {
			if (arg1) {
				tab_gv.setVisibility(View.VISIBLE);
				tab_lv.setVisibility(View.GONE);
			} else {
				tab_gv.setVisibility(View.GONE);
				tab_lv.setVisibility(View.VISIBLE);
			}
		}
	}

}
