package com.handmark.pulltorefresh.library.internal;

import com.ekwing.students.config.Logger;

import android.util.Log;

public class Utils {

	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		Logger.w(LOG_TAG, "You're using the deprecated " + depreacted
				+ " attr, please switch over to " + replacement);
	}

}
