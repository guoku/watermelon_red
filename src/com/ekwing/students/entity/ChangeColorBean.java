package com.ekwing.students.entity;

import java.io.Serializable;

public class ChangeColorBean implements Serializable {
	private String str;

	public String getStr() {
		if (str == null) {
			str = "";
		}
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
