package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class RepeatNameBean implements Serializable {
	private String errlog;
	private String intent;
	private ArrayList<AccountBean> overname;

	public String getErrlog() {
		if (errlog == null) {
			errlog = "";
		}
		return errlog;
	}

	public void setErrlog(String errlog) {
		this.errlog = errlog;
	}

	public String getIntent() {
		if (intent == null) {
			intent = "";
		}
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public ArrayList<AccountBean> getOvername() {
		if (overname == null || "".equals(overname)) {
			overname = new ArrayList<AccountBean>();
		}
		return overname;
	}

	public void setOvername(ArrayList<AccountBean> overname) {
		this.overname = overname;
	}

}
