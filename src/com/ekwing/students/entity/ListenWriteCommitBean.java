package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ListenWriteCommitBean implements Serializable {
	private String id; // 课文id
	private String units_id; // 单元id
	private ArrayList<CommitDetailBean> detail;

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

	public ArrayList<CommitDetailBean> getDetail() {
		if (detail == null || "".equals(detail)) {
			detail = new ArrayList<CommitDetailBean>();
		}
		return detail;
	}

	public void setDetail(ArrayList<CommitDetailBean> detail) {
		this.detail = detail;
	}

}
