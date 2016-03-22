/**

 */
package com.guoku.guokuv4.act;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.ArticlesCategoryAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.ArticlesUserBean;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.guoku.guokuv4.utils.StringUtils;
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
 * @date 2015-10-23 下午4:16:14 用户图文list
 */
public class UserArticleListAct extends NetWorkActivity {

	private final int DATA = 1001;
	private final int DATA_ADD = 1002;

	@ViewInject(R.id.tv_empty)
	TextView tvEmpty;

	@ViewInject(R.id.pull_listview)
	private PullToRefreshListView listView;

	ArticlesCategoryAdapter adapter;

	int countValue = 30;

	int page = 1;

	UserBean uBean;

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
				if(adapter.getList().get(arg2 - 1).getContent().length() > 0){
					sharebean.setContext(adapter.getList().get(arg2 - 1).getContent());
				}else{
					sharebean.setContext(adapter.getList().get(arg2 - 1).getContent().substring(0, 50));
				}
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
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				page = 1;
				getData(page, DATA, false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				page++;
				getData(page, DATA_ADD, false);
			}
		});
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
		switch (where) {
		case DATA:
			ArticlesUserBean articlesUserBean = JSON.parseObject(result, ArticlesUserBean.class);
			adapter.setList(articlesUserBean.getArticles());
			break;
		case DATA_ADD:
			ArticlesUserBean articlesUserBean2 = JSON.parseObject(result, ArticlesUserBean.class);
			adapter.addListsLast(articlesUserBean2.getArticles());
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
		String title = getIntent().getExtras().getString(PersonalFragment.class.getName());
		uBean = (UserBean) getIntent().getExtras().getSerializable(PersonalFragment.INTENT_CODE);

		if (getIntent().getExtras().getBoolean(PersonalFragment.IS_EMPTY)) {
			isDataEmpty(true, listView, tvEmpty);
			tvEmpty.setText(getResources().getString(R.string.tv_empty_other,
					StringUtils.setSubstring(title, title.length() - 2, title.length())));
		} else {
			getData(page, DATA, true);
		}

		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);
	}

	private void getData(int page, int netTag, boolean isDialog) {
		sendConnection(Constant.USERINFO + uBean.getUser_id() + "/dig/articles/",
				new String[] { "page", "size"},
				new String[] { page + "", String.valueOf(countValue)}, netTag,
				isDialog);
	}
}
