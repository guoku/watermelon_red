package com.ekwing.students.entity;

import java.io.Serializable;

public class ListenResultBean implements Serializable {
	private String rightnum;
	private String wrongnum;
	private String duration;
	private String s_score;
	private String score;
	private String title;

	public String getTitle() {
		if (title == null) {
			title = "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRightnum() {
		if (rightnum == null) {
			rightnum = "";
		}
		return rightnum;
	}

	public void setRightnum(String rightnum) {
		this.rightnum = rightnum;
	}

	public String getWrongnum() {
		if (wrongnum == null) {
			wrongnum = "";
		}
		return wrongnum;
	}

	public void setWrongnum(String wrongnum) {
		this.wrongnum = wrongnum;
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

	public String getS_score() {
		if (s_score == null) {
			s_score = "";
		}
		return s_score;
	}

	public void setS_score(String s_score) {
		this.s_score = s_score;
	}

	public String getScore() {
		if (score == null || "".equals(score)) {
			score = "";
		}
		int parseScore = 0;
		try {
			parseScore = (int) Double.parseDouble(score);
		} catch (Exception e) {
			parseScore = 0;
		}
		return parseScore + "";
	}

	public void setScore(String score) {
		this.score = score;
	}

}
