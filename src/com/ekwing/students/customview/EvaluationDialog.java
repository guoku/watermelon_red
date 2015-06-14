package com.ekwing.students.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.ekwing.students.utils.Utils;
import com.guoku.R;

/**
 * VipDialog
 * 
 * @author di.chao
 * 
 */
public class EvaluationDialog extends Dialog {
	private Context context;

	public EvaluationDialog(Context context) {
		super(context, R.style.VipDialog);
		this.context = context;
	}

	public interface CallBack {
		public void setControlView(View view);

		public void setListen();
	}

	public void setView(View view, CallBack callBack) {
		this.setContentView(view);
		callBack.setControlView(view);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setDimAmount(0.75f);
		WindowManager.LayoutParams wl = getWindow().getAttributes();
		wl.width = Utils.getScreenWidth(context) - 80;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		EvaluationDialog.this.setCancelable(true);
		EvaluationDialog.this.setCanceledOnTouchOutside(true);
		callBack.setListen();
	}
}
