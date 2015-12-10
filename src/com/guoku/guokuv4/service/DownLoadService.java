/**

 */
package com.guoku.guokuv4.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.IntroAct;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.utils.StringUtils;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月27日 下午3:37:21 下载引导图
 */
public class DownLoadService extends Service {
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				stopSelf(); // 当系统接收到消息后，关闭service
			}

		};
	};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new MyThread()).start();// 启动线程
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				URL myFileUrl = null;
				// Bitmap bitmap = null;
				myFileUrl = new URL(GuokuApplication.getInstance().getLaunchBean().getLaunch_image_url());
				HttpURLConnection conn;
				conn = (HttpURLConnection) myFileUrl.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();

				// bitmap = BitmapFactory.decodeStream(is);
				//
				// if
				// (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
				// {
				// BitmapUtil.saveFile(bitmap);
				saveFile(is);
				Intent intent = new Intent(getApplication(), IntroAct.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplication().startActivity(intent);
				// }
				handler.sendEmptyMessage(1);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void saveFile(InputStream is) {
		try {
			byte buffer[] = new byte[4096];
			int rc = is.read(buffer, 0, buffer.length);

			String path = Constant.LAUNCH_PATH;
			File dirFile = new File(path);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			File myCaptureFile = new File(path
					+ StringUtils.setReplace(GuokuApplication.getInstance().getLaunchBean().getLaunch_image_url()));
			FileOutputStream fos = new FileOutputStream(myCaptureFile);
			while (rc > 0) {
				fos.write(buffer, 0, rc);
				rc = is.read(buffer, 0, buffer.length);
			}
			fos.flush();
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
