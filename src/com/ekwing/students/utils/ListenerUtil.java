package com.ekwing.students.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

public class ListenerUtil {

	public static TextWatcher EdTextListener(final EditText ed_text, final ImageView delete) {
		ed_text.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					delete.setVisibility(View.VISIBLE);
				} else {
					delete.setVisibility(View.GONE);
				}

			}
		});

		TextWatcher mTextWatcher = new TextWatcher() {
			private CharSequence temp;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				temp = s;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
//				Log.i("aaaa", "长度:" + temp.length());
				if (temp.length() > 0) {
					delete.setVisibility(View.VISIBLE);
				} else {
					delete.setVisibility(View.GONE);
				}
			}
		};
		return mTextWatcher;
	}
}
