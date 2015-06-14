package com.ekwing.students.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;

import com.guoku.R;

/**
 * 倒计时的dialog
 * 
 */
public class CountDownDialog extends Dialog {
	public static final int COUNTDOWNTIME = 5464;
	private TextView countdown_tv;

	public CountDownDialog(final Handler handler, Context context) {
		super(context, R.style.CustomCountDownTimer);
		setContentView(R.layout.dialog_countdown_textview);
		countdown_tv = (TextView) findViewById(R.id.countdown_tv);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		// getWindow().setDimAmount(0.75f);
		this.setCancelable(false);
		this.setCanceledOnTouchOutside(false);
		// TimeCount countTime = new TimeCount(4 * 1000, 1000);
		//
		// countTime.start();
		new CountDownTimer(4 * 1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				countdown_tv.setText(millisUntilFinished / 1000 + "");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(COUNTDOWNTIME);
			}
		}.start();
	}
}
