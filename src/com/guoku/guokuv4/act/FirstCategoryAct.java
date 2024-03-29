/**

 */
package com.guoku.guokuv4.act;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.guoku.R;
import com.guoku.app.GuokuApplication;
import com.guoku.guokuv4.adapter.ArticlesCategoryAdapter;
import com.guoku.guokuv4.adapter.GridViewAdapter;
import com.guoku.guokuv4.base.NetWorkActivity;
import com.guoku.guokuv4.bean.ArticlesCategoryFirst;
import com.guoku.guokuv4.bean.Sharebean;
import com.guoku.guokuv4.bean.TagBean;
import com.guoku.guokuv4.bean.TagTwo;
import com.guoku.guokuv4.config.Constant;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.SharePrenceUtil;
import com.guoku.guokuv4.utils.StringUtils;
import com.guoku.guokuv4.view.ScrollViewWithGridView;
import com.guoku.guokuv4.view.ScrollViewWithListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年1月29日 上午10:59:39 一级品类，包含图文和商品
 */
public class FirstCategoryAct extends NetWorkActivity {

	private final int NET_FIRST_ARTICLES = 1001;
	private final int CATABLIST = 1002;
	private final int PROINFO = 1003;
	private static final int CATABLIST_UP = 1004;

	public static final String SECOND_ACT_ONTENT = "SECOND_ACT_ONTENT";// 二级分类ACT

	@ViewInject(R.id.view_tw)
	View view;

	@ViewInject(R.id.view_line)
	View viewLine;

	@ViewInject(R.id.view_bg)
	View viewBg;

	@ViewInject(R.id.tv_more)
	TextView tvMore;

	@ViewInject(R.id.tv_more_articles)
	TextView tvMoreArticles;// 更多图文

	@ViewInject(R.id.layout_add_tag)
	LinearLayout layoutAddTag;// 标签layout

	@ViewInject(R.id.listView_article)
	private ScrollViewWithListView lvArticle;// 图文

	@ViewInject(R.id.tab_gv)
	private ScrollViewWithGridView tab_gv;

	@ViewInject(R.id.sv)
	private PullToRefreshScrollView sv;// gridview上拉刷新

	GridViewAdapter gvAdapter;
	ArticlesCategoryAdapter articlesAdapter;// 图文adapter
	TagBean tagBean;// 一级全部

