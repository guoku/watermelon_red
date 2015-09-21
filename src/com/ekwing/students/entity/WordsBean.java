package com.ekwing.students.entity;

import java.io.Serializable;

import com.ekwing.students.utils.TextUtils;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;

public class WordsBean implements Serializable {

	@Id
	private String id; // 题目标示
	@Column(column = "retext")
	private String retext; // 题目
	@Column(column = "soundPath")
	private String soundPath; // 元音地址
	@Column(column = "reSoundPath")
	private String reSoundPath; // 录音地址
	@Column(column = "dur")
	private String dur; // 最大录音时间
	@Column(column = "trans")
	private String trans; // 单词含义--------------》扩展数据，可无
	@Column(column = "expSend")
	private String expSend; // 例句---------------->扩展数据，可无
	@Column(column = "start")
	private String start; // 音频开始时间
	@Column(column = "keep")
	private String keep; // 音频持续时间
	private String qtype;
	private String isSubmit;
	private String tid; //

	private String ret; // SBC返回的json串

	public String getTid() {
		if (tid == null) {
			tid = "";
		}
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getIsSubmit() {
		if (isSubmit == null || "".equals(isSubmit)) {
			isSubmit = "-1";
		}
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		// if (!"1".equals(this.isSubmit)) {
		this.isSubmit = isSubmit;
		// }
	}

	public String getQtype() {
		if (qtype == null) {
			qtype = "";
		}
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
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

	public String getRetext() {
		if (retext == null) {
			retext = "";
		}
		return TextUtils.replaceBlank(retext);
	}

	public void setRetext(String retext) {
		this.retext = retext;
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

	public String getReSoundPath() {
		if (reSoundPath == null) {
			reSoundPath = "";
		}
		return reSoundPath;
	}

	public void setReSoundPath(String reSoundPath) {
		this.reSoundPath = reSoundPath;
	}

	public int getDur() {
		if (dur == null || "".equals(dur)) {
			dur = "0";
		}
		int parseDouble = (int) Double.parseDouble(dur);
		return parseDouble;
	}

	public void setDur(String dur) {
		this.dur = dur;
	}

	public int getStart() {
		if (start == null || "".equals(start)) {
			start = "0";
		}
		int parseDouble = 0;
		try {
			parseDouble = (int) Double.parseDouble(start);

		} catch (Exception e) {
			// TODO: handle exception
			parseDouble = 0;
		}
		return parseDouble;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public int getKeep() {
		if (keep == null || "".equals(keep)) {
			keep = "0";
		}
		int parseDouble = (int) Double.parseDouble(keep);
		return parseDouble;
	}

	public void setKeep(String keep) {
		this.keep = keep;
	}

	public String getTrans() {
		if (trans == null) {
			trans = "";
		}
		return trans;
	}

	public void setTrans(String trans) {
		this.trans = trans;
	}

	public String getExpSend() {
		if (expSend == null) {
			expSend = "";
		}
		return expSend;
	}

	public void setExpSend(String expSend) {
		this.expSend = expSend;
	}

	public String getRet() {
		if (ret == null) {
			return "{\"id\":\"" + id + "\",\"text\":\"" + getRetext()
					+ "\",\"score\":\"" + -1 + "\",\"details\":[]}";
		}
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	@Column(column = "SBCResult")
	private SBCWordBean SBCResult; // sbc结果页
	@Column(column = "isShow")
	private boolean isShow;
	@Column(column = "grade")
	private String grade; // 成绩
	@Column(column = "sendShow")
	private boolean sendShow;

	public SBCWordBean getSBCResult() {
		if (SBCResult == null) {
			return new SBCWordBean("", "", "");
		}
		return SBCResult;
	}

	public void setSBCResult(SBCWordBean sBCResult) {
		SBCResult = sBCResult;
	}

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public String getGrade() {
		// if(grade==null){
		// grade = "1";
		// }
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public boolean isSendShow() {
		return sendShow;
	}

	public void setSendShow(boolean sendShow) {
		this.sendShow = sendShow;
	}

	@Override
	public String toString() {
		return "WordsBean [id=" + id + ", retext=" + retext + ", soundPath="
				+ soundPath + ", reSoundPath=" + reSoundPath + ", dur=" + dur
				+ ", trans=" + trans + ", expSend=" + expSend + ", start="
				+ start + ", keep=" + keep + ", qtype=" + qtype + ", ret="
				+ ret + ", SBCResult=" + SBCResult + ", isShow=" + isShow
				+ ", grade=" + grade + ", sendShow=" + sendShow + "]";
	}

}
