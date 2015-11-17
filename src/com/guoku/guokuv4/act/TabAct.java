package com.guoku.guokuv4.act;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.guokuv4.adapter.EntityAdapter;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.TagBean;
import com.guoku.guokuv4.bean.TagTwo;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.GuokuUtil;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class TabAct extends NetWorkActivity implements OnClickListener,
		OnCheckedChangeListener {

	private static final int CATABLIST = 10;
	private static final int CATABLIST_UP = 11;
	private static final int PROINFO = 12;
	private static final int LIST = 1;
	private static final int GRID = 2;
	// private static final int TAG_CATEGORY = 3;// 分类
	public static final String CID = "CID";// 二级分类ui返回的选择分类
	public static final String SECOND_ACT_ONTENT = "SECOND_ACT_ONTENT";// 二级分类ACT

	// @ViewInject(R.id.scroll_view)
	// private ScrollView scrollView;
	
	@ViewInject(R.id.sv)
	private PullToRefreshScrollView sv;//gridview上拉刷新

	@ViewInject(R.id.layout_add_tag)
	LinearLayout layoutAddTag;// 标签layout

	@ViewInject(R.id.check_box_like_time)
	CheckBox cbLikeTime;// tab上的喜欢、时间切换按钮

	@ViewInject(R.id.check_box_show)
	CheckBox cbShow;// tab上的显示方式按钮

	@ViewInject(R.id.pull_listview)
	private PullToRefreshListView tab_lv;

	@ViewInject(R.id.tab_gv)
	private ScrollViewWithGridView tab_gv;

	@ViewInject(R.id.tab_tv_count)
	private TextView tab_tv_count;

	@ViewInject(R.id.check_box_lyout)
	private CheckBox cBoxLayout;

	@ViewInject(R.id.tv_what_def)
	private CheckBox tvWhatDef;// 默认搜索条件

	@ViewInject(R.id.tv_what)
	private CheckBox tvWhatlike;// 按喜爱

	@ViewInject(R.id.img_arrws)
	private ImageView arrowsImg;

	@ViewInject(R.id.view_back_black)
	private View backblack;

	private GridViewAdapter gvAdapter;
	private EntityAdapter lvAdapter;

	private String cid;
	private ArrayList<EntityBean> list;
	private String isLike;
	private int curTab = LIST;

	private Animation animationll;
	private Animation animationBackShow;
	private Animation animationBackHide;
	private boolean animIsRunning = false;

	private int animationTiem = 300;
	private boolean isWhat = false;// 用来纪录排序方式 false：默认按时间 true：喜欢

	TagBean tagBean;// 一级全部
	
	private String defWgat = TIME;//默认按时间排序
	
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);

		init();
	}

	private void init() {

		cBoxLayout.setOnCheckedChangeListener(this);
		tvWhatDef.setOnCheckedChangeListener(this);
		tvWhatlike.setOnCheckedChangeListener(this);

		cbLikeTime.setOnCheckedChangeListener(this);
		cbShow.setOnCheckedChangeListener(this);

		getTitleLayoutSeach().setVisibility(View.VISIBLE);
	}

	@Override
	protected void onSuccess(String result, int where) {
		tab_lv.onRefreshComplete();
		sv.onRefreshComplete();
		switch (where) {
		case CATABLIST:
			try {
				list = (ArrayList<EntityBean>) JSON.parseArray(result,
						EntityBean.class);
				gvAdapter.setList(list);
				lvAdapter.setList(list);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case CATABLIST_UP:
			try {
				ArrayList<EntityBean> lists = (ArrayList<EntityBean>) JSON.parseArray(result,
						EntityBean.class);
				list.addAll(lists);
				gvAdapter.addListsLast(lists);
				lvAdapter.addListsLast(lists);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case PROINFO:
			try {
				PInfoBean bean = ParseUtil.getPI(result);
				Intent intent = new Intent(context, ProductInfoAct.class);
				intent.putExtra("data", JSON.toJSONString(bean));
				startActivity(intent);
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
		tab_lv.onRefreshComplete();
		sv.onRefreshComplete();
	}

	@Override
	protected void setupData() {
		cid = getIntent().getStringExtra("data");
		setGCenter(true, getIntent().getStringExtra("name"));
		isLike = getIntent().getStringExtra("add");
		setGLeft(true, R.drawable.back_selector);
		list = new ArrayList<EntityBean>();

		gvAdapter = new GridViewAdapter(context, 3);
		lvAdapter = new EntityAdapter(context);

		// tab_gv.addHeaderView(v)

		tab_lv.setAdapter(lvAdapter);
		tab_gv.setAdapter(gvAdapter);
		
		tab_lv.setPullToRefreshOverScrollEnabled(false);
		tab_lv.setScrollingWhileRefreshingEnabled(false);
		tab_lv.setMode(Mode.BOTH);
		tab_lv.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page = 1;
				sendData(CATABLIST, false);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				if (list.size() > 0) {
					page ++;
					sendData(CATABLIST_UP, false);
				} else {
					// Toast
				}
			}
		});

		tab_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				arg2 -= 1;
				sendConnection(Constant.PROINFO + list.get(arg2).getEntity_id()
						+ "/", new String[] { "entity_id" },
						new String[] { list.get(arg2).getEntity_id() },
						PROINFO, true);
			}
		});

		tab_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO + list.get(arg2).getEntity_id()
						+ "/", new String[] { "entity_id" },
						new String[] { list.get(arg2).getEntity_id() },
						PROINFO, true);
			}
		});

		sendData(CATABLIST, true);// 默认按时间排序

		try {
			String result = SharePrenceUtil.getTab(mContext);
			if (!StringUtils.isEmpty(result)) {
				ArrayList<TagBean> tBean = (ArrayList<TagBean>) JSON
						.parseArray(result, TagBean.class);
				initTag(tBean);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		sv.setMode(Mode.BOTH);
		sv.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				page = 1;
				sendData(CATABLIST, false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				page ++;
				sendData(CATABLIST_UP, false);
			}
		});
	}

	@SuppressLint("NewApi")
	private void initTag(ArrayList<TagBean> tBean) {

		for (int i = 0; i < tBean.size(); i++) {

			if (String.valueOf(tBean.get(i).getGroup_id()).equals(cid)) {
				tagBean = tBean.get(i);
				for (int j = 0; j < 5; j++) {
					final TagTwo tagtwo = tBean.get(i).getContent().get(j);
					final TextView textView = new TextView(this);
					LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
					lParams.setMargins(5, 5, 5, 5);
					textView.setLayoutParams(lParams);
					textView.setText(tagtwo.getCategory_title());
					textView.setBackgroundResource(R.drawable.text_bg_box_gray);
					textView.setGravity(Gravity.CENTER);
					textView.setPadding(3, 3, 3, 3);
					textView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Bundle bundle = new Bundle();
							bundle.putSerializable(SECOND_ACT_ONTENT, tagtwo);
							openActivity(TabListSecondAct.class, bundle);
						}
					});
					layoutAddTag.addView(textView);
				}
			}
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SharePrenceUtil.setTAG(mContext, curTab);

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if (arg0 == cBoxLayout) {
			if (arg1) {
				curTab = LIST;
				tab_gv.setVisibility(View.GONE);
				tab_lv.setVisibility(View.VISIBLE);
			} else {
				curTab = GRID;
				tab_gv.setVisibility(View.VISIBLE);
				tab_lv.setVisibility(View.GONE);
			}
			if (tvWhatlike.getVisibility() == View.VISIBLE) {
				showView(tvWhatlike);
			}
			// scrollView.scrollTo(0, 0);
			// scrollView.smoothScrollTo(0, 0);

		}
		if (arg0 == tvWhatDef) {
			showView(tvWhatlike);
		}
		if (arg0 == tvWhatlike) {
			showView(tvWhatlike);
			whatText();
		}

		if (arg0 == cbLikeTime) {
			page = 1;
			if (arg1) {
				defWgat = LIKE;
				sendData(CATABLIST, true);
			} else {
				defWgat = TIME;
				sendData(CATABLIST, true);
			}
		}
		if (arg0 == cbShow) {
			if (arg1) {
				curTab = GRID;
				tab_gv.setVisibility(View.VISIBLE);
				tab_lv.setVisibility(View.GONE);
			} else {
				curTab = LIST;
				tab_gv.setVisibility(View.GONE);
				tab_lv.setVisibility(View.VISIBLE);
			}
		}
	}

	private void sendData(int tag, boolean isLoding) {
		
		sendConnection(Constant.CATAB + cid + "/selection/", new String[] {
				"page","size","sort"}, new String[] { String.valueOf(page),
				"30", defWgat}, tag, isLoding);

	}

	private void whatText() {
		if (isWhat) {
			isWhat = false;
			tvWhatDef.setText(R.string.tv_seach_time);
			tvWhatlike.setText(R.string.tv_seach_like);
			defWgat = TIME;
			sendData(CATABLIST, true);
		} else {
			isWhat = true;
			tvWhatDef.setText(R.string.tv_seach_like);
			tvWhatlike.setText(R.string.tv_seach_time);
			defWgat = LIKE;
			sendData(CATABLIST, true);
		}
	}

	private void showView(View view) {

		if (view.getVisibility() == View.VISIBLE) {
			animationll = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
					0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
			view.setVisibility(View.GONE);
			hideBackBlack();

		} else {
			view.setVisibility(View.VISIBLE);
			animationll = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
					Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
					-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
			showBackBlack();
		}
		animationll.setDuration(animationTiem);
		view.startAnimation(animationll);
	}

	public void showBackBlack() {
		if (animationBackShow == null) {
			backblack.setVisibility(View.VISIBLE);
			animationBackShow = new AlphaAnimation(0.0f, 1.0f);
			animationBackShow.setAnimationListener(animationShowListener);
		}
		animationBackShow.setDuration(animationTiem);
		backblack.startAnimation(animationBackShow);

		arrowsImg.setImageResource(R.drawable.arrws_close);
	}

	public void hideBackBlack() {
		if (animationBackHide == null) {
			animationBackHide = new AlphaAnimation(1.0f, 0.0f);
			animationBackHide.setAnimationListener(animationHideListener);
		}
		animationBackHide.setDuration(animationTiem);
		backblack.startAnimation(animationBackHide);

		arrowsImg.setImageResource(R.drawable.arrws_open);
	}

	AnimationListener animationShowListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			animIsRunning = true;
			backblack.setVisibility(View.VISIBLE);
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
			animIsRunning = false;
		}
	};

	@OnClick(R.id.tv_more)
	private void onClickMore(View v) {

		if (tagBean != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(TabAct.class.getName(), tagBean);
			openActivity(CategoryListAct.class, bundle);
		}
	}
}
