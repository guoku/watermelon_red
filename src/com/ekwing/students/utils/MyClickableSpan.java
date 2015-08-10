package com.ekwing.students.utils;

import android.text.style.ClickableSpan;

public abstract class MyClickableSpan extends ClickableSpan {
	public String text;

	public MyClickableSpan(String text) {
		this.text = text.replaceAll("#", "");
	}

}
