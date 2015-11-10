/**

 */
package com.guoku.guokuv4.act.seach;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月10日 下午6:11:44 
 */
public class SearchInterface {

	public static interface OnActivityChangeListener{
		void onActivityChange();
	}
	public static interface OnFragmentChangeListener{
		void onFragmentChange(String str);
	}
}
