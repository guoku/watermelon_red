package com.ekwing.students.entity;

import java.io.Serializable;

public class CBean implements Serializable {
	private String id, province_id, name;

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince_id() {
		if (province_id == null) {
			province_id = "";
		}
		return province_id;
	}

	public void setProvince_id(String province_id) {
		this.province_id = province_id;
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
