package com.guoku.guokuv4.entity.test;

public class PBean {
	private ContentBean content;
	private String post_time, type;

	public ContentBean getContent() {
		return content;
	}

	public void setContent(ContentBean content) {
		this.content = content;
	}

	public String getPost_time() {
		return post_time;
	}

	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PBean [content=" + content + ", post_time=" + post_time
				+ ", type=" + type + "]";
	}

}
