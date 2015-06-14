package com.guoku.guokuv4.entity.test;

public class BannerBean {
	private String url, img;

	public String getUrl() {
		return url;
	}

	public String getUrlLast() {
		if (url.contains("/")) {
			return url.substring(url.lastIndexOf("/") + 1, url.length());
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
