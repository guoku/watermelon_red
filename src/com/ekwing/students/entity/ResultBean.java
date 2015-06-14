package com.ekwing.students.entity;

import java.io.Serializable;

public class ResultBean implements Serializable {
	private ScoreDetailsBean scoreDetail;
	private ReadBean read;
	private ExpDetailBean expDetail;
	private String star;
	private String status;
	private String passStatus;
	private String addCredit;
	private String addExp;
	private String score;

	public String getScore() {
		if (score == null || "".equals(score)) {
			score = "0";
		}
		int parScore = 0;
		try {
			parScore = (int) Float.parseFloat(score);
		} catch (Exception e) {
			parScore = 0;
		}
		return parScore + "";
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStar() {
		if (star == null) {
			star = "";
		}
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getStatus() {
		if (status == null) {
			status = "";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassStatus() {
		if (passStatus == null) {
			passStatus = "";
		}
		return passStatus;
	}

	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}

	public String getAddCredit() {
		if (addCredit == null || "".equals(addCredit)) {
			addCredit = "0";
		}
		return addCredit;
	}

	public void setAddCredit(String addCredit) {
		this.addCredit = addCredit;
	}

	public String getAddExp() {
		if (addExp == null || "".equals(addExp)) {
			addExp = "0";
		}
		return addExp;
	}

	public void setAddExp(String addExp) {
		this.addExp = addExp;
	}

	public ScoreDetailsBean getScoreDetail() {
		return scoreDetail;
	}

	public void setScoreDetail(ScoreDetailsBean scoreDetail) {
		this.scoreDetail = scoreDetail;
	}

	public ReadBean getRead() {
		return read;
	}

	public void setRead(ReadBean read) {
		this.read = read;
	}

	public ExpDetailBean getExpDetail() {
		return expDetail;
	}

	public void setExpDetail(ExpDetailBean expDetail) {
		this.expDetail = expDetail;
	}

}
