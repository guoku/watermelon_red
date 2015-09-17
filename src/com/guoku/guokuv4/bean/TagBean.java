/**

 */
package com.guoku.guokuv4.bean;

import java.util.ArrayList;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-17 下午4:37:33 品类
 */
public class TagBean {

	int category_count;
	
	ArrayList<TagTwo> content;
	
	int group_id;
	
	int status;
	
	String title;

	public int getCategory_count() {
		return category_count;
	}

	public void setCategory_count(int category_count) {
		this.category_count = category_count;
	}

	public ArrayList<TagTwo> getContent() {
		return content;
	}

	public void setContent(ArrayList<TagTwo> content) {
		this.content = content;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
