/**

 */
package com.guoku.guokuv4.gragment;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoku.R;
import com.guoku.guokuv4.base.BaseFrament;
import com.guoku.guokuv4.homepage.OneFragment;
import com.guoku.guokuv4.homepage.TwoFragmnet;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-9 下午4:03:08 
 * 4.1精选首页图文流
 */
public class JingXuanPageFragment extends BaseFrament{
	
	@ViewInject(R.id.vPager)
	private ViewPager viewPager;// 页卡内容
	
	@ViewInject(R.id.tab_1)
	private TextView tab1;// 推荐
	
	@ViewInject(R.id.tab_2)
	private TextView tab2;// 商品
	
	@ViewInject(R.id.tab_3)
	private TextView tab3;// 图文
	
	@ViewInject(R.id.cursor)
	ImageView imageView;//动画图片
	
	private List<Fragment> fragments;// Tab页面列表
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private int selectedColor, unSelectedColor;
	/** 页卡总数 **/
	private static final int pageSize = 3;

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
		selectedColor = getResources().getColor(R.color.rose_red);
		unSelectedColor = getResources().getColor(R.color.title_bar_gray);

		InitImageView();
		InitTextView();
		InitViewPager();
	}
	

	/**
	 * 初始化头标
	 * 
	 */
	private void InitTextView() {

		tab1.setTextColor(selectedColor);
		tab2.setTextColor(unSelectedColor);
		tab3.setTextColor(unSelectedColor);

		tab1.setOnClickListener(new MyOnClickListener(0));
		tab2.setOnClickListener(new MyOnClickListener(1));
		tab3.setOnClickListener(new MyOnClickListener(2));
	}
	
	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果
	 */

	private void InitImageView() {
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_focus).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / pageSize) / 2 - bmpW * 2;// 计算偏移量--(屏幕宽度/页卡总数)/2-图片实际宽度*2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	/**
	 * 初始化Viewpager页
	 */
	private void InitViewPager() {
		fragments = new ArrayList<Fragment>();
		fragments.add(new OneFragment());
		fragments.add(new TwoFragmnet());
		fragments.add(new TwoFragmnet());
		viewPager.setAdapter(new myPagerAdapter(getChildFragmentManager(),
				fragments));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}


	@Override
	protected int getContentId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_homepage;
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

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {

			switch (index) {
			case 0:
				tab1.setTextColor(selectedColor);
				tab2.setTextColor(unSelectedColor);
				tab3.setTextColor(unSelectedColor);
				break;
			case 1:
				tab2.setTextColor(selectedColor);
				tab1.setTextColor(unSelectedColor);
				tab3.setTextColor(unSelectedColor);
				break;
			case 2:
				tab3.setTextColor(selectedColor);
				tab1.setTextColor(unSelectedColor);
				tab2.setTextColor(unSelectedColor);
				break;
			}
			viewPager.setCurrentItem(index);
		}

	}
	
	/**
	 * 为选项卡绑定监听器
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = index;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);

			switch (index) {
			case 0:
				tab1.setTextColor(selectedColor);
				tab2.setTextColor(unSelectedColor);
				tab3.setTextColor(unSelectedColor);
				break;
			case 1:
				tab2.setTextColor(selectedColor);
				tab1.setTextColor(unSelectedColor);
				tab3.setTextColor(unSelectedColor);
				break;
			case 2:
				tab3.setTextColor(selectedColor);
				tab1.setTextColor(unSelectedColor);
				tab2.setTextColor(unSelectedColor);
				break;
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
