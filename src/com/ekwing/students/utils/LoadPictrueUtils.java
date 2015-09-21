package com.ekwing.students.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.ekwing.students.config.Constant;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class LoadPictrueUtils {

	private String uri;
	private ImageView imageView;
	private byte[] picByte;

	public void getPicture(String uri, ImageView imageView) {
		this.uri = uri;
		this.imageView = imageView;
		new Thread(runnable).start();
	}

	public void getLocalPicture() {

	}

	Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				if (picByte != null) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0,
							picByte.length);
					saveBitmap(bitmap, Constant.IMAGES_PATH
							+ convertUrlToFileName(uri));
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	};

	private String convertUrlToFileName(String url) {
		String[] strs = url.split("/");
		return strs[strs.length - 1];
	}

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

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(uri);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setReadTimeout(10000);

				if (conn.getResponseCode() == 200) {
					InputStream fis = conn.getInputStream();
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] bytes = new byte[1024];
					int length = -1;
					while ((length = fis.read(bytes)) != -1) {
						bos.write(bytes, 0, length);
					}
					picByte = bos.toByteArray();
					bos.close();
					fis.close();

					Message message = new Message();
					message.what = 1;
					handle.sendMessage(message);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

}