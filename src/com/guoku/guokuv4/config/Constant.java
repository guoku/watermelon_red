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
	
	//友盟自定义统计
	public static final String UM_SHOP_LIKE = "good_like";//商品喜爱
	public static final String UM_SHOP_LIKE_UN = "good_like_un";//取消商品喜爱
	public static final String UM_SHOP_INFO = "good_info";//查看商品详情
	public static final String UM_SHOP_DOWN = "good_down";//精选流下拉刷新
	public static final String UM_SHOP_UP = "good_up";//精选流上拉加载更多
	public static final String UM_ARTICLE_ZAN = "article_zan";//图文点赞
	public static final String UM_ARTICLE_ZAN_UN = "article_zan_un";//取消图文点赞
	public static final String UM_ARTICLE_INFO = "article_info";//图文详情
	public static final String UM_ARTICLE_DOWN = "article_down";//图文精选流下拉刷新
	public static final String UM_ARTICLE_UP = "article_up";//图文精选流上拉加载更多
	public static final String UM_SHOP_BUY = "good_buy";//商品购买按钮
	public static final String UM_BANNER = "banner";//banner
	public static final String UM_INTRO = "intro";//intro
	public static final String UM_USER_SUGGESTED = "user_suggested_click";//点击推荐用户
	public static final String UM_SORT_SUGGESTED = "sort_suggested_click";//点击推荐品类
	public static final String UM_ARTICLE_HOT = "article_hot_click";//点击热门图文
	public static final String UM_SHOP_HOT = "good_hot_click";//点击热门商品
	public static final String UM_ARTICLE_TO_GOOD = "article_to_good";//图文详情页进入商品
	public static final String UM_ARTICLE_TO_USER = "article_to_user";//图文详情页进入用户
	public static final String UM_ARTICLE_TO_TAOBAO = "article_to_taobao";//图文详情页进入淘宝
	public static final String UM_SUGGESTED_FIRSE_TO_SEC = "suggested_first_to_sec";//一级品类中点击二级品类标签
	public static final String UM_SUGGESTED_FIRSE_MORE_TAG = "suggested_first_more";//一级品类中点击更多进入二级品类
	public static final String UM_SUGGESTED_FIRSE_TO_ARICLE_MORE = "suggested_first_to_article_more";//一级品类中点击图文更多
	public static final String UM_SUGGESTED_FIRSE_TO_ARICLE = "suggested_first_to_article";//一级品类中点击图文
	public static final String UM_SUGGESTED_FIRSE_TO_GOOD = "suggested_first_to_good";//一级品类中点击商品
	public static final String UM_SUGGESTED_FIRSE_DOWN = "suggested_first_down";//一级品类中下拉刷新
	public static final String UM_SUGGESTED_FIRSE_UP = "suggested_first_up";//一级品类中上拉加载更多
	public static final String UM_SUGGESTED_SEC_CLICK = "suggested_sec_click";//二级品类item
	public static final String UM_SUGGESTED_SEC_TO_ARICLE = "suggested_sec_to_aricle";//二级品类页点击图文
	public static final String UM_SUGGESTED_SEC_TO_GOOD = "suggested_sec_to_good";//二级品类页点击商品
	public static final String UM_SUGGESTED_SEC_DOWN = "suggested_sec_down";//二级品类页下拉刷新
	public static final String UM_SUGGESTED_SEC_UP = "suggested_sec_up";//二级品类页上拉加载更多
	public static final String UM_USER_FOLLOW = "user_follow";//个人页用户点击关注按钮
	public static final String UM_USER_FOLLOW_UN = "user_follow_un";//个人页用户点击取消关注按钮
	public static final String UM_USER_LIKE_LIST = "user_like_list";//个人页用户页用户的喜爱按钮
	public static final String UM_USER_ARTICLE_LIST = "user_articlle_list";//个人页用户页用户的图文按钮
	public static final String UM_USER_COMMENTS_LIST = "user_comments_list";//个人页用户页用户的点评按钮
	public static final String UM_USER_TAG_LIST = "user_tag_list";//个人页用户页用户的标签按钮
	public static final String UM_USER_ARTICLE_ZAN_LIST  = "user_articlle_zan_list";//个人页用户页用户赞的标签按钮
	public static final String UM_USER_AUTHO_ARTICLE_ITEM = "user_autho_articlle_item";//个人页授权用户点击图文item
	public static final String UM_TAB_FEED_BT = "tab_feed_bt";//点击动态消息页中的动态
	public static final String UM_TAB_MESSAGE_BT = "tab_message_bt";//点击动态消息页中的消息
	public static final String UM_TAB_FEED_DOWN = "tab_feed_down";//动态消息页中的动态下拉刷新
	public static final String UM_TAB_FEED_UP = "tab_feed_up";//动态消息页中的动态上拉加载更多
	public static final String UM_TAB_MESSAGE_DOWN  = "tab_message_down";//动态消息页中的消息下拉刷新
	public static final String UM_TAB_MESSAGE_UP = "tab_message_up";//动态消息页中的消息上拉加载更多
	
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

	public static final String URL_ARTICLES = "http://m.guoku.com";// 文章前缀
	public static final String URL_ARTICLES_SHARE = "http://www.guoku.com";// 分享果库文章前缀
	public static final String URL_IMG = "http://imgcdn.guoku.com/";// 图片前缀
//	public static final String URL = "http://api.guoku.com";// 生产环境地址
//	 public static final String URL = "http://10.0.0.101:8000";//测试环境地址
	 public static final String URL = "http://test.guoku.com";//测试环境地址
//	 public static final String URL = "http://192.168.1.112:9200";// 赵旭

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
	/** 验证邮箱 */
	public static final String EMAIL_VERIFIED = URL + "/mobile/v4/user/email/verified/";
	/** 一级品类中的图文 */
	public static final String CATEGORY_FIRST = URL + "/mobile/v4/category/";
	/** 推荐全部用户 */
	public static final String AUTHORIZED_USERS = URL + "/mobile/v4/authorized/users/";
	/** 查看有多少个更新精选商品数量 */
	public static final String SHOP_UNREAD = URL + "/mobile/v4/unread/";
	/** 图文点赞 */
	public static final String ARTICLES_DIG = URL + "/mobile/v4/articles/dig/";
	/** 图文取消点赞 */
	public static final String ARTICLES_UNDIG = URL + "/mobile/v4/articles/undig/";
	
	public static final String DESCRIPTOR = "1";
	public static final String WX_APPID = "wx59118ccde8270caa";
	public static final String WX_SECRET = "2200ad1c64775d37bcb0e7f74c8a0641";

	/** 是否打印友盟的log */
	public static boolean UMENG_LOG = true;

	/** 关注、喜欢等广播action */
	public static final String INTENT_ACTION = Constant.class.getName();
	public static final String INTENT_ACTION_KEY = Constant.class.getName()
			+ "_KEY";
//	public static final int INTENT_ACTION_VALUE_FOLLOW = 2;// 关注

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
