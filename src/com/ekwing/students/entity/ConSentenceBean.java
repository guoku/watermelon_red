package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ConSentenceBean implements Serializable {
	private String id;
	private String title;
	private String units_id;
	private String content;
	private String realTxt;
	private String audio;
	private String duration;
	private String translation;
	private String paragraph;
	private String word_num;
	private String sentence_num;
	private String is_translation;
	private String qtype;
	private ArrayList<ConContentBean> sentence = new ArrayList<ConContentBean>();
	private ArrayList<ConContentBean> detail = new ArrayList<ConContentBean>();

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<ConContentBean> getDetail() {
		if (detail == null || "".equals(detail)) {
			detail = new ArrayList<ConContentBean>();
		}
		return detail;
	}

	public void setDetail(ArrayList<ConContentBean> detail) {
		this.detail = detail;
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

	public String getRealTxt() {
		if (realTxt == null) {
			realTxt = "";
		}
		return realTxt;
	}

	public void setRealTxt(String realTxt) {
		this.realTxt = realTxt;
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

	public String getDuration() {
		if (duration == null && "".equals(duration)) {
			duration = "0";
		}
		int parDur = 0;
		try {
			parDur = (int) Float.parseFloat(duration);
		} catch (Exception e) {
			parDur = 0;
		}
		return parDur + "";
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTranslation() {
		if (translation == null) {
			translation = "";
		}
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

	public String getParagraph() {
		if (paragraph == null) {
			paragraph = "";
		}
		return paragraph;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
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

	public String getSentence_num() {
		if (sentence_num == null) {
			sentence_num = "";
		}
		return sentence_num;
	}

	public void setSentence_num(String sentence_num) {
		this.sentence_num = sentence_num;
	}

	public String getIs_translation() {
		if (is_translation == null) {
			is_translation = "";
		}
		return is_translation;
	}

	public void setIs_translation(String is_translation) {
		this.is_translation = is_translation;
	}

	public String getQtype() {
		if (qtype == null) {
			qtype = "";
		}
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	public ArrayList<ConContentBean> getSentence() {
		if (sentence == null || "".equals(sentence)) {
			sentence = new ArrayList<ConContentBean>();
		}
		return sentence;
	}

	public void setSentence(ArrayList<ConContentBean> sentence) {
		this.sentence = sentence;
	}

}
