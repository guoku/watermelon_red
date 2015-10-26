package com.guoku.guokuv4.utils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.guoku.guokuv4.config.Logger;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

public class BitmapUtil {

	public static int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
		Bitmap returnBm = null;

		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (returnBm == null) {
			returnBm = bm;
		}
		if (bm != returnBm) {
			bm.recycle();
		}
		return returnBm;
	}

	public static <T extends View> void SetBitMapImage(final Context context,
			T v, String url, final int defortIc) {
		BitmapUtils utils = new BitmapUtils(context);
		utils.configDefaultLoadingImage(defortIc);
		utils.configDefaultLoadFailedImage(defortIc);
		utils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		utils.display(v, url, new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				Logger.e("onLoadCompleted", "onLoadCompleted");
				((ImageView) arg0).setImageBitmap(arg2);
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				Bitmap decodeResource = BitmapFactory.decodeResource(
						context.getResources(), defortIc);
				((ImageView) arg0).setImageBitmap(decodeResource);
				Logger.e("onLoadFailed", "onLoadFailed");
			}
		});
	}

	public static <T extends View> void SetBitMaps(final Context context, T v,
			String url, final int defortIc) {
		BitmapUtils utils = new BitmapUtils(context);
		utils.configDefaultLoadingImage(defortIc);
		utils.configDefaultLoadFailedImage(defortIc);
		utils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		utils.display(v, url, new BitmapLoadCallBack<View>() {

			@Override
			public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
					BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
				Logger.e("onLoadCompleted", "onLoadCompleted");
				((ImageView) arg0).setImageBitmap(arg2);
			}

			@Override
			public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
				Bitmap decodeResource = BitmapFactory.decodeResource(
						context.getResources(), defortIc);
				((ImageView) arg0).setImageBitmap(decodeResource);
				Logger.e("onLoadFailed", "onLoadFailed");
			}
		});
	}

	// public static <T extends View> void SetBitMapAndTheme(
	// final Context context, T v, String url, final int defortIc) {
	// BitmapUtils utils = new BitmapUtils(context);
	// utils.configDefaultLoadingImage(defortIc);
	// utils.configDefaultLoadFailedImage(defortIc);
	// utils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	// utils.display(v, url, new BitmapLoadCallBack<View>() {
	//
	// @Override
	// public void onLoadCompleted(View arg0, String arg1, Bitmap arg2,
	// BitmapDisplayConfig arg3, BitmapLoadFrom arg4) {
	// Logger.e("onLoadCompleted", "onLoadCompleted");
	// ((ImageView) arg0).setImageBitmap(toRoundBitmap(arg2));
	// }
	//
	// @Override
	// public void onLoadFailed(View arg0, String arg1, Drawable arg2) {
	// Bitmap decodeResource = BitmapFactory.decodeResource(
	// context.getResources(), defortIc);
	// ((ImageView) arg0)
	// .setImageBitmap(toRoundBitmap(decodeResource));
	// Logger.e("onLoadFailed", "onLoadFailed");
	// }
	// });
	// }

	// public static int getImgByName(String imageName) {
	// int reld = R.drawable.weather_icon11;
	// if (imageName.endsWith("png")) {
	// imageName = imageName.substring(0, imageName.indexOf("png") - 1);
	// }
	// try {
	// Class<?> c = R.drawable.class;
	// Field field = c.getField(imageName);
	// reld = (Integer) field.get(null);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return reld;
	// }
	//
	// public static int getMarkerImg(String typeStr) {
	// int reld = R.drawable.weather_icon11;
	// try {
	// Class<?> c = R.drawable.class;
	// Field field = c.getField(typeStr);
	// reld = (Integer) field.get(null);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return reld;
	// }

	public static int dip2pix(Context context, int dips) {
		int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
		return (dips * densityDpi) / 160;
	}

	public static int pix2dip(Context context, int pixs) {
		int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
		return (pixs * 160) / densityDpi;
	}

	/**
	 * 将图片保存在指定路径中
	 * 
	 * @param bitmap
	 * @param descPath
	 */
	public static void saveBitmap(Bitmap bitmap, String descPath) {
		File file = new File(descPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			try {
				bitmap.compress(CompressFormat.JPEG, 30, new FileOutputStream(
						file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	// /-------------------------
	// /**
	// * 获得一个displayeImageOptons对象
	// *
	// * @param defaultImage
	// * @return
	// */
	// public static DisplayImageOptions getDisplayImageOptions(int
	// defaultImage) {
	// DisplayImageOptions options = new
	// DisplayImageOptions.Builder().showImageForEmptyUri(defaultImage).showImageOnLoading(defaultImage)
	// .showImageOnFail(defaultImage).resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(true)
	// .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
	// .displayer(new FadeInBitmapDisplayer(300)).build();
	// return options;
	// }
	//
	// public static DisplayImageOptions getDisplayImageOptions(Drawable
	// defaultImage) {
	// DisplayImageOptions options = new
	// DisplayImageOptions.Builder().resetViewBeforeLoading(false).showImageForEmptyUri(defaultImage)
	// .showImageOnLoading(defaultImage).showImageOnFail(defaultImage).cacheOnDisk(true).cacheInMemory(true)
	// .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
	// .displayer(new FadeInBitmapDisplayer(300)).build();
	// return options;
	// }

	// -------------------------------------------------------------------------------------------------------------------
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = 12;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	// /**
	// * 转换图片成圆形
	// *
	// * @param bitmap
	// * 传入Bitmap对象
	// * @return
	// */
	// public static Bitmap toRoundBitmap(Bitmap bitmap) {
	// if (bitmap == null) {
	// return null;
	// }
	// int width = bitmap.getWidth();
	// int height = bitmap.getHeight();
	// float roundPx;
	// float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
	// if (width <= height) {
	// roundPx = width / 2;
	// top = 0;
	// bottom = width;
	// left = 0;
	// right = width;
	// height = width;
	// dst_left = 0;
	// dst_top = 0;
	// dst_right = width;
	// dst_bottom = width;
	// } else {
	// roundPx = height / 2;
	// float clip = (width - height) / 2;
	// left = clip;
	// right = width - clip;
	// top = 0;
	// bottom = height;
	// width = height;
	// dst_left = 0;
	// dst_top = 0;
	// dst_right = height;
	// dst_bottom = height;
	// }
	// Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	// Canvas canvas = new Canvas(output);
	// final int color = 0xff424242;
	// final Paint paint = new Paint();
	// final Rect src = new Rect((int) left, (int) top, (int) right,
	// (int) bottom);
	// final Rect dst = new Rect((int) dst_left, (int) dst_top,
	// (int) dst_right, (int) dst_bottom);
	// final RectF rectF = new RectF(dst);
	// paint.setAntiAlias(true);
	// canvas.drawARGB(0, 0, 0, 0);
	// paint.setColor(color);
	// canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	// canvas.drawBitmap(bitmap, src, dst, paint);
	// return output;
	// }

	/**
	 * @param 将图片内容解析成字节数组
	 * @param inStream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	/**
	 * @param 将字节数组转换为ImageView可调用的Bitmap对象
	 * @param bytes
	 * @param opts
	 * @return Bitmap
	 */
	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	/**
	 * @param 图片缩放
	 * @param bitmap
	 *            对象
	 * @param w
	 *            要缩放的宽度
	 * @param h
	 *            要缩放的高度
	 * @return newBmp 新 Bitmap对象
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = (float) w / (float) width;
		float scaleHeight = (float) h / (float) height;
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newBmp;
	}

	public static Bitmap zoomBMP(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = (float) w / (float) width;
		float scaleHeight = (float) h / (float) height;
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newBmp;
	}

	/**
	 * 把Bitmap转Byte
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	@SuppressLint("NewApi")
	public static String getBitmapStrBase64(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, baos);
		byte[] bytes = baos.toByteArray();
		String data = Base64.encodeToString(bytes, 0, bytes.length,
				Base64.DEFAULT);
		return data;
	}

	/**
	 * 把字节数组保存为一个文件
	 */
	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {

		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);

		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);

		return bitmap;

	}

	public static void saveBitmap(String path, Bitmap bitmap) {
		getFileFromBytes(Bitmap2Bytes(bitmap), path);
	}

	/**
	 * 读取照片exif信息中的旋转角度<br/>
	 * http://www.eoeandroid.com/thread-196978-1-1.html
	 * 
	 * @param path
	 *            照片路径
	 * @return角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

}
