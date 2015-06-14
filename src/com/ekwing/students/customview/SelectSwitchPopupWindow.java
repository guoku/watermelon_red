//package com.ekwing.students.customview;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//
//import com.ekwing.students.utils.SharePrenceUtil;
//import com.guoku.R;
//
//public class SelectSwitchPopupWindow extends PopupWindow {
//
//	private View mMenuView;
//	private ImageView switch1;
//	private ImageView switch2;
//	private Context context;
//
//	public SelectSwitchPopupWindow(Activity contexts, final boolean vip) {
//		super(contexts);
//		this.context = contexts;
//		// LayoutInflater inflater = (LayoutInflater) context
//		// .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		// mMenuView = inflater.inflate(R.layout.alert_dialog, null);
//		mMenuView = View.inflate(context, R.layout.dialog_smart, null);
//		switch1 = (ImageView) mMenuView.findViewById(R.id.switch1);
//		switch2 = (ImageView) mMenuView.findViewById(R.id.switch2);
//		// 设置SelectPicPopupWindow的View
//		int h = contexts.getWindowManager().getDefaultDisplay().getHeight();
//		int w = contexts.getWindowManager().getDefaultDisplay().getWidth();
//		this.setContentView(mMenuView);
//		// 设置SelectPicPopupWindow弹出窗体的宽
//		this.setWidth(w / 2);
//		// 设置SelectPicPopupWindow弹出窗体的高
//		this.setHeight(LayoutParams.WRAP_CONTENT);
//		// 设置SelectPicPopupWindow弹出窗体可点击
//		this.setFocusable(true);
//		// 设置SelectPicPopupWindow弹出窗体动画效果
//		// this.setAnimationStyle(R.style.AnimBottom);
//		// 实例化一个ColorDrawable颜色为半透明
//		ColorDrawable dw = new ColorDrawable(0xffffff);
//		// 设置SelectPicPopupWindow弹出窗体的背景
//		this.setBackgroundDrawable(dw);
//		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//		mMenuView.setOnTouchListener(new OnTouchListener() {
//
//			public boolean onTouch(View v, MotionEvent event) {
//
//				int height = mMenuView.findViewById(R.id.pop_layou).getTop();
//				int y = (int) event.getY();
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					if (y < height) {
//						dismiss();
//					}
//				}
//				return true;
//			}
//		});
//
//		if (SharePrenceUtil.getContinueRead(context)) {
//			switch1.setImageResource(R.drawable.switch_open);
//		} else {
//			switch1.setImageResource(R.drawable.switch_close);
//		}
//
//		if (SharePrenceUtil.getCorrection(context)) {
//			switch2.setImageResource(R.drawable.switch_open);
//		} else {
//			switch2.setImageResource(R.drawable.switch_close);
//		}
//
//		// 设置按钮监听
//		switch1.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				if (SharePrenceUtil.getContinueRead(context)) {
//					switch1.setImageResource(R.drawable.switch_close);
//					SharePrenceUtil.setContinueRead(context, false);
//				} else {
//					switch1.setImageResource(R.drawable.switch_open);
//					SharePrenceUtil.setContinueRead(context, true);
//				}
//			}
//		});
//
//		switch2.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				if (vip) {
//					if (SharePrenceUtil.getCorrection(context)) {
//						switch2.setImageResource(R.drawable.switch_close);
//						SharePrenceUtil.setCorrection(context, false);
//					} else {
//						switch2.setImageResource(R.drawable.switch_open);
//						SharePrenceUtil.setCorrection(context, true);
//					}
//				} else {
//					VIPDialog dialog = new VIPDialog(context);
//					dialog.show();
//				}
//			}
//		});
//
//	}
//
// }
