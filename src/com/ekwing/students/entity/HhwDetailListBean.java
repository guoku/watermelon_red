package com.ekwing.students.entity;

import java.io.Serializable;

public class HhwDetailListBean implements Serializable{
	private boolean isDowned;//是否下载过
	private String tname;
	private String tid,ttype,tctype,tdescribe,tstatus,tscore,tscorenum;


	public boolean isDowned() {
		return isDowned;
	}

	public void setDowned(boolean isDowned) {
		this.isDowned = isDowned;
	}

	public String getTid() {
		if(tid ==null){
			tid="";
		}
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTtype() {
		if(ttype ==null){
			ttype="";
		}
		return ttype;
	}

	public void setTtype(String ttype) {
		this.ttype = ttype;
	}

	public String getTctype() {
		if(tctype ==null){
			tctype="";
		}
		return tctype;
	}

	public void setTctype(String tctype) {
		this.tctype = tctype;
	}

	public String getTdescribe() {
		if(tdescribe ==null){
			tdescribe="";
		}
		return tdescribe;
	}

	public void setTdescribe(String tdescribe) {
		this.tdescribe = tdescribe;
	}

	public String getTstatus() {
		if(tstatus ==null){
			tstatus="";
		}
		return tstatus;
	}

	public void setTstatus(String tstatus) {
		this.tstatus = tstatus;
	}

	public String getTscore() {
		if(tscore ==null){
			tscore="";
		}
		return tscore;
	}

	public void setTscore(String tscore) {
		this.tscore = tscore;
	}

	public String getTscorenum() {
		if(tscorenum ==null){
			tscorenum="";
		}
		return tscorenum;
	}

	public void setTscorenum(String tscorenum) {
		this.tscorenum = tscorenum;
	}

	public String getTname() {
		if(tname ==null){
			tname="";
		}
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}



}
