package com.ekwing.students.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class CustomGirdView extends GridView {
	public CustomGirdView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 
    public CustomGirdView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 
    public CustomGirdView(Context context) { 
        super(context); 
    } 
    @Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec(  
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    } 
}
