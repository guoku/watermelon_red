package com.ekwing.students.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ekwing.students.entity.CircleItemBean;
import com.guoku.R;

public class CircleChildrenAdapter extends BaseAdapter {
	private Context context;
	private List<CircleItemBean> list;

	public CircleChildrenAdapter(Context context, List<CircleItemBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChildrenViewHolder mHolder = null;
		if (convertView == null) {
			mHolder = new ChildrenViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.child_item, null);
			mHolder.tv_child = (TextView) convertView
					.findViewById(R.id.tv_circle_select);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ChildrenViewHolder) convertView.getTag();
		}
		mHolder.tv_child.setText(list.get(position).getName());
		return convertView;
	}

	static class ChildrenViewHolder {
		public TextView tv_child;
	}
}
