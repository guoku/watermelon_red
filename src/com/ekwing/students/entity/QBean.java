package com.ekwing.students.entity;

import java.io.Serializable;

public class QBean implements Serializable {
	private String id, city_id, name;

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity_id() {
		if (city_id == null) {
			city_id = "";
		}
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
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

}
