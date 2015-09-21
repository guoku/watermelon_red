package com.ekwing.students.entity;

import java.io.Serializable;

public class HWListBean implements Serializable {

	private String tid; // 具体某一项的id
	private String tname; // 标题
	private String tdescribe; // 作业描述
	private String tstatus; // 作业状态
	private String tscore; // 作业成绩

	public String getTid() {
		if (tid == null) {
			tid = "";
		}
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTname() {
		if (tname == null) {
			tname = "";
		}
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTdescribe() {
		if (tdescribe == null) {
			tdescribe = "";
		}
		return tdescribe;
	}

	public void setTdescribe(String tdescribe) {
		this.tdescribe = tdescribe;
	}

	public String getTstatus() {
		if (tstatus == null) {
			tstatus = "";
		}
		return tstatus;
	}

	public void setTstatus(String tstatus) {
		this.tstatus = tstatus;
	}

	public String getTscore() {
		if (tscore == null) {
			tscore = "";
		}
		return tscore;
	}

	public void setTscore(String tscore) {
		this.tscore = tscore;
	}

	public HWListBean() {
		super();
	}

}
