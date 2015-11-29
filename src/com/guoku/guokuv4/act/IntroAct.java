/**

 */
package com.guoku.guokuv4.act;

import java.io.File;

import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.base.BaseActivity;
import com.guoku.guokuv4.bean.LaunchBean;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月26日 下午2:35:57 引导页
 */
public class IntroAct extends BaseActivity {

	@ViewInject(R.id.text1)
	TextView tvTitle;//标题
	
	@ViewInject(R.id.text2)
	TextView tvContext;//说明内容
	
	@ViewInject(R.id.img_launch)
	ImageView simpleDraweeView;
	
	@ViewInject(R.id.textView1)
	TextView tvButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(0, 0);
		
		setContentView(R.layout.activity_intro);
		
		this.setFinishOnTouchOutside(false);
		
		initLaunch();
	}
	
	private void initLaunch(){
		String path = Constant.LAUNCH_PATH + StringUtils.setReplace(GuokuApplication.getInstance().getLaunchBean().getLaunch_image_url());
		File file = new File(path);
		if (file.exists()) {
			simpleDraweeView.setImageURI(Uri.parse("file://" + path));
		}
		
		LaunchBean lBean = GuokuApplication.getInstance().getLaunchBean();
		
		tvTitle.setText(lBean.getTitle());
		
		tvContext.setText(lBean.getDescription());
		
		tvButton.setText(lBean.getAction_title());
		
	}
	
	@OnClick(R.id.textView1)
	private void onViewClick(View view){
		finishAct();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void finishAct() {
		finish();
		overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
	}
}
