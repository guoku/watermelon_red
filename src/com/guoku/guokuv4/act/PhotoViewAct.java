/**

 */
package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVAnalytics;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.CommentsBean;
import com.guoku.guokuv4.bean.LikesBean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.NoteBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.photoview.HackyViewPager;
import com.guoku.guokuv4.photoview.PhotoView;
import com.guoku.guokuv4.utils.ImgUtils.AnimateFirstDisplayListener;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import de.greenrobot.event.EventBus;;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年12月17日 下午5:13:40 查看商品大图
 */
public class PhotoViewAct extends NetWorkActivity implements OnPageChangeListener {

	private static final int LIKE1 = 1001;// 喜欢
	private static final int LIKE0 = 1002;// 不喜欢

	@ViewInject(R.id.hacky_view_pager)
	private HackyViewPager mViewPager;

	@ViewInject(R.id.layout_round)
	LinearLayout viewRound;

	@ViewInject(R.id.product_iv_like)
	ImageView imageLike;

	@ViewInject(R.id.likes_count)
	TextView likeCount;

	@ViewInject(R.id.comment_count)
	TextView commentCount;

	public static final String KEY_ITEM = "KEY_ITEM";

	PInfoBean pInfoBean = null;

	JSONArray thumbs;

	int item = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// overridePendingTransition(R.anim.push_fade_in, R.anim.push_fade_out);

		overridePendingTransition(R.anim.act_fade_in, R.anim.act_fade_out);

		EventBus.getDefault().register(this);

		setContentView(R.layout.activity_photo_view);
		
		mTintManager.setStatusBarTintResource(0);
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case LIKE0:
			if (pInfoBean == null) {
				return;
			}
			pInfoBean.getEntity().setLike_already("0");
			isLikes(pInfoBean);
			likeCount.setText(pInfoBean.getEntity().getLike_countCut());

			EventBus.getDefault().post(new LikesBean(false));

			break;
		case LIKE1:
			AVAnalytics.onEvent(this, "like");
			MobclickAgent.onEvent(this, "like");
			if (pInfoBean == null) {
				return;
			}
			pInfoBean.getEntity().setLike_already("1");
			isLikes(pInfoBean);
			likeCount.setText(pInfoBean.getEntity().getLike_countAdd());

			EventBus.getDefault().post(new LikesBean(true));
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			pInfoBean = (PInfoBean) bundle.getSerializable(PhotoViewAct.class.getName());
			item = bundle.getInt(KEY_ITEM);
			mViewPager.setAdapter(new SamplePagerAdapter(this, pInfoBean));
			mViewPager.setCurrentItem(item);
			mViewPager.setOnPageChangeListener(this);

			isLikes(pInfoBean);
			likeCount.setText(pInfoBean.getEntity().getLike_count());
			commentCount.setText(pInfoBean.getEntity().getNote_count());

