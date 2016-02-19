/**

 */
package com.guoku.guokuv4.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.bean.AuthorizeduserBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年2月19日 下午4:34:42 推荐用户adapter
 */
public class RecommendUserAdapter extends ArrayListAdapter<AuthorizeduserBean> {

	public RecommendUserAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_recommend_user, null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}
		AuthorizeduserBean bean = (AuthorizeduserBean) mList.get(position);
		holder.img.setImageURI(Uri.parse(bean.getUser().get50()));
//		holder.img.setLayoutParams(new LayoutParams(20, 20));

		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.psrson_iv_pic)
		SimpleDraweeView img;
	}

}
