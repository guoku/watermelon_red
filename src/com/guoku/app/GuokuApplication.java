package com.guoku.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.wxlib.util.SysUtil;
import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.guoku.guokuv4.bean.LaunchBean;
import com.guoku.guokuv4.config.AlibabaConfig;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.config.Logger;
import com.guoku.guokuv4.entity.test.AccountBean;
import com.guoku.guokuv4.utils.SharePrenceUtil;
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

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class GuokuApplication extends Application {
	private static GuokuApplication instance;
	public boolean toSee = false;
	private List<Activity> activityList = new ArrayList<Activity>();
	private AccountBean bean;
	private String session;
	private YWIMKit mIMKit;
	private LaunchBean launchBean;
	
    public LaunchBean getLaunchBean() {
		return launchBean;
	}

	public void setLaunchBean(LaunchBean launchBean) {
		this.launchBean = launchBean;
	}

	public YWIMKit getIMKit() {
        return mIMKit;
    }

    public void setIMKit(YWIMKit imkit) {
        mIMKit = imkit;
    }
    
    public void initIMKit(String userId, String appKey) {
        mIMKit = YWAPI.getIMKitInstance(userId.toString(), appKey);
    }

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

	public static GuokuApplication getInstance() {
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

	@Override
	public void onCreate() {

		Fresco.initialize(getApplicationContext());// 初始化Fresco

		super.onCreate();
		instance = this;
		Constant.init(this);
		if (Constant.LOG_OPEN) {
			Constant.logOpen();
		} else {
			Constant.logClose();
		}
		initImageLoader(getApplicationContext());
		
		initOpenIM();
		initTaoBao();
	}
	
	private void initTaoBao(){
		
		try {
			AVOSCloud.initialize(this,
					"laier6ulcszfjkn08448ng37nwc71ux4uv6yc6vi529v29a0",
					"6ad7o8urhbw4q5kx8hfoiaxjjtme205ohodgoy6ltwts8b1i");
			this.bean = SharePrenceUtil.getUserBean(getApplicationContext());
			
			AlibabaSDK.asyncInit(this, new InitResultCallback() {

				@Override
				public void onSuccess() {
					Logger.i("taobao", "onSuccess---->");
				}

				@Override
				public void onFailure(int code, String message) {
					Logger.e("taobao", "fail---->" + message);
				}

			});
		} catch (Exception e) {
			// TODO: handle exception
			Logger.e("taobao", "***********error***********");
		}
	}
	
    private void initOpenIM(){
		
		SysUtil.setApplication(this);
		if(SysUtil.isTCMSServiceProcess(this)){
		return;
		}
//		//第一个参数是Application Context
//		//这里的APP_KEY即应用创建时申请的APP_KEY
//		YWAPI.init(this, AlibabaConfig.APP_KEY);
		
		//初始化IMKit，考虑到应用启动会自动登录的情况
//				final String userId = IMAutoLoginInfoStoreUtil.getLoginUserId();
//				final String appkey = IMAutoLoginInfoStoreUtil.getAppkey();
//				if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(appkey)){
//					//初始化imkit
//					GuokuApplication.getInstance().initIMKit(userId, appkey);
//					//通知栏相关的初始化
//				}

				YWAPI.init(this, AlibabaConfig.APP_KEY);
    }
    
    public List<String> splitToList(String param) {
		if(param != null) {
			String[] keys = param.split(" ");
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < keys.length; i++) {
				list.add(keys[i]);
			}
			return list;
		}
		return null;
	}

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

	// 有Activity手动finish的时候需要将其引用在集合中删除
	public void removeActivity(Activity activity) {
		synchronized (this) {
			if (!activityList.isEmpty() && activityList.contains(activity))
				activityList.remove(activity);
		}
		System.gc();
	}

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
