/**

 */
package com.guoku.guokuv4.adapter;

import com.guoku.R;
import com.guoku.guokuv4.bean.SearchLogBean;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月6日 下午2:42:34 
 * 
 * 搜索历史记录
 */
public class SearchLogAdapter extends ArrayListAdapter<SearchLogBean> {
	
	
	public SearchLogAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_tag_text,
					null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}

		holder.textView.setText(mList.get(position).getSerchStr());
		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.textView1)
		TextView textView;
	}

}
