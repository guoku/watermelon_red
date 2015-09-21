/**

 */
package com.guoku.guokuv4.bean;

import java.io.Serializable;

/**
 * @zhangyao
 * @Description: TODO
 * @date 2015-9-17 下午4:55:08
 */
public class TagTwo implements Serializable {

	String category_icon_large;

	String category_icon_small;

	int category_id;

	String category_title;

	int status;

	public String getCategory_icon_large() {
		return category_icon_large;
	}

	public void setCategory_icon_large(String category_icon_large) {
		this.category_icon_large = category_icon_large;
	}

	public String getCategory_icon_small() {
		return category_icon_small;
	}

	public void setCategory_icon_small(String category_icon_small) {
		this.category_icon_small = category_icon_small;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public String getCategory_title() {
		return category_title;
	}

	public void setCategory_title(String category_title) {
		this.category_title = category_title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
