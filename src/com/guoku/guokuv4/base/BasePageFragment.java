/**

 */
package com.guoku.guokuv4.base;

import java.util.ArrayList;
import java.util.List;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.homepage.HomeOneFragment;
import com.guoku.guokuv4.homepage.GoodTwoFragmnet;
import com.guoku.guokuv4.utils.ImgUtils;
import com.guoku.guokuv4.utils.ToastUtil;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-14 下午1:38:08 滑动的标签页
 */
public abstract class BasePageFragment extends BaseFrament {

	private ViewPager viewPager;// 页卡内容

	LinearLayout layoutText;
	
	LinearLayout layoutImg;
	
	ImageView imageView;// 动画图片

	private int offset = 0;// 动画图片偏移量
	public int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int selectedColor, unSelectedColor;

	public int currentItems = 0;
	
	ArrayList<TextView> lTextViews;

	@Override
	protected void init() {
		// TODO Auto-generated method stub

		selectedColor = getResources().getColor(R.color.rose_red);
		unSelectedColor = getResources().getColor(R.color.title_bar_gray);

		InitTextView(initTitleList());
		InitImageView();
		InitViewPager(initFragmentList());
	}

	/**
	 * 标签页数量
	 * 
	 * @return
	 */
	public abstract int tabCount();

	/**
	 * 标签页数量
	 * 
	 * @return
	 */
	public int getTabCount() {
		return tabCount();
	};

	/**
	 * 标签页title
	 * 
	 * @return
	 */
	public abstract ArrayList<TextView> initTitleList();

	/**
	 * 标签页fragment
	 * 
	 * @return
	 */
	public abstract ArrayList<Fragment> initFragmentList();
	
	public void setCurrentItems(int onItem) {
		viewPager.setCurrentItem(onItem);
	}
	
	public int getviewPagerCurrent(){
		return viewPager.getCurrentItem();
	}

	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView(ArrayList<TextView> arrayList) {

		lTextViews = arrayList;

		layoutText = (LinearLayout) contentView
				.findViewById(R.id.linearLayout1);
		for (int i = 0; i < lTextViews.size(); i++) {
			TextView tView = arrayList.get(i);
			if (i == 0) {
				tView.setTextColor(selectedColor);
			} else {
				tView.setTextColor(unSelectedColor);
			}
			tView.setOnClickListener(new MyOnClickListener(i, tView));
			tView.setGravity(Gravity.CENTER);
			LayoutParams lParams = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
			tView.setLayoutParams(lParams);
			layoutText.addView(tView);
		}
	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果
	 */

	private void InitImageView() {
		
		layoutImg = (LinearLayout) contentView
				.findViewById(R.id.linearLayout2);
		
		LayoutParams lParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		for(int i = 0; i < getTabCount(); i ++){
			if(i == 0){
				imageView = new ImageView(context);
				imageView.setLayoutParams(lParams);
				imageView.setImageResource(R.drawable.tab_focus);
				layoutImg.addView(imageView);
			}else{
				ImageView tempView = new ImageView(context);
				tempView.setLayoutParams(lParams);
				tempView.setImageResource(R.drawable.tab_focus);
				tempView.setVisibility(View.INVISIBLE);
				layoutImg.addView(tempView);
			}
		}
		
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_focus).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / getTabCount()) / getTabCount() - bmpW/getTabCount();// 计算偏移量--(屏幕宽度/页卡总数)/2-图片实际宽度/2
														// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}

	/**
	 * 初始化Viewpager页
	 */
	private void InitViewPager(ArrayList<Fragment> fragments) {
		viewPager = (ViewPager) contentView.findViewById(R.id.vPager);
		viewPager.setAdapter(new myPagerAdapter(getChildFragmentManager(),
				fragments));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_base_page;
	}

	@Override
	protected void onSuccess(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onFailure(String result, int where) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub

	}

	/**
	 * 头标点击监听
	 */
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		private TextView tView;

		public MyOnClickListener(int i, TextView textView) {
			this.index = i;
			this.tView = textView;
		}

		public void onClick(View v) {
			int tag = (Integer) v.getTag();
			if (tag == index) {
				tView.setTextColor(selectedColor);
			} else {
				tView.setTextColor(unSelectedColor);
			}
			viewPager.setCurrentItem(index);
		}

	}

	/**
	 * 为选项卡绑定监听器
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * getTabCount() + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * getTabCount();// 页卡1 -> 页卡3 偏移量

		MyOnPageChangeListener() {

		}

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);//
			currIndex = index;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

			for (int i = 0; i < lTextViews.size(); i++) {
				if ((Integer) initTitleList().get(i).getTag() == index) {
					lTextViews.get(i).setTextColor(selectedColor);
				} else {
					lTextViews.get(i).setTextColor(unSelectedColor);
				}
			}
		}
	}

	/**
	 * 定义适配器
	 */
	class myPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		/**
		 * 得到每个页面
		 */
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		/**
		 * 每个页面的title
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		/**
		 * 页面的总个数
		 */
		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}
}
