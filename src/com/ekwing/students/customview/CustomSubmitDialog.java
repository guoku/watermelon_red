package com.ekwing.students.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.ekwing.students.config.Constant;
import com.guoku.R;

/**
 * 提交未未完成通用dialog
 */
public class CustomSubmitDialog extends Dialog {
	private TextView submit_tv;
	private TextView submit_close_tv;
	private TextView submit_message_tv;
	private TextView name_tv;

	public CustomSubmitDialog(final Handler handler, Context context) {
		super(context, R.style.VipDialog);
		View view = View.inflate(context, R.layout.submit_dialog_layout, null);
		CustomSubmitDialog.this.setCancelable(true);
		CustomSubmitDialog.this.setCanceledOnTouchOutside(false);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setDimAmount(0.75f);
		// getWindow().setDimAmount(0.4f);
		WindowManager.LayoutParams wl = getWindow().getAttributes();
		// wl.width = Utils.getScreenWidth(context)-80;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		name_tv = (TextView) view.findViewById(R.id.name_tv);
		submit_message_tv = (TextView) view
				.findViewById(R.id.submit_message_tv);
		submit_close_tv = (TextView) view.findViewById(R.id.submit_close_tv);
		submit_tv = (TextView) view.findViewById(R.id.submit_open_tv);
		submit_tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CustomSubmitDialog.this.dismiss();
				handler.sendEmptyMessage(Constant.SUBMIT_HOMEWORK);
			}
		});

		submit_close_tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CustomSubmitDialog.this.dismiss();
			}
		});

		setContentView(view);
	}

	public void setMessage(String title, String message, String cancel,
			String submit) {
		name_tv.setText(title);
		submit_message_tv.setText(message);
		submit_close_tv.setText(cancel);
		submit_tv.setText(submit);
	}

}
