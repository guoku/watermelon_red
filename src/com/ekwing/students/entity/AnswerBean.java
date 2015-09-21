package com.ekwing.students.entity;

import java.io.Serializable;

public class AnswerBean implements Serializable {
	private String id;
	private String choose;
	private String selected;
	private String text;
	private String type; // img/text ,img--->图片 text---->文字
	private ListItemBean bean;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ListItemBean getBean() {
		return bean;
	}

	public void setBean(ListItemBean bean) {
		this.bean = bean;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}

	@Override
	public String toString() {
		return "AnswerBean [choose=" + choose + "]";
	}

	public AnswerBean(String choose) {
		super();
		this.choose = choose;
	}

	public AnswerBean() {
		super();
	}

}
