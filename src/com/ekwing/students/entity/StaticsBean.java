package com.ekwing.students.entity;

import java.io.Serializable;

public class StaticsBean implements Serializable {
	private String chars;
	private String count;
	private String score;

	public StaticsBean(String chars, String count, String score) {
		super();
		this.chars = chars;
		this.count = count;
		this.score = score;
	}

	public StaticsBean() {
		super();
	}

	public String getChars() {
		return chars;
	}

	public void setChars(String chars) {
		this.chars = chars;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "StaticsBean [chars=" + chars + ", count=" + count + ", score="
				+ score + "]";
	}

}
