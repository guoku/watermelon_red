package com.ekwing.students.entity;

import java.io.Serializable;

public class ExpDetailBean implements Serializable {
	private String add;
	private String level;
	private String current;
	private String nextLevel;

	public String getAdd() {
		if (add == null) {
			add = "";
		}
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getLevel() {
		if (level == null) {
			level = "";
		}
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCurrent() {
		if (current == null) {
			current = "";
		}
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getNextLevel() {
		if (nextLevel == null) {
			nextLevel = "";
		}
		return nextLevel;
	}

	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}

}
