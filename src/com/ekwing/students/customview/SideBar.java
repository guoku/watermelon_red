package com.ekwing.students.customview;

import com.ekwing.students.utils.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SideBar extends View {
	private String[] letter;
	private SectionIndexer sectionIndexter = null;
	private ListView list;
	private TextView mDialogText;
//	private int width = 0;#3ebde0
	private int hight = 0;
	private int mScreenWidth;
	private int mScreenHeight;
    private static  int TEXT_COLOR = Color.parseColor("#000000");
	public SideBar(Context context){
		super(context);
		init();
	}

	public SideBar(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	private void init() {
		//去掉了 :I/O/U/V
		letter = new String[] {"A", "B", "C", "D", "E", "F", "G", "H","I", "J", "K", "L", "M", "N",
				"O","P", "Q", "R", "S", "T", "U","V","W", "X", "Y", "Z"};
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}

	public void setListView(ListView _list) {
		list = _list;
		sectionIndexter = (SectionIndexer) _list.getAdapter();
	}

	public void setTextView(TextView mDialogText) {
		this.mDialogText = mDialogText;
	}

	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		int i = (int) event.getY();
		if (hight <= 0){
			hight = getHeight();
		}
		int idx = i / (hight / 26);
		if (idx >= letter.length){
			idx = letter.length - 1;
		} else if (idx < 0){
			idx = 0;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
			mDialogText.setVisibility(View.VISIBLE);
			mDialogText.setText("" + letter[idx]);
			if (sectionIndexter == null){
				sectionIndexter = (SectionIndexer) list.getAdapter();
			}
			int position = sectionIndexter.getPositionForSection(letter[idx].charAt(0));
			if (position == -1){
				return true;
			}
			list.setSelection(position);
		} else{
			mDialogText.setVisibility(View.INVISIBLE);
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		if(this.mScreenWidth > 720 && this.mScreenHeight > 1280){
			paint.setTextSize(35.0f);
		}else if(this.mScreenWidth > 640 && this.mScreenHeight > 960){
			paint.setTextSize(25.0f);
		}else if(this.mScreenWidth > 320 && this.mScreenWidth <= 640 && this.mScreenHeight <= 960 && this.mScreenHeight > 480){
			paint.setTextSize(15.0f);
		}else{
			paint.setTextSize(20.0f);
		}
		paint.setTextSize(Utils.dp2px(getContext(), 14));
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setColor(TEXT_COLOR);
		paint.setTypeface(Typeface.DEFAULT);
		paint.setAntiAlias(true);
		hight = getHeight();
		// float widthCenter = getMeasuredWidth() / 2;
		for (int i = 0; i < letter.length; i++){
			float xPos = getWidth() / 2 /*- paint.measureText(String.valueOf(l[i])) / 2*/;
			float yPos = (getHeight() / letter.length) * i + (getHeight() / letter.length);
			canvas.drawText(String.valueOf(letter[i]), xPos, yPos, paint);
			// canvas.drawText(String.valueOf(l[i]), widthCenter, m_nItemHeight
			// + (i * m_nItemHeight), paint);
		}
		super.onDraw(canvas);
	}

	public void setScreen(int width, int height) {
		this.mScreenWidth = width;
		this.mScreenHeight = height;
	}
}
