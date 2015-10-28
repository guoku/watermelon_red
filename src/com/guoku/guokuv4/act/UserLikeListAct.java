/**

 */
package com.guoku.guokuv4.act;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.adapter.ArrayListAdapter;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.adapter.TagTextAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.TagBean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.BitmapUtil;
import com.guoku.guokuv4.utils.LogGK;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-23 下午1:43:40 
 * 用户喜爱商品list
 */
public class UserLikeListAct extends NetWorkActivity{
	
	private final int TABLIKE = 1001;// 喜欢
	private final int PROINFO = 1002;
	
	@ViewInject(R.id.layout_comment_title)
	View viewTitle;
	
	@ViewInject(R.id.tv_tag_select)
	TextView tvSelect;
	
//	@ViewInject(R.id.layout_add)
//	LinearLayout lavoutAdd;
	
	@ViewInject(R.id.list_tag)
	ListView listTag;
	
	@ViewInject(R.id.sv)
	private PullToRefreshScrollView sv;//gridview上拉刷新
	
	@ViewInject(R.id.tab_gv)
	private ScrollViewWithGridView tab_gv;
	
	@ViewInject(R.id.view_back_black)
	View backblack;
	
	private TagTextAdapter tagAdapter;
	
	private GridViewAdapter gvAdapter;
	
	int countValue = 30;
	
	UserBean uBean;
	
	private Animation animationBackShow;
	private Animation animationBackHide;
	private Animation animationllShow;
	private Animation animationllHide;
	
	private final int animTime = 300;
	
	private boolean animIsRunning = false;
	
	private String cidTag = "0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_user_like_list);
		
		init();
		initComment();
	}
	
	private void init(){
		
		gvAdapter = new GridViewAdapter(mContext, 3);
		tab_gv.setAdapter(gvAdapter);
		tab_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				getShopInfo(gvAdapter.getItem(arg2).getEntity_id());
			}
		});
		
		sv.setMode(Mode.BOTH);
		sv.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				countValue = 30;
				getLikeData(TABLIKE, false);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				countValue += 30;
				getLikeData(TABLIKE, false);
			}
		});
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		sv.onRefreshComplete();
		switch (where) {
		case TABLIKE:
			gvAdapter.setList(ParseUtil.getTabLikeList(result));
			break;
		case PROINFO:
			openShopInfo(result);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		sv.onRefreshComplete();
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		String title = getIntent().getExtras().getString(PersonalFragment.class.getName());
		uBean = (UserBean) getIntent().getExtras().getSerializable(PersonalFragment.INTENT_CODE);
		getLikeData(TABLIKE, true);
		
		setGCenter(true, title);
		setGLeft(true, R.drawable.back_selector);
	}
	
	private void getLikeData(int netTag, boolean isDialog) {
		sendConnection(Constant.TAB_USER + uBean.getUser_id() + "/" + LIKE
				+ "/", new String[] { "count", "timestamp", "cid"}, new String[] {
				String.valueOf(countValue), System.currentTimeMillis() / 1000 + "", cidTag}, netTag,
				isDialog);
	}
	
	private void getShopInfo(String id) {
		sendConnection(Constant.PROINFO + id + "/",
				new String[] { "entity_id" }, new String[] { id }, PROINFO,
				true);
	}
	
	/**
	 * 初始化分类
	 */
	@SuppressLint("ResourceAsColor")
	private void initComment(){
		
		tagAdapter  = new TagTextAdapter(this);
		listTag.setAdapter(tagAdapter);
		
		try {
			String result = SharePrenceUtil.getTab(mContext);
			if (!StringUtils.isEmpty(result)) {
				ArrayList<TagBean> tBean = (ArrayList<TagBean>) JSON
						.parseArray(result, TagBean.class);
				
				ArrayList<TagBean> tagBeans = new ArrayList<TagBean>();
				for(int i = 0; i < 12; i ++){
					tagBeans.add(tBean.get(i));
				}
				
				tagAdapter.setList(tagBeans);
				
				//手动再第一个添加所有分类
				ArrayList<TagBean> tempBeanList = new ArrayList<TagBean>();
				TagBean tempBean = new TagBean();
				tempBean.setTitle(getResources().getString(R.string.tv_all));
				tempBeanList.add(tempBean);
				tagAdapter.addFirst(tempBeanList);
				
				ViewGroup.LayoutParams params = listTag.getLayoutParams(); 
				params.height = GuokuApplication.screenH / 3;
				listTag.setLayoutParams(params);
				
				listTag.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						// TODO Auto-generated method stub
						tvSelect.setText(tagAdapter.getItem(position).getTitle());
						hideSearchWhat();
						if(position == 0){
							cidTag = "0";
						}else{
							cidTag = String.valueOf(tagAdapter.getItem(position).getGroup_id());
						}
						getLikeData(TABLIKE, true);
					}
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick(R.id.layout_comment_title)
	private void inClickComment(View view){
		listTag.getBackground().setAlpha(230);
		if(listTag.getVisibility() == View.INVISIBLE){
			showSearchWhat();
		}else{
			hideSearchWhat();
		}
		
	}
	
	@OnClick(R.id.view_back_black)
	private void inClickBlack(View view){
		if(listTag.getVisibility() == View.VISIBLE){
			hideSearchWhat();
		}
	}
	
	private void showSearchWhat() {
		showBackBlack();
		if (animationllShow == null) {
			animationllShow = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		}
		animationllShow.setDuration(animTime);
		listTag.startAnimation(animationllShow);
	}
	
	private void hideSearchWhat() {
		hideBackBlack();
		if (animationllHide == null) {
			animationllHide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
		}
		animationllHide.setDuration(animTime);
		listTag.startAnimation(animationllHide);
	}
	
	private void showBackBlack() {
		if (animationBackShow == null) {
			backblack.setVisibility(View.VISIBLE);
			animationBackShow = new AlphaAnimation(0.0f, 1.0f);
			animationBackShow.setAnimationListener(animationShowListener);
		}
		animationBackShow.setDuration(animTime);
		backblack.startAnimation(animationBackShow);
	}
	
	private void hideBackBlack() {
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
			listTag.setVisibility(View.VISIBLE);
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
			listTag.setVisibility(View.INVISIBLE);
			animIsRunning = false;
		}
	};
}
