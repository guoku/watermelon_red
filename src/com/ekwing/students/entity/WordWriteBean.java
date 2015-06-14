package com.ekwing.students.entity;

import java.io.Serializable;

/**
 * 单词听写的bean
 * 
 * @author ytbnkkf
 * 
 */
public class WordWriteBean implements Serializable {
	private String id;
	private String book_content_id; // 文章内容id
	private String units_id; // 单元id
	private String text; // 单词
	private String realtext; // 提交给SBC的单词、
	private String audio; // 音频地址
	private String start; // 原音播放开始时间
	private String duration; // 原音播放持续时间
	private String translation; // 单词解释
	private String sentence; // 例句
	private String important; //
	private String word_type; // 单词词形（adj.)
	private String sentence_exp; // 例句解释
	private String show;
	private String ext; // 扩展用
	private String wnums;
	private String dataId; // 课文id

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataId() {
		if (dataId == null) {
			dataId = "";
		}
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

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

	public String getText() {
		if (text == null) {
			text = "";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRealtext() {
		if (realtext == null) {
			realtext = "";
		}
		return realtext;
	}

	public void setRealtext(String realtext) {
		this.realtext = realtext;
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

	public String getStart() {
		if (start == null) {
			start = "0";
		}
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDuration() {
		if (duration == null) {
			duration = "0";
		}
		return duration;
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

	public String getSentence() {
		if (sentence == null) {
			sentence = "";
		}
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public String getImportant() {
		if (important == null) {
			important = "1";
		}
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	public String getWord_type() {
		if (word_type == null) {
			word_type = "";
		}
		return word_type;
	}

	public void setWord_type(String word_type) {
		this.word_type = word_type;
	}

	public String getSentence_exp() {
		if (sentence_exp == null) {
			sentence_exp = "";
		}
		return sentence_exp;
	}

	public void setSentence_exp(String sentence_exp) {
		this.sentence_exp = sentence_exp;
	}

	public String getShow() {
		if (show == null) {
			show = "1";
		}
		return show;
	}

	public void setShow(String show) {
		this.show = show;
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

	public String getWnums() {
		if (wnums == null) {
			wnums = "1";
		}
		return wnums;
	}

	public void setWnums(String wnums) {
		this.wnums = wnums;
	}

	@Override
	public String toString() {
		return "WordWriteBean [id=" + id + ", dataId=" + dataId + ", book_content_id=" + book_content_id + ", units_id=" + units_id + ", text="
				+ text + ", realtext=" + realtext + ", audio=" + audio + ", start=" + start + ", duration=" + duration + "]";
	}

}
