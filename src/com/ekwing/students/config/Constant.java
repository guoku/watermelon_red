package com.ekwing.students.config;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.utils.Utils;
import com.guoku.guokuv4.gragment.JingXuanFragment;
import com.lidroid.xutils.util.LogUtils;

public class Constant {
	// public static final String EKWING_BASE_URL =
	// "http://192.168.1.221:8050/";
	// public static final String EKWING_BASE_URL = "http://192.168.1.133/";
	// public static final String EKWING_BASE_URL =
	// "http://192.168.1.221:8050/";
	// public static final String EKWING_BASE_URL = "http://192.168.1.221/";
	public static final String EKWING_BASE_URL = "http://www.ekwing.com/";
	// 再次登录记录tab
	public static final String SP_STUDENT_TAB = "sptab";
	public static final String STUDENT_TAB = "tab";
	public static String BASE_PATH = Environment.getExternalStorageDirectory()
			+ "/guoku/";
	public static String SOUND_PATH = BASE_PATH + "/sound/";
	public static String IMAGES_PATH = BASE_PATH + "/images/";
	public static String RECORD_PATH = BASE_PATH + "/record/";
	public static String DBS_PATH = BASE_PATH + "/db/";

	public static String BASE_URL = "";

	// 基本访问地址
	public static String PAY_URL = "";
	// ------------------------------------------------------------------------------------------------------------
	// 是否需要更新喜欢 或者来过的状态
	public static boolean needUpdateLike;
	public static boolean needUpdateHere;
	public static boolean likeStatusAdd;
	public static boolean likeStatusRemove;
	public static boolean beenStatusAdd;
	public static boolean beenStatusRemove;
	// 点击两次退出程序是否需要
	public static boolean noneedTwoPointFinish;
	// 订单状态是否需要更新
	public static boolean needUpdateOrderList;
	public static boolean needUpdateOrderStaue;
	// 是否退出
	public static boolean isLogout;
	// 是否要更新喜欢列表
	public static boolean needUpdateLikeList;
	// 用户是否需要登录
	public static boolean NEED_LOGIN;
	// 图片播放时间间隔
	public static final int PIC_PLAYER_TIME = 5;
	// 是否是jpush方式启动
	public static boolean jpushLaunch;
	// 屏幕宽高
	public static int screenH;
	public static int screenW;
	// 是否有SmartBar
	public static boolean hasSmartBar;
	// log开关
	/** 以后log输出以(LogUtils.*)方式输出 **/
	public static boolean LOG_OPEN = true;
	/** 数据库名字 */

	// 订单状态
	public static final int ORDER_STATUS_WAIT_VALUE = -1; // 待支付
	public static final int ORDER_STATUS_PENDING_VALUE = 0; // 预订单
	public static final int ORDER_STATUS_MANUAL_VALUE = 1; // 预订确认
	public static final int ORDER_STATUS_IN_SERIVCE_VALUE = 2; // 服务中
	public static final int ORDER_STATUS_SERIVIE_END_VALUE = 3; // 服务结束
	public static final int ORDER_STATUS_INVOICE_VALUE = 4; // 账单
	public static final int ORDER_STATUS_DONE_VALUE = 5; // 订单完成

	public static String LOGIN_SELECT_SCHOOL_ID = "LOGIN_SELECT_SCHOOL_ID";

	public final static String SP_USERENTITY = "SP_USERENTITY";
	public final static String SP_USERENTITY_uid = "SP_USERENTITY_uid";
	public final static String SP_USERENTITY_name = "SP_USERENTITY_name";
	public final static String SP_USERENTITY_mobile = "SP_USERENTITY_mobile";
	public final static String SP_USERENTITY_email = "SP_USERENTITY_email";
	public final static String SP_USERENTITY_province = "SP_USERENTITY_province";
	public final static String SP_USERENTITY_city = "SP_USERENTITY_city";
	public final static String SP_USERENTITY_avatar = "SP_USERENTITY_avatar";
	public final static String SP_USERENTITY_token = "SP_USERENTITY_token";
	public final static String SP_USERENTITY_sc = "SP_USERENTITY_str_c";
	public final static String SP_FIRST_URED = "first_used";
	public static final String URL_POSITION = BASE_URL + "/GetCity";
	public static String PATH_PHOTO;
	public final static String LEZYO = "LEZYO";
	public final static String PASS = "pass";
	public final static String EXERCISE = "EXERCISE";
	public final static String EXERCISEE = "exercise";

