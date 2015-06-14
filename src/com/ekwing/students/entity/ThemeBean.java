package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ThemeBean implements Serializable {
	private String levelid, levelStatus, levelave, levelUrl, levelPic, compid, dataid, attribute, localUrl;
	private String levelName; // 1/2/3/4/管卡数字
	private String isLocked; // 是否处于加锁状态 0、加锁 2、解锁
	private String exp; // 经验
	private String ed;// 翼豆
	private String key_word_num; // 重点单词数量
	private List<ThemeBean1> list;
	private String word_num; // 单词数量

	// public String getLocalUrl() {
	// if (localUrl == null) {
	// localUrl = "";
	// }
	// return localUrl;
	// }
	//
	// public void setLocalUrl(String localUrl) {
	// this.localUrl = localUrl;
	// }

	public String getAttribute() {
		if (attribute == null) {
			attribute = "";
		}
		return attribute;
	}

	public String getExp() {
		if (exp == null || "".equals(exp)) {
			exp = "0";
		}
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getEd() {
		if (ed == null || "".equals(ed)) {
			ed = "0";
		}
		return ed;
	}

	public void setEd(String ed) {
		this.ed = ed;
	}

	public String getKey_word_num() {
		if (key_word_num == null) {
			key_word_num = "";
		}
		return key_word_num;
	}

	public void setKey_word_num(String key_word_num) {
		this.key_word_num = key_word_num;
	}

	public String getWord_num() {
		if (word_num == null) {
			word_num = "";
		}
		return word_num;
	}

	public void setWord_num(String word_num) {
		this.word_num = word_num;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getIsLocked() {
		if (isLocked == null) {
			isLocked = "";
		}
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getLevelName() {
		if (levelName == null) {
			levelName = "";
		}
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public List<ThemeBean1> getList() {
		if (list == null || "".equals(list)) {
			list = new ArrayList<ThemeBean1>();
		}
		return list;
	}

	public void setList(List<ThemeBean1> list) {
		this.list = list;
	}

	public String getLevelid() {
		if (levelid == null) {
			levelid = "";
		}
		return levelid;
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

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}

	public String getLevelStatus() {
		if (levelStatus == null) {
			levelStatus = "";
		}
		return levelStatus;
	}

	public void setLevelStatus(String levelStatus) {
		this.levelStatus = levelStatus;
	}

	public String getLevelave() {
		if (levelave != null && levelave.equals("")) {
			return 0 + "";
		}
		return levelave;
	}

	public void setLevelave(String levelave) {
		this.levelave = levelave;
	}

	public String getLevelUrl() {
		if (levelUrl == null) {
			levelUrl = "";
		}
		return levelUrl;
	}

	public void setLevelUrl(String levelUrl) {
		this.levelUrl = levelUrl;
	}

	public String getLevelPic() {
		if (levelPic == null) {
			levelPic = "";
		}
		return levelPic;
	}

	public void setLevelPic(String levelPic) {
		this.levelPic = levelPic;
	}

	@Override
	public String toString() {
		return "ThemeBean [levelName=" + levelName + ", list=" + list + "]";
	}

}
