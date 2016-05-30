/**

 */
package com.guoku.guokuv4.act;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.bean.LaunchBean;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月26日 下午2:35:57 引导页
 */
public class IntroAct extends NetWorkActivity {

	private final int PROINFO = 1001;
	private final int USERINFO = 1002;
	private final int TAG_PROINFO = 1003;


//	@ViewInject(R.id.text1)
//	TextView tvTitle;// 标题
//
//	@ViewInject(R.id.text2)
//	TextView tvContext;// 说明内容

	@ViewInject(R.id.img_launch)
	SimpleDraweeView simpleDraweeView;
	
	@ViewInject(R.id.close_launch)
	ImageView imgClose;

	@ViewInject(R.id.layout_ra)
	RelativeLayout reLayout;

	LaunchBean lBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(false);
		}
		mTintManager.setStatusBarTintEnabled(false);

		overridePendingTransition(R.anim.up_to_down_push_in, R.anim.up_to_down_push_out);

		setContentView(R.layout.activity_intro);

		this.setFinishOnTouchOutside(false);

		initLaunch();
	}

	private void initLaunch() {

		lBean = GuokuApplication.getInstance().getLaunchBean();

		String path = Constant.LAUNCH_PATH + StringUtils.setReplace(lBean.getLaunch_image_url());
		File file = new File(path);
		if (file.exists()) {
			// simpleDraweeView.setImageURI(Uri.parse("file://" + path));
			ViewGroup.LayoutParams params = simpleDraweeView.getLayoutParams();
			params.width = (GuokuApplication.screenW - 36) / 4  * 3;
			params.height = GuokuApplication.screenW - 36;
			simpleDraweeView.setLayoutParams(params);
			DraweeController controller = Fresco.newDraweeControllerBuilder().setAutoPlayAnimations(true)
					.setUri(Uri.parse("file://" + path)).build();
			simpleDraweeView.setController(controller);
			
			
			
			RelativeLayout.LayoutParams lp = (LayoutParams) imgClose.getLayoutParams(); 
//			params2.width = (GuokuApplication.screenW - 36) / 4  * 3 - 30;
//			params2.height = GuokuApplication.screenW - 36 - 30;
			lp.setMargins(0, 0,44, 44);
			imgClose.setLayoutParams(lp);
			
		}

//		tvTitle.setText(Html.fromHtml(lBean.getTitle()));
//
//		tvContext.setText(Html.fromHtml(lBean.getDescription()));

		// tvButton.setText(Html.fromHtml(lBean.getAction_title()));
	}

	@OnClick(R.id.img_launch)
	private void onViewClick(View view) {

		if (lBean.getAction().contains(Constant.ACTION_START) || lBean.getAction().contains(Constant.ACTION_CLOSE)) {
			umStatistics(Constant.UM_INTRO, Constant.ACTION_CLOSE, lBean.getAction());
			finishAct();
		} else if (lBean.getAction().contains(Constant.ACTION_ENTITY)) {
			String id = lBean.getAction().replace(Constant.ACTION_ENTITY, "");
			sendConnection(Constant.PROINFO + id, new String[] { "entity_id" }, new String[] { id }, PROINFO, true);
			umStatistics(Constant.UM_INTRO, id, lBean.getAction());
		} else if (lBean.getAction().contains(Constant.ACTION_HTTP)) {
			Bundle bundle = new Bundle();
			Sharebean sharebean = new Sharebean();
			sharebean.setAricleUrl(lBean.getAction());
			bundle.putSerializable(WebShareAct.class.getName(), sharebean);
			openActivity(WebShareAct.class, bundle);
			finishAct();
			
			umStatistics(Constant.UM_INTRO, Constant.ACTION_HTTP, lBean.getAction());

		} else if (lBean.getAction().contains(Constant.ACTION_USER)) {
			sendConnection(Constant.USERINFO + lBean.getAction().replace(Constant.ACTION_USER, ""), new String[] { "timestamp" },
					new String[] { System.currentTimeMillis() / 1000 + "" }, USERINFO, false);
			
			umStatistics(Constant.UM_INTRO, Constant.ACTION_USER, lBean.getAction());
		} else if (lBean.getAction().contains(Constant.ACTION_TAG)) {
			if (GuokuApplication.getInstance().getBean() == null) {
				openLogin();
			} else {
				String tagStr = lBean.getAction().replace(Constant.ACTION_TAG, "").trim().replace("#", "");
				Intent intent = new Intent(this, EntityAct.class);
				intent.putExtra("data", GuokuApplication.getInstance().getBean().getUser().getUser_id());
				intent.putExtra("name", tagStr);
				startActivity(intent);
			}
			finishAct();
			umStatistics(Constant.UM_INTRO,  Constant.ACTION_TAG, lBean.getAction());
		} else if (lBean.getAction().contains(Constant.ACTION_CATEGORY)) {
			// String categoryId = lBean.getAction().replace(ACTION_CATEGORY,
			// "");
			// sendConnection(Constant.PROINFO + categoryId,
			// new String[] { "entity_id" }, new String[] { categoryId },
			// TAG_PROINFO, true);
			finishAct();
			umStatistics(Constant.UM_INTRO, Constant.ACTION_CATEGORY, lBean.getAction());
		} else if (lBean.getAction().contains(Constant.ACTION_ARTICLE)) {
			Bundle bundle = new Bundle();
			Sharebean sharebean = new Sharebean();
			sharebean.setAricleUrl(lBean.getAction().replace(Constant.ACTION_ARTICLE, ""));
			bundle.putSerializable(WebShareAct.class.getName(), sharebean);
			openActivity(WebShareAct.class, bundle);
			finishAct();
			umStatistics(Constant.UM_INTRO, Constant.ACTION_ARTICLE, lBean.getAction());
		} else {
			finishAct();
			umStatistics(Constant.UM_INTRO, "0", "finishAct");
		}
	}

	@OnClick(R.id.close_launch)
	private void onViewClickClose(View view) {
		SharePrenceUtil.setLaunch(mContext, true);
		finish();
		overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void finishAct() {
		SharePrenceUtil.setLaunch(mContext, false);
		finish();
		overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (where) {
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			finishAct();
			break;
		case USERINFO:
			try {
				JSONObject root = new JSONObject(result);
				UserBean uBean = (UserBean) JSON.parseObject(root.getString("user"), UserBean.class);
				intent = new Intent(mContext, UserBaseFrament.class);
				intent.putExtra("data", uBean);
				startActivity(intent);
				finishAct();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case TAG_PROINFO:

			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case PROINFO:
			ToastUtil.show(mContext, "获取商品信息失败，请稍后再试");
			finishAct();
			break;
		case USERINFO:
			ToastUtil.show(mContext, "获取用户信息失败，请稍后再试");
			finishAct();
			break;

		default:
			break;
		}
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub

	}
}
