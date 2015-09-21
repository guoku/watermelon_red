package com.ekwing.students.entity;

import java.io.Serializable;

public class ReadBean implements Serializable {
	private String durationRead; // 持续时间阅读
	private String durationDo; // 答题时间
	private String evaluate; // 自我评价 1-4
	private String know;
	private String WPM;

	public String getDurationRead() {
		if (durationRead == null) {
			durationRead = "";
		}
		return durationRead;
	}

	public void setDurationRead(String durationRead) {
		this.durationRead = durationRead;
	}

	public String getDurationDo() {
		if (durationDo == null) {
			durationDo = "";
		}
		return durationDo;
	}

	public void setDurationDo(String durationDo) {
		this.durationDo = durationDo;
	}

	public String getEvaluate() {
		if (evaluate == null || "".equals(evaluate)) {
			evaluate = "1";
		}
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getKnow() {
		if (know == null) {
			know = "";
		}
		return know;
	}

	public void setKnow(String know) {
		this.know = know;
	}

	public String getWPM() {
		if (WPM == null) {
			WPM = "";
		}
		return WPM;
	}

	public void setWPM(String wPM) {
		WPM = wPM;
	}

}
