/**

 */
package com.guoku.guokuv4.act.seach;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月9日 下午4:40:49 搜索中的商品页
 */
public class SearchGoodFragment extends BaseFrament {

	private static final int SEARCH = 1001;
	private static final int SEARCHADD = 1002;

	@ViewInject(R.id.pull_listview)
	PullToRefreshListView listView;

	private EntityAdapter entityAdapter;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		entityAdapter = new EntityAdapter(context);
		listView.setAdapter(entityAdapter);
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_search_good;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case SEARCH:
			if (result.contains("stat") && result.contains("entity_list")) {
				JSONObject root;
				try {
					root = new JSONObject(result);
					entityAdapter.setList((ArrayList<EntityBean>) JSON
							.parseArray(root.getString("entity_list"),
									EntityBean.class));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (entityAdapter.getCount() == 0) {
					ToastUtil.show(context, "没有相关结果");
				}
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		sendConnection(Constant.SEARCH + "entity/search/", new String[] { "count", "offset", "q", "type" },
				new String[] { "30", 0 + "", "tab", "all" }, 0 == 0 ? SEARCH : SEARCHADD, false);
	}

}
