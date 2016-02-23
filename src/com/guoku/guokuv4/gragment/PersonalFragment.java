package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.login.LoginService;
import com.alibaba.sdk.android.login.callback.LogoutCallback;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.CommentTalkAct;
import com.guoku.guokuv4.act.FansAct;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.act.PhotoCheckAct;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.RegisterAct;
import com.guoku.guokuv4.act.SettingAct;
import com.guoku.guokuv4.act.UserArticleListAct;
import com.guoku.guokuv4.act.UserCommentListAct;
import com.guoku.guokuv4.act.UserInfoAct;
import com.guoku.guokuv4.act.UserLikeListAct;
import com.guoku.guokuv4.act.UserTagListAct;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.adapter.ArticlesCategoryAdapter;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.adapter.ListImgLeftAdapter;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.Articles;
import com.guoku.guokuv4.bean.CommentsBean;
import com.guoku.guokuv4.bean.LikesBean;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.eventbus.FollowEB;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.LogGK;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.LayoutItemView;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.guoku.guokuv4.view.ScrollViewWithListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

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
	private final int TABARTICLE = 1004;// 图文
	public static final String IS_EMPTY = "IS_EMPTY";// //判断喜爱、点评、图文、标签等个数，来传给下一个act

	private final String LIKE = "like";
	private final String NOTE = "entity/note";
	private final String ARTICLE = "last_post_article";

	public int userType;// 0=本人（默认）1=普通用户 2=认证用户

	@ViewInject(R.id.title_bar)
	LinearLayout titleBar;

	@ViewInject(R.id.title_bar_centrt_tv)
	private TextView title;

	@ViewInject(R.id.psrson_iv_sex)
	private TextView psrson_iv_sex;

	@ViewInject(R.id.title_bar_rigth_iv)
	private ImageView iv_set;

	@ViewInject(R.id.red_round)
	private ImageView redRound;

	@ViewInject(R.id.title_bar_left_iv)
	private ImageView tabLeftImg;

	@ViewInject(R.id.title_bar_left_iv1)
	private ImageView tabLeftImgLine;

	@ViewInject(R.id.alibaba_wang)
	private ImageView ailWang;

	@ViewInject(R.id.alibaba_card)
	private ImageView ailCard;

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

	@ViewInject(R.id.listview_user_article)
	private ScrollViewWithListView listArticle;// 用户图文listview

	@ViewInject(R.id.listview_commit)
	private ScrollViewWithListView listComment;// 点评 listview

	@ViewInject(R.id.view_stub_user_authen)
	ViewStub viewArticleList;// 鉴权媒体认证用户

	@ViewInject(R.id.view_stub_user)
	View viewUserList;// 本人和普通用户

	ScrollViewWithListView listUserAuthon;// 鉴权媒体认证用户图文list

	ArticlesCategoryAdapter articlesAuthonAdapter;// 鉴权媒体认证用户图文adapter

	private GridViewAdapter gvAdapter;

	private ListImgLeftAdapter listImgLeftAdapter;// 左侧图片

	private ArticlesCategoryAdapter articlesAdapter;// 图文adapter

	@ViewInject(R.id.psrson_tv_name)
	private TextView psrson_tv_name;
	@ViewInject(R.id.psrson_tv_sign)
	private TextView psrson_tv_sign;
	@ViewInject(R.id.psrson_tv_guanzhu)
	private TextView psrson_tv_guanzhu;

	public UserBean uBean;

	private int temp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected int getContentId() {

		int layoutID;
		switch (userType) {
		case 0:
			if (GuokuApplication.getInstance().getBean() == null) {
				layoutID = R.layout.pserson_no_log;
			} else
				layoutID = R.layout.fragment_personal;
			break;
		case 1:
			layoutID = R.layout.fragment_personal;
			break;
		default:
			layoutID = R.layout.fragment_personal;
			break;
		}
		return layoutID;
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
			if (isAdded()) {
				PInfoBean bean = ParseUtil.getPI(result);
				intent = new Intent(context, ProductInfoAct.class);
				intent.putExtra("data", JSON.toJSONString(bean));
				startActivity(intent);
			}
			break;
		case USERINFO:
//			 if (userType == 0) {
			 refreshUserInfo(result);
//			 }
			break;
		case FOLLOW0:
			FollowEB fEb = new FollowEB();
			fEb.setFollow(false);
			EventBus.getDefault().post(fEb);
			break;
		case FOLLOW1:
			FollowEB fEb2 = new FollowEB();
			fEb2.setFollow(true);
			EventBus.getDefault().post(fEb2);
			break;
		case TABLIKE:
			gvAdapter.setList(ParseUtil.getTabLikeList(result));
			// userLike.tv2.setText(uBean.getLike_count());
			break;
		case TABARTICLE:
			ArrayList<Articles> arrayList = (ArrayList<Articles>) JSON.parseArray(result, Articles.class);
			articlesAdapter.setList(arrayList);
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

		switch (userType) {
		case 0:
			viewUserList.setVisibility(View.VISIBLE);
			uBean = GuokuApplication.getInstance().getBean().getUser();
			iv_set.setVisibility(View.VISIBLE);
			tv_title.setText("我");
			iv_set.setImageResource(R.drawable.setting);
			if (uBean.isMail_verified()) {
				redRound.setVisibility(View.GONE);
			} else {
				redRound.setVisibility(View.VISIBLE);
			}
			initUnUserAuthon();
			break;
		case 1:
			viewUserList.setVisibility(View.VISIBLE);
			initUnUser();
			initUnUserAuthon();
			break;
		case 2:
			viewUserList.setVisibility(View.GONE);
			initUnUser();
			viewArticleList.inflate();
			setTextRightImg(psrson_iv_sex, R.drawable.official);
			initUserAuthon();
			break;

		default:
			break;
		}
		psrson_tv_fans.setText(uBean.getFan_count());
		psrson_tv_guanzhu.setText(uBean.getFollowing_count());
		psrson_tv_name.setText(uBean.getNickname());
		psrson_tv_sign.setText(uBean.getBio());
		psrson_iv_pic.setImageURI(Uri.parse(uBean.get240()));

		getUserInfo();
	}

	/**
	 * 初始化非本人用户UI
	 */
	private void initUnUser() {

		titleBar.setVisibility(View.GONE);
		iv_set.setVisibility(View.GONE);
		tabLeftImg.setImageResource(R.drawable.back);
		tabLeftImgLine.setVisibility(View.VISIBLE);
		title.setText(uBean.getNickname());
		redRound.setVisibility(View.GONE);
		setConcem();
	}

	/**
	 * 初始化非鉴权用户逻辑&数据
	 */
	private void initUnUserAuthon() {

		if (uBean.getGender().equals("男")) {
			psrson_iv_sex.setTextColor(Color.rgb(19, 143, 215));
			setTextRightImg(psrson_iv_sex, R.drawable.male);
		} else {
			psrson_iv_sex.setTextColor(Color.rgb(253, 189, 217));
			setTextRightImg(psrson_iv_sex, R.drawable.female);
		}
		setUserTab();
		initLike();
		initArticle();
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
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				getShopInfo(gvAdapter.getItem(arg2).getEntity_id());
			}
		});

		getInitData(LIKE, "4", TABLIKE);
	}

	/***
	 * 初始化图文
	 */
	private void initArticle() {
		articlesAdapter = new ArticlesCategoryAdapter(getActivity());
		listArticle.setAdapter(articlesAdapter);
		listArticle.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				Sharebean sharebean = new Sharebean();
				sharebean.setTitle(articlesAdapter.getList().get(arg2).getTitle());
				sharebean.setContext(articlesAdapter.getList().get(arg2).getContent().substring(0, 50));
				sharebean.setAricleUrl(articlesAdapter.getList().get(arg2).getUrl());
				sharebean.setImgUrl(articlesAdapter.getList().get(arg2).getCover());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);

				openActivity(WebShareAct.class, bundle);
			}
		});

		getInitData(ARTICLE, "3", TABARTICLE);
	}

	/**
	 * 初始化点评
	 */
	private void initConmment() {

		listImgLeftAdapter = new ListImgLeftAdapter(getActivity());
		listComment.setAdapter(listImgLeftAdapter);
		listComment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				getShopInfo(listImgLeftAdapter.getItem(arg2).getEntity().getEntity_id());
			}
		});

		getInitData(NOTE, "3", TABNOTE);
	}

	/**
	 * 初始化鉴权媒体认证用户
	 */
	private void initUserAuthon() {
		listUserAuthon = (ScrollViewWithListView) contentView.findViewById(R.id.listView_article);
		articlesAuthonAdapter = new ArticlesCategoryAdapter(getActivity());
		listUserAuthon.setAdapter(articlesAdapter);
		listUserAuthon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				Sharebean sharebean = new Sharebean();
				sharebean.setTitle(articlesAdapter.getList().get(arg2).getTitle());
				sharebean.setContext(articlesAdapter.getList().get(arg2).getContent().substring(0, 50));
				sharebean.setAricleUrl(articlesAdapter.getList().get(arg2).getUrl());
				sharebean.setImgUrl(articlesAdapter.getList().get(arg2).getCover());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);

				openActivity(WebShareAct.class, bundle);
			}
		});
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

	@OnClick(R.id.psrson_iv_pic)
	public void onHeadImg(View v) {
		Bundle bundle = new Bundle();
		bundle.putString(PhotoCheckAct.class.getName(), uBean.get800());
		openActivity(PhotoCheckAct.class, bundle);
	}

	@OnClick(R.id.title_bar_rigth_iv)
	public void setting(View v) {
		startActivity(new Intent(context, SettingAct.class));
	}

	@OnClick(R.id.psrson_no_sina)
	public void psrson_no_sina(View v) {
	}

	@OnClick(R.id.psrson_no_tao)
	public void psrson_no_taobao(View v) {
	}

	@OnClick(R.id.psrson_ll_btn)
	public void psrson_ll_btn(View v) {
		if (userType == 0) {
			Intent intent = new Intent(context, UserInfoAct.class);
			startActivityForResult(intent, RESULT_CODE);
		} else {
			if (uBean.getRelation().equals("0") || uBean.getRelation().equals("2")) {
				sendConnectionPost(Constant.FOLLOW + uBean.getUser_id() + "/follow/1/", new String[] {},
						new String[] {}, FOLLOW1, false);
				uBean.setRelation("1");
			} else {
				sendConnectionPost(Constant.FOLLOW + uBean.getUser_id() + "/follow/0/", new String[] {},
						new String[] {}, FOLLOW0, false);
				uBean.setRelation("0");
			}
		}
	}

	@OnClick(R.id.psrson_ll_follow)
	public void psrson_ll_follow(View v) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("url", Constant.GETFANSLIST  + uBean.getUser_id() + "/following/");
		intent.putExtra("name", "关注");
		startActivity(intent);
	}

	@OnClick(R.id.psrson_ll_fans)
	public void psrson_ll_fans(View v) {
		Intent intent = new Intent(context, FansAct.class);
		intent.putExtra("url", Constant.GETFANSLIST  + uBean.getUser_id() + "/fan/");
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
		onStartAct(UserLikeListAct.class, userLike.tv1.getText().toString(), userLike.tv2.getText().toString());
	}

	@OnClick(R.id.tv_user_comment)
	private void userCommentClick(View v) {
		onStartAct(UserCommentListAct.class, userComment.tv1.getText().toString(),
				userComment.tv2.getText().toString());
	}

	@OnClick(R.id.tv_user_article)
	private void userArticleClick(View v) {
		onStartAct(UserArticleListAct.class, userArticle.tv1.getText().toString(),
				userArticle.tv2.getText().toString());
	}

	@OnClick(R.id.tv_user_tag)
	private void userTagClick(View v) {
		onStartAct(UserTagListAct.class, userTag.tv1.getText().toString(), userTag.tv2.getText().toString());
	}

	private void getUserInfo() {

		sendConnection(Constant.USERINFO + uBean.getUser_id() + "/", new String[] { "timestamp" },
				new String[] { System.currentTimeMillis() / 1000 + "" }, USERINFO, false);
	}

	private void onStartAct(Class<?> activity, String title, String count) {

		Bundle bundle = new Bundle();
		if (StringUtils.isEmpty(count)) {
			bundle.putBoolean(IS_EMPTY, true);
		} else {
			bundle.putBoolean(IS_EMPTY, false);
		}
		bundle.putString(this.getClass().getName(), title);
		bundle.putSerializable(INTENT_CODE, uBean);
		openActivity(activity, bundle);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * 刷新用户数据
	 */
	private void refreshUserInfo(String result) {

		JSONObject root;
		try {
			root = new JSONObject(result);

			uBean = (UserBean) JSON.parseObject(root.getString("user"), UserBean.class);
			if(userType == 0){
				AccountBean userAccountBean = new AccountBean();
				userAccountBean.setUser(uBean);
				if (!StringUtils.isEmpty(GuokuApplication.getInstance().getBean().getSession())) {
					userAccountBean.setSession(GuokuApplication.getInstance().getBean().getSession());
				}
				SharePrenceUtil.setUserBean(context, userAccountBean);
			}
			refreshUI();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void setTextRightImg(TextView view, int id) {

		Drawable drawable = getResources().getDrawable(id);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
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

	private void getInitData(String value, String countValue, int net_tag) {
		sendConnection(Constant.TAB_USER + uBean.getUser_id() + "/" + value + "/",
				new String[] { "count", "timestamp" },
				new String[] { countValue, System.currentTimeMillis() / 1000 + "" }, net_tag, false);
	}

	private void setUserTab() {

		String tempStr;
		if (userType == 0) {
			tempStr = getActivity().getResources().getString(R.string.tv_user_my);
		} else {
			tempStr = getActivity().getResources().getString(R.string.tv_user_he);
		}

		userLike.tv1.setText(tempStr + getActivity().getResources().getString(R.string.tv_user_like));
		userComment.tv1.setText(tempStr + getActivity().getResources().getString(R.string.tv_user_comment));
		userArticle.tv1.setText(tempStr + getActivity().getResources().getString(R.string.tv_user_article));
		userTag.tv1.setText(tempStr + getActivity().getResources().getString(R.string.tv_user_tag));
		userArticleZan.tv1.setText(tempStr + getActivity().getResources().getString(R.string.tv_user_article_zan));

		refreshUI();
	}

	private boolean isUnZero(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		if (str.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	private void getShopInfo(String id) {
		sendConnection(Constant.PROINFO + id + "/", new String[] { "entity_id" }, new String[] { id }, PROINFO, true);
	}

	private void refreshUI() {
		psrson_tv_guanzhu.setText(uBean.getFollowing_count());

		if (userType != 2) {
			if (isUnZero(uBean.getLike_count())) {
				userLike.tv2.setText(uBean.getLike_count());
			}
			if (isUnZero(uBean.getEntity_note_count())) {
				userComment.tv2.setText(uBean.getEntity_note_count());
			}
			if (isUnZero(uBean.getArticle_count())) {
				userArticle.tv2.setText(uBean.getArticle_count());
			}
			if (isUnZero(uBean.getTag_count())) {
				userTag.tv2.setText(uBean.getTag_count());
			}
			userArticleZan.tv2.setText("555");
		}

		if (userType != 0) {
			setConcem();
		}
	}

	/************** 阿里openIM ****************/

	private void initAliWang() {
		ailWang.setVisibility(View.VISIBLE);
		ailWang.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				openOpenAccountLogin();
			}
		});

		ailCard.setVisibility(View.VISIBLE);
		ailCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginOutAli();
			}
		});
	}

	/**
	 * open account 登录
	 */
	public void openOpenAccountLogin() {

		String userid = "13466452759";
		String password = "zylove59";
		IYWLoginService loginService = GuokuApplication.getInstance().getIMKit().getLoginService();
		YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
		loginService.login(loginParam, new IWxCallback() {

			@Override
			public void onSuccess(Object... arg0) {
				ToastUtil.show(getActivity(), "IM登录成功");
				LogGK.d("***********IM登录成功");
			}

			@Override
			public void onProgress(int arg0) {
				// TODO Auto-generated method stub
				ToastUtil.show(getActivity(), "正在登录");
			}

			@Override
			public void onError(int errCode, String description) {
				// 如果登录失败，errCode为错误码,description是错误的具体描述信息
				ToastUtil.show(getActivity(), "登录失败");
				LogGK.d("***********IM登录失败:" + description + errCode);
			}
		});

		// LoginService loginService =
		// AlibabaSDK.getService(LoginService.class);
		// loginService.showLogin(getActivity(), new LoginCallback() {
		//
		// @Override
		// public void onSuccess(Session session) {
		//
		// ToastUtil.show(getActivity(), "鉴权成功");
		// LogGK.d("***********鉴权成功");
		//
		// GuokuApplication.getInstance().initIMKit(session.getUser().nick,
		// AlibabaConfig.APP_KEY);
		// IYWLoginService loginService =
		// GuokuApplication.getInstance().getIMKit().getLoginService();
		//
		// loginService.login(null, new IWxCallback() {
		//
		// @Override
		// public void onSuccess(Object... arg0) {
		// // TODO Auto-generated method stub
		// ToastUtil.show(getActivity(), "IM登录成功");
		// LogGK.d("***********IM登录成功");
		// }
		//
		// @Override
		// public void onProgress(int arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onError(int arg0, String arg1) {
		// // TODO Auto-generated method stub
		// ToastUtil.show(getActivity(), "IM登录失败" + arg0 + arg1);
		// LogGK.d("***********IM登录失败" + arg0 + arg1);
		// }
		// });
		// }
		//
		// @Override
		// public void onFailure(int code, String message) {
		// ToastUtil.show(getActivity(), "授权取消");
		// LogGK.d("***********授权取消");
		// }
		// });
	}

	private void loginOutAli() {

		LoginService loginService = AlibabaSDK.getService(LoginService.class);

		loginService.logout(getActivity(), new LogoutCallback() {

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub

			}
		});
	}

	public void onEventMainThread(LikesBean likesBean) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
					getInitData(LIKE, "4", TABLIKE);
					getUserInfo();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

		getInitData(LIKE, "4", TABLIKE);
		getUserInfo();
	}

	public void onEventMainThread(CommentsBean commentsBean) {
		getUserInfo();
		getInitData(NOTE, "3", TABNOTE);
	}
	
	public void onEventMainThread(FollowEB fEb) {
		if (uBean != null) {
			setConcem();
			if(userType == 0){
				getUserInfo();
			}
		}
	}

}
