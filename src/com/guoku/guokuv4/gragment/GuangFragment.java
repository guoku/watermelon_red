package com.guoku.guokuv4.gragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
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
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.ArrayListAdapter;
import com.guoku.R;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.SeachAct;
import com.guoku.guokuv4.act.TabAct;
import com.guoku.guokuv4.act.UserAct;
import com.guoku.guokuv4.act.WebAct;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.Categories;
import com.guoku.guokuv4.bean.Categories.Category;
import com.guoku.guokuv4.entity.test.BannerBean;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.ImgUtils;
import com.guoku.guokuv4.view.ImageAddTextLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;

public class GuangFragment extends BaseFrament {
	private static final int HOT = 10;
	private static final int PROINFO = 12;
	private static final int FAXIANHOME = 13;
	private static final int USERINFO = 14;
	private static final int DISCOVER = 15;// 推荐品类

	@ViewInject(R.id.user_et_name)
	private TextView user_et_name;

	@ViewInject(R.id.product_vp_img)
	private ViewPager vp;

	@ViewInject(R.id.faxian_sv)
	private ScrollView sv;

	@ViewInject(R.id.gallery_recommend_sort)
	private LinearLayout vpRecommendSort;// 推荐品类

	private ScheduledExecutorService scheduledExecutorService;
	private boolean onTouchTrue;
	private MyViewPagerAdapter adapter;

	@ViewInject(R.id.faxian_gv)
	private GridView faxian_gv;

	private ArrayListAdapter<EntityBean> gvAdapter;

	private DisplayImageOptions options;
	private DisplayImageOptions optionsRound;// 圆角的
	private ArrayList<EntityBean> hotList;
	private ArrayList<BannerBean> list;
	private ArrayList<Tab2Bean> list_cid;

	private ArrayList<EntityBean> discover;// 分类

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
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5,
				TimeUnit.SECONDS);
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
		case HOT:
			try {
				JSONObject root = new JSONObject(result);
				JSONArray array = root.getJSONArray("content");
				hotList = new ArrayList<EntityBean>();
				for (int i = 0; i < array.length(); i++) {
					hotList.add(JSON.parseObject(array.getJSONObject(i)
							.getString("entity"), EntityBean.class));
				}
				gvAdapter.setList(hotList);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case FAXIANHOME:
			try {
				JSONObject root = new JSONObject(result);
				list = (ArrayList<BannerBean>) JSON.parseArray(
						root.getString("banner"), BannerBean.class);
				List<ImageView> imgs = new ArrayList<ImageView>();
				for (int i = 0; i < list.size(); i++) {
					final ImageView image = new ImageView(getActivity());
					image.setTag(i);
					image.setScaleType(ScaleType.FIT_XY);
					imageLoader
							.displayImage(list.get(i).getImg(), image, options,
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
								Intent intent = new Intent(context,
										WebAct.class);
								intent.putExtra("data", url);
								intent.putExtra("name", "  ");
								intent.putExtra("type", "banner");
								startActivity(intent);
							} else if (url.contains("entity")) {
								sendConnection(Constant.PROINFO + last + "/",
										new String[] { "entity_id" },
										new String[] { last }, PROINFO, true);
							} else if (url.contains("user_id")) {
								sendConnection(Constant.USERINFO + last + "/",
										new String[] {}, new String[] {},
										USERINFO, true);
							} else if (url.contains("category_id")) {
								for (Tab2Bean bean : list_cid) {
									if (bean.getCategory_id().equals(last)) {
										Intent intent = new Intent(context,
												TabAct.class);
										intent.putExtra("data",
												bean.getCategory_id());
										intent.putExtra("name",
												bean.getCategory_title());
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
				UserBean userBean = (UserBean) JSON.parseArray(
						root.getString("user"), UserBean.class);
				intent = new Intent(context, UserAct.class);
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
				arrayList = (ArrayList<Categories>) JSON.parseArray(
						root.getString("categories"), Categories.class);
				for (int i = 0; i < arrayList.size(); i++) {
					final ImageAddTextLayout imagTextLayout = new ImageAddTextLayout(getActivity());
					LayoutParams params = new LayoutParams(280, 280);
					imagTextLayout.setLayoutParams(params);
					imagTextLayout.setPadding(10, 0, 10, 0);
					imageLoader.displayImage(arrayList.get(i).getCategory()
							.getCover_url(), imagTextLayout.imView, optionsRound,
							new ImgUtils.AnimateFirstDisplayListener());
					imagTextLayout.tView.setText(arrayList.get(i).getCategory().getTitle().trim().replace(" ", "\n"));
					imagTextLayout.setTag(arrayList.get(i).getCategory());
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

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			sv.scrollTo(0, 0);
			break;
		}

	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init() {

		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.drawable.item240)
				.showImageForEmptyUri(R.drawable.item240)
				.showImageOnFail(R.drawable.item240).build();

		optionsRound = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.drawable.item240)
				.showImageForEmptyUri(R.drawable.item240)
				.showImageOnFail(R.drawable.item240)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		gvAdapter = new ArrayListAdapter<EntityBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if (convertView == null) {
					convertView = new ImageView(mContext);
					LayoutParams params = new LayoutParams(
							EkwingApplication.screenW / 3 - 10,
							EkwingApplication.screenW / 3 - 10);
					convertView.setLayoutParams(params);
					((ImageView) convertView).setScaleType(ScaleType.FIT_XY);
					convertView.setBackgroundColor(Color.WHITE);
				}
				imageLoader.displayImage(mList.get(position).get240(),
						(ImageView) convertView, options,
						new ImgUtils.AnimateFirstDisplayListener());

				return convertView;
			}
		};

		hotList = new ArrayList<EntityBean>();

		faxian_gv.setAdapter(gvAdapter);

		faxian_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO
						+ hotList.get(arg2).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { hotList
								.get(arg2).getEntity_id() }, PROINFO, true);

			}
		});

		try {
			android.widget.RelativeLayout.LayoutParams param = new android.widget.RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					(EkwingApplication.screenW) * 472 / 1028);
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

		} catch (Exception e) {
		}

	}

	@Override
	protected void setData() {
		list_cid = ParseUtil.getTab2List(context);
		sendConnection(Constant.HOT, new String[] {}, new String[] {}, HOT,
				false);
		sendConnection(Constant.FAXIANHOME, new String[] {}, new String[] {},
				FAXIANHOME, false);
		sendConnection(Constant.DISCOVER, new String[] {}, new String[] {},
				DISCOVER, false);

	}

	@OnClick(R.id.user_et_name)
	public void SQR(View v) {
		startActivity(new Intent(context, SeachAct.class));

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
}
