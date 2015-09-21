package com.ekwing.students.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.guoku.R;

public class LandscapeProgressBar extends View {

	private int maxProgress = 100;
	private int progress = 1;
	private int progressStrokeWidth = 30;

	RectF mRectF;
	Paint mPaint;

	public LandscapeProgressBar(Context context) {
		super(context);
		mRectF = new RectF();
		mPaint = new Paint();
	}

	public LandscapeProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mRectF = new RectF();
		mPaint = new Paint();
	}

	public LandscapeProgressBar(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mRectF = new RectF();
		mPaint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = this.getWidth();
		int height = this.getHeight();

		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.GRAY);
		canvas.drawColor(Color.TRANSPARENT);
		mPaint.setStrokeWidth(progressStrokeWidth);
		mPaint.setStyle(Style.STROKE);

		mRectF.left = progressStrokeWidth;
		mRectF.top = progressStrokeWidth;
		mRectF.right = width - progressStrokeWidth;
		mRectF.bottom = height - progressStrokeWidth;

		canvas.drawArc(mRectF, 0, width, false, mPaint);
		mPaint.setColor(getResources().getColor(R.color.base_blue));
		canvas.drawArc(mRectF, 0, ((float) progress / maxProgress) * 360,
				false, mPaint);

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
