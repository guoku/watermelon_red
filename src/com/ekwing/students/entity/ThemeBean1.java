package com.ekwing.students.entity;

import java.io.Serializable;

public class ThemeBean1 implements Serializable {
	@Override
	public String toString() {
		return "ThemeBean1 [levelid=" + levelid + ", levelStatus="
				+ levelStatus + ", levelave=" + levelave + ", levelUrl="
				+ levelUrl + ", levelPic=" + levelPic + ", compid=" + compid
				+ ", dataid=" + dataid + ", attribute=" + attribute + "]";
	}

	private String levelid, levelStatus, levelave, levelUrl, levelPic, compid,
			dataid;
	private String attribute, audioUrl, method;

	public String getMethod() {
		if (method == null) {
			method = "";
		}
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAudioUrl() {
		if (audioUrl == null) {
			audioUrl = "";
		}
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getLevelid() {
		if (levelid == null) {
			levelid = "";
		}
		return levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}

	public String getLevelStatus() {
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public String getLevelave() {
		if (levelave == null || "".equals(levelave)) {
			levelave = "0";
		}
		return levelave;
	}

	public void setLevelave(String levelave) {
		this.levelave = levelave;
	}

	public String getLevelUrl() {
		if (levelUrl == null || "".equals(levelUrl)) {
			levelUrl = "";
		}
		return levelUrl;
	}

	public void setLevelUrl(String levelUrl) {
		this.levelUrl = levelUrl;
	}

	public String getLevelPic() {
		return levelPic;
	}

	public void setLevelPic(String levelPic) {
		this.levelPic = levelPic;
	}

	public String getCompid() {
		if (compid == null) {
			compid = "";
		}
		return compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getDataid() {
		if (dataid == null) {
			dataid = "";
		}
		return dataid;
	}

	public void setDataid(String dataid) {
		this.dataid = dataid;
	}

}
