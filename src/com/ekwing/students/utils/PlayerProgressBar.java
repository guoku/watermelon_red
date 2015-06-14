package com.ekwing.students.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.guoku.R;

public class PlayerProgressBar extends ImageView {
	private int maxProgress = 100;
	private int progress = 0;
	private int progressStrokeWidth = 10;
	// 画圆所在的距形区域
	RectF oval;
	Paint paint;

	public PlayerProgressBar(Context context) {
		super(context);
		oval = new RectF();
		paint = new Paint();
	}

	public PlayerProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 自动生成的构造函数存根
		oval = new RectF();
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// 自动生成的方法存根
		super.onDraw(canvas);
		int width = this.getWidth();
		int height = this.getHeight();

		if (width != height) {
			int min = Math.min(width, height);
			width = min;
			height = min;
		}

		paint.setAntiAlias(true); // 设置画笔为抗锯齿
		paint.setColor(Color.WHITE); // 设置画笔颜色
		canvas.drawColor(Color.TRANSPARENT); // 白色背景
		paint.setStrokeWidth(progressStrokeWidth); // 线宽
		paint.setStyle(Style.STROKE);

		oval.left = progressStrokeWidth / 2; // 左上角x
		oval.top = progressStrokeWidth / 2; // 左上角y
		oval.right = width - progressStrokeWidth / 2; // 左下角x
		oval.bottom = height - progressStrokeWidth / 2; // 右下角y

		canvas.drawArc(oval, -90, 360, false, paint); // 绘制白色圆圈，即进度条背景
		// paint.setColor(Color.rgb(0x57, 0x87, 0xb6)); //0b6ac4
		paint.setColor(Color.rgb(124, 162, 12)); // 蓝色的圆圈：即进度条
		canvas.drawArc(oval, -90, ((float) progress / maxProgress) * 360, false, paint); // 绘制进度圆弧，这里是蓝色
	}

	public void setImageAndTime(int resid, int time) {
		setBackgroundResource(R.drawable.ic_launcher);
	}

	public int getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
		this.invalidate();
	}

	/**
	 * 非UI线程调用
	 */
	public void setProgressNotInUiThread(int progress) {
		this.progress = progress;
		this.postInvalidate();
	}
}
