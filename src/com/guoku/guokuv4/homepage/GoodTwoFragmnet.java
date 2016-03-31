package com.guoku.guokuv4.homepage;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.adapter.JingXuanAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.LikesBean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.PBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

public class GoodTwoFragmnet extends BaseFrament implements OnClickListener {

	private static final int JINGXUANUP = 10;
	private static final int PROINFO = 12;
	private static final int LIKE1 = 13;
	private static final int LIKE0 = 14;
	private static final int JINGXUAN_DOWN = 15;
	// public static final int UPDATA_LIKE = 16;
	public static final String INTNT_KEY = GoodTwoFragmnet.class.getName();
	// private static final int UPDATA_LIKE_UN = 17;
	@ViewInject(R.id.jingxuan_lv_1)
	public PullToRefreshListView jingxuan_lv_1;
	@ViewInject(R.id.tv_check_net)
	ImageView tvCheckNet;
	@ViewInject(R.id.re_head_view)
	RelativeLayout re_head_view;
	@ViewInject(R.id.tv_count)
	TextView tvShopCount;//更新商品数量

	public JingXuanAdapter adapter;
	private ArrayList<PBean> list;
	private PBean pBean;

	private int cur;
	public View layoutView;// 刷新喜欢img
	public int pos;// 记录点击的哪个商品
	int indexList;

	private boolean scrollFlag = false;// 标记是否滑动
	private boolean isUnRead;
	
	public Animation animationllShow;
	public Animation animationllHide;
	public final int animTime = 200;
	public boolean animIsRunning = false;

