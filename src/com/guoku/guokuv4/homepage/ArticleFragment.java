/**

 */
package com.guoku.guokuv4.homepage;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.adapter.ArticleAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.ArticlesList;
import com.guoku.guokuv4.bean.LikesBean;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.eventbus.ZanEB;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import de.greenrobot.event.EventBus;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-14 下午6:23:44 图文列表
 */
public class ArticleFragment extends BaseFrament implements OnItemClickListener {

	private static final int TAG_ARTICLE = 1003;// 文章
	private static final int TAG_ARTICLE_ADD = 1004;// 上拉更多
	
	@ViewInject(R.id.list_article)
	public PullToRefreshListView listViewArtivle;
	
	@ViewInject(R.id.tv_check_net)
	ImageView tvCheckNet;

	ArticleAdapter articleAdapter;

	int page = 1;
	
	boolean isLoad;
	
	int tempItem;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this);
		}
		
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
						sentRequest(false);
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
		if(listViewArtivle.getVisibility() == View.GONE){
			tvCheckNet.setVisibility(View.GONE);
			listViewArtivle.setVisibility(View.VISIBLE);
		}
		listViewArtivle.onRefreshComplete();
		switch (where) {
		case TAG_ARTICLE:
			isLoad = true;
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
		GuokuUtil.closeListViewHeader(listViewArtivle);
		switch (where) {
		case TAG_ARTICLE:
			if(articleAdapter.getList() == null){
				tvCheckNet.setVisibility(View.VISIBLE);
				listViewArtivle.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
//		sentRequest(true);
	}

	public void sentRequest(boolean isShow) {

		sendConnection(Constant.ARTICLES, new String[] { "page", "size" },
				new String[] { String.valueOf(page), "20"}, TAG_ARTICLE, isShow);
	}

	private void sentRequestAdd() {

		sendConnection(Constant.ARTICLES, new String[] { "page", "size" },
				new String[] { String.valueOf(page), "20"}, TAG_ARTICLE_ADD, false);
	}

	private void setResult(String result, int type) {
		
		try {
			ArrayList<ArticlesList> listbean = (ArrayList<ArticlesList>) JSON
					.parseArray(result, ArticlesList.class);
			if (type == TAG_ARTICLE) {
				articleAdapter.setList(listbean);
			}
			if (type == TAG_ARTICLE_ADD) {
				articleAdapter.addListsLast(listbean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			ToastUtil.show(context, R.string.error_data_json);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		tempItem = arg2 -= 1;
		Bundle bundle = new Bundle();
		Sharebean sharebean = new Sharebean();
		sharebean.setTitle(articleAdapter.getList().get(tempItem).getTitle());
		sharebean.setContext(articleAdapter.getList().get(tempItem).getContent()
				.substring(0, 50));
		sharebean.setAricleUrl(articleAdapter.getList().get(tempItem).getUrl());
		sharebean.setImgUrl(articleAdapter.getList().get(tempItem).getCover());
		sharebean.setAricleId(String.valueOf(articleAdapter.getList().get(tempItem).getArticle_id()));
		sharebean.setIs_dig(articleAdapter.getList().get(arg2).isIs_dig());
		bundle.putSerializable(WebShareAct.class.getName(), sharebean);

		openActivity(WebShareAct.class, bundle);
	}
	
	@OnClick(R.id.tv_check_net)
	private void onCheckNetClick(View view){
		sentRequest(true);
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			if(isLoad == false){
				listViewArtivle.setRefreshing();
				sentRequest(false);
			}
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().unregister(this);
		}
	}
	
	public void onEventMainThread(ZanEB zEb) {
		if(articleAdapter.getList() != null){
			if(zEb.isZan()){
				articleAdapter.getList().get(tempItem).setIs_dig(true);
			}else{
				articleAdapter.getList().get(tempItem).setIs_dig(false);
			}
		}
	}
}
