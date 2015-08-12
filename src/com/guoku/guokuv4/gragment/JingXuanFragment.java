package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.utils.ArrayListAdapter;
import com.ekwing.students.utils.POPUtils;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.adapter.JingXuanAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.entity.test.JIngxuanBean;
import com.guoku.guokuv4.entity.test.PBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.BroadUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.umeng.analytics.MobclickAgent;

public class JingXuanFragment extends BaseFrament implements OnClickListener,
		OnDismissListener {

	private static final int JINGXUANUP = 10;
	private static final int PROINFO = 12;
	private static final int LIKE1 = 13;
	private static final int LIKE0 = 14;
	private static final int TYPE = 15;
	@ViewInject(R.id.jingxuan_lv_1)
	private PullToRefreshListView jingxuan_lv_1;
	private JingXuanAdapter adapter;
	private ArrayList<PBean> list;
	private PBean pBean;

	@ViewInject(R.id.jingxuan_tv)
	private TextView tv;

	@ViewInject(R.id.jingxuan_iv)
	private ImageView iv;
	@ViewInject(R.id.jingxuan_iv_bar)
	private ImageView bar;

	private int cur;
	private GridView view;
	private ArrayList<JIngxuanBean> list2;
	private ArrayListAdapter<JIngxuanBean> adapter2;
	private View layoutView;//刷新喜欢img

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		imageLoader.clearMemoryCache();
	}

	@Override
	protected void init() {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		EkwingApplication.screenW = metrics.widthPixels;
		EkwingApplication.screenH = metrics.heightPixels;

		jingxuan_lv_1.setOnScrollListener(new PauseOnScrollListener(
				imageLoader, true, true));

		// list = new ArrayList<ProductBean>();
		adapter = new JingXuanAdapter(context, this);

		jingxuan_lv_1.setPullToRefreshOverScrollEnabled(false);
		jingxuan_lv_1.setScrollingWhileRefreshingEnabled(false);
		jingxuan_lv_1.setMode(Mode.BOTH);
		jingxuan_lv_1.setAdapter(adapter);

		jingxuan_lv_1.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// sendConnection(Constant.JINGXUAN, new String[] { "count",
				// "timestamp" },
				// new String[] { "10",
				// System.currentTimeMillis() / 1000 + "" },
				// JINGXUANDOWN, false);
				getJingXuan(System.currentTimeMillis() / 1000 + "");
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (list.size() > 0) {

					// sendConnection(Constant.JINGXUAN, new String[] { "count",
					// "timestamp" },
					// new String[] { "10",
					// list.get(list.size() - 1).getPost_time() },
					// JINGXUANUP, false);
					getJingXuanDown(list.get(list.size() - 1).getPost_time());
				} else {
					// Toast
				}
			}
		});

		jingxuan_lv_1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				sendConnection(Constant.PROINFO
						+ list.get(arg2 - 1).getContent().getEntity()
								.getEntity_id() + "/",
						new String[] { "entity_id" },
						new String[] { list.get(arg2 - 1).getContent()
								.getEntity().getEntity_id() }, PROINFO, true);
			}
		});

		list2 = JIngxuanBean.getlist();

		view = (GridView) LayoutInflater.from(context).inflate(R.layout.gv_4,
				null);
		adapter2 = new ArrayListAdapter<JIngxuanBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView = new TextView(mContext);
				textView.setText(mList.get(position).getName());
				textView.setBackgroundColor(Color.rgb(250, 250, 250));
				LayoutParams params = new LayoutParams(
						EkwingApplication.screenW / 5 + 20,
						EkwingApplication.screenW / 5 + 20);
				textView.setGravity(Gravity.CENTER);
				textView.setLayoutParams(params);
				Logger.i(TAG, "po-->" + position + ",cur--->" + cur);
				if (cur == position) {
					textView.setTextColor(Color.rgb(66, 126, 192));
				} else {
					textView.setTextColor(getResources().getColor(R.color.g_other));
				}

				return textView;
			}
		};
		adapter2.setList(list2);
		view.setAdapter(adapter2);
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				cur = arg2;
				POPUtils.dissMiss();
				bar.setImageResource(R.drawable.open_list);

				cur = arg2;
				if (arg2 == 0) {
					tv.setVisibility(View.GONE);
					iv.setVisibility(View.VISIBLE);
				} else {
					iv.setVisibility(View.GONE);

					tv.setVisibility(View.VISIBLE);
					tv.setText(list2.get(arg2).getName());
				}
				jingxuan_lv_1.setSelection(0);
				getJingXuanShow(System.currentTimeMillis() / 1000 + "");
			}
		});
		POPUtils.setPop(view);

		POPUtils.setListener(this);
	}

	@Override
	protected void setData() {
		if (adapter.getCount() > 0) {
			return;
		}
		getJingXuan(System.currentTimeMillis() / 1000 + "");
	}

	private void getJingXuan(String time) {
		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp",
				"rcat" }, new String[] { "30", time, cur + "" }, TYPE, false);
	}

	private void getJingXuanShow(String time) {
		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp",
				"rcat" }, new String[] { "30", time, cur + "" }, TYPE, true);
	}

	private void getJingXuanDown(String time) {
		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp",
				"rcat" }, new String[] { "30", time, cur + "" }, JINGXUANUP,
				false);
	}

	@OnClick(R.id.jingxuan_iv)
	public void showiv(View v) {
		showPop(v);
	}

	@OnClick(R.id.refresh)
	public void refresh(View v) {
		getJingXuanShow(System.currentTimeMillis() / 1000 + "");
		jingxuan_lv_1.setSelection(0);
	}

	@OnClick(R.id.jingxuan_tv)
	public void showtv(View v) {

		showPop(v);
	}

	private void showPop(View v) {
		bar.setImageResource(R.drawable.close_list);
		adapter2.notifyDataSetChanged();
		POPUtils.show(v);
	}

	@Override
	protected int getContentId() {
		return R.layout.fragment_destination;
	}

	@Override
	protected void onSuccess(String result, int where) {
		jingxuan_lv_1.onRefreshComplete();
		switch (where) {
		case TYPE:
			list = ParseUtil.getJingXuanList(result);
			adapter.setList(list);
			break;

		case JINGXUANUP:
			list.addAll(ParseUtil.getJingXuanList(result));
			adapter.setList(list);
			break;
		// case JINGXUANDOWN:
		// list = ParseUtil.getJingXuanList(result);
		// adapter.setList(list);
		// break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case LIKE0:
			if (pBean == null) {
				return;
			}
			ToastUtil.show(context, "取消喜爱");
			pBean.getContent().getEntity().setLike_already("0");
			pBean.getContent()
					.getEntity()
					.setLike_count(
							pBean.getContent().getEntity().getLike_countCut());
			
			adapter.setStatus(layoutView, pBean);
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY, Constant.INTENT_ACTION_VALUE_LIKE);
			break;
		case LIKE1:
			AVAnalytics.onEvent(context, "like_click", pBean.getContent()
					.getEntity().getTitle());
			AVAnalytics.onEvent(context, "like");
			MobclickAgent.onEvent(context, "like");

			if (pBean == null) {
				return;
			}
			ToastUtil.show(context, "喜爱成功");
			pBean.getContent().getEntity().setLike_already("1");
			pBean.getContent()
					.getEntity()
					.setLike_count(
							pBean.getContent().getEntity().getLike_countAdd());
			adapter.setStatus(layoutView, pBean);
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY, Constant.INTENT_ACTION_VALUE_LIKE);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		jingxuan_lv_1.onRefreshComplete();
		switch (where) {
		case LIKE0:
			ToastUtil.show(context, "取消失敗");
			break;
		case LIKE1:
			ToastUtil.show(context, "喜爱失败");
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.jingxuan_item_ll_like:
			pBean = (PBean) arg0.getTag();
			layoutView = arg0;
			if (pBean.getContent().getEntity().getLike_already().equals("0")) {

				sendConnectionPost(Constant.TOLIKE
						+ pBean.getContent().getEntity().getEntity_id()
						+ "/like/1/", new String[] {}, new String[] {}, LIKE1,
						false);
			} else {

				sendConnectionPost(Constant.TOLIKE
						+ pBean.getContent().getEntity().getEntity_id()
						+ "/like/0/", new String[] {}, new String[] {}, LIKE0,
						false);
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onDismiss() {
		// TODO Auto-generated method stub

		bar.setImageResource(R.drawable.open_list);

	}
}
