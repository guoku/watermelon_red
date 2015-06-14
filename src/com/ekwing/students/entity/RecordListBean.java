package com.ekwing.students.entity;

import java.io.Serializable;

public class RecordListBean implements Serializable {

	private String url;
	private String start;
	private String keep;
	private String id, tid;

	public String getId() {
		if(id==null){
			id ="";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTid() {
		if(tid ==null){
			tid ="";
		}
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStart() {
		if (start == null || "".equals(start)) {
			start = "0";
		}
		int parStart =0;
		try {
			parStart = (int) Float.parseFloat(start);
		} catch (Exception e) {
			parStart =0;
		}
		return parStart;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public int getKeep() {
		if (keep == null || "".equals(keep)) {
			keep = "0";
		}
		int parkeep=0;
		try {
			parkeep = (int) Float.parseFloat(keep);
		} catch (Exception e) {
			parkeep =0;
		}
		return parkeep;
	}

	public void setKeep(String keep) {
		this.keep = keep;
	}

	@Override
	public String toString() {
		return "RecordListBean [url=" + url + ", start=" + start + ", keep=" + keep + "]";
	}

	public RecordListBean() {
		super();
	}

}
