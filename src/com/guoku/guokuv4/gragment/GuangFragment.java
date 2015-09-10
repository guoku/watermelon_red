package com.guoku.guokuv4.gragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ekwing.students.EkwingApplication;
import com.ekwing.students.config.Constant;
import com.ekwing.students.utils.ArrayListAdapter;
import com.guoku.R;
import com.guoku.guokuv4.act.ProductInfoAct;
import com.guoku.guokuv4.act.SeachAct;
import com.guoku.guokuv4.act.TabAct;
import com.guoku.guokuv4.act.UserAct;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.bean.Categories;
import com.guoku.guokuv4.bean.Categories.Category;
import com.guoku.guokuv4.entity.test.EntityBean;
import com.guoku.guokuv4.entity.test.PInfoBean;
import com.guoku.guokuv4.entity.test.Tab2Bean;
import com.guoku.guokuv4.entity.test.UserBean;
import com.guoku.guokuv4.parse.ParseUtil;
import com.guoku.guokuv4.utils.ImgUtils;
import com.guoku.guokuv4.view.ImageAddTextLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class GuangFragment extends BaseFrament {
	private static final int HOT = 10;
	private static final int PROINFO = 12;

	private static final int DISCOVER = 15;// 推荐品类

	@ViewInject(R.id.user_et_name)
	private TextView user_et_name;

	@ViewInject(R.id.faxian_sv)
	private ScrollView sv;

	@ViewInject(R.id.gallery_recommend_sort)
	private LinearLayout vpRecommendSort;// 推荐品类

	@ViewInject(R.id.faxian_gv)
	private GridView faxian_gv;

	private ArrayListAdapter<EntityBean> gvAdapter;

	private DisplayImageOptions options;
	private DisplayImageOptions optionsRound;// 圆角的
	private ArrayList<EntityBean> hotList;

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		imageLoader.clearMemoryCache();
	}

	@Override
	protected int getContentId() {
		return R.layout.fragment_guang;
	}

	@Override
	protected void onSuccess(String result, int where) {
		if (getActivity() == null) {
			return;
		}
		switch (where) {
		case HOT:
			try {
				JSONObject root = new JSONObject(result);
				JSONArray array = root.getJSONArray("content");
				hotList = new ArrayList<EntityBean>();
				for (int i = 0; i < array.length(); i++) {
					hotList.add(JSON.parseObject(array.getJSONObject(i)
							.getString("entity"), EntityBean.class));
				}
				gvAdapter.setList(hotList);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case PROINFO:
			PInfoBean bean = ParseUtil.getPI(result);
			Intent intent = new Intent(context, ProductInfoAct.class);
			intent.putExtra("data", JSON.toJSONString(bean));
			startActivity(intent);
			break;
		case DISCOVER:
			try {
				JSONObject root = new JSONObject(result);
				ArrayList<Categories> arrayList = new ArrayList<Categories>();
				arrayList = (ArrayList<Categories>) JSON.parseArray(
						root.getString("categories"), Categories.class);
				for (int i = 0; i < arrayList.size(); i++) {
					final ImageAddTextLayout imagTextLayout = new ImageAddTextLayout(
							getActivity());
					LayoutParams params = new LayoutParams(280, 280);
					imagTextLayout.setLayoutParams(params);
					imagTextLayout.setPadding(10, 0, 10, 0);
					imageLoader.displayImage(arrayList.get(i).getCategory()
							.getCover_url(), imagTextLayout.imView,
							optionsRound,
							new ImgUtils.AnimateFirstDisplayListener());
					imagTextLayout.tView.setText(arrayList.get(i).getCategory()
							.getTitle().trim().replace(" ", "\n"));
					imagTextLayout.setTag(arrayList.get(i).getCategory());
					vpRecommendSort.addView(imagTextLayout);
					imagTextLayout.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Categories.Category category = (Category) arg0
									.getTag();
							Intent intent = new Intent(getActivity(),
									TabAct.class);
							intent.putExtra("data", category.getId());
							intent.putExtra("name", category.getTitle());
							getActivity().startActivity(intent);
						}
					});
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			sv.scrollTo(0, 0);
			sv.smoothScrollTo(0, 0);
			break;
		}

	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void init() {

		options = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.drawable.item240)
				.showImageForEmptyUri(R.drawable.item240)
				.showImageOnFail(R.drawable.item240).build();

		optionsRound = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.EXACTLY)
				.considerExifParams(true).bitmapConfig(Config.RGB_565)
				.showImageOnLoading(R.drawable.item240)
				.showImageForEmptyUri(R.drawable.item240)
				.showImageOnFail(R.drawable.item240)
				.displayer(new RoundedBitmapDisplayer(20)).build();

		gvAdapter = new ArrayListAdapter<EntityBean>(context) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				if (convertView == null) {
					convertView = new ImageView(mContext);
					LayoutParams params = new LayoutParams(
							EkwingApplication.screenW / 3 - 10,
							EkwingApplication.screenW / 3 - 10);
					convertView.setLayoutParams(params);
					((ImageView) convertView).setScaleType(ScaleType.FIT_XY);
					convertView.setBackgroundColor(Color.WHITE);
				}
				imageLoader.displayImage(mList.get(position).get240(),
						(ImageView) convertView, options,
						new ImgUtils.AnimateFirstDisplayListener());

				return convertView;
			}
		};

		hotList = new ArrayList<EntityBean>();

		faxian_gv.setAdapter(gvAdapter);

		faxian_gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sendConnection(Constant.PROINFO
						+ hotList.get(arg2).getEntity_id() + "/",
						new String[] { "entity_id" }, new String[] { hotList
								.get(arg2).getEntity_id() }, PROINFO, true);

			}
		});
	}

	@Override
	protected void setData() {
		sendConnection(Constant.HOT, new String[] {}, new String[] {}, HOT,
				false);

		sendConnection(Constant.DISCOVER, new String[] {}, new String[] {},
				DISCOVER, false);

	}

	@OnClick(R.id.user_et_name)
	public void SQR(View v) {
		startActivity(new Intent(context, SeachAct.class));

	}
}
