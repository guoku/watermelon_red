package com.guoku.guokuv4.entity.test;

import java.io.Serializable;

import com.guoku.guokuv4.utils.StringUtils;

public class UserBean implements Serializable {
//	private String is_censor, bio, avatar_large, user_id, following_count,
//			fan_count, city, gender, avatar_small, is_active,
//			entity_note_count, tag_count, website, like_count, relation,
//			location, nickname, email;
//	
    private String website;
    private String bio;
    private String avatar_large;
    private String user_id;
    private String following_count;
    private String fan_count;
    private String city;
    private String gender;
    private String avatar_small;
    private String is_active;
    private String entity_note_count;
    private String tag_count;
    private String like_count;
    private String relation;
    private String location;
    private String article_count;
    private String nickname;
    private String email;


	public String getArticle_count() {
		return article_count;
	}

	public void setArticle_count(String article_count) {
		this.article_count = article_count;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAvatar_large() {
		return avatar_large;
	}

	public String get240() {
		if(!StringUtils.isEmpty(avatar_large)){
			if (avatar_large.contains("images")
					&& !avatar_large.contains("woman.png")
					&& !avatar_large.contains("man.png")) {
				return avatar_large.replaceFirst("images", "images/240");
			}
		}
		return avatar_large;
	}

	public String get50() {
		if (avatar_large.contains("images")
				&& !avatar_large.contains("woman.png")
				&& !avatar_large.contains("man.png")) {
			return avatar_large.replaceFirst("images", "images/100");
		}
		return avatar_large;
	}

	public void setAvatar_large(String avatar_large) {
		this.avatar_large = avatar_large;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFollowing_count() {
		return following_count;
	}

	public void setFollowing_count(String following_count) {
		this.following_count = following_count;
	}

	public String getFan_count() {
		if(StringUtils.isEmpty(fan_count)){
			return "0";
		}
		return fan_count;
	}

	public void setFan_count(String fan_count) {
		this.fan_count = fan_count;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		if (!StringUtils.isEmpty(gender)) {
			if (gender.equals("F")) {
				return "女";
			} else {
				return "男";
			}
		}
		return "男";

	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatar_small() {
		return avatar_small;
	}

	public void setAvatar_small(String avatar_small) {
		this.avatar_small = avatar_small;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getEntity_note_count() {
		return entity_note_count;
	}

	public void setEntity_note_count(String entity_note_count) {
		this.entity_note_count = entity_note_count;
	}

	public String getTag_count() {
		return tag_count;
	}

	public void setTag_count(String tag_count) {
		this.tag_count = tag_count;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLike_count() {
		return like_count;
	}

	public void setLike_count(String like_count) {
		this.like_count = like_count;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
