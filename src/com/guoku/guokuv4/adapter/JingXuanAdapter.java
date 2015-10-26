package com.guoku.guokuv4.adapter;

import java.util.ArrayList;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.entity.test.PBean;
import com.guoku.guokuv4.utils.DateUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class JingXuanAdapter extends ArrayListAdapter<PBean> {
	private int w = GuokuApplication.screenW * 9 / 10;
	private OnClickListener listener;

	public JingXuanAdapter(Context context, OnClickListener listener) {
		super(context);
		this.listener = listener;
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

		holder.iv_img.setImageURI(Uri.parse(bean.getContent().getEntity()
				.get800()));
		holder.iv_img.setLayoutParams(params);

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
			holder.tv_likes.setText(bean.getContent().getEntity().getLike_count());
		} else {
			holder.tv_likes.setVisibility(View.GONE);
		}
		holder.tv_time.setText(DateUtils.getStandardDate(bean.getPost_time()));
		return convertView;
	}

	public void setUserBeans(ArrayList<PBean> beans) {
		setList(beans);
	}

	public void setStatus(View view, PBean bean) {
		ImageView iv_isLike = (ImageView) view
				.findViewById(R.id.jingxuan_item_iv_like);
		TextView tv_like_count = (TextView) view
				.findViewById(R.id.jingxuan_item_tv_likes);
		if ("1".equals(bean.getContent().getEntity().getLike_already())) {
			iv_isLike.setImageResource(R.drawable.like_red);
		} else {
			iv_isLike.setImageResource(R.drawable.like_gary);
		}
		tv_like_count.setText(bean.getContent().getEntity().getLike_count());
		if (!bean.getContent().getEntity().getLike_count().equals("")) {
			tv_like_count.setVisibility(View.VISIBLE);
			tv_like_count.setText(""
					+ bean.getContent().getEntity().getLike_count());
		} else {
			tv_like_count.setVisibility(View.GONE);
		}
	}

	private class ViewHolder {

		@ViewInject(R.id.jingxuan_item_iv_img)
		private SimpleDraweeView iv_img;
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
