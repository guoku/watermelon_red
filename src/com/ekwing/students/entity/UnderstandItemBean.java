package com.ekwing.students.entity;

import java.io.Serializable;

/**
 * 阅读理解选择item的bean 包括选项和内容
 * 
 * @author ytbnkkf
 * 
 */
public class UnderstandItemBean implements Serializable {

	private String keys; // 选项
	private String text; // 内容
//	private String selected; // 是否被选中
	private UnderstandQuestionBean bean;

	public UnderstandQuestionBean getBean() {
		return bean;
	}

	public void setBean(UnderstandQuestionBean bean) {
		this.bean = bean;
	}

//	public String getSelected() {
//		if (selected == null) {
//			selected = "-1";
//		}
//		return selected;
//	}
	//
	// public void setSelected(String selected) {
	// this.selected = selected;
	// }

	public String getKeys() {
		if (keys == null) {
			keys = "";
		}
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
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

	@Override
	public String toString() {
		return "UnderstandItemBean [keys=" + keys + ", text=" + text + "]";
	}

}
