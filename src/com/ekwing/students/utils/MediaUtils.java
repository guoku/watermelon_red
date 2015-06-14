package com.ekwing.students.utils;

import java.util.ArrayList;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.entity.RecordListBean;
import com.ekwing.students.entity.RetListBean;
import com.ekwing.students.entity.SoundListBean;

/**
 * 在线、本地播放工具类
 * 
 * @author think
 * 
 */
public class MediaUtils {
	public static final int WORDS_CONFIRM_LISTEN = 55;
	public static final int WORDS_START = 56;
	public static final int CONTINUE = 566;
	public static final int WORDS_PLAY_FINISH = 665;
	public static MediaPlayer mediaPlayer;
	private ArrayList<SoundListBean> list;
	private ArrayList<RecordListBean> listrecord;
	private ArrayList<String> writeLists;
	private int index;
	private int indexr;
	private Handler handler;
	private String tid;
	private ArrayList<RetListBean> id;
	private Context mcontext;
	private boolean isRecord;

	public void setFile(String tid, ArrayList<RetListBean> id) {
		this.tid = tid;
		this.id = id;
	}

	public void setList(ArrayList<SoundListBean> list) {
		isRecord = false;
		Logger.e("setList", "setList------------------------------>");
		index = 0;
		if (list == null) {
			list = new ArrayList<SoundListBean>();
		}
		this.list = list;
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(final MediaPlayer mp) {
				Logger.i("play", "start---->" + mp.getDuration());
				mp.start();
				SoundListBean info = MediaUtils.this.list.get(index);
				index++;

				if (info.getKeep() > 0) {
					mp.seekTo(info.getStart());

					MediaUtils.this.handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							if (!isRecord) {
								Logger.e("mp", "mp-------------->"
										+ mediaPlayer);
								if (mediaPlayer != null) {
									mediaPlayer.stop();
									if (MediaUtils.this.list.size() > index) {
										play(MediaUtils.this.list.get(index));
									} else {
										index = 0;
									}
								}
							}
						}
					}, info.getKeep());
				}
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (!isRecord) {
					if (MediaUtils.this.list.size() > index) {
						Logger.i("play", "next---->" + index);
						handler.sendEmptyMessage(Constant.ORINAL_STARTS_PLAY);
						play(MediaUtils.this.list.get(index));
					} else {
						handler.sendEmptyMessage(Constant.ORINAL_PLSYFINISH);
						index = 0;
					}
				}
			}
		});
		play(this.list.get(index));
	}

	/**
	 * 播放录音
	 * 
	 * @param list
	 */
	public void setListRecord(ArrayList<RecordListBean> list) {
		Logger.d(
				MediaUtils.class.getSimpleName(),
				"setListRecord------------------------------>"
						+ list.toString());
		isRecord = true;
		indexr = 0;
		this.listrecord = list;
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(final MediaPlayer mp) {
				mp.start();
				RecordListBean info = MediaUtils.this.listrecord.get(indexr);
				indexr++;
				if (info.getKeep() > 0) {
					mp.seekTo(info.getStart());
					MediaUtils.this.handler.postDelayed(new Runnable() {

						@Override
						public void run() {
							if (isRecord) {
								if (mp != null) {
									mp.stop();
									if (MediaUtils.this.listrecord.size() > indexr) {
										Logger.d("0", "0");
										playRecord(MediaUtils.this.listrecord
												.get(indexr));
									} else {
										indexr = 0;
									}
								}
							}
						}
					}, info.getKeep());
				}
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				if (isRecord) {
					if (MediaUtils.this.listrecord.size() > indexr) {
						Logger.e(MediaUtils.class.getSimpleName(),
								"setOnCompletionListener===ifindex=========>"
										+ indexr);
						handler.sendEmptyMessage(Constant.STARTS_PLAY);
						Logger.d("1", "1");
						playRecord(MediaUtils.this.listrecord.get(indexr));
					} else {
						Logger.e(MediaUtils.class.getSimpleName(), "播放完成---》"
								+ indexr);
						handler.sendEmptyMessage(Constant.PLSYFINISH);
						indexr = 0;
					}
				}
			}
		});
		Logger.d("2", "2");
		playRecord(this.listrecord.get(indexr));
	}

	protected void playRecord(RecordListBean info) {
		Logger.e("", "录音======info.url=============>" + info.getUrl());
		try {
			mediaPlayer.reset();
			if (info.getUrl() == null || "".equals(info.getUrl())) {
				return;
			} else if (info.getUrl().contains("http")) {
				Logger.e("playre", "http-======LezyoApplication=======----->"
						+ EkwingApplication.getInstance().getUid() + tid
						+ id.get(index).getId() + ".wav");
				if (DownUtils.fileIsExists2(EkwingApplication.getInstance()
						.getUid() + tid + id.get(indexr).getId() + ".wav")) {
					Logger.d("isFile", "http本地---------------->"
							+ EkwingApplication.getInstance().getUid() + tid
							+ id.get(indexr).getId() + ".wav");
					mediaPlayer.setDataSource(Constant.RECORD_PATH
							+ EkwingApplication.getInstance().getUid() + tid
							+ id.get(indexr).getId() + ".wav");
				} else {
					Logger.d("isFile", "http网络------------------------>");
					mediaPlayer.setDataSource(info.getUrl());
				}
			} else {
				Logger.e("",
						"playre---本地------------------------>" + info.getUrl());
				Logger.e("", "playre---本地------------------------>" + indexr);
				mediaPlayer.setDataSource(Constant.RECORD_PATH + info.getUrl()
						+ ".wav");
			}
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MediaUtils(Handler handler, Context context) {
		mediaPlayer = new MediaPlayer();
		mcontext = context;
		this.handler = handler;

		mediaPlayer.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
				ToastUtil.show(mcontext, "音频获取失败");
				MediaUtils.this.handler.sendEmptyMessage(Constant.MDEIA_ERROR);
				return false;
			}
		});
	}

	private void play(final SoundListBean info) {
		Logger.e("play", "援引------------------------->" + info.getUrl());
		try {
			mediaPlayer.reset();
			if (info.getUrl().contains("http")) {
				String voiceName = DownUtils
						.convertUrlToFileName(info.getUrl());
				if (DownUtils.fileIsExists(voiceName) == true) {
					mediaPlayer.setDataSource(Constant.SOUND_PATH + voiceName);
				} else {
					mediaPlayer.setDataSource(info.getUrl());
				}
			} else {
				mediaPlayer.setDataSource(Constant.SOUND_PATH + info.getUrl());
			}
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play(String path, final int start, final int end) {
		Logger.e("play", "play3333333333-path------------------------->" + path);
		Logger.e("play", "play3333333333-start------------------------->"
				+ start);
		Logger.e("play", "play3333333333-keep------------------------->" + end);
		try {
			mediaPlayer.reset();
			if (path.contains("http")) {
				String voiceName = DownUtils.convertUrlToFileName(path);
				if (DownUtils.fileIsExists(voiceName) == true) {
					Logger.e("play",
							"本地--true-->" + DownUtils.fileIsExists(voiceName));
					mediaPlayer.setDataSource(Constant.SOUND_PATH + voiceName);
				} else {
					Logger.e("play",
							"网络--false-->" + DownUtils.fileIsExists(path));
					mediaPlayer.setDataSource(path);
				}
			} else {
				// Logger.e("play", "play3333333333-------------------------->"
				// + path);
				mediaPlayer.setDataSource(Constant.SOUND_PATH + path);
			}
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(final MediaPlayer mp) {
					mp.start();
					if (start != 0 && end != 0) {
						mp.seekTo(start);
						handler.postDelayed(new Runnable() {

							@Override
							public void run() {
								try {
									if (mp != null && mp.isPlaying()) {
										mp.stop();
										handler.sendEmptyMessage(CONTINUE);
										Logger.e("run", "run=end============>"
												+ end);
									}
								} catch (Exception e) {
									Logger.e("MU", e.toString());
								}
							}
						}, end);
					}
				}
			});
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer arg0) {
					Logger.e("onfinish", "onfinish=============>");
					handler.sendEmptyMessage(CONTINUE);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isplaying() {
		if (mediaPlayer == null) {
			return false;
		}
		return mediaPlayer.isPlaying();
	}

	public void play(String path) {
		Logger.e("play", "play================>" + path);
		try {
			mediaPlayer.reset();
			if (path.contains("http")) {
				String voiceName = DownUtils.convertUrlToFileName(path);
				if (DownUtils.fileIsExists(voiceName) == true) {
					Logger.e("play",
							"本地--true-->" + DownUtils.fileIsExists(voiceName));
					mediaPlayer.setDataSource(Constant.SOUND_PATH + voiceName);
				} else {
					Logger.e("play",
							"网络--false-->" + DownUtils.fileIsExists(path));
					mediaPlayer.setDataSource(path);
				}

			} else {
				mediaPlayer.setDataSource(Constant.SOUND_PATH + path);
			}

			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(final MediaPlayer mp) {
					mp.start();
				}
			});

			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer arg0) {
					handler.sendEmptyMessage(WORDS_PLAY_FINISH);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playNetOrNative(String path) {
		// Logger.e("play", "play================>" + path);
		try {
			mediaPlayer.reset();
			if (path.contains("http")) {
				String voiceName = DownUtils.convertUrlToFileName(path);
				if (DownUtils.fileIsExists(voiceName) == true) {
					Logger.e("play",
							"本地--true-->" + DownUtils.fileIsExists(voiceName));
					mediaPlayer.setDataSource(Constant.SOUND_PATH + voiceName);
				} else {
					Logger.e("play",
							"网络--false-->" + DownUtils.fileIsExists(voiceName));
					Logger.e("play", "网络--false-=====path===->" + path);
					mediaPlayer.setDataSource(path);
				}

			} else {
				mediaPlayer.setDataSource(Constant.SOUND_PATH + path);
			}
			mediaPlayer.prepareAsync();
			// mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
			// @Override
			// public void onPrepared(final MediaPlayer mp) {
			// Logger.e("setOnPreparedListener",
			// "onPrepared-------------------->" + index);
			// mp.start();
			// }
			// });

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setListOriginal(ArrayList<String> list) {
		// Logger.e("setList", "setList------------------------------>");
		index = 0;
		if (list == null) {
			list = new ArrayList<String>();
		}
		this.writeLists = list;
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				handler.sendEmptyMessage(Constant.ORINAL_STARTS_PLAY);
				index++;
			}
		});
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				Logger.d("index", "222----------------->" + index);
				if (MediaUtils.this.writeLists.size() > index) {
					Logger.i("play", "next---->" + index);

					playNetOrNative(MediaUtils.this.writeLists.get(index));

				} else {
					handler.sendEmptyMessage(Constant.ORINAL_PLSYFINISH);
					index = 0;
				}
			}
		});
		playNetOrNative(this.writeLists.get(index));
	}

	/**
	 * 根据地址播放音频： 带http，则播放网络，播放网络之前会判断本地有没有这个音频，有则播放本地，没有则播放网络 不带http、则直接播放本地
	 * 
	 * @param path
	 */
	public void playByPath(final Handler handler, String path) {
		try {
			mediaPlayer.reset();
			if (path.contains("http")) {
				String voiceName = DownUtils.convertUrlToFileName(path);
				if (DownUtils.fileIsExists(voiceName) == true) {
					Logger.i("play",
							"本地--true-->" + DownUtils.fileIsExists(voiceName));
					mediaPlayer.setDataSource(Constant.SOUND_PATH + voiceName);
				} else {
					Logger.i("play",
							"网络--false-->" + DownUtils.fileIsExists(voiceName));
					mediaPlayer.setDataSource(path);
				}

			} else {
				mediaPlayer.setDataSource(Constant.SOUND_PATH + path);
			}
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(final MediaPlayer mp) {
					mp.start();
					handler.sendEmptyMessage(Constant.FLUSH_STATUS_START);

				}
			});
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer arg0) {
					handler.sendEmptyMessage(Constant.FLUSH_STATUS);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 播放网络音频
	 * 
	 * @param handler
	 * @param path
	 */
	public void play(final Handler handler, String path) {
		Logger.e("", "播放网络音频===========>" + mediaPlayer);
		try {
			mediaPlayer.reset();
			if (path.contains("http")) {
				mediaPlayer.setDataSource(path);
			}
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(final MediaPlayer mp) {
					mp.start();
					handler.sendEmptyMessage(WORDS_START);
				}
			});
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer arg0) {
					handler.sendEmptyMessage(WORDS_CONFIRM_LISTEN);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pauseOrPlay() {
		Logger.e("pauseOrPlay", "pauseOrPlay------------------------------>");
		if (mediaPlayer != null && mediaPlayer.isPlaying())
			mediaPlayer.pause();
		else {
			mediaPlayer.start();
		}
	}

	public void pauseOrPlay(String path) {
		Logger.e("pauseOrPlay", "pauseOrPlay------------------------------>");
		if (mediaPlayer != null && mediaPlayer.isPlaying())
			mediaPlayer.pause();
		else {
			mediaPlayer.start();
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			// if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			Logger.e("", "stop=====================================");
			// }
			// mediaPlayer.release();
			handler.sendEmptyMessage(Constant.PLSYFINISH);
		}
	}

	public void destory() {
		Logger.e("", "destory=====================================");
		if (mediaPlayer != null) {
			// if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			// }
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
}
