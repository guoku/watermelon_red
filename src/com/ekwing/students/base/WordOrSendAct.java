//package com.ekwing.students.base;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Message;
//import android.view.View;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.aispeech.AIEngine;
//import com.aispeech.AIEngineHelper;
//import com.aispeech.AIRecorder;
//import com.ekwing.students.R;
//import com.ekwing.students.config.Constant;
//import com.ekwing.students.config.Logger;
//import com.ekwing.students.entity.WordsBean;
//import com.ekwing.students.utils.PlayerProgressBar;
//import com.ekwing.students.utils.TextUtils;
//
//public abstract class WordOrSendAct extends NetWorkActivity {
//
//	protected static final int SBC_RETORT = 100;
//	protected static final int SBC_SUCCESS = 101;
//	protected static final int SBC_Loading = 102;
//	protected static final int SBC_STOP = 103;
//	protected static final int RF = 104;
//
//	private AIRecorder recorder = null;
//	private long engine = 0;
//	private ExecutorService workerThread = Executors.newFixedThreadPool(1);
//	private String appKey = "1402652612000294";
//	private String secretKey = "2f0463776440f1cf44cc732520ada117";
//	private String userId = "this-is-user-id";
//	private String deviceId = "";
//	private String serialNumber = "";
//	private long waitEndTime;
//	private long waitStartTime;
//	protected int s = 0;
//	protected boolean sbcposition = true; // 思必驰返回数据先刷新界面的状态
//
//	protected int count, currentPage; // 判断是否为自动提交的变量
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		try {
//			init();
//		} catch (Exception e) {
//
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
////		recorder.ondestory();
//		if (engine != 0) {
//
//			AIEngine.aiengine_delete(engine);
//			engine = 0;
//			Logger.d(TAG, "engine deleted: " + engine);
//		}
//		if (recorder != null) {
//			recorder.stop();
//			recorder.ondestory();
//			recorder = null;
//		}
//	}
//
//	protected AIRecorder.Callback getCallback(final String reText) {
//		AIRecorder.Callback recorderCallback = new AIRecorder.Callback() {
//			public void onStarted() {
//				byte[] id = new byte[64];
//				String param = "{\"coreProvideType\": \"native\", \"app\": {\"userId\": \""
//						+ userId
//						+ "\"}, \"audio\": {\"audioType\": \"wav\", \"channel\": 1, \"sampleBytes\": 2, \"sampleRate\": 16000}, \"request\": {\"coreType\": \"en.sent.score\", \"refText\":\""
//						+ TextUtils.replaceBlank(reText)
//						+ "\", \"rank\": 100}}";
//				int rv = AIEngine.aiengine_start(engine, param, id, callback);
//
//				Logger.d(TAG, "engine start: " + rv);
//				Logger.d(TAG, "engine param: " + param);
//
//				runOnUiThread(new Runnable() {
//					public void run() {
//						mHandler.sendEmptyMessage(SBC_Loading);
//					}
//				});
//			}
//
//			public void onStopped() {
//				AIEngine.aiengine_stop(engine);
//				waitStartTime = System.currentTimeMillis();
//				Logger.d(TAG, "engine stopped");
//				runOnUiThread(new Runnable() {
//					public void run() {
//						mHandler.sendEmptyMessage(SBC_STOP);
//					}
//				});
//			}
//
//			public void onData(byte[] data, int size) {
//				AIEngine.aiengine_feed(engine, data, size);
//			}
//		};
//		return recorderCallback;
//	}
//
//	private AIEngine.aiengine_callback callback = new AIEngine.aiengine_callback() {
//
//		@Override
//		public int run(byte[] id, int type, byte[] data, int size) {
//
//			if (type == AIEngine.AIENGINE_MESSAGE_TYPE_JSON) {
//
//				final String result = new String(data, 0, size).trim();
//				if (recorder.isRunning()) {
//					recorder.stop();
//				}
//				waitEndTime = System.currentTimeMillis();
//				Logger.d(TAG, "wait time for result: "
//						+ (waitEndTime - waitStartTime));
//				runOnUiThread(new Runnable() {
//
//					public void run() {
//						Message msg = Message.obtain();
//						msg.what = SBC_RETORT;
//						msg.obj = result;
//						mHandler.sendMessage(msg);
//						Logger.d(TAG, result);
//					}
//				});
//
//			}
//			return 0;
//		}
//	};
//
//	public void runOnWorkerThread(Runnable runnable) {
//		workerThread.execute(runnable);
//	}
//
//	protected void init() {
//		runOnWorkerThread(new Runnable() {
//
//			public void run() {
//
//				if (engine == 0) {
//
//					byte buf[] = new byte[1024];
//					AIEngine.aiengine_get_device_id(buf,
//							getApplicationContext());
//					deviceId = new String(buf).trim();
//					Logger.d(TAG, "deviceId: " + deviceId);
//					String resourcePath = AIEngineHelper.extractResourceOnce(
//							getApplicationContext(), "aiengine.resource.zip",
//							true);
//					String provisionPath = AIEngineHelper.extractResourceOnce(
//							getApplicationContext(), "aiengine.provision",
//							false);
//					Logger.d(TAG, "provisionPath: " + provisionPath);
//
//					serialNumber = AIEngineHelper.registerDeviceOnce(
//							getApplicationContext(), appKey, secretKey,
//							deviceId, userId);
//					Logger.d(TAG, "serialNumber: " + serialNumber);
//
//					Logger.d(TAG, "TEST4" + "provisionPath:" + provisionPath);
//
//					String cfg = String
//							.format("{\"appKey\": \"%s\", \"secretKey\": \"%s\", \"serialNumber\": \"%s\", \"provision\": \"%s\", \"native\": {\"en.word.score\":{\"res\": \"%s\"},\"en.sent.score\": {\"res\": \"%s\"}}}",
//									appKey, secretKey, serialNumber,
//									provisionPath, new File(resourcePath,
//											"bin/eng.wrd.splp.1.6")
//											.getAbsolutePath(), new File(
//											resourcePath,
//											"bin/eng.snt.splp.0.10")
//											.getAbsolutePath());
//					Logger.e(TAG, "cfg:" + cfg);
//					engine = AIEngine
//							.aiengine_new(cfg, getApplicationContext());
//					Logger.e(TAG, "aiengine: " + engine);
//				}
//
//				if (recorder == null) {
//					recorder = new AIRecorder();
//					Logger.d(TAG, "airecorder: " + recorder);
//				}
//				mHandler.sendEmptyMessage(SBC_SUCCESS);
//			}
//		});
//	}
//
//	/**
//	 * 播放指定录音
//	 * 
//	 * @param path
//	 */
//	protected void PlayBack(String path) {
//		if (path == null || "".equals(path)) {
//			return;
//		}
//		recorder.playback(path);
//	}
//
//	// /**
//	// * 播放录音
//	// */
//	// protected void PlayBack() {
//	// recorder.playback();
//	// }
//
//	/**
//	 * 开始录音
//	 * 
//	 * @param name
//	 *            ：文件名
//	 * @param text
//	 *            ：测评句子
//	 */
//	protected void Play(String name, String text) {
//		if (engine == 0 || recorder == null) {
//			return;
//		}
//		String wavPath = Constant.RECORD_PATH + name + ".wav";
//		Logger.e("wavPath", "wavPath" + wavPath);
//		recorder.start(wavPath, getCallback(text));
//	}
//
//	protected void autoWordReadMode(final ArrayList<WordsBean> list,
//			final int position, final LinearLayout show_grade_static,
//			final PlayerProgressBar wordslisten_iv_record_voice,
//			final ImageView words_btn_listen_record, String resoundPath,
//			final ImageView include_words_shade) {
//		list.get(position).setReSoundPath(resoundPath);
//		Play(resoundPath, list.get(position).getRetext());
//		mHandler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				if (list.get(position).getSBCResult().getStatics().size() > 0) {
//					show_grade_static.setVisibility(View.VISIBLE);
//					words_btn_listen_record.setVisibility(View.VISIBLE);
//				}
//				Stop();
//				currentPage = position;
//				// isRecording = false;
//			}
//		}, list.get(position).getDur() * 1000);
//
//		wordslisten_iv_record_voice.setProgress(0);
//		new CountDownTimer(list.get(position).getDur() * 1000, (list.get(
//				position).getDur() * 1000) / 105) {
//			@Override
//			public void onTick(long millisUntilFinished) {
//				Logger.e(TAG, "onTick---------------->" + s);
//				wordslisten_iv_record_voice
//						.setImageResource(R.drawable.record_green);
//				wordslisten_iv_record_voice.setProgress(s);
//				s = s + 1;
//			}
//
//			@Override
//			public void onFinish() {
//				Logger.e(TAG, "onFinish---------------->" + s);
//				Logger.e(TAG, "onFinish---------------->"
//						+ wordslisten_iv_record_voice);
//				s = 0;
//				// v.setClickable(true);
//				// Stop();
//				sbcposition = true;
//				include_words_shade.setClickable(false);
//				wordslisten_iv_record_voice.setProgress(s);
//				wordslisten_iv_record_voice
//						.setImageResource(R.drawable.record_grey);
//			}
//		}.start();
//	}
//
//	protected void autoSendReadMode(final ArrayList<WordsBean> list,
//			final int position, final ImageView iv_1,
//			final PlayerProgressBar ppb,
//			final ImageView words_btn_listen_record, final String resoundPath,
//			final ImageView include_words_shade, final BaseAdapter adapter) {
//		Play(resoundPath, list.get(currentPage).getRetext());
//		mHandler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				if (list.get(currentPage).getSBCResult().getStatics().size() > 0) {
//					iv_1.setVisibility(View.VISIBLE);
//					ppb.setVisibility(View.VISIBLE);
//				}
//				Stop();
//				adapter.notifyDataSetChanged();
//			}
//		}, list.get(currentPage).getDur());
//		ppb.setProgress(0);
//		new CountDownTimer(list.get(currentPage).getDur(),
//				(list.get(currentPage).getDur()) / 10) {
//			@Override
//			public void onTick(long millisUntilFinished) {
//				Logger.e(TAG, "onTick---------------->" + s);
//				ppb.setImageResource(R.drawable.record_green);
//				ppb.setProgress(s);
//				s = s + 20;
//				mHandler.sendEmptyMessage(RF);
//				adapter.notifyDataSetChanged();
//			}
//
//			@Override
//			public void onFinish() {
//				s = 0;
//				sbcposition = true;
//				include_words_shade.setClickable(false);
//				ppb.setProgress(s);
//				ppb.setImageResource(R.drawable.record_grey);
//				adapter.notifyDataSetChanged();
//				mHandler.sendEmptyMessage(RF);
//			}
//		}.start();
//
//		// Play(resoundPath, list.get(currentPage).getRetext());
//		// mHandler.postDelayed(new Runnable() {
//		// @Override
//		// public void run() {
//		// if (list.get(currentPage).getSBCResult().getStatics().size() > 0) {
//		// show_grade_static.setVisibility(View.VISIBLE);
//		// wordslisten_iv_record_voice.setVisibility(View.VISIBLE);
//		// }
//		// Stop();
//		// adapter.notifyDataSetChanged();
//		// }
//		// }, list.get(currentPage).getDur());
//		// wordslisten_iv_record_voice.setProgress(0);
//		// new CountDownTimer(list.get(currentPage).getDur(),
//		// (list.get(currentPage).getDur()) / 10) {
//		// @Override
//		// public void onTick(long millisUntilFinished) {
//		// Logger.e(TAG, "onTick---------------->" + s);
//		// wordslisten_iv_record_voice
//		// .setImageResource(R.drawable.record_green);
//		// wordslisten_iv_record_voice.setProgress(s);
//		// s = s + 20;
//		// mHandler.sendEmptyMessage(RF);
//		// }
//		//
//		// @Override
//		// public void onFinish() {
//		// s = 0;
//		// sbcposition = true;
//		// include_words_shade.setClickable(false);
//		// wordslisten_iv_record_voice.setProgress(s);
//		// wordslisten_iv_record_voice
//		// .setImageResource(R.drawable.record_grey);
//		// mHandler.sendEmptyMessage(RF);
//		// }
//		// }.start();
//	}
//
//	/**
//	 * 停止
//	 */
//	protected void Stop() {
//		Logger.e(
//				"WordOrSendAct",
//				"WordOrSendAct-------------------------------------------------------------------");
//
//		if (engine == 0 || recorder == null) {
//			return;
//		}
//		recorder.stop();
//	}
//	
//	/**
//	 * 销毁
//	 */
//	protected void destoryRecord() {
//		Logger.e(
//				"destoryRecord",
//				"destoryRecord-------------------------------------------------------------------");
//		if (engine != 0) {
//
//			AIEngine.aiengine_delete(engine);
//			engine = 0;
//			Logger.d(TAG, "engine deleted: " + engine);
//		}
//		if (recorder != null) {
//			recorder.stop();
//			recorder.ondestory();
//			recorder = null;
//		}
//	}
//
// }
