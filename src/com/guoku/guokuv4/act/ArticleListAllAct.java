/**

 */
package com.guoku.guokuv4.act;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.ArticlesCategoryAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.ArticlesCategoryFirst;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
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
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年1月29日 下午7:30:36 所有图文list
 */
public class ArticleListAllAct extends NetWorkActivity {

	private final int NET_ARTICLE = 1001;
	private final int NET_ARTICLE_ADD = 1002;

	@ViewInject(R.id.tv_empty)
	TextView tvEmpty;

	@ViewInject(R.id.pull_listview)
	private PullToRefreshListView listView;

	ArticlesCategoryAdapter adapter;

	int page = 1;
	String cid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_user_comment_list);

		init();
	}

	private void init() {

		adapter = new ArticlesCategoryAdapter(mContext);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				Sharebean sharebean = new Sharebean();
				sharebean.setTitle(adapter.getList().get(arg2 - 1).getTitle());
				sharebean.setContext(adapter.getList().get(arg2 - 1).getContent().substring(0, 50));
				sharebean.setAricleUrl(adapter.getList().get(arg2 - 1).getUrl());
				sharebean.setImgUrl(adapter.getList().get(arg2 - 1).getCover());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);

				openActivity(WebShareAct.class, bundle);
			}
		});

		listView.setPullToRefreshOverScrollEnabled(false);
		listView.setScrollingWhileRefreshingEnabled(false);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				page = 1;
				getData(NET_ARTICLE, false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				page += 1;
				getData(NET_ARTICLE_ADD, false);
			}
		});
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
		switch (where) {
		case NET_ARTICLE:
			ArticlesCategoryFirst aFirst = JSON.parseObject(result, ArticlesCategoryFirst.class);
			adapter.setList(aFirst.getArticles());
			break;
		case NET_ARTICLE_ADD:
			ArticlesCategoryFirst aFirstAdd = JSON.parseObject(result, ArticlesCategoryFirst.class);
			adapter.addLists(aFirstAdd.getArticles());
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
	protected void setupData() {
		// TODO Auto-generated method stub

		cid = getIntent().getExtras().getString(FirstCategoryAct.class.getName());
		String title = getIntent().getExtras().getString("TITLE_BAR") + getResources().getString(R.string.tv_article_all);
		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);

		getData(NET_ARTICLE, true);
	}

	private void getData(int netTag, boolean isDialog) {

		sendConnection(Constant.CATEGORY_FIRST + cid + "/articles/", new String[] { "page" }, new String[] { String.valueOf(page) },
				netTag, isDialog);
	}

}
