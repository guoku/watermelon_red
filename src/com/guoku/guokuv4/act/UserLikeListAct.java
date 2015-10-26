/**

 */
package com.guoku.guokuv4.act;

import com.guoku.R;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-23 下午1:43:40 
 * 用户喜爱商品list
 */
public class UserLikeListAct extends NetWorkActivity{
	
	private final int TABLIKE = 1001;// 喜欢
	private final int PROINFO = 1002;
	
	@ViewInject(R.id.sv)
	private PullToRefreshScrollView sv;//gridview上拉刷新
	
	@ViewInject(R.id.tab_gv)
	private ScrollViewWithGridView tab_gv;
	
	private GridViewAdapter gvAdapter;
	
	int countValue = 30;
	
	UserBean uBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_user_like_list);
		
		init();
	}
	
	private void init(){
		
		gvAdapter = new GridViewAdapter(mContext, 3);
		tab_gv.setAdapter(gvAdapter);
		tab_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				getShopInfo(gvAdapter.getItem(arg2).getEntity_id());
			}
		});
		
		sv.setMode(Mode.BOTH);
		sv.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				countValue = 30;
				getLikeData(TABLIKE, false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				countValue += 30;
				getLikeData(TABLIKE, false);
			}
		});
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		sv.onRefreshComplete();
		switch (where) {
		case TABLIKE:
			gvAdapter.setList(ParseUtil.getTabLikeList(result));
			break;
		case PROINFO:
			openShopInfo(result);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		sv.onRefreshComplete();
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		String title = getIntent().getExtras().getString(PersonalFragment.class.getName());
		uBean = (UserBean) getIntent().getExtras().getSerializable(PersonalFragment.INTENT_CODE);
		getLikeData(TABLIKE, true);
		
		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);
	}
	
	private void getLikeData(int netTag, boolean isDialog) {
		sendConnection(Constant.TAB_USER + uBean.getUser_id() + "/" + LIKE
				+ "/", new String[] { "count", "timestamp" }, new String[] {
				String.valueOf(countValue), System.currentTimeMillis() / 1000 + "" }, netTag,
				isDialog);
	}
	
	private void getShopInfo(String id) {
		sendConnection(Constant.PROINFO + id + "/",
				new String[] { "entity_id" }, new String[] { id }, PROINFO,
				true);
	}
}
