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

	ArrayList<Articles> articles;

	Stat stat;

	public ArrayList<Articles> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Articles> articles) {
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

		public int getAll_count() {
			return all_count;
		}

		public void setAll_count(int all_count) {
			this.all_count = all_count;
		}
	}
}
