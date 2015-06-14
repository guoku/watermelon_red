package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListenBean implements Serializable {

	private String pid; // 听力部分id
	private String soundPath; // 听力原音地址
	private String partTitle; // part部分题目
	private List<ListItemBean> itemlist = new ArrayList<ListItemBean>();

	// private List<ListenUnderDetailBean> parLists = new
	// ArrayList<ListenUnderDetailBean>();

	// public List<ParentBean> getParLists() {
	// if(parLists==null){
	// parLists = new ArrayList<ParentBean>();
	// }
	// return parLists;
	// }
	//
	// public void setParLists(List<ParentBean> parLists) {
	// this.parLists = parLists;
	// }

	public String getPid() {
		if (pid == null) {
			pid = "";
		}
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSoundPath() {
		if (soundPath == null) {
			soundPath = "";
		}
		return soundPath;
	}

	public void setSoundPath(String soundPath) {
		this.soundPath = soundPath;
	}

	public String getPartTitle() {
		if (partTitle == null) {
			partTitle = "";
		}
		return partTitle;
	}

	public void setPartTitle(String partTitle) {
		this.partTitle = partTitle;
	}

	public List<ListItemBean> getItemlist() {
		if (itemlist == null || "".equals(itemlist)) {
			itemlist = new ArrayList<ListItemBean>();
		}
		return itemlist;
	}

	public void setItemlist(List<ListItemBean> itemlist) {
		this.itemlist = itemlist;
	}

	@Override
	public String toString() {
		return "ListenBean [pid=" + pid + ", soundPath=" + soundPath + ", partTitle=" + partTitle + ", itemlist=" + itemlist + "]";
	}

	private String id; // 作业 hwcid
	private String types;
	private String ctypes;
	private String unit_id; // 单元id
	private String duration;// 音频总时长
	private String count;// 小题数
	private String homework_id;
	private ArrayList<ListenContentsBean> contents = new ArrayList<ListenContentsBean>();

	public String getHomework_id() {
		if (homework_id == null) {
			homework_id = "";
		}
		return homework_id;
	}

	public void setHomework_id(String homework_id) {
		this.homework_id = homework_id;
	}

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypes() {
		if (types == null) {
			types = "";
		}
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getCtypes() {
		if (ctypes == null) {
			ctypes = "";
		}
		return ctypes;
	}

	public void setCtypes(String ctypes) {
		this.ctypes = ctypes;
	}

	public String getUnit_id() {
		if (unit_id == null) {
			unit_id = "";
		}
		return unit_id;
	}

	public void setUnit_id(String unit_id) {
		this.unit_id = unit_id;
	}

	public String getDuration() {
		if (duration == null || "".equals(duration)) {
			duration = "0";
		}
		int parDur = 0;
		try {
			parDur = (int) Double.parseDouble(duration);
		} catch (Exception e) {
			parDur = 0;
		}
		return parDur + "";
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCount() {
		if (count == null || "".equals(count)) {
			count = "0";
		}
		int parCount = 0;
		try {
			parCount = (int) Double.parseDouble(count);
		} catch (Exception e) {
			parCount = 0;
		}
		return parCount + "";
	}

	public void setCount(String count) {
		this.count = count;
	}

	public ArrayList<ListenContentsBean> getContents() {
		if (contents == null || "".equals(contents)) {
			contents = new ArrayList<ListenContentsBean>();
		}
		return contents;
	}

	public void setContents(ArrayList<ListenContentsBean> contents) {
		this.contents = contents;
	}

}
