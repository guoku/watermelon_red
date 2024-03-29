package com.guoku.guokuv4.gragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.FansAct;
import com.guoku.guokuv4.act.FirstCategoryAct;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.act.seach.SearchAct;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.adapter.GuangArticlesAdapter;
import com.guoku.guokuv4.adapter.GuangShopAdapter;
import com.guoku.guokuv4.adapter.SearchLogAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.bean.AuthorizeduserBean;
import com.guoku.guokuv4.bean.CategoryBean;
import com.guoku.guokuv4.bean.Discover;
import com.guoku.guokuv4.bean.Discover.BannerEntity;
import com.guoku.guokuv4.bean.Discover.CategoriesEntity;
import com.guoku.guokuv4.bean.Discover.CategoriesEntity.CategoryEntity;
import com.guoku.guokuv4.bean.SearchLogBean;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.eventbus.ZanEB;
import com.guoku.guokuv4.main.WelAct;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.ImgUtils;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.EditTextWithDel;
import com.guoku.guokuv4.view.ImageAddTextLayout;
import com.guoku.guokuv4.view.LayoutSearchBar;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.guoku.guokuv4.view.ScrollViewWithListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

public class GuangFragment extends BaseFrament {
	private static final int PROINFO = 12;
	// private static final int FAXIANHOME = 13;
	private static final int USERINFO = 14;
	private static final int DISCOVER = 15;// banner, 推荐文章， 推荐商品等

	@ViewInject(R.id.layout_search_bar)
	LayoutSearchBar viewSearchbar;

	@ViewInject(R.id.ed_search)
	EditTextWithDel edSearch;

	@ViewInject(R.id.list_search_log)
	public ListView listSearchLog;// 搜索记录list

	@ViewInject(R.id.layout_log)
	public View viewLog;

	@ViewInject(R.id.view_back_black)
	View backblack;// 弹出搜索记录背景

	@ViewInject(R.id.product_vp_img)
	private ViewPager vp;

	@ViewInject(R.id.faxian_sv)
	private ScrollView sv;

	@ViewInject(R.id.gallery_recommend_user)
	private LinearLayout layoutUser;// 推荐用户

	@ViewInject(R.id.layout_user_recommend)
	private LinearLayout viewUser;// 推荐用户layout

	@ViewInject(R.id.gallery_recommend_sort)
	private LinearLayout vpRecommendSort;// 推荐品类

	@ViewInject(R.id.listView_article)
	private ScrollViewWithListView lvArticle;// 热门图文

	GuangArticlesAdapter articlesAdapter;// 热门图文

	private ScheduledExecutorService scheduledExecutorService;
	private boolean onTouchTrue;
	private MyViewPagerAdapter adapter;

	@ViewInject(R.id.tab_gv)
	private ScrollViewWithGridView tab_gv;

	private GuangShopAdapter gvAdapter;

	private SearchLogAdapter searchLogAdapter;// 搜索记录

	// private ArrayList<BannerBean> list;
	private ArrayList<CategoryBean> list_cid;

	private ArrayList<EntityBean> discover;// 分类

	int beannerIndex;
	ArrayList<BannerEntity> beannerList;

	int articleIndex;

	int openType; // 1:banner 2：精选图文 用来区分本view的图文的入口，用来更新数据

	public Animation animationBackShow;
	public Animation animationBackHide;
	public Animation animationllShow;
	public Animation animationllHide;
	public final int animTime = 300;
	public boolean animIsRunning = false;

