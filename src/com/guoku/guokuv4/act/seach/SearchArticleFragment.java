/**

 */
package com.guoku.guokuv4.act.seach;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.adapter.SearchArticleFragmentAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.ArticlesSearch;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月9日 下午4:42:58 搜索图文页
 */
public class SearchArticleFragment extends BaseFrament {

	public static final int SEARCH = 1001;
	private static final int SEARCHADD = 1002;

	@ViewInject(R.id.pull_listview)
	PullToRefreshListView listView;

	@ViewInject(R.id.layout_search_empty)
	View viewEmpty;

	SearchArticleFragmentAdapter adapter;

	int page = 1;

	@Override
	protected void init() {
		// TODO Auto-generated method stub

		adapter = new SearchArticleFragmentAdapter(context);
		listView.setAdapter(adapter);

		listView.setMode(Mode.PULL_FROM_END);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

				arg2 -= 1;

				Bundle bundle = new Bundle();
				Sharebean sharebean = new Sharebean();
				sharebean.setTitle(adapter.getList().get(arg2).getTitle());
				sharebean.setContext(adapter.getList().get(arg2).getContent().substring(0, 50));
				sharebean.setAricleUrl(adapter.getList().get(arg2).getUrl());
				sharebean.setImgUrl(adapter.getList().get(arg2).getCover());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);

				openActivity(WebShareAct.class, bundle);
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				page++;
				getData(SearchAct.searchStr, false);
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
				listView.setAdapter(adapter);// 为了listview在重新搜索的时候回到初始位置
				ArticlesSearch aSearch = JSON.parseObject(result, ArticlesSearch.class);
				adapter.setList(aSearch.getArticles());
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (adapter.getCount() == 0) {
				showEmpty(viewEmpty, listView);
			}
			break;
		case SEARCHADD:
			try {
				ArticlesSearch aSearch = JSON.parseObject(result, ArticlesSearch.class);
				adapter.addListsLast(aSearch.getArticles());
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		getData(SearchAct.searchStr, false);
	}

	public void getData(String str, boolean isShowDialog) {
		sendConnection(Constant.SEARCH + "articles/search/", new String[] { "q", "page", "size" },
				new String[] { str, String.valueOf(page), "20" }, page == 1 ? SEARCH : SEARCHADD, isShowDialog);
	}

}
