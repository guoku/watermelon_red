package com.ekwing.students.utils;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.ekwing.students.customview.EvaluationDialog;
import com.ekwing.students.customview.EvaluationDialog.CallBack;
import com.guoku.R;

public class BackDialogUtils {

	public static void showBackDialog(final Activity articleListenActivity) {
		final EvaluationDialog dialog = new EvaluationDialog(articleListenActivity);
		View view = View.inflate(articleListenActivity, R.layout.exit_current_view_dialog, null);
		dialog.setView(view, new CallBack() {

			private TextView cancel;
			private TextView exit;

			@Override
			public void setListen() {
				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				exit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						articleListenActivity.finish();
					}
				});
			}

			@Override
			public void setControlView(View view) {
				exit = (TextView) view.findViewById(R.id.exit_confirm_tv);
				cancel = (TextView) view.findViewById(R.id.exit_cancle_tv);
			}
		});
		dialog.show();
	}
	
	
	public static void showBackDialog1(final EvaluationDialog dialog ,final Activity articleListenActivity,final OnClickListener itemclick) {
		View view = View.inflate(articleListenActivity, R.layout.exit_current_view_dialog, null);
		dialog.setView(view, new CallBack() {
			
			private TextView cancel;
			private TextView exit;
			
			@Override
			public void setListen() {
				cancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				exit.setOnClickListener(itemclick);
			}
			
			@Override
			public void setControlView(View view) {
				exit = (TextView) view.findViewById(R.id.exit_confirm_tv);
				cancel = (TextView) view.findViewById(R.id.exit_cancle_tv);
			}
		});
		dialog.show();
	}
}
