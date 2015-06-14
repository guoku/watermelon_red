package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class OtherBean implements Serializable{
	private String haveItem;  //是否为选项
	private ArrayList<HaveItemBean> list;
	public String getHaveItem() {
		if(haveItem==null || "".equals(haveItem)){
			haveItem = "0";
		}
		return haveItem;
	}
	public void setHaveItem(String haveItem) {
		this.haveItem = haveItem;
	}
	public ArrayList<HaveItemBean> getList() {
		if(list ==null ||"".equals(list)){
			list = new ArrayList<HaveItemBean>();
		}
		return list;
	}
	public void setList(ArrayList<HaveItemBean> list) {
		this.list = list;
	}
	
	
}
