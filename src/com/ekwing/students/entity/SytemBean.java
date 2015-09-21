package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SytemBean implements Serializable {
	private String id;
	private String ask; // 大题标题
	private String audio; // 音频对应 内容
	// private ArrayList<OtherBean> others; // 图片 或 文字
	private OtherBean others; // 图片 或 文字
	private String times; // 播放时长 从开始
	private String content;

	public String getContent() {
		if (content == null) {
			content = "";
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getAsk() {
		if (ask == null) {
			ask = "";
		}
		return ask;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public String getAudio() {
		if (audio == null) {
			audio = "";
		}
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	// public OtherBean getOthers() {
	// return others;
	// }
	//
	// public void setOthers(OtherBean others) {
	// if ("".equals(others)) {
	// others = new OtherBean();
	// }
	// this.others = others;
	// }

	public String getTimes() {
		if (times == null || "".equals(times)) {
			times = "0";
		}
		return times;
	}

	// public ArrayList<OtherBean> getOthers() {
	// if (others == null || "".equals(others)) {
	// others = new ArrayList<OtherBean>();
	// }
	// return others;
	// }
	//
	// public void setOthers(ArrayList<OtherBean> others) {
	// this.others = others;
	// }

	public void setTimes(String times) {
		this.times = times;
	}

}
