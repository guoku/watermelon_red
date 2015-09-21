package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ReadUnderstandBean implements Serializable {
	private String id; // 文章id
	private String units_id; // 单元id
	private String content; // 文章内容 html方式
	private String pics; // 图片url
	private String words; // 重点词汇
	private String types; // 文章篇 A篇
	private String word_num; // 单词数量
	private String key_word_num; // 重点词个数
	private String title; // 文章标题
	private ArrayList<UnderstandQuestionBean> question; // 问题

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getContent() {
		if (content == null) {
			content = "";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPics() {
		if (pics == null) {
			pics = "";
		}
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public String getWords() {
		if (words == null) {
			words = "";
		}
		return words;
	}

	public void setWords(String words) {
		this.words = words;
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

	public String getWord_num() {
		if (word_num == null) {
			word_num = "";
		}
		return word_num;
	}

	public void setWord_num(String word_num) {
		this.word_num = word_num;
	}

	public String getKey_word_num() {
		if (key_word_num == null) {
			key_word_num = "";
		}
		return key_word_num;
	}

	public void setKey_word_num(String key_word_num) {
		this.key_word_num = key_word_num;
	}

	public String getTitle() {
		if (title == null) {
			title = "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<UnderstandQuestionBean> getQuestion() {
		if (question == null || "".equals(question)) {
			question = new ArrayList<UnderstandQuestionBean>();
		}
		return question;
	}

	public void setQuestion(ArrayList<UnderstandQuestionBean> question) {
		this.question = question;
	}

}
