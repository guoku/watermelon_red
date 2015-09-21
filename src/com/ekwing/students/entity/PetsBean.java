package com.ekwing.students.entity;

public class PetsBean {
	private String pet_conf_id; // 宠物id
	private String user_pet_id; // 宠物id
	private String user_pet_name; // 宠物的名字
	private String pet_name; // 宠物的名字
	private String level; // 宠物的级别
	private String levelTitle; // 宠物的代号
	private String level_title; // 宠物的代号
	private String pet_nextLevel; // 距离下一等级
	private String pet_url; // gif图url
	private String gender; // 性别 1、男 2、女
	private String status;
	private String per_experience;
	private String next_level_need_experience;
	private String current_level_get_experience;
	private String total_experience;
	private String title_type;
	private String current_level_experience;

	public String getUser_pet_id() {
		if (user_pet_id == null) {
			user_pet_id = "";
		}
		return user_pet_id;
	}

	public void setUser_pet_id(String user_pet_id) {
		this.user_pet_id = user_pet_id;
	}

	public String getPer_experience() {
		if (per_experience == null) {
			per_experience = "";
		}
		return per_experience;
	}

	public void setPer_experience(String per_experience) {
		this.per_experience = per_experience;
	}

	public String getNext_level_need_experience() {
		if (next_level_need_experience == null
				|| "".equals(next_level_need_experience)) {
			next_level_need_experience = "0";
		}
		int parseInt = 0;
		try {
			parseInt = (int) Double.parseDouble(next_level_need_experience);
		} catch (Exception e) {
			parseInt = 0;
		}
		return parseInt + "";
	}

	public void setNext_level_need_experience(String next_level_need_experience) {
		this.next_level_need_experience = next_level_need_experience;
	}

	public String getCurrent_level_get_experience() {
		if (current_level_get_experience == null
				|| "".equals(current_level_get_experience)) {
			current_level_get_experience = "0";
		}
		int parseInt = 0;
		try {
			parseInt = (int) Double.parseDouble(current_level_get_experience);
		} catch (Exception e) {
			parseInt = 0;
		}
		return parseInt + "";
	}

	public void setCurrent_level_get_experience(
			String current_level_get_experience) {
		this.current_level_get_experience = current_level_get_experience;
	}

	public String getTotal_experience() {
		if (total_experience == null || "".equals(total_experience)) {
			total_experience = "0";
		}
		return total_experience;
	}

	public void setTotal_experience(String total_experience) {
		this.total_experience = total_experience;
	}

	public String getTitle_type() {
		if (title_type == null) {
			title_type = "";
		}
		return title_type;
	}

	public void setTitle_type(String title_type) {
		this.title_type = title_type;
	}

	public String getCurrent_level_experience() {
		if (current_level_experience == null) {
			current_level_experience = "";
		}
		return current_level_experience;
	}

	public void setCurrent_level_experience(String current_level_experience) {
		this.current_level_experience = current_level_experience;
	}

	public String getLevel_title() {
		if (level_title == null) {
			level_title = "";
		}
		return level_title;
	}

	public void setLevel_title(String level_title) {
		this.level_title = level_title;
	}

	public String getPet_name() {
		if (pet_name == null) {
			pet_name = "";
		}
		return pet_name;
	}

	public void setPet_name(String pet_name) {
		this.pet_name = pet_name;
	}

	public String getStatus() {
		if (status == null) {
			status = "1";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		if (gender == null) {
			gender = "1";
		}
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPet_conf_id() {
		if (pet_conf_id == null) {
			pet_conf_id = "";
		}
		return pet_conf_id;
	}

	public void setPet_conf_id(String pet_conf_id) {
		this.pet_conf_id = pet_conf_id;
	}

	public String getUser_pet_name() {
		if (user_pet_name == null) {
			user_pet_name = "";
		}
		return user_pet_name;
	}

	public void setUser_pet_name(String user_pet_name) {
		this.user_pet_name = user_pet_name;
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

	public String getLevelTitle() {
		if (levelTitle == null) {
			levelTitle = "";
		}
		return levelTitle;
	}

	public void setLevelTitle(String levelTitle) {
		this.levelTitle = levelTitle;
	}

	public String getPet_nextLevel() {
		if (pet_nextLevel == null) {
			pet_nextLevel = "";
		}
		return pet_nextLevel;
	}

	public void setPet_nextLevel(String pet_nextLevel) {
		this.pet_nextLevel = pet_nextLevel;
	}

	public String getPet_url() {
		if (pet_url == null) {
			pet_url = "";
		}
		return pet_url;
	}

	public void setPet_url(String pet_url) {
		this.pet_url = pet_url;
	}
}
