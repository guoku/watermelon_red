/**

 */
package com.guoku.guokuv4.act;

import com.guoku.R;
import com.guoku.guokuv4.adapter.UserTagListAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-23 下午5:04:40 用户标签list
 */
public class UserTagListAct extends NetWorkActivity {

	private final int TAB_TAG = 1001;
	
	@ViewInject(R.id.tv_empty)
	TextView tvEmpty;

	@ViewInject(R.id.pull_listview)
	private PullToRefreshListView listView;

	UserTagListAdapter adapter;

	int countValue = 30;

	UserBean uBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_user_tag_list);

		init();
	}

	private void init() {

		adapter = new UserTagListAdapter(mContext);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserTagListAct.this, EntityAct.class);
				intent.putExtra("data", uBean.getUser_id());
				intent.putExtra("name", adapter.getItem(arg2 - 1).getTag());
				startActivity(intent);
			}
		});

		listView.setPullToRefreshOverScrollEnabled(false);
		listView.setScrollingWhileRefreshingEnabled(false);
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				countValue = 30;
				getNetData(TAB_TAG, false);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				countValue += 30;
				getNetData(TAB_TAG, false);
			}
		});
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
		switch (where) {
		case TAB_TAG:
			adapter.setList(ParseUtil.getTabTagList(result));
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
		String title = getIntent().getExtras().getString(
				PersonalFragment.class.getName());
		uBean = (UserBean) getIntent().getExtras().getSerializable(
				PersonalFragment.INTENT_CODE);
		
		if(getIntent().getExtras().getBoolean(PersonalFragment.IS_EMPTY)){
			isDataEmpty(true, listView, tvEmpty);
			tvEmpty.setText(getResources().getString(R.string.tv_empty_other, StringUtils.setSubstring(title, title.length() - 2, title.length())));
		}else{
			getNetData(TAB_TAG, true);
		}
		

		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);
	}

	private void getNetData(int netTag, boolean isDialog) {
		sendConnection(
				Constant.TAB_USER + uBean.getUser_id() + "/tag/",
				new String[] { "count", "timestamp" },
				new String[] { String.valueOf(countValue),
						System.currentTimeMillis() / 1000 + "" }, netTag,
				isDialog);
	}
}
