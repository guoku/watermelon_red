/**

 */
package com.guoku.guokuv4.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekwing.students.utils.ArrayListAdapter;
import com.guoku.R;
import com.guoku.guokuv4.bean.TagTwo;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-18 上午10:06:37 
 * 二级品类
 */
public class CategoryListAdapter extends ArrayListAdapter<TagTwo> {

	private DisplayImageOptions options;

	public CategoryListAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GVViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.category_list_adapter_item,
					null);
			holder = new GVViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (GVViewHold) convertView.getTag();
		}

		final TagTwo bean = mList.get(position);
		holder.text.setText(bean.getCategory_title());
		return convertView;
	}

	private class GVViewHold {
		@ViewInject(R.id.text)
		TextView text;

	}
}