	/** 缓存图片路径 */
	public static final String CACHE_PATH = "lezyo_image";
	public static final String CACHE_BIG_PATH = "guoku";
	public final static String SP_HAS_LOGIN = "HAS_LOGIN";
	public static final String DATA_BASE_NAME = "ekwing_student.db"; // 数据库的名字
	public static final String EKWING_IS_FIRST_ARTICLE = "ekwing_isfirst";
	public static final String TAG = "TAG";
	public static final String EKWING_ARTICLE = "isfirst_article";
	public static final String EKWING_IS_AUTO_PLAY = "ekwing_isauto";
	public static final String EKWING_WORDS_PLAY = "words_auto";
	public static final int PLSYFINISH = 99;
	public static final int MDEIA_ERROR = 5000;
	public static final String HW_JSON = "jsondata";
	public static final String SP_EKWING_HW = "sp_json";
	public static final String SP_EKWING_HW_DETAILS = "hw_jsons";
	public static final String SP_EKWING_DATE = "sp_date";
	public static final String EKWING_DATE = "date";
	public static final String SP_SUGGEST = "ekwing_su";
	public static final String SUGGEST = "suggestion";
	public static final String SP_USEREBEAN = "userbean";
	public static final String SP_USEREBEAN_UID = "user_id";
	public static final String SP_USERBEAN_TOKEN = "user_token";
	public static final String SP_SWITCH_STATE = "ekwing_switch";
	public static final String EKWING_LOGIN = "ekwing_login";
	public static final String GUOKU_TAB = "GUOKU_TAB";
	public static final String GUOKU_TAB_LIST = "GUOKU_TAB_LIST";

