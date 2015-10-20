/**

 */
package com.guoku.guokuv4.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-8-10 下午6:26:37
 */
public class ImgUtils {

	/**
	 * 图片加载显示监听器
	 * 
	 * @author Administrator
	 * 
	 */
	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				// 是否第一次显示
				// boolean firstDisplay = !displayedImages.contains(imageUri);
				// if (firstDisplay) {
				// 图片淡入效果
				FadeInBitmapDisplayer.animate(imageView, 500);
				displayedImages.add(imageUri);
				// }
			}
		}
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Activity context, float dpValue) {
	 final float scale = context.getResources().getDisplayMetrics().density;
	 return (int) (dpValue * scale + 0.5f);
	}

}
