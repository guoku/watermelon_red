package com.ekwing.students.entity;

import java.io.Serializable;

public class SBCDetailsBean implements Serializable {

	private String chars;
	private String score;
	private String start;
	private String end;
	private String dur;
	private String fluency;
	private String stressref;
	private String stressscore;
	private String toneref;
	private String tonescore;
	private String senseref;
	private String sensescore;
	private String text;

	public String getText() {
		if (text == null) {
			text = "";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStart() {
		if (start == null || "".equals(start)) {
			start = "0";
		}
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		if (end == null || "".equals(end)) {
			end = "0";
		}
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getDur() {
		if (dur == null || "".equals(dur)) {
			dur = "0";
		}
		return dur;
	}

	public void setDur(String dur) {
		this.dur = dur;
	}

	public String getFluency() {
		return fluency;
	}

	public void setFluency(String fluency) {
		this.fluency = fluency;
	}

	public String getStressref() {
		return stressref;
	}

	public void setStressref(String stressref) {
		this.stressref = stressref;
	}

	public String getStressscore() {
		return stressscore;
	}

	public void setStressscore(String stressscore) {
		this.stressscore = stressscore;
	}

	public String getToneref() {
		return toneref;
	}

	public void setToneref(String toneref) {
		this.toneref = toneref;
	}

	public String getTonescore() {
		return tonescore;
	}

	public void setTonescore(String tonescore) {
		this.tonescore = tonescore;
	}

	public String getSenseref() {
		return senseref;
	}

	public void setSenseref(String senseref) {
		this.senseref = senseref;
	}

	public String getSensescore() {
		return sensescore;
	}

	public void setSensescore(String sensescore) {
		this.sensescore = sensescore;
	}

	public String getChars() {
		if (chars == null) {
			chars = "";
		}
		return chars;
	}

	public void setChars(String chars) {
		this.chars = chars;
	}

	public String getScore() {
		if (score == null || "".equals(score)) {
			score = "0";
		}
		int parseScore = 0;
		try {
			parseScore = (int) Float.parseFloat(score);
		} catch (Exception e) {
			parseScore = 0;
		}
		return parseScore + "";
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "SBCDetailsBean [chars=" + chars + ", score=" + score + ", start=" + start + ", end=" + end + ", dur=" + dur + ", fluency=" + fluency
				+ ", stressref=" + stressref + ", stressscore=" + stressscore + ", toneref=" + toneref + ", tonescore=" + tonescore + ", senseref="
				+ senseref + ", sensescore=" + sensescore + ", text=" + text + "]";
	}

	public SBCDetailsBean(String chars, String score) {
		super();
		this.chars = chars;
		this.score = score;
	}

	public SBCDetailsBean() {
		super();
	}

}
