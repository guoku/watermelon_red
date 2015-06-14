package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReadEkwingBean implements Serializable {
	private String id; // 课文id
	private String units_id; // /单元 id
	private String title; // 课文名称
	private String part;
	private String activity;
	private String page;
	private String content;
	private String audio;
	private String duration;
	private String paragraph;
	private String key_word_num;
	private String word_num;
	private String ext;
	private String qtype;
	private String topic;
	private List<SentenceBean> sentence;

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

	public String getTitle() {
		if (title == null) {
			title = "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPart() {
		if (part == null) {
			part = "";
		}
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getActivity() {
		if (activity == null) {
			activity = "";
		}
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getPage() {
		if (page == null) {
			page = "";
		}
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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
		if (duration == null) {
			duration = "";
		}
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
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

	public String getKey_word_num() {
		if (key_word_num == null) {
			key_word_num = "";
		}
		return key_word_num;
	}

	public void setKey_word_num(String key_word_num) {
		this.key_word_num = key_word_num;
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

	public String getExt() {
		if (ext == null) {
			ext = "";
		}
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
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

	public String getTopic() {
		if (topic == null) {
			topic = "";
		}
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public List<SentenceBean> getSentence() {
		if (sentence == null || "".equals(sentence)) {
			sentence = new ArrayList<SentenceBean>();
		}
		return sentence;
	}

	public void setSentence(List<SentenceBean> sentence) {
		this.sentence = sentence;
	}

}
