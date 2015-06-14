package com.ekwing.students.entity;

import java.io.Serializable;

public class GridBean implements Serializable {

	private String type, name;
	private String netType;
	private int icon;

	public String getType() {
		if (type == null) {
			type = "";
		}
		return type;
	}

	public String getNetType() {
		if (netType == null) {
			netType = "";
		}
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public void setType(String type) {
		if(type ==null){
			type ="";
		}
		this.type = type;
	}

	public String getName() {
		if(name ==null){
			name ="";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
}
