package com.ekwing.students.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ekwing.students.config.Logger;
import com.ekwing.students.entity.ThemeBean;
import com.guoku.R;

public class Utils {

	private static Random random = new Random();

	/**
	 * 得到位置最后解锁的关卡position
	 * 
	 * @param themeLists
	 * @return
	 */
	public static int getPosition(ArrayList<ThemeBean> themeLists) {
		if (themeLists == null || themeLists.size() <= 0) {
			return 0;
		}
		int size = themeLists.size();
		int count = 0;
		for (int i = 0; i < size; i++) {
			if ("1".equals(themeLists.get(i).getIsLocked())) {
				break;
			}
			count++;
		}
		Logger.e("Utils", "Utils======>" + count);
		return count;
	}

	public static void hideKeyboard(Context context, EditText editText) {
		editText.setText("");
		editText.setHint("");
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 判断是不是平板
	 * 
	 * @param context
	 * @return false是平板，true 不是平板
	 */
	public static boolean isTabletDevice(Context context) {
		boolean isTable = false;
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		int type = telephony.getPhoneType();
		if (type == TelephonyManager.PHONE_TYPE_NONE) {
			isTable = true;
		} else {
			isTable = false;
		}
		return isTable;
	}

	/**
	 * 判断必须要有数字和字母
	 * 
	 * @param str
	 *            传入的字符串
	 * @return
	 */
	public static boolean isWordsAndNumbers(String str) {
		// 任意字符都可以，但是必须要有数字和字母
		String regexWord = ".*[a-zA-Z]+.*";
		String regexNumber = ".*\\d+.*";
		// 只能是数字和字母
		// String regexWord = "\\w*[a-zA-Z]+\\w*";
		// String regexNumber = "\\w*\\d+\\w*";
		return str.matches(regexWord) && str.matches(regexNumber);

	}

	/**
	 * 加载assets目录下的图片并设置图片
	 * 
	 * @param context
	 * @param iv
	 * @param name
	 */
	public static void getImageFromAssetsFile(Activity context, ImageView view,
			String fileName, int id) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		Logger.e("file", "picname====>" + fileName);
		try {
			InputStream is = am.open("images/" + fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			Logger.e("", "ioexception=====>" + e.toString());
			image = BitmapFactory.decodeResource(context.getResources(), id);
		}

		view.setImageBitmap(image);

	}

	/**
	 * 计算星星的数量
	 * 
	 * @param context
	 *            上下文
	 * @param ll
	 *            LinearLayout 容器
	 * @param leveal
	 *            星星的变量 --->显示几颗星
	 * @return
	 */
	// public static LinearLayout calculateStar(Context context, LinearLayout
	// ll,
	// String leveal) {
	// int aveGread = 0;
	// try {
	// aveGread = Integer.parseInt(leveal);
	// } catch (Exception e) {
	// // TODO: handle exception
	// aveGread = 0;
	// }
	// Logger.e("utils", "avegred================>" + aveGread);
	// for (int i = 0; i < aveGread; i++) {
	// ImageView iv = new ImageView(context);
	// iv.setScaleType(ImageView.ScaleType.CENTER);
	// android.widget.LinearLayout.LayoutParams lp = new
	// android.widget.LinearLayout.LayoutParams(
	// Utils.getScreenWidth(context) / 16,
	// Utils.getScreenWidth(context) / 16);
	// // lp.weight = 1;
	// lp.setMargins(2, 0, 2, 0);
	//
	// iv.setImageResource(R.drawable.solid_star);
	// iv.setLayoutParams(lp);
	//
	// ll.addView(iv);
	// }
	//
	// return ll;
	// }

	/**
	 * 集合随机排序算法
	 * 
	 * @param ls
	 * @return
	 */
	public static <T> List<T> randomSortList(List<T> ls) {
		List<T> randomList = new ArrayList<T>();
		int size = ls.size();
		// while (size > 0) {
		// int randomNum = random.nextInt(size);
		// randomList.add(ls.get(randomNum));
		// ls.remove(randomNum);
		// size = ls.size();
		// }
		for (int i = 0; i < size; i++) {
			int randomNum = random.nextInt(size - i);
			randomList.add(ls.get(randomNum));
			ls.remove(randomNum);
		}
		return randomList;
	}

	public static ArrayList<String> randomSortArray(String[] list) {
		List<String> randomList = new ArrayList<String>();
		// 数组长度
		int count = list.length;
		// 用一个LinkedList作为中介
		LinkedList<String> temp = new LinkedList<String>();
		// 初始化temp
		for (int i = 0; i < count; i++) {
			temp.add(list[i]);
		}
		for (int i = 0; i < count; i++) {
			int num = random.nextInt(count - i);
			randomList.add(temp.get(num));
			temp.remove(num);
		}
		return (ArrayList<String>) randomList;
	}

	/**
	 * 判定输入汉字
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 检测String是否全是中文
	 * 
	 * @param name
	 * @return
	 */
	public static boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}

	/**
	 * 手机号判断
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		// Pattern p =
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 小数转化成百分数
	 * 
	 * @param a
	 *            小数
	 */
	public static String decimal2Percent(double a) {

		NumberFormat num = NumberFormat.getPercentInstance();
		num.setMaximumIntegerDigits(3); // 小数点前面最多多少位
		num.setMaximumFractionDigits(0); // 小数点后面最多多少位
		return num.format(a);
	}

