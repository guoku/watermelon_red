package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ekwing.students.config.Logger;

import android.util.Log;

public class SBCWordBean implements Serializable {
	private String pron;
	private String overall;
	private String accuracy;
	private String integrity;
	private String averageScore;
	private String fluencyoverall;
	private String fluencypause;
	private String fluencyspeed;
	private String rhythmoverall;
	private String rhythmsense;
	private String rhythmstress;
	private String rhythmtone;
	private ArrayList<String> errChars;
	private ArrayList<String> errStaticChars;
	private String staticsSum;
	private String ret; // sbc串
	private List<SBCDetailsBean> details = new ArrayList<SBCDetailsBean>();
	private List<StaticsBean> statics = new ArrayList<StaticsBean>();
	private String fluency;
	private String version;
	private String res;
	private String rank;
	private String precision;
	private String pretime;
	private String systime;
	private String vavetime;
	private String delaytime;
	private String url;
	private String id;
	private String text;

	@SuppressWarnings("unused")
	private SBCWordBean() {

	}

	public SBCWordBean(String url, String text, String id) {
		this.id = id;
		this.text = text;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFluency() {
		return fluency;
	}

	public void setFluency(String fluency) {
		this.fluency = fluency;
	}

	public String getVersion() {
		return version;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getPretime() {
		return pretime;
	}

	public void setPretime(String pretime) {
		this.pretime = pretime;
	}

	public String getSystime() {
		return systime;
	}

	public void setSystime(String systime) {
		this.systime = systime;
	}

	public String getVavetime() {
		return vavetime;
	}

	public void setVavetime(String vavetime) {
		this.vavetime = vavetime;
	}

	public String getDelaytime() {
		return delaytime;
	}

	public void setDelaytime(String delaytime) {
		this.delaytime = delaytime;
	}

	public String getStaticsSum() {
		if (staticsSum == null) {
			staticsSum = "";
		}
		return staticsSum;
	}

	public void setStaticsSum(String staticsSum) {
		this.staticsSum = staticsSum;
	}

	public ArrayList<String> getErrStaticChars() {
		if (errStaticChars != null && errStaticChars.size() == 0) {
			return null;
		}
		return errStaticChars;
	}

	public void setErrStaticChars(ArrayList<String> errStaticChars) {
		this.errStaticChars = errStaticChars;
	}

	public ArrayList<String> getErrChars() {
		return errChars;
	}

	public void setErrChars(ArrayList<String> errChars) {
		this.errChars = errChars;
	}

	public String getFluencyoverall() {
		if (fluencyoverall == null || "".equals(fluencyoverall)) {
			fluencyoverall = "0";
		}
		int parflu = 0;
		try {
			parflu = (int) Double.parseDouble(fluencyoverall);
		} catch (Exception e) {
			parflu = 0;
		}
		return parflu + "";
	}

	public void setFluencyoverall(String fluencyoverall) {
		this.fluencyoverall = fluencyoverall;
	}

	public String getFluencypause() {
		return fluencypause;
	}

	public void setFluencypause(String fluencypause) {
		this.fluencypause = fluencypause;
	}

	public String getFluencyspeed() {
		return fluencyspeed;
	}

	public void setFluencyspeed(String fluencyspeed) {
		this.fluencyspeed = fluencyspeed;
	}

	public String getRhythmoverall() {
		return rhythmoverall;
	}

	public void setRhythmoverall(String rhythmoverall) {
		this.rhythmoverall = rhythmoverall;
	}

	public String getRhythmsense() {
		return rhythmsense;
	}

	public void setRhythmsense(String rhythmsense) {
		this.rhythmsense = rhythmsense;
	}

	public String getRhythmstress() {
		return rhythmstress;
	}

	public void setRhythmstress(String rhythmstress) {
		this.rhythmstress = rhythmstress;
	}

	public String getRhythmtone() {
		return rhythmtone;
	}

	public void setRhythmtone(String rhythmtone) {
		this.rhythmtone = rhythmtone;
	}

	public String getPron() {
		return pron;
	}

	public void setPron(String pron) {
		this.pron = pron;
	}

	public String getAverageScore() {
		double parseInt = 0;
		try {
			if (averageScore == null || "".equals(averageScore)) {
				averageScore = "0";
			}
			parseInt = Double.parseDouble(averageScore);
		} catch (Exception e) {
			parseInt = 0;
		}
		return (int) parseInt + "";
	}

	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
	}

	public String getOverall() {
		if (overall == null || "".equals(overall)) {
			overall = "0";
		}
		int parOver = 0;
		try {
			parOver = (int) Double.parseDouble(overall);
		} catch (Exception e) {
			parOver = 0;
		}
		// if (overall != null) {
		// if (overall.contains(".")) {
		// String[] split = overall.split("\\.");
		// Logger.e("split", "split-------------->" + split.length);
		// Logger.e("split", "split-------------->" + split[0]);
		// return split[0];
		// }
		// } else {
		// overall = "0";
		// }
		return parOver + "";
	}

	public void setOverall(String overall) {
		this.overall = overall;
	}

	public String getAccuracy() {
		if (accuracy == null || "".equals(accuracy)) {
			accuracy = "0";
		}
		int parInt = 0;
		try {
			parInt = (int) Double.parseDouble(accuracy);
		} catch (Exception e) {
			parInt = 0;
		}
		return parInt + "";
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getIntegrity() {
		if (integrity == null || "".equals(integrity)) {
			integrity = "0";
		}
		int parInt = 0;
		try {
			parInt = (int) Double.parseDouble(integrity);
		} catch (Exception e) {
			parInt = 0;
		}
		return parInt + "";
	}

	public void setIntegrity(String integrity) {
		this.integrity = integrity;
	}

	public List<SBCDetailsBean> getDetails() {
		if (details == null || "".equals(details)) {
			details = new ArrayList<SBCDetailsBean>();
		}
		return details;
	}

	public void setDetails(List<SBCDetailsBean> details) {
		this.details = details;
	}

	public List<StaticsBean> getStatics() {
		if (statics == null || "".equals(statics)) {
			statics = new ArrayList<StaticsBean>();
		}
		return statics;
	}

	public void setStatics(List<StaticsBean> statics) {
		this.statics = statics;
	}

	@Override
	public String toString() {
		return " [pron=" + pron + ", overall=" + overall + ", accuracy=" + accuracy + ", integrity=" + integrity + ", averageScore=" + averageScore
				+ ", fluencyoverall=" + fluencyoverall + ", fluencypause=" + fluencypause + ", fluencyspeed=" + fluencyspeed + ", rhythmoverall="
				+ rhythmoverall + ", rhythmsense=" + rhythmsense + ", rhythmstress=" + rhythmstress + ", rhythmtone=" + rhythmtone + ", errChars="
				+ errChars + ", errStaticChars=" + errStaticChars + ", staticsSum=" + staticsSum + ", ret=" + ret + ", details=" + details
				+ ", statics=" + statics + ", fluency=" + fluency + ", version=" + version + ", res=" + res + ", rank=" + rank + ", precision="
				+ precision + ", pretime=" + pretime + ", systime=" + systime + ", vavetime=" + vavetime + ", delaytime=" + delaytime + ", url="
				+ url + ", id=" + id + ", text=" + text + "]";
	}

	// public SBCWordBean(String pron, String overall, String accuracy, String
	// integrity, String averageScore, String fluencyoverall,
	// String fluencypause, String fluencyspeed, String rhythmoverall, String
	// rhythmsense, String rhythmstress, String rhythmtone,
	// ArrayList<String> errChars, ArrayList<String> errStaticChars,
	// List<SBCDetailsBean> details, List<StaticsBean> statics) {
	// super();
	// this.pron = pron;
	// this.overall = overall;
	// this.accuracy = accuracy;
	// this.integrity = integrity;
	// this.averageScore = averageScore;
	// this.fluencyoverall = fluencyoverall;
	// this.fluencypause = fluencypause;
	// this.fluencyspeed = fluencyspeed;
	// this.rhythmoverall = rhythmoverall;
	// this.rhythmsense = rhythmsense;
	// this.rhythmstress = rhythmstress;
	// this.rhythmtone = rhythmtone;
	// this.errChars = errChars;
	// this.errStaticChars = errStaticChars;
	// this.details = details;
	// this.statics = statics;
	// }
	//
	// public SBCWordBean() {
	// super();
	// }

}
