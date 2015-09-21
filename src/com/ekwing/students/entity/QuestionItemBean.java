package com.ekwing.students.entity;

import java.io.Serializable;

public class QuestionItemBean implements Serializable {
	private String keys;
	private String type; // ”type“:’text/img’
	private String text;
	private ListenQuestionBean bean;
	private String right;
	private String ans;
	private String itemPosition;

	public String getItemPosition() {
		if (itemPosition == null) {
			itemPosition = "";
		}
		return itemPosition;
	}

	public void setItemPosition(String itemPosition) {
		this.itemPosition = itemPosition;
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

	public String getRight() {
		if (right == null) {
			right = "";
		}
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public ListenQuestionBean getBean() {
		return bean;
	}

	public void setBean(ListenQuestionBean bean) {
		this.bean = bean;
	}

	public String getKeys() {
		if (keys == null) {
			keys = "";
		}
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
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

	public String getText() {
		if (text == null) {
			text = "";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
