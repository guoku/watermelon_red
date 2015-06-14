package com.ekwing.students.entity;

/**
 * 用户基本信息的bean
 * 
 * @author think
 * 
 */
public class UserLoginBean {
	private String uid; // 学生的id
	private String token; // token
	private String pmm; // 密码
	private String identity; // 身份

	public String getIdentity() {
		if (identity == null) {
			identity = "1";
		}
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPmm() {
		if (pmm == null) {
			pmm = "";
		}
		return pmm;
	}

	public void setPmm(String pmm) {
		this.pmm = pmm;
	}

	public String getUid() {
		if (uid == null) {
			return "";
		}
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		if (token == null) {
			return "";
		}
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserLoginBean [uid=" + uid + ", token=" + token + ", pmm=" + pmm + "]";
	}

}
