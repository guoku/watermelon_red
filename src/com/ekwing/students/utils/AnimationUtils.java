package com.ekwing.students.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimationUtils {
	public static void setTransAnimation(View v,int a) {
		TranslateAnimation animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 9, Animation.RELATIVE_TO_SELF, 0);
		animation.setDuration(a);
		animation.setFillAfter(true);
		v.setAnimation(animation);
	}
}
