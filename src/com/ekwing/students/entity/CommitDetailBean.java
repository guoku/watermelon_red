package com.ekwing.students.entity;

import java.io.Serializable;

/**
 * 单词听写fill的bean ,
 * 
 * @author ytbnkkf
 * 
 */
public class CommitDetailBean implements Serializable {
	private String id; // 单词id
	private String text; // 答案
	private String ans; // 用户输入

	// public CommitDetailBean(String id, String text, String ans) {
	// this.id = id;
	// this.text = text;
	// this.ans = ans;
	// }

	public CommitDetailBean() {
	}

	public String getId() {
		if(id ==null){
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		if(text ==null){
			text="";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAns() {
		if (ans == null) {
			ans = "";
		}
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

}
