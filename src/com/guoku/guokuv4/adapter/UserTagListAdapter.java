/**

 */
package com.guoku.guokuv4.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekwing.students.utils.ArrayListAdapter;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.UserTagBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-23 下午5:06:39 
 */
public class UserTagListAdapter extends ArrayListAdapter<UserTagBean>{
	
	Context context;

	public UserTagListAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TBViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.person_item3,
					null);
			holder = new TBViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (TBViewHold) convertView.getTag();
		}
		UserTagBean bean = mList.get(position);
		// imageLoader.displayImage(bean.getImg(),
		// holder.person_item_iv_pic);
		holder.person_item_tv_context.setText("#" + bean.getTag());
		holder.person_item_tv_time.setText(bean.getEntity_count()
				+ "件商品");
		return convertView;
	}
	
	private class TBViewHold {
		@ViewInject(R.id.person_item3_iv_pic)
		ImageView person_item_iv_pic;

		@ViewInject(R.id.person_item3_tv_context)
		TextView person_item_tv_context;

		@ViewInject(R.id.person_item3_tv_time)
		TextView person_item_tv_time;

	}
}
