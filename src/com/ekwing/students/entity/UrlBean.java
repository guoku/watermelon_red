package com.ekwing.students.entity;

import java.io.Serializable;

public class UrlBean implements Serializable{
	private String url;

	public String getUrl() {
		if(url ==null){
			url = "";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "UrlBean [url=" + url + "]";
	}
	
}
