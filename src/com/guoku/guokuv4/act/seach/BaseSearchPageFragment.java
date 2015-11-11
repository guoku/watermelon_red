/**

 */
package com.guoku.guokuv4.act.seach;

import java.util.ArrayList;

import com.guoku.R;
import com.guoku.guokuv4.act.seach.SearchInterface.OnActivityChangeListener;
import com.guoku.guokuv4.act.seach.SearchInterface.OnFragmentChangeListener;
import com.guoku.guokuv4.base.BasePageFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月5日 下午1:47:11 搜索Fragment
 */
public class BaseSearchPageFragment extends BasePageFragment implements OnFragmentChangeListener {

	public static OnActivityChangeListener onActivityChangeListener;

	public void setOnActivityChangeListener(OnActivityChangeListener onActivityChangeListener) {
		BaseSearchPageFragment.onActivityChangeListener = onActivityChangeListener;
	}

	int count = 4;

	SearchGoodFragment searchGoodFragment;
	SearchTagFragment searchTagFragment;
	SearchUserFragment searchUserFragment;
	SearchArticleFragment searchArticleFragment;

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
				textView.setText(R.string.tv_commodity);
				break;
			case 1:
				textView.setText(R.string.tv_article);
				break;
			case 2:
				textView.setText(R.string.tv_commodity_type);
				break;
			case 3:
				textView.setText(R.string.tv_user);
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
		searchGoodFragment = new SearchGoodFragment();
		searchTagFragment = new SearchTagFragment();
		searchUserFragment = new SearchUserFragment();
		searchArticleFragment = new SearchArticleFragment();
		list.add(searchGoodFragment);
		list.add(searchArticleFragment);
		list.add(searchTagFragment);
		list.add(searchUserFragment);
		return list;
	}

	@Override
	public void onFragmentChange(String str) {
		// TODO Auto-generated method stub
		switch (currIndex) {
		case 0:
			searchGoodFragment.getData(str, false, 0);
			break;
		case 1:

			break;
		case 2:
			searchTagFragment.getData();
			break;
		case 3:
			searchUserFragment.getData(str, false, 0);
			break;

		default:
			break;
		}
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onActivityChangeListener = (OnActivityChangeListener) activity;
	}

}
