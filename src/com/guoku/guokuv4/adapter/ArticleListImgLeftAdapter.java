/**

 */
package com.guoku.guokuv4.adapter;

import com.guoku.R;
import com.guoku.guokuv4.entity.test.TabNoteBean;
import com.guoku.guokuv4.utils.DateUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-23 下午4:20:42 
 * 图片在左边的图文adapter
 */
public class ArticleListImgLeftAdapter extends ArrayListAdapter<TabNoteBean>{
	
	Context context;

	public ArticleListImgLeftAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.list_img_left_adapter_item,
					null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}
		TabNoteBean bean = mList.get(position);
		holder.person_item_iv_pic.setImageURI(Uri.parse(bean.getEntity().get240()));
		holder.person_item_tv_context.setText(bean.getNote()
				.getContent());
		holder.person_item_tv_time.setText(DateUtils
				.getStandardDate(bean.getNote().getUpdated_time()));

		return convertView;
	}
	
	private class ViewHold {
		@ViewInject(R.id.person_item_iv_pic)
		ImageView person_item_iv_pic;

		@ViewInject(R.id.person_item_tv_context)
		TextView person_item_tv_context;

		@ViewInject(R.id.person_item_tv_time)
		TextView person_item_tv_time;

	}
}
