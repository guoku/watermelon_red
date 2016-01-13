/**

 */
package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import de.greenrobot.event.EventBus;

import com.guoku.R;
import com.guoku.guokuv4.base.BasePageFragment;
import com.guoku.guokuv4.bean.LikesBean;
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
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		EventBus.getDefault().register(this);
	}

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
	
	public void onEventMainThread(LikesBean likesBean) {
		if(likesBean.isLike()){
			String count = goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity().getLike_countAdd();
			goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity().setLike_already("1");
			goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity()
					.setLike_count(count);
			goodTwoFragmnet.adapter.setStatus(goodTwoFragmnet.layoutView,
					goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos));
		}else{
			String count2 = goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity().getLike_countCut();
			goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity().setLike_already("0");
			goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos).getContent().getEntity()
					.setLike_count(count2);
			goodTwoFragmnet.adapter.setStatus(goodTwoFragmnet.layoutView,
					goodTwoFragmnet.adapter.getItem(goodTwoFragmnet.pos));
		}
	}
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

}
