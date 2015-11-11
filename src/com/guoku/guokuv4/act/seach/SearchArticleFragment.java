/**

 */
package com.guoku.guokuv4.act.seach;

import com.guoku.R;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月9日 下午4:42:58 
 * 搜索图文页
 */
public class SearchArticleFragment extends BaseFrament{
	
	public static final int SEARCH = 1001;
	private static final int SEARCHADD = 1002;
	
	@ViewInject(R.id.pull_listview)
	PullToRefreshListView listView;

	int page = 0;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		listView.setMode(Mode.PULL_FROM_END);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
			}
		});
		
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				
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
		
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		getData(SearchAct.searchStr, true);
	}

	public void getData(String str, boolean isShowDialog) {
		sendConnection(Constant.SEARCH + "articles/search/", new String[] {"q", "page", "size"},
				new String[] { str, String.valueOf(page), "20"}, page == 0 ? SEARCH : SEARCHADD, isShowDialog);
	}

}
