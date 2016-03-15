/**

 */
package com.guoku.guokuv4.act.seach;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.FansAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.eventbus.FollowEB;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import de.greenrobot.event.EventBus;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月9日 下午4:44:47 搜索用户页
 */
public class SearchUserFragment extends BaseFrament implements OnClickListener {

	public static final int SEARCH = 1001;
	private static final int SEARCHADD = 1002;
	private static final int FOLLOW1 = 1003;
	private static final int FOLLOW0 = 1004;

	@ViewInject(R.id.pull_listview)
	PullToRefreshListView listView;
	
	@ViewInject(R.id.layout_search_empty)
	View viewEmpty;

	private FansAdapter fansAdapter;

	UserBean beanFowllow;// 刷新关注按钮相关
	ImageView imgFowllow;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		if(!EventBus.getDefault().isRegistered(this)){
			EventBus.getDefault().register(this);
		}
		
		fansAdapter = new FansAdapter(context, this);

		listView.setMode(Mode.PULL_FROM_END);
		listView.setAdapter(fansAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(context, UserBaseFrament.class);
				intent.putExtra("data", fansAdapter.getItem(arg2 - 1));
				startActivity(intent);
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				getData(SearchAct.searchStr, false, fansAdapter.getCount());
			}
		});
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_search_good;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
		switch (where) {
		case SEARCH:
			try {
				hideEmpty(viewEmpty, listView);
				ArrayList<UserBean> list = (ArrayList<UserBean>) JSON.parseArray(result, UserBean.class);
				ArrayList<UserBean> bufList = new ArrayList<UserBean>();
				if (list != null && list.size() > 0) {
					for (UserBean bean : list) {
						if (!bean.getIs_active().equals("0")) {
							bufList.add(bean);
						}
					}
					fansAdapter.setList(bufList);
				}
				if (fansAdapter.getCount() == 0) {
					showEmpty(viewEmpty, listView);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case SEARCHADD:
			try {
				ArrayList<UserBean> listUser = ((ArrayList<UserBean>) JSON.parseArray(result, UserBean.class));
				fansAdapter.addListsLast(listUser);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case FOLLOW0:
			fansAdapter.setStatus(imgFowllow, beanFowllow);
			
			FollowEB fEb = new FollowEB();
			fEb.setFollow(false);
			EventBus.getDefault().post(fEb);
			break;
		case FOLLOW1:
			fansAdapter.setStatus(imgFowllow, beanFowllow);
			
			FollowEB fEb2 = new FollowEB();
			fEb2.setFollow(true);
			EventBus.getDefault().post(fEb2);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		listView.onRefreshComplete();
		switch (where) {
		case FOLLOW0:
			ToastUtil.show(context, "取消关注失败");
		case FOLLOW1:
			ToastUtil.show(context, "关注失败");
		default:
			break;
		}
	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		getData(SearchAct.searchStr, false, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fans_item_iv_status:
			beanFowllow = (UserBean) v.getTag();
			imgFowllow = (ImageView) v;

			if (beanFowllow.getRelation().equals("0") || beanFowllow.getRelation().equals("2")) {
				sendConnectionPost(Constant.FOLLOW + beanFowllow.getUser_id() + "/follow/1/", new String[] {},
						new String[] {}, FOLLOW1, false);
				beanFowllow.setRelation("1");
			} else {
				sendConnectionPost(Constant.FOLLOW + beanFowllow.getUser_id() + "/follow/0/", new String[] {},
						new String[] {}, FOLLOW0, false);
				beanFowllow.setRelation("0");
			}
			// fansAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	public void getData(String str, boolean isShowDialog, int off) {
		sendConnection(Constant.SEARCH + "user/search/", new String[] { "count", "offset", "q", "type" },
				new String[] { "30", off + "", str, "all" }, off == 0 ? SEARCH : SEARCHADD, isShowDialog);
	}
	
	public void onEventMainThread(FollowEB fEb) {
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