	/**
	 * 四舍五入
	 * 
	 * @param v
	 * @param scale
	 * @return
	 */
	public static double roundForNumber(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 安装一个应用程序
	 * 
	 * @param context
	 * @param apkfile
	 */
	public static void installApplication(Context context, File apkfile) {
		/*
		 * <action android:name="android.intent.action.VIEW" /> <category
		 * android:name="android.intent.category.DEFAULT" /> <data
		 * android:scheme="content" /> <data android:scheme="file" /> <data
		 * android:mimeType="application/vnd.android.package-archive" />
		 */
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		// intent.setType("application/vnd.android.package-archive");
		// intent.setData(Uri.fromFile(apkfile));
		intent.setDataAndType(Uri.fromFile(apkfile),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 根据比例，把dp转换成px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * 根据比例，把px转换成dp
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static float px2dp(Context context, int px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return ((float) px - 0.5f) / scale;
	}

	// /**
	// * 将中文转换为英文
	// *
	// * @param name
	// * @return
	// * @throws BadHanyuPinyinOutputFormatCombination
	// */
	// public static String getEname(String name) throws
	// BadHanyuPinyinOutputFormatCombination {
	// if (name == null || name.equals(""))
	// return null;
	// HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
	// pyFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 设置样式
	// pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
	// pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	// return PinyinHelper.toHanyuPinyinString(name, pyFormat, "");
	// }

	/**
	 * 获取IMEI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getDeviceId();
	}

	/**
	 * 返回电话的IMEI:手机的唯一标识码
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		String imei = "";
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null) {
			imei = tm.getDeviceId();
			if (isEmpty(imei)) {
				imei = createIMSI();
			}
		}
		return imei;
	}

	/**
	 * 获取手机mac地址<br/>
	 * 错误返回12个0
	 */
	public static String getMacAddress(Context context) {
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr
					.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	/**
	 * 手机名称
	 * 
	 * @return
	 */
	public static String getPhoneName() {
		return android.os.Build.MANUFACTURER;
	}

	/**
	 * 获取手机IMSI
	 * 
	 * @return
	 */
	public static final String createIMSI() {
		StringBuffer strbuf = new StringBuffer();
		for (int i = 0; i < 15; i++)
			strbuf.append(random.nextInt(10));
		return strbuf.toString();
	}

	/**
	 * 手机制造商
	 * 
	 * @return
	 */
	public static String getProduct() {

		return android.os.Build.PRODUCT;
	}

	/**
	 * 手机系统版本
	 * 
	 * @return
	 */
	public static String getOsVersion() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 获取当前的版本号
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context context) {
		String version = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 通过Wifi获取MAC ADDRESS作为DEVICE ID 缺点：如果Wifi关闭的时候，硬件设备可能无法返回MAC ADDRESS.。
	 * 
	 * @return
	 */
	public static String getMacAddress() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			// 赋予默认值
			ex.printStackTrace();
		}
		return macSerial;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return (s == null || "".equals(s) || "null".equals(s.toLowerCase()));
	}

	/**
	 * 得到手机屏幕的宽
	 * 
	 * @param mActivity
	 * @return
	 */
	public static int getScreenWidth(Activity mActivity) {
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static float getDensity(Context contex) {
		return contex.getResources().getDisplayMetrics().density;
	}

	public static int getUsefulScreenHeight(Context context) {
		return getScreenHeight(context)
				- StatusBarHeightUtils.getStatusHeight(context);
	}

	/**
	 * 得到手机屏幕的高
	 * 
	 * @param act
	 * @return
	 */
	public static int getScreenHeight(Activity mActivity) {
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 获取文件夹大小
	 * 
	 * @param path
	 * @return
	 */
	public static String getSizeFile(String path) {
		if (isEmpty(path))
			return null;
		String fileSize = null;
		try {
			File ff = new File(path);
			// 如果路径是文件夹的时候
			if (ff.isDirectory()) {
				long l = getFileFolderSize(ff);
				fileSize = formetFileSize(l);
			} else {
				long lo = getFileSizes(ff);
				fileSize = formetFileSize(lo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileSize;
	}

	/**
	 * 得到文件目录大小
	 * 
	 * @param path
	 * @return
	 */
	public static long getFileDirSize(String path) {
		if (Utils.isEmpty(path))
			return 0;
		File sizeFile = new File(path);
		try {
			if (sizeFile.isDirectory()) {
				return getFileFolderSize(sizeFile);
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 取得文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileSizes(File f) throws Exception {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
		} else {
			f.createNewFile();
			System.out.println("文件不存在");
		}
		return s;
	}

	/**
	 * 递归 //取得文件夹大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static long getFileFolderSize(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileFolderSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * 转换文件大小
	 * 
	 * @param fileS
	 * @return
	 */
	public static String formetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.##");
		String fileSizeString = "";
		if (fileS == 0 || fileS < 350)
			return "0M";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/*
	 * 根据key获取metadata中的值
	 * 
	 * @param context
	 * 
	 * @param channelKey
	 * 
	 * @return
	 */
	public static int getMetaDataValue(Context context, String channelKey) {
		int msg = 0;
		try {
			ApplicationInfo appInfo = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			msg = appInfo.metaData.getInt(channelKey);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 将字符串转化成字符数组
	 * 
	 * @param update_mode
	 * @return
	 */
	public static String[] convertStrToArray(String update_mode) {
		String[] split = update_mode.split(",");
		return split;
	}

}
