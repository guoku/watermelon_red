package com.ekwing.students.entity;

import com.lidroid.xutils.db.annotation.Id;

public class SelectBean {
	@Id
	private String themeid; // 获取内容入参ID
	private String title; // 外研”/”个人情况”,
	private String status; // ”0”/”1”,是否完成
	private String progress; // //未完成下百分比，
	private String type; // 1上册 2 下册 0 全册 EX 扩展教材
	private String classNum; // 6/7/8/9 //年级
	private String picName; // book_1 --- 999/topic_1 // 对应关系 3.1.1.1 已列出。
	private String other;
	private String other1;
	private String other2;

	public String getClassNum() {
		if (classNum == null) {
			classNum = "";
		}
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getPicName() {
		if (picName == null) {
			picName = "";
		}
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getOther() {
		if (other == null) {
			other = "";
		}
		return other;
	}

	public String getOther2() {
		if (other2 == null) {
			other2 = "";
		}
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getOther1() {
		if (other1 == null) {
			other1 = "";
		}
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
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

	public String getStatus() {
		if (status == null || "".equals(status)) {
			status = "0";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProgress() {
		int parsePro;
		if (progress == null || "".equals(progress)) {
			progress = "0";
		}
		try {
			parsePro = (int) Double.parseDouble(progress);
		} catch (Exception e) {
			parsePro = 0;
		}
		return parsePro + "";
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getThemeid() {
		if (themeid == null) {
			themeid = "";
		}
		return themeid;
	}

	public void setThemeid(String themeid) {
		this.themeid = themeid;
	}

	public String getType() {
		if (type == null) {
			type = "";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
