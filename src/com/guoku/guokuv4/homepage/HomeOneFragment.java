package com.guoku.guokuv4.homepage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Constant;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.act.WebAct;
import com.guoku.guokuv4.adapter.HomeOneArticlesAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.HomePageOneBean;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

public class HomeOneFragment extends BaseFrament {

	private static final int HOME = 1001;// 图文banner

	@ViewInject(R.id.product_vp_img)
	private ViewPager vPagerBaner;

	@ViewInject(R.id.listView_article)
	private ListView lvArticle;// 文章

	HomePageOneBean bean;

	HomeOneArticlesAdapter articlesAdapter;

	private ScheduledExecutorService scheduledExecutorService;
	private boolean onTouchTrue;
	private MyViewPagerAdapter adapter;
	private int currentItem;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			vPagerBaner.setCurrentItem(currentItem);
		}
	};

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		initbaner();
		initArticle();
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fargment_one;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case HOME:
			setResult(result);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		sendConnection(Constant.HOME, new String[] {}, new String[] {}, HOME,
				false);
	}

	private void initArticle() {
		articlesAdapter = new HomeOneArticlesAdapter(getActivity());
		lvArticle.setAdapter(articlesAdapter);
	}

	private void initbaner() {

		try {
			android.widget.RelativeLayout.LayoutParams param = new android.widget.RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					(EkwingApplication.screenW) * 472 / 1028);
			vPagerBaner.setLayoutParams(param);

			vPagerBaner.setOnTouchListener(new OnTouchListener() {
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
			vPagerBaner.setOnPageChangeListener(new OnPageChangeListener() {
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
			vPagerBaner.setAdapter(adapter);
		} catch (Exception e) {
		}
	}

	/**
	 * 换行切换任务
	 */
	private class ScrollTask implements Runnable {
		public void run() {
			synchronized (vPagerBaner) {
				if (!onTouchTrue) {
					currentItem = (currentItem + 1) % adapter.getCount();
					handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
				}
				// Log.e(TAG, "ScrollTask切换-->" + currentItem);
			}
		}
	}

	private void setResult(String result) {

		try {
			bean = JSON.parseObject(result, HomePageOneBean.class);
			List<SimpleDraweeView> imgs = new ArrayList<SimpleDraweeView>();

			GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(
					getResources());

			for (int i = 0; i < bean.getBanner().size(); i++) {
				final SimpleDraweeView image = new SimpleDraweeView(
						getActivity());
				GenericDraweeHierarchy hierarchy = builder.setPlaceholderImage(
						getResources().getDrawable(R.drawable.item800)).build();
				image.setHierarchy(hierarchy);
				image.setTag(i);
				image.setScaleType(ScaleType.FIT_XY);
				image.setImageURI(Uri.parse(bean.getBanner().get(i).getImg()));
				image.setLayoutParams(new LayoutParams(200, 200));
				image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						int index = (Integer) arg0.getTag();
						String url = bean.getBanner().get(index).getUrl();
						AVAnalytics.onEvent(getActivity(), "banner");
						MobclickAgent.onEvent(getActivity(), "banner");

						if (url.contains("http")) {
							Intent intent = new Intent(context, WebAct.class);
							intent.putExtra("data", url);
							intent.putExtra("name", "  ");
							intent.putExtra("type", "banner");
							startActivity(intent);
						}
					}
				});
				imgs.add(image);
			}
			adapter.setList(imgs);
			articlesAdapter.setList(bean.getArticles());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5,
				TimeUnit.SECONDS);
	}

	@Override
	public void onStop() {
		super.onStop();
		scheduledExecutorService.shutdown();
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<SimpleDraweeView> mListViews;

		public MyViewPagerAdapter() {
			this.mListViews = new ArrayList<SimpleDraweeView>();
		}

		public void setList(List<SimpleDraweeView> list) {
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
			SimpleDraweeView sView = mListViews.get(position);
			// GenericDraweeHierarchy hierarchy = sView.getHierarchy();
			// hierarchy.setPlaceholderImage(getResources().getDrawable(R.drawable.item800));
			// hierarchy.setPlaceholderImage(R.drawable.ic_logo);
			// sView.setHierarchy(hierarchy);
			return sView;
		}

		@Override
		public int getCount() {
			return mListViews.size();// 返回页卡的数量
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public float getPageWidth(int position) {
			// TODO Auto-generated method stub
			return super.getPageWidth(position);
		}
	}
}