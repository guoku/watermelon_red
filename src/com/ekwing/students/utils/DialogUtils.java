package com.ekwing.students.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

public class DialogUtils {
	public static Dialog getEDialog(Context context, OnClickListener listener,
			String text, EditText editText) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle(text)
				.setIcon(android.R.drawable.ic_dialog_info).setView(editText)
				.setPositiveButton("确定", listener)
				.setNegativeButton("取消", null).create();
		return dialog;
	}

	public static Dialog getEDialog(Context context, OnClickListener listener,
			String title, String mess, EditText editText) {
		Dialog dialog = new AlertDialog.Builder(context).setTitle(title)
				.setMessage(mess).setIcon(android.R.drawable.ic_dialog_info)
				.setView(editText).setPositiveButton("确定", listener)
				.setNegativeButton("取消", null).create();
		return dialog;
	}

	public static AlertDialog listDialgo(final Context constant,
			final CharSequence[] items, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(constant);
		builder.setItems(items, listener);
		AlertDialog alert = builder.create();
		return alert;
	}

}
