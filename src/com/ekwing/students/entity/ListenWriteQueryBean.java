package com.ekwing.students.entity;

import java.io.Serializable;

public class ListenWriteQueryBean implements Serializable{
	private String id; // 单词id
	// private String isTure;
	private boolean isTure; // 用户输入的是否正确
	private String text; // 正确单词
	private String ans; // 用户输入
	private String url; // 音频地址

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public boolean isTure() {
		return isTure;
	}

	public void setTure(boolean isTure) {
		this.isTure = isTure;
	}


	public String getAns() {
		if(ans == null){
			ans = "";
		}
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public String getText() {
		if (text == null) {
			text = "";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

}
