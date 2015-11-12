/**

 */
package com.guoku.guokuv4.act.seach;

import java.util.ArrayList;

import com.guoku.R;
import com.guoku.guokuv4.adapter.SeachCommodityTypeAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月9日 下午4:44:03 搜索标签页
 */
public class SearchTagFragment extends BaseFrament {
	
	private static final int TAB = 1001;

	@ViewInject(R.id.gd_commodity_type)
	ScrollViewWithGridView gridView;
	
	@ViewInject(R.id.layout_search_empty)
	View viewEmpty;

	ArrayList<Tab2Bean> listTab, curList;

	private SeachCommodityTypeAdapter seachCommodityTypeAdapter;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		seachCommodityTypeAdapter = new SeachCommodityTypeAdapter(context);
		gridView.setAdapter(seachCommodityTypeAdapter);
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_search_tag;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case TAB:
			try {
				SharePrenceUtil.setTabList(context, result);
				listTab = ParseUtil.getTab2List(context);
				curList = new ArrayList<Tab2Bean>();
				getData();
			} catch (Exception e) {
				// TODO: handle exception
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
		sendConnection(Constant.TAB, new String[] {}, new String[] {}, TAB,
				false);
	}

	public void getData() {

		curList.clear();
		if (listTab.size() > 0) {
			for (Tab2Bean bean : listTab) {
				if (bean.getCategory_title().contains(SearchAct.searchStr)) {
					curList.add(bean);
				}
			}
			seachCommodityTypeAdapter.setList(curList);
			hideEmpty(viewEmpty, gridView);
			if (seachCommodityTypeAdapter.getCount() == 0) {
				showEmpty(viewEmpty, gridView);
			}
		}

	}

}
