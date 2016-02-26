/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2016年2月26日 上午11:39:30 
 * 用户信息中的图文
 */
public class ArticlesUserBean {

	int count;
	
	ArrayList<Articles> articles;
	
	int page;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<Articles> getArticles() {
		return articles;
	}

	public void setArticles(ArrayList<Articles> articles) {
		this.articles = articles;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	
}
