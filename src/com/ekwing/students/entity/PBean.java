package com.ekwing.students.entity;

import java.io.Serializable;

public class PBean implements Serializable {
	private String id, name, key, allkey;

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getKey() {
		if (key == null) {
			key = "";
		}
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAllkey() {
		if (allkey == null) {
			allkey = "";
		}
		return allkey;
	}

	public void setAllkey(String allkey) {
		this.allkey = allkey;
	}

}
