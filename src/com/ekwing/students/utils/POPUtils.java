package com.ekwing.students.utils;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class POPUtils {
	private static PopupWindow pop;

	public static void setPop(View v) {
		pop = new PopupWindow(v, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, false);
		pop.setBackgroundDrawable(new ColorDrawable(0));
		pop.setOutsideTouchable(true);
		pop.setFocusable(true);
	}

	public static void setListener(OnDismissListener listener) {
		pop.setOnDismissListener(listener);
	}

	public static void showLocation(View v, int Gravity) {
		if (pop.isShowing()) {
			dissMiss();
		} else
			pop.showAtLocation((View) v.getParent(), Gravity, 0, 0);
	}

	public static void show(View v) {
		if (pop.isShowing()) {
			dissMiss();
		} else
			pop.showAsDropDown(v);
	}

	public static void dissMiss() {
		pop.dismiss();
	}
}
