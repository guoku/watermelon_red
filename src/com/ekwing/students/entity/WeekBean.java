package com.ekwing.students.entity;

public class WeekBean {
	private String days; // 日期
	private String weeks;
	private String status; // 作业状态：0,无作业 。1，未完成 。2，完成
	private String times;

	public String getTimes() {
		if (times == null) {
			times = "";
		}
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getDays() {
		if (days == null) {
			days = "";
		}
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getWeeks() {
		if (weeks == null) {
			weeks = "";
		}
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getStatus() {
		if (status == null) {
			status = "";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "WeekBean [days=" + days + ", weeks=" + weeks + ", status="
				+ status + ", times=" + times + "]";
	}

	public WeekBean(String days, String weeks, String status, String times) {
		super();
		this.days = days;
		this.weeks = weeks;
		this.status = status;
		this.times = times;
	}

	public WeekBean() {
		super();
	}

}
