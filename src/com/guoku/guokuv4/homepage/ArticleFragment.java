/**

 */
package com.guoku.guokuv4.homepage;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.act.WebAct;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.adapter.ArticleAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.ArticlesList;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-14 下午6:23:44 图文列表
 */
public class ArticleFragment extends BaseFrament implements OnItemClickListener {

	private static final int TAG_ARTICLE = 1003;// 文章
	private static final int TAG_ARTICLE_ADD = 1004;// 上拉更多

	@ViewInject(R.id.list_article)
	PullToRefreshListView listViewArtivle;

	ArticleAdapter articleAdapter;

	int page = 1;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		articleAdapter = new ArticleAdapter(getActivity());

		listViewArtivle.setMode(Mode.BOTH);
		listViewArtivle.setOnItemClickListener(this);
		listViewArtivle.setAdapter(articleAdapter);
		listViewArtivle
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						page = 1;
						sentRequest();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						page++;
						sentRequestAdd();
					}
				});
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_article;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		listViewArtivle.onRefreshComplete();
		switch (where) {
		case TAG_ARTICLE:
			setResult(result, TAG_ARTICLE);
			break;
		case TAG_ARTICLE_ADD:
			setResult(result, TAG_ARTICLE_ADD);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		listViewArtivle.onRefreshComplete();
	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		sentRequest();
	}

	private void sentRequest() {

		sendConnection(Constant.ARTICLES, new String[] { "page" },
				new String[] { String.valueOf(page) }, TAG_ARTICLE, false);
	}

	private void sentRequestAdd() {

		sendConnection(Constant.ARTICLES, new String[] { "page" },
				new String[] { String.valueOf(page) }, TAG_ARTICLE_ADD, false);
	}

	private void setResult(String result, int type) {
		ArrayList<ArticlesList> listbean = (ArrayList<ArticlesList>) JSON
				.parseArray(result, ArticlesList.class);
		if (type == TAG_ARTICLE) {
			articleAdapter.setList(listbean);
		}
		if (type == TAG_ARTICLE_ADD) {
			articleAdapter.addListsLast(listbean);
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		arg2 -= 1;
		Bundle bundle = new Bundle();
		Sharebean sharebean = new Sharebean();
		sharebean.setTitle(articleAdapter.getList().get(arg2).getTitle());
		sharebean.setContext(articleAdapter.getList().get(arg2).getContent()
				.substring(0, 50));
		sharebean.setAricleUrl(articleAdapter.getList().get(arg2).getUrl());
		sharebean.setImgUrl(articleAdapter.getList().get(arg2).getCover());
		bundle.putSerializable(WebShareAct.class.getName(), sharebean);

		openActivity(WebShareAct.class, bundle);

	}
}
