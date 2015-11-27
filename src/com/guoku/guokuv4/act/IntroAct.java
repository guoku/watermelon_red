/**

 */
package com.guoku.guokuv4.act;

import java.io.File;

import com.guoku.R;
import com.guoku.guokuv4.base.BaseActivity;
import com.guoku.guokuv4.config.Constant;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月26日 下午2:35:57 引导页
 */
public class IntroAct extends BaseActivity {

	@ViewInject(R.id.article_img)
	ImageView simpleDraweeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(0, 0);
		
		setContentView(R.layout.activity_intro);
		String path = Constant.LAUNCH_PATH + Constant.LAUNCH_NAME;
		File file = new File(path);
		if (file.exists()) {
			Bitmap bm = BitmapFactory.decodeFile(path);
			// 将图片显示到ImageView中
			simpleDraweeView.setImageBitmap(bm);
		}
	}
}
