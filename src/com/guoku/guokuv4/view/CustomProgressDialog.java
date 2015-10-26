package com.guoku.guokuv4.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.guoku.R;

/**
 * 自定义进度对话框
 * 
 * @author di.chao
 * 
 */
public class CustomProgressDialog extends Dialog {
	// private Context context;

	public CustomProgressDialog(Context context) {
		super(context, R.style.CustomProgressDialog);
		// this.context = context;
		setContentView(R.layout.customdialoglayout);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}

	/**
	 * 提示内容
	 * 
	 * @param strMessage
	 * @return
	 * 
	 */
	public void setMessage(String strMessage) {
		TextView tvMsg = (TextView) this.findViewById(R.id.id_tv_loadingmsg);
		tvMsg.setText(strMessage);
	}

}
