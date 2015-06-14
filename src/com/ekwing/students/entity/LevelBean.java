package com.ekwing.students.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

public class LevelBean implements Serializable {
	@Id
	private String themeid;
	private String themeName, themeNameEng, themePic, totals, curProgress, levels, aveGread, themeStues, attribute, progress, count, locaUrl;
	private List<ThemeBean> themees;
	private String themeString;

	public String getThemeString() {
		if (themeString == null) {
			themeString = "";
		}
		return themeString;
	}

	public void setThemeString(String themeString) {
		this.themeString = themeString;
	}

	public String getLocaUrl() {
		if (locaUrl == null) {
			locaUrl = "";
		}
		return locaUrl;
	}

	public void setLocaUrl(String locaUrl) {
		this.locaUrl = locaUrl;
	}

	public String getProgress() {
		if (progress == null || "".equals(progress)) {
			progress = "0";
		}
		int parsePro = 0;
		try {
			parsePro = (int) Float.parseFloat(progress);
		} catch (Exception e) {
			parsePro = 0;
		}
		return parsePro + "";
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getCount() {
		if (count == null || "".equals(count)) {
			count = "0";
		}
		int parseCount = 0;
		try {
			parseCount = (int) Float.parseFloat(count);
		} catch (Exception e) {
			// TODO: handle exception
			parseCount = 0;
		}
		return parseCount + "";
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getAttribute() {
		if (attribute == null) {
			attribute = "";
		}
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public List<ThemeBean> getThemees() {
		if (themees == null || "".equals(themees)) {
			themees = new ArrayList<ThemeBean>();
		}
		return themees;
	}

	public void setThemees(List<ThemeBean> t) {
		if (themees != null && !"".equals(themees) && themees.size() > 0) {
			// for (int i = 0; i < this.themees.size(); i++) {
			// for (int j = 0; j < t.size(); j++) {
			// if (themees.get(i).getLevelid().equals(t.get(j))) {
			// t.get(j).setLocalUrl(themees.get(i).getLocalUrl());
			// }
			// }
			// }
		} else {
			themees = new ArrayList<ThemeBean>();
		}
		this.themees = t;
	}

	public String getThemeid() {
		if (themeid == null) {
			themeid = "";
		}
		return themeid;
	}

	public void setThemeid(String themeid) {
		this.themeid = themeid;
	}

	public String getThemeName() {
		if (themeName == null) {
			themeName = "";
		}
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getThemeNameEng() {
		if (themeNameEng == null) {
			themeNameEng = "";
		}
		return themeNameEng;
	}

	public void setThemeNameEng(String themeNameEng) {
		this.themeNameEng = themeNameEng;
	}

	public String getThemePic() {
		if (themePic == null) {
			themePic = "";
		}
		return themePic;
	}

	public void setThemePic(String themePic) {
		this.themePic = themePic;
	}

	public String getTotals() {
		if (totals == null || "".equals(totals)) {
			totals = "0";
		}
		int parseTotals = 0;
		try {
			parseTotals = (int) Float.parseFloat(totals);
		} catch (Exception e) {
			parseTotals = 0;
		}
		return parseTotals + "";
	}

	public void setTotals(String totals) {
		this.totals = totals;
	}

	public String getCurProgress() {
		if (curProgress == null || "".equals(curProgress)) {
			curProgress = "0";
		}
		int parseCur = 0;
		try {
			parseCur = (int) Float.parseFloat(curProgress);
		} catch (Exception e) {
			// TODO: handle exception
			parseCur = 0;
		}
		return parseCur + "";
	}

	public void setCurProgress(String curProgress) {
		this.curProgress = curProgress;
	}

	public String getLevels() {
		if (levels == null) {
			levels = "";
		}
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

	public String getAveGread() {
		if (aveGread == null || "".equals(aveGread)) {
			aveGread = "0";
		}
		int parseAvg = 0;
		try {
			parseAvg = (int) Float.parseFloat(aveGread);
		} catch (Exception e) {
			parseAvg = 0;
		}
		return parseAvg + "";
	}

	public void setAveGread(String aveGread) {
		this.aveGread = aveGread;
	}

	public String getThemeStues() {
		return themeStues;
	}

	public void setThemeStues(String themeStues) {
		this.themeStues = themeStues;
	}

	@Override
	public String toString() {
		return "LevelBean [themeid=" + themeid + ", themeName=" + themeName + ", themeNameEng=" + themeNameEng + ", themePic=" + themePic
				+ ", totals=" + totals + ", curProgress=" + curProgress + ", levels=" + levels + ", aveGread=" + aveGread + ", themeStues="
				+ themeStues + ", themees=" + themees + "]";
	}

}
