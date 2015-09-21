package com.ekwing.students.entity;

import java.io.Serializable;

public class SoundListBean implements Serializable {
	private String url;
	private String start;
	private String keep;

	public String getUrl() {
		if (url == null) {
			url = "";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStart() {
		if (start == null || "".equals(start)) {
			start = "0";
		}
		int parStart = 0;
		try {
			parStart = (int) Float.parseFloat(start);
		} catch (Exception e) {
			parStart = 0;
		}
		return parStart;
	}

	public int getKeep() {
		if (keep == null || "".equals(keep)) {
			keep = "0";
		}
		int parKeep = 0;
		try {
			parKeep = (int) Float.parseFloat(keep);
		} catch (Exception e) {
			parKeep = 0;
		}
		return parKeep;
	}

	public void setKeep(String keep) {
		this.keep = keep;
	}

	public void setStart(String start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "SoundListBean [url=" + url + ", start=" + start + ", keep="
				+ keep + "]";
	}

}
