package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Constant;
import com.ekwing.students.customview.ScrollViewWithListView;
import com.ekwing.students.utils.ArrayListAdapter;
import com.ekwing.students.utils.DateUtils;
import com.ekwing.students.utils.SharePrenceUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.act.CommentTalkAct;
import com.guoku.guokuv4.act.EntityAct;
import com.guoku.guokuv4.act.FansAct;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.RegisterAct;
import com.guoku.guokuv4.act.SettingAct;
import com.guoku.guokuv4.act.UserInfoAct;
import com.guoku.guokuv4.adapter.GridView3vAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.TabNoteBean;
import com.guoku.guokuv4.entity.test.TagBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.ImgUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

public class PersonalFragment extends BaseFrament {

	private static final int TABLIKE = 10;
	private static final int TABNOTE = 11;
	private static final int TABTAG = 12;
	private static final int TABLIKEADD = 15;
	private static final int TABLIKEADD_DOWN = 151;//下拉
	private static final int TABNOTEADD = 16;
	private static final int TABNOTEADD_DOWN = 161;
	private static final int TABTAGADD = 17;
	private static final int COMMENTLIST = 14;
	private static final int PROINFO = 13;
	private static final int USERINFO = 18;
	public static final int RESULT_CODE = 1001;// 编辑个人资料

	@ViewInject(R.id.psrson_tv_tab2)
	private TextView psrson_tv_tab2;

	@ViewInject(R.id.title_bar_centrt_tv)
	private TextView title;

	@ViewInject(R.id.psrson_tv_tab1)
	private TextView psrson_tv_tab1;

	@ViewInject(R.id.psrson_iv_sex)
	private TextView psrson_iv_sex;

	@ViewInject(R.id.psrson_tv_tab3)
	private TextView psrson_tv_tab3;

	@ViewInject(R.id.psrson_iv_tab1)
	private ImageView psrson_iv_tab1;

	@ViewInject(R.id.psrson_iv_tab2)
	private ImageView psrson_iv_tab2;

	@ViewInject(R.id.psrson_iv_tab3)
	private ImageView psrson_iv_tab3;

	@ViewInject(R.id.title_bar_rigth_iv)
	private ImageView iv_set;

	@ViewInject(R.id.psrson_iv_pic)
	private SimpleDraweeView psrson_iv_pic;

	@ViewInject(R.id.sv)
	private PullToRefreshScrollView sv;

	@ViewInject(R.id.psrson_tv_fans)
	private TextView psrson_tv_fans;

	@ViewInject(R.id.title_bar_centrt_tv)
	private TextView tv_title;

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

	private AccountBean userBean;
	private DisplayImageOptions optionsRound;
	private String time = System.currentTimeMillis() / 1000 + "";

	private BroadcastReceiver receiveBroadCast; // 用来处理其它ui操作的关注、喜欢等，保证数据急时同步

	@Override
	protected int getContentId() {
		optionsRound = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new RoundedBitmapDisplayer(90))
				.showImageForEmptyUri(R.drawable.user100)
				.showImageOnFail(R.drawable.user100)
				.showImageOnLoading(R.drawable.user100).cacheOnDisk(true)
				.cacheInMemory(true).build();

