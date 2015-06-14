package com.ekwing.students.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;

public class MySpannableString {

	public SpannableString getClickableSpan(String str, String from, String to,
			OnClickListener onClickListener1, OnClickListener onClickListener2) {

		SpannableString spanableInfo = new SpannableString(str);

		if (from != null && from.length() > 0) {
			int start, end;
			start = str.indexOf(from);
			end = start + from.length();
			spanableInfo.setSpan(new Clickable(onClickListener1), start, end,
					Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		if (to != null && to.length() > 0) {
			int start, end;
			start = str.indexOf(to);
			end = start + to.length();
			spanableInfo.setSpan(
					new ForegroundColorSpan(Color.rgb(157, 158, 159)), start,
					end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spanableInfo;
	}

	class Clickable extends ClickableSpan implements OnClickListener {
		private final View.OnClickListener mListener;

		public Clickable(View.OnClickListener l) {
			mListener = l;
		}

		@Override
		public void onClick(View v) {
			mListener.onClick(v);
		}
	}
	//
}