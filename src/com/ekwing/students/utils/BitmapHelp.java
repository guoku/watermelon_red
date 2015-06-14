package com.ekwing.students.utils;

import android.content.Context;
import android.view.animation.AlphaAnimation;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

/**
 * 拟单例模式，最多有3个图片加载器加载图片，避免OOM 
 * @author bu.xuesong
 */
public class BitmapHelp {
	
	private BitmapHelp() {
	}
	private static BitmapUtils bitmapUtils;
	/**
	 * 返回一个图片加载对象
	 * @param appContext
	 * @return
	 */
//	public static BitmapUtils getBitmapUtils(Context appContext) {
//		bitmapUtils = getBitMapUtils(bitmapUtils,appContext);
//		bitmapUtils.configDefaultLoadingImage(R.drawable.default_icon);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.default_icon);
//		return bitmapUtils;
//	}
//	private static BitmapUtils getBitMapUtils (BitmapUtils bitmapUtils,Context context) {
//		if (bitmapUtils == null) {
//			bitmapUtils = new BitmapUtils(context, getCachePath(Constant.CACHE_PATH, context));
//			bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
//			bitmapUtils.configMemoryCacheEnabled(true);
//			bitmapUtils.configDiskCacheEnabled(true);
//			bitmapUtils.configDefaultAutoRotation(true);
//			bitmapUtils.configThreadPoolSize(5);
//			AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
//			animation.setDuration(400);
//			bitmapUtils.configDefaultImageLoadAnimation(animation);
//		}
//		return bitmapUtils;
//	}
//	/**
//	 * 根据子路径获取程序缓存目录*改路径可能不存在
//	 * @param childPath 子路径
//	 * @param context
//	 * @return
//	 */
//	public static String getCachePath(String childPath , Context context) {
//		File cacheDir = StorageUtils.getCacheDirectory(context);
//	      //设置图片的缓冲目录
//		 cacheDir =  new File(cacheDir , childPath);
//		return cacheDir.getAbsolutePath();
//	}
	/**
	 * 根据默认图片获取一个display对象
	 * @param context
	 * @param defaultImg
	 * @return
	 */
	public static BitmapDisplayConfig getDisplayConifg (Context context , int defaultImg) {
		BitmapDisplayConfig config = new BitmapDisplayConfig();
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		 animation.setDuration(400);
		config.setAnimation(animation);
		config.setAutoRotation(true);
		config.setLoadFailedDrawable(context.getResources().getDrawable(defaultImg));
		config.setLoadingDrawable(context.getResources().getDrawable(defaultImg));
		return config;
	}
}
