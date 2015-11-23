/**

 */
package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.base.BasePageFragment;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.homepage.ArticleFragment;
import com.guoku.guokuv4.homepage.GoodTwoFragmnet;
import com.guoku.guokuv4.utils.BroadUtil;
import com.guoku.guokuv4.utils.StringUtils;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-9 下午4:03:08 4.1精选首页图文流
 */
public class JingXuanPageFragment extends BasePageFragment {

	public GoodTwoFragmnet goodTwoFragmnet = new GoodTwoFragmnet();
	public ArticleFragment articleFragment = new ArticleFragment();

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
		list.add(goodTwoFragmnet);
		list.add(articleFragment);
		return list;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (data != null) {
			if (resultCode == goodTwoFragmnet.UPDATA_LIKE) {
				boolean isLike = data.getBooleanExtra(goodTwoFragmnet.INTNT_KEY, false);
				if (isLike) {
					int count = 0;
					if (StringUtils.isEmpty(goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent()
							.getEntity().getLike_count())) {
						count++;
					} else {
						count = Integer.valueOf(goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent()
								.getEntity().getLike_count());
						count++;
					}
					goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity().setLike_already("1");
					goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity()
							.setLike_count(String.valueOf(count));
					goodTwoFragmnet.adapter.setStatus(goodTwoFragmnet.layoutView,
							goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos));

//					BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY, Constant.INTENT_ACTION_VALUE_LIKE);
				} else {
					int count2 = 0;
					if (!StringUtils.isEmpty(goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent()
							.getEntity().getLike_count())) {
						count2 = Integer.valueOf(goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent()
								.getEntity().getLike_count());
						if (count2 > 0) {
							count2--;
						} else {
							count2 = 0;
						}
					}
					goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity().setLike_already("0");
					goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity()
							.setLike_count(String.valueOf(count2));
					goodTwoFragmnet.adapter.setStatus(goodTwoFragmnet.layoutView,
							goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos));

//					BroadUtil.setBroadcastInt(context, Constant.INTENT_ACTION_KEY, Constant.INTENT_ACTION_VALUE_LIKE);
				}
			}
		}
	}

}
