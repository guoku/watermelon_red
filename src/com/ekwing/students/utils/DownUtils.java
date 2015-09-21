package com.ekwing.students.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class DownUtils extends RequestCallBack<File> {
	public static final int DOWNSTART = 7;
	public static final int GIF = 20;
	public static final int SOUND = 21;
	public static final int DOWNFAIL = 8;
	public static final int DOWNLONDING = 9;
	public static final int DOWNSUCCESS = 10;

	private HttpUtils http;
	private ArrayList<String> urls;
	private int index = 0;
	private int method;
	private Handler handler;
	private boolean isloading = true;

	public DownUtils() {
		http = new HttpUtils();
	}

	public void downByUrl(final Context context, final String themeid,
			final String levelid, final String title, final String url,
			final Handler handler, final TextView v, final ImageView iv) {
		Logger.e("downByUrl", "downByUrl--------------------?" + url);
		this.handler = handler;
		http.download(url, Constant.BASE_PATH + "/db/"
				+ convertUrlToFileName(url), new RequestCallBack<File>() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				Logger.e("onStart", "onStart---------------------->");
				handler.sendEmptyMessage(DOWNSTART);
			}

			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// TODO Auto-generated method stub
				Logger.e("onSuccess", "onSuccess---------------------->");
				try {
					ZipUtil.unzip(Constant.BASE_PATH + "/db/"
							+ convertUrlToFileName(url), Constant.BASE_PATH);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				v.setText("训练模式");
				iv.setVisibility(View.GONE);
				// SharePrenceUtil.setUpdateExerciseLocalUrl(context, levelid,
				// url, title, themeid);
				handler.sendEmptyMessage(DOWNSUCCESS);

			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				Message message = Message.obtain();
				message.what = DOWNLONDING;
				// message.arg1 = (int) ((double) (current / total) * 100);
				// double percent = (double) ((int)current /(int) total);
				double aa = (double) current / total;
				Logger.e("onLoading", "aaaaaaaaaaaa-------------------->" + aa);

				String srt = Utils.decimal2Percent((double) current / total);
				Logger.e("onLoading", "cuttent-str--------------------->" + srt);
				Logger.e("onLoading", "percent---------------------->"
						+ (double) current / total);
				Logger.e("onLoading", "current---------------------->"
						+ current);
				Logger.e("onLoading", "total---------------------->" + total);
				v.setText("下载" + srt);

				handler.sendMessage(message);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Logger.e("onFailure", "onFailure---------------------->");
				handler.sendEmptyMessage(DOWNFAIL);
			}
		});
	}

	public void downByUrl(final Context context, final String themeid,
			final String levelid, final String title, final String url,
			final Handler handler, final TextView v) {
		Logger.e("downByUrl", "downByUrl--------------------?" + url);
		this.handler = handler;
		http.download(url, Constant.BASE_PATH + "/db/"
				+ convertUrlToFileName(url), new RequestCallBack<File>() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				Logger.e("onStart", "onStart---------------------->");
				handler.sendEmptyMessage(DOWNSTART);
			}

			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// TODO Auto-generated method stub
				Logger.e("onSuccess", "onSuccess---------------------->");
				try {
					ZipUtil.unzip(Constant.BASE_PATH + "/db/"
							+ convertUrlToFileName(url), Constant.BASE_PATH);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				v.setText("训练");
				// iv.setVisibility(View.GONE);
				// SharePrenceUtil.setUpdateExerciseLocalUrl(context, levelid,
				// url, title, themeid);
				handler.sendEmptyMessage(DOWNSUCCESS);

			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
				Message message = Message.obtain();
				message.what = DOWNLONDING;
				// message.arg1 = (int) ((double) (current / total) * 100);
				// double percent = (double) ((int)current /(int) total);
				double aa = (double) current / total;
				Logger.e("onLoading", "aaaaaaaaaaaa-------------------->" + aa);

				String srt = Utils.decimal2Percent((double) current / total);
				Logger.e("onLoading", "cuttent-str--------------------->" + srt);
				Logger.e("onLoading", "percent---------------------->"
						+ (double) current / total);
				Logger.e("onLoading", "current---------------------->"
						+ current);
				Logger.e("onLoading", "total---------------------->" + total);
				v.setText("下载" + srt);

				handler.sendMessage(message);
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Logger.e("onFailure", "onFailure---------------------->");
				handler.sendEmptyMessage(DOWNFAIL);
			}
		});
	}

	/**
	 * 听力理解查看解析页音频下载
	 * 
	 * @param url
	 * @param handler
	 * @param tv
	 */
	public void downListenQueryAudio(final String url, final Handler handler,
			final TextView tv) {
		Logger.e("downListenQueryAudio",
				"downListenQueryAudio--------------------?" + url);
		this.handler = handler;
		// if (isloading) {
		Logger.e("downListenQueryAudio",
				"downListenQueryAudio---convertUrlToFileName1-----------------?"
						+ convertUrlToFileName(url));
		Logger.e("downListenQueryAudio",
				"downListenQueryAudio---convertUrlToFileName-----------------?"
						+ Constant.SOUND_PATH);
		Logger.e("downListenQueryAudio",
				"downListenQueryAudio---convertUrlToFileName-----------------?"
						+ Constant.SOUND_PATH + convertUrlToFileName(url));

		http.download(url, Constant.SOUND_PATH + convertUrlToFileName(url),
				new RequestCallBack<File>() {

					@Override
					public void onStart() {
						super.onStart();
						Logger.e("downListenQueryAudio",
								"onStart---------------------->");
					}

					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						Logger.e("downListenQueryAudio",
								"onSuccess---------------------->");
						tv.setText("播放");
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
						Message message = Message.obtain();
						message.what = DOWNLONDING;
						double aa = (double) current / total;
						Logger.e("downListenQueryAudio",
								"onloading----aaaaaaaaaaaa-------------------->"
										+ aa);
						String srt = Utils.decimal2Percent((double) current
								/ total);
						tv.setText("下载" + srt);

						handler.sendMessage(message);
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// isloading = true;
						// ll.setClickable(true);
						// ll.setEnabled(true);
						// handler.sendEmptyMessage(DOWNFAIL);
					}
				});
		// }
	}

	public void downReading(final String url, final Handler handler,
			final LinearLayout ll, final TextView v, final ImageView iv,
			final int position) {
		Logger.e("downByUrl", "downByUrl--------------------?" + url);
		this.handler = handler;
		if (isloading) {
			http.download(url, Constant.BASE_PATH + "/sound/"
					+ convertUrlToFileName(url), new RequestCallBack<File>() {

				@Override
				public void onStart() {
					super.onStart();
					Logger.e("onStart", "onStart---------------------->");
					isloading = false;
					ll.setEnabled(false);
					ll.setClickable(false);
					handler.sendEmptyMessage(DOWNSTART);
				}

				@Override
				public void onSuccess(ResponseInfo<File> arg0) {
					Logger.e("onSuccess", "onSuccess---------------------->");
					isloading = true;
					if (position == 0) {
						v.setText("A篇");
					} else if (position == 1) {
						v.setText("B篇");
					} else if (position == 2) {
						v.setText("C篇");
					}
					ll.setClickable(true);
					ll.setEnabled(true);
					iv.setVisibility(View.GONE);
					handler.sendEmptyMessage(DOWNSUCCESS);
				}

				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					Message message = Message.obtain();
					message.what = DOWNLONDING;
					double aa = (double) current / total;
					Logger.e("onLoading", "aaaaaaaaaaaa-------------------->"
							+ aa);

					String srt = Utils
							.decimal2Percent((double) current / total);
					v.setText("下载" + srt);

					handler.sendMessage(message);
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					isloading = true;
					ll.setClickable(true);
					ll.setEnabled(true);
					handler.sendEmptyMessage(DOWNFAIL);
				}
			});
		}
	}

	private void down() {
		if (urls.size() > index) {
			boolean isenable = false;
			if (method == GIF) {
				isenable = fileIsExistsInImages2(convertUrlToFileName(urls
						.get(index)));
				Logger.e("downUtils", "isenable===========>" + index);
				Logger.e("downUtils", "isenable===========>" + isenable);
				Logger.e("downUtils", "gif名字===========>"
						+ convertUrlToFileName(urls.get(index)));
				Logger.e("downUtils", "gif地址===========>" + urls.get(index));
			} else if (method == SOUND) {
				isenable = fileIsExists(convertUrlToFileName(urls.get(index)));
			}
			while (isenable) {
				// isenable = false;
				Message message = Message.obtain();
				message.what = DOWNLONDING;
				message.arg1 = index;
				handler.sendMessage(message);
				index = index + 1;
				if (urls.size() == index) {
					break;
				}
				if (method == GIF) {
					isenable = fileIsExistsInImages2(convertUrlToFileName(urls
							.get(index)));
				} else if (method == SOUND) {
					isenable = fileIsExists(convertUrlToFileName(urls
							.get(index)));
				}
			}
			if (urls.size() > index) {
				switch (method) {
				case GIF:
					Logger.d("downutils", "gif===index============>" + index);
					http.download(this.urls.get(index), Constant.IMAGES_PATH
							+ convertUrlToFileName(urls.get(index)), this);
					break;

				case SOUND:
					http.download(this.urls.get(index), Constant.SOUND_PATH
							+ convertUrlToFileName(urls.get(index)), this);
					break;
				}
			} else {
				handler.sendEmptyMessage(DOWNSUCCESS);
			}
		} else
			handler.sendEmptyMessage(DOWNSUCCESS);
	}

	public void setList(ArrayList<String> urls, Handler handler, int method) {
		if (urls == null) {
			urls = new ArrayList<String>();
		}
		this.urls = urls;
		this.handler = handler;
		this.method = method;
		down();
	}

	@Override
	public void onFailure(HttpException arg0, String arg1) {
		Logger.e("onFailure", "--onFailure----------------------->" + arg1);
		handler.sendEmptyMessage(DOWNFAIL);
	}

	@Override
	public void onStart() {
		Logger.e("onStart", "--onStart----------------------->");
		handler.sendEmptyMessage(DOWNSTART);
	}

	@Override
	public void onSuccess(ResponseInfo arg0) {
		// TODO Auto-generated method stub
		Logger.e("onSuccess", "--onSuccess----------------------->" + arg0);
		Logger.e("onSuccess", "--onSuccess-index---------------------->"
				+ index);
		if (index == urls.size()) {
			handler.sendEmptyMessage(DOWNSUCCESS);
			index = 0;
		} else {
			Message message = Message.obtain();
			message.what = DOWNLONDING;
			message.arg1 = index;
			handler.sendMessage(message);
			Logger.e("onSuccess", "--onSuccess===========>" + index);
			down();
		}
	}

	public static String convertUrlToFileNameLast(String url) {
		String[] strs = url.split("/");
		if (strs.length < 0) {
			return "";
		}
		return strs[strs.length - 1];
	}

	public static String convertUrlToFileName(String url) {
		if ("".equals(url) || !url.contains("/")) {
			return "";
		}
		String[] strs = url.split("/");
		if (strs.length < 0) {
			return "";
		}
		return strs[strs.length - 2] + strs[strs.length - 1];
	}

	public static String convertUrlToFileName1(String url) {
		String[] strs = url.split("/");
		String zipStr = strs[strs.length - 1];
		return zipStr.replace(".zip", "") + ".db";

	}

	public static boolean fileIsExists(String name) {
		try {
			File f = new File(Constant.SOUND_PATH + name);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean fileIsExists2(String name) {
		try {
			File f = new File(Constant.RECORD_PATH + name);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean fileIsExists1(String name) {
		try {
			File f = new File(Constant.BASE_PATH + "/db/" + name);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean fileIsExistsInImages(String name) {
		try {
			File f = new File(name);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean fileIsExistsInImages2(String name) {
		try {
			File f = new File(Constant.IMAGES_PATH + name);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
