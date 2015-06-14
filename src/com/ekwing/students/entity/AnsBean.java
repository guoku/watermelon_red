package com.ekwing.students.entity;

import java.io.Serializable;

public class AnsBean implements Serializable {
	private String id;
	private String ans;

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
