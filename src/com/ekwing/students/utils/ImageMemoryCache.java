package com.ekwing.students.utils;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageMemoryCache {
	/**
	 * 从内存读取数据�?度是�?��的，为了更大限度使用内存，这里使用了两层缓存�?硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存�
	 * ?
	 */
	private static final int SOFT_CACHE_SIZE = 15; // 软引用缓存容�?
	private static LruCache<String, Bitmap> mLruCache; // 硬引用缓�?
	private static LinkedHashMap<String, SoftReference<Bitmap>> mSoftCache; // 软引用缓�?
	private ImageFileCache fileCache;

	public ImageMemoryCache(Context context) {
		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		int cacheSize = 1024 * 1024 * memClass / 2; // 硬引用缓存容量，为系统可用内存的1/4
		mLruCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				if (value != null)
					return value.getRowBytes() * value.getHeight();
				else
					return 0;
			}

			@Override
			protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
				if (oldValue != null)
					// 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的图片转入此软引用缓存?
					mSoftCache.put(key, new SoftReference<Bitmap>(oldValue));
			}
		};
		mSoftCache = new LinkedHashMap<String, SoftReference<Bitmap>>(SOFT_CACHE_SIZE, 0.75f, true) {
			private static final long serialVersionUID = 6040103833179403725L;

			@Override
			protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
				if (size() > SOFT_CACHE_SIZE) {
					return true;
				}
				return false;
			}
		};
		fileCache = new ImageFileCache();
	}

	public void loadImage(final String url, final Handler handler, final ImageView view, final int default_image) {
		view.setImageResource(default_image);
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (url != null) {

					Bitmap bitmap = getBitmapFromCache(url);
					if (bitmap == null) {
						bitmap = fileCache.getImage(url);
						if (bitmap == null) {
							bitmap = ImageGetFromHttp.downloadBitmapOnly(url);
							if (bitmap != null) {
								fileCache.saveBitmap(bitmap, url);
								addBitmapToCache(url, bitmap);
							}
						} else {
							addBitmapToCache(url, bitmap);
						}
					}
				}
				handler.post(new Runnable() {

					@Override
					public void run() {
						if (getBitmapFromCache(url) != null) {
							view.setImageBitmap(getBitmapFromCache(url));
						} else {
							view.setImageResource(default_image);
						}

					}
				});
			}
		}).start();
	}

	public void loadImageBG(final String url, final Handler handler, final View view, final int default_image) {

		handler.post(new Runnable() {

			@Override
			public void run() {
				if (url != null) {
					Bitmap bitmap = getBitmapFromCache(url);
					if (bitmap == null) {
						bitmap = fileCache.getImage(url);
						if (bitmap == null) {

							// new Thread(new Runnable() {
							//
							// @Override
							// public void run() {
							// TODO Auto-generated method stub
							bitmap = ImageGetFromHttp.downloadBitmapOnly(url);
							if (bitmap != null) {
								fileCache.saveBitmap(bitmap, url);
								addBitmapToCache(url, bitmap);
							}
							// }
							// }).start();
							//

						} else {
							addBitmapToCache(url, bitmap);
						}
					}
				}
				handler.post(new Runnable() {

					@SuppressWarnings("deprecation")
					@Override
					public void run() {
						if (getBitmapFromCache(url) != null) {
							BitmapDrawable drawable = new BitmapDrawable(getBitmapFromCache(url));
							view.setBackgroundDrawable(drawable);
						} else {
							view.setBackgroundResource(default_image);
						}

					}
				});

			}
		});
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// if (url != null) {
		// Bitmap bitmap = getBitmapFromCache(url);
		// if (bitmap == null) {
		// bitmap = fileCache.getImage(url);
		// Logger.i("缓存", "获取文件缓存图片:" + bitmap);
		// if (bitmap == null) {
		// Logger.i("缓存", url.toString());
		// bitmap = ImageGetFromHttp.downloadBitmap(url);
		// if (bitmap != null) {
		// fileCache.saveBitmap(bitmap, url);
		// addBitmapToCache(url, bitmap);
		// }
		// } else {
		// addBitmapToCache(url, bitmap);
		// }
		// }
		// }
		// handler.post(new Runnable() {
		//
		// @SuppressWarnings("deprecation")
		// @Override
		// public void run() {
		// if (getBitmapFromCache(url) != null) {
		// BitmapDrawable drawable = new BitmapDrawable(
		// getBitmapFromCache(url));
		// view.setBackgroundDrawable(drawable);
		// } else {
		// view.setImageResource(default_image);
		// }
		//
		// }
		// });
		// }
		// }).start();
	}

	/**
	 * 从缓存中获取图片
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap;

		if (url == null) {
			return null;
		}
		// 先从硬引用缓存中获取
		synchronized (mLruCache) {
			bitmap = mLruCache.get(url);
			if (bitmap != null) {
				// 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是�?��被删�?
				mLruCache.remove(url);
				mLruCache.put(url, bitmap);
				return bitmap;
			}
		}
		// 如果硬引用缓存中找不到，到软引用缓存中找
		synchronized (mSoftCache) {
			SoftReference<Bitmap> bitmapReference = mSoftCache.get(url);
			if (bitmapReference != null) {
				bitmap = bitmapReference.get();
				if (bitmap != null) {
					// 将图片移回硬缓存
					mLruCache.put(url, bitmap);
					mSoftCache.remove(url);
					return bitmap;
				} else {
					mSoftCache.remove(url);
				}
			}
		}
		return null;
	}

	/**
	 * 添加图片到缓存?
	 */
	public void addBitmapToCache(String url, Bitmap bitmap) {
		if (bitmap != null) {
			synchronized (mLruCache) {
				mLruCache.put(url, bitmap);
			}
		}
	}

	public void clearCache() {
		mSoftCache.clear();
	}
}