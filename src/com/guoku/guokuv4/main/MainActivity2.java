package com.guoku.guokuv4.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.base.NetWorkActivity;
import com.ekwing.students.utils.ToastUtil;
import com.guoku.R;
import com.guoku.guokuv4.act.LoginAct;
import com.guoku.guokuv4.act.SettingAct;
import com.guoku.guokuv4.gragment.GuangFragment;
import com.guoku.guokuv4.gragment.JingXuanFragment;
import com.guoku.guokuv4.gragment.OrderFragment;
import com.guoku.guokuv4.gragment.PersonalFragment;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

public class MainActivity2 extends NetWorkActivity {

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

	@ViewInject(R.id.main_bar_tv_1)
	private TextView main_bar_tv_1;// 个人

	@ViewInject(R.id.main_bar_tv_2)
	private TextView main_bar_tv_2;// 个人

	@ViewInject(R.id.main_bar_tv_3)
	private TextView main_bar_tv_3;// 个人

	@ViewInject(R.id.main_bar_tv_4)
	private TextView main_bar_tv_4;// 个人

	// private ImageView iv_destination;//
	// private ImageView iv_guang;//
	// private ImageView iv_order;//
	// private ImageView iv_personal;//
	private Fragment mContent;
	private JingXuanFragment destinationFragment;
	private GuangFragment qunaerFragment;
	private PersonalFragment personalFragment;
	private OrderFragment orderFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		UmengUpdateAgent.update(this);
	}

	@Override
	protected void setupData() {
		destinationFragment = new JingXuanFragment();
		qunaerFragment = new GuangFragment();
		personalFragment = new PersonalFragment();
		orderFragment = new OrderFragment();

		commitFragment(R.id.fl_content, destinationFragment);
		mContent = destinationFragment;

		if (EkwingApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
			main_bar_tv_4.setText("设置");
		} else {
			main_bar_tv_4.setText("我");
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
		if (EkwingApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
			main_bar_tv_4.setText("设置");
		} else {
			main_bar_tv_4.setText("我");
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}
	}

	/**
	 * 整个应用处于低内存状态下的回调
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		ImageLoader.getInstance().clearMemoryCache();
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

	@OnClick(R.id.ll_destination)
	public void ll_destination(View v) {
		if (destinationFragment == null) {
			destinationFragment = new JingXuanFragment();
		}
		main_bar_tv_1.setTextColor(Color.WHITE);
		main_bar_tv_2.setTextColor(Color.rgb(157, 158, 159));
		main_bar_tv_3.setTextColor(Color.rgb(157, 158, 159));
		main_bar_tv_4.setTextColor(Color.rgb(157, 158, 159));
		switchContent(destinationFragment);
		main_bar_1.setImageResource(R.drawable.tabbar_icon_selection_press);
		main_bar_2.setImageResource(R.drawable.tabbar_icon_discover);
		main_bar_3.setImageResource(R.drawable.tabbar_icon_notifaction);
		if (EkwingApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
			main_bar_tv_4.setText("设置");
		} else {
			main_bar_tv_4.setText("我");
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}
	}

	@OnClick(R.id.ll_order)
	public void ll_order(View v) {
		if (EkwingApplication.getInstance().getBean() != null) {
			if (orderFragment == null) {
				orderFragment = new OrderFragment();
			}
			switchContent(orderFragment);
			main_bar_1.setImageResource(R.drawable.tabbar_icon_selection);
			main_bar_2.setImageResource(R.drawable.tabbar_icon_discover);
			main_bar_3
					.setImageResource(R.drawable.tabbar_icon_notifaction_press);
			if (EkwingApplication.getInstance().getBean() == null) {
				main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
				main_bar_tv_4.setText("设置");
			} else {
				main_bar_tv_4.setText("我");
				main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
			}
			main_bar_tv_1.setTextColor(Color.rgb(157, 158, 159));
			main_bar_tv_2.setTextColor(Color.rgb(157, 158, 159));
			main_bar_tv_3.setTextColor(Color.WHITE);
			main_bar_tv_4.setTextColor(Color.rgb(157, 158, 159));
		} else {
			ToastUtil.show(mContext, "请登录");
			startActivity(new Intent(this, LoginAct.class));
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
		if (EkwingApplication.getInstance().getBean() == null) {
			main_bar_4.setImageResource(R.drawable.tabbar_icon_setting);
			main_bar_tv_4.setText("设置");
		} else {
			main_bar_tv_4.setText("我");
			main_bar_4.setImageResource(R.drawable.tabbar_icon_me);
		}

		main_bar_tv_1.setTextColor(Color.rgb(157, 158, 159));
		main_bar_tv_2.setTextColor(Color.WHITE);
		main_bar_tv_3.setTextColor(Color.rgb(157, 158, 159));
		main_bar_tv_4.setTextColor(Color.rgb(157, 158, 159));
	}

	@OnClick(R.id.ll_personal)
	public void ll_personal(View v) {
		if (EkwingApplication.getInstance().getBean() != null) {
			if (personalFragment == null) {
				personalFragment = new PersonalFragment();
			}
			switchContent(personalFragment);

			main_bar_1.setImageResource(R.drawable.tabbar_icon_selection);
			main_bar_2.setImageResource(R.drawable.tabbar_icon_discover);
			main_bar_3.setImageResource(R.drawable.tabbar_icon_notifaction);
			if (EkwingApplication.getInstance().getBean() == null) {
				main_bar_4
						.setImageResource(R.drawable.tabbar_icon_setting_press);
			} else
				main_bar_4.setImageResource(R.drawable.tabbar_icon_me_press);

			main_bar_tv_1.setTextColor(Color.rgb(157, 158, 159));
			main_bar_tv_2.setTextColor(Color.rgb(157, 158, 159));
			main_bar_tv_3.setTextColor(Color.rgb(157, 158, 159));
			main_bar_tv_4.setTextColor(Color.WHITE);
		} else {
			ToastUtil.show(mContext, "请登录");
			startActivity(new Intent(this, SettingAct.class));
		}
	}

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.ll_destination:
	// // iv_destination.setBackgroundResource(R.drawable.tabbar_home_press);
	// // iv_guang.setBackgroundResource(R.drawable.tabbar_guang_normal);
	// // iv_order.setBackgroundResource(R.drawable.tabbar_order_normal);
	// // iv_personal
	// // .setBackgroundResource(R.drawable.tabbar_personal_normal);
	// if (destinationFragment == null) {
	// destinationFragment = new DestinationFragment();
	// }
	// switchContent(destinationFragment);
	// MobclickAgent.onEvent(this, "event_1");
	// break;
	// case R.id.ll_guang:
	// // iv_destination.setBackgroundResource(R.drawable.tabbar_home_normal);
	// // iv_guang.setBackgroundResource(R.drawable.tabbar_guang_press);
	// // iv_order.setBackgroundResource(R.drawable.tabbar_order_normal);
	// // iv_personal
	// // .setBackgroundResource(R.drawable.tabbar_personal_normal);
	// if (qunaerFragment == null) {
	// qunaerFragment = new GuangFragment();
	// // DataManager.guangFragment = qunaerFragment;
	// }
	// switchContent(qunaerFragment);
	// MobclickAgent.onEvent(this, "event_2");
	// break;
	// case R.id.ll_order:
	// // iv_destination.setBackgroundResource(R.drawable.tabbar_home_normal);
	// // iv_guang.setBackgroundResource(R.drawable.tabbar_guang_normal);
	// // iv_order.setBackgroundResource(R.drawable.tabbar_order_press);
	// // iv_personal
	// // .setBackgroundResource(R.drawable.tabbar_personal_normal);
	// if (orderFragment == null) {
	// orderFragment = new OrderFragment();
	// }
	// switchContent(orderFragment);
	// MobclickAgent.onEvent(this, "event_4");
	// break;
	// case R.id.ll_personal:
	// // iv_destination.setBackgroundResource(R.drawable.tabbar_home_normal);
	// // iv_guang.setBackgroundResource(R.drawable.tabbar_guang_normal);
	// // iv_order.setBackgroundResource(R.drawable.tabbar_order_normal);
	// // iv_personal.setBackgroundResource(R.drawable.tabbar_personal_press);
	// if (personalFragment == null) {
	// personalFragment = new PersonalFragment();
	// }
	// switchContent(personalFragment);
	// MobclickAgent.onEvent(this, "event_5");
	// break;
	//
	// default:
	// break;
	// }
	// }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		switchContent(destinationFragment);
		// iv_destination.setBackgroundResource(R.drawable.tabbar_home_press);
		// iv_guang.setBackgroundResource(R.drawable.tabbar_guang_normal);
		// iv_order.setBackgroundResource(R.drawable.tabbar_order_normal);
		// iv_personal.setBackgroundResource(R.drawable.tabbar_personal_normal);
	}

	/** 修改显示的内容 不会重新加载 **/
	public void switchContent(Fragment to) {
		if (mContent != to) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(mContent).add(R.id.fl_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
			}
			mContent = to;
		}
	}

	// @Override
	// public boolean dispatchKeyEvent(KeyEvent event) {
	// if (event.getAction() == KeyEvent.ACTION_DOWN
	// && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	// // if (DataManager.ifInGuang == false) {
	// // ServiceUtils.showExitDialog(this);
	// // } else {
	// // // 判断是否进入逛一逛界面，进入的话返回。
	// // getSupportFragmentManager().popBackStack();
	// // DataManager.ifInGuang = false;
	// // }
	// return true;
	// }
	// return super.dispatchKeyEvent(event);
	// }

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

}