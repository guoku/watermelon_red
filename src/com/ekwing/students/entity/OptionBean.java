package com.ekwing.students.entity;

import java.io.Serializable;

public class OptionBean implements Serializable {
	private String id;
	private String key;
	private String text;
	private String book_content_id, units_id;

	public String getBook_content_id() {
		if (book_content_id == null) {
			book_content_id = "";
		}
		return book_content_id;
	}

	public void setBook_content_id(String book_content_id) {
		this.book_content_id = book_content_id;
	}

	public String getUnits_id() {
		if (units_id == null) {
			units_id = "";
		}
		return units_id;
	}

	public void setUnits_id(String units_id) {
		this.units_id = units_id;
	}

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getKey() {
		if (key == null) {
			key = "";
		}
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "OptionBean [id=" + id + ", key=" + key + ", text=" + text + "]";
	}

}
