/**

 */
package com.guoku.guokuv4.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.bean.ArticlesList;
import com.guoku.guokuv4.config.Constant;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月12日 下午3:12:27 搜索图文adapter
 */
public class SearchArticleFragmentAdapter extends ArrayListAdapter<ArticlesList> {
	private int w = GuokuApplication.screenW / 4;

	public SearchArticleFragmentAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_search_article_fragment, null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}
		ArticlesList articles = (ArticlesList) mList.get(position);
		holder.entity_item_iv_pic.setImageURI(Uri.parse(Constant.URL_IMG + articles.getCover()));
		LayoutParams params = new LayoutParams(w, w);
		holder.entity_item_iv_pic.setLayoutParams(params);
		holder.entity_item_tv_name.setText(articles.getTitle());
		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.entity_item_iv_pic)
		SimpleDraweeView entity_item_iv_pic;
		@ViewInject(R.id.entity_item_tv_name)
		TextView entity_item_tv_name;
	}

}
