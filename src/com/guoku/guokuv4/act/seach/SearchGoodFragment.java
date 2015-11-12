/**

 */
package com.guoku.guokuv4.act.seach;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.TabAct;
import com.guoku.guokuv4.act.seach.SearchInterface.OnActivityChangeListener;
import com.guoku.guokuv4.act.seach.SearchInterface.OnFragmentChangeListener;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月9日 下午4:40:49 搜索中的商品页
 */
public class SearchGoodFragment extends BaseFrament {

	public static final int SEARCH = 1001;
	private static final int SEARCHADD = 1002;
	private static final int PROINFO = 1003;

	@ViewInject(R.id.pull_listview)
	PullToRefreshListView listView;
	
	@ViewInject(R.id.layout_search_empty)
	View viewEmpty;

	private EntityAdapter entityAdapter;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
		entityAdapter = new EntityAdapter(context);
		listView.setAdapter(entityAdapter);

		listView.setMode(Mode.PULL_FROM_END);
		listView.setAdapter(entityAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				sendConnection(Constant.PROINFO + entityAdapter.getItem(arg2 - 1).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { entityAdapter.getItem(arg2 - 1).getEntity_id() },
						PROINFO, true);
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getData(SearchAct.searchStr, false, entityAdapter.getCount());
			}
		});
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_search_good;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
		switch (where) {
		case SEARCH:
			try {
				hideEmpty(viewEmpty, listView);
				listView.setAdapter(entityAdapter);//为了listview在重新搜索的时候回到初始位置
				JSONObject root = new JSONObject(result);
				entityAdapter.setList(
						(ArrayList<EntityBean>) JSON.parseArray(root.getString("entity_list"), EntityBean.class));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (entityAdapter.getCount() == 0) {
				showEmpty(viewEmpty, listView);
			}
			break;
		case SEARCHADD:
			JSONObject root;
			try {
				root = new JSONObject(result);
				ArrayList<EntityBean> listEntity = ((ArrayList<EntityBean>) JSON
						.parseArray(root.getString("entity_list"), EntityBean.class));
				entityAdapter.addListsLast(listEntity);
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
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		getData(SearchAct.searchStr, false, 0);
	}

	public void getData(String str, boolean isShowDialog, int off) {
		sendConnection(Constant.SEARCH + "entity/search/", new String[] { "count", "offset", "q", "type" },
				new String[] { "30", off + "", str, "all" }, off == 0 ? SEARCH : SEARCHADD, isShowDialog);
	}

}
