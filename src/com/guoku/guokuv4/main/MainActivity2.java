package com.guoku.guokuv4.main;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.act.EntityAct;
import com.guoku.guokuv4.act.IntroAct;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.SettingAct;
import com.guoku.guokuv4.act.WebShareAct;
import com.guoku.guokuv4.base.BaseActivity.OnDoubleClickListener;
import com.guoku.guokuv4.bean.PushBean;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.base.UserBaseFrament;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.gragment.GuangFragment;
import com.guoku.guokuv4.gragment.JingXuanPageFragment;
import com.guoku.guokuv4.gragment.OrderFragment;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.service.DownLoadService;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.jpush.android.api.JPushInterface;

public class MainActivity2 extends NetWorkActivity implements OnDoubleClickListener {

	private final int PROINFO = 1001;
	private final int USERINFO = 1002;

	@ViewInject(R.id.ll_destination)
	private LinearLayout ll_destination;// 目的地

	@ViewInject(R.id.ll_guang)
	private LinearLayout ll_guang;// 去哪儿

	@ViewInject(R.id.ll_order)
	private LinearLayout ll_order;// 攻略

	@ViewInject(R.id.ll_personal)
	private LinearLayout ll_personal;// 个人

	@ViewInject(R.id.main_bar_1)
	private ImageView main_bar_1;// 个人

	@ViewInject(R.id.main_bar_2)
	private ImageView main_bar_2;// 个人

	@ViewInject(R.id.main_bar_3)
	private ImageView main_bar_3;// 个人

	@ViewInject(R.id.main_bar_4)
	private ImageView main_bar_4;// 个人

	private Fragment mContent;

	private JingXuanPageFragment jingXuanPageFragment;
	private GuangFragment qunaerFragment;
	private PersonalFragment personalFragment;
	private OrderFragment orderFragment;

	private static Boolean isExit = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		UmengUpdateAgent.update(this);

		registerDoubleClickListener(main_bar_1, this);

		startLaunch();

