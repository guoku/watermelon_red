package com.ekwing.students.entity;

import java.util.ArrayList;

/**
 * 阅读理解要提交的answer
 * 
 * @author ytbnkkf
 * 
 */
public class UnderCommitBean {
	private String word_num;
	private String key_word_num;
	private String units_id;
	private String id;
	private ArrayList<UnderCommitDetailBean> detail;

	public String getWord_num() {
		return word_num;
	}

	public void setWord_num(String word_num) {
		this.word_num = word_num;
	}

	public String getKey_word_num() {
		return key_word_num;
	}

	public void setKey_word_num(String key_word_num) {
		this.key_word_num = key_word_num;
	}

	public String getUnits_id() {
		return units_id;
	}

	public void setUnits_id(String units_id) {
		this.units_id = units_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<UnderCommitDetailBean> getDetail() {
		if (detail == null && "".equals(detail)) {
			detail = new ArrayList<UnderCommitDetailBean>();
		}
		return detail;
	}

	public void setDetail(ArrayList<UnderCommitDetailBean> detail) {
		this.detail = detail;
	}

}
