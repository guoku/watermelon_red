//package com.ekwing.students.base;
//
//import java.io.File;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import android.os.Message;
//
//import com.ekwing.students.config.Constant;
//import com.ekwing.students.config.Logger;
//import com.ekwing.students.utils.TextUtils;
//
//public class PredBaseAct extends NetWorkActivity {
//
//	protected static final int SBC_RETORT = 101;
//	protected static final int SBC_SUCCESS = 100;
//	protected static final int SBC_STOP = 103;
//	protected static final int SBC_Loading = 102;
//
//	private String TAG = "HelloStream";
//	private AIRecorder recorder = null;
//	private long engine = 0;
//	private ExecutorService workerThread = Executors.newFixedThreadPool(1);
//
//	private String appKey = "1402652612000294";
//	private String secretKey = "2f0463776440f1cf44cc732520ada117";
//	private String userId = "this-is-user-id";
//	private String deviceId = "";
//	private String serialNumber = "";
//	private long waitEndTime;
//	private long waitStartTime;
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//		try {
//			init();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		// recorder.ondestory();
//		if (engine != 0) {
//
//			AIEngine.aiengine_delete(engine);
//			engine = 0;
//			Logger.d(TAG, "engine deleted: " + engine);
//		}
//
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
//						+ "\"}, \"audio\": {\"audioType\": \"wav\", \"channel\": 1, \"sampleBytes\": 2, \"sampleRate\": 16000}, \"request\": {\"coreType\": \"en.pred.exam\", \"refText\":{\"lm\": \""
//						+ TextUtils.replaceBlank(reText) + "\"}, \"rank\": 100, \"precision\": 0.5}}";
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
//				Logger.d(TAG, "wait time for result: " + (waitEndTime - waitStartTime));
//				runOnUiThread(new Runnable() {
//
//					public void run() {
//						Message msg = Message.obtain();
//						msg.what = SBC_SUCCESS;
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
//					AIEngine.aiengine_get_device_id(buf, getApplicationContext());
//					deviceId = new String(buf).trim();
//					Logger.d(TAG, "deviceId: " + deviceId);
//					String resourcePath = AIEngineHelper.extractResourceOnce(getApplicationContext(), "aiengine.resource.zip", true);
//					String provisionPath = AIEngineHelper.extractResourceOnce(getApplicationContext(), "aiengine.provision", false);
//					Logger.d(TAG, "provisionPath: " + provisionPath);
//
//					serialNumber = AIEngineHelper.registerDeviceOnce(getApplicationContext(), appKey, secretKey, deviceId, userId);
//					Logger.d(TAG, "serialNumber: " + serialNumber);
//
//					Logger.d(TAG, "TEST4" + "provisionPath:" + provisionPath);
//
//					String cfg = String
//							.format("{\"appKey\": \"%s\", \"secretKey\": \"%s\", \"serialNumber\": \"%s\", \"provision\": \"%s\", \"native\": {\"en.pred.exam\":{\"res\": \"%s\"}}}",
//									appKey, secretKey, serialNumber, provisionPath, new File(resourcePath, "bin/eng.pred").getAbsolutePath());
//					Logger.e(TAG, "cfg:" + cfg);
//					engine = AIEngine.aiengine_new(cfg, getApplicationContext());
//					Logger.e(TAG, "aiengine: " + engine);
//				}
//
//				if (recorder == null) {
//					recorder = new AIRecorder();
//					Logger.d(TAG, "airecorder: " + recorder);
//				}
//				mHandler.sendEmptyMessage(SBC_RETORT);
//			}
//		});
//	}
//
//	protected void PlayBack(String path) {
//		recorder.playback(path);
//	}
//
//	protected void PlayBack() {
//		recorder.playback();
//	}
//
//	protected void Play(String name, String text) {
//		if (engine == 0 || recorder == null) {
//			return;
//		}
//		String wavPath = Constant.RECORD_PATH + name + ".wav";
//		recorder.start(wavPath, getCallback(text));
//	}
//
//	protected void Stop() {
//		if (engine == 0 || recorder == null) {
//			return;
//		}
//		recorder.stop();
//	}
//
//	@Override
//	protected void onSuccess(String result, int where) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected void onFailure(String result, int where) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	protected void setupData() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
