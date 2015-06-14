package com.ekwing.students.entity;

import java.io.Serializable;

public class ScoreDetailsBean implements Serializable {
	private String total;
	private String wrongNum;
	private String rightNum;
	private String score;
	private String qtype;
	private String title;
	// private String total;

	private int good; // 读音较好
	private int bad; // 读音稍差数
	private int total_word; // 单词总数
	private int fluency; // 流利度
	private int accuracy; // 准确度
	private int integrity; // 完整度

	public int getFluency() {
		if (fluency == 0) {
			fluency = 1;
		}
		return fluency;
	}

	public void setFluency(int fluency) {
		this.fluency = fluency;
	}

	public int getAccuracy() {
		if (accuracy == 0) {
			accuracy = 1;
		}
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getIntegrity() {
		if (integrity == 0) {
			integrity = 1;
		}
		return integrity;
	}

	public void setIntegrity(int integrity) {
		this.integrity = integrity;
	}

	public int getGood() {

		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getBad() {
		return bad;
	}

	public void setBad(int bad) {
		this.bad = bad;
	}

	public int getTotal_word() {
		return total_word;
	}

	public void setTotal_word(int total_word) {
		this.total_word = total_word;
	}

	public String getTotal() {
		if (total == null) {
			total = "";
		}
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getWrongNum() {
		if (wrongNum == null) {
			wrongNum = "";
		}
		return wrongNum;
	}

	public void setWrongNum(String wrongNum) {
		this.wrongNum = wrongNum;
	}

	public String getRightNum() {
		if (rightNum == null) {
			rightNum = "";
		}
		return rightNum;
	}

	public void setRightNum(String rightNum) {
		this.rightNum = rightNum;
	}

	public String getScore() {
		if (score == null || "".equals(score)) {
			score = "0";
		}
		int parScore = 0;
		try {
			parScore = (int) Float.parseFloat(score);
		} catch (Exception e) {
			// TODO: handle exception
			parScore = 0;
		}
		return parScore + "";
	}

	public void setScore(String score) {
		this.score = score;
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

	public String getTitle() {
		if (title == null) {
			title = "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
