package com.ekwing.students.entity;

public class NewTaskBean {
	private String expId; // 任务唯一标识
	private String exptitle; // 任务内容
	private String expAward; // 任务奖励----->魔豆
	private String expStatus; // 任务状态 0:未完成 1:已完成 2:已领取
	private String expLevel; // 任务经验 ----->经验
	private String impotContext; // 需要变色的部分

	public String getExpId() {
		if (expId == null) {
			expId = "";
		}
		return expId;
	}

	public String getExpLevel() {
		if (expLevel == null) {
			expLevel = "0";
		}
		return expLevel;
	}

	public void setExpLevel(String expLevel) {

		this.expLevel = expLevel;
	}

	public void setExpId(String expId) {
		this.expId = expId;
	}

	public String getExptitle() {
		if (exptitle == null) {
			exptitle = "";
		}
		return exptitle;
	}

	public void setExptitle(String exptitle) {
		this.exptitle = exptitle;
	}

	public String getExpAward() {
		if (expAward == null) {
			expAward = "0";
		}
		return expAward;
	}

	public void setExpAward(String expAward) {
		this.expAward = expAward;
	}

	public String getExpStatus() {
		if (expStatus == null) {
			expStatus = "";
		}
		return expStatus;
	}

	public void setExpStatus(String expStatus) {
		this.expStatus = expStatus;
	}

	public String getImpotContext() {
		if (impotContext == null) {
			impotContext = "";
		}
		return impotContext;
	}

	public void setImpotContext(String impotContext) {
		this.impotContext = impotContext;
	}

	@Override
	public String toString() {
		return "NewTaskBean [expId=" + expId + ", exptitle=" + exptitle
				+ ", expAward=" + expAward + ", expStatus=" + expStatus
				+ ", impotContext=" + impotContext + "]";
	}

}