	private int currentItem;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			vp.setCurrentItem(currentItem);
		}
	};

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		imageLoader.clearMemoryCache();
	}

	@Override
	public void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5, TimeUnit.SECONDS);
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().register(this);
		}
	}

	/**
	 * 换行切换任务
	 */
	private class ScrollTask implements Runnable {
		public void run() {
			synchronized (vp) {
				if (!onTouchTrue) {
					currentItem = (currentItem + 1) % adapter.getCount();
					handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
				}
				// Log.e(TAG, "ScrollTask切换-->" + currentItem);
			}
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		scheduledExecutorService.shutdown();
	}

	@Override
	protected int getContentId() {
		return R.layout.fragment_guang;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onSuccess(String result, int where) {
		if (getActivity() == null) {
			return;
		}
		switch (where) {
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case USERINFO:
			try {
				JSONObject root = new JSONObject(result);
				UserBean userBean = (UserBean) JSON.parseArray(root.getString("user"), UserBean.class);
				intent = new Intent(context, UserBaseFrament.class);
				intent.putExtra("data", userBean);
				startActivity(intent);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			break;
		case DISCOVER:
			try {
				refresh(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			sv.scrollTo(0, 0);
			sv.smoothScrollTo(0, 0);
			break;
		}

	}

	private void refresh(String result) {

		Discover discover = JSON.parseObject(result, Discover.class);

		/******** banner *********/
		setBeannerData(discover.getBanner());

		/******** 品类 *********/
		setCategoriesData(discover.getCategories());

		/******** 热门图文 *********/
		articlesAdapter.setList(discover.getArticles());

		/******** 热门商品 *********/
		try {
			gvAdapter.setList(discover.getEntities());
		} catch (Exception e) {
			// TODO: handle exception
		}

		/******** 推荐用户 *********/
		setRecommendUser(discover.getAuthorizeduser());

		sv.scrollTo(0, 0);
		sv.smoothScrollTo(0, 0);
	}

	private void setBeannerData(final ArrayList<BannerEntity> listData) {

		beannerList = listData;

		List<ImageView> imgs = new ArrayList<ImageView>();
		for (int i = 0; i < beannerList.size(); i++) {
			final ImageView image = new ImageView(getActivity());
			image.setTag(i);
			image.setScaleType(ScaleType.FIT_XY);
			imageLoader.displayImage(beannerList.get(i).getImg(), image, options,
					new ImgUtils.AnimateFirstDisplayListener());
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					beannerIndex = (Integer) arg0.getTag();
					String url = beannerList.get(beannerIndex).getUrl();
					String last = beannerList.get(beannerIndex).getUrlLast();
					AVAnalytics.onEvent(getActivity(), "banner");
					umStatistics(Constant.UM_BANNER, url, beannerList.get(beannerIndex).getImg());

					if (url.contains("http")) {
						Bundle bundle = new Bundle();
						Sharebean sharebean = new Sharebean();
						sharebean.setTitle("");
						sharebean.setContext("");
						sharebean.setAricleUrl(url);
						sharebean.setImgUrl(beannerList.get(beannerIndex).getImg());
						if (beannerList.get(beannerIndex).getArticle() != null) {
							sharebean.setAricleId(
									String.valueOf(beannerList.get(beannerIndex).getArticle().getArticle_id()));
							sharebean.setIs_dig(beannerList.get(beannerIndex).getArticle().isIs_dig());
							openType = 1;
						}
						bundle.putSerializable(WebShareAct.class.getName(), sharebean);
						openActivity(WebShareAct.class, bundle);

					} else if (url.contains("entity")) {
						sendConnection(Constant.PROINFO + last + "/", new String[] { "entity_id" },
								new String[] { last }, PROINFO, true);
					} else if (url.contains("user_id")) {
						sendConnection(Constant.USERINFO + last + "/", new String[] {}, new String[] {}, USERINFO,
								true);
					} else if (url.contains("category_id")) {
						for (CategoryBean bean : list_cid) {
							if (String.valueOf(bean.getGroup_id()).equals(last)) {
								// Intent intent = new Intent(context,
								// TabAct.class);
								Intent intent = new Intent(getActivity(), FirstCategoryAct.class);
								intent.putExtra("data", String.valueOf(bean.getGroup_id()));
								intent.putExtra("name", bean.getTitle());
								startActivity(intent);
							}
						}
					}
				}
			});
			imgs.add(image);
		}
		adapter.setList(imgs);
	}

	private void setCategoriesData(ArrayList<CategoriesEntity> arrayList) {

		for (int i = 0; i < arrayList.size(); i++) {
			final ImageAddTextLayout imagTextLayout = new ImageAddTextLayout(getActivity());
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			params.gravity = Gravity.CENTER;
			imagTextLayout.setPadding(10, 0, 10, 0);
			imagTextLayout.imView.setImageURI(Uri.parse(arrayList.get(i).getCategory().getCover_url()));
			imagTextLayout.tView.setText(arrayList.get(i).getCategory().getTitle().trim().replace(" ", "\n"));
			imagTextLayout.tView.setLayoutParams(params);
			imagTextLayout.setTag(arrayList.get(i).getCategory());
			vpRecommendSort.addView(imagTextLayout);
			imagTextLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					CategoryEntity category = (CategoryEntity) arg0.getTag();
					// Intent intent = new Intent(getActivity(),
					// TabAct.class);
					Intent intent = new Intent(getActivity(), FirstCategoryAct.class);
					intent.putExtra("data", category.getId());
					intent.putExtra("name", category.getTitle());
					getActivity().startActivity(intent);

					umStatistics(Constant.UM_SORT_SUGGESTED, category.getId(), category.getTitle());
				}
			});
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init() {

		gvAdapter = new GuangShopAdapter(getActivity(), 2);

		tab_gv.setNumColumns(2);

		tab_gv.setAdapter(gvAdapter);

		tab_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String id = String.valueOf(gvAdapter.getList().get(arg2).getEntity().getEntity_id());
				sendConnection(Constant.PROINFO + gvAdapter.getList().get(arg2).getEntity().getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { id }, PROINFO, true);
				umStatistics(Constant.UM_SHOP_HOT, id, gvAdapter.getList().get(arg2).getEntity().getTitle());
			}
		});

		try {
			android.widget.RelativeLayout.LayoutParams param = new android.widget.RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, (GuokuApplication.screenW) * 472 / 1028);
			vp.setLayoutParams(param);

			vp.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						break;
					case MotionEvent.ACTION_UP:
						onTouchTrue = true;
						break;
					default:
						break;
					}
					return false;
				}
			});
			vp.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int arg0) {
					currentItem = arg0;
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});
			adapter = new MyViewPagerAdapter();
			vp.setAdapter(adapter);

			initArticle();

			initSearchLog();

		} catch (Exception e) {
		}

	}

	private void initArticle() {
		articlesAdapter = new GuangArticlesAdapter(getActivity());
		lvArticle.setAdapter(articlesAdapter);
		lvArticle.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

				articleIndex = arg2;
				openType = 2;
				Bundle bundle = new Bundle();
				Sharebean sharebean = new Sharebean();
				sharebean.setTitle(articlesAdapter.getList().get(arg2).getArticle().getTitle());
				sharebean.setContext(articlesAdapter.getList().get(arg2).getArticle().getContent().substring(0, 50));
				sharebean.setAricleUrl(articlesAdapter.getList().get(arg2).getArticle().getUrl());
				sharebean.setImgUrl(articlesAdapter.getList().get(arg2).getArticle().getCover());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);
				sharebean.setAricleId(String.valueOf(articlesAdapter.getList().get(arg2).getArticle().getArticle_id()));
				sharebean.setIs_dig(articlesAdapter.getList().get(arg2).getArticle().isIs_dig());
				openActivity(WebShareAct.class, bundle);

				umStatistics(Constant.UM_ARTICLE_HOT,
						String.valueOf(articlesAdapter.getList().get(arg2).getArticle().getArticle_id()),
						articlesAdapter.getList().get(arg2).getArticle().getTitle());
			}
		});
	}

	@Override
	protected void setData() {
		list_cid = ParseUtil.getTab2List(context);
		// sendConnection(Constant.FAXIANHOME, new String[] {}, new String[] {},
		// FAXIANHOME, false);
		if(StringUtils.isEmpty(WelAct.tempDiscover)){
			sendConnection(Constant.DISCOVER, new String[] {}, new String[] {}, DISCOVER, false);
		}else{
			refresh(WelAct.tempDiscover);
		}
	}

	/******** 推荐用户 *********/
	private void setRecommendUser(final ArrayList<AuthorizeduserBean> authorizeduserBeans) {
		if (authorizeduserBeans.size() > 0) {
			viewUser.setVisibility(View.VISIBLE);
			for (int i = 0; i < authorizeduserBeans.size(); i++) {
				View view = View.inflate(getActivity(), R.layout.item_recommend_user, null);
				SimpleDraweeView sView = (SimpleDraweeView) view.findViewById(R.id.psrson_iv_pic);
				sView.setImageURI(Uri.parse(authorizeduserBeans.get(i).getUser().get50()));

				TextView nickName = (TextView) view.findViewById(R.id.tv_psrson);
				nickName.setText(authorizeduserBeans.get(i).getUser().getNickname());

				view.setTag(authorizeduserBeans.get(i));
				layoutUser.addView(view);

				view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						AuthorizeduserBean aBean = (AuthorizeduserBean) v.getTag();
						Intent intent = new Intent(context, UserBaseFrament.class);
						intent.putExtra("data", aBean.getUser());
						startActivity(intent);

						umStatistics(Constant.UM_USER_SUGGESTED, aBean.getUser().getUser_id(),
								aBean.getUser().getNickname());
					}
				});
			}
		} else {
			viewUser.setVisibility(View.GONE);
		}

	}

	@OnClick(R.id.ed_search)
	public void SQR(View v) {
		// startActivity(new Intent(context, SeachAct.class));
		// startActivity(new Intent(context, SeachAct.class));
		edOnClick();
	}

	@OnClick(R.id.view_back_black)
	private void inClickBlack(View view) {
		if (viewLog.getVisibility() == View.VISIBLE) {
			hideSearchWhat();
		}
	}

	/**
	 * 更多推荐用户
	 * 
	 * @param view
	 */
	@OnClick(R.id.tv_user_more)
	private void userMore(View view) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("url", Constant.AUTHORIZED_USERS);
		intent.putExtra("name", "推荐用户");
		intent.putExtra(PersonalFragment.class.getName(), true);
		startActivity(intent);
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<ImageView> mListViews;

		public MyViewPagerAdapter() {
			this.mListViews = new ArrayList<ImageView>();// 构造方法，参数是我们的页卡，这样比较方便。
		}

		public void setList(List<ImageView> list) {
			mListViews = list;
			notifyDataSetChanged();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));// 删除页卡
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
			container.addView(mListViews.get(position), 0);// 添加页卡
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();// 返回页卡的数量
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;// 官方提示这样写
		}

		@Override
		public float getPageWidth(int position) {
			// TODO Auto-generated method stub
			return super.getPageWidth(position);
		}
	}

	/**
	 * 
	 */
	@OnClick(R.id.tv_cancel)
	private void inClickClean(View view) {
		if (viewLog.getVisibility() == View.VISIBLE) {
			hideSearchWhat();
		}
		edSearch.getText().clear();
		GuokuUtil.hideKeyBoard(context);
	}

	@OnClick(R.id.tv_clean)
	private void onCleanView(View view) {
		SharePrenceUtil.delSearchLog(context);
		hideSearchWhat();
	}

	/**
	 * 处理搜索事件
	 */
	public void onKeyDowns() {
		final String content = edSearch.getText().toString();
		if (!StringUtils.isEmpty(content)) {
			SharePrenceUtil.saveSearchRecord(getActivity(), edSearch.getText().toString());
			goSearchAct(content);
		} else {
			ToastUtil.show(getActivity(), getActivity().getResources().getString(R.string.tv_search_please_Enter));
		}
	}

	/**
	 * 初始化搜索记录
	 */
	private void initSearchLog() {

		searchLogAdapter = new SearchLogAdapter(getActivity());
		listSearchLog.setAdapter(searchLogAdapter);

		ViewGroup.LayoutParams params = listSearchLog.getLayoutParams();
		params.height = GuokuApplication.screenH / 3;
		listSearchLog.setLayoutParams(params);

		listSearchLog.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				goSearchAct(searchLogAdapter.getItem(position).getSerchStr());
			}
		});

		edSearch.setOnTouchListener(new OnTouchListener() {
			int touch_flag = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				touch_flag++;
				if (touch_flag == 2) {
					edOnClick();
				}
				return false;
			}
		});
	}

	private void edOnClick() {

		if (searchLogAdapter != null) {
			viewLog.getBackground().setAlpha(230);
			if (viewLog.getVisibility() == View.INVISIBLE) {
				List<SearchLogBean> sBeans = SharePrenceUtil.getSearchRecord(getActivity());
				if (sBeans != null) {
					searchLogAdapter.setList((ArrayList<SearchLogBean>) sBeans);
					showSearchWhat();
				}

				viewSearchbar.isShowClean(true);
			}
		}
	}

	private void goSearchAct(String value) {

		if (!StringUtils.isEmpty(value)) {
			if (viewLog.getVisibility() == View.VISIBLE) {
				hideSearchWhat();
			}
			edSearch.getText().clear();
			GuokuUtil.hideKeyBoard(context);
			Intent intent = new Intent(context, SearchAct.class);
			intent.putExtra(GuangFragment.class.getName(), value);
			startActivity(intent);
			getActivity().overridePendingTransition(R.anim.act_fade_in, R.anim.act_fade_out);
		}
	}

	public void showSearchWhat() {
		showBackBlack();
		if (animationllShow == null) {
			animationllShow = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, -ImgUtils.dip2px(context, 48), Animation.RELATIVE_TO_SELF, 0.0f);
		}
		animationllShow.setDuration(animTime);
		viewLog.startAnimation(animationllShow);
	}

	public void hideSearchWhat() {
		viewSearchbar.isShowClean(false);
		hideBackBlack();
		if (animationllHide == null) {
			animationllHide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -ImgUtils.dip2px(context, 48));
		}
		animationllHide.setDuration(animTime);
		viewLog.startAnimation(animationllHide);
	}

	public void showBackBlack() {
		if (animationBackShow == null) {
			backblack.setVisibility(View.VISIBLE);
			animationBackShow = new AlphaAnimation(0.0f, 1.0f);
			animationBackShow.setAnimationListener(animationShowListener);
		}
		animationBackShow.setDuration(animTime);
		backblack.startAnimation(animationBackShow);
	}

	public void hideBackBlack() {
		if (animationBackHide == null) {
			animationBackHide = new AlphaAnimation(1.0f, 0.0f);
			animationBackHide.setAnimationListener(animationHideListener);
		}
		animationBackHide.setDuration(animTime);
		backblack.startAnimation(animationBackHide);
	}

	AnimationListener animationShowListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			animIsRunning = true;
			backblack.setVisibility(View.VISIBLE);
			viewLog.setVisibility(View.VISIBLE);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			animIsRunning = false;
		}
	};

	AnimationListener animationHideListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			animIsRunning = true;
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			backblack.setVisibility(View.INVISIBLE);
			viewLog.setVisibility(View.INVISIBLE);
			animIsRunning = false;
		}
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (!EventBus.getDefault().isRegistered(this)) {
			EventBus.getDefault().unregister(this);
		}
	}

	public void onEventMainThread(ZanEB zEb) {
		if (zEb.isZan()) {
			if (openType == 1) {
				beannerList.get(beannerIndex).getArticle().setIs_dig(true);
			}
			if (openType == 2) {
				articlesAdapter.getItem(articleIndex).getArticle().setIs_dig(true);
			}
		} else {
			if (openType == 1) {
				beannerList.get(beannerIndex).getArticle().setIs_dig(false);
			}
			if (openType == 2) {
				articlesAdapter.getItem(articleIndex).getArticle().setIs_dig(false);
			}
		}
	}
}
