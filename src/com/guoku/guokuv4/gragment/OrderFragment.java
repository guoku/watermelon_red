package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.adapter.ArrayListAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.MessageBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.PointBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.DateUtils;
import com.guoku.guokuv4.utils.ImgUtils;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.StringUtils.OnNoteTag;
import com.guoku.guokuv4.utils.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderFragment extends BaseFrament implements OnClickListener {

	private static final int POINTLIST = 10;
	private static final int MESSAGELIST = 11;
	private static final int POINTLIST1 = 13;
	private static final int MESSAGELIST1 = 14;
	private static final int PROINFO = 12;

	@ViewInject(R.id.tongzhi_tv_tab2)
	private TextView tongzhi_tv_tab2;

	@ViewInject(R.id.tongzhi_tv_tab1)
	private TextView tongzhi_tv_tab1;

	@ViewInject(R.id.title_bar_centrt_tv)
	private TextView title_bar_centrt_tv;

	@ViewInject(R.id.tongzhi_iv_tab1)
	private ImageView tongzhi_iv_tab1;
	@ViewInject(R.id.title_bar_left_iv)
	private ImageView title_bar_left_iv;

	@ViewInject(R.id.tongzhi_iv_tab2)
	private ImageView tongzhi_iv_tab2;

	@ViewInject(R.id.tongzhi_lv)
	private PullToRefreshListView tongzhi_lv;
	private int curTab = 1;

	private ArrayListAdapter<PointBean> pointAdapter;
	private ArrayListAdapter<MessageBean> messageAdapter;

	private ArrayList<PointBean> pointList;
	private ArrayList<MessageBean> messageList;

	@Override
	protected int getContentId() {
		optionsRound = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY).displayer(new RoundedBitmapDisplayer(90))
				.showImageForEmptyUri(R.drawable.user100).showImageOnFail(R.drawable.user100)
				.showImageOnLoading(R.drawable.user100).build();

		return R.layout.fragment_order;
	}

	@Override
	protected void onSuccess(String result, int where) {
		tongzhi_lv.onRefreshComplete();
		switch (where) {
		case POINTLIST:
			pointList.addAll(ParseUtil.getPointList(result));
			pointAdapter.setList(pointList);
			if (pointAdapter.getCount() == 0) {
				ToastUtil.show(context, "你还没有动态");
			}

			break;
		case MESSAGELIST:
			messageList.addAll(ParseUtil.getMessageList(result));
			messageAdapter.setList(messageList);
			if (messageAdapter.getCount() == 0) {
				ToastUtil.show(context, "你还没有消息");
			}
			break;
		case POINTLIST1:
			pointList = ParseUtil.getPointList(result);
			pointAdapter.setList(pointList);
			if (pointAdapter.getCount() == 0) {
				ToastUtil.show(context, "你还没有动态");
			}

			break;
		case MESSAGELIST1:
			messageList = (ParseUtil.getMessageList(result));
			messageAdapter.setList(messageList);
			if (messageAdapter.getCount() == 0) {
				ToastUtil.show(context, "你还没有消息");
			}
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		tongzhi_lv.onRefreshComplete();
	}

	@Override
	protected void init() {

		title_bar_centrt_tv.setText("通知");
		title_bar_left_iv.setVisibility(View.GONE);
		pointAdapter = new ArrayListAdapter<PointBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(context, R.layout.tongzhi_item, null);
					holder = new ViewHolder();
					ViewUtils.inject(holder, convertView);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				final PointBean bean = mList.get(position);

				holder.tongzhi_item_iv_userpic.setOnClickListener(OrderFragment.this);
				holder.tongzhi_item_iv_userpic.setTag(bean);

				holder.tongzhi_item_iv_pimg.setTag(bean);
				holder.tongzhi_item_iv_pimg.setOnClickListener(OrderFragment.this);
				holder.tongzhi_item_iv_pimg.setVisibility(View.VISIBLE);
				holder.tv_context2.setVisibility(View.GONE);

				if (bean.getType().equals("user_like")) {
					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getLiker().get50()));

					openNickName(bean.getContent().getLiker(),
							bean.getContent().getLiker().getNickname() + " 喜爱了 1 件商品  ",
							bean.getContent().getLiker().getNickname().length(), holder.tv_context);

					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(bean.getContent().getEntity().get50()));
				} else if (bean.getType().equals("entity")) {
					holder.tongzhi_item_iv_userpic
							.setImageURI(Uri.parse(bean.getContent().getNote().getCreator().get50()));

					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(bean.getContent().getEntity().get50()));

					openNickName(bean.getContent().getNote().getCreator(),
							bean.getContent().getNote().getCreator().getNickname() + " 点评了 1 件商品  ",
							bean.getContent().getNote().getCreator().getNickname().length(), holder.tv_context);

				} else if (bean.getType().equals("user_follow")) {

					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getUser().get50()));
					holder.tongzhi_item_iv_pimg.setVisibility(View.INVISIBLE);
					openNickName(bean.getContent().getUser(), bean.getContent().getUser().getNickname() + " 开始关注  ",
							bean.getContent().getUser().getNickname().length(), holder.tv_context);
					holder.tv_context2.setVisibility(View.VISIBLE);
					openNickName(bean.getContent().getTarget(), bean.getContent().getTarget().getNickname(),
							bean.getContent().getTarget().getNickname().length(), holder.tv_context2);
				} else if (bean.getType().equals("article_dig")) {
					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getDigger().get50()));
					openNickName(bean.getContent().getDigger(),
							bean.getContent().getDigger().getNickname() + " 赞了 1 篇图文  ",
							bean.getContent().getDigger().getNickname().length(), holder.tv_context);

					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(Constant.URL_IMG + bean.getContent().getArticle().get240()));
				}
				return convertView;
			}
		};

		messageAdapter = new ArrayListAdapter<MessageBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					convertView = View.inflate(context, R.layout.tongzhi_item2, null);
					holder = new ViewHolder();
					ViewUtils.inject(holder, convertView);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				final MessageBean bean = mList.get(position);
				holder.tongzhi_item_iv_userpic.setOnClickListener(OrderFragment.this);
				holder.tongzhi_item_iv_userpic.setTag(bean);

				holder.tongzhi_item_iv_pimg.setTag(bean);
				holder.tongzhi_item_iv_pimg.setOnClickListener(OrderFragment.this);

				if (bean.getType().equals("note_comment_message")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getComment_user().get50()));
					holder.tongzhi_item_iv_pimg.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(bean.getContent().getNote().get50()));
					openNickName(bean.getContent().getComment_user(),
							bean.getContent().getComment_user().getNickname() + " 评论了你撰写的点评  ",
							bean.getContent().getComment_user().getNickname().length(), holder.tv_context);
				} else if (bean.getType().equals("note_poke_message")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getPoker().get50()));
					holder.tongzhi_item_iv_pimg.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(bean.getContent().getNote().get50()));

					openNickName(bean.getContent().getPoker(), bean.getContent().getPoker().getNickname() + " 赞了你的点评  ",
							bean.getContent().getPoker().getNickname().length(), holder.tv_context);
				} else if (bean.getType().equals("entity_note_message")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_pimg.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_userpic
							.setImageURI(Uri.parse(bean.getContent().getNote().getCreator().get50()));
					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(bean.getContent().getEntity().get50()));

					openNickName(bean.getContent().getNote().getCreator(),
							bean.getContent().getNote().getCreator().getNickname() + " 点评了你添加的商品  ",
							bean.getContent().getNote().getCreator().getNickname().length(), holder.tv_context);

				} else if (bean.getType().equals("user_follow")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);

					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getFollower().get50()));

					holder.tongzhi_item_iv_pimg.setVisibility(View.INVISIBLE);

					openNickName(bean.getContent().getFollower(),
							bean.getContent().getFollower().getNickname() + " 开始关注你  ",
							bean.getContent().getFollower().getNickname().length(), holder.tv_context);

				} else if (bean.getType().equals("note_comment_reply_message")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getFollower().get50()));
					holder.tongzhi_item_iv_pimg.setVisibility(View.INVISIBLE);

					openNickName(bean.getContent().getFollower(),
							bean.getContent().getFollower().getNickname() + " 回复了你的评论  ",
							bean.getContent().getFollower().getNickname().length(), holder.tv_context);

				} else if (bean.getType().equals("note_selection_message")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);

					holder.tongzhi_item_iv_pimg.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_userpic.setImageResource(R.drawable.star);
					// .setVisibility(View.INVISIBLE);
					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(bean.getContent().getEntity().get50()));
					holder.tv_context.setText("你添加的商品被收录精选 " + " " + DateUtils.getStandardDate(bean.getCreated_time()));
				} else if (bean.getType().equals("entity_like_message")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_userpic.setImageURI(Uri.parse(bean.getContent().getLiker().get50()));
					holder.tongzhi_item_iv_pimg.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(bean.getContent().getEntity().get50()));

					openNickName(bean.getContent().getLiker(),
							bean.getContent().getLiker().getNickname() + " 喜爱了你添加的商品  ",
							bean.getContent().getLiker().getNickname().length(), holder.tv_context);
				} else if (bean.getType().equals("dig_article_message")) {
					holder.tongzhi_item_iv_userpic.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_pimg.setVisibility(View.VISIBLE);
					holder.tongzhi_item_iv_userpic
							.setImageURI(Uri.parse(bean.getContent().getDigger().get50()));
					holder.tongzhi_item_iv_pimg.setImageURI(Uri.parse(Constant.URL_IMG + bean.getContent().getArticle().get240()));

					openNickName(bean.getContent().getDigger(),
							bean.getContent().getDigger().getNickname() + " 赞了你写的图文  ",
							bean.getContent().getDigger().getNickname().length(), holder.tv_context);

				}

				return convertView;
			}
		};

		tongzhi_lv.setPullToRefreshOverScrollEnabled(false);
		tongzhi_lv.setScrollingWhileRefreshingEnabled(false);
		tongzhi_lv.setMode(Mode.BOTH);
		tongzhi_lv.setAdapter(pointAdapter);

		tongzhi_lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				if (curTab == 1) {
					getPointList1(System.currentTimeMillis() / 1000 + "");
				} else {
					getMessageList1(System.currentTimeMillis() / 1000 + "");
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (curTab == 1) {
					if (pointList != null && pointList.size() - 1 > 0) {
						if (pointList.get(pointList.size() - 1).getType().equals("entity")) {
							getPointList(pointList.get(pointList.size() - 1).getContent().getNote().getUpdated_time());
						} else if (pointList.get(pointList.size() - 1).getType().equals("user_like")) {

							getPointList(
									pointList.get(pointList.size() - 1).getContent().getEntity().getUpdated_time());
						}
					} else
						tongzhi_lv.onRefreshComplete();
				} else {
					if (messageList != null && messageList.size() - 1 > 0) {
						getMessageList(messageList.get(messageList.size() - 1).getCreated_time());
					} else
						tongzhi_lv.onRefreshComplete();
				}
			}
		});

	}

	@Override
	protected void setData() {

		pointList = new ArrayList<PointBean>();
		messageList = new ArrayList<MessageBean>();
		tongzhi_lv.setRefreshing();
		getPointList(System.currentTimeMillis() / 1000 + "");
	}

	private void getPointList(String time) {
		sendConnection(Constant.POINTLIST, new String[] { "count", "timestamp" }, new String[] { "30", time + "" },
				POINTLIST, false);
	}

	private void getPointList1(String time) {
		sendConnection(Constant.POINTLIST, new String[] { "count", "timestamp" }, new String[] { "30", time + "" },
				POINTLIST1, false);
	}

	private void getMessageList(String time) {
		tongzhi_lv.setRefreshing();
		sendConnection(Constant.MESSAGELIST, new String[] { "count", "timestamp" }, new String[] { "30", time + "" },
				MESSAGELIST, false);
	}

	private void getMessageList1(String time) {
		sendConnection(Constant.MESSAGELIST, new String[] { "count", "timestamp" }, new String[] { "30", time + "" },
				MESSAGELIST1, false);
	}

	@OnClick(R.id.tongzhi_ll_tab1)
	public void Tab1(View v) {
		curTab = 1;
		tongzhi_iv_tab1.setVisibility(View.VISIBLE);
		tongzhi_iv_tab2.setVisibility(View.INVISIBLE);
		tongzhi_lv.setAdapter(pointAdapter);
		if (pointList.size() <= 0) {
			getPointList(System.currentTimeMillis() / 1000 + "");
		}

		tongzhi_tv_tab1.setTextColor(Color.rgb(65, 66, 67));
		tongzhi_tv_tab2.setTextColor(Color.rgb(157, 158, 159));

	}

	@OnClick(R.id.tongzhi_ll_tab2)
	public void Tab2(View v) {
		curTab = 2;
		tongzhi_iv_tab1.setVisibility(View.INVISIBLE);
		tongzhi_iv_tab2.setVisibility(View.VISIBLE);
		tongzhi_lv.setAdapter(messageAdapter);
		if (messageList.size() <= 0) {
			getMessageList(System.currentTimeMillis() / 1000 + "");
		}

		tongzhi_tv_tab2.setTextColor(Color.rgb(65, 66, 67));
		tongzhi_tv_tab1.setTextColor(Color.rgb(157, 158, 159));

	}

	private class ViewHolder {

		@ViewInject(R.id.tongzhi_item_iv_pimg)
		private SimpleDraweeView tongzhi_item_iv_pimg;

		@ViewInject(R.id.tongzhi_item_iv_userpic)
		private SimpleDraweeView tongzhi_item_iv_userpic;

		@ViewInject(R.id.tongzhi_item_tv_context)
		private TextView tv_context;

		@ViewInject(R.id.tongzhi_item_tv_context2)
		private TextView tv_context2;
	}

	private void openUserAct(String entityInfo) {

		sendConnection(Constant.PROINFO + entityInfo + "/", new String[] { "entity_id" }, new String[] { entityInfo },
				PROINFO, true);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tongzhi_item_iv_pimg:
			if (curTab == 1) {
				PointBean bean = (PointBean) arg0.getTag();
				if(bean.getContent().getDigger() != null){
					Bundle bundle = new Bundle();
					Sharebean sharebean = new Sharebean();
					sharebean.setTitle(bean.getContent().getArticle().getTitle());
					sharebean.setContext(bean.getContent().getArticle().getContent().substring(0, 50));
					sharebean.setAricleUrl(bean.getContent().getArticle().getUrl());
					sharebean.setImgUrl(bean.getContent().getArticle().getCover());
					sharebean.setIs_dig(bean.getContent().getArticle().isIs_dig());
					bundle.putSerializable(WebShareAct.class.getName(), sharebean);
					sharebean.setAricleId(String.valueOf(bean.getContent().getArticle().getArticle_id()));
					openActivity(WebShareAct.class, bundle);
				}else{
					openUserAct(bean.getContent().getEntity().getEntity_id());
				}
			} else {
				MessageBean bean = (MessageBean) arg0.getTag();
				if(bean.getContent().getArticle() != null){
					Bundle bundle = new Bundle();
					Sharebean sharebean = new Sharebean();
					sharebean.setTitle(bean.getContent().getArticle().getTitle());
					sharebean.setContext(bean.getContent().getArticle().getContent().substring(0, 50));
					sharebean.setAricleUrl(bean.getContent().getArticle().getUrl());
					sharebean.setImgUrl(bean.getContent().getArticle().getCover());
					sharebean.setIs_dig(bean.getContent().getArticle().isIs_dig());
					bundle.putSerializable(WebShareAct.class.getName(), sharebean);
					sharebean.setAricleId(String.valueOf(bean.getContent().getArticle().getArticle_id()));
					openActivity(WebShareAct.class, bundle);
				}else{
					openUserAct(bean.getEntity_id());
				}
			}
			break;

		case R.id.tongzhi_item_iv_userpic:
			if (curTab == 1) {
				PointBean bean = (PointBean) arg0.getTag();
				Intent intent = new Intent(context, UserBaseFrament.class);
				if (bean.getType().equals("user_like")) {
					intent.putExtra("data", bean.getContent().getLiker());
				} else if (bean.getType().equals("entity")) {
					intent.putExtra("data", bean.getContent().getNote().getCreator());
				} else if (bean.getType().equals("user_follow")) {
					intent.putExtra("data", bean.getContent().getUser());
				} else if (bean.getType().equals("article_dig")) {
					intent.putExtra("data", bean.getContent().getDigger());
				}
				startActivity(intent);
			} else {
				MessageBean bean = (MessageBean) arg0.getTag();
				if (bean.getType().equals("note_comment_message")) {
					Intent intent = new Intent(context, UserBaseFrament.class);
					intent.putExtra("data", bean.getContent().getComment_user());
					startActivity(intent);
				} else if (bean.getType().equals("note_poke_message")) {
					Intent intent = new Intent(context, UserBaseFrament.class);
					intent.putExtra("data", bean.getContent().getPoker());
					startActivity(intent);
				} else if (bean.getType().equals("entity_note_message")) {
					Intent intent = new Intent(context, UserBaseFrament.class);
					intent.putExtra("data", bean.getContent().getNote().getCreator());
					startActivity(intent);
				} else if (bean.getType().equals("user_follow")) {
					Intent intent = new Intent(context, UserBaseFrament.class);
					intent.putExtra("data", bean.getContent().getFollower());
					startActivity(intent);
				} else if (bean.getType().equals("note_comment_reply_message")) {
					Intent intent = new Intent(context, UserBaseFrament.class);
					intent.putExtra("data", bean.getContent().getFollower());
					startActivity(intent);
				} else if (bean.getType().equals("entity_like_message")) {
					Intent intent = new Intent(context, UserBaseFrament.class);
					intent.putExtra("data", bean.getContent().getLiker());
					startActivity(intent);
				}
			}
			break;

		}

	}

	/**
	 * 点击nickname跳转到
	 * 
	 * @param userBean
	 * @param str
	 * @param end
	 * @param tv
	 */
	private void openNickName(final UserBean userBean, String str, int end, TextView tv) {

		StringUtils.setNickName(context, str, 0, end, tv, new OnNoteTag() {
			@Override
			public void setTagClick(String tagName) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, UserBaseFrament.class);
				intent.putExtra("data", userBean);
				startActivity(intent);
			}
		});
	}
}
