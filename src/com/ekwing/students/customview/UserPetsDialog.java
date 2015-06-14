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
 * 
 * UserPetsDialog
 * @author ytbnkkf
 * 
 */
public class UserPetsDialog extends Dialog {
	private Context context;
	public UserPetsDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
		this.context = context;
	}

	public interface Call2Back {
		public void setControlView(View view);

		public void setListen();
	}

	public void setView(View view, Call2Back callBack) {
		this.setContentView(view);
		callBack.setControlView(view);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		getWindow().setDimAmount(0.75f);
		 WindowManager.LayoutParams wl = getWindow().getAttributes();
//		 wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
//		 wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		 wl.width = Utils.getScreenWidth(context)-100;
		 wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		UserPetsDialog.this.setCancelable(true);
		UserPetsDialog.this.setCanceledOnTouchOutside(true);
		callBack.setListen();
	}
}
