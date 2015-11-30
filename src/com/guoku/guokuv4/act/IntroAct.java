/**

 */
package com.guoku.guokuv4.act;

import java.io.File;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.BaseActivity;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.LaunchBean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.PInfoBean;
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
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月26日 下午2:35:57 引导页
 */
public class IntroAct extends NetWorkActivity {

	private final int PROINFO = 1001;

	private final String ACTION_START = "guoku://start";// 什么都不做，只关闭dialog
	private final String ACTION_ENTITY = "guoku://entity/";// 跳到商品
	private final String ACTION_HTTP = "http://";// 跳到webview

	@ViewInject(R.id.text1)
	TextView tvTitle;// 标题

	@ViewInject(R.id.text2)
	TextView tvContext;// 说明内容

	@ViewInject(R.id.img_launch)
	ImageView simpleDraweeView;

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
			simpleDraweeView.setImageURI(Uri.parse("file://" + path));
		}

		tvTitle.setText(lBean.getTitle());

		tvContext.setText(lBean.getDescription());

		tvButton.setText(lBean.getAction_title());

	}

	@OnClick(R.id.textView1)
	private void onViewClick(View view) {

		if (lBean.getAction().contains(ACTION_START)) {
			finishAct();
		} else if (lBean.getAction().contains(ACTION_ENTITY)) {
			String id = lBean.getAction().replace(ACTION_ENTITY, "");
			sendConnection(Constant.PROINFO + id + "/", new String[] { "entity_id" }, new String[] { id }, PROINFO,
					true);
		} else if (lBean.getAction().contains(ACTION_HTTP)) {
			Intent intent = new Intent(context, WebAct.class);
			intent.putExtra("data", lBean.getAction());
			startActivity(intent);
			finishAct();
		} else {
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
		switch (where) {
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);

			finishAct();
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

		default:
			break;
		}
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub

	}
}
