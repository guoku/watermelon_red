package com.ekwing.students.entity;

import java.io.Serializable;

public class ConContentBean implements Serializable {
	private String id;
	private String units_id;
	private String fiction_id;
	private String text;
	private String realtext;
	private String start;
	private String duration;
	private String translation;
	private String user_answer;
	private String end;
	private String record_duration;
	private String role;

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

	public String getFiction_id() {
		if (fiction_id == null) {
			fiction_id = "";
		}
		return fiction_id;
	}

	public void setFiction_id(String fiction_id) {
		this.fiction_id = fiction_id;
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

	public String getStart() {
		if (start == null || "".equals(start)) {
			start = "0";
		}
		int parseStart = 0;
		try {
			parseStart = (int) Float.parseFloat(start);
		} catch (Exception e) {
			parseStart = 0;
		}
		return parseStart + "";
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDuration() {
		if (duration == null || "".equals(duration)) {
			duration = "0";
		}
		int parsedur = 0;
		try {
			parsedur = (int) Float.parseFloat(duration);
		} catch (Exception e) {
			parsedur = 0;
		}
		return parsedur + "";
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

	public String getUser_answer() {
		if (user_answer == null) {
			user_answer = "";
		}
		return user_answer;
	}

	public void setUser_answer(String user_answer) {
		this.user_answer = user_answer;
	}

	public String getEnd() {
		if (end == null || "".equals(end)) {
			end = "0";
		}
		int parseEnd = 0;
		try {
			parseEnd = (int) Float.parseFloat(end);
		} catch (Exception e) {
			parseEnd = 0;
		}
		return parseEnd + "";
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getRecord_duration() {
		if (record_duration == null || "".equals(record_duration)) {
			record_duration = "0";
		}
		int parsedur = 0;
		try {
			parsedur = (int) Float.parseFloat(record_duration);
		} catch (Exception e) {
			parsedur = 0;
		}
		return parsedur + "";
	}

	public void setRecord_duration(String record_duration) {
		this.record_duration = record_duration;
	}

	public String getRole() {
		if (role == null) {
			role = "";
		}
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
