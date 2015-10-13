package com.guoku.guokuv4.act;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.customview.ScrollViewWithListView;
import com.ekwing.students.utils.ArrayListAdapter;
import com.ekwing.students.utils.DateUtils;
import com.ekwing.students.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.adapter.GridView3vAdapter;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.TabNoteBean;
import com.guoku.guokuv4.entity.test.TagBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.BroadUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class UserAct extends NetWorkActivity {

	private static final int FOLLOW1 = 13;
	private static final int FOLLOW0 = 14;
	private static final int TABLIKE = 10;
	private static final int TABNOTE = 11;
	private static final int TABTAG = 12;
	private static final int COMMENTLIST = 15;
	private static final int PROINFO = 16;

	private static final int TABLIKEADD = 18;
	private static final int TABNOTEADD = 19;
	private static final int TABTAGADD = 17;

	@ViewInject(R.id.psrson_tv_tab2)
	private TextView psrson_tv_tab2;

	@ViewInject(R.id.psrson_tv_tab1)
	private TextView psrson_tv_tab1;

	@ViewInject(R.id.psrson_tv_tab3)
	private TextView psrson_tv_tab3;

	@ViewInject(R.id.psrson_tv_btn)
	private TextView psrson_tv_btn;

	@ViewInject(R.id.sv)
	private PullToRefreshScrollView sv;

	@ViewInject(R.id.psrson_iv_btn)
	private ImageView psrson_iv_btn;

	@ViewInject(R.id.psrson_iv_tab1)
	private ImageView psrson_iv_tab1;

	@ViewInject(R.id.psrson_iv_tab2)
	private ImageView psrson_iv_tab2;

	@ViewInject(R.id.psrson_ll_btn)
	private LinearLayout psrson_ll_btn;

	@ViewInject(R.id.psrson_iv_tab3)
	private ImageView psrson_iv_tab3;

	@ViewInject(R.id.psrson_iv_pic)
	private SimpleDraweeView psrson_iv_pic;

	@ViewInject(R.id.psrson_tv_fans)
	private TextView psrson_tv_fans;
	@ViewInject(R.id.psrson_iv_sex)
	private TextView psrson_iv_sex;
	@ViewInject(R.id.psrson_tv_name)
	private TextView psrson_tv_name;
	@ViewInject(R.id.psrson_tv_sign)
	private TextView psrson_tv_sign;
	@ViewInject(R.id.psrson_tv_guanzhu)
	private TextView psrson_tv_guanzhu;

	@ViewInject(R.id.psrson_gv)
	private com.ekwing.students.customview.ScrollViewWithGridView psrson_gv;

	@ViewInject(R.id.psrson_lv_1)
	private ScrollViewWithListView psrson_lv_1;

	@ViewInject(R.id.psrson_lv_2)
	private ScrollViewWithListView psrson_lv_2;

	private GridView3vAdapter gvAdapter;
	private ArrayListAdapter<TabNoteBean> lvAdapter;
	private ArrayListAdapter<TagBean> tabAdapter;

	private ArrayList<EntityBean> likeList;

	private ArrayList<TabNoteBean> noteList;

	private ArrayList<TagBean> tagList;

	private UserBean userBean;
	private boolean isMe;
	private String time = "";
	private DisplayImageOptions optionsRound1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_personal);
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		sv.onRefreshComplete();
		switch (where) {
		case TABLIKE:
			try {
				time = new JSONObject(result).getString("timestamp");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			likeList = ParseUtil.getTabLikeList(result);
			gvAdapter.setList(likeList);
			break;
		case TABLIKEADD:
			try {
				time = new JSONObject(result).getString("timestamp");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			likeList.addAll(ParseUtil.getTabLikeList(result));
			gvAdapter.setList(likeList);
			break;
		case TABNOTE:
			noteList = ParseUtil.getTabNoteList(result);
			lvAdapter.setList(noteList);
			break;
		case TABNOTEADD:
			noteList.addAll(ParseUtil.getTabNoteList(result));
			lvAdapter.setList(noteList);
			break;
		case TABTAG:
			tagList = ParseUtil.getTabTagList(result);
			tabAdapter.setList(tagList);
			break;
		case TABTAGADD:
			tagList.addAll(ParseUtil.getTabTagList(result));
			tabAdapter.setList(tagList);
			break;
		case FOLLOW0:
			psrson_tv_btn.setText("关注");
			psrson_iv_btn.setImageResource(R.drawable.add_to);
			psrson_tv_btn.setTextColor(Color.WHITE);
			psrson_ll_btn.setBackgroundResource(R.drawable.blue_shap);
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY,
					Constant.INTENT_ACTION_VALUE_FOLLOW);
			break;
		case FOLLOW1:
			ToastUtil.show(context, "关注成功");
			JSONObject root;
			try {
				root = new JSONObject(result);
				Logger.i(TAG, root.getString("relation"));
				if (root.getString("relation").equals("1")) {
					psrson_tv_btn.setText("已关注");
					psrson_iv_btn.setImageResource(R.drawable.hai_to);
					psrson_tv_btn.setTextColor(Color.argb(255, 19, 143, 215));
					psrson_ll_btn.setBackgroundResource(R.drawable.tfz_shap);
				} else {
					psrson_ll_btn.setBackgroundResource(R.drawable.tfz_shap);
					psrson_tv_btn.setText("互相关注");
					psrson_iv_btn.setImageResource(R.drawable.to);
					psrson_tv_btn.setTextColor(Color.argb(255, 19, 143, 215));
				}
				BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY,
						Constant.INTENT_ACTION_VALUE_FOLLOW);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case COMMENTLIST:
			Intent intent = new Intent(mContext, CommentTalkAct.class);
			intent.putExtra("data", result);
			startActivity(intent);
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		switch (where) {
		case FOLLOW0:
			ToastUtil.show(context, "取消关注失败");
			break;
		case FOLLOW1:
			ToastUtil.show(context, "关注失败");
		default:
			break;
		}
	}

	@Override
	protected void setupData() {

		optionsRound1 = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new RoundedBitmapDisplayer(300))
				.showImageForEmptyUri(R.drawable.user100)
				.showImageOnFail(R.drawable.user100)
				.showImageOnLoading(R.drawable.user100).build();

		setGLeft(true, R.drawable.back_selector);

		userBean = (UserBean) getIntent().getSerializableExtra("data");
		if (EkwingApplication.getInstance().getBean() != null) {
			isMe = EkwingApplication.getInstance().getBean().getUser()
					.getUser_id().equals(userBean.getUser_id());
		}

		psrson_tv_fans.setText(userBean.getFan_count());
		psrson_tv_guanzhu.setText(userBean.getFollowing_count());
		psrson_tv_name.setText(userBean.getNickname());
		psrson_tv_sign.setText(userBean.getBio());
		if (userBean.getGender().equals("男")) {
			psrson_iv_sex.setText("♂");
			psrson_iv_sex.setTextColor(Color.rgb(19, 143, 215));
		} else {
			psrson_iv_sex.setText("♀");
			psrson_iv_sex.setTextColor(Color.rgb(253, 189, 217));
		}

		psrson_iv_pic.setImageURI(Uri.parse(userBean.get240()));

		psrson_tv_tab2.setText("点评 " + userBean.getEntity_note_count());
		psrson_tv_tab1.setText("喜爱 " + userBean.getLike_count());
		psrson_tv_tab3.setText("标签 " + userBean.getTag_count());

		if (isMe) {
			setGCenter(true, "我");
			// psrson_tv_btn.setText("修改个人资料");
			// psrson_ll_btn.setBackgroundColor(color.g_224);
		} else {
			psrson_ll_btn.setBackgroundResource(R.drawable.blue_shap);
			psrson_tv_btn.setText("关注");
			psrson_iv_btn.setImageResource(R.drawable.add_to);
			psrson_tv_btn.setTextColor(Color.WHITE);
		}

		if (!StringUtils.isEmpty(userBean.getRelation())) {
			if (userBean.getRelation().equals("1")) {
				setGCenter(true, userBean.getNickname());
				// psrson_tv_btn.setVisibility(View.GONE);
				// psrson_iv_btn.setBackgroundResource(R.drawable.has);
				psrson_ll_btn.setBackgroundResource(R.drawable.tfz_shap);
				psrson_tv_btn.setText("已关注");
				psrson_iv_btn.setImageResource(R.drawable.hai_to);
				psrson_tv_btn.setTextColor(Color.argb(255, 19, 143, 215));
			} else if (userBean.getRelation().equals("3")) {
				setGCenter(true, userBean.getNickname());
				// psrson_iv_btn.setBackgroundResource(R.drawable.double1);
				// psrson_tv_btn.setVisibility(View.GONE);
				psrson_ll_btn.setBackgroundResource(R.drawable.tfz_shap);
				// psrson_ll_btn.setBackgroundColor(color.g_blue);
				psrson_tv_btn.setText("互相关注");
				psrson_iv_btn.setImageResource(R.drawable.to);
				psrson_tv_btn.setTextColor(Color.argb(255, 19, 143, 215));
			}
		}

		else {
			setGCenter(true, userBean.getNickname());
			// psrson_iv_btn.setBackgroundResource(R.drawable.add);
			psrson_ll_btn.setBackgroundResource(R.drawable.blue_shap);
			// psrson_tv_btn.setVisibility(View.GONE);
			// psrson_ll_btn.setBackgroundColor(color.g_other);
			psrson_tv_btn.setText("关注");
			psrson_iv_btn.setImageResource(R.drawable.add_to);
			psrson_tv_btn.setTextColor(Color.WHITE);
		}

		psrson_lv_1.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
		psrson_lv_2.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
		psrson_gv.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));

		gvAdapter = new GridView3vAdapter(mContext);

		lvAdapter = new ArrayListAdapter<TabNoteBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LVViewHold holder = null;
				if (convertView == null) {
					convertView = View.inflate(context, R.layout.person_item2,
							null);
					holder = new LVViewHold();
					ViewUtils.inject(holder, convertView);
					convertView.setTag(holder);
				} else {
					holder = (LVViewHold) convertView.getTag();
				}
				TabNoteBean bean = mList.get(position);

				holder.person_item_iv_pic.setImageURI(Uri.parse(bean
						.getEntity().get240()));

				holder.person_item_tv_context.setText(bean.getNote()
						.getContent());
				holder.person_item_tv_time.setText(DateUtils
						.getStandardDate(bean.getNote().getUpdated_time()));
				return convertView;
			}
		};
		tabAdapter = new ArrayListAdapter<TagBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TBViewHold holder = null;
				if (convertView == null) {
					convertView = View.inflate(context, R.layout.person_item3,
							null);
					holder = new TBViewHold();
					ViewUtils.inject(holder, convertView);
					convertView.setTag(holder);
				} else {
					holder = (TBViewHold) convertView.getTag();
				}
				TagBean bean = mList.get(position);
				// imageLoader.displayImage(bean.getImg(),
				// holder.person_item_iv_pic);
				holder.person_item_tv_context.setText("#" + bean.getTag());
				holder.person_item_tv_time.setText(bean.getEntity_count()
						+ "件商品");
				return convertView;
			}
		};
		psrson_gv.setAdapter(gvAdapter);
		psrson_lv_1.setAdapter(lvAdapter);
		psrson_lv_2.setAdapter(tabAdapter);

		likeList = new ArrayList<EntityBean>();
		noteList = new ArrayList<TabNoteBean>();
		tagList = new ArrayList<TagBean>();

		sendConnection(Constant.TABLIKE + userBean.getUser_id() + "/like/",
				new String[] { "count", "timestamp" }, new String[] { "30",
						System.currentTimeMillis() / 1000 + "" }, TABLIKE,
				false);

		psrson_lv_1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO
						+ noteList.get(arg2).getEntity().getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { noteList
								.get(arg2).getEntity().getEntity_id() },
						PROINFO, true);
			}
		});

		psrson_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO
						+ gvAdapter.getItem(arg2).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { gvAdapter
								.getItem(arg2).getEntity_id() }, PROINFO, true);
			}
		});
		psrson_lv_2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, EntityAct.class);
				intent.putExtra("data", userBean.getUser_id());
				intent.putExtra("name", tagList.get(arg2).getTag());
				startActivity(intent);
			}
		});

		sv.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
				if (psrson_gv.getVisibility() == View.VISIBLE) {
					if (likeList != null && likeList.size() - 1 > 0) {
						sendConnection(Constant.TABLIKE + userBean.getUser_id()
								+ "/like/",
								new String[] { "count", "timestamp" },
								new String[] { "30", time }, TABLIKEADD, false);
					} else
						sv.onRefreshComplete();
				} else if (psrson_lv_1.getVisibility() == View.VISIBLE) {

					if (noteList != null && noteList.size() - 1 > 0) {
						sendConnection(Constant.TABLIKE + userBean.getUser_id()
								+ "/entity/note/", new String[] { "count",
								"timestamp" }, new String[] {
								"30",
								noteList.get(noteList.size() - 1).getNote()
										.getCreated_time() }, TABNOTEADD, false);
					} else
						sv.onRefreshComplete();

				} else if (psrson_lv_2.getVisibility() == View.VISIBLE) {
					sv.onRefreshComplete();
				} else

					sv.onRefreshComplete();

			}
		});
		sv.setMode(Mode.PULL_FROM_END);

	}

	@OnClick(R.id.psrson_ll_tab1)
	public void Tab1(View v) {
		sv.setMode(Mode.PULL_FROM_END);

		psrson_iv_tab1.setVisibility(View.VISIBLE);
		psrson_iv_tab2.setVisibility(View.INVISIBLE);
		psrson_iv_tab3.setVisibility(View.INVISIBLE);
		psrson_gv.setVisibility(View.VISIBLE);
		psrson_lv_1.setVisibility(View.GONE);
		psrson_lv_2.setVisibility(View.GONE);

		// psrson_tv_tab1.setTextColor(color.g_main);
		// psrson_tv_tab2.setTextColor(color.g_other);
		// psrson_tv_tab3.setTextColor(color.g_other);
		//
		//

		psrson_tv_tab1.setTextColor(Color.rgb(65, 66, 67));
		psrson_tv_tab2.setTextColor(Color.rgb(157, 158, 159));
		psrson_tv_tab3.setTextColor(Color.rgb(157, 158, 159));

		if (likeList.size() <= 0) {
			sendConnection(Constant.TABLIKE + userBean.getUser_id() + "/like/",
					new String[] { "count", "timestamp" }, new String[] { "30",
							System.currentTimeMillis() / 1000 + "" }, TABLIKE,
					false);
		} else
			gvAdapter.setList(likeList);
		// sv.smoothScrollTo(0, 0);
	}

	@OnClick(R.id.psrson_ll_btn)
	public void psrson_ll_btn(View v) {

		if (isMe) {
			Intent intent = new Intent(context, UserInfoAct.class);
			intent.putExtra("data", EkwingApplication.getInstance().getBean());
			startActivity(intent);
		} else {
			if (userBean.getRelation().equals("0")
					|| userBean.getRelation().equals("2")) {
				sendConnectionPOST(Constant.FOLLOW + userBean.getUser_id()
						+ "/follow/1/", new String[] {}, new String[] {},
						FOLLOW1, false);
				userBean.setRelation("1");
			} else {
				sendConnectionPOST(Constant.FOLLOW + userBean.getUser_id()
						+ "/follow/0/", new String[] {}, new String[] {},
						FOLLOW0, false);
				userBean.setRelation("0");
			}
		}

	}

	@OnClick(R.id.psrson_ll_follow)
	public void psrson_ll_follow(View v) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("data", userBean.getUser_id());
		intent.putExtra("url", "/following/");
		intent.putExtra("name", "关注");
		startActivity(intent);
	}

	@OnClick(R.id.psrson_ll_fans)
	public void psrson_ll_fans(View v) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("data", userBean.getUser_id());
		intent.putExtra("url", "/fan/");
		intent.putExtra("name", "粉丝");
		startActivity(intent);
	}

	@OnClick(R.id.psrson_ll_tab2)
	public void Tab2(View v) {
		sv.setMode(Mode.PULL_FROM_END);

		psrson_iv_tab1.setVisibility(View.INVISIBLE);
		psrson_iv_tab2.setVisibility(View.VISIBLE);
		psrson_iv_tab3.setVisibility(View.INVISIBLE);

		// psrson_tv_tab2.setTextColor(color.g_main);
		// psrson_tv_tab1.setTextColor(color.g_other);
		// psrson_tv_tab3.setTextColor(color.g_other);

		psrson_tv_tab2.setTextColor(Color.rgb(65, 66, 67));
		psrson_tv_tab1.setTextColor(Color.rgb(157, 158, 159));
		psrson_tv_tab3.setTextColor(Color.rgb(157, 158, 159));

		psrson_gv.setVisibility(View.GONE);
		psrson_lv_1.setVisibility(View.VISIBLE);
		psrson_lv_2.setVisibility(View.GONE);

		if (noteList.size() <= 0) {
			sendConnection(
					Constant.TABLIKE + userBean.getUser_id() + "/entity/note/",
					new String[] { "count", "timestamp" },
					new String[] { "30", System.currentTimeMillis() / 1000 + "" },
					TABNOTE, false);
		} else
			lvAdapter.setList(noteList);
		// sv.smoothScrollTo(0, 0);
	}

	@OnClick(R.id.psrson_ll_tab3)
	public void Tab3(View v) {
		sv.setMode(Mode.DISABLED);

		psrson_iv_tab1.setVisibility(View.INVISIBLE);
		psrson_iv_tab2.setVisibility(View.INVISIBLE);
		psrson_iv_tab3.setVisibility(View.VISIBLE);

		// psrson_tv_tab2.setTextColor(color.g_other);
		// psrson_tv_tab1.setTextColor(color.g_other);
		// psrson_tv_tab3.setTextColor(color.g_main);

		psrson_tv_tab3.setTextColor(Color.rgb(65, 66, 67));
		psrson_tv_tab2.setTextColor(Color.rgb(157, 158, 159));
		psrson_tv_tab1.setTextColor(Color.rgb(157, 158, 159));

		psrson_gv.setVisibility(View.GONE);
		psrson_lv_1.setVisibility(View.GONE);
		psrson_lv_2.setVisibility(View.VISIBLE);

		if (tagList.size() <= 0) {
			sendConnection(Constant.TABLIKE + userBean.getUser_id() + "/tag/",
					new String[] { "count", "timestamp" }, new String[] { "30",
							System.currentTimeMillis() / 1000 + "" }, TABTAG,
					false);
		} else
			tabAdapter.setList(tagList);
		// sv.smoothScrollTo(0, 0);
	}

	private class LVViewHold {
		@ViewInject(R.id.person_item_iv_pic)
		SimpleDraweeView person_item_iv_pic;

		@ViewInject(R.id.person_item_tv_context)
		TextView person_item_tv_context;

		@ViewInject(R.id.person_item_tv_time)
		TextView person_item_tv_time;

	}

	private class TBViewHold {
		@ViewInject(R.id.person_item3_iv_pic)
		SimpleDraweeView person_item_iv_pic;

		@ViewInject(R.id.person_item3_tv_context)
		TextView person_item_tv_context;

		@ViewInject(R.id.person_item3_tv_time)
		TextView person_item_tv_time;

	}
}
