/**

 */
package com.guoku.guokuv4.homepage;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.config.Constant;
import com.guoku.R;
import com.guoku.guokuv4.act.WebAct;
import com.guoku.guokuv4.adapter.ArticleAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.ArticlesList;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-14 下午6:23:44 
 * 图文列表
 */
public class ArticleFragment extends BaseFrament implements OnItemClickListener{
	
	private static final int TAG_ARTICLE = 1003;// 文章
	
	@ViewInject(R.id.list_article)
	PullToRefreshListView listViewArtivle;
	
	ArticleAdapter articleAdapter;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		articleAdapter = new ArticleAdapter(getActivity());
		listViewArtivle.setAdapter(articleAdapter);
		listViewArtivle.setOnItemClickListener(this);
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_article;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case TAG_ARTICLE:
			setResult(result);
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
		sendConnection(Constant.ARTICLES, new String[] {"page"}, new String[] {"7"}, TAG_ARTICLE,
				false);
	}
	
	private void setResult(String result){
		ArrayList<ArticlesList> listbean = (ArrayList<ArticlesList>) JSON.parseArray(result, ArticlesList.class);
		articleAdapter.setList(listbean);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context,
				WebAct.class);
		intent.putExtra("data", Constant.URL_ARTICLES + articleAdapter.getList().get(arg2).getUrl());
		intent.putExtra("name", "  ");
		intent.putExtra("type", "banner");
		startActivity(intent);
	}
}
