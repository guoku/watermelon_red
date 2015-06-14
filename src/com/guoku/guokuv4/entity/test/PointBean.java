package com.guoku.guokuv4.entity.test;

public class PointBean {
	private ContentBean content;
	private String type, created_time;

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public ContentBean getContent() {
		return content;
	}

	public void setContent(ContentBean content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PointBean [content=" + content + ", type=" + type + "]";
	}

}
