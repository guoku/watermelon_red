/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年1月29日 下午4:17:53 一级品类的图文
 */
public class ArticlesCategoryFirst {

	ArrayList<ArticlesList> articles;

	Stat stat;

	public ArrayList<ArticlesList> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ArticlesList> articles) {
		this.articles = articles;
	}

	public Stat getStat() {
		return stat;
	}

	public void setStat(Stat stat) {
		this.stat = stat;
	}

	public class Stat {
		int all_count;
		
		boolean is_sub;//是否是二级品类
		
		int group_id;//二级所属的一级品类id
		
		public boolean isIs_sub() {
			return is_sub;
		}

		public void setIs_sub(boolean is_sub) {
			this.is_sub = is_sub;
		}

		public int getGroup_id() {
			return group_id;
		}

		public void setGroup_id(int group_id) {
			this.group_id = group_id;
		}

		public int getAll_count() {
			return all_count;
		}

		public void setAll_count(int all_count) {
			this.all_count = all_count;
		}
	}
}
