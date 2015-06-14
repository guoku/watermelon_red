package com.ekwing.students.entity;

import java.io.Serializable;

public class SBean implements Serializable {
	private String id, // 学校id
			city_id, // 城市id
			name, // 学校名称
			keys, // 学校KEY
			status, // status 学校开阈状态[1->未开;2->试用;3->正式]
			url;// 192.168.1.133/Login/s?name=gzsd1zx //学校二级域名登陆地址

	public String getId() {
		if (id == null) {
			id = "";
		}
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity_id() {
		if (city_id == null) {
			city_id = "";
		}
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeys() {
		if (keys == null) {
			keys = "";
		}
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
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

	public String getUrl() {
		if (url == null) {
			url = "";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
