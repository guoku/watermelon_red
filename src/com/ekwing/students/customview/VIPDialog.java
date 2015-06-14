package com.ekwing.students.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.guoku.R;

/**
 * VipDialog
 * 
 * @author di.chao
 * 
 */
public class VIPDialog extends Dialog {
	public VIPDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
		setContentView(R.layout.vip_layout);
		TextView vip_confirm_btn = (TextView) findViewById(R.id.vip_confirm_btn);
		TextView id_loadingmsg = (TextView) findViewById(R.id.id_loadingmsg);
		id_loadingmsg.setText("当前还未开通VIP,请访问翼课网www.ekwing.com 开通付费。");
		setStringText(id_loadingmsg, Color.parseColor("#f74812"));
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setDimAmount(0.75f);
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);

		vip_confirm_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				VIPDialog.this.dismiss();
			}
		});
	}

	private void setStringText(TextView tv, int color) {
		SpannableStringBuilder builder = new SpannableStringBuilder(tv
				.getText().toString());
		ForegroundColorSpan blueSpan = new ForegroundColorSpan(color);
		builder.setSpan(blueSpan, 16, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv.setText(builder);
	}
}
