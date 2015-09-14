/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-14 下午7:10:27 首页文章列表
 */
public class HomeArticleBean {
	
	ArrayList<Articles> articles;

	ArrayList<Banner> banner;

	ArrayList<Entities> entities;

	ArrayList<Categories> categories;

	public ArrayList<Articles> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Articles> articles) {
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

	public ArrayList<Categories> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Categories> categories) {
		this.categories = categories;
	}
}
