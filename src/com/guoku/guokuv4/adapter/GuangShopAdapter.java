/**

 */
package com.guoku.guokuv4.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.utils.ArrayListAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.bean.Discover;
import com.guoku.guokuv4.utils.LogGK;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-13 下午1:52:20 
 * 发现页热门商品
 */
public class GuangShopAdapter extends ArrayListAdapter<Discover.EntitiesEntity> {

	public GuangShopAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHold holder = null;
		if (convertView == null) {
			try {
				convertView = View.inflate(mContext, R.layout.grid_view_img_item,
						null);
				holder = new ViewHold();
				ViewUtils.inject(holder, convertView);
				convertView.setTag(holder);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			holder = (ViewHold) convertView.getTag();
		}

		LogGK.d("****" + position);
		
		if(!StringUtils.isEmpty(mList.get(position).getEntity().getChief_image())){
			holder.imgIcon.setImageURI(Uri.parse(mList.get(position).getEntity().getChief_image()));
		}

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				EkwingApplication.screenW / 3 - 10,
				EkwingApplication.screenW / 3 - 10);
		holder.imgIcon.setLayoutParams(params);

		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.img)
		SimpleDraweeView imgIcon;
	}
}
