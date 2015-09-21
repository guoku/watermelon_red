/**

 */
package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.base.BasePageFragment;
import com.guoku.guokuv4.homepage.ArticleFragment;
import com.guoku.guokuv4.homepage.GoodTwoFragmnet;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-9 下午4:03:08 4.1精选首页图文流
 */
public class JingXuanPageFragment extends BasePageFragment {

	int count = 2;

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
			// case 0:
			// textView.setText(R.string.tv_tab_recommend);
			// break;
			// case 1:
			// textView.setText(R.string.tv_tab_commodity);
			// break;
			// case 2:
			// textView.setText(R.string.tv_tab_image_text);
			// break;
			// }
			case 0:
				textView.setText(R.string.tv_tab_commodity);
				break;
			case 1:
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
		return list;
	}

}
