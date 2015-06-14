package com.ekwing.students.entity;

public class CircleItemBean {

	private String name;

	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