		resolveJPush();
	}

	@Override
	protected void setupData() {

		jingXuanPageFragment = new JingXuanPageFragment();
		// destinationFragment = new JingXuanFragment();
		qunaerFragment = new GuangFragment();
		personalFragment = new PersonalFragment();
		orderFragment = new OrderFragment();

		// commitFragment(R.id.fl_content, destinationFragment);
		// mContent = destinationFragment;

		commitFragment(R.id.fl_content, jingXuanPageFragment);
		mContent = jingXuanPageFragment;

		if (GuokuApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
		} else {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}
	}

	/**
	 * 检查应用更新
	 */
	private void checkUpdata() {
		// 禁用友盟的集成测试；这是开发阶段使用的！
		// UmengUpdateAgent.setUpdateCheckConfig(false);
		// 取消应用仅仅在wifi状态下检查网络！
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		// 检查更新
		UmengUpdateAgent.update(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/**
		 * 友盟的统计功能
		 */
		MobclickAgent.onPageStart(this.getClass().getSimpleName()); // 统计页面
		MobclickAgent.onResume(this);
		if (GuokuApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
		} else {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		/**
		 * 友盟的统计功能，注意这个的代码顺序不能变化！
		 */
		MobclickAgent.onPageEnd(this.getClass().getSimpleName());
		MobclickAgent.onPause(this);
	}

	/**
	 * 整个应用处于低内存状态下的回调
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		ImageLoader.getInstance().clearMemoryCache();
	}

	@OnClick(R.id.ll_destination)
	public void ll_destination(View v) {

		if (jingXuanPageFragment == null) {
			jingXuanPageFragment = new JingXuanPageFragment();
		}
		// switchContent(destinationFragment);
		switchContent(jingXuanPageFragment);
		main_bar_1.setImageResource(R.drawable.tabbar_icon_selection_press);
		main_bar_2.setImageResource(R.drawable.tabbar_icon_discover);
		main_bar_3.setImageResource(R.drawable.tabbar_icon_notifaction);
		if (GuokuApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
		} else {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}
	}

	@OnClick(R.id.ll_order)
	public void ll_order(View v) {
		if (GuokuApplication.getInstance().getBean() != null) {
			if (orderFragment == null) {
				orderFragment = new OrderFragment();
			}
			switchContent(orderFragment);
			main_bar_1.setImageResource(R.drawable.tabbar_icon_selection);
			main_bar_2.setImageResource(R.drawable.tabbar_icon_discover);
			main_bar_3.setImageResource(R.drawable.tabbar_icon_notifaction_press);
			if (GuokuApplication.getInstance().getBean() == null) {
				main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
			} else {
				main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
			}
		} else {
			openLogin();
		}
	}

	@OnClick(R.id.ll_guang)
	public void ll_guang(View v) {
		if (qunaerFragment == null) {
			qunaerFragment = new GuangFragment();
		}
		switchContent(qunaerFragment);

		main_bar_1.setImageResource(R.drawable.tabbar_icon_selection);
		main_bar_2.setImageResource(R.drawable.tabbar_icon_discover_press);
		main_bar_3.setImageResource(R.drawable.tabbar_icon_notifaction);
		if (GuokuApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
		} else {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}

		removeFrameLayout();// 删除引导页
	}

	@OnClick(R.id.ll_personal)
	public void ll_personal(View v) {
		if (GuokuApplication.getInstance().getBean() != null) {
			if (personalFragment == null) {
				personalFragment = new PersonalFragment();
			}
			switchContent(personalFragment);

			main_bar_1.setImageResource(R.drawable.tabbar_icon_selection);
			main_bar_2.setImageResource(R.drawable.tabbar_icon_discover);
			main_bar_3.setImageResource(R.drawable.tabbar_icon_notifaction);
			if (GuokuApplication.getInstance().getBean() == null) {
				main_bar_4.setImageResource(R.drawable.tabbar_icon_setting_press);
			} else
				main_bar_4.setImageResource(R.drawable.tabbar_icon_me_press);
		} else {
			startActivity(new Intent(this, SettingAct.class));
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		// switchContent(destinationFragment);
		switchContent(jingXuanPageFragment);
	}

	/** 修改显示的内容 不会重新加载 **/
	public void switchContent(Fragment to) {
		if (mContent != to) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(mContent).add(R.id.fl_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
			}
			mContent = to;
		}
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (where) {
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case USERINFO:
			try {
				JSONObject root = new JSONObject(result);
				UserBean uBean = (UserBean) JSON.parseObject(root.getString("user"), UserBean.class);
				intent = new Intent(mContext, UserBaseFrament.class);
				intent.putExtra("data", uBean);
				startActivity(intent);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		switch (where) {
		case PROINFO:
			ToastUtil.show(mContext, "获取商品信息失败，请稍后再试");
			break;
		case USERINFO:
			ToastUtil.show(mContext, "获取用户信息失败，请稍后再试");
			break;
		default:
			break;
		}
	}

	@Override
	public void setCurrentItems() {
		// TODO Auto-generated method stub
		jingXuanPageFragment.setCurrentItems(1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (qunaerFragment != null) {
			if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && event.getAction() == KeyEvent.ACTION_DOWN) {
				qunaerFragment.onKeyDowns();
				return true;
			}
			if (qunaerFragment.listSearchLog != null) {
				if (qunaerFragment.viewLog.getVisibility() == View.VISIBLE) {
					qunaerFragment.hideSearchWhat();
					return true;
				}
			}
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 注册一个双击事件
	 */
	public void registerDoubleClickListener(View view, final OnDoubleClickListener listener) {
		if (listener == null)
			return;
		view.setOnClickListener(new View.OnClickListener() {
			private static final int DOUBLE_CLICK_TIME = 350; // 双击间隔时间350毫秒
			private boolean waitDouble = true;

			private Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					listener.OnSingleClick((View) msg.obj);
				}

			};

			// 等待双击
			public void onClick(final View v) {
				if (waitDouble) {
					waitDouble = false; // 与执行双击事件
					new Thread() {

						public void run() {
							try {
								Thread.sleep(DOUBLE_CLICK_TIME);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // 等待双击时间，否则执行单击事件
							if (!waitDouble) {
								// 如果过了等待事件还是预执行双击状态，则视为单击
								waitDouble = true;
								Message msg = handler.obtainMessage();
								msg.obj = v;
								handler.sendMessage(msg);
							}
						}

					}.start();
				} else {
					waitDouble = true;
					listener.OnDoubleClick(v); // 执行双击
				}
			}
		});
	}

	@Override
	public void OnSingleClick(View v) {
		// TODO Auto-generated method stub
		if (jingXuanPageFragment == null) {
			jingXuanPageFragment = new JingXuanPageFragment();
		}
		switchContent(jingXuanPageFragment);
		main_bar_1.setImageResource(R.drawable.tabbar_icon_selection_press);
		main_bar_2.setImageResource(R.drawable.tabbar_icon_discover);
		main_bar_3.setImageResource(R.drawable.tabbar_icon_notifaction);
		if (GuokuApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
		} else {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}
	}

	@Override
	public void OnDoubleClick(View v) {
		// TODO Auto-generated method stub
		if (jingXuanPageFragment.currIndex == 0) {
			jingXuanPageFragment.goodTwoFragmnet.jingxuan_lv_1.getRefreshableView().smoothScrollToPosition(0);
		}
		if (jingXuanPageFragment.currIndex == 1) {
			jingXuanPageFragment.articleFragment.listViewArtivle.getRefreshableView().smoothScrollToPosition(0);
		}
	}

	/**
	 * 启动引导页 versioncode大于才接口version才启动
	 */
	private void startLaunch() {

		if (GuokuApplication.getInstance().getLaunchBean() != null) {
			try {
				PackageInfo pinfo = getPackageManager().getPackageInfo("com.guoku", PackageManager.GET_CONFIGURATIONS);
				if (pinfo.versionCode >= GuokuApplication.getInstance().getLaunchBean().getVersion()) {
					if (!StringUtils.isEmpty(GuokuApplication.getInstance().getLaunchBean().getLaunch_image_url())) {
						String path = Constant.LAUNCH_PATH + StringUtils
								.setReplace(GuokuApplication.getInstance().getLaunchBean().getLaunch_image_url());
						File file = new File(path);
						if (!file.exists()) {
							Intent intent = new Intent(this, DownLoadService.class);
							startService(intent);
						} else {
							if (SharePrenceUtil.getLaunch(this)) {
								openActivity(IntroAct.class);
							}
						}
					}
				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			ToastUtil.show(mContext, mContent.getString(R.string.tv_exit_tose));
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 1000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}

	// for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.guoku.guokuv4.main.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";

	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				String messge = intent.getStringExtra(KEY_MESSAGE);
				String extras = intent.getStringExtra(KEY_EXTRAS);
				StringBuilder showMsg = new StringBuilder();
				showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
				if (!StringUtils.isEmpty(extras)) {
					showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
				}
				setCostomMsg(showMsg.toString());
			}
		}
	}

	private void setCostomMsg(String msg) {

		ToastUtil.show(mContext, msg);
	}

	/**
	 * 点击处理jpush推送消息逻辑
	 */
	private void resolveJPush() {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {

			Log.d("EXTRA_EXTRA=========", bundle.getString(JPushInterface.EXTRA_EXTRA));
			PushBean pushBean = JSON.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA), PushBean.class);
			if (pushBean != null) {
				if (!StringUtils.isEmpty(pushBean.getUrl())) {
					if (pushBean.getUrl().contains(Constant.ACTION_HTTP)) {
						Sharebean sharebean = new Sharebean();
						sharebean.setAricleUrl(pushBean.getUrl());
						bundle.putSerializable(WebShareAct.class.getName(), sharebean);
						openActivity(WebShareAct.class, bundle);
					} else if (pushBean.getUrl().contains(Constant.ACTION_ENTITY)) {
						String id = pushBean.getUrl().replace(Constant.ACTION_ENTITY, "");
						sendConnection(Constant.PROINFO + id, new String[] { "entity_id" }, new String[] { id },
								PROINFO, true);
						umStatistics(Constant.UM_INTRO, id, pushBean.getUrl());
					} else if (pushBean.getUrl().contains(Constant.ACTION_TAG)) {
						if (GuokuApplication.getInstance().getBean() == null) {
							openLogin();
						} else {
							String tagStr = pushBean.getUrl().replace(Constant.ACTION_TAG, "").trim().replace("#", "");
							Intent intent = new Intent(this, EntityAct.class);
							intent.putExtra("data", GuokuApplication.getInstance().getBean().getUser().getUser_id());
							intent.putExtra("name", tagStr);
							startActivity(intent);
						}
					} else if (pushBean.getUrl().contains(Constant.ACTION_ARTICLE)) {
						Sharebean sharebean = new Sharebean();

						String tempUrl = "http://m.guoku.com/articles/"
								+ pushBean.getUrl().replace(Constant.ACTION_ARTICLE, "") + "/?from=app";
						sharebean.setAricleUrl(tempUrl);
						bundle.putSerializable(WebShareAct.class.getName(), sharebean);
						openActivity(WebShareAct.class, bundle);
					} else if (pushBean.getUrl().contains(Constant.ACTION_USER)) {
						sendConnection(Constant.USERINFO + pushBean.getUrl().replace(Constant.ACTION_USER, ""),
								new String[] { "timestamp" }, new String[] { System.currentTimeMillis() / 1000 + "" },
								USERINFO, false);

					}
				}
			}
		}
	}

}