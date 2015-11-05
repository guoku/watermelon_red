/**

 */
package com.guoku.guokuv4.act.seach;

import java.util.ArrayList;

import com.guoku.R;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.base.BasePageFragment;
import com.guoku.guokuv4.homepage.ArticleFragment;
import com.guoku.guokuv4.homepage.GoodTwoFragmnet;

import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月5日 下午1:47:11 
 * 搜索Fragment
 */
public class BaseSeachPageFragment extends BasePageFragment{
	
	int count = 4;

	@Override
	public int tabCount() {
		// TODO Auto-generated method stub
		return count;
	}

	@Override
	public ArrayList<TextView> initTitleList() {
		// TODO Auto-generated method stub
		ArrayList<TextView> list = new ArrayList<TextView>();
		for (int i = 0; i < count; i++) {
			TextView textView = new TextView(getActivity());
			switch (i) {
			case 0:
				textView.setText(R.string.tv_tab_commodity);
				break;
			case 1:
				textView.setText(R.string.tv_tab_commodity);
				break;
			case 2:
				textView.setText(R.string.tv_tab_commodity);
				break;
			case 3:
				textView.setText(R.string.tv_tab_image_text);
				break;
			}
			textView.setTag(i);
			list.add(textView);
		}
		return list;
	}

	@Override
	public ArrayList<Fragment> initFragmentList() {
		// TODO Auto-generated method stub
		ArrayList<Fragment> list = new ArrayList<Fragment>();
		// list.add(new HomeOneFragment());
		list.add(new GoodTwoFragmnet());
		list.add(new ArticleFragment());
		list.add(new GoodTwoFragmnet());
		list.add(new ArticleFragment());
		return list;
	}

}
