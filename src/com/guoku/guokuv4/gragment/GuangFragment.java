package com.guoku.guokuv4.gragment;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.ProductInfoAct;
//import com.guoku.guokuv4.act.SeachAct;
import com.guoku.guokuv4.act.TabAct;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.act.seach.SearchAct;
import com.guoku.guokuv4.adapter.GuangArticlesAdapter;
import com.guoku.guokuv4.adapter.GuangShopAdapter;
import com.guoku.guokuv4.adapter.SearchLogAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.bean.Discover;
import com.guoku.guokuv4.bean.SearchLogBean;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.BannerBean;
import com.guoku.guokuv4.entity.test.Categories;
import com.guoku.guokuv4.entity.test.Categories.Category;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.ImgUtils;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.EditTextWithDel;
import com.guoku.guokuv4.view.ImageAddTextLayout;
import com.guoku.guokuv4.view.ScrollViewWithListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class GuangFragment extends BaseFrament {
	private static final int PROINFO = 12;
	private static final int FAXIANHOME = 13;
	private static final int USERINFO = 14;
	private static final int DISCOVER = 15;// banner, 推荐文章， 推荐商品等

	@ViewInject(R.id.ed_search)
	EditTextWithDel edSearch;
	
	@ViewInject(R.id.list_search_log)
	public ListView listSearchLog;//搜索记录list
	
	@ViewInject(R.id.layout_log)
	public View viewLog;
	
	@ViewInject(R.id.view_back_black)
	View backblack;//弹出搜索记录背景

	@ViewInject(R.id.product_vp_img)
	private ViewPager vp;

	@ViewInject(R.id.faxian_sv)
	private ScrollView sv;

	@ViewInject(R.id.gallery_recommend_sort)
	private LinearLayout vpRecommendSort;// 推荐品类

	@ViewInject(R.id.listView_article)
	private ScrollViewWithListView lvArticle;// 热门图文

	GuangArticlesAdapter articlesAdapter;// 热门图文

	private ScheduledExecutorService scheduledExecutorService;
	private boolean onTouchTrue;
	private MyViewPagerAdapter adapter;

	@ViewInject(R.id.faxian_gv)
	private GridView faxian_gv;

	private GuangShopAdapter gvAdapter;
	
	private SearchLogAdapter searchLogAdapter;//搜索记录

	private ArrayList<BannerBean> list;
	private ArrayList<Tab2Bean> list_cid;

	private ArrayList<EntityBean> discover;// 分类
	
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
		case FAXIANHOME:
			try {
				JSONObject root = new JSONObject(result);
				list = (ArrayList<BannerBean>) JSON.parseArray(root.getString("banner"), BannerBean.class);
				List<ImageView> imgs = new ArrayList<ImageView>();
				for (int i = 0; i < list.size(); i++) {
					final ImageView image = new ImageView(getActivity());
					image.setTag(i);
					image.setScaleType(ScaleType.FIT_XY);
					imageLoader.displayImage(list.get(i).getImg(), image, options,
							new ImgUtils.AnimateFirstDisplayListener());
					image.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							int index = (Integer) arg0.getTag();
							String url = list.get(index).getUrl();
							String last = list.get(index).getUrlLast();
							AVAnalytics.onEvent(getActivity(), "banner");
							MobclickAgent.onEvent(getActivity(), "banner");

							if (url.contains("http")) {
								// Intent intent = new Intent(context,
								// WebAct.class);
								// intent.putExtra("data", url);
								// intent.putExtra("name", " ");
								// intent.putExtra("type", "banner");
								// startActivity(intent);

								Bundle bundle = new Bundle();
								Sharebean sharebean = new Sharebean();
								sharebean.setTitle("");
								sharebean.setContext("");
								sharebean.setAricleUrl(url);
								sharebean.setImgUrl(list.get(index).getImg());
								bundle.putSerializable(WebShareAct.class.getName(), sharebean);
								openActivity(WebShareAct.class, bundle);

							} else if (url.contains("entity")) {
								sendConnection(Constant.PROINFO + last + "/", new String[] { "entity_id" },
										new String[] { last }, PROINFO, true);
							} else if (url.contains("user_id")) {
								sendConnection(Constant.USERINFO + last + "/", new String[] {}, new String[] {},
										USERINFO, true);
							} else if (url.contains("category_id")) {
								for (Tab2Bean bean : list_cid) {
									if (bean.getCategory_id().equals(last)) {
										Intent intent = new Intent(context, TabAct.class);
										intent.putExtra("data", bean.getCategory_id());
										intent.putExtra("name", bean.getCategory_title());
										startActivity(intent);
									}
								}
							}
						}
					});
					imgs.add(image);
				}
				adapter.setList(imgs);
			} catch (JSONException e) {
				e.printStackTrace();
			}
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
				JSONObject root = new JSONObject(result);
				ArrayList<Categories> arrayList = new ArrayList<Categories>();
				arrayList = (ArrayList<Categories>) JSON.parseArray(root.getString("categories"), Categories.class);
				for (int i = 0; i < arrayList.size(); i++) {
					final ImageAddTextLayout imagTextLayout = new ImageAddTextLayout(getActivity());
					LayoutParams params = new LayoutParams(280, 280);
					imagTextLayout.setLayoutParams(params);
					imagTextLayout.setPadding(10, 0, 10, 0);
					imagTextLayout.imView.setImageURI(Uri.parse(arrayList.get(i).getCategory().getCover_url()));
					imagTextLayout.tView.setText(arrayList.get(i).getCategory().getTitle().trim().replace(" ", "\n"));
					imagTextLayout.setTag(arrayList.get(i).getCategory());
					// imagTextLayout.setTag(i);
					vpRecommendSort.addView(imagTextLayout);
					imagTextLayout.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Categories.Category category = (Category) arg0.getTag();
							Intent intent = new Intent(getActivity(), TabAct.class);
							intent.putExtra("data", category.getId());
							intent.putExtra("name", category.getTitle());
							getActivity().startActivity(intent);
						}
					});
				}

				/******** 热门图文 *********/
				Discover discover = JSON.parseObject(result, Discover.class);
				articlesAdapter.setList(discover.getArticles());

				/******** 热门商品 *********/
				try {
					gvAdapter.setList(discover.getEntities());
				} catch (Exception e) {
					// TODO: handle exception
				}

				sv.scrollTo(0, 0);
				sv.smoothScrollTo(0, 0);

			} catch (JSONException e) {
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

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init() {

		gvAdapter = new GuangShopAdapter(getActivity());

		faxian_gv.setAdapter(gvAdapter);

		faxian_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String id = String.valueOf(gvAdapter.getList().get(arg2).getEntity().getEntity_id());
				sendConnection(Constant.PROINFO + gvAdapter.getList().get(arg2).getEntity().getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { id }, PROINFO, true);

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

				Bundle bundle = new Bundle();
				Sharebean sharebean = new Sharebean();
				sharebean.setTitle(articlesAdapter.getList().get(arg2).getArticle().getTitle());
				sharebean.setContext(articlesAdapter.getList().get(arg2).getArticle().getContent().substring(0, 50));
				sharebean.setAricleUrl(articlesAdapter.getList().get(arg2).getArticle().getUrl());
				sharebean.setImgUrl(articlesAdapter.getList().get(arg2).getArticle().getCover());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);

				openActivity(WebShareAct.class, bundle);
			}
		});
	}

	@Override
	protected void setData() {
		list_cid = ParseUtil.getTab2List(context);
		sendConnection(Constant.FAXIANHOME, new String[] {}, new String[] {}, FAXIANHOME, false);
		sendConnection(Constant.DISCOVER, new String[] {}, new String[] {}, DISCOVER, false);
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
	private void onCleanView(View view){
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
	private void initSearchLog(){
		
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
			int touch_flag=0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				touch_flag++;
				if(touch_flag==2){
					edOnClick();
				}
				return false;
			}
		});
	}
	
	private void edOnClick(){
		
		if (searchLogAdapter != null) {
			viewLog.getBackground().setAlpha(230);
			if (viewLog.getVisibility() == View.INVISIBLE) {
				List<SearchLogBean> sBeans = SharePrenceUtil.getSearchRecord(getActivity());
				if (sBeans != null) {
					searchLogAdapter.setList((ArrayList<SearchLogBean>) sBeans);
					showSearchWhat();
				}
			}
		}
	}
	
	private void goSearchAct(String value){
		
		if(!StringUtils.isEmpty(value)){
			if (viewLog.getVisibility() == View.VISIBLE) {
				hideSearchWhat();
			}
			edSearch.getText().clear();
			GuokuUtil.hideKeyBoard(context);
			Intent intent = new Intent(context, SearchAct.class);
			intent.putExtra(GuangFragment.class.getName(), value);
			startActivity(intent);
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
}
