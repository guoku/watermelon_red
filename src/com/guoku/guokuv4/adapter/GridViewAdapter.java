/**

 */
package com.guoku.guokuv4.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-24 下午2:48:28 3 列的瀑布流
 */
public class GridViewAdapter extends ArrayListAdapter<EntityBean> {

	int count;
	Context mContext;
	
	public GridViewAdapter(Context context, int counts) {
		super(context);
		// TODO Auto-generated constructor stub
		this.count = counts;
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHold holder = null;
		if (convertView == null) {
			if(count == 2){
				convertView = View.inflate(mContext, R.layout.grid2_view_img_item,
						null);
			}else{
				convertView = View.inflate(mContext, R.layout.grid_view_img_item,
						null);
			}
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
//		params.setMargins(0, 10, 0, 0);
		holder.imgIcon.setLayoutParams(params);
		
		if(count == 2){
//			if(StringUtils.isEmpty(mList.get(position).getBrand())){
//				holder.title.setVisibility(View.GONE);
//			}else{
//				holder.title.setVisibility(View.VISIBLE);
//				holder.title.setText(mList.get(position).getBrand());
//			}
			holder.title.setText(mList.get(position).getBrand());
			holder.context.setText(mList.get(position).getTitle());
			holder.money.setText(mContext.getResources().getString(R.string.tv_rmb, mList.get(position).getPrice()));
		}

		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.img)
		SimpleDraweeView imgIcon;
		@ViewInject(R.id.title)
		TextView title;
		@ViewInject(R.id.context)
		TextView context;
		@ViewInject(R.id.money)
		TextView money;
	}
}
