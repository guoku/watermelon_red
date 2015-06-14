package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author guoshasha
 * 作业详情列表数据的实体类
 */
public class HHWDetailsBean implements Serializable{
	private String hwId;//作业id
	private String hwStatus;//作业状态--1,完成,2未完成,3未下载
	private String  hwName;//作业名字
	private String hwDescribe;//作业描述
	private String hwLastTime;//时间
	private String teachetid;
	private String hwLeave;
	private String classScore;
	private String teacherComment;
	private String lastPrompt;
	private String Date;
	private List <HhwDetailListBean> hwList = new ArrayList<HhwDetailListBean>();//作业类型的标题
	
	public String getHwLastTime() {
		if(hwLastTime ==null){
			hwLastTime="";
		}
		return hwLastTime;
	}

	public void setHwLastTime(String hwLastTime) {
		this.hwLastTime = hwLastTime;
	}

	public String getTeachetid() {
		if(teachetid ==null){
			teachetid="";
		}
		return teachetid;
	}

	public void setTeachetid(String teachetid) {
		this.teachetid = teachetid;
	}

	public String getHwLeave() {
		if(hwLeave ==null){
			hwLeave="";
		}
		return hwLeave;
	}

	public void setHwLeave(String hwLeave) {
		this.hwLeave = hwLeave;
	}

	public String getClassScore() {
		if(classScore ==null){
			classScore="";
		}
		return classScore;
	}

	public void setClassScore(String classScore) {
		
		this.classScore = classScore;
	}

	public String getTeacherComment() {
		if(teacherComment ==null){
			teacherComment="";
		}
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}

	public String getLastPrompt() {
		if(lastPrompt ==null){
			lastPrompt="";
		}
		return lastPrompt;
	}

	public void setLastPrompt(String lastPrompt) {
		this.lastPrompt = lastPrompt;
	}

	public String getDate() {
		if(Date ==null){
			Date="";
		}
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getHwId() {
		if(hwId ==null){
			hwId="";
		}
		return hwId;
	}
	public void setHwId(String hwId) {
		this.hwId = hwId;
	}
	public String getHwStatus() {
		if(hwStatus ==null){
			hwStatus="";
		}
		return hwStatus;
	}
	public void setHwStatus(String hwStatus) {
		this.hwStatus = hwStatus;
	}
	public String getHwName() {
		if(hwName ==null){
			hwName="";
		}
		return hwName;
	}
	public void setHwName(String hwName) {
		this.hwName = hwName;
	}
	public String getHwDescribe() {
		if(hwDescribe ==null){
			hwDescribe="";
		}
		return hwDescribe;
	}
	public void setHwDescribe(String hwDescribe) {
		this.hwDescribe = hwDescribe;
	}
	public List<HhwDetailListBean> getHwList() {
		if(hwList == null || "".equals(hwList)){
			hwList = new ArrayList<HhwDetailListBean>();
		}
		return hwList;
	}
	public void setHwList(List<HhwDetailListBean> hwList) {
		this.hwList = hwList;
	}

}
