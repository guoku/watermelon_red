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
	
	boolean isFirst;//是否是一级的更多 true是  false否

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
				sharebean.setIs_dig(adapter.getList().get(arg2).isIs_dig());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);
				sharebean.setAricleId(String.valueOf(adapter.getList().get(arg2 - 1).getArticle_id()));
				openActivity(WebShareAct.class, bundle);
			}
		});

		listView.setPullToRefreshOverScrollEnabled(false);
		listView.setScrollingWhileRefreshingEnabled(false);
		listView.setMode(Mode.BOTH);
		listView.setRefreshing();
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
		isFirst = getIntent().getExtras().getBoolean("IS_FIRST");
		cid = getIntent().getExtras().getString(FirstCategoryAct.class.getName());
		String title;
		if(isFirst){
			title = getIntent().getExtras().getString("TITLE_BAR") + getResources().getString(R.string.tv_article_all);
		}else{
			title = getResources().getString(R.string.tv_article_all2);
		}
		
		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);

		getData(NET_ARTICLE, false);
	}

	private void getData(int netTag, boolean isDialog) {

		if(isFirst){
			sendConnection(Constant.CATEGORY_FIRST + cid + "/articles/", new String[] { "page" }, new String[] { String.valueOf(page) },
					netTag, isDialog);
		}
		else{
			sendConnection(Constant.CATEGORY_FIRST + "sub/" + cid + "/articles/", new String[] { "page", "size" },
					new String[] { String.valueOf(page), "30" }, netTag, isDialog);
		}
	}

}
