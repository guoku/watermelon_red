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
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class EntityAdapter extends ArrayListAdapter<EntityBean> {
	private int w = GuokuApplication.screenW / 4;

	public EntityAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.entity_item, null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}
		EntityBean bean = (EntityBean) mList.get(position);
		holder.entity_item_iv_pic.setImageURI(Uri.parse(bean.get240()));
		LayoutParams params = new LayoutParams(w, w);
		holder.entity_item_iv_pic.setLayoutParams(params);
		
		if (bean.getLike_already().equals("0")) {
			holder.entity_item_iv_islike.setImageResource(R.drawable.icon_like);
		} else {
			holder.entity_item_iv_islike
					.setImageResource(R.drawable.icon_like_press);
		}
		holder.entity_item_tv_com.setText(bean.getNote_count());
		holder.entity_item_tv_like.setText(bean.getLike_count());
		holder.entity_item_tv_name.setText(bean.getTitle());
		holder.entity_item_tv_price.setText("￥" + bean.getPrice());
		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.entity_item_iv_islike)
		ImageView entity_item_iv_islike;
		@ViewInject(R.id.entity_item_iv_pic)
		SimpleDraweeView entity_item_iv_pic;
		@ViewInject(R.id.entity_item_tv_com)
		TextView entity_item_tv_com;
		@ViewInject(R.id.entity_item_tv_like)
		TextView entity_item_tv_like;
		@ViewInject(R.id.entity_item_tv_name)
		TextView entity_item_tv_name;
		@ViewInject(R.id.entity_item_tv_price)
		TextView entity_item_tv_price;
	}

}
