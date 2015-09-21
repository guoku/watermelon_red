package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HWIndexBean implements Serializable {

	private String hwId; // 作业id
	private String teachetid; // 老师的id
	private String hwStatus; // 作业状态--1,完成, 2未完成, 3未下载
	private String proHwStatus;
	private String hwName; // 作业名字
	private String hwDescribe; // 作业描述
	private String hwLeave; // 作业留言
	private String hwLastTime; // 作业最晚提交时间戳
	private String teacherComment; // 教师评语
	private String classScore; // 教师评语
	private String lastPrompt; // 教师评语
	private List<HWListBean> hwList = new ArrayList<HWListBean>(); // 具的每一项作业
	private List<DownUrlBean> downUrl = new ArrayList<DownUrlBean>(); // 具体每一项要下载作业的url

	public String getProHwStatus() {
		if (proHwStatus == null) {
			proHwStatus = "";
		}
		return proHwStatus;
	}

	public void setProHwStatus(String proHwStatus) {
		this.proHwStatus = proHwStatus;
	}

	public String getHwId() {
		if (hwId == null) {
			hwId = "";
		}
		return hwId;
	}

	public String getTeachetid() {
		if (teachetid == null) {
			teachetid = "";
		}
		return teachetid;
	}

	public void setTeachetid(String teachetid) {
		this.teachetid = teachetid;
	}

	public void setHwId(String hwId) {
		this.hwId = hwId;
	}

	public String getHwStatus() {
		if (hwStatus == null) {
			hwStatus = "";
		}
		return hwStatus;
	}

	public void setHwStatus(String hwStatus) {
		// if (hwStatus != null && !"3".equals(hwStatus)) {
		// setProHwStatus(hwStatus);
		// }
		this.hwStatus = hwStatus;
	}

	public String getHwName() {
		if (hwName == null) {
			hwName = "";
		} else if (hwName.length() > 10) {
			hwName.substring(0, 10);
		}
		return hwName;
	}

	public void setHwName(String hwName) {
		this.hwName = hwName;
	}

	public String getHwDescribe() {
		if (hwDescribe == null) {
			hwDescribe = "";
		}
		return hwDescribe;
	}

	public void setHwDescribe(String hwDescribe) {
		this.hwDescribe = hwDescribe;
	}

	public String getHwLeave() {
		if (hwLeave == null) {
			hwLeave = "";
		}
		return hwLeave;
	}

	public void setHwLeave(String hwLeave) {
		this.hwLeave = hwLeave;
	}

	public String getHwLastTime() {
		if (hwLastTime == null || "".equals(hwLastTime)) {
			hwLastTime = "0";
		}
		int lasttime = 0;
		try {
			lasttime = (int) Double.parseDouble(hwLastTime);
		} catch (Exception e) {
			// TODO: handle exception
			lasttime = 0;
		}
		return lasttime + "";
	}

	public void setHwLastTime(String hwLastTime) {
		this.hwLastTime = hwLastTime;
	}

	public String getTeacherComment() {
		if (teacherComment == null) {
			teacherComment = "";
		}
		return teacherComment;
	}

	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}

	public String getClassScore() {
		if (classScore == null) {
			classScore = "C";
		}
		return classScore;
	}

	public void setClassScore(String classScore) {
		this.classScore = classScore;
	}

	public String getLastPrompt() {
		if (lastPrompt == null) {
			lastPrompt = "";
		}
		return lastPrompt;
	}

	public void setLastPrompt(String lastPrompt) {
		this.lastPrompt = lastPrompt;
	}

	public List<HWListBean> getHwList() {
		if (hwList == null || "".equals(hwList)) {
			hwList = new ArrayList<HWListBean>();
		}
		return hwList;
	}

	public void setHwList(List<HWListBean> hwList) {
		this.hwList = hwList;
	}

	public List<DownUrlBean> getDownUrl() {
		if (downUrl == null || "".equals(downUrl)) {
			downUrl = new ArrayList<DownUrlBean>();
		}
		return downUrl;
	}

	public void setDownUrl(List<DownUrlBean> downUrl) {
		this.downUrl = downUrl;
	}

	public HWIndexBean() {
		super();
	}

	@Override
	public String toString() {
		return "HWIndexBean [hwId=" + hwId + ", teachetid=" + teachetid
				+ ", hwStatus=" + hwStatus + ", hwName=" + hwName
				+ ", hwDescribe=" + hwDescribe + ", hwLeave=" + hwLeave
				+ ", hwLastTime=" + hwLastTime + ", teacherComment="
				+ teacherComment + ", classScore=" + classScore
				+ ", lastPrompt=" + lastPrompt + ", hwList=" + hwList
				+ ", downUrl=" + downUrl + "]";
	}

}
