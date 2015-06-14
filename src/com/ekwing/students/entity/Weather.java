package com.ekwing.students.entity;

import java.io.Serializable;

/**
 * @author bu.xuesong
 */
public class Weather implements Serializable{
	private static final long serialVersionUID = 7890606891770073804L;
	private String img_name;//": "",
      private String desc;//": "��",
      private String temperature;//": "8��~-3��"
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
      
}
