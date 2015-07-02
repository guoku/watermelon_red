package com.ekwing.students;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.avos.avoscloud.AVOSCloud;
import com.ekwing.students.config.Constant;
import com.ekwing.students.config.Logger;
import com.ekwing.students.utils.SharePrenceUtil;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.UserAct;
import com.guoku.guokuv4.act.UserInfoAct;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class EkwingApplication extends Application {
	private static EkwingApplication instance;
	public boolean toSee = false;
	public static String userAgent;
	private List<Activity> activityList = new ArrayList<Activity>();
	private AccountBean bean;
	private String session;

	public AccountBean getBean() {
		return bean;
	}

	public void login(AccountBean bean) {
		this.session = bean.getSession();
		this.bean = bean;
		SharePrenceUtil.setUserBean(instance, this.bean);
		// Log.i("APP", bean.toString());
	}

	public void logout() {
		this.bean = null;
		this.session = null;
		SharePrenceUtil.setUserBean(instance, null);
	}

	public String getSession() {
		return session;
	}

	public static int screenH;

	public static int screenW;

	public static EkwingApplication getInstance() {
		return instance;
	}

	// private boolean isLogin;
	private int page;
	private String uid;

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		if (uid == null) {
			uid = "";
		}
		return uid;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	// public boolean isLogin() {
	// return isLogin;
	// }
	//
	// public void setLogin(boolean isLogin) {
	// this.isLogin = isLogin;
	// }

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		Constant.init(this);
		if (Constant.LOG_OPEN) {
			Constant.logOpen();
		} else {
			Constant.logClose();
		}
		initImageLoader(getApplicationContext());
		AVOSCloud.initialize(this,
				"laier6ulcszfjkn08448ng37nwc71ux4uv6yc6vi529v29a0",
				"6ad7o8urhbw4q5kx8hfoiaxjjtme205ohodgoy6ltwts8b1i");
		this.bean = SharePrenceUtil.getUserBean(getApplicationContext());

		AlibabaSDK.asyncInit(this, new InitResultCallback() {

			@Override
			public void onSuccess() {
				Logger.i("taobao", "onSuccess---->");
				// Toast.makeText(MainActivity.this, "初始化成功",
				// Toast.LENGTH_SHORT)
				// .show();
				// ToastUtil.show(instance, "初始化成功");
			}

			@Override
			public void onFailure(int code, String message) {
				Logger.e("taobao", "fail---->" + message);
				// Toast.makeText(MainActivity.this, "初始化异常",
				// Toast.LENGTH_SHORT)
				// .show();
				// ToastUtil.show(instance, "初始化异常");

			}

		});

	}

	// public void finishRealName() {
	// synchronized (this) {
	// if (activityList != null && activityList.size() > 0) {
	// for (Activity a : activityList) {
	// if (a.getClass().getSimpleName()
	// .equals(SelectProvinceAct.class.getSimpleName())
	// || a.getClass()
	// .getSimpleName()
	// .equals(SelectCityAct.class.getSimpleName())
	// || a.getClass().getSimpleName()
	// .equals(SelectXAct.class.getSimpleName())
	// || a.getClass()
	// .getSimpleName()
	// .equals(LoginPagerActivity.class
	// .getSimpleName())) {
	// a.finish();
	// }
	// }
	// }
	// }
	// }

	// public void finishRealName1() {
	// synchronized (this) {
	// if (activityList != null && activityList.size() > 0) {
	// for (Activity a : activityList) {
	// if (a.getClass().getSimpleName()
	// .equals(SelectSchoolAct.class.getSimpleName())
	// || a.getClass()
	// .getSimpleName()
	// .equals(LoginPagerActivity.class
	// .getSimpleName())) {
	// a.finish();
	// }
	// }
	// }
	// }
	// }
	//
	// public void finishPhone() {
	// synchronized (this) {
	// if (activityList != null && activityList.size() > 0) {
	// for (Activity a : activityList) {
	// if (a.getClass()
	// .getSimpleName()
	// .equals(UserInputPhoneActivity.class
	// .getSimpleName())
	// || a.getClass()
	// .getSimpleName()
	// .equals(UserInputValidationActivity.class
	// .getSimpleName())) {
	// a.finish();
	// }
	// }
	// }
	// }
	// }

	public void addActivity(Activity activity) {
		try {
			// 新加进来的Activity进行主题的设置
			synchronized (this) {
				if (!activityList.contains(activity)) {
					activityList.add(activity);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Activity getActivity(String simpleName) {
		Activity activity = null;
		synchronized (this) {
			if (activityList != null && activityList.size() > 0) {
				for (Activity a : activityList) {
					if (simpleName.equals(a.getClass().getSimpleName())) {
						activity = a;
						break;
					}
				}
			}
		}
		return activity;
	}

	public void finishAll() {
		synchronized (this) {
			Logger.i("finishAll", "activityList-->" + activityList.size());
			if (activityList != null && activityList.size() > 0) {
				for (Activity a : activityList) {
					Logger.i("finishAll", a.getClass().getSimpleName());
					a.finish();
				}
			}
		}
	}

	// public void goToMain() {
	// synchronized (this) {
	// if (activityList != null && activityList.size() > 0) {
	// for (Activity a : activityList) {
	// // if (!MainActivity.class.getSimpleName().equals(
	// // a.getClass().getSimpleName())) {
	// a.finish();
	// // }
	// }
	// }
	// }
	// startActivity(new Intent(this, MainActivity2.class));
	// }

	// public void goToExercise() {
	// synchronized (this) {
	// if (activityList != null && activityList.size() > 0) {
	// for (Activity a : activityList) {
	// if (WordsConfirmResultActivity.class.getSimpleName()
	// .equals(a.getClass().getSimpleName())) {
	// a.finish();
	// }
	// // if (ConfirmDiffActivity.class.getSimpleName().equals(
	// // a.getClass().getSimpleName())) {
	// // a.finish();
	// // }
	// // if
	// // (WordsConfirmModeActivity.class.getSimpleName().equals(
	// // a.getClass().getSimpleName())) {
	// // a.finish();
	// // }
	// }
	// }
	// }
	// }

	// 有Activity手动finish的时候需要将其引用在集合中删除
	public void removeActivity(Activity activity) {
		synchronized (this) {
			if (!activityList.isEmpty() && activityList.contains(activity))
				activityList.remove(activity);
		}
		System.gc();
	}

	// // ------用于jpush跳转－－
	// /**
	// * 以jpush方式打开程序后的返回逻辑 返回
	// *
	// * @param activityclass对象
	// * 要跳转的activity
	// */
	// public void backActivity(Class<?> activityClass, Context context) {
	// boolean findIt = false;
	// synchronized (this) {
	// if (activityList != null && activityList.size() > 0) {
	// for (int i = activityList.size() - 1; i >= 0; i--) {
	// Activity tempActivity = activityList.get(i);
	// String simpleName = tempActivity.getClass().getSimpleName();
	// if (!simpleName.equals(activityClass.getSimpleName())) {
	// // 因销毁其中任意一个 整个程序将退出
	// if (!simpleName.equals("UserCenterActivity")
	// && !simpleName
	// .equals("DestinationMainActivity")
	// && !simpleName.equals("ProductMainActivity")
	// && !simpleName.equals("ImHereMainActivity")
	// && !simpleName.equals("MainActivity")) {
	// tempActivity.finish();
	// }
	// } else {
	// findIt = true;
	// break;
	// }
	// }
	// }
	// if (!findIt) {
	// // 如果主界面都没有被启动过，则启动主界面
	// if (activityClass.getSimpleName().equals("UserCenterActivity")) {
	// Intent intent = new Intent(context, MainActivity.class);
	// context.startActivity(intent);
	// } else {
	// Intent intent = new Intent(context, activityClass);
	// context.startActivity(intent);
	// }
	// }
	// }
	// }

	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getCacheDirectory(instance);
		cacheDir = new File(cacheDir, Constant.CACHE_BIG_PATH);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				instance).threadPoolSize(5)
				.diskCacheExtraOptions(480, 320, null)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.diskCache(new UnlimitedDiscCache(cacheDir))
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(10 * 1024 * 1024))
				.memoryCacheSize(10 * 1024 * 1024)
				.memoryCacheSizePercentage(13) // default
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.imageDownloader(new BaseImageDownloader(instance)) // default
				.imageDecoder(new BaseImageDecoder(true)) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}
}
