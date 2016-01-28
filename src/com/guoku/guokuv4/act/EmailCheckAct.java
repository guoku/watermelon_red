/**

 */
package com.guoku.guokuv4.act;

import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.my.ChangeEmailAct;
import com.guoku.guokuv4.utils.ToastUtil;
import com.guoku.guokuv4.view.CountDownButtonHelper;
import com.guoku.guokuv4.view.CountDownButtonHelper.OnFinishListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年1月27日 下午6:11:29 验证邮箱
 */
public class EmailCheckAct extends NetWorkActivity {

	private static final int EMAIL_CHACK = 1001;

	@ViewInject(R.id.send_num_bt)
	Button btSend;

	@ViewInject(R.id.tv_email)
	TextView tvEmail;

	@ViewInject(R.id.tv_email_hint)
	TextView tvEmailHint;
	
	CountDownButtonHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		EventBus.getDefault().register(this);

		setContentView(R.layout.activity_email_check);

		init();
	}

	private void init() {

		tvEmail.setText(GuokuApplication.getInstance().getBean().getUser().getEmail());
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case EMAIL_CHACK:
			tvEmailHint.setText(R.string.tv_send_email_already);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case EMAIL_CHACK:
			ToastUtil.show(this, getResources().getString(R.string.error_bad_request));
			break;

		default:
			break;
		}
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		setGCenter(true, getResources().getString(R.string.tv_check_email_title));
		setGLeft(true, R.drawable.back_selector);
	}

	@OnClick(R.id.send_num_bt)
	public void onSendClick(View view) {

		sendConnection(Constant.EMAIL_VERIFIED, new String[] {}, new String[] {}, EMAIL_CHACK, true);

		helper = new CountDownButtonHelper(this, btSend,
				getResources().getString(R.string.tv_send_email_again), 60, 1);
		helper.setOnFinishListener(new OnFinishListener() {

			@Override
			public void finish() {
				// Toast.makeText(EmailCheckAct.this, "倒计时结束",
				// Toast.LENGTH_SHORT).show();
			}
		});
		helper.start();

	}
	
	public void onEventMainThread(String email) {
		finish();
	}

	@OnClick(R.id.modify_email)
	public void onModifyClick(View view) {
		openActivity(ChangeEmailAct.class);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

}
