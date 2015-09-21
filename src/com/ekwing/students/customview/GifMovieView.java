package com.ekwing.students.customview;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.ekwing.students.config.Logger;

public class GifMovieView extends View {

	private static final int DEFAULT_MOVIEW_DURATION = 1000;

	private int mMovieResourceId;
	private Movie mMovie;

	private long mMovieStart;
	private int mCurrentAnimationTime = 0;

	/**
	 * Position for drawing animation frames in the center of the view.
	 */
	private float mLeft;
	private float mTop;

	/**
	 * Scaling factor to fit the animation within view bounds.
	 */
	private float mScale;
	private Handler handler;
	private String path;

	/**
	 * Scaled movie frames width and height.
	 */
	private int mMeasuredMovieWidth;
	private int mMeasuredMovieHeight;

	private volatile boolean mPaused = false;
	private boolean mVisible = true;

	private ArrayList<String> resIds;
	private int maxIndex, curindex;

	private boolean show, playFirst;

	private Thread thread;

	public GifMovieView(Context context) {
		this(context, null);
	}

	public GifMovieView(Context context, AttributeSet attrs) {
		this(context, attrs, -1);
	}

	public GifMovieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setViewAttributes(context, attrs, defStyle);
	}

	@SuppressLint("NewApi")
	private void setViewAttributes(Context context, AttributeSet attrs,
			int defStyle) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}

	}

	public void setMovieResource(int movieResId) {
		this.mMovieResourceId = movieResId;
		mMovie = Movie.decodeStream(getResources().openRawResource(
				mMovieResourceId));
		requestLayout();
	}

	public void setMovieResourceByPath(String path) {
		try {
			InputStream is = null;
			try {
				is = new BufferedInputStream(new FileInputStream(path),
						16 * 1024);
				is.mark(16 * 1024);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mMovie = Movie.decodeStream(is);
			requestLayout();
		} catch (Exception e) {

		}
	}

	public void setMovieResource(ArrayList<String> movieResIds,
			final Handler handler) {
		this.resIds = movieResIds;
		show = true;
		this.maxIndex = movieResIds.size();
		this.handler = handler;
		this.path = path;
		this.playFirst = true;
		setMovieResourceByPath(movieResIds.get(curindex));

	}

	public void playNext() {
		Logger.e("tag", "index-->" + curindex);
		curindex++;
		if (curindex < maxIndex && playFirst) {
			setMovieResourceByPath(resIds.get(curindex));
			playFirst = false;
			if (!playFirst) {
				thread = new Thread() {
					public void run() {
						try {
							sleep(1000 * 3);
							handler.post(new Runnable() {
								@Override
								public void run() {
									setMovieResourceByPath(resIds.get(0));
									playFirst = true;
								}
							});
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					};
				};
				thread.start();
			}
		} else {
			curindex = 0;
		}
	}

	public void setMovieResource(String path) {
		File file = new File(path);
		InputStream is;
		try {
			is = new FileInputStream(file);
			mMovie = Movie.decodeStream(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		requestLayout();
	}

	public void setMovie(Movie movie) {
		this.mMovie = movie;
		requestLayout();
	}

	public void setDisMiss() {
		show = false;
	}

	public Movie getMovie() {
		return mMovie;
	}

	public void setMovieTime(int time) {
		mCurrentAnimationTime = time;
		invalidate();
	}

	public void setPaused(boolean paused) {
		this.mPaused = paused;

		/**
		 * Calculate new movie start time, so that it resumes from the same
		 * frame.
		 */
		if (!paused) {
			mMovieStart = android.os.SystemClock.uptimeMillis()
					- mCurrentAnimationTime;
		}

		invalidate();
	}

	public boolean isPaused() {
		return this.mPaused;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (mMovie != null) {
			int movieWidth = mMovie.width();
			int movieHeight = mMovie.height();

			/*
			 * Calculate horizontal scaling
			 */
			float scaleH = 1f;
			int measureModeWidth = MeasureSpec.getMode(widthMeasureSpec);

			if (measureModeWidth != MeasureSpec.UNSPECIFIED) {
				int maximumWidth = MeasureSpec.getSize(widthMeasureSpec);
				if (movieWidth > maximumWidth) {
					scaleH = (float) movieWidth / (float) maximumWidth;
				}
			}

			/*
			 * calculate vertical scaling
			 */
			float scaleW = 1f;
			int measureModeHeight = MeasureSpec.getMode(heightMeasureSpec);

			if (measureModeHeight != MeasureSpec.UNSPECIFIED) {
				int maximumHeight = MeasureSpec.getSize(heightMeasureSpec);
				if (movieHeight > maximumHeight) {
					scaleW = (float) movieHeight / (float) maximumHeight;
				}
			}

			/*
			 * calculate overall scale
			 */
			mScale = 1f / Math.max(scaleH, scaleW);

			mMeasuredMovieWidth = (int) (movieWidth * mScale);
			mMeasuredMovieHeight = (int) (movieHeight * mScale);

			setMeasuredDimension(mMeasuredMovieWidth, mMeasuredMovieHeight);

		} else {
			/*
			 * No movie set, just set minimum available size.
			 */
			setMeasuredDimension(getSuggestedMinimumWidth(),
					getSuggestedMinimumHeight());
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		/*
		 * Calculate left / top for drawing in center
		 */
		mLeft = (getWidth() - mMeasuredMovieWidth) / 2f;
		mTop = (getHeight() - mMeasuredMovieHeight) / 2f;

		mVisible = getVisibility() == View.VISIBLE;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mMovie != null) {
			if (!mPaused) {
				updateAnimationTime();
				drawMovieFrame(canvas);
				invalidateView();
			} else {
				drawMovieFrame(canvas);
			}
		}
	}

	/**
	 * Invalidates view only if it is visible. <br>
	 * {@link #postInvalidateOnAnimation()} is used for Jelly Bean and higher.
	 * 
	 */
	@SuppressLint("NewApi")
	private void invalidateView() {
		if (mVisible) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				postInvalidateOnAnimation();
			} else {
				invalidate();
			}
		}
	}

	/**
	 * Calculate current animation time
	 */
	private void updateAnimationTime() {
		long now = android.os.SystemClock.uptimeMillis();

		if (mMovieStart == 0) {
			mMovieStart = now;
		}

		int dur = mMovie.duration();

		if (dur == 0) {
			dur = DEFAULT_MOVIEW_DURATION;
		}

		mCurrentAnimationTime = (int) ((now - mMovieStart) % dur);
	}

	/**
	 * Draw current GIF frame
	 */
	private void drawMovieFrame(Canvas canvas) {

		mMovie.setTime(mCurrentAnimationTime);

		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		canvas.scale(mScale, mScale);
		mMovie.draw(canvas, mLeft / mScale, mTop / mScale);
		canvas.restore();
	}

	@SuppressLint("NewApi")
	@Override
	public void onScreenStateChanged(int screenState) {
		super.onScreenStateChanged(screenState);
		mVisible = screenState == SCREEN_STATE_ON;
		invalidateView();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		mVisible = visibility == View.VISIBLE;
		invalidateView();
	}
}
