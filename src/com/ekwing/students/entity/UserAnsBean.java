package com.ekwing.students.entity;

import java.io.Serializable;

public class UserAnsBean implements Serializable{
	private String correct;
	private String ans;
	private String right;

	public String getCorrect() {
		if (correct == null) {
			correct = "";
		}
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}

	public String getAns() {
		if (ans == null) {
			ans = "";
		}
		return ans;
	}

	public void setAns(String ans) {
		this.ans = ans;
	}

	public String getRight() {
		if (right == null) {
			right = "";
		}
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}
}
