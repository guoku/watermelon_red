/**

 */
package com.guoku.guokuv4.homepage;

import android.widget.Switch;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.config.Constant;
import com.guoku.R;
import com.guoku.guokuv4.adapter.ArticleAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.HomeArticleBean;
import com.guoku.guokuv4.bean.HomePageOneBean;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-14 下午6:23:44 
 * 图文列表
 */
public class ArticleFragment extends BaseFrament{
	
	private static final int TAG_ARTICLE = 1003;// 文章
	
	@ViewInject(R.id.list_article)
	PullToRefreshListView listViewArtivle;
	
	ArticleAdapter articleAdapter;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		articleAdapter = new ArticleAdapter(getActivity());
		listViewArtivle.setAdapter(articleAdapter);
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
		sendConnection(Constant.HOME, new String[] {}, new String[] {}, TAG_ARTICLE,
				false);
	}
	
	private void setResult(String result){
		HomeArticleBean bean = JSON.parseObject(result, HomeArticleBean.class);
		articleAdapter.setList(bean.getArticles());
	}
}
