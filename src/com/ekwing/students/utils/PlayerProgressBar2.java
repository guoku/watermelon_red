//package com.ekwing.students.utils;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.graphics.RectF;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.util.AttributeSet;
//import android.widget.ImageView;
//
//import com.guoku.R;
//
//public class PlayerProgressBar2 extends ImageView {
//	public static final int START_PROGRESS = 401;
//	public static final int CURRENT_PROGRESS = 402;
//	public static final int FINISH_PROGRESS = 403;
//	private int maxProgress = 100;
//	private int progress = 0;
//	private int progressStrokeWidth = 10;
//	RectF oval;
//	Paint paint;
//	private int s;
//	private Handler handler;
//	private CountDownTimer timer;
//	private int curStatus;
//	private boolean isProgress;
//
//	public int getCurStatus() {
//		return curStatus;
//	}
//
//	private void setCurStatus(int curStatus) {
//		this.curStatus = curStatus;
//	}
//
//	public PlayerProgressBar2(Context context, Handler handler) {
//		super(context);
//		oval = new RectF();
//		// this.handler = handler;
//		paint = new Paint();
//	}
//
//	public PlayerProgressBar2(Context context, AttributeSet attrs,
//			Handler handler) {
//		super(context, attrs);
//		// 自动生成的构造函数存根
//		// this.handler = handler;
//		oval = new RectF();
//		paint = new Paint();
//	}
//
//	@Override
//	protected void onDraw(Canvas canvas) {
//		// 自动生成的方法存根
//		super.onDraw(canvas);
//		int width = this.getWidth();
//		int height = this.getHeight();
//
//		if (width != height) {
//			int min = Math.min(width, height);
//			width = min;
//			height = min;
//		}
//
//		paint.setAntiAlias(true); // 设置画笔为抗锯齿
//		paint.setColor(Color.WHITE); // 设置画笔颜色
//		canvas.drawColor(Color.TRANSPARENT); // 白色背景
//		paint.setStrokeWidth(progressStrokeWidth); // 线宽
//		paint.setStyle(Style.STROKE);
//
//		oval.left = progressStrokeWidth / 2; // 左上角x
//		oval.top = progressStrokeWidth / 2; // 左上角y
//		oval.right = width - progressStrokeWidth / 2; // 左下角x
//		oval.bottom = height - progressStrokeWidth / 2; // 右下角y
//
//		canvas.drawArc(oval, -90, 360, false, paint); // 绘制白色圆圈，即进度条背景
//		// paint.setColor(Color.rgb(0x57, 0x87, 0xb6)); //0b6ac4
//		paint.setColor(Color.rgb(124, 162, 12)); // 蓝色的圆圈：即进度条
//		canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360,
//				false, paint); // 绘制进度圆弧，这里是蓝色
//	}
//
//	public void setImageAndTime(int resid, int time) {
//		setBackgroundResource(R.drawable.ic_launcher);
//	}
//
//	public int getMaxProgress() {
//		return maxProgress;
//	}
//
//	public void setMaxProgress(int maxProgress) {
//		this.maxProgress = maxProgress;
//	}
//
//	public void setProgress(int progress) {
//		this.progress = progress;
//		this.invalidate();
//	}
//
//	public void startProgress(final Handler handler, int ti, final Runnable r1,
//			final Runnable r2) {
//		setCurStatus(START_PROGRESS);
//		isProgress = false;
//		s = 0;
//		timer = new CountDownTimer(ti, ti / 100) {
//			@Override
//			public void onTick(long millisUntilFinished) {
//				setCurStatus(CURRENT_PROGRESS);
//				setImageResource(R.drawable.record_green);
//				setProgress(s);
//				s = s + 1;
//				handler.post(r1);
//				isProgress = true;
//			}
//
//			@Override
//			public void onFinish() {
//				setCurStatus(FINISH_PROGRESS);
//				s = 0;
//				setProgress(s);
//				setImageResource(R.drawable.record_grey);
//				handler.post(r2);
//				isProgress = false;
//			}
//		}.start();
//	}
//
//	public void cancelProgress() {
//		timer.cancel();
//		isProgress = false;
//	}
//
//	/**
//	 * 非UI线程调用
//	 */
//	public void setProgressNotInUiThread(int progress) {
//		this.progress = progress;
//		this.postInvalidate();
//	}
// }
