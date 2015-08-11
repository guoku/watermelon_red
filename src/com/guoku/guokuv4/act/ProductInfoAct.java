package com.guoku.guokuv4.act;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.trade.ItemService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.avos.avoscloud.AVAnalytics;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.customview.CustomShareBoard;
import com.ekwing.students.customview.ScrollViewWithGridView;
import com.ekwing.students.customview.ScrollViewWithListView;
import com.ekwing.students.utils.ArrayListAdapter;
import com.ekwing.students.utils.BitmapUtil;
import com.ekwing.students.utils.DateUtils;
import com.ekwing.students.utils.StringUtil;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.BroadUtil;
import com.guoku.guokuv4.utils.ImgUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.taobao.tae.sdk.model.TaokeParams;
import com.taobao.tae.sdk.webview.TaeWebViewUiSettings;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;

public class ProductInfoAct extends NetWorkActivity implements OnClickListener,
		DialogInterface.OnClickListener {
	private static final int GUESS = 10;
	private static final int PROINFO = 11;
	private static final int PY1 = 12;
	private static final int PY2 = 13;
	private static final int LIKE1 = 16;
	private static final int LIKE0 = 15;
	private static final int COMMENTLIST = 14;
	private static final int PROINFOFULL = 17;

	private PInfoBean productBean;

	@ViewInject(R.id.product_tv_name)
	private TextView product_tv_name;

	@ViewInject(R.id.tv_point)
	private TextView tv_point;

	// @ViewInject(R.id.product_tv_comment)
	// private TextView product_tv_comment;

	@ViewInject(R.id.product_vp_img)
	private ViewPager vp;

	private MyViewPagerAdapter adapter;

	@ViewInject(R.id.product_iv_like)
	private ImageView product_iv_like;
	@ViewInject(R.id.product_sv)
	private ScrollView sv;

	@ViewInject(R.id.product_tv_likes)
	private TextView product_tv_likes;

	@ViewInject(R.id.product_tv_like_size)
	private TextView product_tv_like_size;

	@ViewInject(R.id.product_tv_price)
	private TextView product_tv_price;

	@ViewInject(R.id.product_tv_from)
	private TextView product_tv_from;

	@ViewInject(R.id.product_gv_user)
	private ScrollViewWithGridView product_gv_user;

	@ViewInject(R.id.product_lv)
	private ScrollViewWithListView product_lv;

	@ViewInject(R.id.product_gv_product)
	private ScrollViewWithGridView product_gv_product;

	private ArrayListAdapter<UserBean> gv1Adapter;
	private ArrayListAdapter<EntityBean> gv2Adapter;
	private ArrayListAdapter<NoteBean> comAdapter;
	private ArrayList<Tab2Bean> tabList;
	private Tab2Bean curTab2Bean;

	private String root = "";
	// private ImgAdapter imgAdapter;
	private int currentItem;
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			vp.setCurrentItem(currentItem);// 切换当前显示的图片
			StringUtil.setTextColoPoint(tv_point, root, currentItem);
			// Log.e(TAG, "handler切换-->" + currentItem);
		}
	};
	private ScheduledExecutorService scheduledExecutorService;
	private boolean onTouchTrue;
	private NoteBean noteBean;
	private UMSocialService mController;
	private NoteBean myNoteBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_act);
		mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	}

	@Override
	protected void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5,
				TimeUnit.SECONDS);
		sendConnection(Constant.GUESS, new String[] { "count", "cid", "eid" },
				new String[] { "10", productBean.getEntity().getCategory_id(),
						productBean.getEntity().getEntity_id() }, GUESS, false);
	}

	@Override
	protected void onStop() {
		super.onStop();
		scheduledExecutorService.shutdown();
	}

	@Override
	protected void onSuccess(String result, int where) {
		switch (where) {
		case GUESS:
			ArrayList<EntityBean> list = ParseUtil.getGuessList(result);
			// Log.i(TAG, "GUESS-->" + list.toString());
			ArrayList<EntityBean> bufList = new ArrayList<EntityBean>();
			if (list.size() > 9) {
				for (int i = 0; i < 9; i++) {
					bufList.add(list.get(i));
				}
			}
			gv2Adapter.setList(bufList);
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case PROINFOFULL:
			productBean = ParseUtil.getPI(result);
			// setupData();
			gv1Adapter.setList(productBean.getLike_user_list());
			comAdapter.setList(productBean.getNote_list());

			break;

		case COMMENTLIST:
			intent = new Intent(mContext, CommentTalkAct.class);
			intent.putExtra("data", result);
			startActivity(intent);
			break;
		case LIKE0:
			if (productBean == null) {
				return;
			}
			// ToastUtil.show(context, "取消喜爱");
			productBean.getEntity().setLike_already("0");
			product_iv_like.setBackgroundResource(R.drawable.like_gary);
			product_tv_likes.setText("喜爱 "
					+ productBean.getEntity().getLike_countCut());
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY, Constant.INTENT_ACTION_VALUE_LIKE);
			break;
		case LIKE1:
			AVAnalytics.onEvent(this, "like");
			MobclickAgent.onEvent(this, "like");

			if (productBean == null) {
				return;
			}
			ToastUtil.show(context, "喜爱成功");
			productBean.getEntity().setLike_already("1");
			product_iv_like.setBackgroundResource(R.drawable.like_red);
			product_tv_likes.setText("喜爱 "
					+ productBean.getEntity().getLike_countAdd());
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY, Constant.INTENT_ACTION_VALUE_LIKE);
			break;
		case PY1:
			AVAnalytics.onEvent(this, "poke");
			MobclickAgent.onEvent(this, "poke");

			if (noteBean == null) {
				return;
			}
			noteBean.setPoke_already("1");
			noteBean.setPoke_countADD();
			comAdapter.notifyDataSetChanged();
			break;
		case PY2:
			if (noteBean == null) {
				return;
			}
			noteBean.setPoke_already("0");
			noteBean.setPoke_countCut();
			comAdapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		switch (where) {
		case LIKE0:
			ToastUtil.show(context, "取消失败");
			break;
		case LIKE1:
			ToastUtil.show(context, "喜爱失败");
			break;
		default:
			break;
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		onTouchTrue = false;
		Logger.w(TAG, "onRestart");
		sendConnection(Constant.PROINFO
				+ productBean.getEntity().getEntity_id() + "/",
				new String[] { "entity_id" }, new String[] { productBean
						.getEntity().getEntity_id() }, PROINFOFULL, true);

	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	//
	// Logger.w(TAG, "onResume");
	//
	// sendConnection(Constant.PROINFO
	// + productBean.getEntity().getEntity_id() + "/",
	// new String[] { "entity_id" }, new String[] { productBean
	// .getEntity().getEntity_id() }, PROINFOFULL, true);
	//
	// }

	@Override
	protected void setupData() {
		setGCenter(true, "商品");
		setGLeft(true, R.drawable.back_selector);
		setGRigth(true, R.drawable.more);

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				case 200:
					Intent intent = new Intent(mContext, EntityAct.class);
					String[] strs = ((String) msg.obj).split("&&");
					intent.putExtra("data", strs[1]);
					intent.putExtra("name", strs[0]);
					startActivity(intent);

					break;

				default:
					break;
				}
			}
		};

		productBean = JSON.parseObject(getIntent().getStringExtra("data"),
				PInfoBean.class);
		tabList = ParseUtil.getTab2ALL(mContext);
		for (Tab2Bean bean : tabList) {
			if (bean.getCategory_id().equals(
					productBean.getEntity().getCategory_id())) {
				curTab2Bean = bean;
				product_tv_from.setText("来自「" + bean.getCategory_title() + "」");
				break;
			}
		}
		if (productBean.getEntity().getBrand() != null
				&& !"".equals(productBean.getEntity().getBrand())) {
			product_tv_name.setText(productBean.getEntity().getBrand() + " - "
					+ productBean.getEntity().getTitle());
		} else
			product_tv_name.setText(productBean.getEntity().getTitle());
		// product_tv_likes.setText(productBean.getEntity().getLike_count());

		if (!productBean.getEntity().getLike_count().equals("")) {
			product_tv_likes.setText("喜爱 "
					+ productBean.getEntity().getLike_count());
			product_tv_like_size.setText(productBean.getEntity()
					.getLike_count() + " 人喜爱");
		}
		product_tv_price.setText("￥ " + productBean.getEntity().getPrice());
		// imageLoader.displayImage(productBean.getPic(), product_iv_pic);

		try {
			android.widget.RelativeLayout.LayoutParams param = new android.widget.RelativeLayout.LayoutParams(
					EkwingApplication.screenW - BitmapUtil.dip2px(mContext, 8),
					EkwingApplication.screenW - BitmapUtil.dip2px(mContext, 8));
			param.addRule(RelativeLayout.CENTER_IN_PARENT);
			vp.setLayoutParams(param);

			JSONArray thumbs = new JSONArray(productBean.getEntity()
					.getDetail_images());
			List<ImageView> imgs = new ArrayList<ImageView>();
			for (int i = 0; i < thumbs.length(); i++) {
				String url = thumbs.getString(i);
				if (url.contains("images")) {
					url = url.replaceFirst("images", "images/800");
				}
				Logger.i(TAG, "url-->" + url);
				final ImageView image = new ImageView(this);
				image.setTag(i);
				image.setScaleType(ScaleType.FIT_CENTER);
				imageLoader.displayImage(url, image, options,
						new ImgUtils.AnimateFirstDisplayListener());
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						gotoTaoBao();
					}
				});
				imgs.add(image);
			}
			Logger.i(TAG, "imgs-->" + imgs.size());
			// if (imgs.size() == 0) {
			final ImageView image = new ImageView(this);
			image.setScaleType(ScaleType.FIT_CENTER);
			imageLoader.displayImage(productBean.getEntity().get800(), image,
					new ImgUtils.AnimateFirstDisplayListener());
			image.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					gotoTaoBao();
				}
			});
			imgs.add(0, image);
			// }

			vp.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						Logger.i(TAG, "ACTION_DOWN");
						break;
					case MotionEvent.ACTION_UP:
						onTouchTrue = true;
						Logger.i(TAG, "ACTION_UP");

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
					StringUtil.setTextColoPoint(tv_point, root, currentItem);

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});
			if (imgs.size() > 1) {
				for (int i = 0; i < imgs.size(); i++) {
					root = root + "●";
				}
				tv_point.setText(root);
			}
			StringUtil.setTextColoPoint(tv_point, root, currentItem);
			adapter = new MyViewPagerAdapter(imgs);
			vp.setAdapter(adapter);
		} catch (Exception e) {
		}

		if ("1".equals(productBean.getEntity().getLike_already())) {
			product_iv_like.setBackgroundResource(R.drawable.like_red);
		} else
			product_iv_like.setBackgroundResource(R.drawable.like_gary);

		gv1Adapter = new ArrayListAdapter<UserBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = new ImageView(mContext);
					LayoutParams params = new LayoutParams(
							EkwingApplication.screenW / 7 - 25,
							EkwingApplication.screenW / 7 - 25);
					convertView.setLayoutParams(params);
					((ImageView) convertView).setScaleType(ScaleType.FIT_XY);
				}

				imageLoader.displayImage(mList.get(position).get50(),
						(ImageView) convertView, optionsRound,
						new ImgUtils.AnimateFirstDisplayListener());

				// BitmapUtil.setRoundImage(imageLoader, mList.get(position)
				// .getAvatar_small(), options, (ImageView) convertView);

				return convertView;
			}
		};

		gv2Adapter = new ArrayListAdapter<EntityBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = new ImageView(mContext);
					LayoutParams params = new LayoutParams(
							EkwingApplication.screenW / 3 - 30,
							EkwingApplication.screenW / 3 - 30);
					convertView.setLayoutParams(params);
					((ImageView) convertView)
							.setScaleType(ScaleType.FIT_CENTER);
					convertView.setBackgroundColor(Color.WHITE);
				}
				imageLoader.displayImage(mList.get(position).get240(),
						(ImageView) convertView, options,
						new ImgUtils.AnimateFirstDisplayListener());

				return convertView;
			}
		};
		comAdapter = new ArrayListAdapter<NoteBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(context, R.layout.comment_item,
							null);
					holder = new ViewHolder();
					ViewUtils.inject(holder, convertView);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				NoteBean bean = mList.get(position);

				imageLoader.displayImage(bean.getCreator().get50(),
						holder.comment_item_iv_pic, optionsRound,
						new ImgUtils.AnimateFirstDisplayListener());
				holder.comment_item_iv_pic.setTag(bean);
				holder.comment_item_iv_pic
						.setOnClickListener(ProductInfoAct.this);
				// BitmapUtil
				// .setRoundImage(imageLoader, bean.getCreator()
				// .getAvatar_small(), options,
				// holder.comment_item_iv_pic);

				holder.ll_like.setOnClickListener(ProductInfoAct.this);
				holder.ll_like.setTag(bean);
				if ("1".equals(bean.getPoke_already())) {
					holder.comment_item_iv_islike
							.setImageResource(R.drawable.good_press);
				} else {
					holder.comment_item_iv_islike
							.setImageResource(R.drawable.good);
				}

				if ("0".equals(bean.getIs_selected())) {
					holder.comment_item_iv_status.setVisibility(View.INVISIBLE);
				} else {
					holder.comment_item_iv_status.setVisibility(View.VISIBLE);
				}

				// if ("0".equals(bean.getComment_count())) {
				// holder.comment_item_iv_iscom
				// .setImageResource(R.drawable.comment);
				// } else {
				// holder.comment_item_iv_iscom
				// .setImageResource(R.drawable.comment_press);
				// }

				holder.comment_item_tv_name.setText(bean.getCreator()
						.getNickname());
				// holder.comment_item_tv_context.setText(bean.getContent());
				StringUtil.setTextColoPoint2(holder.comment_item_tv_context,
						bean.getContent(), mHandler, bean.getCreator()
								.getUser_id());
				holder.comment_item_tv_likes.setText(bean.getPoke_count());
				holder.comment_item_tv_coms.setText(bean.getComment_count());
				holder.comment_item_tv_time.setText(DateUtils
						.getStandardDate(bean.getUpdated_time()));
				return convertView;
			}
		};

		product_gv_user.setAdapter(gv1Adapter);
		product_gv_product.setAdapter(gv2Adapter);
		product_lv.setAdapter(comAdapter);

		// ````````````````````````````````````````````````````````````````````````
		gv1Adapter.setList(productBean.getLike_user_list());
		comAdapter.setList(productBean.getNote_list());
		if (EkwingApplication.getInstance().getBean() != null) {

			ArrayList<NoteBean> list = productBean.getNote_list();
			for (NoteBean bean : list) {
				if (bean.getCreator()
						.getUser_id()
						.equals(EkwingApplication.getInstance().getBean()
								.getUser().getUser_id())) {
					myNoteBean = bean;
					// product_tv_comment.setText("修改点评");
				}
			}
		}

		product_gv_user.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, UserAct.class);
				intent.putExtra("data",
						productBean.getLike_user_list().get(arg2));
				startActivity(intent);
			}
		});

		product_gv_product.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO
						+ gv2Adapter.getItem(arg2).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { gv2Adapter
								.getItem(arg2).getEntity_id() }, PROINFO, true);
			}
		});

		product_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (EkwingApplication.getInstance().getBean() != null) {
					sendConnection(Constant.COMMENTLIST
							+ productBean.getNote_list().get(arg2).getNote_id()
							+ "/", new String[] {}, new String[] {},
							COMMENTLIST, false);
				} else {
					startActivity(new Intent(mContext, LoginAct.class));
				}

			}
		});

	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void right(View v) {
		postShare(productBean.getEntity().getTitle(), new UMImage(this,
				productBean.getEntity().get240()), "", productBean.getEntity()
				.getEntity_id(), productBean);
	}

	private void postShare(String context, UMImage url, String id, String tid,
			PInfoBean productBean) {
		CustomShareBoard shareBoard = new CustomShareBoard(this);
		shareBoard.setShareContext(context, url, id, tid, productBean);
		shareBoard.showAtLocation(this.getWindow().getDecorView(),
				Gravity.BOTTOM, 0, 0);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.alpha = 0.7f;
		getWindow().setAttributes(params);

		shareBoard.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams params = getWindow().getAttributes();
				params.alpha = 1f;
				getWindow().setAttributes(params);

			}
		});

	}

	@OnClick(R.id.product_ll_like)
	public void Like(View v) {
		if (productBean.getEntity().getLike_already().equals("0")) {
			sendConnectionPOST(Constant.TOLIKE
					+ productBean.getEntity().getEntity_id() + "/like/1/",
					new String[] {}, new String[] {}, LIKE1, false);

		} else {
			sendConnectionPOST(Constant.TOLIKE
					+ productBean.getEntity().getEntity_id() + "/like/0/",
					new String[] {}, new String[] {}, LIKE0, false);

		}
	}

	@OnClick(R.id.product_ll_like_2)
	public void Like_2(View v) {
		// sendConnectionPOST(Constant.TOLIKE
		// + productBean.getEntity().getEntity_id() + "/like/",
		// new String[] {}, new String[] {}, LIKE1, false);
		Intent intent = new Intent(mContext, LikesAct.class);
		intent.putExtra("data", productBean.getEntity().getEntity_id());
		startActivity(intent);
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
			}
		}
	}

	@OnClick(R.id.product_ll_price)
	public void Price(View v) {
		gotoTaoBao();
	}

	private void gotoTaoBao() {
		AVAnalytics.onEvent(this, "buy");
		MobclickAgent.onEvent(this, "buy");

		try {
			JSONArray array;
			array = new JSONArray(productBean.getEntity().getItem_list());
			if (!(array.getJSONObject(0).getString("origin_source")
					.contains("taobao") || array.getJSONObject(0)
					.getString("origin_source").contains("tmall"))) {
				Intent intent = new Intent(context, WebAct.class);
				intent.putExtra("data",
						array.getJSONObject(0).getString("buy_link"));
				intent.putExtra("name", "  ");
				intent.putExtra("UA", "UA");
				startActivity(intent);
				return;

			}
			TaeWebViewUiSettings taeWebViewUiSettings = new TaeWebViewUiSettings();
			TaokeParams taokeParams = new TaokeParams();
			taokeParams.pid = "mm_28514026_4132785_24810648";
			taokeParams.unionId = "null";
			ItemService itemService = AlibabaSDK.getService(ItemService.class);
			if (itemService == null) {
				ToastUtil.show(mContext, "淘宝链接失败");
				return;
			}
			itemService.showTaokeItemDetailByItemId(
					this,
					new TradeProcessCallback() {

						@Override
						public void onPaySuccess(TradeResult tradeResult) {
							// Toast.makeText(MainActivity.this, "支付成功",
							// Toast.LENGTH_SHORT).show();
							ToastUtil.show(mContext, "支付成功");

						}

						@Override
						public void onFailure(int code, String msg) {
							if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
								// Toast.makeText(MainActivity.this, "确认交易订单失败",
								// Toast.LENGTH_SHORT).show();
								ToastUtil.show(mContext, "确认交易订单失败");

							} else {
								ToastUtil.show(mContext, "交易取消");

								// Toast.makeText(MainActivity.this, "交易取消",
								// Toast.LENGTH_SHORT).show();
							}
						}

					}, taeWebViewUiSettings,
					array.getJSONObject(0).getLong("origin_id"), 1, null,
					taokeParams);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@OnClick(R.id.product_tv_comment)
	public void Comment(View v) {
		if (EkwingApplication.getInstance().getBean() != null) {
			Intent intent = new Intent(mContext, CommentAct.class);
			intent.putExtra("data", JSON.toJSONString(productBean));
			if (myNoteBean != null) {
				intent.putExtra("noteid", myNoteBean.getNote_id());
			}
			startActivityForResult(intent, 10086);
		} else {
			startActivity(new Intent(mContext, LoginAct.class));
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(arg0);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(arg0, arg1, arg2);
		}
		switch (arg0) {
		case 10086:
			if (arg2 != null && arg2.getStringExtra("data") != null
					&& !"".equals(arg2.getStringExtra("data"))) {
				NoteBean bean = JSON.parseObject(arg2.getStringExtra("data"),
						NoteBean.class);
				productBean.getNote_list().add(bean);
				comAdapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}

	}

	@OnClick(R.id.product_ll_tab)
	public void Tab(View v) {
		Intent intent = new Intent(mContext, TabAct.class);
		intent.putExtra("data", curTab2Bean.getCategory_id());
		intent.putExtra("name", curTab2Bean.getCategory_title());
		mContext.startActivity(intent);
	}

	private class ViewHolder {

		@ViewInject(R.id.comment_item_iv_pic)
		private ImageView comment_item_iv_pic;

		@ViewInject(R.id.comment_item_iv_status)
		private ImageView comment_item_iv_status;

		@ViewInject(R.id.comment_item_ll_islike)
		private LinearLayout ll_like;

		@ViewInject(R.id.comment_item_iv_islike)
		private ImageView comment_item_iv_islike;

		@ViewInject(R.id.comment_item_iv_select)
		private ImageView comment_item_iv_select;

		@ViewInject(R.id.comment_item_iv_iscom)
		private ImageView comment_item_iv_iscom;

		@ViewInject(R.id.comment_item_tv_name)
		private TextView comment_item_tv_name;

		@ViewInject(R.id.comment_item_tv_context)
		private TextView comment_item_tv_context;

		@ViewInject(R.id.comment_item_tv_likes)
		private TextView comment_item_tv_likes;

		@ViewInject(R.id.comment_item_tv_coms)
		private TextView comment_item_tv_coms;

		@ViewInject(R.id.comment_item_tv_time)
		private TextView comment_item_tv_time;

	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<ImageView> mListViews;

		public MyViewPagerAdapter(List<ImageView> mListViews) {
			this.mListViews = mListViews;// 构造方法，参数是我们的页卡，这样比较方便。
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
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.comment_item_ll_islike:
			noteBean = (NoteBean) arg0.getTag();
			if (noteBean.getPoke_already().equals("0")) {
				sendConnectionPOST(Constant.TOPY + noteBean.getEntity_id()
						+ "/poke/1/", new String[] {}, new String[] {}, PY1,
						false);
			} else {
				sendConnectionPOST(Constant.TOPY + noteBean.getEntity_id()
						+ "/poke/0/", new String[] {}, new String[] {}, PY2,
						false);
			}
			comAdapter.notifyDataSetChanged();
			break;
		case R.id.comment_item_iv_pic:
			NoteBean noteBean = (NoteBean) arg0.getTag();
			Intent intent = new Intent(mContext, UserAct.class);
			intent.putExtra("data", noteBean.getCreator());
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg1) {
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;
		case 3:
			Intent intent = new Intent(this, HexieAct.class);
			intent.putExtra("data", productBean.getEntity().getEntity_id());
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
