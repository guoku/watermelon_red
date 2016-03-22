/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015年11月12日 下午3:20:41 搜索图文
 */
public class ArticlesSearch {

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

		public int getAll_count() {
			return all_count;
		}

		public void setAll_count(int all_count) {
			this.all_count = all_count;
		}
	}
}
