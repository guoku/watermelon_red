package com.ekwing.students.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class BasePageAdapter extends FragmentStatePagerAdapter {

	public ArrayList<Fragment> mFragments = new ArrayList<Fragment>();;
	private List<String> titles = new ArrayList<String>();

	public BasePageAdapter(FragmentActivity activity) {
		super(activity.getSupportFragmentManager());
	}

	public void addFragment(List<String> titles, List<Fragment> fragment) {
		this.titles.addAll(titles);
		mFragments.addAll(fragment);
		notifyDataSetChanged();
	}

	public void addTab(String title, Fragment fragment) {
		this.titles.add(title);
		mFragments.add(fragment);
		notifyDataSetChanged();
	}

	public void addFragment(List<Fragment> fragment) {
		mFragments.addAll(fragment);
		notifyDataSetChanged();
	}

	public void addTab(Fragment fragment) {
		mFragments.add(fragment);
		notifyDataSetChanged();
	}

	public void Clear() {
		mFragments.clear();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (titles.size() > position) {
			return titles.get(position);
		} else {
			return "";
		}
	}

	@Override
	public Fragment getItem(int arg0) {
		return mFragments.get(arg0);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
}
