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
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
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
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月26日 下午2:35:57 引导页
 */
public class IntroAct extends NetWorkActivity {
	
	public final static int CODE = 1000;

	private final int PROINFO = 1001;
	private final int USERINFO = 1002;
	private final int TAG_PROINFO = 1003;

	private final String ACTION_START = "guoku://start";// 什么都不做，只关闭dialog
	private final String ACTION_ENTITY = "guoku://entity";// 跳到商品
	private final String ACTION_HTTP = "http://";// 跳到webview
	private final String ACTION_USER = "guoku://user";// 跳到某个用户
	private final String ACTION_TAG = "guoku://tag";// 跳到某个标签页
	private final String ACTION_CATEGORY = "guoku://category";// 跳到品类
	private final String ACTION_ARTICLE = "guoku://article";// 跳到图文
	private final String ACTION_CLOSE = "guoku://close";// 关闭引导页
	private final String ACTION_PAGE = "guoku://page";// 跳到首页的第几页 从1开始

	@ViewInject(R.id.text1)
	TextView tvTitle;// 标题

	@ViewInject(R.id.text2)
	TextView tvContext;// 说明内容

	@ViewInject(R.id.img_launch)
	SimpleDraweeView simpleDraweeView;

	@ViewInject(R.id.textView1)
	TextView tvButton;

	LaunchBean lBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

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
//			simpleDraweeView.setImageURI(Uri.parse("file://" + path));
			
//			ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("file://" + path))
//			    .build();

			DraweeController controller = Fresco.newDraweeControllerBuilder()
//			    .setImageRequest(request)
			    .setAutoPlayAnimations(true)
			    .setUri(Uri.parse("file://" + path))
			    .build();
			simpleDraweeView.setController(controller);
		}

		tvTitle.setText(lBean.getTitle());

		tvContext.setText(lBean.getDescription());

		tvButton.setText(lBean.getAction_title());

	}

	@OnClick(R.id.textView1)
	private void onViewClick(View view) {

		if (lBean.getAction().contains(ACTION_START) || lBean.getAction().contains(ACTION_CLOSE)) {
			finishAct();
		} else if (lBean.getAction().contains(ACTION_ENTITY)) {
			String id = lBean.getAction().replace(ACTION_ENTITY, "");
			sendConnection(Constant.PROINFO + id, new String[] { "entity_id" }, new String[] { id }, PROINFO, true);
		} else if (lBean.getAction().contains(ACTION_HTTP)) {
			
			Bundle bundle = new Bundle();
			Sharebean sharebean = new Sharebean();
			sharebean.setAricleUrl(lBean.getAction());
			bundle.putSerializable(WebShareAct.class.getName(), sharebean);
			openActivity(WebShareAct.class, bundle);
			finishAct();
			
		} else if (lBean.getAction().contains(ACTION_USER)) {
			sendConnection(Constant.USERINFO + lBean.getAction().replace(ACTION_USER, ""), new String[] { "timestamp" },
					new String[] { System.currentTimeMillis() / 1000 + "" }, USERINFO, false);
		} else if (lBean.getAction().contains(ACTION_TAG)) {
			if (GuokuApplication.getInstance().getBean() == null) {
				openLogin();
			} else {
				String tagStr = lBean.getAction().replace(ACTION_TAG, "").trim().replace("#", "");
				Intent intent = new Intent(this, EntityAct.class);
				intent.putExtra("data", GuokuApplication.getInstance().getBean().getUser().getUser_id());
				intent.putExtra("name", tagStr);
				startActivity(intent);
			}
			finishAct();
		} else if (lBean.getAction().contains(ACTION_CATEGORY)) {
			// 品类需要单独接口，后续增加
			// String categoryId = lBean.getAction().replace(ACTION_CATEGORY,
			// "");
			// sendConnection(Constant.PROINFO + categoryId,
			// new String[] { "entity_id" }, new String[] { categoryId },
			// TAG_PROINFO, true);
		} else if (lBean.getAction().contains(ACTION_ARTICLE)) {
			Bundle bundle = new Bundle();
			Sharebean sharebean = new Sharebean();
			sharebean.setAricleUrl(lBean.getAction().replace(ACTION_ARTICLE, ""));
			bundle.putSerializable(WebShareAct.class.getName(), sharebean);
			openActivity(WebShareAct.class, bundle);
			finishAct();
		} else if (lBean.getAction().contains(ACTION_PAGE)) {
			Intent intent = new Intent();
			setResult(CODE, intent);
			finishAct();
		}
		else {
			finishAct();
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
