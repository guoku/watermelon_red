package com.ekwing.students.entity;

import java.io.Serializable;

public class ConfirmResultBean implements Serializable {
	private String grades; // 成绩
	private String title;
	private String total;
	private String right;
	private String error;
	private String get1;
	private String get2;
	private String p1;
	private String p2;
	private String p3;

	public String getGrades() {
		if (grades == null || "".equals(grades)) {
			grades = "0";
		}
		int parseFloat = 0;
		try {
			parseFloat = (int) Float.parseFloat(grades);
		} catch (Exception e) {
			parseFloat = 0;
		}
		return parseFloat + "";
	}

	public void setGrades(String grades) {
		this.grades = grades;
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

	public String getTotal() {
		if (total == null) {
			total = "";
		}
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
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

	public String getError() {
		if (error == null) {
			error = "";
		}
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getGet1() {
		if (get1 == null || "".equals(get1)) {
			get1 = "0";
		}
		return get1;
	}

	public void setGet1(String get1) {
		this.get1 = get1;
	}

	public String getGet2() {
		if (get2 == null || "".equals(get2)) {
			get2 = "0";
		}
		return get2;
	}

	public void setGet2(String get2) {
		this.get2 = get2;
	}

	public int getP1() {
		try {
			if (p1 == null || "".equals(p1)) {
				return 1;
			}
			return Integer.parseInt(p1);
		} catch (Exception e) {
			return 1;
		}
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public int getP2() {
		try {
			if (p2 == null || "".equals(p2)) {
				return 1;
			}
			return Integer.parseInt(p2);
		} catch (Exception e) {
			return 1;
		}
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public int getP3() {
		try {
			if (p3 == null || "".equals(p3)) {
				return 1;
			}
			return Integer.parseInt(p3);
		} catch (Exception e) {
			return 1;
		}
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	@Override
	public String toString() {
		return "ConfirmResultBean [grades=" + grades + ", title=" + title
				+ ", total=" + total + ", right=" + right + ", error=" + error
				+ ", get1=" + get1 + ", get2=" + get2 + ", p1=" + p1 + ", p2="
				+ p2 + ", p3=" + p3 + "]";
	}

}
