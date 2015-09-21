package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class UnderstandQuestionBean implements Serializable {
	private String id; // 问题id
	private String question; // 问题
	private String subtypes; // 题型子类型
	private ArrayList<UnderstandItemBean> items; // 选项
	private String itemstype; // 选项类型[1->文字;2->图片;3->音频]
	private String related_id;
	private String related_type;
	private String selected; // 是否被选中
	private String answer; // 正确答案
	private String user_answer; // 用户选项
	private String analyz; // 解析

	public String getUser_answer() {
		if (user_answer == null) {
			user_answer = "";
		}
		return user_answer;
	}

	public void setUser_answer(String user_answer) {
		this.user_answer = user_answer;
	}

	public String getAnalyz() {
		if (analyz == null) {
			analyz = "";
		}
		return analyz;
	}

	public void setAnalyz(String analyz) {
		this.analyz = analyz;
	}

	public String getAnswer() {
		if (answer == null) {
			answer = "";
		}
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSelected() {
		if (selected == null) {
			selected = "-1";
		}
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
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

	public String getQuestion() {
		if (question == null) {
			question = "";
		}
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSubtypes() {
		if (subtypes == null) {
			subtypes = "";
		}
		return subtypes;
	}

	public void setSubtypes(String subtypes) {
		this.subtypes = subtypes;
	}

	public ArrayList<UnderstandItemBean> getItems() {
		if (items == null) {
			items = new ArrayList<UnderstandItemBean>();
		}
		return items;
	}

	public void setItems(ArrayList<UnderstandItemBean> items) {
		this.items = items;
	}

	public String getItemstype() {
		if (itemstype == null) {
			itemstype = "";
		}
		return itemstype;
	}

	public void setItemstype(String itemstype) {
		this.itemstype = itemstype;
	}

	public String getRelated_id() {
		if (related_id == null) {
			related_id = "";
		}
		return related_id;
	}

	public void setRelated_id(String related_id) {
		this.related_id = related_id;
	}

	public String getRelated_type() {
		if (related_type == null) {
			related_type = "";
		}
		return related_type;
	}

	public void setRelated_type(String related_type) {
		this.related_type = related_type;
	}

	@Override
	public String toString() {
		return "UnderstandQuestionBean [id=" + id + ", question=" + question
				+ ", subtypes=" + subtypes + ", items=" + items
				+ ", itemstype=" + itemstype + ", related_id=" + related_id
				+ ", related_type=" + related_type + ", selected=" + selected
				+ "]";
	}

}
