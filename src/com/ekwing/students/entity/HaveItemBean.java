package com.ekwing.students.entity;

import java.io.Serializable;

public class HaveItemBean implements Serializable{
	private String item; // "item": "A| ",
	private String type; // "type":"text/img
	private String value;

	public String getItem() {
		if (item == null) {
			item = "";
		}
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getType() {
		if (type == null) {
			type = "";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		if (value == null) {
			value = "";
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
