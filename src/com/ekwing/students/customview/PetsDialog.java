package com.ekwing.students.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.guoku.R;

/**
 * VipDialog
 * 
 * @author di.chao
 * 
 */
public class PetsDialog extends Dialog {
	public PetsDialog(Context context) {
		super(context, R.style.CustomProgressDialog);

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
		// WindowManager.LayoutParams wl = getWindow().getAttributes();
		// wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		// wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		PetsDialog.this.setCancelable(false);
		PetsDialog.this.setCanceledOnTouchOutside(false);
		callBack.setListen();
	}
}