	private String cid;
	private String defWgat = TIME;// 默认按时间排序
	private int page = 1;
	ArrayList<EntityBean> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_category);
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub
		sv.onRefreshComplete();
		switch (where) {
		case NET_FIRST_ARTICLES:
			try {
				ArticlesCategoryFirst aFirst = JSON.parseObject(result, ArticlesCategoryFirst.class);

				if (aFirst.getArticles().size() > 0) {
					articlesAdapter.setList(aFirst.getArticles());
					if (aFirst.getStat().getAll_count() > 3) {
						tvMoreArticles.setVisibility(View.VISIBLE);
					} else {
						tvMoreArticles.setVisibility(View.GONE);
					}
					hideView(false);
				} else {
					hideView(true);
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case CATABLIST:
			try {
				list = (ArrayList<EntityBean>) JSON.parseArray(result, EntityBean.class);
				gvAdapter.setList(list);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case PROINFO:
			try {
				PInfoBean bean = ParseUtil.getPI(result);
				Intent intent = new Intent(context, ProductInfoAct.class);
				intent.putExtra("data", JSON.toJSONString(bean));
				startActivity(intent);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case CATABLIST_UP:
			try {
				ArrayList<EntityBean> lists = (ArrayList<EntityBean>) JSON.parseArray(result, EntityBean.class);
				list.addAll(lists);
				gvAdapter.addListsLast(lists);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub
		sv.onRefreshComplete();
	}

	@Override
	protected void setupData() {
		// TODO Auto-generated method stub
		cid = getIntent().getStringExtra("data");
		setGCenter(true, getIntent().getStringExtra("name"));
		setGLeft(true, R.drawable.back_selector);

		sendDataAricles(NET_FIRST_ARTICLES, true, "");
		sendData(CATABLIST, true);
		init();
		initTag();
		initArticle();
		initShop();
	}

	private void init() {
		sv.setMode(Mode.BOTH);
		sv.setOnRefreshListener(new OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				page = 1;

				sendDataAricles(NET_FIRST_ARTICLES, false, "");
				sendData(CATABLIST, false);
				umStatistics(Constant.UM_SUGGESTED_FIRSE_DOWN, "page = " + page , "");
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				page++;
				sendData(CATABLIST_UP, false);
				umStatistics(Constant.UM_SUGGESTED_FIRSE_UP, "page = " + page , "");
			}
		});
	}

	private void initShop() {

		gvAdapter = new GridViewAdapter(context, 2);
		tab_gv.setNumColumns(2);
		// tab_gv.setHorizontalSpacing(40);
		tab_gv.setAdapter(gvAdapter);
		tab_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				sendConnection(Constant.PROINFO + gvAdapter.getList().get(arg2).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { gvAdapter.getList().get(arg2).getEntity_id() },
						PROINFO, true);
				
				umStatistics(Constant.UM_SUGGESTED_FIRSE_TO_GOOD, gvAdapter.getList().get(arg2).getEntity_id() , gvAdapter.getList().get(arg2).getTitle());
			}
		});
	}

	@SuppressLint("NewApi")
	private void initTag() {

		try {
			String result = SharePrenceUtil.getTab(mContext);
			if (!StringUtils.isEmpty(result)) {
				ArrayList<TagBean> tBean = (ArrayList<TagBean>) JSON.parseArray(result, TagBean.class);

				for (int i = 0; i < tBean.size(); i++) {

					if (String.valueOf(tBean.get(i).getGroup_id()).equals(cid)) {
						tagBean = tBean.get(i);
						final TextView textTemp = (TextView) View.inflate(mContext, R.layout.textview_tag, null);
						textTemp.setText("我");
						Paint paint = new Paint();
						paint.setTextSize(textTemp.getTextSize());
						float size = paint.measureText(textTemp.getText().toString());
						float w = (GuokuApplication.screenW - tvMore.getWidth()) / size;
						
						StringBuffer sBuffer = new StringBuffer();
						List<TagTwo> litTemp = new ArrayList<TagTwo>();
						for (int k = 0; k < tBean.get(i).getContent().size(); k++) {
							final TagTwo tagtwo = tBean.get(i).getContent().get(k);
							if (sBuffer.length() > w) {
								break;
							}
							sBuffer.append(tagtwo.getCategory_title());
							litTemp.add(tagtwo);
						}


						for (int j = 0; j < litTemp.size() / 2; j++) {
							final TagTwo tagtwo = litTemp.get(j);
							final TextView textView = (TextView) View.inflate(mContext, R.layout.textview_tag, null);
							LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
									LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
									1.0f);
							lParams.setMargins(0, 0, 10, 0);
							textView.setText(tagtwo.getCategory_title());
							textView.setLayoutParams(lParams);
							textView.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									Bundle bundle = new Bundle();
									bundle.putSerializable(SECOND_ACT_ONTENT, tagtwo);
									// openActivity(TabListSecondAct.class,
									// bundle);
									openActivity(SecondCategoryAct.class, bundle);

									umStatistics(Constant.UM_SUGGESTED_FIRSE_TO_SEC, String.valueOf(tagtwo.getCategory_id()), tagtwo.getCategory_title());
								}
							});
							layoutAddTag.addView(textView);
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initArticle() {
		articlesAdapter = new ArticlesCategoryAdapter(this);
		lvArticle.setAdapter(articlesAdapter);
		lvArticle.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub

				Bundle bundle = new Bundle();
				Sharebean sharebean = new Sharebean();
				sharebean.setTitle(articlesAdapter.getList().get(arg2).getTitle());
				if(articlesAdapter.getList().get(arg2).getContent().length() > 50){
					sharebean.setContext(articlesAdapter.getList().get(arg2).getContent().substring(0, 50));	
				}else{
					sharebean.setContext(articlesAdapter.getList().get(arg2).getContent());	
				}
				sharebean.setAricleUrl(articlesAdapter.getList().get(arg2).getUrl());
				sharebean.setImgUrl(articlesAdapter.getList().get(arg2).getCover());
				sharebean.setIs_dig(articlesAdapter.getList().get(arg2).isIs_dig());
				bundle.putSerializable(WebShareAct.class.getName(), sharebean);
				sharebean.setAricleId(String.valueOf(articlesAdapter.getList().get(arg2).getArticle_id()));
				openActivity(WebShareAct.class, bundle);
				
				umStatistics(Constant.UM_SUGGESTED_FIRSE_TO_ARICLE, String.valueOf(articlesAdapter.getList().get(arg2).getArticle_id()), articlesAdapter.getList().get(arg2).getTitle());
			}
		});
	}

	@OnClick(R.id.tv_more)
	private void onClickMore(View v) {

		if (tagBean != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(TabAct.class.getName(), tagBean);
			openActivity(CategoryListAct.class, bundle);
			
			umStatistics(Constant.UM_SUGGESTED_FIRSE_MORE_TAG);
		}
	}

	@OnClick(R.id.tv_more_articles)
	private void onClickMoreArticles(View v) {

		if (tagBean != null) {
			Bundle bundle = new Bundle();
			bundle.putString(FirstCategoryAct.class.getName(), cid);
			bundle.putString("TITLE_BAR", getIntent().getStringExtra("name"));
			bundle.putBoolean("IS_FIRST", true);
			openActivity(ArticleListAllAct.class, bundle);
			
			umStatistics(Constant.UM_SUGGESTED_FIRSE_TO_ARICLE_MORE);
		}
	}

	private void sendData(int tag, boolean isLoding) {

		sendConnection(Constant.CATAB + cid + "/selection/", new String[] { "page", "sort" },
				new String[] { String.valueOf(page), defWgat }, tag, isLoding);

	}

	private void sendDataAricles(int tag, boolean isLoding, String urlValue) {
		sendConnection(Constant.CATEGORY_FIRST + urlValue + cid + "/articles/", new String[] { "page", "size" },
				new String[] { "1", "3" }, tag, isLoding);

	}

	private void hideView(boolean isShow) {
		if (isShow) {
			view.setVisibility(View.GONE);
			viewLine.setVisibility(View.GONE);
			viewBg.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
			viewLine.setVisibility(View.VISIBLE);
			viewBg.setVisibility(View.VISIBLE);
		}
	}

}
