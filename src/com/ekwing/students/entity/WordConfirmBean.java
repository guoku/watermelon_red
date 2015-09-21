package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WordConfirmBean implements Serializable {
	private String unitId;
	private String qtype;
	private String trans;
	private List<TestBean> list = new ArrayList<TestBean>();

	public String getUnitId() {
		if (unitId == null) {
			unitId = "";
		}
		return unitId;
	}

	public String getTrans() {
		if (trans == null) {
			trans = "";
		}
		return trans;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public List<TestBean> getList() {
		if (list == null || "".equals(list)) {
			list = new ArrayList<TestBean>();
		}
		return list;
	}

	public void setList(List<TestBean> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "WordConfirmBean [unitId=" + unitId + ", qtype=" + qtype
				+ ", trans=" + trans + ", list=" + list + "]";
	}

}
