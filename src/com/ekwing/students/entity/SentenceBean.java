package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.ekwing.students.utils.TextUtils;

public class SentenceBean implements Serializable {
	private String id;
	private String book_content_id;
	private String units_id;
	private String text;
	private String realtext;
	private String audio;
	private String start;
	private String duration;
	private String translation;
	private String sentence;
	private String important;
	private String word_type;
	private String sentence_exp;
	private String show;
	private String ext;
	private String record_duration;
	private boolean shows;
	private String reSoundPath;
	private String grades;
	private String answers;
	private SBCWordBean sbcResult;
	private String score;
	private ArrayList<ErrListBean> errLists;
	private String isSubmit;

	public String getIsSubmit() {
		if (isSubmit == null || "".equals(isSubmit)) {
			isSubmit = "0";
		}
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	public ArrayList<ErrListBean> getErrLists() {
		if (errLists == null || "".equals(errLists)) {
			errLists = new ArrayList<ErrListBean>();
		}
		return errLists;
	}

	public void setErrLists(ArrayList<ErrListBean> errLists) {

		this.errLists = errLists;
	}

	public String getScore() {
		if (score == null) {
			score = "0";
		}
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public SBCWordBean getSbcResult() {
		if (sbcResult == null) {
			return new SBCWordBean("", "", "");
		}
		return sbcResult;
	}

	public void setSbcResult(SBCWordBean sbcResult) {
		this.sbcResult = sbcResult;
	}

	public String getAnswers() {
		if (answers == null) {
			return "{\"id\":\"" + id + "\",\"text\":\"" + TextUtils.replaceBlank(getText()) + "\",\"score\":\"" + -1 + "\",\"speed\":\"" + 1
					+ "\",\"difficulty\":0}";
		}
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}

	public boolean isShows() {
		return shows;
	}

	public void setShows(boolean shows) {
		this.shows = shows;
	}

	public String getReSoundPath() {
		if (reSoundPath == null) {
			reSoundPath = "";
		}
		return reSoundPath;
	}

	public void setReSoundPath(String reSoundPath) {
		this.reSoundPath = reSoundPath;
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
		if (start == null || "".equals(start)) {
			start = "0";
		}
		int parStart = 0;
		try {
			parStart = (int) Float.parseFloat(start);
		} catch (Exception e) {
			parStart = 0;
		}
		return parStart + "";
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDuration() {
		if (duration == null || "".equals(duration)) {
			duration = "0";
		}
		int parseFloat = 0;
		try {
			parseFloat = (int) Float.parseFloat(duration);
		} catch (Exception e) {
			parseFloat = 0;
		}
		return parseFloat + "";
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
			important = "";
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

	public int getRecord_duration() {
		if (record_duration == null || "".equals(record_duration)) {
			record_duration = "0";
		}
		int parseDouble = 0;
		try {
			parseDouble = (int) Double.parseDouble(record_duration);
		} catch (Exception e) {
			parseDouble = 0;
		}
		return parseDouble;
	}

	public void setRecord_duration(String record_duration) {
		this.record_duration = record_duration;
	}

}
