package com.ekwing.students.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;

public class MD5 {

	public static String getMD5(String val) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(val.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}

	public static String getString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
		}
		return sb.toString();
	}

	public static String getStrToBase64(String str) {
		// Logger.i("md5", Base64.encodeToString(str.getBytes(), 0));
		if (str == null) {

			return "";
		}
		return Base64.encodeToString(str.getBytes(), 0);
	}

	/**
	 * md5加密工具方法
	 * 
	 * @param text
	 * @return
	 */
	public static String getMD5Encode(String text) {
		try {
			MessageDigest digester = MessageDigest.getInstance("MD5");
			byte[] result = digester.digest(text.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				int number = b & 0xff;// 加盐
				String hexStr = Integer.toHexString(number);
				if (hexStr.length() == 1) {
					sb.append("0");
				}
				sb.append(hexStr);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

	}

}
