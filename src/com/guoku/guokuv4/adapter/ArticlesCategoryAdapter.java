/**

 */
package com.guoku.guokuv4.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.guokuv4.bean.ArticlesList;
import com.guoku.guokuv4.config.Constant;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年1月29日 下午5:01:54 
 * 一级品类中的图文
 */
public class ArticlesCategoryAdapter extends ArrayListAdapter<ArticlesList> {

	public ArticlesCategoryAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.home_one_articles_item, null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}

		ArticlesList articles = mList.get(position);
		holder.tvName.setText(articles.getTitle());
		holder.tvBelow.setText(articles.getDigest());
		holder.imgIcon.setImageURI(Uri.parse(Constant.URL_IMG
				+ articles.get240()));

		return convertView;
	}

	class ViewHold {
		@ViewInject(R.id.article_title)
		TextView tvName;// 文章名称
		@ViewInject(R.id.article_below)
		TextView tvBelow;// 底部标签
		@ViewInject(R.id.img_article)
		SimpleDraweeView imgIcon;// 文章icon
	}
}