			initRound();
		}
	}

	private void initRound() {
		if (thumbs != null) {
			if (thumbs.length() > 1) {
				for (int i = thumbs.length() - 1; i >= 0; i--) {
					ImageView imageView = new ImageView(mContext);
					LayoutParams lParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					lParams.setMargins(10, 0, 10, 0);
					imageView.setLayoutParams(lParams);
					if (item == i) {
						imageView.setImageResource(R.drawable.ced_d);
					} else {
						imageView.setImageResource(R.drawable.ced_n);
					}
					viewRound.addView(imageView);
				}
			}
		}
	}

	private void isLikes(PInfoBean productBean) {

		if ("1".equals(productBean.getEntity().getLike_already())) {
			imageLike.setImageResource(R.drawable.photo_hearted);
		} else {
			imageLike.setImageResource(R.drawable.photo_heart);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

		for (int i = 0; i < viewRound.getChildCount(); i++) {
			ImageView imageView = (ImageView) viewRound.getChildAt(i);
			imageView.setImageResource(R.drawable.ced_d);
			if (arg0 != i) {
				imageView.setImageResource(R.drawable.ced_n);
			}
		}
	}

	@OnClick(R.id.layout_like)
	private void onLike(View v) {
		if (GuokuApplication.getInstance().getBean() != null) {
			likeClick();
		} else {
			startActivity(new Intent(mContext, LoginAct.class));
		}
	}

	@OnClick(R.id.layout_comment)
	private void onComment(View v) {
		if (GuokuApplication.getInstance().getBean() != null) {
			Intent intent = new Intent(mContext, CommentAct.class);
			ArrayList<NoteBean> list = pInfoBean.getNote_list();
			for (NoteBean bean : list) {
				if (bean.getCreator().getUser_id()
						.equals(GuokuApplication.getInstance().getBean().getUser().getUser_id())) {
					intent.putExtra("noteid", bean.getNote_id());
				}
			}
			intent.putExtra("data", JSON.toJSONString(pInfoBean));
			startActivity(intent);
		} else {
			startActivity(new Intent(mContext, LoginAct.class));
		}
	}

	@OnClick(R.id.img_taobao)
	private void onTaobao(View v) {
		gotoTaoBao(pInfoBean, 0);
	}

	private void likeClick() {

		if (pInfoBean != null) {
			if (pInfoBean.getEntity().getLike_already().equals("0")) {
				sendConnectionPOST(Constant.TOLIKE + pInfoBean.getEntity().getEntity_id() + "/like/1/", new String[] {},
						new String[] {}, LIKE1, false);

			} else {
				sendConnectionPOST(Constant.TOLIKE + pInfoBean.getEntity().getEntity_id() + "/like/0/", new String[] {},
						new String[] {}, LIKE0, false);
			}
		}
	}
	
	@OnClick(R.id.close_img)
	private void closeImg(View view){
		
		finishAct();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		finishAct();
		return super.onKeyUp(keyCode, event);
	}

	class SamplePagerAdapter extends PagerAdapter {

		PInfoBean pBean;
		private ImageLoader imageLoader;
		private ImageLoaderConfiguration config;
		private DisplayImageOptions options;
		Context context;

		public SamplePagerAdapter(Context mContext, PInfoBean pInfoBean) {
			// TODO Auto-generated constructor stub
			this.pBean = pInfoBean;

			try {
				thumbs = new JSONArray(pBean.getEntity().getDetail_images());
//				if(thumbs.length() == 0){
					thumbs.put(pBean.getEntity().getChief_image());
//				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			config = new ImageLoaderConfiguration.Builder(mContext).threadPriority(Thread.NORM_PRIORITY - 2)
					.denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator())
					.tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
			imageLoader = ImageLoader.getInstance();
			imageLoader.init(config);
			options = new DisplayImageOptions.Builder().showImageOnLoading(R.color.gray)
					.showImageForEmptyUri(R.color.gray).showImageOnFail(R.color.gray)
					// .displayer(new RoundedBitmapDisplayer(20)) 圆角显示图片
					.build();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return thumbs.length();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = (PhotoView) View.inflate(container.getContext(), R.layout.simple_drawee_view, null);

			try {
				imageLoader.displayImage(thumbs.getString(position).replaceFirst("images", "images/800"), photoView,
						options, new AnimateFirstDisplayListener());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			container.addView(photoView);
			return photoView;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView((View) object);
		}
	}

	private void handleTransEffect() {
		Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.photo_trans);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

			}
		});
		mViewPager.startAnimation(anim);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishAct();
		}

		return super.onKeyDown(keyCode, event);
	}

	public void onEventMainThread(CommentsBean commentsBean) {
		// if(commentsBean.isAdd){
		// pInfoBean.getEntity().setNote_count(commentsBean.getCommentValue());
		// }else{
		// commentCount.setText(pInfoBean.getEntity().getNote_countAdd());
		// }
		finishAct();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	private void finishAct() {
		finish();
		// overridePendingTransition(R.anim.push_fade_down_in,
		// R.anim.push_fade_down_out);
		overridePendingTransition(R.anim.act_fade_in, R.anim.act_fade_out);
	}

}
