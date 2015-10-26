package com.guoku.guokuv4.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.act.TabAct;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class TABListAdapter extends ArrayListAdapter<Tab2Bean> {
	ImageLoader loader = ImageLoader.getInstance();

	private DisplayImageOptions options;

	public TABListAdapter(Context context) {
		super(context);
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.color.g_g)
				.showImageForEmptyUri(R.color.g_g).cacheOnDisk(true)
				.showImageOnFail(R.color.g_g).build();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GVViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.faxian_lv_gv_item,
					null);
			holder = new GVViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (GVViewHold) convertView.getTag();
		}

		final Tab2Bean bean = mList.get(position);
		loader.displayImage(bean.getCategory_icon_small(),
				holder.faxian_lv_gv_item_iv_img, options);
		holder.faxian_lv_gv_item_tv_name.setText(bean.getCategory_title());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, TabAct.class);
				intent.putExtra("data", bean.getCategory_id());
				intent.putExtra("name", bean.getCategory_title());
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	private class GVViewHold {
		@ViewInject(R.id.faxian_lv_gv_item_tv_name)
		TextView faxian_lv_gv_item_tv_name;

		@ViewInject(R.id.faxian_lv_gv_item_iv_img)
		ImageView faxian_lv_gv_item_iv_img;

	}

}
