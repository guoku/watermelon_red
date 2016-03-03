package com.guoku.guokuv4.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.utils.ImgUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FansAdapter extends ArrayListAdapter<UserBean> {
	private OnClickListener listener;

	public FansAdapter(Context context, OnClickListener listener) {
		super(context);
		this.listener = listener;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.fans_item, null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}
		UserBean bean = (UserBean) mList.get(position);
		holder.fans_item_iv_pic.setImageURI(Uri.parse(bean.get50()));
		holder.fans_item_tv_fans.setText("关注  " + bean.getFollowing_count()
				+ "    粉丝 " + bean.getFan_count());
		holder.fans_item_tv_name.setText(bean.getNickname());
		if (bean.getRelation().equals("0")) {
			holder.fans_item_iv_status.setImageResource(R.drawable.add);
			holder.fans_item_iv_status
					.setBackgroundResource(R.drawable.blue_shap);
		} else if (bean.getRelation().equals("2")) {
			holder.fans_item_iv_status.setImageResource(R.drawable.add);
			holder.fans_item_iv_status
					.setBackgroundResource(R.drawable.blue_shap);
		} else if (bean.getRelation().equals("1")) {
			holder.fans_item_iv_status.setImageResource(R.drawable.has);
			holder.fans_item_iv_status
					.setBackgroundResource(R.drawable.ttz_shap);
		} else if (bean.getRelation().equals("3")) {
			holder.fans_item_iv_status.setImageResource(R.drawable.double1);
			holder.fans_item_iv_status
					.setBackgroundResource(R.drawable.ttz_shap);
		}
		if(bean.isAuthorized_author()){
			holder.imgAutuon.setVisibility(View.VISIBLE);
		}else{
			holder.imgAutuon.setVisibility(View.GONE);
		}
		holder.fans_item_iv_status.setTag(bean);
		holder.fans_item_iv_status.setOnClickListener(listener);
		return convertView;
	}

	public void setUserBeans(ArrayList<UserBean> beans) {
		setList(beans);
	}

	public void updateView(PullToRefreshListView lv, int itemIndex) {
		// 得到第一个可显示控件的位置，
		int visiblePosition = lv.getFirstVisiblePosition();
		// 只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
		if (itemIndex - visiblePosition >= 0) {

			View view = lv.getChildAt(itemIndex - visiblePosition);
			ViewHold holder = (ViewHold) view.getTag();

			UserBean bean = (UserBean) mList.get(itemIndex);
			holder.fans_item_iv_pic.setImageURI(Uri.parse(bean.get50()));
			// BitmapUtil.setRoundImage(loader, bean.getAvatar_small(), options,
			// holder.fans_item_iv_pic);
			holder.fans_item_tv_fans.setText("关注  " + bean.getFollowing_count() + "    粉丝 " + bean.getFan_count());
			holder.fans_item_tv_name.setText(bean.getNickname());
			if (bean.getRelation().equals("0")) {
				holder.fans_item_iv_status.setImageResource(R.drawable.add);
				holder.fans_item_iv_status.setBackgroundResource(R.drawable.blue_shap);
			} else if (bean.getRelation().equals("2")) {
				holder.fans_item_iv_status.setImageResource(R.drawable.add);
				holder.fans_item_iv_status.setBackgroundResource(R.drawable.blue_shap);
			} else if (bean.getRelation().equals("1")) {
				holder.fans_item_iv_status.setImageResource(R.drawable.has);
				holder.fans_item_iv_status.setBackgroundResource(R.drawable.ttz_shap);
			} else if (bean.getRelation().equals("3")) {
				holder.fans_item_iv_status.setImageResource(R.drawable.double1);
				holder.fans_item_iv_status.setBackgroundResource(R.drawable.ttz_shap);
			}
			if(bean.isAuthorized_author()){
				holder.imgAutuon.setVisibility(View.VISIBLE);
			}else{
				holder.imgAutuon.setVisibility(View.GONE);
			}
			holder.fans_item_iv_status.setTag(bean);
			holder.fans_item_iv_status.setOnClickListener(listener);

		}
	}

	public void setStatus(ImageView view, UserBean bean) {
		if (bean.getRelation().equals("0")) {
			view.setImageResource(R.drawable.add);
			view.setBackgroundResource(R.drawable.blue_shap);
		} else if (bean.getRelation().equals("2")) {
			view.setImageResource(R.drawable.add);
			view.setBackgroundResource(R.drawable.blue_shap);
		} else if (bean.getRelation().equals("1")) {
			view.setImageResource(R.drawable.has);
			view.setBackgroundResource(R.drawable.ttz_shap);
		} else if (bean.getRelation().equals("3")) {
			view.setImageResource(R.drawable.double1);
			view.setBackgroundResource(R.drawable.ttz_shap);
		}
	}

	class ViewHold {
		@ViewInject(R.id.fans_item_iv_pic)
		SimpleDraweeView fans_item_iv_pic;
		@ViewInject(R.id.fans_item_iv_status)
		ImageView fans_item_iv_status;
		@ViewInject(R.id.fans_item_tv_fans)
		TextView fans_item_tv_fans;
		@ViewInject(R.id.fans_item_tv_name)
		TextView fans_item_tv_name;
		@ViewInject(R.id.img_authon)
		ImageView imgAutuon;
	}

}