		if (EkwingApplication.getInstance().getBean() == null) {

			return R.layout.pserson_no_log;
		} else
			return R.layout.fragment_personal;
	}

	@Override
	protected void onSuccess(String result, int where) {
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
			psrson_tv_tab3.setText("标签 " + String.valueOf(tagList.size()));
			break;
		case TABTAGADD:
			tagList.addAll(ParseUtil.getTabTagList(result));
			tabAdapter.setList(tagList);
			break;
		case COMMENTLIST:
			Intent intent = new Intent(getActivity(), CommentTalkAct.class);
			intent.putExtra("data", result);
			startActivity(intent);
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case USERINFO:
			Log.d("USERINFO=", result);
			refreshFollow(result);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {

	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		imageLoader.clearMemoryCache();
	}

	@Override
	protected void init() {
		userBean = EkwingApplication.getInstance().getBean();
		iv_set.setVisibility(View.VISIBLE);
		if (userBean == null) {
			iv_set.setImageResource(R.drawable.setting);
			title.setText("我");
			return;
		}
		tv_title.setText("我");
		iv_set.setImageResource(R.drawable.setting);
		psrson_tv_fans.setText(userBean.getUser().getFan_count());
		psrson_tv_guanzhu.setText(userBean.getUser().getFollowing_count());

		psrson_tv_name.setText(userBean.getUser().getNickname());
		psrson_tv_sign.setText(userBean.getUser().getBio());
		
		psrson_iv_pic.setImageURI(Uri.parse(userBean.getUser().get240()));

		if (userBean.getUser().getGender().equals("男")) {
			psrson_iv_sex.setTextColor(Color.rgb(19, 143, 215));
			setTextRightImg(psrson_iv_sex, R.drawable.male);
		} else {
			psrson_iv_sex.setTextColor(Color.rgb(253, 189, 217));
			setTextRightImg(psrson_iv_sex, R.drawable.female);
		}

		psrson_tv_tab2.setText("点评 "
				+ userBean.getUser().getEntity_note_count());
		psrson_tv_tab1.setText("喜爱 " + userBean.getUser().getLike_count());
		psrson_tv_tab3.setText("标签 " + userBean.getUser().getTag_count());

		// BitmapUtil.setRoundImage(imageLoader, userBean.getUser()
		// .getAvatar_large(), options, psrson_iv_pic);

		psrson_lv_1.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
		psrson_lv_2.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));
		psrson_gv.setOnScrollListener(new PauseOnScrollListener(imageLoader,
				true, true));

		gvAdapter = new GridView3vAdapter(getActivity());
		
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
				imageLoader.displayImage(bean.getEntity().get240(),
						holder.person_item_iv_pic, options,
						new ImgUtils.AnimateFirstDisplayListener());
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
				Intent intent = new Intent(getActivity(), EntityAct.class);
				intent.putExtra("data", userBean.getUser().getUser_id());
				intent.putExtra("name", tagList.get(arg2).getTag());
				getActivity().startActivity(intent);
			}
		});

		initReceiver();
		getUserInfo();
	}

	@Override
	protected void setData() {
		if (userBean == null) {
			return;
		}
		noteList = new ArrayList<TabNoteBean>();
		likeList = new ArrayList<EntityBean>();
		tagList = new ArrayList<TagBean>();
		// sendConnection(Constant.TABLIKE + userBean.getUser().getUser_id()
		// + "/like/", new String[] { "count", "timestamp" },
		// new String[] { "30", System.currentTimeMillis() / 1000 + "" },
		// TABLIKE, false);
		Tab1(null);
		sv.setMode(Mode.BOTH);
		
		sv.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				time = System.currentTimeMillis() / 1000 + "";
				if (psrson_gv.getVisibility() == View.VISIBLE
						&& likeList != null && likeList.size() > 0) {
					sendConnection(Constant.TABLIKE
							+ userBean.getUser().getUser_id() + "/like/",
							new String[] { "count", "timestamp" },
							new String[] { "30", time }, TABLIKEADD, false);

				} else if (psrson_lv_1.getVisibility() == View.VISIBLE
						&& noteList != null && noteList.size() > 0) {

					sendConnection(
							Constant.TABLIKE + userBean.getUser().getUser_id()
									+ "/entity/note/", new String[] { "count",
									"timestamp" }, new String[] {
									"30",
									noteList.get(noteList.size() - 1).getNote()
											.getCreated_time() }, TABNOTEADD,
							false);

				} else if (psrson_lv_2.getVisibility() == View.VISIBLE) {
					sv.onRefreshComplete();
				} else
					sv.onRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				if (psrson_gv.getVisibility() == View.VISIBLE
						&& likeList != null && likeList.size() > 0) {
					sendConnection(Constant.TABLIKE
							+ userBean.getUser().getUser_id() + "/like/",
							new String[] { "count", "timestamp" },
							new String[] { "30", time }, TABLIKEADD, false);

				} else if (psrson_lv_1.getVisibility() == View.VISIBLE
						&& noteList != null && noteList.size() > 0) {

					sendConnection(
							Constant.TABLIKE + userBean.getUser().getUser_id()
									+ "/entity/note/", new String[] { "count",
									"timestamp" }, new String[] {
									"30",
									noteList.get(noteList.size() - 1).getNote()
											.getCreated_time() }, TABNOTEADD,
							false);

				} else if (psrson_lv_2.getVisibility() == View.VISIBLE) {
					sv.onRefreshComplete();
				} else
					sv.onRefreshComplete();
			}
		});

	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void setting(View v) {
		startActivity(new Intent(getActivity(), SettingAct.class));
	}

	// @Override
	// public void onStart() {
	// super.onStart();
	// init();
	// setData();
	// }

	@OnClick(R.id.psrson_ll_tab1)
	public void Tab1(View v) {
		sv.setMode(Mode.PULL_FROM_END);

		psrson_tv_tab1.setTextColor(Color.rgb(65, 66, 67));
		psrson_tv_tab2.setTextColor(Color.rgb(157, 158, 159));
		psrson_tv_tab3.setTextColor(Color.rgb(157, 158, 159));

		psrson_iv_tab1.setVisibility(View.VISIBLE);
		psrson_iv_tab2.setVisibility(View.INVISIBLE);
		psrson_iv_tab3.setVisibility(View.INVISIBLE);
		psrson_gv.setVisibility(View.VISIBLE);
		psrson_lv_1.setVisibility(View.GONE);
		psrson_lv_2.setVisibility(View.GONE);
		gvAdapter.setList(likeList);
		// if (likeList.size() <= 0) {
		sendConnection(Constant.TABLIKE + userBean.getUser().getUser_id()
				+ "/like/", new String[] { "count", "timestamp" },
				new String[] { "30", System.currentTimeMillis() / 1000 + "" },
				TABLIKE, false);
		// }
		// sv.smoothScrollTo(0, 0);
	}

	@OnClick(R.id.psrson_ll_tab2)
	public void Tab2(View v) {
		sv.setMode(Mode.PULL_FROM_END);

		psrson_tv_tab2.setTextColor(Color.rgb(65, 66, 67));
		psrson_tv_tab3.setTextColor(Color.rgb(157, 158, 159));
		psrson_tv_tab1.setTextColor(Color.rgb(157, 158, 159));

		psrson_iv_tab1.setVisibility(View.INVISIBLE);
		psrson_iv_tab2.setVisibility(View.VISIBLE);
		psrson_iv_tab3.setVisibility(View.INVISIBLE);

		psrson_gv.setVisibility(View.GONE);
		psrson_lv_1.setVisibility(View.VISIBLE);
		psrson_lv_2.setVisibility(View.GONE);

		lvAdapter.setList(noteList);
		// if (noteList.size() <= 0) {
		sendConnection(Constant.TABLIKE + userBean.getUser().getUser_id()
				+ "/entity/note/", new String[] { "count", "timestamp" },
				new String[] { "30", System.currentTimeMillis() / 1000 + "" },
				TABNOTE, false);
		// }
		// sv.smoothScrollTo(0, 0);
	}

	@OnClick(R.id.psrson_ll_tab3)
	public void Tab3(View v) {
		sv.setMode(Mode.DISABLED);

		psrson_tv_tab3.setTextColor(Color.rgb(65, 66, 67));
		psrson_tv_tab2.setTextColor(Color.rgb(157, 158, 159));
		psrson_tv_tab1.setTextColor(Color.rgb(157, 158, 159));

		psrson_iv_tab1.setVisibility(View.INVISIBLE);
		psrson_iv_tab2.setVisibility(View.INVISIBLE);
		psrson_iv_tab3.setVisibility(View.VISIBLE);

		psrson_gv.setVisibility(View.GONE);
		psrson_lv_1.setVisibility(View.GONE);
		psrson_lv_2.setVisibility(View.VISIBLE);

		tabAdapter.setList(tagList);
		// if (tagList.size() <= 0) {
		sendConnection(Constant.TABLIKE + userBean.getUser().getUser_id()
				+ "/tag/", new String[] { "count", "timestamp" }, new String[] {
				"30", System.currentTimeMillis() / 1000 + "" }, TABTAG, true);
		// }
		// sv.smoothScrollTo(0, 0);
		// sv.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
		//
		// @Override
		// public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
		//
		// }
		// });
	}

	@OnClick(R.id.psrson_no_sina)
	public void psrson_no_sina(View v) {
	}

	@OnClick(R.id.psrson_no_tao)
	public void psrson_no_taobao(View v) {
	}

	@OnClick(R.id.psrson_ll_btn)
	public void psrson_ll_btn(View v) {
		Intent intent = new Intent(context, UserInfoAct.class);
		startActivityForResult(intent, RESULT_CODE);
	}

	@OnClick(R.id.psrson_ll_follow)
	public void psrson_ll_follow(View v) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("data", userBean.getUser().getUser_id());
		intent.putExtra("url", "/following/");
		intent.putExtra("name", "关注");
		startActivity(intent);
	}

	@OnClick(R.id.psrson_ll_fans)
	public void psrson_ll_fans(View v) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("data", userBean.getUser().getUser_id());
		intent.putExtra("url", "/fan/");
		intent.putExtra("name", "粉丝");
		startActivity(intent);
	}

	@OnClick(R.id.psrson_no_btn)
	public void psrson_no_btn(View v) {
		startActivity(new Intent(getActivity(), RegisterAct.class));
	}

	@OnClick(R.id.psrson_no_login)
	public void psrson_no_login(View v) {
		Intent intent = new Intent(context, LoginAct.class);
		startActivity(intent);
	}

	private class LVViewHold {
		@ViewInject(R.id.person_item_iv_pic)
		ImageView person_item_iv_pic;

		@ViewInject(R.id.person_item_tv_context)
		TextView person_item_tv_context;

		@ViewInject(R.id.person_item_tv_time)
		TextView person_item_tv_time;

	}

	private class TBViewHold {
		@ViewInject(R.id.person_item3_iv_pic)
		ImageView person_item_iv_pic;

		@ViewInject(R.id.person_item3_tv_context)
		TextView person_item_tv_context;

		@ViewInject(R.id.person_item3_tv_time)
		TextView person_item_tv_time;

	}

	private void getUserInfo() {

		sendConnection(Constant.USERINFO + userBean.getUser().getUser_id()
				+ "/", new String[] {}, new String[] {}, USERINFO, false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		register();
	}

	private void initReceiver() {
		receiveBroadCast = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				if (userBean != null) {

					Bundle bundle = intent.getExtras();
					if (bundle != null) {
						switch (bundle.getInt(Constant.INTENT_ACTION_KEY)) {
						case Constant.INTENT_ACTION_VALUE_LIKE:
							sendConnection(
									Constant.TABLIKE
											+ userBean.getUser().getUser_id()
											+ "/like/",
									new String[] { "count", "timestamp" },
									new String[] {
											"30",
											System.currentTimeMillis() / 1000
													+ "" }, TABLIKE, false);
							break;
						case Constant.INTENT_ACTION_VALUE_FOLLOW:
							getUserInfo();
							break;

						default:
							break;
						}
					}
				}
			}
		};
	}

	private void register() {

		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.INTENT_ACTION);
		filter.setPriority(Integer.MAX_VALUE);
		context.registerReceiver(receiveBroadCast, filter);
	}

	/**
	 * 处理关注返回的事件
	 */
	private void refreshFollow(String result) {

		JSONObject root;
		try {
			root = new JSONObject(result);
			UserBean userBean = (UserBean) JSON.parseObject(
					root.getString("user"), UserBean.class);
			String count = userBean.getFollowing_count();
			userBean.setFollowing_count(count);
			SharePrenceUtil.setUserBean(context, this.userBean);
			psrson_tv_guanzhu.setText(userBean.getFollowing_count());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (receiveBroadCast != null) {
			context.unregisterReceiver(receiveBroadCast);
		}
	}

	private void setTextRightImg(TextView view, int id) {

		Drawable drawable = getResources().getDrawable(id);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		view.setCompoundDrawables(null, null, drawable, null);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_CODE) {

			userBean = EkwingApplication.getInstance().getBean();

			psrson_tv_name.setText(userBean.getUser().getNickname());
			psrson_tv_sign.setText(userBean.getUser().getBio());
			
			psrson_iv_pic.setImageURI(Uri.parse(userBean.getUser().get240()));

			if (userBean.getUser().getGender().equals("男")) {
				psrson_iv_sex.setTextColor(Color.rgb(19, 143, 215));
				setTextRightImg(psrson_iv_sex, R.drawable.male);
			} else {
				psrson_iv_sex.setTextColor(Color.rgb(253, 189, 217));
				setTextRightImg(psrson_iv_sex, R.drawable.female);
			}

		}
	}

}
