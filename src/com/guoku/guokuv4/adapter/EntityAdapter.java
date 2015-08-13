package com.guoku.guokuv4.adapter;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.utils.ArrayListAdapter;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.utils.ImgUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class EntityAdapter extends ArrayListAdapter<EntityBean> {
	private ImageLoader loader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private int w = EkwingApplication.screenW / 4;

	public EntityAdapter(Context context) {
		super(context);
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.color.g_w)
				.showImageForEmptyUri(R.color.g_w).showImageOnFail(R.color.g_w)
				.cacheInMemory(true)
				.build();
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
		loader.displayImage(bean.get240(), holder.entity_item_iv_pic, options,
				new ImgUtils.AnimateFirstDisplayListener());
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
		ImageView entity_item_iv_pic;

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
