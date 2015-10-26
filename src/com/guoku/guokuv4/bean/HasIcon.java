package com.guoku.guokuv4.bean;

import java.io.Serializable;

public class HasIcon implements Serializable {
	private String id;
	private String url;
	private String name; // 标记名称

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		if (url == null) {
			url = "";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "HasIcon [id=" + id + ", url=" + url + ", name=" + name + "]";
	}
}
