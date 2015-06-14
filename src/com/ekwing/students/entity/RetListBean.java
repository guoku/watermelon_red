package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RetListBean implements Serializable {

	private String id;
	private String retext; // 单词或者句子
	private String chars; // 音标
	private String score; // 分数
	private List<ErrListBean> errlist = new ArrayList<ErrListBean>(); // 读错的音标

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRetext() {
		if (retext == null) {
			retext = "";
		}
		return retext;
	}

	public void setRetext(String retext) {
		this.retext = retext;
	}

	public String getChars() {
		if (chars == null) {
			chars = "";
		}
		return chars;
	}

	public void setChars(String chars) {
		this.chars = chars;
	}

	public String getScore() {
		if (score == null || "".equals(score)) {
			score = "0";
		}
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<ErrListBean> getErrlist() {
		if (errlist == null || "".equals(errlist)) {
			errlist = new ArrayList<ErrListBean>();
		}
		return errlist;
	}

	public void setErrlist(List<ErrListBean> errlist) {
		this.errlist = errlist;
	}

	@Override
	public String toString() {
		return "retListBean [id=" + id + ", retext=" + retext + ", chars=" + chars + ", score=" + score + ", errlist=" + errlist + "]";
	}

	public RetListBean(String id, String retext, String chars, String score, List<ErrListBean> errlist) {
		super();
		this.id = id;
		this.retext = retext;
		this.chars = chars;
		this.score = score;
		this.errlist = errlist;
	}

	public RetListBean() {
		super();
	}

}
