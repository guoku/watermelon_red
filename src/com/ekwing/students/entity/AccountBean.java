package com.ekwing.students.entity;

import java.io.Serializable;

public class AccountBean implements Serializable {
	private String uid; // 学生id
	private String username; // "10271177" //学号
	private String type; // // 类型 1 教师 2学生 3家长
	private String classname; // "励志班" //班级名
	private String account;
	private String url;
	private String sex;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getType() {
		if (type == null) {
			type = "1";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
