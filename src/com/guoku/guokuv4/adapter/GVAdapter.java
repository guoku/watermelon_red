package com.guoku.guokuv4.adapter;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.utils.ArrayListAdapter;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class GVAdapter extends ArrayListAdapter<EntityBean> {
	private ImageLoader loader = ImageLoader.getInstance();
	private DisplayImageOptions options;

	public GVAdapter(Context context) {
		super(context);
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.color.g_w)
				.showImageForEmptyUri(R.color.g_w).showImageOnFail(R.color.g_w)
				.build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ImageView(mContext);
			LayoutParams params = new LayoutParams(
					EkwingApplication.screenW / 3 - 10,
					EkwingApplication.screenW / 3 - 10);
			convertView.setLayoutParams(params);
			((ImageView) convertView).setScaleType(ScaleType.FIT_XY);
			convertView.setBackgroundColor(Color.WHITE);
		}
		loader.displayImage(mList.get(position).get240(),
				(ImageView) convertView, options);
		return convertView;
	}

}
