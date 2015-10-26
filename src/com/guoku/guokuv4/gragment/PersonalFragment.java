package com.guoku.guokuv4.gragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.CommentTalkAct;
import com.guoku.guokuv4.act.FansAct;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.RegisterAct;
import com.guoku.guokuv4.act.SettingAct;
import com.guoku.guokuv4.act.UserArticleListAct;
import com.guoku.guokuv4.act.UserCommentListAct;
import com.guoku.guokuv4.act.UserInfoAct;
import com.guoku.guokuv4.act.UserLikeListAct;
import com.guoku.guokuv4.act.UserTagListAct;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.adapter.ListImgLeftAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.BroadUtil;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.LayoutItemView;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.guoku.guokuv4.view.ScrollViewWithListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalFragment extends BaseFrament {

	public static final String INTENT_CODE = "INTENT_CODE";

	private static final int FOLLOW1 = 10;// 关注
	private static final int FOLLOW0 = 11;// 取消关注
	private static final int COMMENTLIST = 14;
	private static final int PROINFO = 13;
	private static final int USERINFO = 18;
	public static final int RESULT_CODE = 1001;

	private final int TABLIKE = 1002;// 喜欢
	private final int TABNOTE = 1003;// 点评

	private final String LIKE = "like";
	private final String NOTE = "entity/note";

	public boolean isUser;// 是否是非本人 true＝是

	@ViewInject(R.id.title_bar)
	LinearLayout titleBar;

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

	@ViewInject(R.id.tv_user_like)
	LayoutItemView userLike;// 用户的喜爱tab

	@ViewInject(R.id.tv_user_comment)
	LayoutItemView userComment;// 用户的点评tab

	@ViewInject(R.id.tv_user_article)
	LayoutItemView userArticle;// 用户的图文tab

	@ViewInject(R.id.tv_user_tag)
	LayoutItemView userTag;// 用户参与的标签tab

	@ViewInject(R.id.tv_user_article_zan)
	LayoutItemView userArticleZan;// 用户赞过的图文tab

	@ViewInject(R.id.psrson_ll_btn)
	private LinearLayout layout_edit;// 编辑个人资料

	@ViewInject(R.id.psrson_tv_btn)
	private TextView psrson_tv_btn;// 关注 or 编辑个人资料

	@ViewInject(R.id.psrson_iv_btn)
	private ImageView psrson_iv_btn;// 关注 or 编辑个人资料 icon

	@ViewInject(R.id.gridview_like)
	private ScrollViewWithGridView gridviewLike;// 喜欢 gridview

	@ViewInject(R.id.listview_commit)
	private ScrollViewWithListView listComment;// 点评 listview

	private GridViewAdapter gvAdapter;

	private ListImgLeftAdapter listImgLeftAdapter;// 左侧图片

	@ViewInject(R.id.psrson_tv_name)
	private TextView psrson_tv_name;
	@ViewInject(R.id.psrson_tv_sign)
	private TextView psrson_tv_sign;
	@ViewInject(R.id.psrson_tv_guanzhu)
	private TextView psrson_tv_guanzhu;

	public UserBean uBean;

	private BroadcastReceiver receiveBroadCast; // 用来处理其它ui操作的关注、喜欢等，保证数据急时同步
	
	private int temp;

	@Override
	protected int getContentId() {

		if (isUser) {
			return R.layout.fragment_personal;
		} else {
			if (GuokuApplication.getInstance().getBean() == null) {

				return R.layout.pserson_no_log;
			} else
				return R.layout.fragment_personal;
		}
	}

	@Override
	protected void setData() {
		if (uBean == null) {
			return;
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
		case FOLLOW0:
			setConcem();
			BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY,
					Constant.INTENT_ACTION_VALUE_FOLLOW);
			break;
		case FOLLOW1:
			ToastUtil.show(context, "关注成功");
			try {
				setConcem();
				BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY,
						Constant.INTENT_ACTION_VALUE_FOLLOW);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case TABLIKE:
			gvAdapter.setList(ParseUtil.getTabLikeList(result));
			break;
		case TABNOTE:
			listImgLeftAdapter.setList(ParseUtil.getTabNoteList(result));
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

		if (!isUser) {
			uBean = GuokuApplication.getInstance().getBean().getUser();
			iv_set.setVisibility(View.VISIBLE);
			tv_title.setText("我");
			iv_set.setImageResource(R.drawable.setting);
		} else {
			titleBar.setVisibility(View.GONE);
			iv_set.setVisibility(View.GONE);
			tabLeftImg.setImageResource(R.drawable.back);
			tabLeftImgLine.setVisibility(View.VISIBLE);
			title.setText(uBean.getNickname());

			setConcem();
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

		setUserTab();
		initReceiver();
		// getUserInfo();
		initLike();
		initConmment();
	}

	/**
	 * 初始化喜欢
	 */
	private void initLike() {

		gvAdapter = new GridViewAdapter(getActivity(), 4);
		gridviewLike.setAdapter(gvAdapter);
		gridviewLike.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				getShopInfo(gvAdapter.getItem(arg2).getEntity_id());
			}
		});

		getLikeData(LIKE, "4", TABLIKE);
	}

	/**
	 * 初始化点评
	 */
	private void initConmment() {

		listImgLeftAdapter = new ListImgLeftAdapter(getActivity());
		listComment.setAdapter(listImgLeftAdapter);
		listComment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				getShopInfo(listImgLeftAdapter.getItem(arg2).getEntity()
						.getEntity_id());
			}
		});

		getLikeData(NOTE, "3", TABNOTE);
	}

	/**
	 * 处理关注状态显示逻辑
	 */
	private void setConcem() {

		if (!StringUtils.isEmpty(uBean.getRelation())) {
			if (uBean.getRelation().equals("0")) {
				layout_edit.setBackgroundResource(R.drawable.blue_shap);
				psrson_tv_btn.setText("关注");
				psrson_iv_btn.setImageResource(R.drawable.add_to);
				psrson_tv_btn.setTextColor(Color.WHITE);
			}
			if (uBean.getRelation().equals("1")) {
				layout_edit.setBackgroundResource(R.drawable.tfz_shap);
				psrson_tv_btn.setText("已关注");
				psrson_iv_btn.setImageResource(R.drawable.hai_to);
				psrson_tv_btn.setTextColor(Color.argb(255, 19, 143, 215));
			} else if (uBean.getRelation().equals("3")) {
				layout_edit.setBackgroundResource(R.drawable.tfz_shap);
				psrson_tv_btn.setText("互相关注");
				psrson_iv_btn.setImageResource(R.drawable.to);
				psrson_tv_btn.setTextColor(Color.argb(255, 19, 143, 215));
			}
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
		if (isUser) {
			if (uBean.getRelation().equals("0")
					|| uBean.getRelation().equals("2")) {
				sendConnectionPost(Constant.FOLLOW + uBean.getUser_id()
						+ "/follow/1/", new String[] {}, new String[] {},
						FOLLOW1, false);
				uBean.setRelation("1");
			} else {
				sendConnectionPost(Constant.FOLLOW + uBean.getUser_id()
						+ "/follow/0/", new String[] {}, new String[] {},
						FOLLOW0, false);
				uBean.setRelation("0");
			}
		} else {
			Intent intent = new Intent(context, UserInfoAct.class);
			startActivityForResult(intent, RESULT_CODE);
		}
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

	@OnClick(R.id.tv_user_like)
	private void userLikeClick(View v) {
		if (!uBean.getLike_count().equals("0")) {
			onStartAct(UserLikeListAct.class, userLike.tv1
					.getText().toString());
		}
	}
	
	@OnClick(R.id.tv_user_comment)
	private void userCommentClick(View v) {
		if (!uBean.getEntity_note_count().equals("0")) {
			onStartAct(UserCommentListAct.class, userComment.tv1
					.getText().toString());
		}
	}
	
	@OnClick(R.id.tv_user_article)
	private void userArticleClick(View v) {
		if (!uBean.getArticle_count().equals("0")) {
			onStartAct(UserArticleListAct.class, userArticle.tv1
					.getText().toString());
		}
	}
	
	@OnClick(R.id.tv_user_tag)
	private void userTagClick(View v) {
		if (!uBean.getTag_count().equals("0")) {
			onStartAct(UserTagListAct.class, userTag.tv1
					.getText().toString());
		}
	}

	private void getUserInfo() {

		sendConnection(Constant.USERINFO + uBean.getUser_id() + "/",
				new String[] {}, new String[] {}, USERINFO, false);
	}
	
	private void onStartAct(Class<?> activity, String title){
		
		Bundle bundle = new Bundle();
		bundle.putString(this.getClass().getName(), title);
		bundle.putSerializable(INTENT_CODE, uBean);
		openActivity(activity, bundle);
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
							getLikeData(LIKE, "4", TABLIKE);
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

			uBean = GuokuApplication.getInstance().getBean().getUser();

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

	private void getLikeData(String value, String countValue, int net_tag) {
		sendConnection(Constant.TAB_USER + uBean.getUser_id() + "/" + value
				+ "/", new String[] { "count", "timestamp" }, new String[] {
				countValue, System.currentTimeMillis() / 1000 + "" }, net_tag,
				false);
	}

	private void setUserTab() {

		String tempStr;
		if (isUser) {
			tempStr = getActivity().getResources().getString(
					R.string.tv_user_he);
		} else {
			tempStr = getActivity().getResources().getString(
					R.string.tv_user_my);
		}

		userLike.tv1.setText(tempStr
				+ getActivity().getResources().getString(R.string.tv_user_like));
		userLike.tv2.setText(uBean.getLike_count());
		
		userComment.tv1.setText(tempStr
				+ getActivity().getResources().getString(
						R.string.tv_user_comment));
		userComment.tv2.setText(uBean.getEntity_note_count());
		
		userArticle.tv1.setText(tempStr
				+ getActivity().getResources().getString(
						R.string.tv_user_article));
		userArticle.tv2.setText(uBean.getArticle_count());
		
		userTag.tv1.setText(tempStr
				+ getActivity().getResources().getString(R.string.tv_user_tag));
		userTag.tv2.setText(uBean.getTag_count());
		
		userArticleZan.tv1.setText(tempStr
				+ getActivity().getResources().getString(
						R.string.tv_user_article_zan));
		userArticleZan.tv2.setText("555");
	}

	private void getShopInfo(String id) {
		sendConnection(Constant.PROINFO + id + "/",
				new String[] { "entity_id" }, new String[] { id }, PROINFO,
				true);
	}

}
