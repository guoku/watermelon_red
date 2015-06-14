package com.ekwing.students.entity;

import java.io.Serializable;

public class DownUrlBean implements Serializable {
	private String url;

	public String getUrl() {
		if (url == null) {
			url = "";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
