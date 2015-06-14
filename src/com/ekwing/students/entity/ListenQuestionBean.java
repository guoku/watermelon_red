package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ListenQuestionBean implements Serializable {
	private String id;
	private String question; // 问题
	private String types;
	private String subtypes;
	private ArrayList<QuestionItemBean> items = new ArrayList<QuestionItemBean>();
	private String itemstype;// 选项类型 text/img
	private String answer; // 答案
	private String analyz; // 解析
	private String related_type;
	private String selected; // 是否被选中
	private AnsBean submitAns;
	private String partId;
	private String content; // 听力原文
	private ArrayList<UserAnsBean> ans = new ArrayList<UserAnsBean>();
	private String audio; // 听力原音
	private String partPosition; // 大标题的position
	private String itemPositon; // 问题的position

	public String getPartPosition() {
		if (partPosition == null) {
			partPosition = "";
		}
		return partPosition;
	}

	public void setPartPosition(String partPosition) {
		this.partPosition = partPosition;
	}

	public String getItemPositon() {
		if (itemPositon == null) {
			itemPositon = "";
		}
		return itemPositon;
	}

	public void setItemPositon(String itemPositon) {
		this.itemPositon = itemPositon;
	}

	public String getAudio() {
		if (audio == null) {
			audio = "";
		}
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getContent() {
		if (content == null) {
			content = "";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<UserAnsBean> getAns() {
		if (ans == null || "".equals(ans)) {
			ans = new ArrayList<UserAnsBean>();
		}
		return ans;
	}

	public void setAns(ArrayList<UserAnsBean> ans) {
		this.ans = ans;
	}

	public AnsBean getSubmitAns() {
		return submitAns;
	}

	public void setSubmitAns(AnsBean submitAns) {
		this.submitAns = submitAns;
	}

	public String getPartId() {
		if (partId == null) {
			partId = "";
		}
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
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

	public String getTypes() {
		if (types == null) {
			types = "";
		}
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
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

	public ArrayList<QuestionItemBean> getItems() {
		if (items == null || "".equals(items)) {
			items = new ArrayList<QuestionItemBean>();
		}
		return items;
	}

	public void setItems(ArrayList<QuestionItemBean> items) {
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

	public String getAnswer() {
		if (answer == null) {
			answer = "";
		}
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public String getRelated_type() {
		if (related_type == null) {
			related_type = "";
		}
		return related_type;
	}

	public void setRelated_type(String related_type) {
		this.related_type = related_type;
	}
}
