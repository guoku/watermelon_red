/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-11 上午11:02:24 首页图文banner流
 */
public class HomePageOneBean {

	ArrayList<ArticlesList> articles;

	ArrayList<Banner> banner;

	ArrayList<Entities> entities;

	public ArrayList<ArticlesList> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<ArticlesList> articles) {
		this.articles = articles;
	}

	public ArrayList<Banner> getBanner() {
		return banner;
	}

	public void setBanner(ArrayList<Banner> banner) {
		this.banner = banner;
	}

	public ArrayList<Entities> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entities> entities) {
		this.entities = entities;
	}

}
