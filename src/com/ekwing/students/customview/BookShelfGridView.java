//package com.ekwing.students.customview;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.widget.GridView;
//
//import com.ekwing.students.config.Logger;
//import com.guoku.R;
//
//public class BookShelfGridView extends GridView {
//	private  int DBH ;
//
//	private Bitmap bmp_center;
//
//	public void setHifth(int h) {
//		Logger.e(VIEW_LOG_TAG, "hhhhhhhhhhhhhhhhhhhhhhhh------------------>"+h);
//		DBH = h;
//	}
//
//	public BookShelfGridView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		bmp_center = BitmapFactory.decodeResource(getResources(), R.drawable.sheft_grid);
//	}
//
//	@Override
//	protected void dispatchDraw(Canvas canvas) {
//		Logger.e(VIEW_LOG_TAG, "D------------------>"+DBH);
//		
//		int count = getChildCount();
//		int top = count > 0 ? getChildAt(0).getTop() : 0;
//		int center_height = bmp_center.getHeight();
//		int width = getWidth();
//		int y = top + DBH;
//		int size = count % 3 == 0 ? count / 3 : count / 3 + 1;
//		for (int i = 0; i < size; i++) {
//			Rect rect_center = new Rect(0, y, width - 0, y + center_height);
//			canvas.drawBitmap(bmp_center, null, rect_center, null);
//			if(i%2 == 0){
//				y += center_height + DBH+8;
//			}else{
//				y += center_height + DBH-12;
//			}
//		}
//
//		super.dispatchDraw(canvas);
//	}
//
//	/**
//	 * 获取书架的高度
//	 * */
//	public int getCenterHeight() {
//		return bmp_center.getHeight();
//	}
// }