	@Override
	protected void init() {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		GuokuApplication.screenW = metrics.widthPixels;
		GuokuApplication.screenH = metrics.heightPixels;

		// list = new ArrayList<ProductBean>();
		adapter = new JingXuanAdapter(context, this);

		jingxuan_lv_1.setMode(Mode.BOTH);
		jingxuan_lv_1.setAdapter(adapter);
		jingxuan_lv_1.getRefreshableView().setOnScrollListener(onScrollListener);

		jingxuan_lv_1.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				if (list != null) {
					if (list.size() > 0) {
						closeHeadView();
						getJingXuan(System.currentTimeMillis() / 1000 + "", false);
					}
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

				if (list != null) {
					if (list.size() > 0) {
						getJingXuanDown(list.get(list.size() - 1).getPost_time());
					}
				}
			}
		});

		jingxuan_lv_1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				layoutView = arg1;
				pos = arg2 - 1;
				getShopInfo();
			}
		});

		initUnRead();
	}

	@Override
	protected void setData() {
		if (adapter.getCount() > 0) {
			return;
		}

	}

	private void getJingXuan(String time, boolean isShowDialog) {
		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp", "rcat" },
				new String[] { "30", time, cur + "" }, JINGXUAN_DOWN, isShowDialog);
	}

	private void getJingXuanDown(String time) {
		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp", "rcat" },
				new String[] { "30", time, cur + "" }, JINGXUANUP, false);
	}

	private void getShopInfo() {
		sendConnection(Constant.PROINFO + list.get(pos).getContent().getEntity().getEntity_id() + "/",
				new String[] { "entity_id" }, new String[] { list.get(pos).getContent().getEntity().getEntity_id() },
				PROINFO, true);
	}

	@Override
	protected int getContentId() {
		return R.layout.fragment_destination;
	}

	@Override
	protected void onSuccess(String result, int where) {
		if (jingxuan_lv_1.getVisibility() == View.GONE) {
			tvCheckNet.setVisibility(View.GONE);
			jingxuan_lv_1.setVisibility(View.VISIBLE);
		}
		jingxuan_lv_1.onRefreshComplete();
		switch (where) {
		case JINGXUAN_DOWN:
			list = ParseUtil.getJingXuanList(result);
			adapter.setList(list);
			break;
		case JINGXUANUP:
			list.addAll(ParseUtil.getJingXuanList(result));
			adapter.setList(list);
			break;
		case PROINFO:
			try {
				PInfoBean bean = ParseUtil.getPI(result);
				Intent intent = new Intent(context, ProductInfoAct.class);
				intent.putExtra("data", JSON.toJSONString(bean));
				startActivity(intent);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case LIKE0:
			EventBus.getDefault().post(new LikesBean(false));
			break;
		case LIKE1:
			EventBus.getDefault().post(new LikesBean(true));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		jingxuan_lv_1.onRefreshComplete();
		GuokuUtil.closeListViewHeader(jingxuan_lv_1);
		switch (where) {
		case LIKE0:
			ToastUtil.show(context, "取消喜爱失敗");
			break;
		case LIKE1:
			ToastUtil.show(context, "喜爱失败");
			break;
		case JINGXUAN_DOWN:
			if (list == null) {
				tvCheckNet.setVisibility(View.VISIBLE);
				jingxuan_lv_1.setVisibility(View.GONE);
			}
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
			pos = pBean.getPosition();
			layoutView = arg0;
			if (pBean.getContent().getEntity().getLike_already().equals("0")) {

				sendConnectionPost(Constant.TOLIKE + pBean.getContent().getEntity().getEntity_id() + "/like/1/",
						new String[] {}, new String[] {}, LIKE1, false);
			} else {

				sendConnectionPost(Constant.TOLIKE + pBean.getContent().getEntity().getEntity_id() + "/like/0/",
						new String[] {}, new String[] {}, LIKE0, false);
			}
			break;

		default:
			break;
		}

	}

	@OnClick(R.id.tv_check_net)
	private void onCheckNetClick(View view) {
		getJingXuan(System.currentTimeMillis() / 1000 + "", true);
	}

	@OnClick(R.id.re_head_view)
	private void onCheckUpdata(View view) {
		jingxuan_lv_1.setRefreshing();
		closeHeadView();
		getJingXuan(System.currentTimeMillis() / 1000 + "", false);
		SharePrenceUtil.delShopUnRead(getActivity());
	}

	@OnClick(R.id.img_close)
	private void onCheckClose(View view) {
		closeHeadView();
	}

	public void onEventMainThread(LikesBean likesBean) {
		if (likesBean.isLike()) {
			AVAnalytics.onEvent(context, "like_click", pBean.getContent().getEntity().getTitle());
			AVAnalytics.onEvent(context, "like");
			MobclickAgent.onEvent(context, "like");

			if (pBean == null) {
				return;
			}
			pBean.getContent().getEntity().setLike_already("1");
			pBean.getContent().getEntity().setLike_count(pBean.getContent().getEntity().getLike_countAdd());
			adapter.setStatus(layoutView, pBean);
		} else {
			if (pBean == null) {
				return;
			}
			pBean.getContent().getEntity().setLike_already("0");
			pBean.getContent().getEntity().setLike_count(pBean.getContent().getEntity().getLike_countCut());

			adapter.setStatus(layoutView, pBean);
		}
	}

	OnScrollListener onScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			// 不滚动时保存当前滚动到的位置
			if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
				if (adapter != null) {
//					indexList = jingxuan_lv_1.getRefreshableView().getFirstVisiblePosition();
				}
			}

			// 判断状态
			switch (scrollState) {
			// 当不滚动时
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
				scrollFlag = false;
				// 判断滚动到底部 、position是从0开始算起的
				if (jingxuan_lv_1.getRefreshableView()
						.getLastVisiblePosition() == (jingxuan_lv_1.getRefreshableView().getCount() - 1)) {

					// TODO

				}
				// 判断滚动到顶部
				if (jingxuan_lv_1.getRefreshableView().getFirstVisiblePosition() == 0) {

					// TODO
				}

				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
				scrollFlag = true;
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				// 当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时，即滚动时
				scrollFlag = true;
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			// 当滑动时
			if(isUnRead){
				if (scrollFlag) {
					
					if (firstVisibleItem < indexList) {
						// 下滑
						if (re_head_view.getVisibility() == View.GONE) {
							showSearchWhat();
							re_head_view.setVisibility(View.VISIBLE);
						}
					} else if (firstVisibleItem > indexList) {
						// 上滑
						if (re_head_view.getVisibility() == View.VISIBLE) {
							hideSearchWhat();
							re_head_view.setVisibility(View.GONE);
						}
					} else {
						return;
					}
				}
			}
			indexList = firstVisibleItem;// 更新位置
		}
	};

	private void initUnRead() {

		String unReadData = SharePrenceUtil.getShopUnRead(getActivity());

		if (!StringUtils.isEmpty(unReadData)) {
			list = new ArrayList<PBean>();
			list.addAll(ParseUtil.getJingXuanList(unReadData));
			adapter.setList(list);
		} else {
			jingxuan_lv_1.setRefreshing();
			getJingXuan(System.currentTimeMillis() / 1000 + "", false);
		}
		
		if(GuokuApplication.getInstance().getUnReadData() != null){
			if(GuokuApplication.getInstance().getUnReadData().getUnread_selection_count() > 0){
				isUnRead = true;
				tvShopCount.setText(getActivity().getResources().getString(R.string.tv_shop_unread, GuokuApplication.getInstance().getUnReadData().getUnread_selection_count() + ""));
				showSearchWhat();
				re_head_view.setVisibility(View.VISIBLE);
			}
		}
	}

	private void closeHeadView() {
		if(isUnRead){
			isUnRead = false;
			hideSearchWhat();
			re_head_view.setVisibility(View.GONE);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (indexList != 0) {
			SharePrenceUtil.setShopUnRead(getActivity(), list.subList(indexList, list.size()));
		}
	}
	
	public void showSearchWhat() {
		if (animationllShow == null) {
			animationllShow = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		}
		animationllShow.setDuration(animTime);
		re_head_view.startAnimation(animationllShow);
	}

	public void hideSearchWhat() {
		if (animationllHide == null) {
			animationllHide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
		}
		animationllHide.setDuration(animTime);
		re_head_view.startAnimation(animationllHide);
	}
}