package com.guoku.guokuv4.entity.test;

import java.io.Serializable;
import java.util.ArrayList;

public class TAB1Bean implements Serializable {
	private String status, category_count, group_id, title;
	private ArrayList<Tab2Bean> list1;
	private ArrayList<Tab2Bean> list2;

	public ArrayList<Tab2Bean> getList1() {
		return list1;
	}

	public void setList1(ArrayList<Tab2Bean> list1) {
		this.list1 = list1;
	}

	public ArrayList<Tab2Bean> getList2() {
		return list2;
	}

	public void setList2(ArrayList<Tab2Bean> list2) {
		this.list2 = list2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory_count() {
		return category_count;
	}

	public int getCount() {
		return Integer.parseInt(category_count);
	}

	public void setCategory_count(String category_count) {
		this.category_count = category_count;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "TAB1Bean [status=" + status + ", category_count="
				+ category_count + ", group_id=" + group_id + ", title="
				+ title + ", list1=" + list1 + ", list2=" + list2 + "]";
	}

}
