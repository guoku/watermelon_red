package com.guoku.guokuv4.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekwing.students.utils.ArrayListAdapter;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class TabAdapter extends ArrayListAdapter<Tab2Bean> {

	public TabAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TBViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.person_item3, null);
			holder = new TBViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (TBViewHold) convertView.getTag();
		}
		Tab2Bean bean = mList.get(position);
		holder.person_item_tv_context.setText("#" + bean.getCategory_title());
		// holder.person_item_tv_time.setText(bean.get);
		return convertView;
	}

	public void setTabList(ArrayList<Tab2Bean> list) {
		setList(list);
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
