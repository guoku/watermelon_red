/**

 */
package com.guoku.guokuv4.adapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.bean.ArticlesList;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.utils.BitmapUtil;
import com.guoku.guokuv4.utils.DateUtils;
import com.guoku.guokuv4.utils.StringUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-14 下午6:46:17 文章列表
 */
public class ArticleAdapter extends ArrayListAdapter<ArticlesList> {

	private int w = GuokuApplication.screenW;
	private int w_width;
	private int w_hight;
	
	public ArticleAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		w_width = BitmapUtil.dip2pix(mContext, 20);
		w_hight = w / 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHold holder = null;
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.item_fragment_article, null);
			holder = new ViewHold();
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHold) convertView.getTag();
		}

		ArticlesList articles = mList.get(position);
		holder.tvName.setText(articles.getTitle());
//		holder.tvContext.setText(articles.getContent50());
		if(!StringUtils.isEmpty(articles.getDigest())){
			holder.tvContext.setText(articles.getDigest());
		}
		holder.imgIcon.setImageURI(Uri.parse(Constant.URL_IMG
				+ articles.getCover().replace("images", "images/750")));

		LayoutParams params = (LayoutParams) holder.imgIcon.getLayoutParams();
		params.height = w_hight;
		params.width = w - w_width;
		holder.imgIcon.setLayoutParams(params);
		holder.tvTime.setText(DateUtils.getStandardDate(String.valueOf(articles.getPub_time())));

		return convertView;
	}

	static class ViewHold {
		@ViewInject(R.id.article_title)
		TextView tvName;// 文章名称
		@ViewInject(R.id.article_context)
		TextView tvContext;// 文章内容
		@ViewInject(R.id.article_left)
		TextView tvBelow;// 底部标签
		@ViewInject(R.id.article_right)
		TextView tvTime;// 底部时间
		@ViewInject(R.id.article_img)
		SimpleDraweeView imgIcon;// 文章icon
	}

}