	public static String DATA_URL = EKWING_BASE_URL + "sc/datelist";
	public static final String HW_DATAS = EKWING_BASE_URL + "sc/hwlist";
	public static final String EKWING_GET_SUBJECT = EKWING_BASE_URL
			+ "sc/hwcidinfo";
	public static final String EKWING_COMPLETE_QUERY = EKWING_BASE_URL
			+ "sc/hwcidret";
	public static final String EKWING_INTERFACE_SUGGESTION = EKWING_BASE_URL
			+ "sc/suggest";
	public static final String EKWING_MODY_PSW_IN = EKWING_BASE_URL
			+ "sc/uppwd";
	public static final String EKWING_BIND = EKWING_BASE_URL + "sc/binding";
	public static final String EKWING_LOGIN_INTERFACE = EKWING_BASE_URL
			+ "sc/login";
	public static final String EKWING_LOGIN_REAL = EKWING_BASE_URL
			+ "sc/loginschool";
	public static final String EKWING_USER_BASIC = EKWING_BASE_URL
			+ "sc/getuserinfo";
	public static final String EKWING_EMAIL_UN_BIND = EKWING_BASE_URL
			+ "sc/unbind";
	public static final String EKWING_COMMIT_VOLIDATION = EKWING_BASE_URL
			+ "sc/verify";
	public static final String EKWING_FIND_FORGET_PSW = EKWING_BASE_URL
			+ "sc/forgetpwd";
	public static final String VALIDATION_CODE = EKWING_BASE_URL
			+ "sc/forgetcode";
	public static final String EKWING_SETTING_FORGET_PSW = EKWING_BASE_URL
			+ "sc/forgetverify";
	public static final String EKWING_SEND_FLOWER = EKWING_BASE_URL
			+ "sc/sendflower"; // 送花
	public static final String EKWING_MY_SERILIA = EKWING_BASE_URL
			+ "sc/getsocial"; // 我的系列接口
	public static final String EKWING_UPLOAD_HEAD = EKWING_BASE_URL
			+ "sc/updateLogo"; // 上传头像
	public static final String EKWING_EXERCISE_NEW_TASK = EKWING_BASE_URL
			+ "sc/getTask"; // 获取最新任务
	public static final String NETREMIND = "netremind";
	public static final String VIBRAT = "vibrat";
	public static final String SOUND = "sound";
	public static final String SP_USERINFO = "sp_userinfo";
	public static final String SP_CODEPIC = "SP_CODEPIC";
	public static final String USERBEAN = "SP_USERBEAN";
	public static final String SP_CLASSES = "SP_CLASSES";
	public static final String SP_BIRTHDAY = "";
	public static final String SP_SEX = "SP_SEX";
	public static final String SP_NEXTLEVEL = "SP_NEXTLEVEL";
	public static final String SP_USEREMAIL = "SP_USEREMAIL";
	public static final String SP_NICENAME = "SP_NICENAME";
	public static final String SP_AVATAR = "SP_AVATAR";
	public static final String SP_USERNAME = "SP_USERNAME";
	public static final String SP_SCHOOL = "SP_SCHOOL";
	public static final String SP_LEVELS = "SP_LEVELS";
	public static final String SP_HASFLOWER = "SP_HASFLOWER";
	public static final String SP_USERPHONE = "SP_USERPHONE";
	public static final String SP_PARENTPHONE = "SP_PARENTPHONE";
	public static final String SP_ID = "SP_ID";
	public static final String SP_URL = "SP_URL";
	public static final String SP_NAME = "SP_NAME";
	public static final String SP_HASICON = "SP_HASICON";
	public static final String BIND_TYPE = "bind_type";
	public static final String PASS_PHONE = "phone";
	public static final String PASS_CODE = "code";
	public static final String PASS_SID = "passid";
	public static final String WORDS_EXERCISE = "words";
	public static final String ARTICLE_EXERCISE = "articles";
	public static final String READ_UNDER_EXERCISE = "readunder";
	public static final String READING_EXERCISE = "reading";
	public static final String EKWING_WORDS_FIRST = "is_first_words";
	public static final String EKWING_GET_REWARD = EKWING_BASE_URL
			+ "sc/getTaskReward";
	public static final String EKWING_ADD_LIST = EKWING_BASE_URL
			+ "sc/getPointsList";
	public static final String EKWING_ADD = EKWING_BASE_URL
			+ "sc/getPointsInfo";
	public static final String EKWING_RAKING = EKWING_BASE_URL + "sc/getrank";
	public static final String EKWING_DAY_DAY = EKWING_BASE_URL
			+ "sc/getPointsRecent";
	public static final String EKWING_CONFIRM_COMMIT = EKWING_BASE_URL
			+ "sc/savepoints";
	public static final String GETDATA = EKWING_BASE_URL + "sc/getpointsdata";
	public static final String SAVE_HOMEWORK = EKWING_BASE_URL
			+ "sc/saveHomeWork";
	public static final String FLASH_HW = EKWING_BASE_URL + "sc/hwInfo"; // 刷新作业
	public static final String SC_HW = EKWING_BASE_URL + "sc/hwinfo"; // 作业详情
	public static final String EKWING_UPGRADES = EKWING_BASE_URL
			+ "sc/saveaudio"; // 上传
	public static final String SP_EBEAN = "SP_EBEAN";
	public static final int MASTLOGIN = 10000;
	public static final int STARTS_PLAY = 88;
	public static final int FLUSH_STATUS = 111;
	public static final int FLUSH_STATUS_START = 222;
	public static final String SP_ACCOUNT = "SP_ACCOUNT";
	public static final String ACCOUNT = "ACCOUNT";
	public static final String SP_LEVEL = "SP_LEVEL";
	public static final String BORADCAST_EXIT = "com.ekwing.setting.exit";
	public static final String EKWING_IS_FIRST = "EKWING_IS_FIRST";
	public static final String EKWING_LISTEN_WRITE = "LISTEN_WRITE";
	public static final String EKWING_READ_UNDERSTAND = "READ_UNDERSTAND";
	public static final String EKWING_LISTEN_UNDERSTAND = "LISTEN_UNDERSTAND";
	public static final String STUDY_CALCULATE = EKWING_BASE_URL + "";
	public static final int ORINAL_STARTS_PLAY = 2222;
	public static final int ORINAL_PLSYFINISH = 1111;
	public static final String BUBBLE = "bubbles";
	public static final String BUBBLE_ACTION = "com.ekwing.bubbles.nums";
	public static final String MESSAGE = "messages";
	public static final String SP_PETS = "SP_PETS";
	public static final String SP_FIRST = "SP_FIRST";
	public static final String EKWING_GET_WORDS_LISTEN_WRITE = EKWING_BASE_URL
			+ "sc/gethwwordwrite"; // 作业：得到单词听写的数据
	public static final String WORDS_LISTEN_WRITE_COMMIT = EKWING_BASE_URL
			+ "sc/submithwwordwrite"; // 作业：单词听写数据提交
	public static final String EKWING_COMPLETE_WORDS_LISTEN_WRITE_QUERY = EKWING_BASE_URL
			+ "sc/gethwwordwriteanalyz"; // 作业：单词听写数据解析
	public static final String EKWING_GET_READ_UNDERSTAND = EKWING_BASE_URL
			+ "sc/gethwread"; // 作业：得到阅读理解的数据
	public static final String EKWING_READ_UNDER_COMMIT = EKWING_BASE_URL
			+ "sc/submithwread"; // 作业：阅读理解提交数据
	public static final String EKWING_COMPLETE_READ_UNDERSTAND_QUERY = EKWING_BASE_URL
			+ "sc/gethwreadanalyz"; // 作业：阅读理解数据解析
	public static final String EKWING_GET_READ_UNDER_DATAS = EKWING_BASE_URL
			+ "sc/getcgread"; // 翼学院：阅读理解数据获取
	public static final String EKWING_READ_UNDER_COMMIT_EXERCISE = EKWING_BASE_URL
			+ "sc/submitcgread"; // 翼学院：阅读理解提交
	public static final String EKWING_READ_UNDER_QUERY = EKWING_BASE_URL
			+ "sc/getcgread"; // 翼学院：阅读理解解析
	public static final String EKWING_SMART_DATA = EKWING_BASE_URL
			+ "sc/getspeaktext"; // 翼学院：朗读训练数据获取
	public static final String EKWING_SELECT_PROVICE = EKWING_BASE_URL
			+ "sc/getprovince"; // 得到省
	public static final String EKWING_SELECT_CITY = EKWING_BASE_URL
			+ "sc/getcity"; // 得到市
	public static final String EKWING_SELECT_XIAN = EKWING_BASE_URL
			+ "sc/getborough"; // 得到区县
	public static final String EKWING_SELECT_SCHOOL = EKWING_BASE_URL
			+ "sc/getschool"; // 得到学校
	public static final String NO_GET_PET = EKWING_BASE_URL + "sc/getpetconf"; // 得到宠物信息
	public static final String GET_ONE_PET = EKWING_BASE_URL + "sc/addpet"; // 领取宠物
	public static final String EKWING_SMART_UNDER = EKWING_BASE_URL
			+ "sc/getpointslist"; // 翼学院：获取训练列表（口语、阅读）
	public static final String SP_USERBEAN_PSW = "SP_USERBEAN_PSW";
	public static final String SUBMIT_SMART = EKWING_BASE_URL
			+ "sc/submitspeaktext";
	public static final String SP_SCHOOL_SELECT = "SP_SCHOOL_SELECT";
	public static final String LOGIN_SELECT_SCHOOL_NAME = "SCHOOL_NAME";
	public static final String SP_VIP = "SP_VIP";
	public static final String EKWING_READ_UNDERSTAND_TITLE = "UNDERSTAND_TITLE";
	public static final String SP_SETTING = "SP_SETTING";
	public static final String SP_CONTINUE_PLAY = "SP_CONTINUE_PLAY";
	public static final String SP_CORRECT = "SP_CORRECT";
	public static final int ISHAND = 6665;
	public static final String SP_GRADE = "SP_GRADE";
	public static final int SUBMIT_HOMEWORK = 456;
	public static final String LOGIN_SELECT_PROVINCE = "SELECT_PROVINCE";
	public static final String LOGIN_SELECT_PROVINCE_ID = "PROVINCE_ID";
	public static final String LOGIN_METHOD = "LOGIN_METHOD ";
	public static final String SP_USERBEAN_IDENTITY = "USERBEAN_IDENTITY";
	public static final String EKWING_GET_List = EKWING_BASE_URL + "sc/maplist"; // 获取列表
	public static final String EKWING_WECOME = EKWING_BASE_URL + "sc/getPush"; // 首页图片获取
	public static final String EKWING_GET_LISTEN_UNDERSTAND = EKWING_BASE_URL
			+ "sc/getlistening"; // 听力理解获取数据
	public static final String EKWING_LISTEN_UNDERSTAND_RESULT = EKWING_BASE_URL
			+ "sc/submitlistening"; // 听力理解提交
	public static final String CONNECTION = EKWING_BASE_URL + "sc/gettextbyids"; // 连词成句数据获取
	public static final String EKWING_SUBMIT_SENTENCE = EKWING_BASE_URL
			+ "sc/submitWordsentence"; // 提交连词成句
	public static final String EKWING_LISTEN_QUERY = EKWING_BASE_URL
			+ "sc/getlisteninganalyz"; // 查看听力理解解析
	public static final String EKWING_TOTAL_SUBMIT = EKWING_BASE_URL
			+ "sc/submithw"; // 总提交
	public static final String REAL_SELECT_PROVINCE = "REAL_PROVINCE";
	public static final String SP_KEY = "SP_KEY";
	public static final String KEY_STATUS = "KEY_STATUS";
	
	
	public static final String URL = "http://api.guoku.com";//生产环境地址
//	public static final String URL = "http://10.0.0.101:8000";//测试环境地址
//	public static final String URL = "http://test.guoku.com";//测试环境地址
	
	
	
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

	public static final String TABLIKE = URL + "/mobile/v4/user/";
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
	public static final String TAOREGISTER = URL + "/mobile/v4/taobao/register/";
	public static final String FORGET = URL + "/mobile/v4/forget/password/";
	public static final String USERINFO = URL + "/mobile/v4/user/";
	
	
	
	public static final String DESCRIPTOR = "1";
	public static final String WX_APPID = "wx59118ccde8270caa";
	public static final String WX_SECRET = "2200ad1c64775d37bcb0e7f74c8a0641";

	/** 是否打印友盟的log */
	public static boolean UMENG_LOG = true;
	
	
	/**关注、喜欢等广播action*/
	public static final String INTENT_ACTION = Constant.class.getName();
	public static final String INTENT_ACTION_KEY = Constant.class.getName() + "_KEY";
	public static final int INTENT_ACTION_VALUE_LIKE = 1;//喜欢
	public static final int INTENT_ACTION_VALUE_FOLLOW = 2;//关注

	public static void init(EkwingApplication lezyoApplication) {
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
						LOG_OPEN = !Utils.isEmpty(debug)
								&& debug.startsWith("yes");
						UMENG_LOG = !Utils.isEmpty(umeng)
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
