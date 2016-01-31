/**

 */
package com.guoku.guokuv4.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.act.SecondCategoryAct;
import com.guoku.guokuv4.bean.CategoryBean;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-8-11 下午9:30:13
 */
public class SeachCommodityTypeAdapter extends ArrayListAdapter<CategoryBean.ContentEntity> {
	
	public final static String SEACH_TAG = "SEACH_TAG";


	public SeachCommodityTypeAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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

		final CategoryBean.ContentEntity bean = mList.get(position);
		if(StringUtils.isEmpty(bean.getCategory_icon_small())){
			holder.faxian_lv_gv_item_iv_img.setVisibility(View.INVISIBLE);
		}else{
			holder.faxian_lv_gv_item_iv_img.setVisibility(View.VISIBLE);
			holder.faxian_lv_gv_item_iv_img.setImageURI(Uri.parse(bean.getCategory_icon_small()));
		}
		holder.faxian_lv_gv_item_tv_name.setText(bean.getCategory_title());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mContext, SecondCategoryAct.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(SEACH_TAG, bean);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	private class GVViewHold {
		@ViewInject(R.id.faxian_lv_gv_item_tv_name)
		TextView faxian_lv_gv_item_tv_name;

		@ViewInject(R.id.faxian_lv_gv_item_iv_img)
		SimpleDraweeView faxian_lv_gv_item_iv_img;

	}

}
