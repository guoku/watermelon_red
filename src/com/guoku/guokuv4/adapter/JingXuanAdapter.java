package com.guoku.guokuv4.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ekwing.students.EkwingApplication;
import com.ekwing.students.utils.ArrayListAdapter;
import com.ekwing.students.utils.DateUtils;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.PBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class JingXuanAdapter extends ArrayListAdapter<PBean> {
	private ImageLoader loader = ImageLoader.getInstance();
	private int w = EkwingApplication.screenW * 9 / 10;
	private OnClickListener listener;
	private DisplayImageOptions options;

	public JingXuanAdapter(Context context, OnClickListener listener) {
		super(context);
		this.listener = listener;
		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.drawable.item800)
				.showImageForEmptyUri(R.drawable.item800).cacheInMemory(true)
				.showImageOnFail(R.drawable.item800).build();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.jingxuan_item, null);
			holder = new ViewHolder();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PBean bean = mList.get(position);

		LayoutParams params = (LayoutParams) holder.iv_img.getLayoutParams();
		params.height = w;
		params.width = w;
		loader.displayImage(bean.getContent().getEntity().get800(),
				holder.iv_img, options);
		// loader.displayImage(bean.getContent().getNote().getCreator()
		// .getAvatar_small(), holder.iv_last, optionsRound);
		// BitmapUtil.setRoundImage(loader, bean.getContent().getNote()
		// .getCreator().getAvatar_small(), options, holder.iv_last);

		holder.ll_likes.setTag(bean);
		holder.ll_likes.setOnClickListener(listener);

		if ("1".equals(bean.getContent().getEntity().getLike_already())) {
			holder.iv_isLike.setImageResource(R.drawable.like_red);
		} else {
			holder.iv_isLike.setImageResource(R.drawable.like_gary);
		}
		holder.tv_context.setText(bean.getContent().getNote().getContent());
		if (!bean.getContent().getEntity().getLike_count().equals("")) {
			holder.tv_likes.setVisibility(View.VISIBLE);
			holder.tv_likes.setText(""
					+ bean.getContent().getEntity().getLike_count());
		} else {
			holder.tv_likes.setVisibility(View.GONE);
		}
		holder.tv_time.setText(DateUtils.getStandardDate(bean.getPost_time()));
		return convertView;
	}

	public void setUserBeans(ArrayList<PBean> beans) {
		setList(beans);
	}

	private class ViewHolder {

		@ViewInject(R.id.jingxuan_item_iv_img)
		private ImageView iv_img;
		@ViewInject(R.id.jingxuan_item_iv_like)
		private ImageView iv_isLike;
		@ViewInject(R.id.jingxuan_item_iv_last)
		private ImageView iv_last;
		@ViewInject(R.id.jingxuan_item_tv_context)
		private TextView tv_context;
		@ViewInject(R.id.jingxuan_item_tv_time)
		private TextView tv_time;
		@ViewInject(R.id.jingxuan_item_tv_likes)
		private TextView tv_likes;
		@ViewInject(R.id.jingxuan_item_ll_like)
		private LinearLayout ll_likes;
	}

}
