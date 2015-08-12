/**
 * 
 */
package com.guoku.guokuv4.view;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.guoku.R;


/**
 * @zhangyao
 * @Description: 带清空按钮的view
 * @date 2015-8-12 下午5:38:23 
 */
public class LayoutItemEdit extends RelativeLayout{
	
	public TextView tv1;
	public EditTextWithDel edDel;
	ImageView imageVie;

	public LayoutItemEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_item_edit, this);
        init();
	}
	
	private void init(){
		
		tv1 = (TextView) findViewById(R.id.textView1);
		edDel = (EditTextWithDel) findViewById(R.id.editView2);
    	imageVie = (ImageView) findViewById(R.id.imageView1);
	}
	
	public void hideDelBt(){
		edDel.isShowDel = false;
	}
	
	public void showDelBt(){
		edDel.isShowDel = true;
	}
	
	public void inputStylePHONE(){
		edDel.setInputType(InputType.TYPE_CLASS_PHONE);
	}
	
	public void inputStyleNum(){
		edDel.setInputType(InputType.TYPE_CLASS_NUMBER);
	}
	
	public void inputStyleEmail(){
		edDel.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
	}
}
