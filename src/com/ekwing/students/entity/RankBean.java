package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RankBean implements Serializable {
	private String myclasses;
	private String myschool;
	private String mycontry;

	private List<RankPartBean> classes = new ArrayList<RankPartBean>();
	private List<RankPartBean> school = new ArrayList<RankPartBean>();
	private List<RankPartBean> coutry = new ArrayList<RankPartBean>();

	@Override
	public String toString() {
		return "RankBean [myclasses=" + myclasses + ", myschool=" + myschool
				+ ", mycontry=" + mycontry + ", classes=" + classes
				+ ", school=" + school + ", coutry=" + coutry + "]";
	}

	public String getMyclasses() {
		if (myclasses == null) {
			myclasses = "";
		}
		return myclasses;
	}

	public void setMyclasses(String myclasses) {
		this.myclasses = myclasses;
	}

	public String getMyschool() {
		if (myschool == null) {
			myschool = "";
		}
		return myschool;
	}

	public void setMyschool(String myschool) {
		this.myschool = myschool;
	}

	public String getMycontry() {
		if (mycontry == null) {
			mycontry = "";
		}
		return mycontry;
	}

	public void setMycontry(String mycontry) {
		this.mycontry = mycontry;
	}

	public List<RankPartBean> getClasses() {
		if (classes == null || "".equals(classes)) {
			classes = new ArrayList<RankPartBean>();
		}
		return classes;
	}

	public void setClasses(List<RankPartBean> classes) {
		this.classes = classes;
	}

	public List<RankPartBean> getSchool() {
		if (school == null || "".equals(school)) {
			school = new ArrayList<RankPartBean>();
		}
		return school;
	}

	public void setSchool(List<RankPartBean> school) {
		this.school = school;
	}

	public List<RankPartBean> getCoutry() {
		if (coutry == null || "".equals(coutry)) {
			coutry = new ArrayList<RankPartBean>();
		}
		return coutry;
	}

	public void setCoutry(List<RankPartBean> coutry) {
		this.coutry = coutry;
	}

}
