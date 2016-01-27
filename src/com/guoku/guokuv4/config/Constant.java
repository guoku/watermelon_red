package com.guoku.guokuv4.config;

import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.utils.StringUtils;
import com.handmark.pulltorefresh.library.internal.Utils;
import com.lidroid.xutils.util.LogUtils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;

public class Constant {
	// 再次登录记录tab
	public static final String SP_STUDENT_TAB = "sptab";
	public static final String STUDENT_TAB = "tab";
	public static String BASE_PATH = Environment.getExternalStorageDirectory()
			+ "/guoku/";
	public static String IMAGES_PATH = BASE_PATH + "/images/";
	public static String LAUNCH_PATH = BASE_PATH + "launch/";//引导图

	// log开关
	/** 以后log输出以(LogUtils.*)方式输出 **/
	public static boolean LOG_OPEN = true;

	public static final String SP_USERINFO = "sp_userinfo";
	public static final String SP_CODEPIC = "SP_CODEPIC";
	public static final String USERINFO_LOGIN_TYPE = "userinfo_login_type";
	public static final String USERINFO_LOGIN_TYPE_KEY = "userinfo_login_type_key";
	public final static String LEZYO = "LEZYO";

	/** 缓存图片路径 */
	public static final String CACHE_PATH = "lezyo_image";
	public static final String CACHE_BIG_PATH = "guoku";
	public final static String SP_HAS_LOGIN = "HAS_LOGIN";
	public static final String TAG = "TAG";
	public static final String EKWING_LOGIN = "ekwing_login";
	public static final String GUOKU_TAB = "GUOKU_TAB";
	public static final String GUOKU_TAB_LIST = "GUOKU_TAB_LIST";

	public static final String URL_ARTICLES = "http://m.guoku.com";// 文章、商品前缀
	public static final String URL_ARTICLES_SHARE = "http://www.guoku.com";// 分享果库文章前缀
	public static final String URL_IMG = "http://imgcdn.guoku.com/";// 图片前缀
	public static final String URL = "http://api.guoku.com";// 生产环境地址
	// public static final String URL = "http://10.0.0.101:8000";//测试环境地址
//	 public static final String URL = "http://test.guoku.com";//测试环境地址

	public static final String JINGXUAN = URL + "/mobile/v4/selection/";
	public static final String PROINFO = URL + "/mobile/v4/entity/";
	public static final String GUESS = URL + "/mobile/v4/entity/guess/";
	public static final String TOLIKE = URL + "/mobile/v4/entity/";
	public static final String TOPY = URL + "/mobile/v4/entity/note/";
	public static final String COMMENT = URL + "/mobile/v4/entity/note/";
	public static final String COMMENTLIST = URL + "/mobile/v4/entity/note/";
	public static final String COMMENTNOTE = URL + "/mobile/v4/entity/";
	public static final String LOGIN = URL + "/mobile/v4/login/";
	public static final String HOT = URL + "/mobile/v4/popular/";
	public static final String TAB = URL + "/mobile/v4/category/";

	public static final String TAB_USER = URL + "/mobile/v4/user/";
	public static final String POINTLIST = URL + "/mobile/v4/feed/";
	public static final String MESSAGELIST = URL + "/mobile/v4/message/";
	public static final String FAXIANHOME = URL + "/mobile/v4/homepage/";
	public static final String SEARCH = URL + "/mobile/v4/";
	public static final String CATAB = URL + "/mobile/v4/category/";
	public static final String FOLLOW = URL + "/mobile/v4/user/";
	public static final String GETFANSLIST = URL + "/mobile/v4/user/";
	public static final String USERUPDATA = URL + "/mobile/v4/user/update/";
	public static final String GETTAGLIST = URL + "/mobile/v4/user/";
	public static final String REGISTER = URL + "/mobile/v4/register/";
	public static final String TAOBAOLOGIN = URL + "/mobile/v4/baichuan/login/";
	public static final String SINALOGIN = URL + "/mobile/v4/weibo/login/";
	public static final String WXLOGIN = URL + "/mobile/v4/wechat/login/";
	public static final String SINAREGISTER = URL + "/mobile/v4/sina/register/";
	public static final String TAOREGISTER = URL
			+ "/mobile/v4/taobao/register/";
	public static final String FORGET = URL + "/mobile/v4/forget/password/";
	public static final String USERINFO = URL + "/mobile/v4/user/";
	/** 修改密码 */
	public static final String PASSWORD_CHANGE = URL
			+ "/mobile/v4/user/reset/password/";
	/** 热门商品列表 */
	public static final String DISCOVER = URL + "/mobile/v4/discover/";
	/** 首页商品图文banner流 */
	public static final String HOME = URL + "/mobile/v4/home/";
	/** 首页图文流 */
	public static final String ARTICLES = URL + "/mobile/v4/articles/";
	/** 二级品类商品 */
	public static final String CATEGORY = URL + "/mobile/v4/category/";
	/** 品类搜索 */
	public static final String CATEGORY_SEARCH = URL + "/mobile/v4/category/search/";
	/** 商品详情 */
	public static final String DETAIL = URL_ARTICLES_SHARE + "/detail/";
	/** 引导页 */
	public static final String LAUNCH = URL + "/mobile/v4/launchboard/";
	
	public static final String DESCRIPTOR = "1";
	public static final String WX_APPID = "wx59118ccde8270caa";
	public static final String WX_SECRET = "2200ad1c64775d37bcb0e7f74c8a0641";

	/** 是否打印友盟的log */
	public static boolean UMENG_LOG = true;

	/** 关注、喜欢等广播action */
	public static final String INTENT_ACTION = Constant.class.getName();
	public static final String INTENT_ACTION_KEY = Constant.class.getName()
			+ "_KEY";
	public static final int INTENT_ACTION_VALUE_FOLLOW = 2;// 关注

	public static void init(GuokuApplication lezyoApplication) {
		if (lezyoApplication != null) {
			ApplicationInfo appInfo;
			try {
				appInfo = lezyoApplication.getPackageManager()
						.getApplicationInfo(lezyoApplication.getPackageName(),
								PackageManager.GET_META_DATA);
				if (appInfo != null) {
					Bundle b = appInfo.metaData;
					if (b != null) {
						String debug = b.getString("DEBUG");
						String umeng = b.getString("UMENG");
						LOG_OPEN = !StringUtils.isEmpty(debug)
								&& debug.startsWith("yes");
						UMENG_LOG = !StringUtils.isEmpty(umeng)
								&& umeng.startsWith("yes");
					}
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean NeedLogin(String url) {
		return Constant.LOGIN.equals(url) || Constant.REGISTER.equals(url)
				|| Constant.SINALOGIN.equals(url)
				|| Constant.TAOBAOLOGIN.equals(url)
				|| Constant.TAOREGISTER.equals(url)
				|| Constant.WXLOGIN.equals(url)
				|| Constant.SINAREGISTER.equals(url)
				|| Constant.FORGET.equals(url);
	}

	public static void logOpen() {
		LogUtils.allowD = true;
		LogUtils.allowE = true;
		LogUtils.allowI = true;
		LogUtils.allowV = true;
		LogUtils.allowW = true;
		LogUtils.allowWtf = true;
	}

	public static void logClose() {
		LogUtils.allowD = false;
		LogUtils.allowE = false;
		LogUtils.allowI = false;
		LogUtils.allowV = false;
		LogUtils.allowW = false;
		LogUtils.allowWtf = false;
	}
}
