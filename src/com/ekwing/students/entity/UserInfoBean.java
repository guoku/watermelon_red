package com.ekwing.students.entity;

import java.util.ArrayList;
import java.util.List;

public class UserInfoBean {
	private String codePic; // 二维码地址
	private String classes; // 班级
	private String birthday; // 出生年月
	private String sex; // 性别
	private String nextLevel; // 距离下一等级
	private String ebean; // 魔豆
	private String userEmail; // 用户email
	private String nicename; // 用户昵称
	private String avatar; // 头像地址
	private String username; // 用户名
	private String school; // 学校
	private String levels; // 当前等级名称
	private String level; // 当前等级（lv1)
	private String hasFlower; // 鲜花数量
	private String userPhone; // 用户手机
	private String parentPhone; // 家长手机
	private String icon;
	private List<HasIcon> hasIcon = new ArrayList<HasIcon>();
	private boolean vip; // 是否是vip用户 true/false
	private ArrayList<PetsBean> pet; // 宠物bean
	private String first;
	private String grade;

	public String getGrade() {
		if (grade == null) {
			grade = "";
		}
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getFirst() {
		if (first == null) {
			first = "2";
		}
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public boolean isVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
	}

	public ArrayList<PetsBean> getPet() {
		if (pet == null) {
			pet = new ArrayList<PetsBean>();
		}
		return pet;
	}

	public void setPet(ArrayList<PetsBean> pet) {
		this.pet = pet;
	}

	public String getIcon() {
		return icon;
	}

	public String getLevel() {
		if (level == null) {
			level = "";
		}
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCodePic() {
		if (codePic == null) {
			codePic = "";
		}
		return codePic;
	}

	public String getEbean() {
		if (ebean == null) {
			ebean = "";
		}
		return ebean;
	}

	public void setEbean(String ebean) {
		this.ebean = ebean;
	}

	public void setCodePic(String codePic) {
		this.codePic = codePic;
	}

	public String getClasses() {
		if (classes == null) {
			classes = "";
		}
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getBirthday() {
		if (birthday == null) {
			birthday = "";
		}
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		if (sex == null) {
			sex = "";
		}
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNextLevel() {
		if (nextLevel == null) {
			nextLevel = "";
		}
		return nextLevel;
	}

	public void setNextLevel(String nextLevel) {
		this.nextLevel = nextLevel;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getNicename() {
		if (nicename == null) {
			nicename = "";
		}
		return nicename;
	}

	public void setNicename(String nicename) {
		this.nicename = nicename;
	}

	public String getAvatar() {
		if (avatar == null) {
			avatar = "";
		}
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSchool() {
		if (school == null) {
			school = "";
		}
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
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

	public String getHasFlower() {
		if (hasFlower == null || "".equals(hasFlower)) {
			return "0";
		}
		return hasFlower;
	}

	public void setHasFlower(String hasFlower) {
		this.hasFlower = hasFlower;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public List<HasIcon> getHasIcon() {
		if (hasIcon == null) {
			hasIcon = new ArrayList<HasIcon>();
		}
		return hasIcon;
	}

	public void setHasIcon(List<HasIcon> hasIcon) {
		this.hasIcon = hasIcon;
	}

	@Override
	public String toString() {
		return "UserInfoBean [codePic=" + codePic + ", classes=" + classes
				+ ", birthday=" + birthday + ", sex=" + sex + ", nextLevel="
				+ nextLevel + ", ebean=" + ebean + ", userEmail=" + userEmail
				+ ", nicename=" + nicename + ", avatar=" + avatar
				+ ", username=" + username + ", school=" + school + ", levels="
				+ levels + ", hasFlower=" + hasFlower + ", userPhone="
				+ userPhone + ", parentPhone=" + parentPhone + ", icon=" + icon
				+ ", hasIcon=" + hasIcon + "]";
	}

}
