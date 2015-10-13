/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-10-13 上午11:20:36 
 * 热门文章推荐
 */
public class GuangArticles {
	
	int read_count;//文章id
	
	String showcover;
	
	String title;//文章标题
	
	String url;//文章链接
	
	ArrayList<String> tags;
	
	String cover;
	
	int publish;
	
	String content;
	
	Creator creator;
	
	int article_id;

	public int getRead_count() {
		return read_count;
	}

	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}

	public String getShowcover() {
		return showcover;
	}

	public void setShowcover(String showcover) {
		this.showcover = showcover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public int getPublish() {
		return publish;
	}

	public void setPublish(int publish) {
		this.publish = publish;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	public int getArticle_id() {
		return article_id;
	}

	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}

	public class Creator{
		
	}

}
