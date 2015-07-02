package com.guoku.guokuv4.entity.test;

import java.io.Serializable;

public class Tab2Bean implements Serializable {
	private String status, category_id, category_icon_small, category_title,
			category_icon_large;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory_id() {
		return category_id;
	}

	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}

	public String getCategory_icon_small() {
		return category_icon_small;
	}

	public void setCategory_icon_small(String category_icon_small) {
		this.category_icon_small = category_icon_small;
	}

	public String getCategory_title() {
		if (category_title.contains("-")) {
			return category_title.substring(0, category_title.indexOf("-"));
		}
		return category_title;
	}

	public void setCategory_title(String category_title) {
		this.category_title = category_title;
	}

	public String getCategory_icon_large() {
		return category_icon_large;
	}

	public void setCategory_icon_large(String category_icon_large) {
		this.category_icon_large = category_icon_large;
	}

	@Override
	public String toString() {
		return "Tab2Bean [status=" + status + ", category_id=" + category_id
				+ ", category_icon_small=" + category_icon_small
				+ ", category_title=" + category_title
				+ ", category_icon_large=" + category_icon_large + "]";
	}

}
