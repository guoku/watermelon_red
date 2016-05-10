package com.guoku.guokuv4.act;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.adapter.ArrayListAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.bean.CommentsBean;
import com.guoku.guokuv4.bean.LikesBean;
import com.guoku.guokuv4.bean.TagBean;
import com.guoku.guokuv4.bean.TagTwo;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.share.CustomShareBoard;
import com.guoku.guokuv4.utils.DateUtils;
import com.guoku.guokuv4.utils.ImgUtils;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.StringUtils.OnNoteTag;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.MyScrollView;
import com.guoku.guokuv4.view.MyScrollView.OnScrollListener;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.guoku.guokuv4.view.ScrollViewWithListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.media.UMImage;

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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

public class ProductInfoAct extends NetWorkActivity
		implements OnClickListener, DialogInterface.OnClickListener, OnScrollListener {
	public static final String KEY_INTENT = "ProductInfoAct";

	private static final int GUESS = 10;
	private static final int PROINFO = 11;
	private static final int PY1 = 12;
	private static final int PY2 = 13;
	private static final int LIKE1 = 16;
	private static final int LIKE0 = 15;
	private static final int COMMENTLIST = 14;
//	private static final int PROINFOFULL = 17;
	private static final int NOTE_TAG = 18;// 标签
	private static final int USERINFO = 1001;// 用户信息
	private static final int PROINFO_REFRESH = 1002;// 刷新商品

	private PInfoBean productBean;

	@ViewInject(R.id.product_tv_name)
	private TextView product_tv_name;

	@ViewInject(R.id.tv_point)
	private TextView tv_point;

	@ViewInject(R.id.product_vp_img)
	private ViewPager vp;

	private MyViewPagerAdapter adapter;

	@ViewInject(R.id.product_iv_like)
	private ImageView product_iv_like;

	@ViewInject(R.id.product_sv)
	private MyScrollView sv;

	@ViewInject(R.id.product_tv_like_size)
	private TextView product_tv_like_size;

	@ViewInject(R.id.product_iv_likes)
	private ImageView cbliks;// title上的喜欢按钮

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

	@ViewInject(R.id.title_bar_left_iv)
	private ImageView left;

	@ViewInject(R.id.layout2)
	LinearLayout view2;// 价格按钮layout

	@ViewInject(R.id.layout3)
	LinearLayout view3;// 价格按钮layout

	@ViewInject(R.id.layout_more)
	LinearLayout viewMore;

	@ViewInject(R.id.product_ll_like_2)
	LinearLayout layout_like;// 多少人喜爱layout

	int priceBtTop;// 记录价格按钮顶部的位置

	private ArrayListAdapter<UserBean> gv1Adapter;
	private ArrayListAdapter<EntityBean> gv2Adapter;
	private ArrayListAdapter<NoteBean> comAdapter;

	private int isLike;

	private String root = "";
	// private ImgAdapter imgAdapter;
	private int currentItem;
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			vp.setCurrentItem(currentItem);// 切换当前显示的图片
			StringUtils.setTextColoPoint(tv_point, root, currentItem);
			// Log.e(TAG, "handler切换-->" + currentItem);
		}
	};
	private ScheduledExecutorService scheduledExecutorService;
	private boolean onTouchTrue;
	private NoteBean noteBean;

	private NoteBean myNoteBean;
	String checkId;

	private Animation animationIn;
	private Animation animationOut;

	TagTwo tagTwo;// 二级品类

	View lineView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
		setContentView(R.layout.product_act);
		lineView = View.inflate(mContext, R.layout.line_gray, null);
		init();
	}

	private void init() {
		sv.setOnScrollListener(this);
		isLikes();
	}

	private void isLikes() {

		if ("1".equals(productBean.getEntity().getLike_already())) {
			product_iv_like.setImageResource(R.drawable.like_red);
			cbliks.setImageResource(R.drawable.like_red);
		} else {
			product_iv_like.setImageResource(R.drawable.like_gary);
			cbliks.setImageResource(R.drawable.like_gary);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 5, 5, TimeUnit.SECONDS);
		sendConnection(Constant.GUESS, new String[] { "count", "cid", "eid" },
				new String[] { "10", productBean.getEntity().getCategory_id(), productBean.getEntity().getEntity_id() },
				GUESS, false);
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
		case COMMENTLIST:
			intent = new Intent(mContext, CommentTalkAct.class);
			intent.putExtra("data", result);
			startActivity(intent);
			break;
		case LIKE0:
			EventBus.getDefault().post(new LikesBean(false));
			break;
		case LIKE1:
			AVAnalytics.onEvent(this, "like");
			MobclickAgent.onEvent(this, "like");
			EventBus.getDefault().post(new LikesBean(true));
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
		case NOTE_TAG:
			break;
		case USERINFO:
			try {
				JSONObject root = new JSONObject(result);
				UserBean userBean = JSON.parseObject(root.getString("user"), UserBean.class);
				intent = new Intent(context, UserBaseFrament.class);
				intent.putExtra("data", userBean);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case PROINFO_REFRESH:
			try {
				productBean = JSON.parseObject(result, PInfoBean.class);
				refresh();
				onScroll(0);
				gv2Adapter.notifyDataSetChanged();
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		switch (where) {
		case LIKE0:
			ToastUtil.show(context, "取消喜爱失败");
			break;
		case LIKE1:
			ToastUtil.show(context, "喜爱失败");
			break;
		default:
			break;
		}
	}

	@Override
	protected void setupData() {
		setGCenter(true, "商品");
		setGLeft(true, R.drawable.back_selector);
		setGRigth(false, 0);

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
		productBean = JSON.parseObject(getIntent().getStringExtra("data"), PInfoBean.class);
		refresh();
	}
	
	private void refreshLikeLayout(){
		
		if (!productBean.getEntity().getLike_count().equals("")) {
			product_tv_like_size.setText(productBean.getEntity().getLike_count() + " 人喜爱");
//			if(gv1Adapter != null){
//				gv1Adapter.notifyDataSetChanged();
//			}
		} else {
			layout_like.setVisibility(View.GONE);
		}
	}

	private void refresh() {

		try {
			String result = SharePrenceUtil.getTab(mContext);
			if (!StringUtils.isEmpty(result)) {
				ArrayList<TagBean> tBean = (ArrayList<TagBean>) JSON.parseArray(result, TagBean.class);
				for (TagBean tBeant : tBean) {
					for (TagTwo tagTwos : tBeant.getContent()) {
						if (String.valueOf(tagTwos.getCategory_id()).equals(productBean.getEntity().getCategory_id())) {
							product_tv_from.setText("来自「" + tagTwos.getCategory_title() + "」");
							tagTwo = tagTwos;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (productBean.getEntity().getBrand() != null && !"".equals(productBean.getEntity().getBrand())) {
			product_tv_name.setText(productBean.getEntity().getBrand() + " - " + productBean.getEntity().getTitle());
		} else
			product_tv_name.setText(productBean.getEntity().getTitle());
		// product_tv_likes.setText(productBean.getEntity().getLike_count());

		refreshLikeLayout();
		
		product_tv_price
				.setText(getResources().getString(R.string.tv_commodity_go_buy, productBean.getEntity().getPrice()));
		// imageLoader.displayImage(productBean.getPic(), product_iv_pic);

		try {
			android.widget.RelativeLayout.LayoutParams param = new android.widget.RelativeLayout.LayoutParams(
					GuokuApplication.screenW - 10, GuokuApplication.screenW - 10);
			param.addRule(RelativeLayout.CENTER_IN_PARENT);
			vp.setLayoutParams(param);
			sv.smoothScrollTo(0, 0);

			JSONArray thumbs = new JSONArray(productBean.getEntity().getDetail_images());
//			if(thumbs.length() == 0){
				thumbs.put(productBean.getEntity().getChief_image());
//			}
			List<ImageView> imgs = new ArrayList<ImageView>();
			for (int i = thumbs.length() - 1; i >= 0; i--) {
				String url = thumbs.getString(i);
				if (url.contains("images")) {
					url = url.replaceFirst("images", "images/800");
				}
				Logger.i(TAG, "url-->" + url);
				final ImageView image = new ImageView(this);
				image.setTag(i);
				image.setScaleType(ScaleType.FIT_CENTER);
				imageLoader.displayImage(url, image, options, new ImgUtils.AnimateFirstDisplayListener());
				image.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
//						gotoTaoBao(productBean, 0);
						
						Bundle bundle = new Bundle();
						bundle.putSerializable(PhotoViewAct.class.getName(), productBean);
						bundle.putInt(PhotoViewAct.KEY_ITEM, (Integer) image.getTag());
						openActivity(PhotoViewAct.class, bundle);
					}
				});
				imgs.add(image);
			}

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
					StringUtils.setTextColoPoint(tv_point, root, currentItem);

				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
				}
			});
			if (imgs.size() > 1) {
				root = "";
				for (int i = 0; i < imgs.size(); i++) {
					root = root + "●";
				}
				tv_point.setText(root);
			}
			StringUtils.setTextColoPoint(tv_point, root, currentItem);
			adapter = new MyViewPagerAdapter(imgs);
			vp.setAdapter(adapter);
		} catch (Exception e) {
		}

		isLikes();

		gv1Adapter = new ArrayListAdapter<UserBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = new ImageView(mContext);
					LayoutParams params = new LayoutParams(GuokuApplication.screenW / 7 - 25,
							GuokuApplication.screenW / 7 - 25);
					convertView.setLayoutParams(params);
					((ImageView) convertView).setScaleType(ScaleType.FIT_XY);
				}

				imageLoader.displayImage(mList.get(position).get50(), (ImageView) convertView, optionsRound,
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
					LayoutParams params = new LayoutParams(GuokuApplication.screenW / 3 - 30,
							GuokuApplication.screenW / 3 - 30);
					convertView.setLayoutParams(params);
					((ImageView) convertView).setScaleType(ScaleType.FIT_CENTER);
					convertView.setBackgroundColor(Color.WHITE);
				}
				imageLoader.displayImage(mList.get(position).get240(), (ImageView) convertView, options,
						new ImgUtils.AnimateFirstDisplayListener());

				return convertView;
			}
		};
		comAdapter = new ArrayListAdapter<NoteBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(context, R.layout.comment_item, null);
					holder = new ViewHolder();
					ViewUtils.inject(holder, convertView);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				NoteBean bean = mList.get(position);

				imageLoader.displayImage(bean.getCreator().get50(), holder.comment_item_iv_pic, optionsRound,
						new ImgUtils.AnimateFirstDisplayListener());
				holder.comment_item_iv_pic.setTag(bean);
				holder.comment_item_iv_pic.setOnClickListener(ProductInfoAct.this);
				// BitmapUtil
				// .setRoundImage(imageLoader, bean.getCreator()
				// .getAvatar_small(), options,
				// holder.comment_item_iv_pic);

				holder.ll_like.setOnClickListener(ProductInfoAct.this);
				holder.ll_like.setTag(bean);
				if ("1".equals(bean.getPoke_already())) {
					holder.comment_item_iv_islike.setImageResource(R.drawable.good_press);
				} else {
					holder.comment_item_iv_islike.setImageResource(R.drawable.good);
				}

				if ("0".equals(bean.getIs_selected())) {
					holder.comment_item_iv_status.setVisibility(View.INVISIBLE);
				} else {
					holder.comment_item_iv_status.setVisibility(View.VISIBLE);
				}

				holder.comment_item_tv_name.setText(bean.getCreator().getNick());
				holder.comment_item_tv_context.setText(bean.getContent());
				holder.comment_item_tv_likes.setText(bean.getPoke_count());
				holder.comment_item_tv_coms.setText(bean.getComment_count());
				holder.comment_item_tv_time.setText(DateUtils.getStandardDate(bean.getUpdated_time()));

				StringUtils.setNoteTag(mContext, bean.getContent(), holder.comment_item_tv_context, new OnNoteTag() {

					@Override
					public void setTagClick(String tagName) {
						// TODO Auto-generated method stub

						if (GuokuApplication.getInstance().getBean() == null) {
							openLogin();
						} else {
							tagName = tagName.trim().replace("#", "");
							Intent intent = new Intent(ProductInfoAct.this, EntityAct.class);
							intent.putExtra("data", GuokuApplication.getInstance().getBean().getUser().getUser_id());
							intent.putExtra("name", tagName);
							startActivity(intent);
						}
					}
				});
				return convertView;
			}
		};

		product_gv_user.setAdapter(gv1Adapter);
		product_gv_product.setAdapter(gv2Adapter);
		product_lv.setAdapter(comAdapter);

		// ````````````````````````````````````````````````````````````````````````
		gv1Adapter.setList(productBean.getLike_user_list());
		comAdapter.setList(productBean.getNote_list());
		if (GuokuApplication.getInstance().getBean() != null) {

			ArrayList<NoteBean> list = productBean.getNote_list();
			for (NoteBean bean : list) {
				if (bean.getCreator().getUser_id()
						.equals(GuokuApplication.getInstance().getBean().getUser().getUser_id())) {
					myNoteBean = bean;
					// product_tv_comment.setText("修改点评");
				}
			}
		}

		product_gv_user.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// Intent intent = new Intent(mContext, UserAct.class);
				// intent.putExtra("data",
				// productBean.getLike_user_list().get(arg2));
				// startActivity(intent);

				sendConnection(Constant.USERINFO + gv1Adapter.getItem(arg2).getUser_id() + "/", new String[] {},
						new String[] {}, USERINFO, true);
			}
		});

		product_gv_product.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				checkId = gv2Adapter.getItem(arg2).getEntity_id();
				sendConnection(Constant.PROINFO + gv2Adapter.getItem(arg2).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { gv2Adapter.getItem(arg2).getEntity_id() }, PROINFO,
						true);
			}
		});

		product_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (GuokuApplication.getInstance().getBean() != null) {
					sendConnection(Constant.COMMENTLIST + productBean.getNote_list().get(arg2).getNote_id() + "/",
							new String[] {}, new String[] {}, COMMENTLIST, false);
				} else {
					openLogin();
				}

			}
		});

	}

	@OnClick(R.id.product_tv_more)
	public void more(View v) {
		moreClick();
	}

	@OnClick(R.id.product_tv_mores)
	public void mores(View v) {
		moreClick();
	}

	private void moreClick() {
		postShare(productBean.getEntity().getTitle(), new UMImage(this, productBean.getEntity().get240()), "",
				productBean.getEntity().getEntity_id(), productBean);
	}

	private void postShare(String context, UMImage url, String id, String tid, final PInfoBean productBean) {
		final CustomShareBoard shareBoard = new CustomShareBoard(this);
		shareBoard.setShareContext(context, url, id, tid, productBean);
		shareBoard.setAnimationStyle(R.style.popwin_anim_style);
		shareBoard.showAtLocation(this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.alpha = 0.7f;
		getWindow().setAttributes(params);

		shareBoard.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams params = getWindow().getAttributes();
				params.alpha = 1f;
				getWindow().setAttributes(params);
				if (isRefrech) {
					sendConnection(Constant.PROINFO + productBean.getEntity().getEntity_id() + "/",
							new String[] { "entity_id" }, new String[] { productBean.getEntity().getEntity_id() },
							PROINFO_REFRESH, true);

					sendConnection(Constant.GUESS,
							new String[] { "count", "cid", "eid" }, new String[] { "10",
									productBean.getEntity().getCategory_id(), productBean.getEntity().getEntity_id() },
							GUESS, false);
				}
			}
		});

	}

	@OnClick(R.id.product_iv_like)
	public void Like(View v) {
		likeClick();
	}
	
	@OnClick(R.id.product_iv_likes)
	public void LikeTitleBar(View v) {
		likeClick();
	}

	private void likeClick() {

		if (productBean.getEntity().getLike_already().equals("0")) {
			
			umStatistics(Constant.UM_SHOP_LIKE, productBean.getEntity().getEntity_id(), productBean.getEntity().getTitle());
			sendConnectionPOST(Constant.TOLIKE + productBean.getEntity().getEntity_id() + "/like/1/", new String[] {},
					new String[] {}, LIKE1, false);

		} else {
			
			umStatistics(Constant.UM_SHOP_LIKE_UN, productBean.getEntity().getEntity_id(), productBean.getEntity().getTitle());
			sendConnectionPOST(Constant.TOLIKE + productBean.getEntity().getEntity_id() + "/like/0/", new String[] {},
					new String[] {}, LIKE0, false);
		}
	}

	@OnClick(R.id.product_ll_like_2)
	public void Like_2(View v) {
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

	@OnClick(R.id.product_tv_price)
	public void Price(View v) {
		gotoTaoBao(productBean, 0);
	}

	@OnClick(R.id.product_tv_comment)
	public void Comment(View v) {
		setComments();
	}

	@OnClick(R.id.product_tv_comments)
	public void Comments(View v) {
		setComments();
	}

	private void setComments() {
		if (GuokuApplication.getInstance().getBean() != null) {
			Intent intent = new Intent(mContext, CommentAct.class);
			intent.putExtra("data", JSON.toJSONString(productBean));
			if (myNoteBean != null) {
				intent.putExtra("noteid", myNoteBean.getNote_id());
			}
			startActivity(intent);
		} else {
			startActivity(new Intent(mContext, LoginAct.class));
		}
	}
	
	public void onEventMainThread(CommentsBean commentsBean) {
		try {
			myNoteBean = JSON.parseObject(commentsBean.getData(), NoteBean.class);
			if(!commentsBean.isAdd){
				for (int i = 0; i < productBean.getNote_list().size(); i++) {
					if (myNoteBean.getNote_id().equals(productBean.getNote_list().get(i).getNote_id())) {
						productBean.getNote_list().set(i, myNoteBean);
					}
				}
				comAdapter.notifyDataSetChanged();
			}else{
				productBean.getNote_list().add(myNoteBean);
				comAdapter.notifyDataSetChanged();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@OnClick(R.id.product_ll_tab)
	public void Tab(View v) {

		Bundle bundle = new Bundle();
		bundle.putSerializable(TabAct.SECOND_ACT_ONTENT, tagTwo);
		openActivity(SecondCategoryAct.class, bundle);
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
				sendConnectionPOST(Constant.TOPY + noteBean.getEntity_id() + "/poke/1/", new String[] {},
						new String[] {}, PY1, false);
			} else {
				sendConnectionPOST(Constant.TOPY + noteBean.getEntity_id() + "/poke/0/", new String[] {},
						new String[] {}, PY2, false);
			}
			comAdapter.notifyDataSetChanged();
			break;
		case R.id.comment_item_iv_pic:
			NoteBean noteBean = (NoteBean) arg0.getTag();
			sendConnection(Constant.USERINFO + noteBean.getUser_id() + "/", new String[] {}, new String[] {}, USERINFO,
					true);
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

	private void requestIntent() {
		finish();
	}

	@OnClick(R.id.title_bar_left_iv)
	private void leftBt(View v) {
		if (isLike == 1 || isLike == 2) {
			requestIntent();
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isLike == 1 || isLike == 2) {
				requestIntent();
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 获取购买按钮的顶部位置，也就是商品图片底部的位置
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			priceBtTop = viewMore.getBottom();
		}
	}

	@Override
	public void onScroll(int scrollY) {
		// TODO Auto-generated method stub
		if (scrollY >= priceBtTop) {

			/**** 购买按钮 ****/
			if (product_tv_price.getParent() != view3) {
				view3.setVisibility(View.VISIBLE);
				view2.removeView(product_tv_price);
				view3.addView(product_tv_price);
				view3.getBackground().setAlpha(230);

				/**** 喜欢 分享 更多 ****/
				if (getTitleLayout().getVisibility() == View.GONE) {
					if (animationIn == null) {
						animationIn = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
					}
					getTitleLayout().startAnimation(animationIn);
					getTitleLayout().setVisibility(View.VISIBLE);
				}
			}
		} else {
			if (product_tv_price.getParent() != view2) {
				view3.removeView(product_tv_price);
				view2.addView(product_tv_price);

				if (getTitleLayout().getVisibility() == View.VISIBLE) {
					if (animationOut == null) {
						animationOut = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
					}
					getTitleLayout().startAnimation(animationOut);
					view3.setVisibility(View.GONE);
					animationOut.setAnimationListener(new AnimationListener() {

						@Override
						public void onAnimationStart(Animation arg0) {
							// TODO Auto-generated method stub
							getTitleLayout().setVisibility(View.GONE);
						}

						@Override
						public void onAnimationRepeat(Animation arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animation arg0) {
							// TODO Auto-generated method stub
						}
					});
				}
			}
		}
	}
	
	public void onEventMainThread(LikesBean likesBean) {
		if(likesBean.isLike()){
			isLike();
		}else{
			isUnLike();
		}
	}
	
	private void isLike(){
		if (productBean == null) {
			return;
		}
		productBean.getEntity().setLike_already("1");
		productBean.getEntity().getLike_countAdd();
		isLike = 2;
		productBean.getLike_user_list().add(0, GuokuApplication.getInstance().getBean().getUser());
		isLikes();
		refreshLikeLayout();
	}
	
	private void isUnLike(){
		if (productBean == null) {
			return;
		}
		productBean.getEntity().setLike_already("0");
		productBean.getEntity().getLike_countCut();
		isLike = 1;
		productBean.getLike_user_list().remove(GuokuApplication.getInstance().getBean().getUser());
		isLikes();
		refreshLikeLayout();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
