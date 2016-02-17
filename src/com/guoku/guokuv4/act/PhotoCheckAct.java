/**

 */
package com.guoku.guokuv4.act;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年2月17日 上午11:47:01 查看大图
 */
public class PhotoCheckAct extends NetWorkActivity implements OnTouchListener {

	public static String img_uri;

	@ViewInject(R.id.fresco_img)
	SimpleDraweeView frescoImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.act_fade_in, R.anim.act_fade_out);
		img_uri = getIntent().getExtras().getString(PhotoCheckAct.class.getName());
		setContentView(R.layout.fresco_img);
		frescoImg.setImageURI(Uri.parse(img_uri));
		ViewGroup.LayoutParams params = frescoImg.getLayoutParams();
		params.height = GuokuApplication.screenW;
		params.width = GuokuApplication.screenW;
		frescoImg.setLayoutParams(params);
		frescoImg.setOnTouchListener(this);
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finishAct();
		}
		return super.onKeyDown(keyCode, event);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			finishAct();
			break;

		default:
			break;
		}
		return true;
	}
	
	private void finishAct() {
		finish();
		overridePendingTransition(R.anim.act_fade_in, R.anim.act_fade_out);
	}

}
