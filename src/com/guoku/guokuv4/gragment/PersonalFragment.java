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
import android.widget.LinearLayout;
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

	private static final int COMMENTLIST = 14;
	private static final int PROINFO = 13;
	private static final int USERINFO = 18;
	public static final int RESULT_CODE = 1001;
	
	public boolean isUser;//是否是非本人  true＝是

	@ViewInject(R.id.title_bar_centrt_tv)
	private TextView title;

	@ViewInject(R.id.psrson_iv_sex)
	private TextView psrson_iv_sex;

	@ViewInject(R.id.title_bar_rigth_iv)
	private ImageView iv_set;
	
	@ViewInject(R.id.title_bar_left_iv)
	private ImageView tabLeftImg;
	
	@ViewInject(R.id.title_bar_left_iv1)
	private ImageView tabLeftImgLine;

	@ViewInject(R.id.psrson_iv_pic)
	private SimpleDraweeView psrson_iv_pic;

	@ViewInject(R.id.psrson_tv_fans)
	private TextView psrson_tv_fans;

	@ViewInject(R.id.title_bar_centrt_tv)
	private TextView tv_title;
	
	@ViewInject(R.id.psrson_ll_btn)
	private LinearLayout layout_edit;//编辑个人资料

	@ViewInject(R.id.psrson_tv_name)
	private TextView psrson_tv_name;
	@ViewInject(R.id.psrson_tv_sign)
	private TextView psrson_tv_sign;
	@ViewInject(R.id.psrson_tv_guanzhu)
	private TextView psrson_tv_guanzhu;
	
	public UserBean uBean;

	private BroadcastReceiver receiveBroadCast; // 用来处理其它ui操作的关注、喜欢等，保证数据急时同步

	@Override
	protected int getContentId() {
		
		if(isUser){
			return R.layout.fragment_personal;
		}else{
			if (EkwingApplication.getInstance().getBean() == null) {

				return R.layout.pserson_no_log;
			} else
				return R.layout.fragment_personal;
		}
	}

	@Override
	protected void onSuccess(String result, int where) {
		switch (where) {
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
		
		if(!isUser){
			uBean = EkwingApplication.getInstance().getBean().getUser();
		}
		
		if(isUser){
			iv_set.setVisibility(View.GONE);
			tabLeftImg.setImageResource(R.drawable.back);
			tabLeftImgLine.setVisibility(View.VISIBLE);
			layout_edit.setVisibility(View.GONE);
		}else{
			iv_set.setVisibility(View.VISIBLE);
			if (uBean == null) {
				iv_set.setImageResource(R.drawable.setting);
				title.setText("我");
				return;
			}
			tv_title.setText("我");
			iv_set.setImageResource(R.drawable.setting);
		}
		
		psrson_tv_fans.setText(uBean.getFan_count());
		psrson_tv_guanzhu.setText(uBean.getFollowing_count());

		psrson_tv_name.setText(uBean.getNickname());
		psrson_tv_sign.setText(uBean.getBio());
		
		psrson_iv_pic.setImageURI(Uri.parse(uBean.get240()));

		if (uBean.getGender().equals("男")) {
			psrson_iv_sex.setTextColor(Color.rgb(19, 143, 215));
			setTextRightImg(psrson_iv_sex, R.drawable.male);
		} else {
			psrson_iv_sex.setTextColor(Color.rgb(253, 189, 217));
			setTextRightImg(psrson_iv_sex, R.drawable.female);
		}

		initReceiver();
		getUserInfo();
	}

	@Override
	protected void setData() {
		if (uBean == null) {
			return;
		}
	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void setting(View v) {
		startActivity(new Intent(getActivity(), SettingAct.class));
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
		intent.putExtra("data", uBean.getUser_id());
		intent.putExtra("url", "/following/");
		intent.putExtra("name", "关注");
		startActivity(intent);
	}

	@OnClick(R.id.psrson_ll_fans)
	public void psrson_ll_fans(View v) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("data", uBean.getUser_id());
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
	
	@OnClick(R.id.title_bar_left_iv)
	public void onLeftClick(){
		getActivity().finish();
	}

	private void getUserInfo() {

		sendConnection(Constant.USERINFO + uBean.getUser_id()
				+ "/", new String[] {}, new String[] {}, USERINFO, false);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		register();
	}

	/**
	 * 喜欢收到的广播
	 */
	private void initReceiver() {
		receiveBroadCast = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {

				if (uBean != null) {

					Bundle bundle = intent.getExtras();
					if (bundle != null) {
						switch (bundle.getInt(Constant.INTENT_ACTION_KEY)) {
						case Constant.INTENT_ACTION_VALUE_LIKE:
//							sendConnection(
//									Constant.TABLIKE
//											+ uBean.getUser_id()
//											+ "/like/",
//									new String[] { "count", "timestamp" },
//									new String[] {
//											"30",
//											System.currentTimeMillis() / 1000
//													+ "" }, TABLIKE, false);
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
			AccountBean userAccountBean = new AccountBean();
			userAccountBean.setUser(userBean);
			SharePrenceUtil.setUserBean(context, userAccountBean);
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

			uBean = EkwingApplication.getInstance().getBean().getUser();

			psrson_tv_name.setText(uBean.getNickname());
			psrson_tv_sign.setText(uBean.getBio());
			
			psrson_iv_pic.setImageURI(Uri.parse(uBean.get240()));

			if (uBean.getGender().equals("男")) {
				psrson_iv_sex.setTextColor(Color.rgb(19, 143, 215));
				setTextRightImg(psrson_iv_sex, R.drawable.male);
			} else {
				psrson_iv_sex.setTextColor(Color.rgb(253, 189, 217));
				setTextRightImg(psrson_iv_sex, R.drawable.female);
			}

		}
	}

}
