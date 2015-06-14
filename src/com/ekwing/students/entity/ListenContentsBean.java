package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ListenContentsBean implements Serializable {
	private SytemBean stem;
	private ArrayList<ListenQuestionBean> questions = new ArrayList<ListenQuestionBean>();
	private String cn; // 题数、

	public SytemBean getStem() {
		return stem;
	}

	public void setStem(SytemBean stem) {
		this.stem = stem;
	}

	public ArrayList<ListenQuestionBean> getQuestions() {
		if (questions == null || "".equals(questions)) {
			questions = new ArrayList<ListenQuestionBean>();
		}
		return questions;
	}

	public void setQuestions(ArrayList<ListenQuestionBean> questions) {
		this.questions = questions;
	}

	public String getCn() {
		if (cn == null) {
			cn = "";
		}
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

}
