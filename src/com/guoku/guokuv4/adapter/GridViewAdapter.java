/**

 */
package com.guoku.guokuv4.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-24 下午2:48:28 3 列的瀑布流
 */
public class GridViewAdapter extends ArrayListAdapter<EntityBean> {

	int count;
	
	public GridViewAdapter(Context context, int counts) {
		super(context);
		// TODO Auto-generated constructor stub
		this.count = counts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.grid_view_img_item,
					null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}

		holder.imgIcon.setImageURI(Uri.parse(mList.get(position).get240()));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				GuokuApplication.screenW / count - 10,
				GuokuApplication.screenW / count - 10);
		holder.imgIcon.setLayoutParams(params);

		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.img)
		SimpleDraweeView imgIcon;
	}
}
