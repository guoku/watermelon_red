package com.ekwing.students.utils;

import com.ekwing.students.config.Logger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * 自定义水平进度条的工具类
 * 
 * @author think
 * 
 */
public class MyProgressBar extends ProgressBar {
	private String text_progress;
	private Paint mPaint;// 画笔

	public MyProgressBar(Context context) {
		super(context);
		initPaint();
		Logger.e("111111111111111", "111111111111");
	}

	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		Logger.e("22222", "2222222222222222222222222");
		initPaint();
	}

	public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}

	@Override
	public synchronized void setProgress(int progress) {
		Logger.e("33333333333333333333333", "33333333333333333333333");
		super.setProgress(progress);
		setTextProgress(progress);
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect rect = new Rect();
		this.mPaint.getTextBounds(this.text_progress, 0, this.text_progress.length(), rect);
		int x = (getWidth() / 2) - rect.centerX();
		int y = (getHeight() / 2) - rect.centerY();
		canvas.drawText(this.text_progress, x, y, this.mPaint);
	}

	/**
	 * 
	 * description: 初始化画笔 Create by lll on 2013-8-13 下午1:41:49
	 */
	private void initPaint() {
		this.mPaint = new Paint();
		this.mPaint.setAntiAlias(true);
		this.mPaint.setTextSize(32);
		this.mPaint.setColor(Color.WHITE);
	}

	private void setTextProgress(int progress) {
		this.text_progress = progress + "/" + this.getMax();
	}

}