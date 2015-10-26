/**

 */
package com.guoku.guokuv4.act;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.guoku.R;
import com.guoku.guokuv4.adapter.ListImgLeftAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.guoku.guokuv4.parse.ParseUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-23 下午3:52:33 用户点评list
 */
public class UserCommentListAct extends NetWorkActivity {

	private final int TABNOTE = 1001;
	private final int PROINFO = 1002;

	@ViewInject(R.id.tab_lv)
	private PullToRefreshListView listView;

	ListImgLeftAdapter adapter;

	int countValue = 30;

	UserBean uBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_user_comment_list);

		init();
	}

	private void init() {

		adapter = new ListImgLeftAdapter(mContext);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				getShopInfo(adapter.getItem(arg2 - 1).getEntity().getEntity_id());
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
				getLikeData(TABNOTE, false);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				countValue += 30;
				getLikeData(TABNOTE, false);
			}
		});
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
		switch (where) {
		case TABNOTE:
			adapter.setList(ParseUtil.getTabNoteList(result));
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
		listView.onRefreshComplete();
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		String title = getIntent().getExtras().getString(
				PersonalFragment.class.getName());
		uBean = (UserBean) getIntent().getExtras().getSerializable(
				PersonalFragment.INTENT_CODE);
		getLikeData(TABNOTE, true);

		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);
	}

	private void getLikeData(int netTag, boolean isDialog) {
		sendConnection(Constant.TAB_USER + uBean.getUser_id() + "/entity/note"
				+ "/", new String[] { "count", "timestamp" }, new String[] {
				String.valueOf(countValue),
				System.currentTimeMillis() / 1000 + "" }, netTag, isDialog);
	}

	private void getShopInfo(String id) {
		sendConnection(Constant.PROINFO + id + "/",
				new String[] { "entity_id" }, new String[] { id }, PROINFO,
				true);
	}
}
