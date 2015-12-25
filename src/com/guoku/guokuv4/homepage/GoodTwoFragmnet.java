package com.guoku.guokuv4.homepage;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.adapter.JingXuanAdapter;
import com.guoku.guokuv4.base.BaseActivity.OnDoubleClickListener;
import com.guoku.guokuv4.bean.LikesBean;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.PBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.BroadUtil;
import com.guoku.guokuv4.utils.GuokuUtil;
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
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import de.greenrobot.event.EventBus;
import android.widget.ImageView;
import android.widget.ListView;

public class GoodTwoFragmnet extends BaseFrament implements OnClickListener {

	private static final int JINGXUANUP = 10;
	private static final int PROINFO = 12;
	private static final int LIKE1 = 13;
	private static final int LIKE0 = 14;
	private static final int TYPE = 15;
//	public static final int UPDATA_LIKE = 16;
	public static final String INTNT_KEY = GoodTwoFragmnet.class.getName();
	// private static final int UPDATA_LIKE_UN = 17;
	@ViewInject(R.id.jingxuan_lv_1)
	public PullToRefreshListView jingxuan_lv_1;
	
	@ViewInject(R.id.tv_check_net)
	ImageView tvCheckNet;
	
	public JingXuanAdapter adapter;
	private ArrayList<PBean> list;
	private PBean pBean;

	private int cur;
	public View layoutView;// 刷新喜欢img

	public int pos;// 纪录商品

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

		jingxuan_lv_1.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if(list != null){
					if (list.size() > 0) {
						getJingXuan(System.currentTimeMillis() / 1000 + "", false);
					}
				}
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				
				if(list != null){
					if (list.size() > 0) {
						getJingXuanDown(list.get(list.size() - 1).getPost_time());
					}
				}
			}
		});

		jingxuan_lv_1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				layoutView = arg1;
				pos = arg2 - 1;
				sendConnection(Constant.PROINFO
						+ list.get(pos).getContent().getEntity().getEntity_id()
						+ "/", new String[] { "entity_id" },
						new String[] { list.get(pos).getContent().getEntity()
								.getEntity_id() }, PROINFO, true);
			}
		});
	}

	@Override
	protected void setData() {
		if (adapter.getCount() > 0) {
			return;
		}
		getJingXuan(System.currentTimeMillis() / 1000 + "", true);
	}

	private void getJingXuan(String time, boolean isShowDialog) {
		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp",
				"rcat" }, new String[] { "30", time, cur + "" }, TYPE, isShowDialog);
	}

	private void getJingXuanDown(String time) {
		sendConnection(Constant.JINGXUAN, new String[] { "count", "timestamp",
				"rcat" }, new String[] { "30", time, cur + "" }, JINGXUANUP,
				false);
	}

	@Override
	protected int getContentId() {
		return R.layout.fragment_destination;
	}

	@Override
	protected void onSuccess(String result, int where) {
		if(jingxuan_lv_1.getVisibility() == View.GONE){
			tvCheckNet.setVisibility(View.GONE);
			jingxuan_lv_1.setVisibility(View.VISIBLE);
		}
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
			AVAnalytics.onEvent(context, "like_click", pBean.getContent()
					.getEntity().getTitle());
			AVAnalytics.onEvent(context, "like");
			MobclickAgent.onEvent(context, "like");
			EventBus.getDefault().post(new LikesBean(true));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		GuokuUtil.closeListViewHeader(jingxuan_lv_1);
		switch (where) {
		case LIKE0:
			ToastUtil.show(context, "取消喜爱失敗");
			break;
		case LIKE1:
			ToastUtil.show(context, "喜爱失败");
			break;
		case TYPE:
			if(list == null){
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

	
	@OnClick(R.id.tv_check_net)
	private void onCheckNetClick(View view){
		getJingXuan(System.currentTimeMillis() / 1000 + "", true);
	}
}